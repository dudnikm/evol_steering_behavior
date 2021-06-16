import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class MainPanel extends JPanel implements MouseMotionListener, ActionListener {

    public static int SCREEN_WIDTH = 500;
    public static int SCREEN_HEIGHT = 500;
    public static int DELAY = 500;
    public static int SCREEN_UNIT = 12;

    Timer timer;
    Random r;
    Agent agent;
    ArrayList<Food> food;

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
        r = new Random();
        timer = new Timer(DELAY, this);
        agent = new Agent(120,150);
        mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();

        food = new ArrayList<>();
        for(int i = 0; i< 10;i++){
            food.add(new Food(r.nextInt(SCREEN_HEIGHT),r.nextInt(SCREEN_WIDTH)));
        }

        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //Display agent
        g.setColor(Color.white);
        g.fillRect(agent.getX(),agent.getY(),SCREEN_UNIT, SCREEN_UNIT);

        //Display food
        g.setColor(Color.green);
        for(Food f : food){
            g.fillOval(f.getX(),f.getY(),8,8);
        }
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
        checkCollision();
        agent.seek(food);
        repaint();
    }

    public void checkCollision(){
        for(int i = 0; i <food.size();i++){
            Rectangle foodRect = new Rectangle(food.get(i).getX(),food.get(i).getY(),8,8);
            Rectangle agentRect = new Rectangle(agent.getX(),agent.getY(),SCREEN_UNIT,SCREEN_UNIT);
            if(foodRect.intersects(agentRect)){
                food.remove(i);
                System.out.println("REMOVING");
            }
        }

    }

    public void moveAgent(){

        int dX = agent.getTargetX() - agent.getX();
        int dY = agent.getTargetY() - agent.getY();
        int dir;
        if(dX != 0 || dY != 0) {
            dir = dX/Math.abs(dX);
            agent.setX(agent.getX() + 5*dir);
            dir = dY/Math.abs(dY);
            agent.setY(agent.getY() + 5*dir);
        }
    }
}