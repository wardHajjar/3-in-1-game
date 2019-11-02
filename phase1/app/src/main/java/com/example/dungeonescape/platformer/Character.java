package com.example.dungeonescape.platformer;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Represents the ball that bounces on the platforms.
 */
class Character extends Level3Object {

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
        this.rect = new Rect(x - size / 2, (y + size / 4),
                x + size / 2,y + size / 2);
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

        this.oval = new RectF(x - size / 2,y - size / 2,
                x + size / 2,y + size / 2);
        this.rect = new Rect((x - size / 3), (y + size / 4), (x + size / 2), (y + size / 3));
        Rect bounds = new Rect();
        this.oval.roundOut(bounds);
    }

    /** Checks if the Character touches a platform. */
    private void collisionDetection() {
        this.bottom = this.y + (size / 2);

        if (speed > 10) {
            for (Platforms platform: manager.getPlatforms()) {
                if (this.rect.intersect(platform.rectangle) ||
                        (Math.abs((int)bottom - (int)platform.gety()) < 20 && x > platform.getx() &&
                                x < platform.getx() + 150)) {
                    this.gameScore += 1;
                    y = (int) platform.gety() - (size / 2);
                    speed = -75;
                    y += speed;
                    start = true;
                    this.oval = new RectF(x - size / 2,(int)(y + size / 4),
                            x + size / 2,y + size / 2);
                    Rect bounds = new Rect();
                    this.oval.roundOut(bounds);
                }
            }
        }
    }

    /** Checks if there's a Coin at the same coordinate as Character. */
    void coinDetection() {
        this.bottom = this.y + (size / 2);

        for (Coin coin: manager.getCoins()) {
            if (this.rect.intersect(coin.getRect())) {
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
        if (x - 50 <= 0) {
            x = manager.getGridWidth();
        } else {
            x -= 50;
        }
    }

    /** Moves the Character 50 units to the right. */
    void moveRight() {
        if (x + 50 >= manager.getGridWidth()) {
            x = 0;
        } else {
            x += 50;
        }
    }
}