import bagel.Image;
import bagel.Input;
import bagel.util.Point;

/**
 * Class contains normal note's logic
 */
public class NormalNote extends Note{

    public NormalNote(String typeName, int frame, double xCoordinate){
        super(frame, xCoordinate, 100);
        String imageDir = "res/note" + typeName +".png";
        this.IMAGE = new Image(imageDir);
    }


    @Override
    public void checkScore(Input input, Lane lane) {
        int newScore = Accuracy.evalScore(STATIONARY_NOTE_Y, position.y, lane.checkPressed(input));
        if (newScore != Accuracy.NOT_SCORED){
            this.deactivateNote();
            Accuracy.setNewScore(newScore);
        }
    }

    /**
     * Update logic
     * @param input user's input
     * @param level current level
     */
    @Override
    public void update(Input input, Level level) {
        // Draw note
        if (isBeingRendered && !isCompleted){
            this.position = new Point(position.x, position.y + speed);
            this.draw();
        }

    }
}
