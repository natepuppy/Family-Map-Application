package service;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.Random;
import java.util.UUID;

import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import jsonModels.Location;
import jsonModels.LocationData;
import jsonModels.Names;

import model.Event;
import model.Person;
import model.User;
import request.FillRequest;
import result.FillResult;

/** This purpose of this class is to interact with the database through Dao's in
 * order to fill the database with info for a specific username. */
public class FillService {
    /** Empty constructor that doesn't initialize any variables */
    public FillService() {
    }

    /** This function is the service for the /fill/[username]/{generations} API endpoint. It processes the
     * request and returns the result for this specific API call.
     *
     * @param request which holds all info for the Fill request
     * @return FillResult Object
     * */
    public FillResult fill(FillRequest request) throws Exception {
        FillResult result = new FillResult();
        // create instance of database
        Database db = new Database();

        try {
            // make sure generations isn't negative
            if (request.getGenerations() < 0) {
                result.setErrorMessage("Invalid generations parameter");
                return result;
            }

            // open database connection
            Connection conn = db.openConnection();

            // check for user
            UserDao userDao = new UserDao(conn);
            User user  = userDao.query(request.getUserName());
            if (user == null) {
                result.setErrorMessage("Invalid username");
                db.closeConnection(false);
                return result;
            }

            // Create person
            Person person = new Person();
            person.setFirstName(user.getFirstName());
            person.setLastName(user.getLastName());
            person.setPersonID(user.getPersonID());
            person.setGender(user.getGender());
            person.setDescendant(user.getUserName());

            // fill database with events and people for that person
            deletePersonFromDatabase(conn, request.getUserName());
            Event event = createSingleEvent(conn, user);
            fillWithPeople(conn, person, request.getGenerations(), request.getUserName(), event.getYear());
            db.closeConnection(true);

            // calculate what was entered into the database
            double number = Math.pow(2, (request.getGenerations() + 1));
            int numPersons = (int) number - 1;
            int numEvents = (3 * numPersons) - 2;
            result.setMessage("Successfully added " + numPersons + " persons and " + numEvents + " events to the database");  // what about events???
        }
        catch (DataAccessException e) {
            result.setErrorMessage("Internal server error");
            db.closeConnection(false);
            return result;
        }
        return result;
    }

    private boolean fillWithPeople(Connection conn, Person person, int generations, String descendant, int year) {
        if (generations >= 0) {
            try {
                PersonDao dao = new PersonDao(conn);
                // create mother and father objects
                Person[] persons = generatePersons(descendant);
                Person mother = persons[0];
                Person father = persons[1];

                // finish and insert person
                person.setFather(father.getPersonID());
                person.setMother(mother.getPersonID());
                dao.insert(person);

                // insert event objects for the new father and mother
                int birthDate = 0;
                if (generations > 0) birthDate = insertEventsForCouple(conn, father, mother, year, descendant);

                // set the father and mother of the original person that was passed in
                person.setMother(mother.getPersonID());
                person.setFather(father.getPersonID());
                generations--;

                // recursively call
                fillWithPeople(conn, mother, generations, descendant, birthDate);
                fillWithPeople(conn, father, generations, descendant, birthDate);
            }
            catch (DataAccessException e) {
                return false;
            }
        }
        return true;
    }

    private Person[] generatePersons(String descendant) {
        // create reader to read in a static file
        Reader readerFemaleNames = null;
        Reader readerMaleNames = null;
        Reader readerSurname = null;

        try {
            // read in file
            readerFemaleNames = new FileReader("libs/json/fnames.json");
            readerMaleNames = new FileReader("libs/json/mnames.json");
            readerSurname = new FileReader("libs/json/snames.json");
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        // convert from json
        Gson gson = new Gson();
        Names femData = gson.fromJson(readerFemaleNames, Names.class);
        Names maleData = gson.fromJson(readerMaleNames, Names.class);
        Names surData = gson.fromJson(readerSurname, Names.class);

        // get random numbers so the data is random
        Random rand = new Random();
        int x = rand.nextInt(femData.getData().length);
        int y = rand.nextInt(maleData.getData().length);
        int z = rand.nextInt(surData.getData().length);

        // get random data from array
        String femaleName = femData.getData()[x];
        String maleName = maleData.getData()[y];
        String surname = surData.getData()[z];

        // create hash values for father and mother
        String fatherHash = UUID.randomUUID().toString().toLowerCase();
        String motherHash = UUID.randomUUID().toString().toLowerCase();

        // create father and mother
        Person mother = new Person(motherHash, descendant, femaleName, surname, "f", "", "", fatherHash);
        Person father = new Person(fatherHash, descendant, maleName, surname, "m", "", "", motherHash);

        // add both to array to return
        Person[] persons = new Person[2];
        persons[0] = mother;
        persons[1] = father;

        return persons;
    }

    private int insertEventsForCouple(Connection conn, Person father, Person mother, int year, String descendant) throws DataAccessException {  // year = year their kid was born; descendant = name of their kid
        Reader locations = null;

        // get locations from file
        try {
            locations = new FileReader("libs/json/locations.json");
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        // convert location data to object
        Gson gson = new Gson();
        LocationData locData = gson.fromJson(locations, LocationData.class);

        // pick five random numbers that are within the langth of the locData array
        Random rand = new Random();
        int a = rand.nextInt(locData.getData().length);
        int b = rand.nextInt(locData.getData().length);
        int c = rand.nextInt(locData.getData().length);
        int d = rand.nextInt(locData.getData().length);
        int e = rand.nextInt(locData.getData().length);

        // grab the 5 events at those five spots in the array
        Location location1 = locData.getData()[a];
        Location location2 = locData.getData()[b];
        Location location3 = locData.getData()[c];
        Location location4 = locData.getData()[d];
        Location location5 = locData.getData()[e];

        // generate dates for the parents
        int birthYear1 = year - (35 + rand.nextInt(5));
        int birthYear2 = year - (35 + rand.nextInt(5));
        int marriageYear = year - (5 + rand.nextInt(5));

        // generate events for the parents
        Event birth1 = new Event(UUID.randomUUID().toString().toLowerCase(), descendant, father.getPersonID(), location1.getLatitude(),
                location1.getLongitude(), location1.getCountry(), location1.getCity(), "birth",birthYear1);
        Event birth2 = new Event(UUID.randomUUID().toString().toLowerCase(), descendant, mother.getPersonID(), location2.getLatitude(),
                location2.getLongitude(), location2.getCountry(), location2.getCity(), "birth",birthYear2);
        Event baptism1 = new Event(UUID.randomUUID().toString().toLowerCase(), descendant, father.getPersonID(), location3.getLatitude(),
                location3.getLongitude(), location3.getCountry().toLowerCase(), location3.getCity(), "baptism",(birthYear1 + 8));
        Event baptism2 = new Event(UUID.randomUUID().toString().toLowerCase(), descendant, mother.getPersonID(), location4.getLatitude(),
                location4.getLongitude(), location4.getCountry(), location4.getCity(),"baptism",(birthYear2 + 8));
        Event marriage1 = new Event(UUID.randomUUID().toString().toLowerCase(), descendant, father.getPersonID(), location5.getLatitude(),
                location5.getLongitude(), location5.getCountry(), location5.getCity(),"marriage",marriageYear);
        Event marriage2 = new Event(UUID.randomUUID().toString().toLowerCase(), descendant, mother.getPersonID(), location5.getLatitude(),
                location5.getLongitude(), location5.getCountry(), location5.getCity(),"marriage",marriageYear);

        // insert events into database
        EventDao dao = new EventDao(conn);
        dao.insert(birth1);
        dao.insert(birth2);
        dao.insert(baptism1);
        dao.insert(baptism2);
        dao.insert(marriage1);
        dao.insert(marriage2);

        return (birthYear1 + birthYear2) / 2;
    }

    private Event createSingleEvent(Connection conn, User user) throws DataAccessException {  // year = year their kid was born; descendant = name of their kid
        Reader locations = null;

        // get locations from file
        try {
            locations = new FileReader("libs/json/locations.json");
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        // convert to json
        Gson gson = new Gson();
        LocationData locData = gson.fromJson(locations, LocationData.class);

        // get a location
        Random rand = new Random();
        int a = rand.nextInt(locData.getData().length);
        Location location1 = locData.getData()[a];

        // generate a random birth year
        int birthYear = 1900 + (rand.nextInt(119));
        Event birth = new Event(UUID.randomUUID().toString().toLowerCase(), user.getUserName(), user.getPersonID(), location1.getLatitude(),
                location1.getLongitude(), location1.getCountry(), location1.getCity(), "birth",birthYear);

        // insert into database
        EventDao dao = new EventDao(conn);
        dao.insert(birth);
        return birth;
    }

    // This function will delete all ancestor data for a specific user
    // It is currently decommissioned because it was deleting things with
    // the test cases before i could test them
    private void deletePersonFromDatabase(Connection conn, String userName) throws  DataAccessException {
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
//        personDao.delete(userName);
//        eventDao.delete(userName);
    }
}
