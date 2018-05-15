import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Position {
    final int x, y;                                                             // class variables of type int to hold x, y coordinates
    Position next;                                                              // class variable of type Position to hold the next position

    Position(int x, int y) {
        this.x = x;                                                             // sets class variable x to whatever x value is passed in
        this.y = y;                                                             // sets class variable y to whatever y value is passed in
    }
}

// HOLD THE GAME BOARD, COPIES IT, AND DISPLAYS IT
class Board {
    private final int[] array = new int[81];                                    // creates an array 81 ints long

    // CONSTRUCTORS
    private Board(){}                                                           // empty constructor to simply have an uninitialized array of 81 ints

    public Board(int[][] values) {                                              // constructor to initialize array of ints with the values from a 2D array passed in
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                array[9 * j + i] = values[i][j];                                // assigns the values from 2D array i,j to 1D array 9*j+i
    }

    // GETTER METHOD
    public int Get(int i, int j) {
        return array[9 * j + i];                                                // get method requires i and j be passed it to return the value stored in the array
    }

    // SETTER METHOD
    public void Set(int i, int j, int n) {
        array[9 * j + i] = n;                                                   // set method requires i, j, and n be passed to set the array position to n
    }

    // HAS TO BE OVERRIDDEN -- I DO NOT FULLY UNDERSTAND WHY THOUGH
    public boolean Equals(Object obj) {
        return obj instanceof Board && Arrays.equals(array, ((Board) obj).array);
    }

    // HAS TO BE OVERRIDDEN -- I DO NOT FULLY UNDERSTAND WHY THOUGH
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    // CREATES A NEW BOARD AND COPIES ONE BOARD INTO IT
    public Board Copy() {
        Board b = new Board();                                                  // creates a new instance of Board called b
        System.arraycopy(array, 0, b.array, 0, array.length);                   // copies aray into b's array
        return b;                                                               // returns b
    }

    // DISPLAYS THE GAME BOARD
    public void Show() {
        for (int i = 0; i < 9;   i++) {
            if (i % 3 == 0)                                                     // creates the top border and border every 3 rows
                System.out.println("+-----+-----+-----+");
            for (int j = 0; j < 9; j++) {
                System.out.print((j % 3 == 0 ? "|" : " "));                     // either printing a border or empty space
                int n = Get(i, j);                                              // sets n equal to the number found in board position i,j
                System.out.print(n == 0 ? " " : n);                             // either prints 0 or the number; consider switching "0" for " "
            }
            System.out.println("|");                                            // prints the final right-side border
        }
        System.out.println("+-----+-----+-----+");                              // prints the bottom border
    }
}

// CHECKS FOR MULTIPLE SOLUTIONS IN PREVIOUSLY GENERATED PUZZLE
class Multiple_Solution_Test {
    private Board board;                                                        // creates a new instance of the Board class called board
    private final List<Board>solutions = new ArrayList<Board>();                // creates a list of type Board called solutions; holds all possible solutions

    // CONSTRUCTORS
    public Multiple_Solution_Test() {};                                         // empty constructor

    public Multiple_Solution_Test(int[][] arr) {
        board = new Board(arr);                                                 // assigns board the values passed via th e2D array argument
    }

    // OVERLOADED METHOD TO SOLVE THE PUZZLE
    public static void solve(int[][] board) {
        new Multiple_Solution_Test(board).Solve();                              // creates new class instance passing it whatever 2D array was passed, calling the other solve method
    }

    // OVERLOADED METHOD THAT SOLVES THE PUZZLE AND PROVIDES INDICATION OF VALIDITY
    private void Solve() {
        Solve(List_Empty_Positions());                                          // returns the next empty position (type Position)
        int count = solutions.size();                                           // sets count to the size of the solutions list
        if (count == 0)
            System.out.println("No solution - invalid puzzle");                 // if count is set to 0, there are no solutions
        else if (count == 1)
            System.out.println("Unique solution - valid puzzle");               // if count is set to 1, it is a valid puzzle with just one solution
        else {
            System.out.println(count + " solutions - invalid puzzle");          // if there are 2 more more solutions, it's an invalid puzzle
            System.out.println("\nCommon part of all solutions:");              // identifying the common part of all solutions
            Find_Common_Solution();                                             // get the process started to finding the common part of the solutions
            board.Show();                                                       // method to display the common part of the solutions
        }
    }

    // PROVIDES A LIST OF EMPTY POSIITONS IN GAME BOARD
    public Position List_Empty_Positions() {
        Position start = new Position(-1, -1);                                  // initializes start.x to -1 and start.y to -1
        Position p = start;                                                     // sets p.x to start.x and p.y to start.y
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (board.Get(x, y) == 0) {                                     // if board(x,y) == 0, return (though I don't know how it returns...)
                    Position p2 = new Position(x, y);                           // p2.x = x and p2.y = y
                    p.next = p2;                                                // p.next.x and p.next.y = p2.x and p2.y    WHAT AND WHY - next is class variable; p is method variable
                    p = p2;                                                     // p.x and p.y - p2.x and p2.y              WHY - p and p2 are both method variables
                }                                                               // I HAD THE ABOVE COMMENTS FIGURED OUT AT ONE TIME...DO IT AGAIN
            }
        }
        return start.next;                                                      // start.next is keeping track of the x,y coordinates in a nested fashion of all empty positions
    }

    // OVERLOADED METHOD THAT SOLVES THE INDIVIDUAL POSITIONS IN THE PUZZLE
    private void Solve(Position p) {
        // If no remaining empty space, puzzle is solved
        if (p == null) {                                                        // p is the set of x,y coordinates passed from List_Empty_Positions()
            Found_Solution();                                                   // adds solution to a list of solutions and displays the solution
            return;                                                             // returns to calling method Solve()
        }

        // Try all possible values at this position
        int x = p.x;                                                            // sets x to p's x
        int y = p.y;                                                            // sets y to p's y
        for (int n = 1; n <= 9; n++) {                                          // test each number, 1-9, to see if it's legal in the current position
            board.Set(x, y, n);                                                 // calls Set() from Board class and passes the x,y coordinates and number n
            if (Is_Legal(x, y))                                                 // checks if the number passed is legal in position x,y
                Solve(p.next);                                                  // if the number is legal, then solve the next empty position
        }
        board.Set(x, y, 0);                                                     // reset the position
    }

    // DISPLAYS THE SOLUTION(S) ONCE FOUND
    private void Found_Solution() {
        solutions.add(board.Copy());                                            // adds a copy of the solution to the List solutions
        board.Show();                                                           // displays the current solution
        return;                                                                 // return to calling method (Solve(Position p))
    }

    // CHECKS IF A POSITION IS LEGAL
    private boolean Is_Legal(int x, int y) {                                    // accepts the x,y coordinates of a position for legality checks
        int n = board.Get(x, y);                                                // assigns n the number contained at position x,y in Board class
        return Is_Legal_Column(x, n) &&
                Is_Legal_Row(y, n) &&
                Is_Legal_Block(x, y, n);                                        // returns true if n is legal in column, row, and block
    }

    // CHECKS THE COLUMN FOR LEGALITY
    private boolean Is_Legal_Column(int x, int n) {                             // accepts position x and the number to check for legality
        boolean present = false;                                                // initializes present variable to false
        for (int j = 0; j < 9; j++) {                                           // cycle through all 9 positions in column x
            if (board.Get(x, j) == n) {                                         // checks if the number stored at position x,j is the same as n passed in
                // EVALUATE ORDER OF THE FOLLOWING 3 LINES
                if (present)                                                    // checks if present is true
                    return false;                                               // returns false if present is true
                present = true;                                                 // sets present to true if the number passed in is found elsewhere in the column
            }
        }
        return true;                                                            // returns true if the number passed in was NOT found elsewhere in the column
    }

    // CHECKS THE ROW FOR LEGALITY
    private boolean Is_Legal_Row(int y, int n) {                                // accepts position y and the number to check for legality
        boolean present = false;                                                // initializes present variable to false
        for (int i = 0; i < 9; i++) {                                           // cycle through all 9 positions in row y
            if (board.Get(i, y) == n) {                                         // checks if the number stored at position i,y is the same as the number passed in
                if (present)                                                    // checks if present is true
                    return false;                                               // returns false if present is true
                present = true;                                                 // sets present to true if the number passed in is found elsewhere in the row
            }
        }
        return true;                                                            // returns true if the number passed in was NOT found elsewhere in the row
    }

    // CHECKS THE SQUARE FOR LEGALITY
    private boolean Is_Legal_Block(int x, int y, int n) {                       // acepts position x,y and the number to check for legality
        boolean present = false;                                                // initializes present variable to false
        int x1 = x - (x % 3);                                                   // sets x1 to either 0, 3, or 6 depending on value of x
        int x2 = x1 + 3;                                                        // sets x2 to either 3, 6, or 9 depending on value of x
        int y1 = y - (y % 3);                                                   // sets y1 to either 0, 3, or 6 depending on value of y
        int y2 = y1 + 3;                                                        // sets y2 to either 3, 6, or 9 depending on value of y

        for (int i = x1; i < x2; i++) {                                         // set i to x1 (0, 3, or 6); cycle through while i is less than x2 (3, 6, or 9)
            for (int j = y1; y1 < y2; j++) {                                    // set j to y1 (0, 3, or 6); cycle through while j is less than y2 (3, 6, or 9)
                if (board.Get(i, j) == n) {                                     // checks if the number stored at position i,j is the same as the number passed in
                    if (present)                                                // checks if present is true
                        return false;                                           // returns false if present is true
                    present = true;                                             // sets present to true if the number passed in is found elsewhere in the block
                }
            }
        }
        return true;                                                            // returns true if the number passed in was NOT found elsewhere in the block
    }

    // USED WITH Find_Common_Solution_At() TO FIND THE COMMON SOLUTION SET AMONG ALL SOLUTIONS
    public void Find_Common_Solution() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                board.Set(i, j, Find_Common_Solution_At(i, j));                 // sets board coordinates to i,j and the returned number c from the method call
    }

    // USED WITH Find_Common_Solution() TO FIND THE COMMON SOLUTION SET AMONG ALL SOLUTIONS
    private int Find_Common_Solution_At(int i, int j) {
        int c = 0;                                                              // initialize c to 0; returns number common to all solution sets
        for (Board s : solutions) {                                             // cycle through all solutions
            int n = s.Get(i, j);                                                // sets n to value at s position (i,j)
            if (c == 0) {                                                       // if c is 0
                c = n;                                                          // set c to n
            } else if (c != n) {                                                // if c is not equal to n
                c = 0;                                                          // set c to 0
                break;
            }
        }
        return c;                                                               // return c to Find_Common_Solution() to be set in the board at position i,j
    }
}

class Game {
    int[][] grid = new int[9][9];                                               // a 9x9 2D array for the game grid
    int k = 0;                                                                  // TBD if this is needed for removing numbers from the game board still
    Board gb;                                                                   // new addition; use to randomly fill, remove numbers, and pass solution to MST for legality
    Multiple_Solution_Test mst = new Multiple_Solution_Test();                  // creates a variable of type Multiple_Solution_Test

    // CONSTRUCTOR
    Game() {
        // This is the core method that builds/combines the entire program
/*******************************************************************************************************************************************************************/
        Fill_Board_Top_Row(grid);                                               // should fill top row (15 Apr 18)
        // Develop method called Fill_Board(int[][] gbGrid)
        gb = new Board(grid);                                                   // should pass grid to gb (15 Apr 18)
        gb.Show();                                                              // should show top row full...but doesn't...shows empty board (15 Apr 18)
    }
/*******************************************************************************************************************************************************************/

    // FILLS THE FIRST ROW WITH A COMPLETELY RANDOM SEQUENCE OF NUMBERS 1-9; ALLOWS FOR A DIFFERENT SOLUTION EVERY TIME
    public void Fill_Board_Top_Row(int grid[][]) {
        for (int i = 0; i < 0; i++) {                                           // cycle through the top row
            String rng = String.valueOf(new Random().nextInt(9) + 1);           // generate a random number 1-9
            int n = Integer.parseInt(rng);                                      // set n to the random number

            // Tests to see if the random number generated is equal to any numbers in the top row
            // Consider trying to insert function call to Is_Legal_Row()
            while (n == grid[0][0] || n == grid[0][1] || n == grid[0][2] || n == grid[0][3] || n == grid[0][4]
                    || n == grid[0][5] || n == grid[0][6] || n == grid[0][7] || n == grid[0][8]) {
                rng = String.valueOf(new Random().nextInt(9) + 1);              // generate a new number if the previous number is equal to an existing number
                n = Integer.parseInt(rng);                                      // sets n equal to the new number
            }
            grid[0][i] = n;                                                     // sets the current position to the random number
        }
    }
}

public class SudokuConsolidation {
    public static void main(String[] args) {
        new Game();
    }
}
