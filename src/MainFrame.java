import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Program");

        MainPanel mainPanel = new MainPanel();
        this.add(mainPanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
