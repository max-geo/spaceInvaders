package SpaceInvaders;

/**
 * This file contains all constants for the files in this package.
 * 
 * public interface Commons contains the variables of the board, alien spawning positions,
 * player movement, player placement and for the sliders used in the options before starting.
 */

public interface Commons {
    int BOARD_WIDTH = 358;
    int BOARD_HEIGHT = 350;
    int BORDER_RIGHT = 30;
    int BORDER_LEFT = 5;
    
    int MENU_HEIGHT = 300;
    int MENU_WIDTH = 300; 

    int BOTTOM = 290;
    int BOMB_HEIGHT = 5;

    int ALIEN_HEIGHT = 12;
    int ALIEN_WIDTH = 12;
    int ALIEN_INIT_X = 150;
    int ALIEN_INIT_Y = 23;

    int DOWN = 15;
    int HIT_CHANCE = 5;
    int DELAY = 17;
    int PLAYER_WIDTH = 15;
    int PLAYER_HEIGHT = 10;

    int SLIDER_PRESET_MIN = 1;
    int SLIDER_PRESET_MAX = 5;
    int SLIDER_PRESET_INIT = 3;
    int[] LEFT_TO_DESTROY = {15, 20, 24, 30, 35};

    int TOTAL_LIVES_INIT = 1;
}

