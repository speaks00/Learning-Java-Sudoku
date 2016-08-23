import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

public class UserInterface extends JPanel {
    private final JPanel t = new JPanel();
    private final JPanel l = new JPanel();
    private final JPanel r = new JPanel();
    private final JPanel b = new JPanel();
    private final JLabel tl = new JLabel("SUDOKU");
    private final JLabel bl = new JLabel("Time:");
    private final CreateBoard gb = new CreateBoard();
    private JTextField btf = new JTextField(3);

    // CONSTRUCTOR
    public UserInterface(){
        setLayout(new BorderLayout());
        add(t, BorderLayout.NORTH);
        add(l, BorderLayout.WEST);
        add(r, BorderLayout.EAST);
        add(b, BorderLayout.SOUTH);
        tl.setFont(new Font("Serif", Font.BOLD, 40));
        t.add(tl);
        add(gb, BorderLayout.CENTER);
        gb.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        b.add(bl);
        b.add(btf);

        Thread clock = new Thread(){
            public void run() {
                long t, ms;
                int m, s;
                // Start time
                t=System.currentTimeMillis();
                // Begin loop to determine/print elapsed time
                for(;;) {
                    ms = System.currentTimeMillis()-t;
                    m = (int)(ms/60000);
                    s = (int)((ms%60000)/1000);

                    // MM:SS format
                    if (m<10 && (s)<10) {
                        btf.setText("0"+m+":0"+s);
                    } else if (m<10) {
                        btf.setText("0"+m+":"+s);
                    } else if (s<10) {
                        btf.setText(m+":0"+s);
                    } else {
                        btf.setText(m+":"+s);
                    }

                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        clock.start();
    }
}

// CREATES THE MENU BAR
class CreateMenu extends JMenuBar implements ActionListener  {
    private JMenu file, ng, options, bg, fc, help;
    private JMenuItem easy, medium, hard, expert, reset, pause, exit, gray, blue, red, black, green, yellow, htp, r;
    private HowToPlay htpForm;
    private Rules rForm;

    public CreateMenu() {
        // SETUP FILE MENU
        file=new JMenu("File");
        ng=new JMenu("New Game");
        easy=new JMenuItem("Easy");
        easy.addActionListener(this);
        medium=new JMenuItem("Medium");
        medium.addActionListener(this);
        hard=new JMenuItem("Hard");
        hard.addActionListener(this);
        expert=new JMenuItem("Expert");
        expert.addActionListener(this);
        ng.add(easy);
        ng.add(medium);
        ng.add(hard);
        ng.add(expert);
        reset=new JMenuItem("Reset Game");
        reset.addActionListener(this);
        pause=new JMenuItem("Pause Game");
        pause.addActionListener(this);
        exit=new JMenuItem("Exit");
        exit.addActionListener(this);
        file.add(ng);
        file.add(reset);
        file.add(pause);
        file.add(exit);
        this.add(file);

        // SETUP OPTIONS MENU
        options=new JMenu("Options");
        bg=new JMenu("Background Color");
        gray=new JMenuItem("Gray (Default)");
        gray.addActionListener(this);
        blue=new JMenuItem("Blue");
        blue.addActionListener(this);
        red=new JMenuItem("Red");
        red.addActionListener(this);
        bg.add(gray);
        bg.add(blue);
        bg.add(red);
        fc=new JMenu("Font Color");
        black=new JMenuItem("Black (Default)");
        black.addActionListener(this);
        green=new JMenuItem("Green");
        green.addActionListener(this);
        yellow=new JMenuItem("Yellow");
        yellow.addActionListener(this);
        fc.add(black);
        fc.add(green);
        fc.add(yellow);
        options.add(bg);
        options.add(fc);
        this.add(options);

        // SETUP HELP MENU
        help=new JMenu("Help");
        htp=new JMenuItem("How to Play");
        htp.addActionListener(this);
        r=new JMenuItem("Rules");
        r.addActionListener(this);
        help.add(htp);
        help.add(r);
        this.add(help);
    }

    // ACTION LISTENER METHOD
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(easy)) {
            // ADD CODE TO CREATE NEW EASY GAME
        }
        if (e.getSource().equals(medium)) {
            // ADD CODE TO CREATE NEW MEDIUM GAME
        }
        if (e.getSource().equals(hard)) {
            // ADD CODE TO CREATE NEW HARD GAME
        }
        if (e.getSource().equals(expert)) {
            // ADD CODE TO CREATE NEW EXPERT GAME
        }
        if (e.getSource().equals(reset)) {
            // ADD CODE TO RESET CURRENT GAME
        }
        if (e.getSource().equals(pause)) {
            // ADD CODE TO PAUSE CURRENT GAME
        }
        if (e.getSource().equals(exit)) {
            System.exit(0);
        }
        if (e.getSource().equals(gray)) {
            // ADD CODE TO MAKE BACKGROUND COLOR GRAY
        }
        if (e.getSource().equals(blue)) {
            // ADD CODE TO MAKE BACKGROUND COLOR BLUE
        }
        if (e.getSource().equals(red)) {
            // ADD CODE TO MAKE BACKGROUND COLOR RED
        }
        if (e.getSource().equals(black)) {
            // ADD CODE TO MAKE FONT COLOR BLACK
        }
        if (e.getSource().equals(green)) {
            // ADD CODE TO MAKE FONT COLOR GREEN
        }
        if (e.getSource().equals(yellow)) {
            // ADD CODE TO MAKE FONT COLOR YELLOW
        }
        if (e.getSource().equals(htp)) {
            // ADD CODE TO DEMONSTRATE HOW TO PLAY THE GAME
            htpForm=new HowToPlay();
            htpForm.setVisible(true);
        }
        if (e.getSource().equals(r)) {
            try {
                // ADD CODE TO DISPLAY THE RULES OF SUDOKU
                rForm=new Rules();
                rForm.setVisible(true);
            } catch (BadLocationException ex) {
                Logger.getLogger(CreateMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

// BRINGS UP NEW WINDOW WITH DIRECTIONS OF HOW TO PLAY SUDOKU WHEN "HOW TO PLAY" IS SELECTED FROM THE "HELP" MENU
class HowToPlay extends JDialog implements ActionListener {
    private final JPanel contentPanel = new JPanel();

    public static void main(String[] args) {
        try {
            HowToPlay dialog = new HowToPlay();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HowToPlay() {
        setBounds(100,100,450,300);
        getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5,5,5,5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        JLabel title = new JLabel("How to Play Sudoku");
        contentPanel.add(title);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton ok = new JButton("OK");
        ok.setActionCommand("OK");      // WTF is this??
        ok.addActionListener(this);
        buttonPane.add(ok);
        getRootPane().setDefaultButton(ok);     // Automatically selects "OK" as default selected button?
    }

    public void actionPerformed(ActionEvent e) {
        if ("OK".equals(e.getActionCommand())) {
            dispose();
        }
    }
}

// BRINGS UP NEW WINDOW WITH THE RULES OF SUDOKU WHEN "HOW TO PLAY" IS SELECTED FROM THE "HELP" MENU
class Rules extends JDialog implements ActionListener {
    private final JPanel contentPanel = new JPanel();

    public static void main(String[] args) {
        try {
            Rules dialog = new Rules();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Rules() throws BadLocationException {
        setBounds(100,100,450,300);
        getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5,5,5,5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        JLabel title = new JLabel("Rules of Sudoku");
        contentPanel.add(title);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton ok = new JButton("OK");
        ok.setActionCommand("OK");
        ok.addActionListener(this);
        buttonPane.add(ok);
        getRootPane().setDefaultButton(ok);     // Automatically selects "OK" as default selected button
    }

    public void actionPerformed(ActionEvent e) {
        if ("OK".equals(e.getActionCommand())) {
            dispose();
        }
    }
}

// CREATES THE ACTUAL BOARD
class CreateBoard extends JPanel {
    private final JTextField jtf[][]= new JTextField[9][9]; // Array to hold the 81 textfields
    private final JPanel p[][]= new JPanel [3][3]; // Array to hold the 9 panels

    private static int userGrid[][] = new int[9][9]; // Holds the values input by the user
    private static int grid[][] = new int[9][9]; // Grid the program experiments with
    //private static int pGrid[][] = new int[9][9]; // Grid contains possibilities...not sure if this is needed

    // CONSTRUCTOR
    public CreateBoard() {
        // Fill textfield array with 81 textfields
        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                jtf[x][y]=new JTextField(1);
                jtf[x][y].setBorder(BorderFactory.createLoweredBevelBorder());
                jtf[x][y].setHorizontalAlignment(JTextField.CENTER);
                jtf[x][y].setFont(new Font("SansSerif", Font.BOLD, 20));
//                jtf[x][y].setForeground(Color.red);
            }
        }
        // Orient 2D panel array into a 3x3 grid
        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){
                p[x][y]=new JPanel(new GridLayout(3,3));
                p[x][y].setBorder(BorderFactory.createLineBorder(Color.black, 1));
            }
        }

        // Fill grid[][] with a solvable puzzle...HOPEFULLY
        String rng = String.valueOf(new Random().nextInt(9) +1);
        int t = Integer.parseInt(rng);
        grid[0][0] = t;

        // STARTING NUMBER CHECK - SO I CAN SEE IT
        //System.out.println("t = " + t);

// MIGHT SIMPLY REPLACE THIS SEGMENT OF CODE
        // Add textfields to the panels and display the solvable puzzle
        setLayout(new GridLayout(3,3)); // SETS A 3X3 GRID LAYOUT
        for(int u=0; u<3; u++){     // FILLS EACH 3X3 REGION FIRST; U REPRESENTS THE ROW IN A REGION
            for(int i=0; i<3; i++){    // I REPRESENTS THE COLUMN IN A REGION
                for(int x=0; x<3; x++ ){    // I REALLY HAVE NO CLUE WHAT PURPOSE X AND Y SERVE IN ALL THIS
                    for(int y=0; y<3; y++){
                        p[u][i].add(jtf[y+u*3][x+i*3]);

// LOOK AT INSERTING MY TEST.JAVA METHODS AND CLASSES IN HERE INSTEAD OF LOOP() AND VALIDITY() METHODS
                        print(i, u, x, y, loop(0,0,grid));

                        // LETS ME SEE HOW THE GRID IS FILLED
                        //System.out.println("grid = " + grid[i][y]);
                    }
                }
                add(p[u][i]);
            }
        }
// THIS CODE MAY GO INTO THE *IF(E.GETSOURCE().EQUALS(GRAY)){} SECTION ABOVE...JTF IS NOT ALLOWED THERE THOUGH
        // Color select squares gray
        for (int x=0; x<3; x++) {
            for (int y=0; y<3; y++) {
                jtf[x][y].setBackground(Color.lightGray);
                jtf[x][y+6].setBackground(Color.lightGray);
                jtf[x+3][y+3].setBackground(Color.lightGray);
                jtf[x+6][y].setBackground(Color.lightGray);
                jtf[x+6][y+6].setBackground(Color.lightGray);
            }
        }
    }

    // LOOP
    public static int[][] loop(int y, int x, int[][] grid) {
        while (!validity(8, 8, grid) || grid[8][8] == 0) {
            if (userGrid[y][x] != 0) {
                int yy, xx;
                if (x==8) {
                    yy=y+1;
                    xx=0;
                } else {
                    yy=y;
                    xx=x+1;
                }
            } else {
                if (grid[y][x]<9) {
                    grid[y][x]++;
                    if (validity(y, x, grid)) {
                        int yy, xx;
                        if (x==8) {
                            yy=y+1;
                            xx=0;
                        } else {
                            yy=y;
                            xx=x+1;
                        }
                        loop(yy, xx, grid);
                    }
                } else {
                    grid[y][x] = 0;
                    break;
                }
            }
        }
        return grid;
    }

    // VALIDITY...might need work
    public static boolean validity(int x, int y, int[][] grid) {
        String temp="";
        for (int i=0; i<9; i++) {
            temp += Integer.toString(grid[i][y]); // Horizontal check
            temp += Integer.toString(grid[x][i]); // Vertical check
            temp += Integer.toString(grid[(x/3)*3+i/3][(y/3)*3+i%3]); // Square check
        }
        int count=0, idx=0;
        while ((idx=temp.indexOf(Integer.toString(grid[x][y]), idx)) != -1) {
            idx++;
            count++;
        }
        return count==3;
    }

    // PRINT -- likely not needed
    public void print(int i, int u, int x, int y, int[][] grid) {
        jtf[y+u*3][x+i*3].setText(Integer.toString(grid[(x+u*3)][(y+i*3)]));
    }

    // HIDE
    // Easy hides all but 38 textboxes
    // Medium hides all but 30 textboxes
    // Hard hides all but 27 textboxes
    // Expert hides all but 26 textboxes
}
