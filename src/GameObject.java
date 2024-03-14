import bagel.Input;
import bagel.util.Point;
import bagel.Image;


public abstract class GameObject {
    protected Point position;
    protected Image IMAGE;
    public boolean isBeingRendered;
    protected int speed;

    /**
     * Update movement, appearance etc. based on user input
     *
     * @param input user's input
     * @param level current level
     */
    public abstract void update(Input input, Level level);

    /**
     * Draw object on the screen, at position given by object
     */
    public void draw(){
        if(this.isBeingRendered){
            IMAGE.draw(position.x, position.y);
        }
    }
}
