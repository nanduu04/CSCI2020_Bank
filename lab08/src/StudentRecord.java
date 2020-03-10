import java.io.Serializable;

public class StudentRecord {
    protected String StudentID;
    protected Float Midterm;
    protected Float Assignments;
    protected Float FinalExam;
    protected Float FinalGrade;
    protected Character LetterGrade;

    public StudentRecord(String studentID, Float assignments, Float midterm, Float finalExam) {
        StudentID = studentID;
        Midterm = midterm;
        Assignments = assignments;
        FinalExam = finalExam;
    }

    public StudentRecord(String sidText, String assignmentsText, String midtermText, String examText) {
        StudentID = sidText;
        Assignments = Float.valueOf(assignmentsText);
        Midterm = Float.valueOf(midtermText);
        FinalExam = Float.valueOf(examText);
    }

    public String getStudentID() {
        return StudentID;
    }

    public Float getMidterm() {
        return Midterm;
    }

    public Float getAssignments() {
        return Assignments;
    }

    public Float getFinalExam() {
        return FinalExam;
    }

    public Float getFinalGrade() {
        return ((30 * getMidterm()) + (20 * getAssignments()) + (50 * getFinalExam())) / (100);
    }

    public Serializable getLetterGrade() {
        Character tempLetterGrade = ' ';

        if (getFinalGrade() >= 80 && getFinalGrade() <= 100) {
            tempLetterGrade = 'A';
        } else if (getFinalGrade() >= 70 && getFinalGrade() <= 79) {
            tempLetterGrade = 'B';
        } else if (getFinalGrade() >= 60 && getFinalGrade() <= 69) {
            tempLetterGrade = 'C';
        } else if (getFinalGrade() >= 50 && getFinalGrade() <= 59) {
            tempLetterGrade = 'D';
        } else if (getFinalGrade() >= 0 && getFinalGrade() <= 49) {
            tempLetterGrade = 'F';
        } else {
            return null;
        }

        return tempLetterGrade;
    }

    @Override
    public String toString() {
        return "StudentRecord{" +
                "StudentID=" + StudentID +
                ", Midterm=" + Midterm +
                ", Assignments=" + Assignments +
                ", FinalExam=" + FinalExam +
                ", FinalGrade=" + FinalGrade +
                ", LetterGrade='" + LetterGrade + '\'' +
                '}';
    }
}
