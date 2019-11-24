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

    Character(int x, int y, int size, PlatformerManager manager){
        super(x,y,size,manager);
        setShape();
        start = false;
        this.gameScore = 0;
    }

    void setColour(int colour){
        this.colour = colour;
        this.bottom = getY() + (getSize() / 2);
        getPaint().setColor(this.colour);
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

        if (getY() + getSize() / 2 + speed - getManager().getGridHeight() > 0 && !start) {
            setY(getManager().getGridHeight());
            speed = - 75;
            incY(speed);
        } else{
            incY(speed);
        }

        setShape();
    }

    /** Checks if the Character touches a platform. */
    private void collisionDetection() {

        if (speed > 10) {
            for (Platforms platform: getManager().getPlatforms()) {
                if (getShape().intersect(platform.getShape()) ||
                        (Math.abs((int)bottom - (int)platform.getY()) < 20 && getX() > platform.getX() &&
                                getX() < platform.getX() + 150)) {
                    this.gameScore += 1;
                    setY(platform.getY() - (getSize() / 2));
                    speed = -75;
                    incY(speed);
                    start = true;
                    setShape();
                }
            }
        }
    }

    /** Checks if there's a Coin at the same coordinate as Character. */
    void coinDetection() {
        for (Coin coin: getManager().getCoins()) {
            if (getShape().intersect(coin.getShape())) {
                coin.gotCoin();
                getManager().getPlayer().addCoin();
            }
        }
    }

    /** Checks if your Player is alive.
     *
     * @return boolean if the Player is alive.
     */
    boolean isAlive() {
        return !start || getY() <= getManager().getGridHeight();
    }

    /** The number of lives this Player has.
     *
     * @return the int number of lives.
     */
    int getLives(){
        return getManager().getPlayer().getNumLives();
    }

    /** Moves the Character 50 units to the left. */
    void moveLeft() {
        if (getX() - 100 <= 0) {
            setX(getManager().getGridWidth() - 50);
        } else {
            incX(-50);
        }
    }

    /** Moves the Character 50 units to the right. */
    void moveRight() {
        if (getX() + 100 >= getManager().getGridWidth()) {
            setX(0);
        } else {
            incX(50);
        }
    }
}