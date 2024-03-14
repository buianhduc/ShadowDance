import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

public class Guardian extends GameObject{
    private final ArrayList<Projectile> arrows = new ArrayList<>();
    public Guardian(){
        this.position = new Point(800,600);
        this.IMAGE = new Image("res/guardian.PNG");
        this.isBeingRendered = true;
    }
    /**
     * Update movement, appearance etc. based on user input
     *
     * @param input user's input
     * @param level current level
     */
    @Override

    public void update(Input input, Level level) {
        Enemies enemies = level.getEnemies();
        this.draw();

        // Update movement for the arrow
        for(Projectile arrow : arrows){
            if (arrow.isActive()){
                arrow.update(input, level);
            }
        }

        // Check for left shift key pressed to shoot the arrow
        if(input.wasPressed(Keys.LEFT_SHIFT)){
            shoot(level.getEnemies());
        }

        // Deactivate enemy if the arrow hits
        for(Projectile arrow: arrows){
            for(Enemy enemy : enemies.getList()){
                if(arrow.isCollided(enemy)){
                    enemy.isBeingRendered = false;

                }
            }

        }
    }

    /**
     * Shoot an arrow at the nearest enemy
     * @param enemies
     */
    private void shoot(Enemies enemies){
        Point nearestEnemy = enemies.getNearestEnemy(this.position);
        double a = nearestEnemy.y - this.position.y;
        double b = nearestEnemy.x - this.position.x;
        Vector2 vec = new Vector2(a,b);

        // Calculate the angle between enemy and the guardian
        double direction = Math.atan2(vec.normalised().x,vec.normalised().y);
        arrows.add(new Projectile(direction));
    }


}
