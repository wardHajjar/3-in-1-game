package com.example.dungeonescape.brickbreaker;

import com.example.dungeonescape.GameManager;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.view.MotionEvent;

class BBGameManager extends GameManager {

    /**
     * ball - the ball object that bounces around and hits bricks.
     * paddle - the paddle that catches the ball.
     * bricks - list of all the bricks in the game.
     */
    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks;
    private int screenX;
    private int screenY;
    private int numLives;
    private int numCoins;

    BBGameManager(int screenX, int screenY){
        super();

        // construct the ball
        this.ball = new Ball(screenX/2 - 75 + (screenX/2 - 75)/2 - 25,
                screenY - 100 - 26, 25, -26);

        // construct paddle and bricks
        paddle = new Paddle(screenX/2 - 75, screenY - 100, screenX/3, 40);

        bricks = new ArrayList<>();
        int brickWidth = screenX / 6;
        int brickHeight = screenY / 20;
        for (int x = 0; x < screenX; x += brickWidth + 5) {
            for (int y = 10; y < 6 * brickHeight; y += brickHeight + 5) {
                bricks.add(new Brick(x, y, brickWidth, brickHeight));
            }
        }
        this.screenX = screenX;
        this.screenY = screenY;
        this.numLives = 5; //TODO: Change once difficulty level has been set
        this.numCoins = 0;
    }

    void moveBall(){
        ball.move();

        // Wall Collision Detection
        char wall_collision = ball.madeWallCollision(screenX, screenY);
        if ( wall_collision == 'x'){
            ball.setXSpeed(ball.getXSpeed() * -1);
        }
        else if(wall_collision == 'y'){
            ball.setYSpeed(ball.getYSpeed() * -1);
        }

        // Brick Collision Detection
        for (Brick brick: bricks){
            if (!(brick.getHitStatus())) {
                String brick_collision = ball.madeRectCollision(brick.getRect());
                if (brick_collision.equals("x")) {
                    ball.setXSpeed(ball.getXSpeed() * -1);
                    brick.changeHitStatus();
                    break;
                } else if (brick_collision.equals("y")) {
                    ball.setYSpeed(ball.getYSpeed() * -1);
                    brick.changeHitStatus();
                    break;
                }
            }
        }

        // Paddle Collision Detection
        String paddle_collision = ball.madeRectCollision(paddle.getRect());
        if (!(paddle_collision.equals(" "))) {
            ball.setYSpeed(ball.getYSpeed() * -1);
            ball.setRandomXSpeed();
        }

        if (ball.getY() > paddle.getY()){
            numLives -= 1;
            if (numLives != 0){
                ball.setX((paddle.getX() + paddle.getWidth()/2));
                ball.setY(paddle.getY() - 1);
            } //TODO: If lives are 0, playing = false + GameOver screen
        }
    }

    void movePaddle(){
        if (paddle.movingLeft) {
            paddle.move(-20);
        } else if (paddle.movingRight) {
            paddle.move(20);
        }

        if (paddle.getX() <= 0){
            paddle.setX(0);
        }else if (paddle.getX() + paddle.getWidth() >= screenX){
            paddle.setX(screenX - paddle.getWidth());
        }

    }

    void movePaddle(MotionEvent event, float xPos){
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (xPos < paddle.x){
                paddle.setMovementDir("left", true);
            } else if (xPos > paddle.x) {
                paddle.setMovementDir("right", true);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            paddle.setMovementDir("right", false);
            paddle.setMovementDir("left", false);
        }
    }

    void drawGame(Canvas canvas){
        // Ball
        ball.draw(canvas);

        // Paddle
        paddle.draw(canvas);

        // Bricks
        for (int i = 0; i < bricks.size(); i++) {
            if (!bricks.get(i).getHitStatus()) {
                bricks.get(i).draw(canvas);
            }
        }
    }

}
