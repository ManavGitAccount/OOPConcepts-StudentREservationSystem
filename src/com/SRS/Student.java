package com.SRS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * 1. Student class extends person. Therefore student is a subclass.
 * 2. Association --> attends, a many to many association with the Section class. A student attends many sections.
 * Therefore we need handles on many section objects.
 * 3. Association --> maintains, a one-to-one association with the Transcript class. A student owns a transcript.
 *  Therefore we need handle on one transcript object reference.
 * 4. Overloaded constructors.
 * 5. Display Method inherited from Person
 */
public class Student extends Person {

    //----------------
    //Attributes
    //----------------

    private String major;
    private String degree;
    private Transcript transcript;
    private ArrayList<Section> attends;

    //-----------------
    // Constructors. As you can see Student constructor are overloaded. The class has two constructors and one of
    // which is overloaded.
    //-----------------
    public Student(String name, String ssn, String major, String degree){

        // Reuse the code of the parent's constructor.
        super(name, ssn);
        this.setMajor(major);
        this.setDegree(degree);
        // Create a brand new Transcript to serve as this Student's transcript.
        this.setTranscript(new Transcript(this));
        // Instantiating empty support Collection.
        attends = new ArrayList<Section>();
    }

    // A second simpler constructor
    public Student(String ssn){
        // Reuse the code of the first student constructor
        this("TBD", ssn, "TBD", "TBD");
    }


    //--------------------
    //Accessor Methods
    //--------------------
    public void setDegree(String d) {
        this.degree = d;
    }
    public String getDegree(){
        return degree;
    }

    public void setMajor(String m) {
        this.major = m;
    }
    
    public String getMajor(){
        return major;
    }

    public void setTranscript(Transcript t){
        this.transcript = t;
    }
    
    public Transcript getTranscript(){
        return transcript;
    }

    //----------------------
    //Miscellaneous other methods
    //----------------------

    // Method inherited from the parent class which is person.
    public void display(){

        //First lets display the generic Person info.
        super.display();
        
        //Then, display the Student-specific info.
        System.out.println("Student-Specific Information: ");
        System.out.println("\t Major: " + this.getMajor());
        System.out.println("\t Degree: " + this.getDegree());
        this.displayCourseSchedule();
        this.printTranscript();
        
        // Finish with a blank line
        System.out.println();
    }

    private void displayCourseSchedule() {
        // Display a title first.
        System.out.println("Course Schedule for " + this.getName());

        // Step through the ArrayList of Section objects, processing these one by one
        for(Section s : attends){
            // Since the attends ArrayList contains sections that the Student took in the past as well as those
            // for which the Student is currently enrolled, we only want to report on those for which a grade has not
            // yet been assigned.
            if(s.getGrade(this) == null){
                System.out.println("\t Course No.:   " +
                        s.getRepresentedCourse().getCourseNo());
                System.out.println("\t Section No.:  " +
                        s.getSectionNo());
                System.out.println("\t Course Name:  " +
                        s.getRepresentedCourse().getCourseName());
                System.out.println("\t Meeting Day and Time Held:  " +
                        s.getDayOfWeek() + " - " + s.getTimeOfDay());
                System.out.println("\t Room Location:  " +
                        s.getRoom());
                System.out.println("\t Professor's Name :  " +
                        s.getInstructor().getName());
                System.out.println("\t-----------------");

            }
        }
    }
    // We are forced to program this method because it is specified
    // as an abstract method in our parent class (Person); failing to
    // do so would render the Student class abstract, as well.
    //
    // For a Student, we wish to return a String as follows:
    //
    // 	Joe Blow (123-45-6789) [Master of Science - Math]

    @Override
    public String toString() {
        return this.getName() + "(" + this.getSsn() + ") [" + this.getDegree() + " - " + this.getMajor()
                + "]";
    }

    /**
     * example of delegation (https://www.javaguides.net/2018/08/delegation-in-java-with-example.html)
     * When a student enrolls in a Section, this method will be used to pass a reference to that Section
     to the Student object so that the Section reference maybe stored in the attends ArrayList
     **/
    public void addSection(Section s){
        attends.add(s);
    }

    /**
     * example of delegation (https://www.javaguides.net/2018/08/delegation-in-java-with-example.html)
     * When a student withdraws from a Section, this method will be used to pass a reference to the dropped Section
     to the Student object. The student object in turn delegates the work of removing the Section to the attends
     ArrayList by invoking it's remove method.
     **/
    public void dropSection(Section s){
        attends.remove(s);
    }

    /**
     * This method is used to determine whether a given Student is already enrolled in a particular Section - that is,
     * whether that Student's attends collection is already referring to the Section in question - by taking advantage
     * of the ArrayList classes contains method.
     */
    public boolean isEnrolledIn(Section s){
        if(attends.contains(s))
            return true;
        else
            return false;
    }

    /**
     * Condition 1 : If a student is attempting to enroll in Math 101 Section1 , we want to reject his his request if
     * he is already enrolled in Math 101 Section2.
     * Determine whether a student is already enrolled in ANY Section of this same Course.
     */
    public boolean isCurrentlyEnrolledInSimilar(Section s1){
        boolean foundMatch = false;

        Course c1 = s1.getRepresentedCourse();

        for(Section s2 : attends){
            Course c2 = s2.getRepresentedCourse();
            if(c1 == c2){ // equality check to make sure if c1 and c2 are referring to the same object.
                if(s2.getGrade(this) == null){
                    // No grade was assigned! This means that the Student is currently
                    // enrolled in a Section of this same Course.
                    foundMatch = true;
                    break;
                }
            }
        }
        return foundMatch;
    }

    /**
     * We are returning what us actually an ArrayList reference to a generic collection reference.
     *
     */
    public Collection<Section> getEnrolledSections(){
        return attends;
    }

    /**
     * Example of delegation. We use the Students getTranscript method to retrieve handle on the Transcript object
     * that belongs to this Student, and then invoke the display method for that Transcript object.
     */
    public void printTranscript(){
        this.getTranscript().display();
    }
}
