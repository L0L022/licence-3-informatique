package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		Group root = new Group();
		primaryStage.setTitle("Tic Tac Toe");
		Scene scene = new Scene(root, 300, 300);
		primaryStage.setScene(scene);
		VBox vbox = new VBox();
		root.getChildren().add(vbox);
		Label label = new Label("");
		Button restart = new Button("Restart");

		// PlayGrid playGrid = new PlayGrid(label, scene.getWidth() / 1.2,
		// scene.getHeight() / 1.2, 3, 3);
		// restart.setOnMouseClicked(playGrid::restart);
		// vbox.getChildren().add(playGrid);
		// playGrid.widthProperty().bind(scene.widthProperty().divide(1.2));
		// playGrid.heightProperty().bind(scene.heightProperty().divide(1.2));

		GridModel model = new GridModel(3, 3);
		GridView view = new GridView(model, label, scene.getWidth() / 1.2, scene.getHeight() / 1.2);
		GridController controller = new GridController(model, view);
		restart.setOnMouseClicked(controller::restart);
		vbox.getChildren().add(view);
		view.widthProperty().bind(scene.widthProperty().divide(1.2));
		view.heightProperty().bind(scene.heightProperty().divide(1.2));

		vbox.getChildren().add(label);
		vbox.getChildren().add(restart);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
