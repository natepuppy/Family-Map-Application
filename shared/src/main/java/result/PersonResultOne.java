package result;

/** The purpose of this class is to hold the contents of the result of an API call to /person/[personID] */
public class PersonResultOne {
    /** Name of user account this person belongs to */
    private String descendant;
    /** Person’s unique ID */
    private String personID;
    /** Person’s last name */
    private String firstName;
    /** Person’s gender ("m" or "f") */
    private String lastName;
    /** Person’s last name */
    private String gender;
    /** ID of person’s father [OPTIONAL, can be missing] */
    private String father;
    /** ID of person’s mother [OPTIONAL, can be missing] */
    private String mother;
    /** ID of person’s spouse [OPTIONAL, can be missing] */
    private String spouse;
    /** This variable is a String that holds the description of the error that occurred (if there was one) */
    private String errorMessage;

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public PersonResultOne(String descendant, String personID, String firstName, String lastName, String gender, String father, String mother, String spouse, String errorMessage) {
        this.descendant = descendant;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
        this.errorMessage = errorMessage;
    }

    /** Constructor with no parameters. All members are initialized to null. */
    public PersonResultOne() {
        this.descendant = null;
        this.personID = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.father = null;
        this.mother = null;
        this.spouse = null;
        this.errorMessage = null;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
