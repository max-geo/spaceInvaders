package SpaceInvaders.models;

import javax.swing.ImageIcon;


/**
 * Class Player is a subclass of Model. It is used in Board.java 
 * when initializing the playing field.
 * 
 * Stores methods that dictate the alien's movement, 
 * image icons and intializes the aliens themselves and the bombs they shoot.
 */
public class Alien extends Model {

    private Bomb bomb;

    /**
     * Inititalizes the alien by calling the initAlien() method.
     * 
     * @param  x filled with ALIEN_INIT_X (variable found in Commons.java) 
     *     in Board.java when initializing the aliens. 
     * 
     * @param  y filled with ALIEN_INIT_Y (variable found in Commons.java) 
     *     in Board.java when initializing the aliens. 
     */
    public Alien(int x, int y) {
        initAlien(x, y);
    }

    private void initAlien(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);

        String alienImg = "src/images/alien.png";
        ImageIcon icon = new ImageIcon(alienImg);
        setImage(icon.getImage());
    }

    /**
     * The method dictates the movement of the player, depending on the variable direction,
     * which represents the increase/decrease in x-coordinate of the aliens.
     */
    public void act(int direction) {
        this.x += direction;
    }

    /**
     * Method used to return the bomb object.
     * 
     * @return bomb 
     */
    public Bomb getBomb() {
        return bomb;
    }

    /**
     * Class Bomb is a subclass of Model. 
     * It is used in Board.java when initializing the playing field.
     * Stores methods that dictate the bomb's image icons and intializes the bomb itself.
     */
    public class Bomb extends Model {

        private boolean destroyed;
         
        /**
         * Inititalizes the bomb by calling the initBomb() method.
         * 
         * @param  x repretsents the alien's x-coordinate, used as starting position for the bomb
         * @param  y repretsents the alien's y-coordinate, used as starting position for the bomb
         */
        public Bomb(int x, int y) {
            initBomb(x, y);
        }

        private void initBomb(int x, int y) {
            setDestroyed(true);

            this.x = x;
            this.y = y;

            String bombImg = "src/images/bomb.png";
            ImageIcon icon = new ImageIcon(bombImg);
            setImage(icon.getImage());
        }

        /**
         * Method used to set a bomb's status to destroyed.
         * 
         * @param  destroyed  boolean that is used to update object's "destroyed" variable
         */
        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }
        
        /**
         * Method used to see whether a bomb is destroyed or not.
         * 
         * @return destroyed
         */
        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
