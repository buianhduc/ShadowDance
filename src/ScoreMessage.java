import bagel.Font;

public class ScoreMessage {
    private static final int corX = 30;
    private static final int corY = 30;
    private static final String message = "Score ";
    private static final Font SCORE_FONT = new Font("res/FSO8BITR.TTF", 30);

    public static void drawScore(){
        SCORE_FONT.drawString(message+Accuracy.currentScore, corX, corY);
    }

}
