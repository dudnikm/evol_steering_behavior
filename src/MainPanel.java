import javax.swing.*;
import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;


public class MainPanel extends JPanel implements ActionListener {

    public static int SCREEN_WIDTH = 500;
    public static int SCREEN_HEIGHT = 500;
    public static int DELAY = 150;
    public static int SCREEN_UNIT = 12;
    public static int NUMBER_OF_AGENTS = 3;

    Timer timer;
    java.util.Timer healthTimer;
    Random r;
    ArrayList<Food> food;
    Vector2d v1;
    Point[] rotatedCoords;
    Agent[] agents;


    public MainPanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);

        start();
    }

    public void start(){
        r = new Random();
        timer = new Timer(DELAY, this);
        healthTimer = new java.util.Timer();
        rotatedCoords = new Point[3];
        rotatedCoords[0] = new Point();
        rotatedCoords[1] = new Point();
        rotatedCoords[2] = new Point();


        agents = new Agent[NUMBER_OF_AGENTS];
        for(int i =0; i< NUMBER_OF_AGENTS;i++)
            agents[i] = new Agent(r.nextInt(SCREEN_WIDTH),r.nextInt(SCREEN_HEIGHT));

        food = new ArrayList<>();
        for(int i = 0; i< 10;i++){
            food.add(new Food(r.nextInt(SCREEN_HEIGHT),r.nextInt(SCREEN_WIDTH)));
        }

        //Agent's health decrement
        /*healthTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                agent.decreaseHealth();
            }
        }, 1000, 1000);*/

        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        //Display agent

        for(Agent agent: agents) {
            g2d.setColor(agent.getColor());
            Polygon agentPoly = getAgentPoly(agent);
            g2d.drawPolygon(agentPoly);
        }

        //Display food
        g.setColor(Color.green);
        for(Food f : food){
            g.fillOval(f.getX(),f.getY(),8,8);
        }


        //debug(g);
    }

    //Displays info for debugging
    /*public void debug(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform old = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.translate(agent.getX()+SCREEN_UNIT/2,agent.getY()+SCREEN_UNIT/2);
        g2d.setTransform(at);

        //Display velocity
        g2d.setColor(Color.red);
        g2d.drawOval((int)agent.velocity.getX()*2,(int)agent.velocity.getY()*2,8,8);
        //Display acceleration
        g2d.setColor(Color.yellow);
        g2d.drawOval((int)agent.acceleration.getX()*2,(int)agent.acceleration.getY()*2,8,8);
        //Display steering power
        g2d.setColor(Color.blue);
        g2d.drawOval((int)agent.steer.getX()*2,(int)agent.steer.getY()*2,8,8);
        //Display Desired Velocity
        g2d.setColor(Color.magenta);
        g2d.drawOval((int)agent.desiredVelocity.getX()*2,(int)agent.desiredVelocity.getY()*2,8,8);
        g2d.setTransform(old);

        //Print Agent's vectors coordinates
        System.out.printf("Velocity: X = %f, Y = %f \n Acceleration: X = %f, Y = %f \n Steering force: X = %f, Y = %f \n Desired: X = %f, Y = %f \n__________________\n\n",
                agent.velocity.getX(),agent.velocity.getY(),agent.acceleration.getX(),agent.acceleration.getY(),agent.steer.getX(),agent.steer.getY(),agent.desiredVelocity.getX(),agent.desiredVelocity.getY());
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Agent agent: agents) {
            agent.seek(food);
            agent.move();
        }
        checkCollision();
        repaint();
    }

    public void checkCollision(){
        for(int i = 0; i <food.size();i++){
            Rectangle foodRect = new Rectangle(food.get(i).getX(),food.get(i).getY(),8,8);
            for(Agent agent:agents) {
                Rectangle agentRect = new Rectangle((int) agent.getX(), (int) agent.getY(), SCREEN_UNIT, SCREEN_UNIT);
                if (foodRect.intersects(agentRect)) {
                    food.remove(i);
                    spawnFood();
                    agent.increaseHealth();
                }
            }
        }

    }

    public void spawnFood(){
        if(food.size() < 10)
            food.add(new Food(r.nextInt(SCREEN_HEIGHT),r.nextInt(SCREEN_WIDTH)));
    }

    public Polygon getAgentPoly(Agent agent){
        Polygon polygon = new Polygon();
        
        v1 = new Vector2d(0,-10);

        Vector2d v2 = agent.velocity;

        double angle = v2.getX() > 0 ? v2.angle(v1) : v2.angle(v1)*-1;

        AffineTransform.getRotateInstance(angle, agent.getX()+SCREEN_UNIT/2, agent.getY()+SCREEN_UNIT/2)
                .transform(agent.getCoords(),0,rotatedCoords,0,3);

        for(int i =0; i<rotatedCoords.length;i++){
            polygon.addPoint(rotatedCoords[i].x,rotatedCoords[i].y);
        }

        return polygon;
    }

}