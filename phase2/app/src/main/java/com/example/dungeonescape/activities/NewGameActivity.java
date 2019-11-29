package com.example.dungeonescape.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.R;
import com.example.dungeonescape.game.SaveData;
import com.example.dungeonescape.brickbreaker.BBMainActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class NewGameActivity extends GeneralGameActivity {
    private PlayerManager playerManager;
    private Player player;
    private EditText name;
    private String nameText;
    private Boolean isValid;
    private MenuActivity menuActivity = new MenuActivity();

    /** For New Game choices. */
    private List<Button> gameDifficulties = new ArrayList<>();
    private List<Button> playerColourChoices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent i = getIntent();
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");
        isValid = false;
        name = findViewById(R.id.nameInput);
        buttons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_menu) {
            Intent intent = menuActivity.createIntent(NewGameActivity.this,
                    MainActivity.class, playerManager, player);
            startActivity(intent);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void buttons() {
        final TextView newGameText = findViewById(R.id.newGameText);
        final Button enter = findViewById(R.id.enterGame);
        final EditText name = findViewById(R.id.nameInput);
        final Button enterName = findViewById(R.id.checkName);
        final TextView namePrompt = findViewById(R.id.enterNameText);
        final TextView welcomeDisplay = findViewById(R.id.welcomeDisplay);

        updateGameDifficulties();
        updatePlayerColourChoices();

        enterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkName();
                if (isValid){
                    player = new Player(nameText);
                    playerManager.addPlayer(player);
                    enter.setVisibility(View.INVISIBLE);
                    name.setVisibility(View.INVISIBLE);
                    namePrompt.setVisibility(View.INVISIBLE);
                    newGameText.setVisibility(View.INVISIBLE);
                    welcomeDisplay.setVisibility(View.VISIBLE);
                    String welcomeMessage = "Welcome " + nameText;
                    welcomeDisplay.setText(welcomeMessage);
                    TextView colorPrompt =  (TextView) findViewById(R.id.colorPromptText);
                    enterName.setVisibility(View.INVISIBLE);
                    colorPrompt.setText((CharSequence)("Select Character Colour:"));
                    setButtonsVisibility(playerColourChoices, View.VISIBLE);
                }
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    Intent intent = new Intent(NewGameActivity.this, BBMainActivity.class);
                    intent.putExtra("Player", player);
                    intent.putExtra("Game Manager", playerManager);
                    startActivity(intent);
                }
            }
        });

    }

    private void setButtonsVisibility(List<Button> buttonList, int visibility) {
        for (Button button: buttonList) {
            button.setVisibility(visibility);
        }
    }

    private void updatePlayerColourChoices() {
        this.playerColourChoices = createPlayerColourChoices();
    }

    private List<Button> createPlayerColourChoices() {
        final TextView diffPrompt = findViewById(R.id.diffPrompt);
        Button redPlayer = findViewById(R.id.colour1);
        Button greenPlayer = findViewById(R.id.colour2);
        Button yellowPlayer = findViewById(R.id.colour3);

        List<Button> colourChoices = new ArrayList<>();

        redPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    int color = Color.argb(255, 173, 0, 0);
                    player.setColour(color);
                    save();
                    diffPrompt.setText(("Choose Difficulty Level:"));
                    setButtonsVisibility(gameDifficulties, View.VISIBLE);
                }
            }
        });
        colourChoices.add(redPlayer);

        greenPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    int color = Color.argb(255, 76, 175, 80);
                    player.setColour(color);
                    save();
                    diffPrompt.setText(("Choose Difficulty Level:"));
                    setButtonsVisibility(gameDifficulties, View.VISIBLE);
                }
            }
        });
        colourChoices.add(greenPlayer);


        yellowPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    int color = Color.argb(255, 255, 193, 7);
                    player.setColour(color);
                    save();
                    diffPrompt.setText(("Choose Difficulty Level:"));
                    setButtonsVisibility(gameDifficulties, View.VISIBLE);
                }
            }
        });
        colourChoices.add(yellowPlayer);

        return colourChoices;
    }

    private void updateGameDifficulties() {
        this.gameDifficulties = createDifficultyButtons();
    }

    private List<Button> createDifficultyButtons() {
        final Button enter = findViewById(R.id.enterGame);
        List<Button> difficultyButtons = new ArrayList<>();

        Button easyButton = findViewById(R.id.easy);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    player.setDifficulty("Easy");
                    save();
                    enter.setVisibility(View.VISIBLE);
                }

            }
        });
        difficultyButtons.add(easyButton);

        Button hardButton = findViewById(R.id.hard);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    player.setDifficulty("Hard");
                    save();
                    enter.setVisibility(View.VISIBLE);
                }

            }
        });
        difficultyButtons.add(hardButton);

        return difficultyButtons;
    }

    private void checkName() {
        nameText = name.getText().toString();
        isValid = true;
        List<String> allPlayerNames = playerManager.getPlayerNames();

        if (isEmpty(nameText)) {
            Toast t = Toast.makeText(this, "Please Enter a Name", Toast.LENGTH_SHORT);
            isValid = false;
            t.show();
        } else if (allPlayerNames.contains(nameText)) {
            Toast t = Toast.makeText(this, "There is already a player saved with " +
                    "this name", Toast.LENGTH_SHORT);
            isValid = false;
            t.show();
        }
    }

    private void save() {
        playerManager.updatePlayer(player.getName(), player);
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            SaveData.save(playerManager, f);
        }
        catch (Exception e) {
            System.out.println("Couldn't save: " + e.getMessage());
        }
    }
}
