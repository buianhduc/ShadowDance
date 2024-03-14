/**
 * Class for speed up note
 */
public class SpeedUp extends SpecialNote{
    public SpeedUp(int frame, double xCoordinate){
        super("SpeedUp", frame, xCoordinate, "Speed Up");
    }
    @Override
    public void renderEffect() {
        /* TODO: Magic number */
        Note.changeSpeedBy(2);
    }
}
