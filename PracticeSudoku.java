import javax.swing.*;

public class PracticeSudoku extends JFrame {
    public static void main(String[] args) {
        JFrame f=new JFrame("Sudoku Practice");
        UserInterface ui=new UserInterface();
        CreateMenu cm=new CreateMenu();
        JMenuBar mb=new JMenuBar();
        // BUILD FRAME
        mb.add(cm);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(ui);
        f.setJMenuBar(mb);
        f.setSize(500, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }
}
