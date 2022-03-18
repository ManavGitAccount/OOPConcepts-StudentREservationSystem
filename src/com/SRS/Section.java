package com.SRS;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * 1. Offered as one to many association with course. One section can have many courses. Therefore there is only one handle.
 * 2. An unnamed, one to many aggregation with ScheduleOfClasses. One handle. A section has only one handle.
 * 3. teaches, a one to many association with professor. One section can be taught by many professors. Therefore one handle for reference.
 * 4. attends, a many to many association with Student.
 * 5. assigns grade, a one-to-many association with the TranscriptEntry class.
 */
public class Section {

    //--------------------
    // Attributes
    //--------------------
    private int sectionNo;
    private char dayOfWeek;
    private String timeOfDay;
    private String room;
    private int seatingCapacity;
    private Course representedCourse;
    private ScheduleOfClasses offeredIn;
    private Professor instructor;

    // The enrolledStudents HashMap stores Student object references,
    // using each Student's ssn as a String key.
    private HashMap<String, Student> enrolledStudents;

    // The assignedGrades HashMap stores TranscriptEntry object
    // references, using a reference to the Student to whom it belongs
    // as the key.
    private HashMap<Student, TranscriptEntry> assignedGrades;

    //---------------------------------
    // Constructor.(s)
    //---------------------------------

    public Section(int sNo, char day, String time, Course course,
                   String room, int capacity) {
        setSectionNo(sNo);
        setDayOfWeek(day);
        setTimeOfDay(time);
        setRepresentedCourse(course);
        setRoom(room);
        setSeatingCapacity(capacity);

        // A Professor has not yet been identified.
        setInstructor(null);

        // Note that we're instantiating empty support Collection(s).
        enrolledStudents = new HashMap<String, Student>();
        assignedGrades = new HashMap<Student, TranscriptEntry>();
    }

    //------------------
    // Accessor methods.
    //------------------
    public int getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(int sectionNo) {
        this.sectionNo = sectionNo;
    }

    public char getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(char dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public Course getRepresentedCourse() {
        return representedCourse;
    }

    public void setRepresentedCourse(Course representedCourse) {
        this.representedCourse = representedCourse;
    }

    public ScheduleOfClasses getOfferedIn() {
        return offeredIn;
    }

    public void setOfferedIn(ScheduleOfClasses offeredIn) {
        this.offeredIn = offeredIn;
    }

    public Professor getInstructor() {
        return instructor;
    }

    public void setInstructor(Professor instructor) {
        this.instructor = instructor;
    }

    //-----------------------------
    // Miscellaneous other methods.
    //-----------------------------

    public String toString() {
        return getRepresentedCourse().getCourseNo() + " - " +
                getSectionNo() + " - " +
                getDayOfWeek() + " - " +
                getTimeOfDay();
    }

    // The full section number is a concatenation of the
    // course no. and section no., separated by a hyphen;
    // e.g., "ART101 - 1".

    public String getFullSectionNo() {
        return getRepresentedCourse().getCourseNo() +
                " - " + getSectionNo();
    }


    public EnrollmentStatus enroll(Student s) {
        // First make sure that the student is not already enrolled for this Section,
        // and that he/she has NEVER taken and passed the course before.

        /**
         * A student class is associated to Transcript. Therefore all we are doing here is obtaining a handle on student
         * transcript and storing it in a locally declared reference variable called transcript
         */
        Transcript transcript = s.getTranscript();


        /** We begin by verifying that the Student 's' seeking enrollment  hasn't already been enrolled for this section,
         *  and further more that the student has never taken and successfully completed this course in the past.
         */
        if (s.isCurrentlyEnrolledInSimilar(this) ||
                transcript.verifyCompletion(this.getRepresentedCourse())) {
            return EnrollmentStatus.prevEnroll;
        }

        // If there are any prerequisites for this course,
        // check to ensure that the student has completed them

        /**
         * Like the comments above we check to see if the Student has satisfied the pre-requisites for this section, if
         * there are any. We use the sections getRepresentedCourse method to obtain a handle on the Course object that
         * this section presents. And then invoke the hasPrerequisites method on the course object. If the result returned
         * is true, then we know that there are prerequisites to be checked.
         */
        Course c = this.getRepresentedCourse();
        if (c.hasPrerequisites()) {
            /**
             * If there are prerequisites in this course we use the getPrerequisites methods defined by the Course
             * class to obtain the collection of all the prerequisite Courses and iterate through the
             * collection via a for loop.
             */
            for (Course pre : c.getPrerequisite()) {
                // See if the Student's Transcript reflects successful
                // completion of the prerequisite

                /**
                 * If the student is found to have not satisfied any of the prerequisites, then the return statement is
                 * triggered and we return the status value EnrollmentStatus.prereq
                 */
                if (!transcript.verifyCompletion(pre)) {
                    return EnrollmentStatus.prereq;
                }
            }
        }

        /** If the total enrollment is already at the capacity for this section, we reject this
         * enrollment request. We are just verifying in this condition that there is still available seating.
        */
        if (!this.confirmSeatAvailability()) {
            return EnrollmentStatus.secFull;
        }

        /** If we made it here in the code, we are ready to enroll the Student
         *  Note bidirectionality : this section holds onto the Student via the HashMap, and then
         *  the student is given a handle on this section.
        */
        enrolledStudents.put(s.getSsn(), s);

        /** (imp)To achieve bi-directionality of the link between a student and a section, we use the addSection method on
         *  the student reference object reference 's'. Passing it a handle on 'this' Section.
         */
        s.addSection(this);

        /**
         * We then return the value EnrollmentStatus.success to signal successful enrollment.
         */
        return EnrollmentStatus.success;

    }

//        public EnrollmentStatus enroll(Student s){
//
//            //First, make sure that this Student is not already
//            //enrolled for this Section, and that he/she has
//            //NEVER taken and passed the course before.
//
//            Transcript transcript = s.getTranscript();
//
//            if(s.isCurrentlyEnrolledInSimilar(this) ||
//            transcript.verifyCompletion(this.getRepresentedCourse())){
//                return EnrollmentStatus.prevEnroll;
//            }
//
//            //If there are any prerequisites for this course,
//            //check to ensure that the Student has completed them.
//
//            Course c = this.getRepresentedCourse();
//            if(c.hasPrerequisites()){
//                for(Course pre: c.getPrerequisite()){
//                    //See if the Student's Transcript reflects
//                    //successful completion of the prerequisite.
//                    if(!transcript.verifyCompletion(pre)){
//                        return EnrollmentStatus.prereq;
//                    }
//                }
//
//                // If the total enrollment is already at the
//                // capacity for this Section, we reject this
//                // enrollment request.
//                if(!this.confirmSeatAvailability()){
//                    return EnrollmentStatus.secFull;
//                }
//
//                enrolledStatus.put(s.getSsn(), s);
//                s.addSection(this);
//                return EnrollmentStatus.success;
//            }
//        }

    /**
     * The drop method of Section performs the reverse operation of enroll. We start by verifying that the student
     * is indeed enrolled for that Section, since we can't drop a student who isn't enrolled in the first place.
     * The first if clause is just for that.
     *
     */
    public boolean drop(Student s) {

        // We may only drop a student if/he she is enrolled.

        if (!s.isEnrolledIn(this)) return false;

        /**
         * If the student is truly enrolled, we use the HashMap class's remove method to locate and delete the Student
         * reference, via it's ssn attribute value.
         */
        else {
            // Find the student in our HashMap, and remove it.
            enrolledStudents.remove(s.getSsn());

            // Note bi-directionality.
            /**
             * In the interest of bi-directionality, we invoke the dropSection method on the Student, as well, to get
             * rid of the handles at both ends of the link.
             */
            s.dropSection(this);
            return true;
        }
    }

    /**
     * The post grade method is used to assign a grade to a Student by creating a TranscriptEntry object to link this
     * Section to the Student being assigned a grade.
     * We begin by validating that the proposed grade to be assigned to Student s is performed by a static utility method,
     * validateGrade, that is defined by the TranscriptEntry class. The business rules that governs what is a valid grade
     * are posted inside the method.
     * The important thing here is that if the validateGrade method rejects the proposed grade by returning a value of false
     * we in turn exit the postGrade method returning a value of false to client code.
     */
    public boolean postGrade(Student s, String grade){
        // First validate that the grade is properly formed by calling
        // a utility method provided by the TranscriptEntry class.

        if(!TranscriptEntry.validateGrade(grade)){
            return false;
        }

        /** Make sure that we haven't previously assigned a grade to this Student by looking in the HashMap
        * for an entry using this Student as the key.If we discover that a grade has already been assigned,
        * we return a value of false to indicate that we are at risk of overwriting an existing grade.
         * (A different method, eraseGrade(), can then be written to allow a Professor to change his/her mind.)
        */

        if(assignedGrades.get(s) != null) {
            return false;
        }


        /** Assuming that the grade was not previously assigned, we invoke the appropriate constructor to create a new
         * transcript entity object.
         * This object will maintain handles on both the Student to whom a grade has been assigned and on the Section
         * for which the grade was assigned.
         * First, we create a new TranscriptEntry object.  Note that we are passing in a reference to THIS Section,
         * because we want the TranscriptEntry object, as an association class ..., to maintain "handles" on the
         * Section as well as on the Student. (We'll let the TranscriptEntry constructor take care of
         * linking this T.E. to the correct Transcript.)
         */

        TranscriptEntry te = new TranscriptEntry(s, grade, this);

        /** We also store a handle on the TranscriptEntry object in the Sections assignGrades HashMap for this purpose.
         * Then, we "remember" this grade because we wish for the connection between a T.E. and a Section to be
            bidirectional.
         */

        assignedGrades.put(s, te);

        return true;

    }

    /**
     * The getGrade method uses the Student reference passed in as an argument to this method as a lookup key for the
     * assignedGrade HashMap, to retrieve the TranscriptEntry stored therein for the student.
     * @return
     */
    public String getGrade(Student s){

        String grade = null;

        // Retrieve the associated TranscriptEntry object for this specific student  from the assignedGrade
        // HashMap, if one exists, and in turn retrieve its assigned grade.

        TranscriptEntry te = assignedGrades.get(s);

        /**
         * If a transcript entry is found, we use its getGrade method to retrieve the actual data so that it may be returned
         * by this method
         */
        if( te != null) {
            grade = te.getGrade();
        }

        // If we found no TranscriptEntry for this Student, a null value will be returned to signal this.
        return grade;

    }

    /**
     * Internal private housekeeping method. By declaring private visibility we restrict it's use so that only other
     * methods of the section class may invoke it.
     */
    private boolean confirmSeatAvailability(){
        if (enrolledStudents.size() < this.getSeatingCapacity()){
            return true;
        }

        else{
            return false;
        }
    }

    public void display() {
        System.out.println("Section Information: ");
        System.out.println("\tSemester:  " + getOfferedIn().getSemester());
        System.out.println("\tCourse No.:  " + getRepresentedCourse().getCourseNo());
        System.out.println("\tSection No:  " + getSectionNo());
        System.out.println("\tOffered:  " + getDayOfWeek() + 
                " at " + getTimeOfDay());
        System.out.println("\tIn Room:  " + getRoom());
        
        if(getInstructor() != null){
            System.out.println("\tProfessor:  " + getInstructor().getName());
        }
        displayStudentRoster();
    }

    private void displayStudentRoster() {
        System.out.print("\t Total of " + getTotalEnrollment() + " students enrolled");

        // How we punctuate the preceding message depends on
        // how many students are enrolled (note that we used a print() vs
        // println() call above)

        if(getTotalEnrollment() == 0) {
            System.out.println(".");
        }
        else{
            System.out.println(", as follows: ");
        }

        // Iterate through all of the values stored in the HashMap.

        for(Student s : enrolledStudents.values()){
            System.out.println("\t\t" + s.getName());
        }
    }

    public int getTotalEnrollment() {
        return enrolledStudents.size();
    }

    public boolean isSectionOf(Course c) {
        if (c == representedCourse) return true;
        else return false;
    }
}

