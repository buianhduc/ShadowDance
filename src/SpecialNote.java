import bagel.Input;
import bagel.Window;

/**
 *
 */
public abstract class SpecialNote extends NormalNote{
    private final int ACCEPTABLE_RADIUS = 50;
    private final int SCORE_ADDITION = 15;
    private final String message;
    public SpecialNote(String type, int frame, double xCoordinate, String message) {
        super(type, frame, xCoordinate);
        this.message = message;
    }

    /**
     * Render its special effect
     */
    public abstract void renderEffect();

    /**
     * Evaluate the scoring for special note
     *
     * @param triggered if the correct button has been pressed
     * @return note's been deactivated or not
     */
    public boolean evalSpecialScore(boolean triggered){
        double distance = Math.abs(STATIONARY_NOTE_Y - position.y);

        if(triggered){
            return distance <= ACCEPTABLE_RADIUS;
        } else if (position.y >= Window.getHeight()){
            this.deactivateNote();
        }

        return false;
    }
    @Override
    public void checkScore(Input input, Lane lane) {
        if (evalSpecialScore(lane.checkPressed(input))){
            Accuracy.setNewScore(SCORE_ADDITION);
            Accuracy.setNewMessage(this.message);
            renderEffect();
            this.deactivateNote();
        }
    }
}
