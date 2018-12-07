package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BlueSquare implements Square{
    double width, height;

    public BlueSquare(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void paint(GraphicsContext context, int column, int row) {
        context.setFill(Color.BLUE);
        context.fillOval(column*width,row*height,width,height);
    }
}
