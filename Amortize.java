/****************************************************************************************
 * Author:  Matthew                                                                     *
 * Date:  25 Feb 18                                                                     *
 *                                                                                      *
 * Purpose:  This application is an amortization calculator used to determine monthly   *
 *           payments based on the loan amount, period, and interest rate.  It is       *
 *           intuitive in that it takes into account the day of the week (weekend or    *
 *           holiday) your monthly payment is scheduled to come out and applies the     *
 *           correct interest calculation.                                              *
 ***************************************************************************************/
import java.util.*;

public class Amortize {
    // CONSTANTS
    final static int YR = 365;  // Standard year                                // short might be the better option due to memory consumption
    final static int LP = 366;  // Leap year
    // VARIABLES
    Scanner kb = new Scanner(System.in);
    String debt;
    double amount = 0.00;
    int term = 0;
    double interest = 0.00;
    double payment = 0.00;
    int dueDate = 0;

    // CONSTRUCTOR
    Amortize() {
        //User_Input();
        Show_Header();
    }

    // USER INPUT MODULE
    private void User_Input () {
        System.out.print("Enter the debt label: ");
        debt = kb.next();
        System.out.print("Enter the loan amount: ");
        amount = kb.nextDouble();
        System.out.print("Enter the term in months: ");
        term = kb.nextInt();
        System.out.print("Enter the interest rate: ");
        interest = kb.nextDouble();
        System.out.print("Enter monthly payment: ");
        payment = kb.nextDouble();
        System.out.print("Enter the due date: ");
        dueDate = kb.nextInt();
    }

    // DISPLAYS AMORTIZATION TABLE AND KEY DETAILS
    private void Show_Header () {
        System.out.println("\nAmortization Schedule for " + debt);
        for (int i = 0; i < 68; i++) {System.out.print("-");}
        // \n = "enter"; %s = string; %f = float; %d = integer; %-8s = string 8 characters wide left justified (-)
        System.out.format("\n%-8s%-13s%-13s%-13s%-13s%-13s",
                " ","OLD","MONTHLY","INTEREST","PRINCIPAL","NEW","BALANCE");
        System.out.format("\n%-8s%-13s%-13s%-13s%-13s%-13s\n\n",
                "MONTH","BALANCE","PAYMENT","PAID","PAID","BALANCE");
    }

    // DISPLAYS THE CALCULATIONS FOR EACH COLUMN ROW BY ROW
    private void Show_Calculations () {

    }

    // CALCULATES THE PRINCIPAL PAYMENT
    private double Calculate_Principal (double pay, double i) {
        double p = 0.00;
        return p = pay-i;
    }

    // CALCULATES THE INTEREST PAYMENT
    private double Calculate_Interest (double bb, double ir, double d) {
        double i = 0.00;
        return i=((bb*(ir/YR))*d);  // formula will change based on standard or leap years
    }

    // CALCULATES THE CUMULATIVE PRINCIPAL PAID OVER TIME
    private double Calculate_Cumulative_Principal (double pcp, double p) {
        double cp = 0.00;
        return cp=pcp+p;
    }

    // CALCULATES THE CUMULATIVE INTEREST PAID OVER TIME
    private double Calculate_Cumulative_Interest (double pci, double i) {
        double ci = 0.00;
        return ci=pci+i;
    }

    // CALCULATES THE ENDING BALANCE
    private double Calculate_Ending_Balance (double bb, double p) {
        double eb = 0.00;
        return eb=bb-p;
    }

    // CALCULATES THE TOTAL COST TO DATE
    private double Calculate_Total_Cost (double cp, double ci) {
        double tc = 0.00;
        return tc=cp+ci;
    }

    // MAIN METHOD
    public static void main(String[] args) {
        Amortize a = new Amortize();
    }
}
