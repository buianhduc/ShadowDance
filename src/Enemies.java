import bagel.Input;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Wrapper class for a list of enemies
 */
public class Enemies{
    private final ArrayList<Enemy> enemies;

    public Enemies(){
        enemies = new ArrayList<>();
    }

    /**
     * Get the nearest enemy relatively to the guardianPosition
     *
     * @param guardianPosition guardian position
     * @return the nearest enemy, default (0,0)
     */
    public Point getNearestEnemy(Point guardianPosition){
        double minDistance = 99999;
        Point result = new Point(0,0);
        for(Enemy enemy : enemies){
            if(minDistance > guardianPosition.distanceTo(enemy.position) && enemy.isBeingRendered){
                minDistance = guardianPosition.distanceTo(enemy.position);
                result = enemy.position;
            }
        }
        return result;
    }

    /**
     * Add enemy to the game
     */
    public void addEnemy(){
        enemies.add(new Enemy());
    }

    /**
     * Individually update the enemy positions
     * @param input game input
     * @param level current level
     */
    public void update(Input input, Level level){
        for(Enemy enemy : enemies){
            enemy.update(input, level);
        }
    }

    /**
     * Get the list of enemies
     * @return ArrayList of enemies
     */
    public ArrayList<Enemy> getList(){
        return enemies;
    }
}
