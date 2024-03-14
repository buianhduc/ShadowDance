import bagel.Image;
import bagel.Input;
import bagel.util.Point;

/**
 * Hold note class
 */
public class HoldNote extends Note{
    private boolean holdStarted = false;
    private final double HOLD_NOTE_OFFSET = 82;
    public HoldNote(String type, int frame, double xCoordinate){
        super(frame, xCoordinate, 24);
        this.IMAGE = new Image("res/holdNote"+type+".png");
    }
    /**
     * Update movement, appearance etc. based on user input
     *
     * @param input user's input
     * @param level current level
     */
    @Override
    public void update(Input input, Level level) {
        // Draw note
        if (isBeingRendered && !isCompleted){
            this.position = new Point(this.position.x, this.position.y + speed);
            this.draw();
        }

    }

    @Override
    public void checkScore(Input input, Lane lane) {
        if (!isCompleted){
            if (!holdStarted){
                int score = Accuracy.evalScore(STATIONARY_NOTE_Y, this.position.y+ HOLD_NOTE_OFFSET, lane.checkPressed(input));
                if (score == Accuracy.MISS_SCORE) {
                    Accuracy.setNewScore(score);
                    deactivateNote();
                } else if (score != Accuracy.NOT_SCORED) {
                    holdStarted = true;
                    Accuracy.setNewScore(score);
                }
            } else {
                int score = Accuracy.evalScore(STATIONARY_NOTE_Y, this.position.y- HOLD_NOTE_OFFSET, lane.checkReleased(input));
                if (score != Accuracy.NOT_SCORED){
                    deactivateNote();
                    Accuracy.setNewScore(score);
                } else if (lane.checkReleased(input)) {
                    Accuracy.setNewScore(Accuracy.MISS_SCORE);
                    Accuracy.setNewMessage(Accuracy.MISS);
                    deactivateNote();
                }
            }
        }
    }


}
