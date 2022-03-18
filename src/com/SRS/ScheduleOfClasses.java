package com.SRS;

import java.util.HashMap;
import java.util.ArrayList;


/**
 * This is a fairly simple class. It helps show how to encapsulate a collection object within some other class.
 * It consists of only a string attribute representing the semester for which the schedule is valid and a HashMap used
 * to maintain handle on all of the sections that are being offered in that semester.
 */
public class ScheduleOfClasses {

    //------------
    // Attributes.
    //------------
    private String semester;

    // This HasMap stores Section object references, using a String concatenation
    // of course no. and section no. as the key, for example, "MATH01 - 1".

    private HashMap<String, Section> sectionsOffered;

    //----------------
    // Constructor(s).
    //----------------
    public ScheduleOfClasses(String semester){
        setSemester(semester);

        // Note that we're instantiating empty support Collection(s).

        sectionsOffered = new HashMap<String, Section>();
    }


    //------------------
    // Accessor methods.
    //------------------

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public HashMap<String, Section> getSectionsOffered() {
        return sectionsOffered;
    }

    public void setSectionsOffered(HashMap<String, Section> sectionsOffered) {
        this.sectionsOffered = sectionsOffered;
    }

    //-----------------------------
    // Miscellaneous other methods.
    //-----------------------------

    public void display(){
        System.out.println("Schedule of Classes for " + getSemester());
        System.out.println();

        // Iterate through all the values in the HashMap.

        for(Section s : sectionsOffered.values()){
            s.display();
            System.out.println();
        }
    }

    /**
     * This method is used to add a Section object to the HashMap, and then to bi-directionally link this ScheduleOfClasses
     * object back to the Section.
     */
    public void addSection(Section s){
        // We formulate a key by concatenating the course no.
        // and section no., separated by a hyphen.
        String key = s.getRepresentedCourse().getCourseNo() +
                " - " + s.getSectionNo();
        sectionsOffered.put(key, s);

        // Bidirectionally link the ScheduleOfClasses back to the Section.
        s.setOfferedIn(this);

    }


    /**
     * This is a convenience method that is used to look up a section in the encapsulated collection using the full section
     * number that is passed in as the look up key
     *
     */
    // The full section number is a concatenation of the course no. and section no., separated by hyphen;
    // e.g., "ART101 - 1".
    public Section findSection(String fullSectionNo){

        return sectionsOffered.get(fullSectionNo);
    }

    /**
     * This is a convenience method that is used to determine whether the encapsulated collection is empty. Internally it delegates
     * the work of making such a determination to the sectionsOffered collection.
     */
    public boolean isEmpty(){
        if(sectionsOffered.size() == 0) {
            return true;
        }
        else{
            return false;
        }
    }
}
