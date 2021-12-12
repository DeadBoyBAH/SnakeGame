import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(String difficulty, String snake) {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(353, 376);
        setLocation(400, 400);
        add(new GameField(difficulty, snake));
        setVisible(true);
    }
}
