package com.SRS;

/**
 * The TranscriptEntry association class.
 * Associations :
 * earns grade, a one-to-many association with Student
 * assigns grade, a one-to-many association with Section
 * an unnamed , one-to-many aggregation with the Transcript class.
 * Transcript entry is at the end of many associations. So it only needs to maintain a single handle
 * on each type of object - no collection attributes are required.
 */
public class TranscriptEntry {

    //------------------------
    // Attributes.
    //------------------------

    private String grade;
    private Student student;
    private Section section;
    private Transcript transcript;

    /**
     * The Constructor of this class does most of the work for maintaining all of these relationships.
     *
     */
    //------------------------
    // Constructor(s).
    //------------------------
    public TranscriptEntry(Student s, String grade, Section se){
        this.setStudent(s);
        this.setSection(se);

        /**
         * Transcript object has full responsibility for maintaining the bi-directionality of the association
         * between itself and the Transcript object.
         */
        // Obtain the Student's transcript.....
        Transcript t = s.getTranscript();

        // .... and then link the Transcript and the TranscriptEntry
        // together bidirectionally.

        this.setTranscript(t);
        t.addTranscriptEntry(this);
    }

    //------------------
    // Accessor methods.
    //------------------
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }


    //-----------------------------
    // Miscellaneous other methods.
    //-----------------------------

    // These next two methods are declared to be static, so that they
    // may be used as utility methods.

    public static boolean validateGrade(String grade){
        boolean outcome = false;

        if(grade.equals("F") || grade.equals("I")){
            outcome = true;
        }

        if  (grade.startsWith("A") ||
             grade.startsWith("B") ||
             grade.startsWith("C") ||
             grade.startsWith("D")) {
            if(grade.length() == 1) {
                outcome = true;
            }
            else if(grade.length() == 2){
                if(grade.endsWith("+") ||
                        grade.endsWith("-")) {
                    outcome = true;
                }
            }
        }
        return outcome;
    }

    /**
     * This method is used to determine wether a particular grade is a passing grade.
     *
     */
    public static boolean passingGrade(String grade){

        boolean outcome = false;

        // First, make sure it is a valid grade.
        if(validateGrade(grade)){
            // Next, make sure that the grade is a D or better.
            if (grade.startsWith("A") ||
                grade.startsWith("B") ||
                grade.startsWith("C") ||
                grade.startsWith("D")) {
                    outcome = true;
            }
        }

        return outcome;
    }

}
