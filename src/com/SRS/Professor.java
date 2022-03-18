package com.SRS;

import java.util.ArrayList;

/**
 * 1. Involved in Association, one to many with section class. Therefore there is a Section handle to maintain
 *    handling multiple sections. And this is done via ArrayList.
 * 2. Inherits from the person class therefore has to implement display, uses super in constructor etc.
 */

public class Professor extends Person{

    //-------------------
    // Attributes
    //-------------------

    private String title;
    private String department;
    private ArrayList<Section> teaches;

    //----------------
    // Accessor methods.
    //----------------
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDepartment(String dept) {
        department = dept;
    }

    public String getDepartment() {
        return department;
    }
    //----------------
    // Constructor(s).
    //----------------

    public Professor(String name, String ssn, String title, String dept){
        // Reuse the parent constructor with two arguments.
        super(name, ssn);

        setTitle(title);
        setDepartment(dept);

        // Note that we're instantiating empty support Collection(s).

        teaches = new ArrayList<Section>();
    }

    //-----------------------------
    // Miscellaneous other methods.
    //-----------------------------
    public void display(){
        // First, let's display the generic Person info.

        super.display();

        // Then, display Professor-specific info.
        System.out.println("Professor-Specific Information: ");
        System.out.println("\t Title: " + getTitle());
        System.out.println("\t Teaches for Dept. : " + getDepartment());
        displayTeachingAssignments();

        // Finish with a blank line.
        System.out.println();
        
    }

    private void displayTeachingAssignments() {
        System.out.println("Teaching Assignments for " + getName() + ":");

        // We'll step through the teaches ArrayList, processing
        // Section objects one at a time.
        if (teaches.size() == 0) {
            System.out.println("\t(none)");
        }

        else for (Section s : teaches) {
            // Note how we call upon the Section object to do
            // a lot of the work for us!

            System.out.println("\tCourse No.:  " +
                    s.getRepresentedCourse().getCourseNo());
            System.out.println("\tSection No.:  " +
                    s.getSectionNo());
            System.out.println("\tCourse Name:  " +
                    s.getRepresentedCourse().getCourseName());
            System.out.println("\tDay and Time:  " +
                    s.getDayOfWeek() + " - " +
                    s.getTimeOfDay());
            System.out.println("\t-----");
        }
    }

    /**
     * Method accepts a section object reference as an argument and begins by storing this reference in the ArrayList.
     *
     */
    public void agreeToTeach(Section s){
        teaches.add(s);
        // We accomplish bi-directionality by invoking the section objects set instructor method, passing in a handle
        // on the professor object whose methods we are in midst of executing via the this keyword. Technique -->
        // Object Self referencing.
        s.setInstructor(this);
    }

    @Override
    public String toString() {
        return getName() + "(" + getTitle() + ", " +
                getDepartment() + ")";
    }
}