import bagel.Input;
import bagel.Window;

/**
 * Bomb note class
 */
public class BombNote extends NormalNote{
    private final String message = "Lane Cleared";
    private final double ACCEPTABLE_RADIUS = 50;
    public BombNote(int frame, double xCoordinate) {
        super("Bomb", frame, xCoordinate);
    }


    public void renderEffect(Lane lane) {
        lane.removeCurrentNotes();
    }

    private boolean evalSpecialScore(boolean triggered){
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
        // If the note is deactivated
        if (evalSpecialScore(lane.checkPressed(input))){
            Accuracy.setNewMessage(this.message);
            renderEffect(lane);
            this.deactivateNote();
        }
    }

}
