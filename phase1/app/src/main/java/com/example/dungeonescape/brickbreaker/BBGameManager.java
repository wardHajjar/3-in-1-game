package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;


class BBGameManager {

    /**
     * ball - the ball object that bounces around and hits bricks.
     * paddle - the paddle that catches the ball.
     * bricks - list of all the bricks in the game.
     */
    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks;
    private ArrayList<BBCoin> coins;
    private int screenX;
    private int screenY;
    private int numLives;
    private int numCoins;

    BBGameManager(int screenX, int screenY){

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

        // assign coins to random bricks
        coins = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Collections.shuffle(bricks);
            Brick curr = bricks.get(0);
            while (curr.hasCoin()){     // shuffle again if the current brick has a coin
                Collections.shuffle(bricks);
                curr = bricks.get(0);
            }
            BBCoin newCoin = new BBCoin(curr.x + curr.getWidth()/2,
                        curr.y + curr.getHeight()/2);
            curr.setCoin(newCoin);
            coins.add(newCoin);
        }
    }

    void moveBall(){


        ball.move();
//        loseLife();
        // Wall Collision Detection
        char wallCollision = ball.madeWallCollision(screenX, screenY);
        if ( wallCollision == 'x'){
            ball.setXSpeed(ball.getXSpeed() * -1);
        }
        else if(wallCollision == 'y'){
            ball.setYSpeed(ball.getYSpeed() * -1);
        }

        // Brick Collision Detection
        for (Brick brick: bricks){
            if (!(brick.getHitStatus())) {
                String brickCollision = ball.madeRectCollision(brick.getRect());
                if (brickCollision.equals("x")) {
                    ball.setXSpeed(ball.getXSpeed() * -1);
                    brick.changeHitStatus();
                    break;
                } else if (brickCollision.equals("y")) {
                    ball.setYSpeed(ball.getYSpeed() * -1);
                    brick.changeHitStatus();
                    break;
                }
            }
        }

        // Coin Collision Detection
        for (BBCoin currCoin: coins){
            if (!currCoin.getCollectStatus()) {
                String coinCollision = ball.madeRectCollision(currCoin.getRect());
                if (coinCollision.equals("x")) {
                    ball.setXSpeed(ball.getXSpeed() * -1);
                    currCoin.gotCollected();
                    this.numCoins += 1;
                    break;
                } else if (coinCollision.equals("y")) {
                    ball.setYSpeed(ball.getYSpeed() * -1);
                    currCoin.gotCollected();
                    this.numCoins += 1;
                    break;
                }
            }
        }

        // Paddle Collision Detection
        String paddleCollision = ball.madeRectCollision(paddle.getRect());
        if (!(paddleCollision.equals(" "))) {
            ball.setYSpeed(ball.getYSpeed() * -1);
            ball.setRandomXSpeed();
        }
    }

    boolean loseLife(){
        if (ball.getY() > paddle.getY()){
            if (numLives != 0){
                numLives -= 1;
                ball.setX((paddle.getX() + paddle.getWidth()/2));
                ball.setY(paddle.getY() - 26);
            } //TODO: If lives are 0, playing = false + GameOver screen
            return true;
        }
        return false;
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
            Brick curr = bricks.get(i);
            // draws the bricks that have not been hit
            if (!curr.getHitStatus()) {
                curr.draw(canvas);
            } else {    // draw if hit brick contains a coin to be collected
                if (curr.hasCoin() && !curr.coin.getCollectStatus()) {
                    curr.coin.draw(canvas);
                }
            }
        }
    }

    /**
     * Returns the number of character lives
     * @return numLives
     */
    int getCharacterLives(){
        return numLives;
    }

    /**
     * Returns the number of coins the character has so far
     * @return numCoins
     */
    int getCharacterCoins(){
        return numCoins;
    }

    /**
     * Checks whether all the bricks have been destroyed
     * @return true if all bricks got destroyed.
     */
    boolean hitAllBricks(){
        for (Brick brick: bricks){
            if(!brick.getHitStatus()){
                return false;
            }
        }
        return true;
    }

}
