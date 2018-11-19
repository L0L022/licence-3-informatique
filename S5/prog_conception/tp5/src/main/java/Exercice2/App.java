package Exercice2;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Group root = new Group();
		Canvas canvas = new Canvas(130, 110);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		ShapeContainer shapeContainer = new ShapeContainer();
		graphicsContext.setFill(Color.AQUAMARINE);
		graphicsContext.fillOval(10, 10, 10, 10);
		shapeContainer.addShape(new CenterDecorator(
				new BorderDecorator(new Rectangle(Color.BLUE, new Point2D(10, 10), new Point2D(40, 40)), 5), 5));
		shapeContainer.addShape(new CenterDecorator(new BorderDecorator(
				new Polygon(Color.BLUE, new Point2D(50, 10), new Point2D(80, 50), new Point2D(45, 80)), 5), 5));
		shapeContainer.draw(graphicsContext);
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
