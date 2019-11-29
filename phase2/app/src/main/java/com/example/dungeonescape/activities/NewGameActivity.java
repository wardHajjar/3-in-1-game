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

import org.w3c.dom.Text;

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
    private List<TextView> playerNameData = new ArrayList<>();
    private List<TextView> gameDifficulties = new ArrayList<>();
    private List<TextView> playerColourChoices = new ArrayList<>();

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
        Button enterName = findViewById(R.id.checkName);
        final TextView welcomeDisplay = findViewById(R.id.welcomeDisplay);

        setPlayerNameData(createPlayerNameData());
        setGameDifficulties(createDifficultyButtons());
        setPlayerColourChoices(createPlayerColourChoices());

        enterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkName();
                if (isValid){
                    player = new Player(nameText);
                    playerManager.addPlayer(player);
                    setListVisibility(playerNameData, View.INVISIBLE);
                    welcomeDisplay.setVisibility(View.VISIBLE);
                    String welcomeMessage = "Welcome " + nameText;
                    welcomeDisplay.setText(welcomeMessage);
                    TextView colorPrompt =  (TextView) findViewById(R.id.colorPromptText);
                    colorPrompt.setText((CharSequence)("Select Character Colour:"));
                    setListVisibility(playerColourChoices, View.VISIBLE);
                }
            }
        });

        configureEnterGameButton();
    }

    private void configureEnterGameButton() {
        Button enterGame = findViewById(R.id.enterGame);
        enterGame.setOnClickListener(new View.OnClickListener() {
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

    /** Sets all Buttons in the given buttonList to the specified visibility.
     *
     * @param textViewList the list of Buttons to loop through & update Button visibility.
     * @param visibility the Visibility value.
     */
    private void setListVisibility(List<TextView> textViewList, int visibility) {
        for (TextView item: textViewList) {
            item.setVisibility(visibility);
        }
    }

    /** Sets the playerNameData list to the new inputted list.
     *
     * @param playerNameData the new list of Player Name Data (TextView) elements.
     */
    private void setPlayerNameData(List<TextView> playerNameData) {
        this.playerNameData = playerNameData;
    }

    /** Creates the Player Name Data for the Game. The TextView elements match up with the
     * corresponding ID from the activity_new_game.xml file.
     *
     * @return a List of TextView elements representing Player Name Data.
     */
    private List<TextView> createPlayerNameData() {
        List<TextView> nameData = new ArrayList<>();

        nameData.add((TextView) findViewById(R.id.newGameText));
        nameData.add((EditText) findViewById(R.id.nameInput));
        nameData.add((Button) findViewById(R.id.checkName));
        nameData.add((TextView) findViewById(R.id.enterNameText));

        return nameData;
    }

    /** Sets the gameDifficulties list to the new inputted list.
     *
     * @param gameDifficulties the new list of Game Difficulty (TextView) Buttons.
     */
    private void setGameDifficulties(List<TextView> gameDifficulties) {
        this.gameDifficulties = gameDifficulties;
    }

    /** Creates the difficulty buttons for the Game. The buttons match up with the corresponding ID
     * from the activity_new_game.xml file.
     *
     * @return a list of Game Difficulty Buttons.
     */
    private List<TextView> createDifficultyButtons() {
        final Button enter = findViewById(R.id.enterGame);
        List<TextView> difficultyButtons = new ArrayList<>();

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

    /** Sets the playerColourChoices list to the new inputted list.
     *
     * @param playerColourChoices the new list of Player Colour Choice (TextView) Buttons.
     */
    private void setPlayerColourChoices(List<TextView> playerColourChoices) {
        this.playerColourChoices = playerColourChoices;
    }

    /** Creates the Player Colour Choices. The Buttons match up with the corresponding ID from the
     * activity_new_game.xml file.
     *
     * @return a list of Player Colour Choice Buttons.
     */
    private List<TextView> createPlayerColourChoices() {
        final TextView diffPrompt = findViewById(R.id.diffPrompt);
        Button redPlayer = findViewById(R.id.colour1);
        Button greenPlayer = findViewById(R.id.colour2);
        Button yellowPlayer = findViewById(R.id.colour3);

        List<TextView> colourChoices = new ArrayList<>();

        redPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    int color = Color.argb(255, 173, 0, 0);
                    player.setColour(color);
                    save();
                    diffPrompt.setText(("Choose Difficulty Level:"));
                    setListVisibility(gameDifficulties, View.VISIBLE);
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
                    setListVisibility(gameDifficulties, View.VISIBLE);
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
                    setListVisibility(gameDifficulties, View.VISIBLE);
                }
            }
        });
        colourChoices.add(yellowPlayer);

        return colourChoices;
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
