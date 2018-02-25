import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Position {
    final int x, y;
    Position next;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Board {
    private final int[] array = new int[81];

    private Board(){}

    public Board(int[][] values) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                array[9 * j + i] = values[i][j];
    }

    public int Get(int i, int j) {
        return array[9 * j + i];
    }

    public void Set(int i, int j, int n) {
        array[9 * j + i] = n;
    }

    public boolean Equals(Object obj) {
        return obj instanceof Board && Arrays.equals(array, ((Board) obj).array);
    }

    public int hashCode() {
        return Arrays.hashCode(array);
    }

    public Board Copy() {
        Board b = new Board();
        System.arraycopy(array, 0, b.array, 0, array.length);
        return b;
    }

    public void Show() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0)
                System.out.println("+-----+-----+-----+");
            for (int j = 0; j < 9; j++) {
                System.out.print((j % 3 == 0 ? "|" : " "));
                int n = Get(i, j);
                System.out.print(n == 0 ? " " : n);
            }
            System.out.println("|");
        }
        System.out.println("+-----+-----+-----+");
    }
}

class Multiple_Solution_Test {
    private Board board;
    private final List<Board>solutions = new ArrayList<Board>();

    public Multiple_Solution_Test() {};

    public Multiple_Solution_Test(int[][] arr) {
        board = new Board(arr);
    }

    public static void solve(int[][] board) {
        new Multiple_Solution_Test(board).Solve();
    }

    private void Solve() {
        Solve(List_Empty_Positions());
        int count = solutions.size();
        if (count == 0)
            System.out.println("No solution - invalid puzzle");
        else if (count == 1)
            System.out.println("Unique solution - valid puzzle");
        else {
            System.out.println(count + " solutions - invalid puzzle");
            System.out.println("\nCommon part of all solutions:");
            Find_Common_Solution();
            board.Show();
        }
    }

    public Position List_Empty_Positions() {
        Position start = new Position(-1, -1);
        Position p = start;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (board.Get(x, y) == 0) {
                    Position p2 = new Position(x, y);
                    p.next = p2;
                    p = p2;
                }
            }
        }
        return start.next;
    }

    private void Solve(Position p) {
        if (p == null) {
            Found_Solution();
            return;
        }

        int x = p.x;
        int y = p.y;
        for (int n = 1; n <= 9; n++) {
            board.Set(x, y, n);
            if (Is_Legal(x, y))
                Solve(p.next);
        }
        board.Set(x, y, 0);
    }

    private void Found_Solution() {
        solutions.add(board.Copy());
        board.Show();
        return;
    }

    private boolean Is_Legal(int x, int y) {
        int n = board.Get(x, y);
        return Is_Legal_Column(x, n) && Is_Legal_Row(y, n) && Is_Legal_Block(x, y, n);
    }

    private boolean Is_Legal_Column(int x, int n) {
        boolean present = false;
        for (int j = 0; j < 9; j++) {
            if (board.Get(x, j) == n) {
                if (present)
                    return false;
                present = true;
            }
        }
        return true;
    }

    private boolean Is_Legal_Row(int y, int n) {
        boolean present = false;
        for (int i = 0; i < 9; i++) {
            if (board.Get(i, y) == n) {
                if (present)
                    return false;
                present = true;
            }
        }
        return true;
    }

    private boolean Is_Legal_Block(int x, int y, int n) {
        boolean present = false;
        int x1 = x - (x % 3);
        int x2 = x1 + 3;
        int y1 = y - (y % 3);
        int y2 = y1 + 3;

        for (int i = x1; i < x2; i++) {
            for (int j = y1; y1 < y2; j++) {
                if (board.Get(i, j) == n) {
                    if (present)
                        return false;
                    present = true;
                }
            }
        }
        return true;
    }

    public void Find_Common_Solution() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                board.Set(i, j, Find_Common_Solution_At(i, j));
    }

    private int Find_Common_Solution_At(int i, int j) {
        int c = 0;
        for (Board s : solutions) {
            int n = s.Get(i, j);
            if (c == 0) {
                c = n;
            } else if (c != n) {
                c = 0;
                break;
            }
        }
        return c;
    }
}

class Game {
    int[][] grid = new int[9][9];
    Board gb;
    Multiple_Solution_Test mst;

    Game() { }      // empty constructor for now

    public void Fill_Board_Top_Row(int grid[][]) {
        for (int i = 0; i < 0; i++) {
            String rng = String.valueOf(new Random().nextInt(9) + 1);
            int n = Integer.parseInt(rng);

            while (n == grid[0][0] || n == grid[0][1] || n == grid[0][2] || n == grid[0][3] || n == grid[0][4]
                    || n == grid[0][5] || n == grid[0][6] || n == grid[0][7] || n == grid[0][8]) {
                rng = String.valueOf(new Random().nextInt(9) + 1);
                n = Integer.parseInt(rng);
            }
            grid[0][i] = n;
        }
    }

    public boolean Fill_Board(int grid[][]) {
        if (!mst.List_Empty_Positions()) {
            return true;
        }
        return false;
    }
}

public class SudokuConsolidation {
    public static void main(String[] args) {
        // new Game();
    }
}
