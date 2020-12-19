package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import unsw.dungeon.Composition.*;
import unsw.dungeon.entities.*;
import unsw.dungeon.entities.type.*;
import java.io.File;
import java.io.IOException;

import javafx.animation.Timeline;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.event.*;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;

/**
 * A JavaFX controller for the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {
    @FXML
    private HBox base;

    private Text pausetext;

    private GridPane squares;

    @FXML
    private HBox gameArea;

    private VBox goalPane;

    @FXML
    private StackPane bottom;

    private GridPane inventories;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private Timeline timeline;

    private KeyFrame enemyMoving;

    private MediaPlayer bgm;

    private boolean isPaused = false;

    private DungeonScreen dungeonScreen;

    @FXML
    private Button resumeButton;
    @FXML
    private Button retryButton;

    private Boolean hasSound = false;
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, DungeonScreen dungeonScreen) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.dungeonScreen = dungeonScreen;
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());
        squares = new GridPane();
        gameArea.getChildren().add(squares);
        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

        // initialize goal pane

        goalPane = new VBox();
        BackgroundFill background_fill = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
        goalPane.setBackground(new Background(background_fill));
        Text title = new Text("Goal to win!!!");
        title.setWrappingWidth(300);
        title.setFont(Font.font("Comic Sans MS", 15));
        title.setFill(Color.BLACK);
        goalPane.getChildren().add(title);

        goalPane.getChildren().add(setUpGoal(dungeon.getGoal(), 0));
        gameArea.getChildren().add(goalPane);

        // initialize inventory UI
        inventories = new GridPane();
        BackgroundFill background_fill2 = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);
        inventories.setBackground(new Background(background_fill2));
        bottom.getChildren().add(inventories);

        // Add inventory UI listner
        player.inventory().addListener(new ChangeListener<ObservableList<Item>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<Item>> observable,
                    ObservableList<Item> oldValue, ObservableList<Item> newValue) {
                Item newItem = newValue.get(newValue.size() - 1);
                Text use = new Text(Integer.toString(newItem.getUses()));
                addListenerForUse(use, newItem);
                VBox newOne = new VBox();
                newOne.getChildren().addAll(new ImageView(newItem.getImage()), use);
                inventories.add(newOne, newValue.size() - 1, 0);
            }
        });
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        // add function to triger the enemy move in the timeline
        EventHandler<ActionEvent> enemyMove = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dungeon.EnemyMove();
                dungeon.getPlayer().reduceInvicibility();
                if ((!player.isAlive()) || dungeon.isWin())
                    gameOver();
            }
        };
        enemyMoving = new KeyFrame(Duration.seconds(0.75), enemyMove);
        // interactionHappen = new KeyFrame(Duration.millis(100), interaction);
        timeline.getKeyFrames().add(enemyMoving);
        // timeline.getKeyFrames().add(interactionHappen);
        timeline.play();
        // initialize background music
        if (hasSound) {
            Media sound = new Media(new File("sounds/bgm.mp3").toURI().toString());
            bgm = new MediaPlayer(sound);
            bgm.setVolume(0.05);
            bgm.play();
        }

        Integer y = (squares.getRowCount() / 4);
        Integer x = (squares.getColumnCount() / 3);
        // create pause menu text
        pausetext = new Text("--- PAUSED ---");
        pausetext.setWrappingWidth(x * 30);
        pausetext.setFill(Color.WHITE);
        pausetext.setFont(Font.font("Comic Sans MS", x * 4));
        squares.add(pausetext, x, y, x * 2, y);
        pausetext.setVisible(isPaused);

        // create pause menu retry button
        retryButton = new Button("Retry");
        retryButton.setPadding(new Insets(10, 10, 10, 10));
        retryButton.setOnAction(event -> dungeonScreen.restart());
        retryButton.setFont(Font.font("Comic Sans MS", x * 3));
        squares.add(retryButton, x - 1, y + 2, x * 2, y + 2);
        retryButton.setVisible(isPaused);

        // create pause menu resume button
        resumeButton = new Button("Resume");
        resumeButton.setPadding(new Insets(10, 10, 10, 10));
        resumeButton.setOnAction(event -> pause());
        resumeButton.setFont(Font.font("Comic Sans MS", x * 3));
        squares.add(resumeButton, x + 4, y + 2, x * 2, y + 2);
        resumeButton.setVisible(isPaused);
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (isPaused) {
            if (event.getCode() == KeyCode.ESCAPE)
                pause();
            if (event.getCode() == KeyCode.R)
                dungeonScreen.restart();
            return;
        }
        switch (event.getCode()) {
            case UP:
                player.moveUp();
                break;
            case DOWN:
                player.moveDown();
                break;
            case LEFT:
                player.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
                break;
            case ESCAPE:
                pause();
                break;
            default:
                break;
        }
    }

    private void pause() {
        if (!isPaused)
            pauseGame();
        else
            resumeGame();
        pausetext.setVisible(isPaused);
        retryButton.setVisible(isPaused);
        resumeButton.setVisible(isPaused);
    }

    private void pauseGame() {
        timeline.stop();
        if (hasSound)
            bgm.pause();
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.7);
        squares.setEffect(colorAdjust);
        ScaleTransition st = new ScaleTransition(Duration.millis(500), base);
        st.setInterpolator(Interpolator.EASE_IN);
        st.setByX(0.03);
        st.setByY(0.03);
        st.play();
        this.isPaused = true;
    }

    private void resumeGame() {
        timeline.play();
        if (hasSound)
            bgm.play();
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        squares.setEffect(colorAdjust);
        ScaleTransition st = new ScaleTransition(Duration.millis(500), base);
        st.setInterpolator(Interpolator.EASE_OUT);
        st.setByX(-0.03);
        st.setByY(-0.03);
        st.play();
        this.isPaused = false;
    }

    private void gameOver() {
        if (hasSound)
            bgm.stop();
        timeline.stop();
        this.isPaused = true;

        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: #87CEFA");
        FadeTransition ft = new FadeTransition(Duration.millis(3000), pane);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);

        VBox vbox = new VBox();

        Button retryButton = new Button("Retry");
        retryButton.setPadding(new Insets(10, 10, 10, 10));
        retryButton.setOnAction(event -> dungeonScreen.restart());
        retryButton.setFont(Font.font("Comic Sans MS", 20));

        Button exitButton = new Button("Exit");
        exitButton.setPadding(new Insets(10, 10, 10, 10));
        exitButton.setOnAction(event -> exit());
        exitButton.setFont(Font.font("Comic Sans MS", 20));

        Button menuButton = new Button("Back to Menu");
        menuButton.setPadding(new Insets(10, 10, 10, 10));
        menuButton.setOnAction(event -> {
            try {
                dungeonScreen.menu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuButton.setFont(Font.font("Comic Sans MS", 20));
        
        Text text;
        if (dungeon.isWin()) {
            text = new Text("~~~ YOU WIN! ~~~");
        } else {
            text = new Text("~~~ GAME OVER ~~~");
        }
        text.setWrappingWidth(500);
        text.setFill(Color.WHITE);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Comic Sans MS", 40));
        vbox.getChildren().addAll(text, retryButton, exitButton, menuButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        pane.getChildren().add(vbox);

        base.getScene().setRoot(pane);

        ft.play();
    }
    /**
     * The help function to add the listen for the use of Item.
     * @param use
     * @param newOne
     */
    private void addListenerForUse(Text use, Item newOne) {
        newOne.getUseProperty().addListener(new ChangeListener<Number> (){
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                Number oldValue, Number newValue) {
                    use.setText(newValue.toString());
                }
        });
    }

    private VBox setUpGoal(Goal goal, int depth) {
        VBox givenGoal = new VBox();
        if (goal.getSubSize() > 0) {
            givenGoal.getChildren().add(setUpGoal(goal.getFritSubGoal(), depth + 1));
            givenGoal.getChildren().add(new Text(getPreSpace(depth) + goal.getString()));
            givenGoal.getChildren().add(setUpGoal(goal.getSecondSubGoal(), depth + 1));
        } else {
            givenGoal.getChildren().add(new Text(getPreSpace(depth) + goal.getString()));
        }
        return givenGoal;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public void exit() {
        System.exit(0);
    }

    private String getPreSpace(int i) {
        String result = " ";
        for (int j = 0; j < i; j++) {
            result = result + "    ";
        }
        return result;
    }
}

