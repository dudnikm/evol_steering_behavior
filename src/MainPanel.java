import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MainPanel extends JPanel implements MouseMotionListener, ActionListener {

    public static int SCREEN_WIDTH = 500;
    public static int SCREEN_HEIGHT = 500;
    public static int DELAY = 500;
    public static int SCREEN_UNIT = 25;

    Timer timer;
    Agent agent;

    int mouseX;
    int mouseY;


    public MainPanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);

        this.addMouseMotionListener(this);

        start();
    }

    public void start(){
        timer = new Timer(DELAY, this);
        agent = new Agent(120,150);
        mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();

        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillRect(agent.getX(),agent.getY(),SCREEN_UNIT, SCREEN_UNIT);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveAgent();
        System.out.println("action performed");
        repaint();
    }

    public void moveAgent(){

        System.out.printf("mouseX = %d, agentX = %d \n mouseY = %d, agentY = %d",mouseX, agent.getX(), mouseY, agent.getY());
        int dX = mouseX - agent.getX();
        int dY = mouseY - agent.getY();
        if(dX != 0 || dY != 0) {
            agent.setX(agent.getX() + dX/10);
            agent.setY(agent.getY() + dY/5);
        }
    }
}