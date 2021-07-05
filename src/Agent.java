import javax.vecmath.Vector2d;
import java.awt.*;
import java.util.ArrayList;

public class Agent {

    public final int MAX_SPEED = 6;
    public final double MAX_FORCE = 0.2;
    public final int AGENT_SIZE = 14;

    private Vector2d location;
    public Vector2d acceleration;
    public Vector2d velocity;
    public Vector2d desiredVelocity;
    public Vector2d steer;
    private Point[] coords;

    private int targetX;
    private int targetY;
    private Color color;
    private int health;

    public Agent(int x, int y){

        coords = new Point[3];
        coords[0] = new Point(x,y+AGENT_SIZE);
        coords[1] = new Point(x+AGENT_SIZE/2,y);
        coords[2] = new Point(x+AGENT_SIZE,y+AGENT_SIZE);

        location = new Vector2d(x,y);
        acceleration = new Vector2d(0,0);
        velocity = new Vector2d(0,-2);
        desiredVelocity = new Vector2d();
        steer = new Vector2d();
        this.color = Color.green;
        this.health = 4;
    }

    public double getX() {
        return location.getX();
    }

    public double getY() {
        return location.getY();
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }



    public Color getColor() {
        switch (health){
            case 1: this.color = Color.red; break;
            case 2: this.color = Color.orange; break;
            case 3: this.color = Color.yellow; break;
            case 4: this.color = Color.green; break;
        }
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void decreaseHealth(){
        if(health > 0)
            this.health -= 1;
    }

    public void increaseHealth(){
        this.health = 4;
    }

    public void seek(ArrayList<Food> foods){
        targetX = foods.get(0).getX();
        targetY = foods.get(0).getY();

        for (Food food: foods) {
            if((Math.pow(location.getX() - targetX,2) + Math.pow(location.getY() - targetY,2)) > (Math.pow(location.getX() - food.getX(),2) + Math.pow(location.getY() - food.getY(),2))){
                targetX = food.getX();
                targetY = food.getY();
            }
        }

        Vector2d target = new Vector2d(targetX,targetY);
        desiredVelocity.sub(target,location);
        desiredVelocity.normalize();
        desiredVelocity.scale(MAX_SPEED);


        steer.sub(desiredVelocity,velocity);
        System.out.printf("********************\nSteer: X = %f, Y = %f\n********************\n",steer.getX(),steer.getY());
        steer.clampMax(MAX_FORCE);
        System.out.printf("********************\nSteer: X = %f, Y = %f\n********************\n",steer.getX(),steer.getY());

        acceleration.add(steer);
    }

    public void move(){

        //Change coordinates
        coords[0].setLocation(location.x,location.y+AGENT_SIZE+5);
        coords[1].setLocation(location.x+AGENT_SIZE/2,location.y);
        coords[2].setLocation(location.x+AGENT_SIZE,location.y+AGENT_SIZE+5);

        velocity.add(acceleration);
        velocity.clampMax(MAX_SPEED);
        location.add(velocity);
        acceleration.set(new Vector2d(0,0));
    }

    public Point[] getCoords() {
        return coords;
    }
}
