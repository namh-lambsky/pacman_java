package Window;

import javax.swing.*;

/*Constructs the main window, which will be a screen that shows the map and
the title of the game and also prompts the user to press any key to start the game
 */
public class Window extends JFrame {
    public enum View{
        MENU,GAME,GAME_OVER
    }

    GamePanel gamePanel=new GamePanel();

    public Window() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Pac-Man");

        add(gamePanel);
        pack();


        setLocationRelativeTo(null);
        setVisible(true);
        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();
    }
}
