import bagel.Image;

/**
 * Double score class
 */

public class DoubleScore extends SpecialNote{
    public DoubleScore(int frame, double xCoordinate) {
        super("2x", frame, xCoordinate, "Double Score");
        this.IMAGE = new Image("res/note2x.PNG");
    }

    @Override
    public void renderEffect() {
        Accuracy.setMultiplying(2, 480);
    }

}
