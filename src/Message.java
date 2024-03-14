import bagel.Font;
import bagel.Window;
// Adapted from Anh Duc Bui's Project 1
public class Message extends Font{
    private String message;
    private final int size;
    int corX, corY;

    /**
     * Create message - custom size and Y coordinate, horizontally centered
     *
     * @param fontFile directory to font
     * @param size     font size
     * @param message  custom message
     * @param corY     Y coordinate
     * @param corX
     */
    public Message(String fontFile, int size, String message, int corX, int corY) {
        super(fontFile, size);
        this.message = message;
        this.size = size;
        this.corY = corY;
        this.corX = corX;
    }

    /**
     * Create message - using custom size, but screen-centered
     * @param fontName
     * directory to font
     * @param size
     * font size
     * @param message
     * custom message
     */
    public Message(String fontName, int size, String message) {
        super(fontName, size);
        this.message = message;
        this.size = size;
    }

    /**
     * Create message with default font (64px), screen-centered
     * @param fontName
     * directory to font
     * @param message
     * custom message
     */
    public Message(String fontName, String message) {
        super(fontName, 64);
        this.message = message;
        this.size = 64;
    }


    /**
     * Draw message on the screen, centered;
     */
    public void drawStringCentered() {
        double corX = (Window.getWidth() - this.getWidth(message))/2.0;
        double corY = (Window.getHeight() + this.size)/2.0;
        super.drawString(message, corX, corY);
    }

    /**
     * Draw string using custom corX, corY (depends on constructor)
     */
    public void drawString(){
        super.drawString(message, this.corX, this.corY);
    }
    public void setMessage(String newMessage){
        this.message = newMessage;
    }

}

