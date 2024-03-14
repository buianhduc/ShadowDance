import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Lane entity
 */
public class Lane extends GameObject {
    private final String type;
    public final ArrayList<Note> notes;
    private final int DEFAULT_Y_COORDINATE = 384;

    private int currentNote = 0;
    public Keys acceptedKey;

    public Lane(String type, int xCoordinate) {
        this.IMAGE = new Image("res/lane" + type + ".png");
        this.type = type;
        this.position = new Point(xCoordinate, DEFAULT_Y_COORDINATE);
        this.notes = new ArrayList<>();
        this.isBeingRendered = true;
        switch (type) {
            case "Up":
                acceptedKey = Keys.UP;
                break;
            case "Down":
                acceptedKey = Keys.DOWN;
                break;
            case "Left":
                acceptedKey = Keys.LEFT;
                break;
            case "Right":
                acceptedKey = Keys.RIGHT;
                break;
            case "Special":
                acceptedKey = Keys.SPACE;
                break;
        }
    }

    public void addNotes(Note note) {
        notes.add(note);
    }


    public void update(Input input, Level level) {
        // Draw lane image
        super.draw();
        int frameCount = level.getFrame();
        // Get notes
        for (Note i : notes) {
            if (!(i.isCompleted && i.isBeingRendered) && i.getFrame() == frameCount) {
                i.activateNote();
            }
            i.update(input, level);
        }

        if (currentNote < notes.size()){
            notes.get(currentNote).checkScore(input, this);
            if (notes.get(currentNote).isCompleted){
                currentNote++;
            }
        }
    }

    // Getters;
    public double getXCor() {
        return this.position.x;
    }
    public String getType(){
        return this.type;
    }

    /**
     * Remove notes that are active
     */
    public void removeCurrentNotes() {
        for(Note note : notes){
            if(note.isBeingRendered && !note.isCompleted){
                note.deactivateNote();
            }
        }
    }

    /**
     * Check for the game has ended
     * @return the game ended for not
     */
    public boolean checkEnded(){

        for(Note note : notes){
            if(!note.isCompleted) return false;
        }
        return true;
    }

    /**
     * reset lanes
     */
    public void reset(){
        currentNote = 0;
    }

    /**
     * Check for the accepted button has been pressed
     * @param input user's input
     * @return the accepted key has been pressed or not
     */
    public boolean checkPressed(Input input) {
        return input.wasPressed(acceptedKey);
    }

    /**
     * Check for the accepted button has been released
     * @param input user's input
     * @return the accepted key has been released or not
     */
    public boolean checkReleased(Input input) {
        return input.wasReleased(acceptedKey);
    }
}