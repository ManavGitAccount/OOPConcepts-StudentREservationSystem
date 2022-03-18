package com.SRS;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Participates in two associations:
 * 1. Offered As. A one-to-Many association with a Section class. A Course is offered to many sections.
 * 2. Prerequisite. A many to many reflexive association. A course in itself can have many pre-requisite courses. This
 * will allow the course object to maintain handle on other course handle objects.
 */
public class Course {

    //--------------------
    // Attributes.
    //--------------------

    private String courseNo;
    private String courseName;
    private double credits;
    private ArrayList<Section> offeredAsSection;
    private ArrayList<Course> prerequisites;

    //------------------
    // Accessor methods.
    //------------------

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String cNo) {
        this.courseNo = cNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String cName) {
        this.courseName = cName;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public ArrayList<Section> getOfferedAsSection() {
        return offeredAsSection;
    }

    public void setOfferedAsSection(ArrayList<Section> offeredAsSection) {
        this.offeredAsSection = offeredAsSection;
    }

    public Collection<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(ArrayList<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }



    //----------------------
    // Constructor(s)
    //----------------------
    public Course(String cNo, String cName, double credits) {
        setCourseNo(cNo);
        setCourseName(cName);
        setCredits(credits);

        // Note that we're instantiating empty support Collection(s).

        offeredAsSection = new ArrayList<Section>();
        prerequisites = new ArrayList<Course>();
    }

    //-----------------------
    // Course Methods
    //-----------------------

    /**
     * Inspects the size of the prerequisites arrayList to determine whether or not a given Course
     * has any prerequisite Courses
     */
    public boolean hasPrerequisites(){
        if(prerequisites.size() > 0){
            return true;
        }
        else return false;
    }

    public void addPrerequisite(Course c){
        prerequisites.add(c);
    }


    /**
     * Returns a reference to the prerequisite ArrayList as a generic Collection reference,
     * hiding the true identity of the type of collection we've encapsulated.
     */
    public Collection<Course> getPrerequisite(){
        return prerequisites;
    }

    /**
     * This section invokes the Section class constructor to instantiate a new section object s. So we are storing
     * one handle on this Section object in the offeredAsSection ArrayList before returning a second handle on the object
     * to the client code.
     */

    public Section scheduleSection(char day, String time, String room, int capacity){
        //Create a new section (note the creative way in
        // which we are assigning a section number) ...
        Section s = new Section(offeredAsSection.size() + 1, day, time, this,
                room, capacity);

        //....and then to remember it.
        this.addSection(s);

        return s;
    }

    private void addSection(Section s) {
        offeredAsSection.add(s);
    }

    //-----------------------------
    // Miscellaneous other methods.
    //-----------------------------
    public void display(){
        System.out.println("Course Information");
        System.out.println("\t Course No.:  " + getCourseNo());
        System.out.println("\t Course Name: " + getCourseName());
        System.out.println("\t Credits:  "  + getCredits());
        System.out.println("\t Prerequisite Courses:");

        for(Course c : prerequisites){
            System.out.println("\t\t" + c.toString());
        }

        // Note use of print vs println in next line of code.

        System.out.print("\t Offered As Section(s):  ");
        for(Section s : offeredAsSection) {
            System.out.print(s.getSectionNo() + "  ");
        }

        // Finish with a blank line.

        System.out.println();
    }

    public String toString(){
        return getCourseNo() + ": " + getCourseName();
    }


}
