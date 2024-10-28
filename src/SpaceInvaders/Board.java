package SpaceInvaders;

import SpaceInvaders.models.Alien;
import SpaceInvaders.models.Bullet; 
import SpaceInvaders.models.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/** 
 * 
 * class board completely sets up the game and keeps track of the game  
 * 
 * it keeps track of the current difficulty and places aliens based
 *     on that difficulty.
 */
public class Board extends JPanel {

    private Dimension dim;
    private List<Alien> aliens;
    private Player player;
    private Bullet bullet;
    private Timer timer;
    private Timer timer2;

    private String explImg = "src/images/explosion.png";
    private String plyrImg = "src/images/player.png";
    private String alert = "Game Over!";

    private boolean inGame = true;

    private int direction = -1;
    private int deaths = 0;
    private int[] alienRow = {3, 4, 4, 5, 5};
    private int[] alienColumn = {5, 5, 6, 6, 7};

    private int difficulty = 3;
    private int startingLives = Commons.TOTAL_LIVES_INIT;
    private int lives = startingLives;
    private int scoreMod = (int) Math.round(100 / startingLives);
    private int score = 0;

    /**
    * board initialises a board with a player, aliens, bullets and bombs, 
    * although it does not draw the bullets and bombs initially.
    *
    * it keeps track of the current difficulty and places aliens based
    *     on that difficulty.
    */   
    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        dim = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setBackground(Color.black);

        //gameInit();
    }

    /**
     * update settings that can be altered in the options menu 
     * before the game starts, to take user input into account.
     * 
     * updates difficulty and lives
     * 
     * @param difficult the difficulty given by another program for the game.
     * @param lives the amount of starting lives given from another program.
     */
    public void updateBoard(int difficult, int lives) {
        this.difficulty = difficult;
        this.startingLives = lives;
        this.lives = lives;
        this.scoreMod = (int) Math.round(100 / startingLives);
    }
    
    /**
     * creates the game, starts the timer with an updated value gotten from 
     *     the slider in the menu.
     * 
     * this way, gameInit can take the difficulty slider into account.
     */
    public void startGame() {
        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();
        gameInit();
    }

    private void gameInit() {
        aliens = new ArrayList<>();
        for (int i = 0; i < alienRow[difficulty - 1]; i++) {
            
            for (int j = 0; j < alienColumn[difficulty - 1]; j++) {
                Alien alien = new Alien(Commons.ALIEN_INIT_X + 18 * j,
                        Commons.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }
        player = new Player();
        bullet = new Bullet();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGeneral(g);
        drawScore(g);
        drawLives(g);
    }

    private void drawGeneral(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, dim.width, dim.height);
        g.setColor(Color.green);

        if (lives > 0 && inGame) {
            g.drawLine(0, Commons.BOTTOM,
                    Commons.BOARD_WIDTH, Commons.BOTTOM);

            drawPlayer(g);
            drawbullet(g);
            drawAliens(g);
            drawBombs(g);
            drawLives(g);

        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawPlayer(Graphics g) {
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isHit()) {
            player.delete();
            inGame = false;
        }
        drawScore(g);
        drawLives(g);
    }

    private void drawbullet(Graphics g) {
        if (bullet.isVisible()) {
            g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
        }
    }
    
    private void drawAliens(Graphics g) {
        for (Alien alien : aliens) {

            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isHit()) {
                alien.delete();
            }
        }
    }

    private void drawBombs(Graphics g) {
        for (Alien a : aliens) {
            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    private void drawScore(Graphics g) {
        Font small = new Font("Serif", Font.BOLD, 14);
        String scoreS = "Score: " + String.valueOf(score);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(scoreS, 5, 20);
    }

    private void drawLives(Graphics g) {
        Font small = new Font("Serif", Font.BOLD, 14);
        String life = "Lives: " + String.valueOf(lives) + "/" + String.valueOf(startingLives);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(life, Commons.BOARD_WIDTH - 85, 20);
    }
    
    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }    
    
    private void doGameCycle() {
        update();
        repaint();
    }



    private void update() {
        if (deaths == Commons.LEFT_TO_DESTROY[difficulty - 1]) {
            inGame = false;
            timer.stop();
            alert = "Game won!";
        }

        player.move();

        // bullet and collision
        if (bullet.isVisible()) {
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();

            for (Alien alien : aliens) {
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && bullet.isVisible()) {
                    
                    if (bulletX >= (alienX)
                            && bulletX <= (alienX + Commons.ALIEN_WIDTH)
                            && bulletY >= (alienY)
                            && bulletY <= (alienY + Commons.ALIEN_HEIGHT)) {

                        ImageIcon icon = new ImageIcon(explImg);
                        alien.setImage(icon.getImage());
                        alien.setDying(true);
                        deaths++;
                        bullet.delete();
                        score = score + scoreMod;
                    }
                }
            }

            int y = bullet.getY();
            y -= 4;

            if (y < 0) {
                bullet.delete();
            } else {
                bullet.setY(y);
            }
        }

        // aliens and collision
        for (Alien alien : aliens) {
            int x = alien.getX();

            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {
                direction = -1;
                Iterator<Alien> i1 = aliens.iterator();

                while (i1.hasNext()) {
                    Alien a2 = i1.next();
                    a2.setY(a2.getY() + Commons.DOWN);
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != 1) {
                direction = 1;
                Iterator<Alien> i2 = aliens.iterator();

                while (i2.hasNext()) {
                    Alien a = i2.next();
                    a.setY(a.getY() + Commons.DOWN);
                }
            }
        }
        Iterator<Alien> it = aliens.iterator();

        while (it.hasNext()) {
            Alien alien = it.next();

            if (alien.isVisible()) {
                int y = alien.getY();

                if (y > Commons.BOTTOM - Commons.ALIEN_HEIGHT) {
                    inGame = false;
                    alert = "Invasion!";
                }
                alien.act(direction);
            }
        }

        // bombs and player collisions
        Random rand = new Random();

        for (Alien alien : aliens) {
            int bullet = rand.nextInt(15);
            Alien.Bomb bomb = alien.getBomb();

            if (bullet == Commons.HIT_CHANCE && alien.isVisible() && bomb.isDestroyed()) {
                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            //player and alien collisions
            if (player.isVisible() && alien.isVisible()) {
                if (lives > 1 && (playerX + Commons.PLAYER_WIDTH) >= alien.getX()
                            && playerY + Commons.PLAYER_HEIGHT >= (alien.getY())
                            && playerY <= (alien.getY() + Commons.ALIEN_HEIGHT)
                            && playerX <= alien.getX() + Commons.ALIEN_WIDTH) {
                    
                    ImageIcon icon = new ImageIcon(explImg);
                    alien.setImage(icon.getImage());
                    alien.setDying(true);
                    deaths++;
                    score = score + scoreMod;
                    lives--;
                    player.setImage(icon.getImage());

                    timer2 = new Timer(300, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ImageIcon icon = new ImageIcon(plyrImg);
                            player.setImage(icon.getImage());
                            timer2.stop();
                        }
                    });
                    timer2.start();
                    
                } else if (lives == 1 && (playerX + Commons.PLAYER_WIDTH) >= alien.getX()
                            && playerY + Commons.PLAYER_HEIGHT >= (alien.getY())
                            && playerY <= (alien.getY() + Commons.ALIEN_HEIGHT)
                            && playerX <= alien.getX() + Commons.ALIEN_WIDTH) {
                    
                    ImageIcon icon = new ImageIcon(explImg);
                    alien.setImage(icon.getImage());
                    alien.setDying(true);
                    deaths++;
                    score = score + scoreMod;
                    
                    player.setImage(icon.getImage());
                    player.setDying(true);
                    lives--;
                }

            }  

            if (player.isVisible() && !bomb.isDestroyed()) {
                if (lives > 1 && bombX >= (playerX)
                        && bombX <= (playerX + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {

                    ImageIcon icon = new ImageIcon(explImg);
                    player.setImage(icon.getImage());
                    bomb.setDestroyed(true);
                    lives--;
                    
                    timer2 = new Timer(300, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ImageIcon icon2 = new ImageIcon(plyrImg);
                            player.setImage(icon2.getImage());
                            timer2.stop();
                        }
                    });
                    timer2.start();
                
                }  else if (lives == 1 && bombX >= (playerX)
                        && bombX <= (playerX + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {

                    ImageIcon icon = new ImageIcon(explImg);
                    player.setImage(icon.getImage());
                    player.setDying(true);
                    bomb.setDestroyed(true);
                    lives--;           
                }
            }

            if (!bomb.isDestroyed()) {
                bomb.setY(bomb.getY() + 1);

                if (bomb.getY() >= Commons.BOTTOM - Commons.BOMB_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
            int x = player.getX();
            int y = player.getY();
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {
                if (inGame) {
                    if (!bullet.isVisible()) {
                        bullet = new Bullet(x, y);
                    }
                }
            }
        }
    }
    
    private void gameOver(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        Font small = new Font("Serif", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(alert, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(alert)) / 2,
                Commons.BOARD_WIDTH / 2);
    }
}
