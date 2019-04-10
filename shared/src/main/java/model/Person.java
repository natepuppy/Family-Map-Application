package model;

import java.util.Objects;

/** The purpose of this class is to hold the data that is accessed from the Person table in the database. */
public class Person {
    /** Unique identifier for this person (non-empty string) */
    private String personID;
    /** User (Username) to which this person belongs */
    private String descendant;
    /** Person’s first name (non-empty string) */
    private String firstName;
    /** Person’s last name (non-empty string) */
    private String lastName;
    /** Person’s gender (string: "f" or "m") */
    private String gender;
    /** ID of person’s father (possibly null) */
    private String father;
    /** ID of person’s mother (possibly null) */
    private String mother;
    /** ID of person’s spouse (possibly null) */
    private String spouse;

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public Person(String personID, String descendant, String firstName, String lastName, String gender, String father, String mother, String spouse) {
        this.personID = personID; // TODO
        this.descendant = descendant;
        this.firstName = firstName; // TODO
        this.lastName = lastName; // TODO
        this.gender = gender; // TODO
        this.father = father; // TODO
        this.mother = mother; // TODO
        this.spouse = spouse;
    }

    /** Constructor with no parameters. All members are initialized to null. */
    public Person() {
        this.personID = null;
        this.descendant = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.father = null;
        this.mother = null;
        this.spouse = null;
    }


    /** convert all strings in the class to lower case */
    public void toLowerCase() {
        if (this.descendant != null) this.descendant = this.descendant.toLowerCase();
        if (this.personID != null) this.personID = this.personID.toLowerCase();
        if (this.firstName != null) this.firstName = this.firstName.toLowerCase();
        if (this.lastName != null) this.lastName = this.lastName.toLowerCase();
        if (this.gender != null) this.gender = this.gender.toLowerCase();
        if (this.father != null) this.father = this.father.toLowerCase();
        if (this.mother != null) this.mother = this.mother.toLowerCase();
        if (this.spouse != null) this.spouse = this.spouse.toLowerCase();
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID) &&
                Objects.equals(descendant, person.descendant) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(gender, person.gender) &&
                Objects.equals(father, person.father) &&
                Objects.equals(mother, person.mother) &&
                Objects.equals(spouse, person.spouse);
    }
}
