import javax.vecmath.Vector2d;
import java.awt.*;
import java.util.ArrayList;

public class Agent {

    public final int MAX_SPEED = 10;
    public final double MAX_FORCE = 0.1;

    private Vector2d location;
    private Vector2d acceleration;
    public Vector2d velocity;
    private Vector2d desiredVelocity;
    Vector2d steer;

    private int targetX;
    private int targetY;
    private Color color;
    private int health;

    public Agent(int x, int y){
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
        //steer.clampMax(MAX_FORCE);

        acceleration.add(steer);
    }

    public void move(){
        velocity.add(acceleration);
        velocity.clampMax(MAX_SPEED);
        location.add(velocity);
        acceleration.set(new Vector2d());
    }

    public double getAngle(){
        return velocity.angle(desiredVelocity);
    }
}
