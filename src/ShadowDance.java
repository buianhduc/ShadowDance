import bagel.*;

import java.util.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2023
 * Please enter your name below
 * @author Anh Duc Bui 1385532
 */
public class ShadowDance extends AbstractGame  {
    // Game setup
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final static String FONT = "res/FSO8BITR.TTF";
    private final static int MESSAGE_OFFSET_X = 100, MESSAGE_OFFSET_Y = 190;
    private Stages stage = Stages.START;

    // Game resources
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");

    //Game objects
    private final Message TITLE_MESSAGE = new Message(FONT, 64, GAME_TITLE, 220, 250),
                    START_DESC = new Message(FONT, 24, "SELECT LEVEL WITH NUMBER\nNUMBER KEY",
                            220+MESSAGE_OFFSET_X, 250+MESSAGE_OFFSET_Y),
                    LEVEL_OPTION = new Message(FONT, 24, "1     2     3",
                                    220+MESSAGE_OFFSET_X*2, 250+MESSAGE_OFFSET_Y*2);
    private final ResultScreen resultScreen = new ResultScreen();
    private static ArrayList<Level> levels;
    private static int level = 0;

    /**
     * Initialise the game
     */
    private static void initGame(){
        levels = new ArrayList<>();
        levels.add(null);
        levels.add(new Level(Stages.PLAYING_LVL1, "res/level1.csv", 150));
        levels.add(new Level(Stages.PLAYING_LVL2, "res/level2.csv", 400));
        levels.add(new Level(Stages.PLAYING_LVL3, "res/level3.csv", 350));
    }

    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        initGame();
        game.run();
    }

    /**
     * Get font name
     * @return font directory
     */
    public static String getFontName() {
        return FONT;
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        if (stage == Stages.START) {
            TITLE_MESSAGE.drawString();
            START_DESC.drawString();
            LEVEL_OPTION.drawString();
            // Selecting levels
            if (input.wasPressed(Keys.NUM_1)) {
                stage = Stages.PLAYING_LVL1;
                level = 1;
                levels.get(level).reset();
            }
            if (input.wasPressed(Keys.NUM_2)) {
                stage = Stages.PLAYING_LVL2;
                level = 2;
                levels.get(level).reset();
            }
            if (input.wasPressed(Keys.NUM_3)) {
                stage = Stages.PLAYING_LVL3;
                level = 3;
                levels.get(level).reset();
            }
        } else {
            // Evaluate the result screen
            if (stage == Stages.WIN) {
                resultScreen.setState(true);
                stage = resultScreen.update(input);
            } else if(stage == Stages.LOSE){
                resultScreen.setState(false);
                stage = resultScreen.update(input);
            } else {
                // If game hasn't ended, update
                stage = levels.get(level).update(input);
            }

            //When the game is reset
            if(stage == Stages.START){
                level = 0;
                levels.clear();
                initGame();
            }
        }
    }
}
