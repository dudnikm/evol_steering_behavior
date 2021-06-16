import java.util.ArrayList;

public class Agent {

    private int x;
    private int y;
    private int targetX;
    private int targetY;

    public Agent(int x, int y){
        this.x = x;
        this.y = y;
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
}
