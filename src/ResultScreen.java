import bagel.Font;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

public class ResultScreen {
    private final int WINDOW_WIDTH = Window.getWidth(), WINDOW_HEIGHT = Window.getHeight();
    private final Font TITLE_FONT = new Font("res/FSO8BITR.TTF", 64);
    private final Font INSTRUCTION_FONT = new Font("res/FSO8BITR.TTF", 24);

    private final String winMessage = "CLEAR!",
            loseMessage = "TRY AGAIN";
    private final String instruction = "PRESS SPACE TO RETURN TO LEVEL SELECTION";
    private Stages stage = null;

    public void setState(boolean isWon){
        if(isWon) stage = Stages.WIN;
        else stage = Stages.LOSE;
    }
    public Stages update(Input input){
        if(stage != null){
            if(stage == Stages.WIN){
                TITLE_FONT.drawString(winMessage,
                        WINDOW_WIDTH/2 - TITLE_FONT.getWidth(winMessage)/2,
                        WINDOW_HEIGHT/2);
                INSTRUCTION_FONT.drawString(instruction,
                        WINDOW_WIDTH/2 - INSTRUCTION_FONT.getWidth(instruction)/2,
                        500);
            } else if(stage == Stages.LOSE){
                TITLE_FONT.drawString(loseMessage,
                        WINDOW_WIDTH/2 - TITLE_FONT.getWidth(loseMessage)/2,
                        WINDOW_HEIGHT/2);
                INSTRUCTION_FONT.drawString(instruction,
                        WINDOW_WIDTH/2 - INSTRUCTION_FONT.getWidth(instruction)/2,
                        500);
            }
            if(input.wasPressed(Keys.SPACE)){
                return Stages.START;
            }
            return this.stage;
        }
        return Stages.PLAYING;
    }
}
