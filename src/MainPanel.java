import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class MainPanel extends JPanel implements MouseMotionListener, ActionListener {

    public static int SCREEN_WIDTH = 500;
    public static int SCREEN_HEIGHT = 500;
    public static int DELAY = 150;
    public static int SCREEN_UNIT = 12;

    Timer timer;
    java.util.Timer healthTimer;
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
        healthTimer = new java.util.Timer();
        agent = new Agent(120,150);
        mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();

        food = new ArrayList<>();
        for(int i = 0; i< 10;i++){
            food.add(new Food(r.nextInt(SCREEN_HEIGHT),r.nextInt(SCREEN_WIDTH)));
        }
        healthTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                agent.decreaseHealth();
            }
        }, 500, 500);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        Graphics2D g2d = (Graphics2D) g.create();


        //Display agent
        g2d.setColor(agent.getColor());
        Rectangle2D agentRect = new Rectangle(agent.getX(),agent.getY(),SCREEN_UNIT, SCREEN_UNIT);
        //g2d.rotate(Math.toRadians(45));
        g2d.draw(agentRect);
        g2d.fill(agentRect);
        g2d.dispose();
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
                agent.increaseHealth();
            }
        }

    }

    public void moveAgent(){

        int dX = agent.getTargetX() - agent.getX();
        int dY = agent.getTargetY() - agent.getY();
        int dirX, dirY;
        agent.move();
        System.out.println(agent.getSpeed());
        if(dX != 0 || dY != 0) {
            dirX = (dX == 0 ? 1 : dX/Math.abs(dX));
            dirY = (dY == 0 ? 1 : dY/Math.abs(dY));
            agent.setCoords(agent.getX() + agent.getSpeed()*dirX,agent.getY() + agent.getSpeed()*dirY);
        }
    }
}