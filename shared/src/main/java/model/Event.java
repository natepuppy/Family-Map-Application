package model;

/** The purpose of this class is to hold the data that is accessed from the Event table in the database. */
public class Event {
    /** Unique identifier for this event (non-empty string) */
    private String eventID;// TODO
    /** User (Username) to which this person belongs */
    private String descendant;// TODO
    /** ID of person to which this event belongs */
    private String personID; // TODO
    /** Latitude of event’s location (float) */
    private float latitude;// TODO
    /** Longitude of event’s location (float) */
    private float longitude;// TODO
    /** Country in which event occurred */
    private String country;// TODO
    /** City in which event occurred */
    private String city; // TODO
    /** Type of event (birth, baptism, christening, marriage, death, etc.) */
    private String eventType; // TODO
    /** Year in which event occurred (int) */
    private int year;// TODO

    /** Constructor with no parameters. All members are initialized to null. */
    public Event() {
        this.eventID = null;
        this.descendant = null;
        this.personID = null;
        //this.latitude = 0.0F;
        //this.longitude = 0.0F;
        this.country = null;
        this.city = null;
        this.eventType = null;
        //this.year = 0;
    }

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public Event(String eventID, String username, String personID, float latitude, float longitude,
                 String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.descendant = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }


    /** convert all strings in the class to lower case */
    public void toLowerCase() {
        if (this.eventID != null) this.eventID = this.eventID.toLowerCase();
        if (this.descendant != null) this.descendant = this.descendant.toLowerCase();
        if (this.personID != null) this.personID = this.personID.toLowerCase();
        if (this.country != null) this.country = this.country.toLowerCase();
        if (this.city != null) this.city = this.city.toLowerCase();
        if (this.eventType != null) this.eventType = this.eventType.toLowerCase();
    }

    /**
     * Get the Event ID
     */
    public String getEventID() {
        return eventID;
    }


    /**
     * Set the Event ID
     */
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }


    /**
     * Get the user's name
     */
    public String getDescendant() {
        return descendant;
    }


    /**
     * Set the user's name
     */
    public void setDescendant(String username) {
        this.descendant = username;
    }


    /**
     * Get the Person's ID
     */
    public String getPersonID() {
        return personID;
    }


    /**
     * Set the Person's ID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }


    /**
     * Get the Latitude
     */
    public float getLatitude() {
        return latitude;
    }


    /**
     * Set the Latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }


    /**
     * Get the Longitude
     */
    public float getLongitude() {
        return longitude;
    }


    /**
     * Set the Longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }


    /**
     * Get the Country
     */
    public String getCountry() {
        return country;
    }


    /**
     * Set the Country
     */
    public void setCountry(String country) {
        this.country = country;
    }


    /**
     * Get the City
     */
    public String getCity() {
        return city;
    }


    /**
     * Set the City
     */
    public void setCity(String city) {
        this.city = city;
    }


    /**
     * Get Event Type
     */
    public String getEventType() {
        return eventType;
    }


    /**
     * Set the Event Type
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    /**
     * Get the Year
     */
    public int getYear() {
        return year;
    }


    /**
     * Set the Year
     */
    public void setYear(int year) {
        this.year = year;
    }




    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Event) {
            Event oEvent = (Event) o;
            if (oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getDescendant().equals(getDescendant()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude() == (getLatitude()) &&
                    oEvent.getLongitude() == (getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear())) {
                return true;
            }
        }
        return false;
    }
}
