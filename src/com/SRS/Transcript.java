package com.SRS;

import java.util.ArrayList;

/**
 * Class participates in two relationships.
 * maintains, a one to one association with Student
 * An unnamed, one to many aggregation with TranscriptEntry.
 */
public class Transcript {

    //------------
    // Attributes.
    //------------
    private ArrayList<TranscriptEntry> transcriptEntries;
    private Student studentOwner;

    //----------------
    // Constructor(s).
    //----------------

    public Transcript(Student s) {
        setStudentOwner(s);

        // Note that we're instantiating empty support Collection(s).

        transcriptEntries = new ArrayList<TranscriptEntry>();
    }

    //------------------
    // Accessor methods.
    //------------------
    public Student getStudentOwner() {
        return studentOwner;
    }

    public void setStudentOwner(Student studentOwner) {
        this.studentOwner = studentOwner;
    }
    public ArrayList<TranscriptEntry> getTranscriptEntries() {
        return transcriptEntries;
    }

    public void setTranscriptEntries(ArrayList<TranscriptEntry> transcriptEntries) {
        this.transcriptEntries = transcriptEntries;
    }

    //-----------------------------
    // Miscellaneous other methods.
    //-----------------------------

    /**
     * This method is used to determine whether or not the transcript contains evidence that a particular course
     * requirement has been satisfied. This method steps through the ArrayList of TranscriptEntries maintained by the
     * Transcript object . For each transcript entry it maintains a handle on the Section object represented by the entry and
     * then invokes the isSectionOf method on that object to determine whether or not that Section represents the Course
     * of interest.
     */
    public boolean verifyCompletion(Course c){
        boolean outcome = false;

        // Step through all TranscriptEntries, looking for one
        // which reflects a Section of the Course of interest.
        for(TranscriptEntry te : transcriptEntries){
            Section s = te.getSection();

            if (s.isSectionOf(c)) {
                // Ensure that the grade was high enough.
                if(TranscriptEntry.passingGrade(te.getGrade())){
                    outcome = true;

                    // We've found one, so we can afford to terminate the loop now.
                    break;
                }
            }
        }
        return outcome;
    }

    public void addTranscriptEntry(TranscriptEntry te){
        transcriptEntries.add(te);
    }

    public void display() {
        System.out.println("Transcript for:  " +
                getStudentOwner().toString());

        if(transcriptEntries.size() == 0) {
            System.out.println("\t (no entries)");
        }
        else for(TranscriptEntry te : transcriptEntries){
            Section sec = te.getSection();

            Course c = sec.getRepresentedCourse();

            ScheduleOfClasses soc = sec.getOfferedIn();

            System.out.println("\t Semester:    " +
                    soc.getSemester());
            System.out.println("\t Course No.:  " +
                    c.getCourseNo());
            System.out.println("\t Credits:     " +
                    c.getCredits());
            System.out.println("\t Grade Received:  " +
                    te.getGrade());
            System.out.println("\t---------------------");
        }

    }
}
