package unsw.dungeon;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuScreen {

	private Stage stage;
	private String title = "Dungeon Menu";
	private MenuController controller;
	private Scene scene;

	public MenuScreen(Stage stage) throws IOException {
		this.stage = stage;
		controller = new MenuController(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuScreenView.fxml"));
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
}