import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class RowCol
{
    private int row;                                                            // private variable to hold the row value
    private int col;                                                            // private variable to hold the column value
    // CONSTRUCTORS
    public RowCol()
    {
        this(0,0);                                                              // calls RowCol(int r, int c) and sets r and c to 0
    }
    public RowCol(int r, int c)
    {
        Set_Row(r);                                                             // method call for Set_Row() and sets the row to value of r
        Set_Col(c);                                                             // method call for Set_Col() and sets the column to value of c
    }

    // GET METHODS
    public int Get_Row()
    {
        return row;                                                             // method to return the value stored in row
    }
    public int Get_Col()
    {
        return col;                                                             // method to return the value stored in col
    }
    // SET METHODS
    public void Set_Row(int r)
    {
        row = r;                                                                // sets the variable row to value of r
    }
    public void Set_Col(int c)
    {
        col = c;                                                                // sets the variable col to value of c
    }
}

class Grid
{
    public static final int N = 9;                                              // constant value for the allowable numbers
    public static final int UNASSIGNED = 0;                                     // constant value for setting a square to the value of 0
    int[][] grid = new int[N][N];                                               // a 9x9 2D array for the game grid
    int k = 0;                                                                  // variable used to control the Create_Puzzle loop
    SudokuMSTest test;                                                          // creates a variable of type SudokuMSTest

    // CONSTRUCTOR
    Grid()
    {
        Fill_Grid(grid);                                                        // passes the grid to Fill_Grid() to fill it with a solvable puzzle
        test = new SudokuMSTest(grid);                                          // passes the full grid to the SudokuMSTest class

        if (Solve_Sudoku(grid) == true)                                         // if the current grid is solved
        {
            Print_Grid(grid);                                                   // display the grid
            while (k < 43)                                                      // remove 43 digits and replace them with 0's
            {
                Create_Puzzle(grid);                                            // method call to remove a digit
                k++;                                                            // increase the count by 1
            }
            Print_Grid(grid);                                                   // print the new grid
            long t1 = System.currentTimeMillis();                               // set variable to current time
            test.solve(grid);                                                   // checks the solution produced for multiple solutions
            long t2 = System.currentTimeMillis();                               // set variable to current time
            System.out.println(t2-t1);                                          // calculates and displays the length of time to check for solutions
        }
        else
        {
            System.out.println("No solution exists\n\n");                       // if the current grid is not solved, no solution exists
        }
    }

    // FILLS THE FIRST ROW WITH A COMPLETELY RANDOM SEQUENCE OF NUMBERS 1-9
    // ALLOWS FOR A DIFFERENT SOLUTION FROM SOLVE_SUDOKU() EVERY TIME
    public void Fill_Grid(int grid[][])
    {
        for (int i = 0; i < 9; i++)                                             // cycle through the top row
        {
            String rng = String.valueOf(new Random().nextInt(N) + 1);           // generate a random number 1-9
            int t = Integer.parseInt(rng);                                      // set t to the random number

            // tests to see if the random number generated is equal to any numbers in the top row
            while (t == grid[0][0] || t == grid[0][1] || t == grid[0][2] || t == grid[0][3] || t == grid[0][4]
                    || t == grid[0][5] || t == grid[0][6] || t == grid[0][7] || t == grid[0][8])
            {
                rng = String.valueOf(new Random().nextInt(N) +1);               // generate a new number if the previous number is equal to an existing number
                t = Integer.parseInt(rng);                                      // sets t equal to the new number
            }
            grid[0][i] = t;                                                     // sets the current position to the random number
        }
    }

    // SHOULD REMOVE A NUMBER AND CHECK FOR MORE THAN ONE SOLUTION
    // REPEAT PROCESS UNTIL DESIRED DIFFICULTY LEVEL HAS BEEN REACHED AND ONLY ONE SOLUTION EXISTS
    public boolean Create_Puzzle(int grid[][])
    {
        String rng1 = String.valueOf(new Random().nextInt(N));                  // generate a random number between 0 and 8
        String rng2 = String.valueOf(new Random().nextInt(N));                  // generate another random number between 0 and 8
        int i = Integer.parseInt(rng1);                                         // set i equal to the first random number
        int j = Integer.parseInt(rng2);                                         // set j equal to the second random number

        if (grid[i][j] != UNASSIGNED)                                           // if the grid[i][j] position is not 0,
        {
            grid[i][j] = UNASSIGNED;                                            // set it to 0
        }
        else
        {
            Create_Puzzle(grid);                                                // else, do it again
        }

        return false;
    }

    // SOLVES THE GAMEBOARD
    public boolean Solve_Sudoku(int grid[][])
    {
        RowCol r = new RowCol();                                                // establish r as a new RowCol type
        RowCol c = new RowCol();                                                // establish c as a new RowCol type

        if (!Find_Unassigned_Location(grid, r, c))                              // if no empty squares exist, board is solved
        {
            return true;
        }

        for (int num = 1; num <= N; num++)                                      // cycle through numbers 1 through 9
	{
            if (Is_Safe(grid, r.Get_Row(), c.Get_Col(), num))                   // can the number safely go in that position
            {
		grid[r.Get_Row()][c.Get_Col()] = num;                           // if it can, set it there

		if (Solve_Sudoku(grid))                                         // call Solve_Sudoku again
                {
                    return true;                                                // if solved, return true
                }

                grid[r.Get_Row()][c.Get_Col()] = UNASSIGNED;                    // set position equal to 0
            }
	}
        return false;                                                           // not solved
    }

    // FINDS UNASSIGNED LOCATIONS WTIHIN THE GAMEBOARD
    public boolean Find_Unassigned_Location(int grid[][], RowCol r, RowCol c)
    {
        for (int row = 0; row < N; row++)                                       // while row is less than 9
        {
            for (int col = 0; col < N; col++)                                   // while col is less than 9
            {
                if (grid[row][col] == UNASSIGNED)                               // if row, col is unassigned...
                {
                    r.Set_Row(row);                                             // set r to row
                    c.Set_Col(col);                                             // set c to col
                    return true;                                                // return true for an unassigned location
                }
            }
        }
        return false;                                                           // return false if no empty locations exist
    }

    // CHECKS TO SEE IF A NUMBER HAS BEEN USED IN A PARTICULAR ROW
    public boolean Used_In_Row(int grid[][], int row, int num)
    {
        for (int col = 0; col < N; col++)                                       // cycle through columns 0-8
	{
            if (grid[row][col] == num)                                          // if the number is found in that row
		return true;                                                    // number is found in row
	}
        return false;                                                           // number is not found in the row
    }

    // CHECKS TO SEE IF A NUMBER HAS BEEN USED IN A PARTICULAR COLUMN
    public boolean Used_In_Col(int grid[][], int col, int num)
    {
        for (int row = 0; row < N; row++)                                       // cycle through rows 0-8
	{
            if (grid[row][col] == num)                                          // if the number is found in that column
		return true;                                                    // number is found in column
	}
        return false;                                                           // number is not found in the column
    }

    // CHECKS TO SEE IF A NUMBER HAS BEEN USED IN A PARTICULAR SQUARE
    public boolean Used_In_Box(int grid[][], int boxStartRow, int boxStartCol, int num)
    {
        for (int row = 0; row < 3; row++)                                       // cycle through rows 0-8
	{
            for (int col = 0; col < 3; col++)                                   // cycles through columns 0-8
            {
		if (grid[row+boxStartRow][col+boxStartCol] == num)              // if the number is found in that square
                    return true;                                                // number is found in the square
            }
	}
        return false;                                                           // number is not found in the square
    }

    // DETERMINES IF THE POSITION IS SAFE FOR A NUMBER
    public boolean Is_Safe(int grid[][], int row, int col, int num)
    {
        return !Used_In_Row(grid, row, num) &&
		!Used_In_Col(grid, col, num) &&
		!Used_In_Box(grid, (row - (row % 3)), (col - (col % 3)), num);  // makes sure the number is safe to usein that position
    }

    // PRINTS THE GAMEBOARD
    public void Print_Grid(int grid[][])
    {
        int row, col;

	System.out.print("+---------+---------+---------+\n");
	for (row = 0; row < N; row++)
	{
            System.out.print("|");
            for (col = 0; col < N; col++)
            {
		System.out.print(" " + grid[row][col] + " ");

		if ((col + 1) % 3 == 0)
                    System.out.print("|");
            }
            System.out.print("\n");
            if ((row + 1) % 3 == 0)
		System.out.print("+---------+---------+---------+\n");
	}
    }
}

public class test
{
    public static void main(String[] args)
    {
        new Grid();
    }
}

/*  ************  SECTION DESIGNED TO CHECK IF PUZZLE DEVELOPED ABOVE IS VALID  ************  */

// CHECKS FOR MULTIPLE SOLUTIONS IN PUZZLE GENERATED PREVIOUSLY
class SudokuMSTest { // REMOVED public                                          // because it has a private nested class (Position) it has access to all Position methods

    private Board board;                                                        // creates a new instance of the Board class called board
    private final List<Board> solutions = new ArrayList<Board>();               // creates a list of type Board called solutions; holds all possible solutions

    // OVERLOADED METHOD TO SOLVE THE PUZZLE
    public static void solve(int[][] board) {                                   // overloaded method
        new SudokuMSTest(board).solve();                                        // creates new instance of SudokuMSTest passing it whatever 2D array was passed, calling the other solve method
    }

    // CONSTRUCTOR
    public SudokuMSTest(int[][] arr) {        // changed private to public      // constructor that takes a 2D array as an argument
        board = new Board(arr);                                                 // assigns board the values passed via the 2D array argument
    }

    // OVERLOADED METHOD THAT SOLVES THE PUZZLE AND PROVIDES INDICATION OF VALIDITY
    private void solve() {                                                      // overloaded method
        solve(listEmptyPositions());                                            // returns the next empty position (type Position)
        int count = solutions.size();                                           // sets count to the size of the solutions list
        if (count == 0)
            System.out.println("no solution - invalid puzzle");                 // if count is set to 0, there are no solutions
        else if (count == 1)
            System.out.println("unique solution - valid puzzle");               // if count is set to 1, it is a valid puzzle with just one solution
        else {
            System.out.println(count + " solutions - invalid puzzle");          // if there are 2 or more solutions, it's an invalid puzzle
            System.out.println("\ncommon part of all solutions:");              // identifying the common part of all solutions
            findCommonSolution();                                               // get the process started to finding the common part of the solutions
            board.show();                                                       // method to display the common part of the solutions
        }
    }

    private static class Position {                                             // because it is static, it does not have access to the members of the SudokuMSTest class
        final int x, y;                                                         // class variables of type int to hold x,y coordinates
        Position next;                                                          // class variable of type Position to hold the next position

        Position(int x, int y) {                                                // constructor
            this.x = x;                                                         // sets class variable x to whatever x value is passed in
            this.y = y;                                                         // sets class variable y to whatever y value is passed in
        }
    }

    // PROVIDES A LIST OF EMPTY POSITIONS IN GAMEBOARD
    private Position listEmptyPositions() {
        Position start = new Position(-1, -1);                                  // initialize start.x to -1 and start.y to -1
        Position p = start;                                                     // sets p.x = start.x and p.y = start.y
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (board.get(x, y) == 0) {                                     // if board(x,y) == 0, return
                    Position p2 = new Position(x, y);                           // p2.x = x and p2.y = y
                    p.next = p2;                                                // p.next.x and p.next.y = p2.x and p2.y        WHAT AND WHY?? - next is class variable; p is method variable
                    p = p2;                                                     // p.x and p.y = p2.x and p2.y                  WHY?? - p and p2 are both method variables
                }
            }
        }
        return start.next;                                                      // start.next is keeping track of the x,y coordinates in a nested fashion of all empty positions
    }

    // OVERLOADED METHOD THAT SOLVES THE INDIVIDUAL POSITIONS IN THE PUZZLE
    private void solve(Position p) {                                            // solves the current empty position
        // If no remaining empty space, puzzle is solved
        if (p == null) {                                                        // p is the set of x,y coordinates passed from listEmptyPositions()
            foundSolution();                                                    // adds solution to a list of solutions and displays the solution
            return;                                                             // return to calling method solve()
        }

        // Try all possibile values at this position
        int x = p.x;                                                            // sets x to p's x
        int y = p.y;                                                            // sets y to p's y
        for (int n = 1; n <= 9; n++) {                                          // test each number 1-9 to see if it's legal in the current position
            board.set(x, y, n);                                                 // calls set() method from Board class and passes teh x,y coordinates and number n
            if (isLegal(x, y))                                                  // checks if the number passed is legal in position x,y
                solve(p.next);                                                  // if the number is legal, then solve the next empty position   HOW?? - somehow seems to remove each "x, y, next" as it solves it...
        }
        // reset
        board.set(x, y, 0);                                                     // reset that position
    }

    // DISPLAYS THE SOLUTION(S) ONCE FOUND
    private void foundSolution() {
        solutions.add(board.copy());                                            // adds a copy of the solution to the List solutions
        board.show();                                                           // displays the current solution
        return;                                                                 // return to calling method sovle(Position p)
    }

    // CHECKS IF A POSITION IS LEGAL
    private boolean isLegal(int x, int y) {
        int n = board.get(x, y);                                                // assigns n the number contained at position x,y in Board class
        return isLegalColumn(x, n) && isLegalRow(y, n) && isLegalBlock(x, y, n);// returns true if column, row, and block are all legal
    }

    // CHECKS THE COLUMN FOR LEGALITY
    private boolean isLegalColumn(int x, int n) {                               // accepts position x and the number to check for legality
        boolean present = false;                                                // sets present variable to false
        for (int j = 0; j < 9; j++) {                                           // roll through all 9 positions in column x
            if (board.get(x, j) == n) {                                         // checks if the number stored at position x,j is the same as the number passed in
                if (present)                                                    // checks if present is true
                    return false;                                               // returns false if present is true
                present = true;                                                 // sets present to true if the number passed in is found elsewhere in the column
            }
        }
        return true;                                                            // returns true if the number passed in was NOT found elsewhere in the column
    }

    // CHECKS THE ROW FOR LEGALITY
    private boolean isLegalRow(int y, int n) {                                  // accepts position y and the number to check for legality
        boolean present = false;                                                // sets present variable to false
        for (int i = 0; i < 9; i++) {                                           // roll through all 9 positions in row y
            if (board.get(i, y) == n) {                                         // checks if the number stored at position i,y is the same as the number passed in
                if (present)                                                    // checks if present is true
                    return false;                                               // returns false if present is true
                present = true;                                                 // sets present to true if the number passed in is found elsewhere in the row
            }
        }
        return true;                                                            // returns true if the number passed in was NOT found elsewhere in the row
    }

    // CHECKS THE SQUARE FOR LEGALITY
    private boolean isLegalBlock(int x, int y, int n) {                         // accepts position x,y and the number to check for legality
        boolean present = false;                                                // sets present variable to false
        int x1 = x - (x % 3);                                                   // sets x1 to either 0, 3, or 6 depending on value of x
        int x2 = x1 + 3;                                                        // sets x2 to either 3, 6, or 9 depending on value of x
        int y1 = y - (y % 3);                                                   // sets y1 to either 0, 3, or 6 depending on value of y
        int y2 = y1 + 3;                                                        // sets y2 to either 3, 6, or 9 depending on value of y

        for (int i = x1; i < x2; i++) {                                         // set i to x1 (0, 3, or 6); cycle through while i is less than x2 (3, 6, or 9)
            for (int j = y1; j < y2; j++) {                                     // set j to y1 (0, 3, or 6); cycle through while j is less than y2 (3, 6, or 9)
                if (board.get(i, j) == n) {                                     // checks if the number stored at position i,j is the same as the number passed in
                    if (present)                                                // checks if present is true
                        return false;                                           // returns false if present is true
                    present = true;                                             // sets present to true if the number passed in is found elsewhere in the block
                }
            }
        }
        return true;                                                            // returns true if the number passed in was NOT found elsewhere in the block
    }

    // USED WITH FINDCOMMONSOLUTIONAT() TO FIND THE COMMON SOLUTION WET AMONG ALL SOLUTIONS
    public void findCommonSolution() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                board.set(i, j, findCommonSolutionAt(i, j));                    // sets board coordinates to i,j and the returned number c
    }

    // USED WITH FINDCOMMONSOLUTION() TO FIND THE COMMON SOLUTION SET AMONG ALL SOLUTIONS
    private int findCommonSolutionAt(int i, int j) {
        int c = 0;                                                              // initialize c to 0; returns number common to all solution sets
        for (Board s : solutions) {                                             // cycle through all solutions
            int n = s.get(i, j);                                                // sets n to value at s position (i,j)
            if (c == 0) {                                                       // if c is 0
                c = n;                                                          // set c to n
            } else if (c != n) {                                                // if c is not equal to n
                c = 0;                                                          // set c to 0
                break;
            }
        }
        return c;                                                               // return c to findCommonSolution() to be set in the board at position i,j
    }
}

// HOLDS THE GAMEBOARD, COPIES IT, AND PRINTS IT
class Board {
    private final int[] array = new int[81];                                    // creates an array 81 ints long

    // CONSTRUCTORS
    private Board() { }                                                         // empty constructor to simply have an uninitialized array of 81 ints

    public Board(int[][] values) {                                              // constructor to initialize array of ints with the values from a 2D array passed in
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                array[9 * j + i] = values[ i ][j];                              // assigns the values from 2D array i,j to 1D array 9*j+i
    }

    // GETTER METHOD
    public int get(int i, int j) {                                              // get method requires i and j be passed it to return the value stored in array
        return array[9 * j + i];
    }

    // SETTER METHOD
    public void set(int i, int j, int n) {                                      // set method requires i, j, and n be passed to set the array position to n
        array[9 * j + i] = n;
    }

    // HAS TO BE OVERRIDDEN
    public boolean equals(Object obj) {                                         // overridden method to compare objects for equality
        return obj instanceof Board && Arrays.equals(array, ((Board) obj).array);
    }

    // HAS TO BE OVERRIDDEN
    public int hashCode() {                                                     // only overridden because equals() was overridden
        return Arrays.hashCode(array);
    }

    // CREATES A NEW BOARD AND COPIES ONE BOARD INTO THE OTHER
    public Board copy() {
        Board b = new Board();                                                  // creates a new instance of Board called b
        System.arraycopy(array, 0, b.array, 0, array.length);                   // copies array into b's array
        return b;                                                               // returns b
    }

    // PRINTS THE BOARD
    public void show() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0)                                                     // creates the border at top and after 3 rows
                System.out.println("+-----+-----+-----+");
            for (int j = 0; j < 9; j++) {
                System.out.print((j % 3 == 0 ? "|" : " "));                     // either printing a border or empty space
                int n = get(i, j);                                              // sets n equal to the number found in board position i,j
                System.out.print(n == 0 ? " " : n);                             // either prints 0 or the number
            }
            System.out.println("|");                                            // prints the final right-hand border
        }
        System.out.println("+-----+-----+-----+");                              // prints the bottom border
    }
}
