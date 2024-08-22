import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeGame extends JFrame {
    private static final int GRID_SIZE = 20;
    private static final int CELL_SIZE = 20;
    private Snake snake;
    private Point foodPosition;


    public SnakeGame() {
        this.setTitle("Snake");
        this.setSize(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snake = new Snake(this);
        generateFood();

        JPanel gamePanel = new JPanel();
        this.add(gamePanel);

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        snake.setDirection(Snake.Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        snake.setDirection(Snake.Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        snake.setDirection(Snake.Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        snake.setDirection(Snake.Direction.RIGHT);
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        setVisible(true);
        setFocusable(true);
        runGame();
    }
    public void generateFood() {
        boolean foodOnSnake;
        int x, y;
        do {
            x = (int) (Math.random() * GRID_SIZE);
            y = (int) (Math.random() * GRID_SIZE);
            foodOnSnake = false;

            for(Point segment : snake.getBody()) {
                if (segment.getX() == x && segment.getY() == y && x == 0 && y == 0) {
                    foodOnSnake = true;
                    break;
                }
            }
        }while (foodOnSnake);
        foodPosition = new Point(x, y);
    }

    private void runGame() {
        while(true) {
            snake.move();

            if(snake.checkCollision()) {
                JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.PLAIN_MESSAGE);
                resetGame();
            }

            repaint();

            try {
                Thread.sleep(200);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void resetGame() {
        snake = new Snake(this);
        generateFood();
    }

    //Method to draw the snake and the food
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(new Color(121, 79, 54));
        g.fillRect(0, 0, getWidth(), getHeight());

        for(Point segment : snake.getBody()) {
            g.setColor(new Color(27, 94, 2));
            g.fillRect(segment.getX() * CELL_SIZE, segment.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(segment.getX() * CELL_SIZE, segment.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);  //adds a border around each snake part
        }

        g.setColor(Color.RED);
        g.fillRect(foodPosition.getX() * CELL_SIZE, foodPosition.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        Font boldFont = new Font("Arial", Font.BOLD, 14);
        g.setFont(boldFont);
        g.setColor((Color.BLACK));
        g.drawString("Score: " + snake.getScore(), 30, 50);
    }
    public Point getFoodPosition() {
        return foodPosition;
    }

}