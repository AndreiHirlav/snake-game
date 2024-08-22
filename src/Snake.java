import java.util.LinkedList;

public class Snake {
    private LinkedList<Point> body;
    private Direction direction;
    private SnakeGame game;
    private int score;

    public Snake(SnakeGame game){
        body = new LinkedList<>();
        body.add(new Point(5, 5));   //Initial position
        direction = Direction.RIGHT;   //Initial direction
        this.game = game;
    }
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public void move() {
        Point head = body.getFirst();

        switch (direction) {
            case UP:
                head = new Point(head.getX(), head.getY() - 1);
                break;

            case DOWN:
                head = new Point(head.getX(), head.getY() + 1);
                break;

            case LEFT:
                head = new Point(head.getX() - 1, head.getY());
                break;
            case RIGHT:
                head = new Point(head.getX() + 1, head.getY());
                break;
        }
        body.addFirst(head);

        if (head.equals(game.getFoodPosition())) {
            grow();
            game.generateFood();
            score++;
            game.repaint();
        } else {
            body.removeLast();  //remove the last segment of the tail
        }
    }

    public void grow() {
        body.add(new Point(-1, -1));
    }

    public boolean checkCollision() {
        Point head = body.getFirst();

        for(int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i)))
                return true;
        }

        if(head.getX() < 0 || head.getX() >= 20 || head.getY() <= 0 || head.getY() >= 20)
            return true;


        return false;
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public void setDirection(Direction newDirection) {   //Avoid 180 degree direction changes
        if ((direction == Direction.UP && newDirection != Direction.DOWN) ||
                (direction == Direction.DOWN && newDirection != Direction.UP) ||
                (direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
                (direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            direction = newDirection;
        }
    }

    public int getScore() {
        return score;
    }
}
