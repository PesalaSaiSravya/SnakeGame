package game;
import java.util.Scanner;
import java.util.Random;

public class SnakeGame {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final char EMPTY = ' ';
    private static final char SNAKE_BODY = 'O';
    private static final char FOOD = 'X';
    private static final char WALL = '#';

    private static int snakeLength = 3;
    private static int[][] snake = new int[WIDTH * HEIGHT][2]; 
    private static int foodX, foodY;
    private static char direction = 'R'; // R - Right, L - Left, U - Up, D - Down
    private static boolean inGame = true;
    private static Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        initGame();
        while (inGame) {
            printBoard();
            System.out.print("Enter direction (W - up, S - down, A - left, D - right): ");
            char input = scanner.next().toUpperCase().charAt(0);
            updateDirection(input);
            moveSnake();
            checkCollisions();
            checkFood();
        }
        System.out.println("Game Over!");
    }

    private static void initGame() {
        snake[0][0] = HEIGHT / 2;
        snake[0][1] = WIDTH / 2;
        snake[1][0] = HEIGHT / 2;
        snake[1][1] = WIDTH / 2 - 1;
        snake[2][0] = HEIGHT / 2;
        snake[2][1] = WIDTH / 2 - 2;

        generateFood();
    }

    private static void generateFood() {
        foodX = random.nextInt(HEIGHT);
        foodY = random.nextInt(WIDTH);
    }

    private static void printBoard() {
        for (int i = 0; i <= HEIGHT; i++) {
            for (int j = 0; j <= WIDTH; j++) {
                if (i == 0 || i == HEIGHT || j == 0 || j == WIDTH) {
                    System.out.print(WALL); 
                } else if (i == foodX && j == foodY) {
                    System.out.print(FOOD); 
                } else {
                    boolean isBodyPart = false;
                    for (int k = 0; k < snakeLength; k++) {
                        if (snake[k][0] == i && snake[k][1] == j) {
                            System.out.print(SNAKE_BODY);
                            isBodyPart = true;
                            break;
                        }
                    }
                    if (!isBodyPart) {
                        System.out.print(EMPTY); 
                    }
                }
            }
            System.out.println();
        }
    }

    private static void moveSnake() {
        for (int i = snakeLength - 1; i > 0; i--) {
            snake[i][0] = snake[i - 1][0];
            snake[i][1] = snake[i - 1][1];
        }
        switch (direction) {
            case 'U':
                snake[0][0]--;
                break;
            case 'D':
                snake[0][0]++;
                break;
            case 'L':
                snake[0][1]--;
                break;
            case 'R':
                snake[0][1]++;
                break;
        }
    }

    private static void updateDirection(char input) {
        switch (input) {
            case 'W':
                if (direction != 'D') direction = 'U';
                break;
            case 'S':
                if (direction != 'U') direction = 'D';
                break;
            case 'A':
                if (direction != 'R') direction = 'L';
                break;
            case 'D':
                if (direction != 'L') direction = 'R';
                break;
        }
    }

    private static void checkCollisions() {
        if (snake[0][0] == 0 || snake[0][0] == HEIGHT || snake[0][1] == 0 || snake[0][1] == WIDTH) {
            inGame = false;
        }

        for (int i = 1; i < snakeLength; i++) {
            if (snake[i][0] == snake[0][0] && snake[i][1] == snake[0][1]) {
                inGame = false;
            }
        }
    }


    private static void checkFood() {
        if (snake[0][0] == foodX && snake[0][1] == foodY) {
            snakeLength++;
            generateFood();
        }
    }
}
