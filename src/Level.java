import bagel.Input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


/**
 * Class contains a level configuration and logic
 */
public class Level {
    // Level configurations
    private final String dataFileDir;
    private final int winCondition;
    private boolean isPlaying = false;
    private int frameCount;

    // Level entities
    private final ArrayList<Lane> lanes = new ArrayList<>();
    private Enemies enemies = null;
    private Guardian guardian = null;
    private final Stages levelStage;

    public Level(Stages levelStage, String levelDataFile, int winCondition){
        this.levelStage = levelStage;
        // For level 3, add enemies and guardians
        if (levelStage == Stages.PLAYING_LVL3){
            enemies = new Enemies();
            guardian = new Guardian();
        }
        this.winCondition = winCondition;
        this.dataFileDir = levelDataFile;
        frameCount = 0;
    }

    /**
     * Read level data from csv
     * @param csvFile csv file's directory
     */
    private void readCSV(String csvFile) {

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while((line = br.readLine()) != null){
                String[] splitFields = line.split(",");
                String type = splitFields[0];

                // Add lanes to the game
                // <Lane>,<Orientation>,<XCor>
                if (type.equals("Lane")) {
                    lanes.add(new Lane(splitFields[1], Integer.parseInt(splitFields[2])));
                } else {
                    // Add notes to its relative lane
                    //CSV Field: <Lane>, <noteType>, <frameNum>
                    String laneName = splitFields[0];
                    String noteType = splitFields[1];
                    int frame = Integer.parseInt(splitFields[2]);

                    lanes.forEach(lane -> {
                        double laneXCor = lane.getXCor();
                        if (lane.getType().equals(laneName)){
                            switch (noteType) {
                                case "Normal":
                                    lane.addNotes(new NormalNote(laneName, frame, laneXCor));
                                    break;
                                case "Hold":
                                    lane.addNotes(new HoldNote(laneName, frame, laneXCor));
                                    break;
                                case "Bomb":
                                    lane.addNotes(new BombNote(frame, laneXCor));
                                    break;
                                case "SpeedUp":
                                    lane.addNotes(new SpeedUp(frame, laneXCor));
                                    break;
                                case "SlowDown":
                                    lane.addNotes(new SlowDown(frame, laneXCor));
                                    break;
                                case "DoubleScore":
                                    lane.addNotes(new DoubleScore(frame, laneXCor));
                                    break;
                            }
                        }
                    });
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Checking if the game has ended
     * @return game has ended or note
     */
    private boolean checkGameEnded(){
        int laneEnded = 0;
        for(Lane lane : lanes){
            if(lane.checkEnded()) laneEnded++;
        }
        return laneEnded == lanes.size();
    }

    /**
     * Update for each frame of the game
     * @param input user's input
     * @return the stage that the level is at
     */
    public Stages update(Input input){
        if(isPlaying){
            frameCount++;

            for (Lane lane: lanes) {
                lane.update(input, this);
            }
            Accuracy.update();
            ScoreMessage.drawScore();

            // for level 3 only
            if(guardian != null) guardian.update(input, this);
            if (enemies != null) {
                if(frameCount % 600 == 0) enemies.addEnemy();
                enemies.update(input, this);
            }

            // Checking to see if the game's ended
            if(!checkGameEnded()){
                return Stages.PLAYING;
            }

            return evaluate();
        }
        return levelStage;
    }

    /**
     * Evaluate the result
     * @return the result stage
     */
    private Stages evaluate(){
        isPlaying = false;

        if (Accuracy.currentScore > winCondition) {
            return Stages.WIN;
        }
        return Stages.LOSE;
    }

    /**
     * Reset stage to default values
     */
    public void reset() {
        // Clear modifications from previous game
        Accuracy.resetScore();
        lanes.clear();
        Note.reset();

        // Read the data again
        readCSV(dataFileDir);
        lanes.forEach(Lane::reset);
        isPlaying = true;
    }

    // Getters
    public ArrayList<Lane> getLanes() {
        return lanes;
    }

    public Enemies getEnemies() {
        return enemies;
    }

    public int getFrame() {
        return frameCount;
    }
}
