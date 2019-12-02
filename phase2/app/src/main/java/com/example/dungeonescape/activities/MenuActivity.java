package com.example.dungeonescape.activities;

import android.content.Context;
import android.content.Intent;

public class MenuActivity extends GeneralGameActivity {
    public MenuActivity(){}

    public Intent createIntent(Context packageContext,
                     Class nextClass,
                     String playerName) {
        Intent intent = new Intent(packageContext, nextClass);
        intent.putExtra("Player Name", playerName);
        return intent;
    }
}
