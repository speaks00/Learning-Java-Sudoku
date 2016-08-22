import java.util.Random;

class RowCol
{
    private int row;
    private int col;
    // CONSTRUCTORS
    public RowCol()
    {
        this(0,0);
    }
    public RowCol(int r, int c)
    {
        Set_Row(r);
        Set_Col(c);
    }
    // GET METHODS
    public int Get_Row()
    {
        return row;
    }
    public int Get_Col()
    {
        return col;
    }
    // SET METHODS
    public void Set_Row(int r)
    {
        row = r;
    }
    public void Set_Col(int c)
    {
        col = c;
    }
}

class Grid
{
    public static final int N = 9;
    public static final int UNASSIGNED = 0;
    int k = 0;

    Grid()
    {
        int[][] grid = new int[N][N];

        Fill_Grid(grid);

        //Print_Grid(grid);

        if (Solve_Sudoku(grid) == true)
        {
            Print_Grid(grid);
            while (k < 50)
            {
                Create_Puzzle(grid);
                k++;
            }
            Print_Grid(grid);
        }
        else
        {
            System.out.println("No solution exists\n\n");
        }
    }

    // FILLS THE FIRST ROW WITH A COMPLETELY RANDOM SEQUENCE OF NUMBERS 1-9
    // ALLOWS FOR A DIFFERENT SOLUTION FROM SOLVE_SUDOKU() EVERY TIME
    public void Fill_Grid(int grid[][])
    {
        for (int i = 0; i < 9; i++)
        {
            String rng = String.valueOf(new Random().nextInt(N) + 1);
            int t = Integer.parseInt(rng);

            while (t == grid[0][0] || t == grid[0][1] || t == grid[0][2] || t == grid[0][3] || t == grid[0][4]
                    || t == grid[0][5] || t == grid[0][6] || t == grid[0][7] || t == grid[0][8])
            {
                rng = String.valueOf(new Random().nextInt(N) +1);
                t = Integer.parseInt(rng);
            }
            grid[0][i] = t;
        }
    }

    // SHOULD REMOVE A NUMBER AND CHECK FOR MORE THAN ONE SOLUTION
    // REPEAT PROCESS UNTIL DESIRED DIFFICULTY LEVEL HAS BEEN REACHED AND ONLY ONE SOLUTION EXISTS
    public boolean Create_Puzzle(int grid[][])
    {
        String rng1 = String.valueOf(new Random().nextInt(N));
        String rng2 = String.valueOf(new Random().nextInt(N));
        int i = Integer.parseInt(rng1);
        int j = Integer.parseInt(rng2);

        if (grid[i][j] != UNASSIGNED)
        {
            grid[i][j] = UNASSIGNED;
        }
        else
        {
            Create_Puzzle(grid);
        }

        return false;
    }

    public boolean Solve_Sudoku(int grid[][])
    {
        RowCol r = new RowCol();
        RowCol c = new RowCol();

        if (!Find_Unassigned_Location(grid, r, c))
        {
            return true;
        }

        for (int num = 1; num <= N; num++)
	{
            if (Is_Safe(grid, r.Get_Row(), c.Get_Col(), num))
            {
		grid[r.Get_Row()][c.Get_Col()] = num;

		if (Solve_Sudoku(grid))
                {
                    return true;
                }

                grid[r.Get_Row()][c.Get_Col()] = UNASSIGNED;
            }
	}
        return false;
    }

    public boolean Find_Unassigned_Location(int grid[][], RowCol r, RowCol c)
    {
        for (int row = 0; row < N; row++)
        {
            for (int col = 0; col < N; col++)
            {
                if (grid[row][col] == UNASSIGNED)
                {
                    r.Set_Row(row);
                    c.Set_Col(col);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean Used_In_Row(int grid[][], int row, int num)
    {
        for (int col = 0; col < N; col++)
	{
            if (grid[row][col] == num)
		return true;
	}
        return false;
    }

    public boolean Used_In_Col(int grid[][], int col, int num)
    {
        for (int row = 0; row < N; row++)
	{
            if (grid[row][col] == num)
		return true;
	}
        return false;
    }

    public boolean Used_In_Box(int grid[][], int boxStartRow, int boxStartCol, int num)
    {
        for (int row = 0; row < 3; row++)
	{
            for (int col = 0; col < 3; col++)
            {
		if (grid[row+boxStartRow][col+boxStartCol] == num)
                    return true;
            }
	}
        return false;
    }

    public boolean Is_Safe(int grid[][], int row, int col, int num)
    {
        return !Used_In_Row(grid, row, num) &&
		!Used_In_Col(grid, col, num) &&
		!Used_In_Box(grid, (row - (row % 3)), (col - (col % 3)), num);
    }

    public void Print_Grid(int grid[][])
    {
        int row, col;

	System.out.print("-------------------------------\n");
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
		System.out.print("-------------------------------\n");
	}
    }
}

public class Test
{
    public static void main(String[] args)
    {
        new Grid();
    }
}
