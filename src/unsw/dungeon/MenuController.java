package unsw.dungeon;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MenuController {
    @FXML
    private ChoiceBox<String> dungeons;
    @FXML
    private Button StartButton;
    private Stage stage;
    private MediaPlayer bgm;
    private Boolean hasSound= false;

    MenuController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        for (File file : getDungeons())
            dungeons.getItems().add(file.getName());
        if (hasSound) {
            Media sound = new Media(new File("sounds/bgm.mp3").toURI().toString());
            bgm = new MediaPlayer(sound);
            bgm.setVolume(0.05);
            bgm.play();
        }                               

    }

    @FXML
    public void handleStartButton(ActionEvent event) throws IOException {
        startDungeonScreen();
    }

    public void startDungeonScreen() throws IOException {
        if (dungeons.getValue() == null)
            return;
        DungeonScreen screen = new DungeonScreen(this.stage, "Dungeon Game");
        screen.load(dungeons.getValue().toString());
        screen.start();
        if (hasSound) {
            bgm.stop();
        }
    }

    private File[] getDungeons() {
        File folder = new File("dungeons/");
        return folder.listFiles();
    }

}