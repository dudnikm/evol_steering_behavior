import javax.swing.*;
import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;


public class MainPanel extends JPanel implements ActionListener {

    public static int SCREEN_WIDTH = 500;
    public static int SCREEN_HEIGHT = 500;
    public static int DELAY = 150;
    public static int SCREEN_UNIT = 12;

    Timer timer;
    java.util.Timer healthTimer;
    Random r;
    Agent agent;
    ArrayList<Food> food;
    Vector2d vector2d;
    Point[] rotatedCoords;


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
        agent = new Agent(120,150);
        rotatedCoords = new Point[3];
        rotatedCoords[0] = new Point();
        rotatedCoords[1] = new Point();
        rotatedCoords[2] = new Point();

        food = new ArrayList<>();
        for(int i = 0; i< 10;i++){
            food.add(new Food(r.nextInt(SCREEN_HEIGHT),r.nextInt(SCREEN_WIDTH)));
        }
        healthTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                agent.decreaseHealth();
            }
        }, 1000, 1000);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        //Display agent
        Polygon agentPoly = getAgentPoly();

        g2d.setColor(agent.getColor());
        g2d.drawPolygon(agentPoly);

<<<<<<< Updated upstream
        Vector2d vector2 = new Vector2d(SCREEN_WIDTH-10,agent.getY());

        g2d.drawOval((int)vector2d.getX(),(int)vector2d.getY(),5,5);
        g2d.drawOval((int)vector2.getX(),(int)vector2.getY(),5,5);

=======
>>>>>>> Stashed changes
        //Display food
        g.setColor(Color.green);
        for(Food f : food){
            g.fillOval(f.getX(),f.getY(),8,8);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        agent.move();
        checkCollision();
        agent.seek(food);
        repaint();
    }

    public void checkCollision(){
        for(int i = 0; i <food.size();i++){
            Rectangle foodRect = new Rectangle(food.get(i).getX(),food.get(i).getY(),8,8);
            Rectangle agentRect = new Rectangle((int)agent.getX(),(int)agent.getY(),SCREEN_UNIT,SCREEN_UNIT);
            if(foodRect.intersects(agentRect)){
                food.remove(i);
                spawnFood();
                agent.increaseHealth();
            }
        }

    }

    public void spawnFood(){
        if(food.size() < 10)
            food.add(new Food(r.nextInt(SCREEN_HEIGHT),r.nextInt(SCREEN_WIDTH)));
    }

    public Polygon getAgentPoly(){
        Polygon polygon = new Polygon();

        vector2d = new Vector2d(agent.getX(),0);

        Vector2d vector2 = new Vector2d(SCREEN_WIDTH-10,agent.getY());

<<<<<<< Updated upstream
        double angle =  Math.atan2(vector2d.getX() - vector2.getX(), vector2.getY() - vector2d.getY());
=======
        double angle = (v2.getX() > 0 ? v2.angle(v1) : v2.angle(v1)*-1);
>>>>>>> Stashed changes
        System.out.println(Math.toDegrees(angle));



        AffineTransform.getRotateInstance(angle, agent.getX()+SCREEN_UNIT/2, agent.getY()+SCREEN_UNIT/2)
                .transform(agent.getCoords(),0,rotatedCoords,0,3);

        for(int i =0; i<rotatedCoords.length;i++){
            polygon.addPoint(rotatedCoords[i].x,rotatedCoords[i].y);
        }

        return polygon;
    }

}