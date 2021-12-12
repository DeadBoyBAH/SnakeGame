import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image dotHead;
    private Image apple;
    private Image background;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private Direction direction = Direction.RIGHT;
    private InGame inGame = InGame.GAME;
    private JButton button = new JButton("Replay");
    private int score = 0;
    private String difficulty;
    private String snake;

    public GameField(String difficulty, String snake) {
        setBackground(Color.BLACK);
        addButtonReplay();
        this.snake = snake;
        loadImages();
        this.difficulty = difficulty;
        initGame(this.difficulty);
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(String difficulty) {
        inGame = InGame.GAME;
        direction = Direction.RIGHT;
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        switch (difficulty) {
            case "Сложно": timer = new Timer(40, this);
            break;
            case "Нормально": timer = new Timer(100, this);
            break;
            case "Легко": timer = new Timer(200, this);
            break;
        }
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;

        for (int i = dots; i > 0; i--) {
            if (appleX == x[i] && appleY == y[i]) {
                createApple();
                break;
            }
        }
    }

    public void loadImages() {
        ImageIcon iib = new ImageIcon("Icons/Green.png");
        background = iib.getImage();
        ImageIcon iia = new ImageIcon("Icons/Apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("Icons/" + snake + "Tail.png");
        dot = iid.getImage();
        ImageIcon iidh = new ImageIcon("Icons/" + snake + "Head.png");
        dotHead = iidh.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame == InGame.GAME) {
            g.drawImage(background, 0, 0, this);
            g.drawImage(apple, appleX, appleY, this);
            g.drawImage(dotHead, x[0], y[0], this);
            for (int i = 1; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else if (inGame == InGame.LOSE) {
            String gameOver = "Game Over. Difficulty: " + difficulty + " Score: " + score;
            score = 0;
            g.setColor(Color.white);
            g.drawString(gameOver, 75, SIZE/2);
            afterGameOver();
        }
    }

    public void addButtonReplay() {
        button.addActionListener(e -> {
            initGame(this.difficulty);
            button.setVisible(false);
        });
        button.setVisible(false);
        add(button);
    }

    public void afterGameOver() {
        timer.stop();
        inGame = InGame.REPLAY;
        button.setVisible(true);
        revalidate();
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case LEFT: x[0] -= DOT_SIZE;
            break;
            case RIGHT: x[0] += DOT_SIZE;
            break;
            case UP: y[0] -= DOT_SIZE;
            break;
            case DOWN: y[0] += DOT_SIZE;
            break;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
            score++;
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = InGame.LOSE;
            }
        }

        if (x[0] > SIZE || x[0] < 0 || y[0] > SIZE || y[0] < 0) {
            inGame = InGame.LOSE;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame == InGame.GAME) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            } else if (key == KeyEvent.VK_RIGHT && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            } else if (key == KeyEvent.VK_DOWN && direction != Direction.UP) {
                direction = Direction.DOWN;
            } else if (key == KeyEvent.VK_UP && direction != Direction.DOWN) {
                direction = Direction.UP;
            }
        }
    }
}
