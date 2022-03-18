package com.SRS;

// We are making this class abstract because we do not wish for it
// to be instantiated.

/** This class contains
 * 1. private attributes.
 * 2. Constructor and overriden default constructor.
 * 3. getters and setters for those attributes.
 * 4. ToString and Display methods that will get inherited by other classes.
*/
public abstract class Person {

    //-------------
    //Attributes
    //-------------
    private String name;
    private String ssn;

    //-------------
    //Constructor(s).
    //-------------

    public Person(String name, String ssn){
        this.setName(name);
        this.setSsn(ssn);
    }
    // This is just a replacement of the default constructor.
    public Person(){
        this.setName("?");
        this.setSsn("???-??-???");
    }
    //--------------
    // Accessor Methods.
    // Setters and Getters
    //---------------

    public void setName(String n){
        this.name = n;
    }

    public void setSsn(String s){
        this.ssn = s;
    }

    public String getName(){
        return name;
    }

    public String getSsn(){
        return ssn;
    }

    //----------------------
    //Miscellaneous other methods
    //----------------------

    // We'll let each subclass determine how it wishes to be
    // represented as a String value. Therefore each subclass that inherits Person will have to implement
    // this method.
    public abstract String toString();

    // We are adding body to this method solely to be able to generate the bare bones. When this method is inherited
    // other classes can take the skeleton and further enhance it.
    public void display(){
        System.out.println("Person Information: ");
        System.out.println("\t Name : " + this.getName());
        System.out.println("\tSoc. Security No.: " + this.getSsn());
    }
}
