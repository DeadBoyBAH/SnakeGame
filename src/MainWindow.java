import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    String[] difficulty = {
            "Сложно",
            "Нормально",
            "Легко"
    };

    String[] snakes = {
            "Happy",
            "Sad",
            "Russia"
    };

    public MainWindow() {
        setLayout(new FlowLayout());
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(150, 200);
        setLocation(400, 400);
        JButton button = new JButton("Запуск");
        JComboBox comboBoxDifficulties = new JComboBox(difficulty);
        JComboBox comboBoxSnakes = new JComboBox(snakes);
        add(comboBoxDifficulties);
        add(comboBoxSnakes);
        button.addActionListener(e -> {
            new GameWindow(comboBoxDifficulties.getSelectedItem().toString(),
                    comboBoxSnakes.getSelectedItem().toString());
        });
        add(button);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }

}
