/**
 * obejects of Grade class represents a grade report
 * group is an array with the grading category between 1 to 21
 * weight is an array with the percentage each grading category weighs
 * total weight adds to 1
 * the grade is a ragged array with rows equals the size of group
 * Each grade is available if user enters grade between 0 to top score
 * unavailable if enter "n/a"
 * the columns are user defined
 * unavailable is an array with the same size as group, it keeps track of number of "n/a" scores
 * top score is an array with highest score a student can get between 0 to 100
 * the output grade shows individual score on each item
 * unweighted score,weighted score, raw score, and total in percentage
 * 
 * @Felix Chen
 * @credits to StackOverflow algorithm to distinguish double from string input
 * @credits to java2blog algorithm to formate double to percentage
 * @5/30/2021 
 */
import java.util.Scanner;
import java.util.regex.*;
import java.text.DecimalFormat;
public class Grade
{
    /* SubGrade is an array which tracks the sum of each grading category
    SubTotal is an array which tracks the sum of the total possible grade of each category
    unweightedScore is an array which represents the student's grade in each category without counting the weight
    weightedScore multiplies each unweighted score with the corresponding weight,
    the total is the sum of the weightedScore,
    the total score ranges from 0 to 1, 'n/a' if no scores available*/
    private double[][] Grade;
    private boolean[][] isAvailable;
    private double[] Weight;
    private String[] Group;
    private double[] TopScore;
    private int[] Unavailable;
    private final int MaxGroupNum = 21;
    private double Total;
    
    private double[] UnweightedScore;
    private double[] WeightedScore;
    private double[] SubGrade;
    private double[] SubTotal;
    /**
     * Constructor of class Grade
     * initialize instance variable     
     */
    public Grade()
    {
        this.Total = 0;        
    }
    
    //reference link: https://stackoverflow.com/questions/35029367/how-to-loop-ask-if-a-number-is-an-integer
    //returns true when str is an integer
    public boolean isInt(String str)
    {
        try
        {
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
    }
    //checks if a str is an double
    public boolean isDouble(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
    }    
    //round a double num to two decimal places
    public double Round(double num)
    {
        DecimalFormat dec = new DecimalFormat("#0.00");
        num = Double.parseDouble(dec.format(num));
        return num;
    }
    
    /**
     * create a grade report
     * initialize group by user input
     * initialize the ragged array grade, whose rows has the same size as group
     * initialize the ragged array isAvailable which has the same size as grade
     * initialize topscore which has the same size as group
     * initialize weight which has the same size as grade
     */
    public void CreateGradeReport()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("please enter number of groups: ");
        //initialize grade, group,isAvailable, weight, topscore
        while(true)
        {
            String temp = input.nextLine();
            if (isInt(temp) && temp != null)
            {
                int num = Integer.parseInt(temp);
                if (num>0 && num<MaxGroupNum)
                {
                    this.Group = new String[num]; 
                    this.Weight = new double[num];
                    this.Grade = new double[num][]; 
                    this.TopScore = new double[num];
                    this.isAvailable = new boolean[num][];
                    break;
                }
                else
                   System.out.println("error, number of groups are between 1 to 20, please try again."); 
            }
            else
                System.out.println("Invalid input, please try again"); 
        }
        int length = this.Group.length;
        //fill Group
        //use Regular expression to verify group format
        for (int i=0; i<length;i++)
        {
            boolean valid = false, valid2 = false, valid3 = false;
            int j = i + 1;
            System.out.println("please enter name of group " + j);
            while(true)
            {
                String temp = input.nextLine();
                valid = temp.trim().contains(" ");
                valid2 = Pattern.matches("[a-zA-Z]{1,50}",temp.replaceAll("\\s+", ""));
                valid3 = Pattern.matches("[a-zA-Z]{1,50}",temp);
                if((valid && valid2) || valid3)
                {
                    this.Group[i] = temp.replaceAll("\\s+", " ").trim();                    
                    break;
                }
                else
                {
                    System.out.println("error, Group Name must contain letters a-z or A-Z" +
                    "\ngroup name length must be between 1 to 50,\n"+
                    "group name are sperated by spaces if it contains more than two words,\n"+
                    "cannot be empty or only whitespace, please try again");
                }
            }            
        }
        //fill Weight
        double sum = 0.0, high=1.0;
        for(int i=0; i<length;i++)
        {
            double tempSum = 0.0;boolean valid = false;            
            while (!valid)
            {
                System.out.println("please enter "+Group[i] +" weight: ");                                  
                String temp = input.nextLine();
                if (isDouble(temp) && temp != null)
                {
                    double weight = Double.parseDouble(temp);
                    weight = Round(weight); high = Round(high);
                    if (i == length - 1)
                    {                        
                        if (weight == high)
                        {
                            this.Weight[i] = weight;
                            valid = true;
                        }
                        else
                        {
                            System.out.println("error, "+ this.Group[i]+" weight must equal " + high + " please try again");
                            valid = false;
                        }
                    }                      
                    else if(weight > 0.0 && weight < high && sum < 1.0)
                    {                            
                        this.Weight[i] = weight;
                        sum = tempSum + this.Weight[i];
                        high -= this.Weight[i];
                        valid = true;
                    }
                    else if(sum > 1.0)
                    {
                        System.out.println("error, sum of "+ this.Group[i] + "exceeds 1, please try again");
                        valid = false;
                    }
                    else
                    {
                        System.out.println("error,"+ this.Group[i]+" weight must be greater than 0 and smaller than " + high + 
                        " please try again");
                        valid = false;
                    }
                }
                else
                {
                    System.out.println("Invalid input, please try again");
                    valid = false;
                }                   
            }            
        }
        //fill topscore
        for (int i=0; i<length;i++)
        {
            while(true)
            {
                System.out.println("please enter "+ this.Group[i] + " topscore: ");
                String temp = input.nextLine();
                if (isDouble(temp) && temp != null)
                {                    
                    double weight = Double.parseDouble(temp);   
                    if (weight > 1.0 && weight <= 100.0)
                    {
                        this.TopScore[i] = Round(weight);
                        break;
                    } 
                    else
                        System.out.println("error,"+ this.Group[i]+" topscore must be greater than 1 and smaller " 
                        + " than or equal than 100, please try again");
                }
                else
                    System.out.println("Invalid input, please try again");
            }
        }        
        //initialize ragged array Grade        
        for (int i=0; i<length;i++)
        {                
            while(true)
            {
                System.out.println("please enter number of "+ Group[i]);
                String temp = input.nextLine();                 
                if (isInt(temp) && temp != null)
                {
                    int num = Integer.parseInt(temp);
                    if (num > 0 && num < MaxGroupNum)
                    {
                        this.Grade[i]=new double [num];
                        break;                        
                    }
                    else                    
                        System.out.println("error, number of " + this.Group[i] + " is between 1 to 20, please try again.");
                }
                else
                    System.out.println("Invalid input, please try again");                    
            }
        }
        //input.close();        
        System.out.println("creating report...");        
    }
    
    /**
     * fill a grade report
     * verify the grade
     * the loop keeps running until entering the valid grade
     */
    public void fillGradeReport()
    {
        Scanner input = new Scanner(System.in);
        int length = this.Group.length;
        //initialize isAvailable
        for (int i=0; i<length;i++)
        {
            this.isAvailable[i] = new boolean[this.Grade[i].length];            
        }       
        //fill Grade and is.Available
        for (int i = 0; i < this.Grade.length;i++)
        {
            int k = 0;
            for (int j = 0; j < this.Grade[i].length; j++)
            {
                k = j+1;
                while(true)
                {
                    System.out.println("please enter grade for " + this.Group[i] + " " + k
                    +" \nEnter n/a if " + this.Group[i] +" " + k +" is unavailable");                    
                    if (input.hasNextDouble())
                    {                    
                        double grade = input.nextDouble();   
                        if (grade >= 0.0 && grade <= this.TopScore[i])
                        {
                            this.Grade[i][j] = grade;
                            this.isAvailable[i][j] = true;
                            break;
                        } 
                        else
                            System.out.println("error,"+ this.Group[i]+" grade must be greater or equal than 0 and smaller or equal than "
                            +this.TopScore[i]+", please try again");
                    }
                    else
                    {
                        String NotAvailable = input.nextLine();
                        if  (NotAvailable.equals("n/a"))
                            {
                                this.isAvailable[i][j] = false;
                                //System.out.println(Group[i]+" "+k+" grade: "+Grade[i][j]);
                                //System.out.println(Group[i]+" "+k+" availability: " + isAvailable[i][j]);
                                break;
                            }
                            else
                                System.out.println("invalid input,please try again");                        
                    }
                }
            }
        }
        System.out.println("Generating Report...");
        //input.close();
    }

    /**
     * calculate grade according to user inputs
     *
     * @a grade report is filled
     * @calculate the grade of each category
     * @calculate total grade
     * @calculate weighted score, and unweighted score
     */
    public void CalculateGrade()
    {
        System.out.println("One moment, calculating Grade...");
        double currentTotal = 1.0; 
        int length = this.Grade.length; 
        this.SubGrade = new double[length];
        this.SubTotal = new double[length];
        this.UnweightedScore = new double[length];
        this.WeightedScore = new double[length];
        this.Unavailable = new int[length];
        //calculate the number of unavailable for each grading category
        //max number of unavailable grade equals corresponding column length, min is 0, i.e. no 'n/a' entered  
        //a grade is unvailable if entered 'n/a'
        for (int i = 0;i < length;i++)
        {
            this.Unavailable[i] = FindUnavailableScore(this.isAvailable[i]);            
        }
        //calculate subTotal of each group if available
        //subtotal = topscore * (column length - number of 'n/a' grades)
        for (int i = 0;i < length;i++)
        {
            if (this.Unavailable[i] < this.Grade[i].length)                                               
                this.SubTotal[i] = this.TopScore[i] * (this.Grade[i].length - this.Unavailable[i]);
        }
        //calculate unweighted score of each group        
        //unweighted score = total student grade / subtotal if available
        for (int i = 0;i < length;i++)
        {            
            if (this.Unavailable[i] < this.Grade[i].length)
            {
                for(int j = 0;j < this.Grade[i].length;j++)             
                    this.SubGrade[i] += this.Grade[i][j];
            }                            
            
            if (this.SubTotal[i] != 0.0)            
                this.UnweightedScore[i] = this.SubGrade[i] / this.SubTotal[i];
        }        
        //calculate weighted score,
        //weighted score = weight * unweighted score if available
        //calculate total 
        //total = sum of weighted score if available
        //reset Total Score for the next grade report
        this.Total = 0.0;
        for (int i = 0;i < length;i++)               
        {   
            if (this.Unavailable[i] == this.Grade[i].length)
                currentTotal -= this.Weight[i];
            else    
                this.WeightedScore[i] = this.Weight[i] * this.UnweightedScore[i];         
            //total is calculated only when weithed score is not all zero, otherwise total is 0 
            if (!isAllZero(this.WeightedScore))
                this.Total += this.WeightedScore[i];             
        }
        //total score = total score / (1 - 'n/a' scores) e.g. if only final is 'n/a' then 1 - .25 = .75
        if (!isAllZero(this.WeightedScore))
            this.Total /= currentTotal;
    } 
    
    /**
     * checks if an array entries are all zero
     *
     * @a filled double array
     * @return true if all entries are zero, otherwise return false
     */
    public boolean isAllZero(double[] arry)
    {
        for (double element : arry)
        {
           if (element != 0) 
                return false;            
        }
        return true; 
    }
    
    /**
     * calculate the number of unavailable scores the boolean array has
     * 0 means alll score is available 
     * if result = the size of arry, the arry is unavailable
     * returns the number of unavaible score
     */
    public int FindUnavailableScore(boolean[] arry)
    {
        int result = 0;
        for (boolean element : arry)
        {
           if (element == false) 
                result += 1;            
        }
        return result; 
    }
    
    /**
     * check if a 2-D double array is all unavailable  
     *
     * @ a filled 2-D double array
     * @return true if the entire array is false, otherwise return false
     */
    public boolean isDoubleArraryAllUnavailable(boolean[][] arry)
    {
        for (boolean element[] : arry)
        {
            for (boolean a : element)
            {
                if (a == true) 
                    return false;
            }
        }
        return true; 
    }
    
    /**
     * a standard toString method to convert variables to String
     *
     * @initialized variables
     * @return String representation of the varibles
     */
    public String toString()
    {
        String retString = "";
        int length = this.Grade.length;
        //weight report
        retString += "Group\t  " + "Weight\n";
        for (int i = 0;i < length;i++)
        {
            retString += this.Group[i] + "\t " + String.format("%.2f%%%n",this.Weight[i]*100.0);
        }
        retString += "\n\nYour Grades:\n";
        //student's individual grade
        for (int i = 0;i < length;i++)
        {
            retString += this.Group[i] + "\t";
            for(int j = 0;j < this.Grade[i].length;j++)
            {                
                if (!this.isAvailable[i][j])
                    retString += " n/a ";                                           
                //reference link:https://java2blog.com/format-double-to-2-decimal-places-java/
                //format output in 2 decimal places percentage 
                else
                    retString += String.format(" %.2f",this.Grade[i][j]) + "/" + String.format("%.2f ",this.TopScore[i]);                    
            }
            retString += "\n";
        }
        retString +="\n\nunweighted score(%):\n";
        for (int i = 0;i < length;i++)
        {
                
            retString +=this.Group[i] + "\t";
        }
        retString += "\n";
        retString += "---------------------------------------------------------------------------------------------------------\n";
        //concatenate unweighted score to retString
        for (int i = 0;i < length;i++)
        {
            if (FindUnavailableScore(this.isAvailable[i]) == this.Grade[i].length)                    
                retString += "n/a\t";
            else
            {   
                retString += String.format("%.2f%%\t",this.UnweightedScore[i] * 100.0);
            }
        }
        retString += "\n\n\nweighted score(%):\n";
        for (int i = 0;i < length;i++)
        {   
            retString +=this.Group[i] + "\t";
        }
        retString += "Total\n";
        retString += "---------------------------------------------------------------------------------------------------------\n";
        //concatenate weighted score to retString
        for (int i = 0;i < length;i++)
        {
            if (FindUnavailableScore(this.isAvailable[i]) == this.Grade[i].length)                    
                retString += "n/a\t";           
            else
            {   
                retString += String.format("%.2f%%\t",this.WeightedScore[i] * 100.0);
            }
        }        
        //concatenate total score to retString
        if (isDoubleArraryAllUnavailable(this.isAvailable))        
            retString += "   n/a    ";
        else
            retString += String.format("  %.2f%%   ",this.Total * 100.0);
        retString += "\n\n\nRaw score:\n";
        retString += "---------------------------------------------------------------------------------------------------------\n"; 
        //concatenate raw score to retString
        for (int i = 0;i < length;i++)
        {
            retString += this.Group[i] + "\n";
            if (FindUnavailableScore(this.isAvailable[i]) == this.Grade[i].length)
                retString += "n/a\n";
            else
            {
                retString += String.format("%.2f ",this.SubGrade[i]) + "\n / \n" + String.format("%.2f ",this.SubTotal[i]) + 
                "\n" + String.format("%.2f%% ",this.UnweightedScore[i]*100.0) + "\n";
            }   
            retString +=  "\n";
        }        
        return retString;
    }
}
