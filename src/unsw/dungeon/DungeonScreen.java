package unsw.dungeon;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DungeonScreen {
    private Stage stage;
    private String title;
    private DungeonController controller;
    private Scene scene;
    private String map;

    public DungeonScreen(Stage stage, String title) {
        this.stage = stage;
        this.title = title;
	}

    public void load(String mapJ) throws IOException {
        map = mapJ;
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(mapJ);
        controller = dungeonLoader.loadController(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        scene = new Scene(root);
    }

    public void start() {
        scene.getRoot().requestFocus();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public void restart() {
        try {
            load(map);
            start();
        } catch (Exception e) {
            System.out.println("restart failed");
        }
    }

    public DungeonController getController() {
        return controller;
    }

    public void menu() throws IOException {
        MenuScreen menuScreen = new MenuScreen(this.stage);
        menuScreen.start();
    }
}