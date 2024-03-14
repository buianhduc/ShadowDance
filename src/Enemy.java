import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Class for enemy's logic
 */

public class Enemy extends GameObject implements Collidable{

    public Enemy(){
        int x = getRandomInt(100, 900),
                y = getRandomInt(100, 500);
        this.position = new Point(x,y);
        this.IMAGE = new Image("res/enemy.PNG");
        this.isBeingRendered = true;

        // get the a random direction for the enemy movement
        this.speed = (getRandomInt(0, 1) == 0) ? 1 : -1;
    }

    /**
     * Update movement, appearance etc. based on user input
     *
     * @param input user's input
     * @param level current level
     */
    @Override
    public void update(Input input, Level level) {
        if(isBeingRendered){
            this.draw();

            // If reached the border of the window
            if(abs(this.position.x + speed) >= Window.getWidth() || abs(this.position.x + speed) <= 0){
                speed = -speed;
            }

            // Steal notes if it collided with one
            ArrayList<Lane> lanes = level.getLanes();
            this.position = new Point(this.position.x + speed, this.position.y);
            for(Lane lane : lanes){
                lane.notes.forEach(note -> {
                    if(note.isActive() && this.isCollided(note)){
                        note.deactivateNote();
                    }
                });
            }
        }

    }

    @Override
    public boolean isCollided(GameObject gameObject) {
        if(gameObject instanceof Note){
            return this.position.distanceTo(gameObject.position) <= 104;
        }
        return false;
    }

    /**
     * Get a random integer number
     * @param lower lower bound
     * @param upper upper bound
     * @return the random number
     */
    private int getRandomInt(int lower, int upper){
        Random random = new Random();
        return random.nextInt(upper + 1 - lower) + lower;
    }

}
