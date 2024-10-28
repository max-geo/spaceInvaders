package SpaceInvaders;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



/**
 * This file contains the menus and the general logic of making those menus, as well as 
 * the master file running board and commons.
 * 
 * public class SpaceInvaders is our entire class in which we run the game with menu and all,
 * public SpaceInvaders then first initialises the UI where you can choose your own settings,
 * and when you are ready you can click play game. 
 * 
 * the only  
 */
public class SpaceInvaders extends JFrame implements ChangeListener {

    int currentDifficulty = Commons.SLIDER_PRESET_INIT;
    int currentLives = Commons.TOTAL_LIVES_INIT;

    private int vGap = 80;
    private int hGap = 6;
    
    /**
     * initialises all options before starting the game,
     * interacting with the menu created will alter certain variables.
     */
    public SpaceInvaders() {
        initOptions();
        //wait until play is clicked in initUIMenu, then initUIgame.
        //initUIGame();
    }
    
    /**
     * initialises the options panel where the user chooses their difficulty.
     * difficulty is based on a scale of 1 to 5, where 1 is easy and 5 is hard and can
     * be determined by the slider.
     * 
     */
    private void initOptions() { 
        SwingUtilities.invokeLater(() -> {
            
            JFrame frame = new JFrame("Space invaders");
            
            JPanel panelDifficulty = new JPanel(new BorderLayout());
            JPanel panelLives = new JPanel(new BorderLayout(hGap, vGap));
      
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Difficulty", panelDifficulty);
            tabbedPane.addTab("Lives", panelLives);
            frame.getContentPane().add(tabbedPane);

            //add background colour for both panels
            Color white = new Color(240, 240, 240);
            panelDifficulty.setBackground(white);
            panelLives.setBackground(white);
            


            //add label for options panel
            JLabel labelOpt = new JLabel("Choose your difficulty", 
                JLabel.CENTER);
            labelOpt.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelDifficulty.add(labelOpt, BorderLayout.NORTH);

            //add slider for options panel
            JSlider difficulty = new JSlider(JSlider.HORIZONTAL, Commons.SLIDER_PRESET_MIN, 
                Commons.SLIDER_PRESET_MAX, Commons.SLIDER_PRESET_INIT);
            Font font = new Font("Serif", Font.ITALIC, 15); 

            //Create the label table
            Hashtable<Integer, JLabel> difficultyTable = new Hashtable<Integer, JLabel>();
            difficultyTable.put(1, new JLabel("easy"));
            difficultyTable.put(3, new JLabel("medium"));
            difficultyTable.put(5, new JLabel("hard"));
            difficulty.setLabelTable(difficultyTable);

            difficulty.addChangeListener(this);
            difficulty.setMajorTickSpacing(1);
            difficulty.setPaintTicks(true);
            difficulty.setPaintLabels(true);
            difficulty.setSnapToTicks(true);
            difficulty.setFont(font);
            panelDifficulty.add(difficulty);

            //add button to play game on options panels
            JButton button = new JButton("play game");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    initUIGame();
                    frame.setVisible(false);
                }
            }); 
            panelDifficulty.add(button, BorderLayout.SOUTH);



            //add labels for lives panel
            JLabel livesTitle = new JLabel("starting lives:");
            panelLives.add(livesTitle, BorderLayout.WEST);

            JLabel livesOpt = new JLabel("choose amount of starting lives ", 
                    JLabel.CENTER);
            labelOpt.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelLives.add(livesOpt, BorderLayout.NORTH);
            
            JLabel extra = new JLabel(" increase lives = less points");
            panelLives.add(extra, BorderLayout.EAST);

            //add spinner for lives panel
            SpinnerModel livesModel = new SpinnerNumberModel(Commons.TOTAL_LIVES_INIT, 
                Commons.TOTAL_LIVES_INIT - 0, Commons.TOTAL_LIVES_INIT + 8, 1);
            JSpinner spinner = new JSpinner(livesModel);
            
            spinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                        int value = (int) spinner.getValue();
                        currentLives = value;
                }
            });
            panelLives.add(spinner, BorderLayout.CENTER);
            
            JButton button2 = new JButton("play game");
            button2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    initUIGame();
                    frame.setVisible(false);
                }
            }); 
            panelLives.add(button2, BorderLayout.SOUTH);

            //setup of the board
            frame.setSize(Commons.MENU_WIDTH, Commons.MENU_HEIGHT);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
        });
    }

    /**
     * stateChanged keeps track of the slider's current state and updates
     * local variable currentDifficulty when the value changes.
     * 
    */
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            currentDifficulty = (int) source.getValue();
        }
    }

    private void initUIGame() {
        Board board = new Board();

        //update last known difficulty and then add to JFrame
        board.updateBoard(currentDifficulty, currentLives);
        board.startGame();
        add(board);
        
        //setup of the board 
        setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SpaceInvaders();
        });
    }
}
