package SpaceInvaders.models;

import java.awt.Image;


/**
 * model keeps track of all possible models on a JPanel.
 * Other classes extend this class such that all functions 
 * of model are usable for objects in the JPanel.
 * 
 * the class exists of a lot of getters and setters.
 */
public class Model {

    private boolean visible;
    private Image image;
    private boolean hit;

    int x;
    int y;
    int dx;

    /**
     * initialises a model by making it visible.
     */
    public Model() {
        visible = true;
    }
    
    /**
     * makes models invisible.
     */
    public void delete() {
        visible = false;
    }
    
    /**
     * makes models visible.
     * 
     * @param visible is a boolean to set a model visible or invisible
     */
    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * checks if a model is visible.
     * 
     * @return visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * sets image of model on the JPanel.
     * 
     * @param image sets an image to this model.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * returns image of class extending model.
     * 
     * @return image
     */
    public Image getImage() {
        return image;
    }

    /**
     * set the X coordinate of a model.
     * 
     * @param x the x coordinate of a model.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * set the Y coordinate of a model.
     * 
     * @param y the y coordinate of a model.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * get the X coordinate of a model.
     * 
     * @return x 
     */
    public int getX() {
        return x;
    }

    /**
     * get the Y coordinate of a model.
     * 
     * @return y
     */
    public int getY() {
        return y;
    }


    /**
     * make a model dissapear.
     * 
     * @param destroyed boolean to set the current model to dying
     */
    public void setDying(boolean destroyed) {
        this.hit = destroyed;
    }

    /**
     * check if model is hit.
     * 
     * @return this.hit
     */
    public boolean isHit() {
        return this.hit;
    }
}
