import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;

public class Projectile extends GameObject implements Collidable{
    private boolean isActive;
    private double direction;
    private final DrawOptions drawOption = new DrawOptions();
    public Projectile(double direction){
        this.IMAGE = new Image("res/arrow.PNG");
        this.position = new Point(800,600);
        this.isActive = true;
        this.speed = 6;
        this.isBeingRendered = true;
        this.direction = direction;
    }

    /**
     * Update movement, appearance etc. based on user input
     *
     * @param input user's input
     * @param level current level
     */
    @Override
    public void update(Input input, Level level) {
        drawOption.setRotation(direction);
        this.draw(drawOption);
        int x = (int) (speed*Math.cos(direction)),
                y = (int) (speed*Math.sin(direction));
        this.position = new Point(this.position.x + x,this.position.y + y);
    }

    public boolean isActive() {
        return isActive;
    }

    private void draw(DrawOptions drawOption){
        if(isBeingRendered){
            this.IMAGE.draw(this.position.x, this.position.y, drawOption);
        }
    }

    @Override
    public boolean isCollided(GameObject gameObject) {
        if(gameObject instanceof Enemy){
            return this.position.distanceTo(gameObject.position) <= 62;
        }
        return false;
    }
}
