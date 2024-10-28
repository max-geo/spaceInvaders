package SpaceInvaders.models;

import javax.swing.ImageIcon;

/**
 * public class Shot is a subclass of Model. It is used in Board.java when initializing 
 *     the playing field.
 * Stores methods that dictate the bullet's image icons and intializes the bullet itself.
 */
public class Bullet extends Model {

    public Bullet() {
    }

    /**
    * bullet initialises the icon and position of the bullet.
    */
    public Bullet(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        String bulletImg = "src/images/bullet.png";
        ImageIcon icon = new ImageIcon(bulletImg);
        setImage(icon.getImage());

        int hSpace = 6;
        setX(x + hSpace);

        int vSpace = 1;
        setY(y - vSpace);
    }
}
