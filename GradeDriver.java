/**
 * A driver for the Grade, Student class
 * create a student object
 * Prompts user with 4 options
 * 1.create a new report for a different class
 * 2.fill the existing report with new grade
 * 3.Enter a new student and a new report
 * 4.End
 * 2,3 are not available on the first run
 * read inputs from users until all inputs are valid
 * create a grade for the student
 * print the grade to screen
 * reenter grade until user chooses to exit
 * 
 * @Felix Chen
 * @credits to Canvas CSS142 grade page inspires the project
 * 5/30/2021
 */
import java.util.Scanner;
import java.util.InputMismatchException;
public class GradeDriver
{    
    /**
     * main method
     */
    public static void main(String[] args)
    {
        // initialise instance variables, times to keeps track of number of times the loops run, 
        //0 means the first time. 2 and 3 are not available on first run        
        int option = 0, times = 0; boolean valid = false;
        Scanner input = new Scanner(System.in);
        System.out.println("Hello, welcome to the grade program");
        Student student = new Student();
        student.CreateStudent();
        //read valid inputs
        while (true)
        {   
            System.out.println("Please pick an option: \n1.Create a new report for a different class\n"+
	        "2.Fill the report with different grade\n3.Enter a new student\n4.end\n" 
	        + "note:2 and 3 are not eligible for the first time user");
	    //try get integer input from user, throws InputMismatchException if not enter a integer     
    	    try
    	    {
    	        if (input.hasNextInt())
    	        {                    
                    option = Integer.parseInt(input.nextLine());    	            
    	            valid = true;
    	        }
    	        else
    	        {
                    valid = false;
    	            input.nextLine();    	            
    	            throw new InputMismatchException("not a integer");
    	        }
    	    }
    	    //catch InputMismatchException error, prints the error message to screen
    	    catch(InputMismatchException e)
    	    {
    	        System.out.println(e.getMessage()+" please try again"); 
    	        valid = false;   
    	    }
    	    //valid checks if there is InputMismatch error, the options are executed only when the input is an integer.
    	    if (valid)
    	    {   
                if (option == 1)
                {   
                    System.out.println("\nplease enter a new course for\n" + "name: " + student.getName() + " ID: " + student.getID());
                    student.CreateGradeReport();
                    student.CalculateGradeReport();
                    student.printReport();
                    times++;
                }
                else if (option == 2)
                {
                    if (times != 0)
                    {
                        System.out.println("\nplease refill the report with new grade for\n" + "name: " + student.getName() + " ID: " 
                        + student.getID());
                        student.CalculateGradeReport();
                        student.printReport();
                        times++;
                    }
                    else
                        System.out.println("error, cannot refill grade because no report has been created, please try again");
                }
                else if (option == 3)
                {
                    if (times != 0)
                    {
                        System.out.println("\nplease enter a new student");
                        student.CreateStudent();
                        student.CreateGradeReport();
                        student.CalculateGradeReport();
                        student.printReport();
                        times++;
                    }
                    else
                        System.out.println("error, cannot create a new student for the first time user, please try again");
                }
                else
                {
                    System.out.println("exiting...");
                    //input.close();
                    System.exit(0);
                }
    	    }
            //input.close();
        }        
    }    
}
