import bagel.Font;
import bagel.Window;

import java.util.ArrayList;

/**
 * Class managing score-related entities
 */
public class Accuracy {

    // Game resources and scoring logics
    private static Font font = new Font(ShadowDance.getFontName(), 40);
    private static int remainingFrame = 30;
    private static String currentMessage = null;
    private static int multiplying = 1;
    public static int currentScore = 0;

    // Store the remaining frame for the double score effect
    private static ArrayList<Integer> doubleScoreEffect = new ArrayList<>();

    // Adapted from Project 1 Solution
    public static final int PERFECT_SCORE = 10;
    public static final int GOOD_SCORE = 5;
    public static final int BAD_SCORE = -1;
    public static final int MISS_SCORE = -5;
    public static final int NOT_SCORED = 0;
    public static final String PERFECT = "PERFECT";
    public static final String GOOD = "GOOD";
    public static final String BAD = "BAD";
    public static final String MISS = "MISS";
    public static final int PERFECT_RADIUS = 15;
    public static final int GOOD_RADIUS = 50;
    public static final int BAD_RADIUS = 100;
    public static final int MISS_RADIUS = 200;

    /**
     * Update the scoring and message (if exists)
     */
    public static void update(){
        // Drawing the message at the centre of the window
        if(remainingFrame > 0 && currentMessage != null){
            remainingFrame--;
            font.drawString(currentMessage,
                    (double) Window.getWidth() /2 - font.getWidth(currentMessage)/2,
                    (double) Window.getHeight() /2);
        } else {
            currentMessage = null;
        }

        // Searching for double score effect, keeping track of its remaining frame
        // and multiplier
        for(int i = 0; i < doubleScoreEffect.size(); i++){
            if(doubleScoreEffect.get(i) == 0){
                multiplying /= 2;
            }
            doubleScoreEffect.set(i, doubleScoreEffect.get(i)-1);
        }
    }

    /**
     * Evalute score based on note's distance to stationary note
     * Adapted from: Project 1's solution
     * @param statNote stationary note of a lane
     * @param targetHeight  the current y position of a note
     * @param triggered evaluate the keyboard input to see if it's triggered
     * @return score evaluation
     */
    public static int evalScore(double statNote, double targetHeight, boolean triggered){
        double distance = Math.abs(targetHeight - statNote);
        if (triggered){
            if (distance <= PERFECT_RADIUS){
                setNewMessage(Accuracy.PERFECT);
                return Accuracy.PERFECT_SCORE;
            } else if (distance <= GOOD_RADIUS){
                setNewMessage(Accuracy.GOOD);
                return Accuracy.GOOD_SCORE;
            } else if (distance <= BAD_RADIUS){
                setNewMessage(Accuracy.BAD);
                return Accuracy.BAD_SCORE;
            } else if (distance <= MISS_RADIUS){
                setNewMessage(Accuracy.MISS);
                return Accuracy.MISS_SCORE;
            }
        } else if (targetHeight >= (Window.getHeight())){
            setNewMessage(Accuracy.MISS);
            return Accuracy.MISS_SCORE;
        }

        return Accuracy.NOT_SCORED;
    }


    /**
     * Set new message for the background
     * @param message
     */
    public static void setNewMessage(String message){
        remainingFrame = 30;
        currentMessage = message;
        Accuracy.update();
    }

    /**
     * set new score for the game
     * @param scoreAddition
     */
    public static void setNewScore(int scoreAddition){
        Accuracy.currentScore += scoreAddition*multiplying;
    }

    /**
     * set multiplier for the score
     * @param multiplying multiplier value
     * @param frames remaining frame for that effect
     */
    public static void setMultiplying(int multiplying, int frames){
        Accuracy.multiplying *= multiplying;
        doubleScoreEffect.add(frames);
    }

    /**
     * reset scoring to default
     */
    public static void resetScore(){
        currentMessage = null;
        currentScore = 0;
        remainingFrame = 30;
        multiplying = 1;
        doubleScoreEffect.clear();
    }
}
