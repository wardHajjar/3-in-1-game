package com.example.dungeonescape.platformer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.example.dungeonescape.player.Player;

/**
 * This class is responsible for drawing out the game objects of the level, as well as
 * as well as updating player state.
 */
public class PlatformerView extends LevelView implements Runnable{


    private Drawable portalImage;
    protected OnCustomEventListener portalEventListener;


    public PlatformerView(Context context, AttributeSet attrs) {

        super(context, attrs, "Regular");
    }

    public void setEnterPortalListener(OnCustomEventListener eventListener) {
        this.portalEventListener = eventListener;
    }


    public void setPortalImage(Drawable drawable) {
        portalImage = drawable;
        getManager().setImage(drawable);
    }


    public void update() {

        if (getManager().getPortal() != null) {
            if (getManager().enterPortal()) {
                enterPortal();
            }
        }
        super.update();

    }

    public void gameOver(Player player) {
        super.gameOver(player);
        getManager().setImage(portalImage);

    }

    public void enterPortal() {
        portalEventListener.onEvent();
    }


}