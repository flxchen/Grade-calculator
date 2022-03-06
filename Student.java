/**
 * objects of Student represents students.
 * each student has studentID, student name, and a Student Grade of class grade.
 * @by Felix Chen
 * @credits to CSSKIL142 instructor: Tim Lum, 
 * suggests the toString method
 * adds grade object in student class
 * @credits to JavaPoint 
 * demenstrates Java regular expression uses 
 */
import java.util.Scanner;
import java.util.regex.*;
public class Student
{
    // StudentGrade represents a grade of a student, details in Grade class 
    private String StudentID;
    private String StudentName;
    private Grade StudentGrade;
    /**
     * Constructor for objects of class Student
     */
    public Student()
    {
        // initialise instance variables
        // create a StudentGrade
        this.StudentID = "";
        this.StudentName = "";
        this.StudentGrade = new Grade();
    }
    
    /**
     * read inputs from the user
     * validate the input
     * @ StudentID, StudentName are String
     * @ the loop keeps running until user enters valid input
     */
    public void CreateStudent()
    {
       while(true)
        {
            readInput();
            if (validateInput())
                break;
        } 
    }
    
    /**
     * read inputs from the user
     * 
     * @StudentID, StudentName are String
     * @use Scanner to read the inputs
     */
    public void readInput()
    {
        // put your code here        
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter student ID:");
        this.StudentID = input.nextLine();
        System.out.println("Please enter student Name:");
        this.StudentName = input.nextLine();
        //input.close();
    }
    
    /**
     * validate user inputs
     * StudentID must be six digits integers 0-9
     * StudentName must be letters a-z or A-Z first name space last name, 
     * length is 1 through 15
     * use regular expression to match specified pattern
     * user input StudentID and StudentName
     * 
     * @return true if both inputs comply with the rules
     * @false otherwise
     * @include reference link
     */
    public Boolean validateInput()
    {   
        /*reference link: https://www.javatpoint.com/java-regex#:~:text=Java%20Regex.%20The%20Java%20Regex%20or%20Regular%20Expression,
        on%20strings%20such%20as%20password%20and%20email%20validation.*/
        //use Pattern, match of Regex class to check inputs
        boolean validID = Pattern.matches("\\d{6}", this.StudentID);       
        boolean validName = Pattern.matches("[a-zA-Z]{1,15}[ ][a-zA-Z]{1,15}", this.StudentName);         
        if (validID & validName)
            return true;         
        else
        {
            System.out.println("student ID must be SIX digits integers: 0-9");
            System.out.print("student name start with first name followed by a space, then last name\n"+
            "name must be letters: a-z,A-Z\nfirst and last name length must be between 1 to 15\n");
            return false;
        }         
    }   
    
    /**
     * Getter to return student ID
     *
     * @user input ID
     * @return ID
     */
    public String getID()
    {
        return this.StudentID;
    }
    
    /**
     * Getter to return student name
     *
     * @user input name
     * @return name
     */
    public String getName()
    {
        return this.StudentName;
    }
    
    /**
     * print student grade
     *
     * @user input grade
     * @print report on screen
     */
    public void printReport()
    {
        System.out.println("\n\n\nprinting report...\n" + toString());
    }
    
    /**
     * initilize a StudentGrade by calling CreateGradeReport form Grade
     * the results are valid input
     * @call Grade StudentGrade method
     * @initilize a StudentGrade representing student's grades
     */
    public void CreateGradeReport()
    {
        this.StudentGrade.CreateGradeReport();
    }
    
    /**
     * fill a StudentGrade 
     * calculate the grade
     * @call Grade fillGradeReport method
     * @call Grade CalculateGrade method
     */
    public void CalculateGradeReport()
    {        
        this.StudentGrade.fillGradeReport();
        this.StudentGrade.CalculateGrade();
    }
    
    /**
     * Standard toString method 
     * 
     * @convert all variables to String 
     * @return String representations of variables
     */
    public String toString()
    {
        String retString= "";
        retString += "\n\nNote: the report is based on graded assignment, to find out final grade," + 
        "refill the report with expectating score.";
        retString += "\nID: "  + this.StudentID + " name: " + this.StudentName +"'s Grade " + "\n";
        retString += "-------------------------------------------------------\n";        
        retString += this.StudentGrade.toString() +"\n";
        return retString;
    }
}
