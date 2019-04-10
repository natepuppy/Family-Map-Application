package result;

/** The purpose of this class is to hold the contents of the result of an API call to /event/[eventID] */
public class EventResultOne {
    /** Name of user account this event belongs to (non-empty string) */
    private String descendant;
    /** Event’s unique ID (non-empty string) */
    private String eventID;
    /** ID of the person this event belongs to (non-empty string) */
    private String personID;
    /** Latitude of the event’s location (number) */
    private float latitude;
    /** Longitude of the event’s location (number) */
    private float longitude;
    /** Name of country where event occurred (non-empty string) */
    private String country;
    /** Name of city where event occurred (non-empty string) */
    private String city;
    /** Type of event (“birth”, “baptism”, etc.) (non-empty string) */
    private String eventType;
    /** Year the event occurred (integer) */
    private int year;
    /** This variable is a String that holds the description of the error that occurred (if there was one) */
    private String errorMessage;


    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public EventResultOne(String descendant, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year, String errorMessage) {
        this.descendant = descendant;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.errorMessage = errorMessage;
    }

    /** Constructor with no parameters. All members are initialized to null or zero. */
    public EventResultOne() {
        this.descendant = null;
        this.eventID = null;
        this.personID = null;
        //this.latitude = 0F;
        //this.longitude = 0F;
        this.country = null;
        this.city = null;
        this.eventType = null;
        //this.year = 0;
        this.errorMessage = null;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
