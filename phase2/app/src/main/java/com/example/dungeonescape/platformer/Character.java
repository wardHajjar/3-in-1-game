package com.example.dungeonescape.platformer;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Represents the ball that bounces on the platforms.
 */
class Character extends PlatformerObject {

    /** The speed of the ball. */
    private int speed = 5;

    /** The bottom of the ball. */
    private float bottom;

    /** If the player has touched the bottom of the screen. */
    private boolean start;

    /** The number of platforms this Character has bounced off of. */
    private int gameScore;

    private int colour;

    //    private Player user; // referencing from player class for lives
    void setColour(int colour){
        this.colour = colour;
        paint.setColor(this.colour);
    }


    Character(int x, int y, int size, PlatformerManager manager){
        super(x,y,size,manager);
        start = false;
        this.gameScore = 0;
    }

    /** Returns the GameScore.
     *
     * @return the integer GameScore.
     */
    int getGameScore(){
        return this.gameScore;
    }

    /** Sets the GameScore.
     *
     * @param gameScore the new GameScore.
     */
    void setGameScore(int gameScore){
        this.gameScore = gameScore;
    }

    /** Moves the Character. */
    void move() {
        collisionDetection();

        double gravity = 3.0;

        if (speed >= 0) {
            speed += gravity;
        } else {
            speed += gravity;
        }

        if (y + size / 2 + speed - manager.getGridHeight() > 0 && !start) {
            y = manager.getGridHeight();
            speed = - 75;
            y += speed;
        } else{
            y += speed;
        }

        setShape();
        Rect bounds = new Rect();
        this.shape.roundOut(bounds);
    }

    /** Checks if the Character touches a platform. */
    private void collisionDetection() {
        this.bottom = this.y + (size / 2);

        if (speed > 10) {
            for (Platforms platform: manager.getPlatforms()) {
                if (this.shape.intersect(platform.shape) ||
                        (Math.abs((int)bottom - (int)platform.getY()) < 20 && x > platform.getX() &&
                                x < platform.getX() + 150)) {
                    this.gameScore += 1;
                    y = (int) platform.getY() - (size / 2);
                    speed = -75;
                    y += speed;
                    start = true;
                    this.shape = new RectF(x - size / 2,y + size / 2,
                            x + size / 2,y + size / 2);
                    Rect bounds = new Rect();
                    this.shape.roundOut(bounds);
                }
            }
        }
    }

    /** Checks if there's a Coin at the same coordinate as Character. */
    void coinDetection() {
        this.bottom = this.y + (size / 2);

        for (Coin coin: manager.getCoins()) {
            if (this.shape.intersect(coin.getShape())) {
                coin.gotCoin();
                manager.getPlayer().addCoin();
            }
        }
    }

    /** Checks if your Player is alive.
     *
     * @return boolean if the Player is alive.
     */
    boolean isAlive() {
        return !start || y <= manager.getGridHeight();
    }

    /** The number of lives this Player has.
     *
     * @return the int number of lives.
     */
    int getLives(){
        return manager.getPlayer().getNumLives();
    }

    /** Moves the Character 50 units to the left. */
    void moveLeft() {
        if (x - 100 <= 0) {
            x = manager.getGridWidth() - 50;
        } else {
            x -= 50;
        }
    }

    /** Moves the Character 50 units to the right. */
    void moveRight() {
        if (x + 100 >= manager.getGridWidth()) {
            x = 0;
        } else {
            x += 50;
        }
    }
}