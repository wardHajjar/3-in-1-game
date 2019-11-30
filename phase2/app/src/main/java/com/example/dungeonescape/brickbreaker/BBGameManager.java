package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

import com.example.dungeonescape.game.collectable.Coin;
import com.example.dungeonescape.game.collectable.Gem;
import com.example.dungeonescape.game.collectable.Potion;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.game.collectable.Blitz;
import com.example.dungeonescape.game.collectable.Collectable;

/** Instantiates and controls game objects. */
class BBGameManager {

    /**
     * ball - the ball object that bounces around and hits bricks.
     * paddle - the paddle that catches the ball.
     * bricks - list of all the bricks in the game.
     */
    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks;
    private ArrayList<Coin> coins;
    private Gem gem;
    private Potion potion;
    private Blitz blitz;
    private int screenX;
    private int screenY;
    private Player player;

    BBGameManager(int screenX, int screenY) {

        /* Construct the ball. */
        this.ball = new Ball(screenX/2 - 75 + (screenX/2 - 75)/2 - 25,
                screenY - 100 - 26, 25, -26, Color.WHITE);

        /* Construct paddle and bricks. */
        paddle = new Paddle(screenX/2 - 75, screenY - 100, screenX/3, 40);

        bricks = new ArrayList<>();
        int brickWidth = screenX / 6;
        int brickHeight = screenY / 20;
        for (int x = 0; x < screenX; x += brickWidth + 5) {
            for (int y = 10; y < 3 * brickHeight; y += brickHeight + 5) {
                bricks.add(new Brick(x, y, brickWidth, brickHeight));
            }
        }
        this.screenX = screenX;
        this.screenY = screenY;
        this.player = null;

        /* Random assignment of coins to bricks. */
        coins = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Brick coinBrick = getRandomBrick();
            Coin newCoin = new Coin(
                    coinBrick.getX() + brickWidth/2,
                    coinBrick.getY() + brickHeight/2,
                    brickHeight/2);
            coinBrick.setItem(newCoin);
            coins.add(newCoin);
        }

        Brick blitzBrick = getRandomBrick();
        Blitz newBlitz = new Blitz(blitzBrick.getX() + brickWidth/2,
                blitzBrick.getY() + brickHeight/2, brickHeight/2);
        this.blitz = newBlitz;
        blitzBrick.setItem(newBlitz);

        Brick gemBrick = getRandomBrick();
        Gem newGem = new Gem(gemBrick.getX() + brickWidth/2,
                gemBrick.getY() + brickHeight/2, brickHeight/2);
        this.gem = newGem;
        gemBrick.setItem(newGem);

        Brick potionBrick = getRandomBrick();
        Potion newPotion = new Potion(potionBrick.getX() + brickWidth/2,
                potionBrick.getY() + brickHeight/2, brickHeight/2);
        this.potion = newPotion;
        potionBrick.setItem(newPotion);

    }

    private Brick getRandomBrick(){
        Random random = new Random();
        Brick curr = bricks.get(random.nextInt(bricks.size()));
        while(curr.hasItem()){
            Collections.shuffle(bricks);
            curr = bricks.get(0);
        }
        return curr;
    }

    /** Determines the physics of the ball in reaction to collisions. */
    void moveBall(){
        ball.move();

        /* Wall Collision Detection. */
        manageWallCollision();

        /* Brick Collision Detection. */
        manageBrickCollision();

        /* Paddle Collision Detection. */
        managePaddleCollision();

        /* Coin Collision Detection. */
        for (Coin coin: coins) {
            boolean itemCollision = manageItemCollision(coin);
            if (itemCollision){
                player.addCoin();
            }
        }

        /* Blitz Collision Detection */
        manageItemCollision(blitz);

        /* Gem Collision Detection */
//        manageItemCollision(gem);

        /* Potion Collision Detection */
//        manageItemCollision(potion);

    }

    private void manageWallCollision(){
        String wallCollision = ball.madeWallCollision(screenX, screenY);
        if ( wallCollision.equals("x")) {
            ball.setXSpeed(ball.getXSpeed() * -1);
        }
        else if(wallCollision.equals("y")) {
            ball.setYSpeed(ball.getYSpeed() * -1);
        }
    }

    private void manageBrickCollision(){
        if (!blitz.getBlitzMode().equals("started")){
            for (Brick brick: bricks) {
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
        }
    }

    private boolean manageItemCollision(Collectable item){
        if (item.getAvailableStatus()) {
            String coinCollision = ball.madeRectCollision(item.getItemShape());
            if (coinCollision.equals("x") || coinCollision.equals("y")) {
                player.addCoin();
                item.gotCollected();
                return true;
            }
        }
        return false;
    }

    private void managePaddleCollision(){
        String paddleCollision = ball.madeRectCollision(paddle.getRect());
        if (!(paddleCollision.equals(" "))) {
            ball.setYSpeed(ball.getYSpeed() * -1);
            ball.setRandomXSpeed();
        }
    }


    /**
     * Checks if the player still has lives left after they've lost the ball.
     * If player still has lives left, the ball is reset.
     * @return boolean whether player still has lives left.
     */
    boolean checkLifeCondition() {
        if (ball.getY() > paddle.getY()){
            if (player.getNumLives() >= 1) {
                player.loseLife();
                ball.setX((paddle.getX() + paddle.getWidth()/2));
                ball.setY(paddle.getY() - 26);
            }
            return true;
        }
        return false;
    }

    /** Move the paddle according to the direction. */
    void movePaddle() {
        if (paddle.getMovingLeft()) {
            paddle.move(-20);
        } else if (paddle.getMovingRight()) {
            paddle.move(20);
        }
        if (paddle.getX() <= 0) {
            paddle.setX(0);
        } else if (paddle.getX() + paddle.getWidth() >= screenX){
            paddle.setX(screenX - paddle.getWidth());
        }
    }

    /**
     * Set paddle movement direction left or right in response to touch event.
     * Calculates the relative position of the touch to paddle and determines the paddle's new
     * movement direction.
     */
    void setPaddleDirection(MotionEvent event, float xPos) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (xPos < paddle.getX()) {
                paddle.setMovementDir("left", true);
            } else if (xPos > paddle.getX()) {
                paddle.setMovementDir("right", true);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            paddle.setMovementDir("right", false);
            paddle.setMovementDir("left", false);
        }
    }

    /**
     * Draws all the game objects: Ball, Paddle, Bricks, Coins.
     * @param canvas the Canvas which the objects are drawn on
     */
    void drawGame(Canvas canvas) {
        /* Ball. */
        ball.draw(canvas);

        /* Paddle. */
        paddle.draw(canvas);

        /* Bricks. */
        for (int i = 0; i < bricks.size(); i++) {
            Brick curr = bricks.get(i);
            /* Draws the bricks that have not been hit. */
            if (!curr.getHitStatus()) {
                curr.draw(canvas);
            } else {    /* Draw if hit brick contains a collectable item to be collected. */
                if (curr.hasItem() && curr.getItem().getAvailableStatus()) {
                    curr.getItem().draw(canvas);
                }
            }
        }
    }

    void initializeBlitzCoins(){
        for (Brick brick: bricks){
            Collectable item = brick.getItem();
            if (item == null || item.getClass() != Coin.class ||
                    (item.getClass() == Coin.class && !item.getAvailableStatus())){
                Coin newCoin = new Coin(brick.getX() + brick.getWidth()/2,
                        brick.getY() + brick.getHeight()/2, brick.getHeight()/2);
                coins.add(newCoin);
            }
        }
    }

    void endBlitzGame(){
        //TODO: change starting index after testing is done
        coins.subList(10, coins.size()).clear();
    }

    void drawBlitzGame(Canvas canvas){
        ball.draw(canvas);
        paddle.draw(canvas);
        for (Coin coin: coins){
            if (coin.getAvailableStatus()){
                coin.draw(canvas);
            }
        }
    }

    Blitz getBlitz(){
        return blitz;
    }

    /**
     * Checks if the ball collides the top boarder/top of the screen.
     * @return true if ball collides with the top of the screen.
     */
    boolean passedBorder() {
        String wallCollision = ball.madeWallCollision(screenX, screenY);
        return wallCollision.equals("win");
    }

    /**
     * Checks whether all the bricks have been destroyed.
     * @return true if all bricks got destroyed.
     */
    boolean hitAllBricks() {
        for (Brick brick: bricks) {
            if(!brick.getHitStatus()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds the user to the game.
     * @param player Player object that represents the user's character.
     */
    void addPlayer(Player player) {
        this.player = player;
        this.ball.setColour(player.getColour());
    }
}
