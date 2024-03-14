import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

public abstract class Note extends GameObject{
    private final int appearFromFrame;
    public boolean isCompleted = false;
    protected static int speed = 2;
    protected final int STATIONARY_NOTE_Y = 657;


    public Note(int frame, double xCoordinate, double yCoordinate){
        this.appearFromFrame = frame;
        this.position = new Point(xCoordinate, yCoordinate);
        this.isBeingRendered = false;

    }

    /**
     * Note's activity toggles
     */
    public void activateNote(){
        this.isBeingRendered = true;
        this.isCompleted = false;
    }
    public void deactivateNote(){
        this.isBeingRendered = false;
        this.isCompleted = true;
    }


    protected static void changeSpeedBy(int newSpeed){
        if (speed + newSpeed > 0) speed += newSpeed;
    }
    public int getFrame(){
        return this.appearFromFrame;
    }

    /**
     * Check for scoring of that note, deactivate upon score change
     * @param input user input
     * @param lane lane that this note is contained in
     */
    public abstract void checkScore(Input input, Lane lane);

    // Check if the note is active (is being rendered and has not completed)
    public boolean isActive() {
        return !isCompleted && isBeingRendered;
    }

    public static void reset(){
        speed = 2;
    }
}
