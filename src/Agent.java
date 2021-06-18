import java.awt.*;
import java.util.ArrayList;

public class Agent {

    public final int MAX_SPEED = 10;
    public final int ACCELERATION = 1;

    private int x;
    private int y;
    private int targetX;
    private int targetY;
    private int speed;
    private Color color;
    private int health;

    public Agent(int x, int y){
        this.x = x;
        this.y = y;
        this.speed = 0;
        this.color = Color.green;
        this.health = 30;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setCoords(int x, int y){
        this.x = x;
        this.y = y;
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
        if(health < 4)
            this.health += 1;
    }

    public void seek(ArrayList<Food> foods){
        targetX = foods.get(0).getX();
        targetY = foods.get(0).getY();

        for (Food food: foods) {
            if((Math.pow(this.x - targetX,2) + Math.pow(this.y - targetY,2)) > (Math.pow(this.x - food.getX(),2) + Math.pow(this.y - food.getY(),2))){
                targetX = food.getX();
                targetY = food.getY();
            }
        }
    }

    public void move(){
        if(speed < MAX_SPEED)
            speed += ACCELERATION;
        if(speed > MAX_SPEED)
            speed = MAX_SPEED;
    }
}
