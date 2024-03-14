import bagel.Image;

public class SlowDown extends SpecialNote{
    public SlowDown(int frame, double xCoordinate){
        super("SlowDown",frame, xCoordinate, "Slow Down");
    }
    @Override
    public void renderEffect() {
        Note.changeSpeedBy(-1);
    }
}
