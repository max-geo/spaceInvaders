package SpaceInvaders.models;

import SpaceInvaders.Commons;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;


/**
 * Class Player is a subclass of Model. It is used in Board.java 
 *     when initializing the playing field.
 * Stores methods that dictate the player's movement, image icons and intializes the player itself.
 */
public class Player extends Model {

    private int width;

    /**
     * Inititalizes player.
     * 
     * Player() calls the initPlayer() function, which actually initializes the player.
     */
    public Player() {
        initPlayer();
    }

    private void initPlayer() {
        String playerImg = "src/images/player.png";
        ImageIcon icon = new ImageIcon((playerImg));

        width = icon.getImage().getWidth(null);
        setImage(icon.getImage());

        int startX = 270;
        setX(startX);

        int startY = 280;
        setY(startY);
    }

    /**
     * The method dictates the movement of the player, depending on the variable dx, 
     *     which represents the increase/decrease in x-coordinate of the player.
     * 
     * Ensures that the player cannot exceed board's bounds
     */
    public void move() {
        x += dx;

        if (x <= 2) {
            x = 2;
        }
        if (x >= Commons.BOARD_WIDTH - 2 * width) {
            x = Commons.BOARD_WIDTH - 2 * width;
        }
    }
    
    /**
     * The method converts user's input to in-game player movement. 
     * Left key sets the decrease of the x-coordinate's value to -2, 
     * essentially moving the player to the left. Right key has the same
     * function, but instead increases dx, moving the player to the right.
     * 
     * @param  e event is generated when a key is pressed
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
    }

    /**
     * When the user stops pressing a button which is the left arrow key
     * or the right arrow key, the ship has to stop moving.
     * 
     * @param  e event is generated when a key is pressed
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
