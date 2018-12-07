package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BlackSquare implements Square {
    double width, height;

    public BlackSquare(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(GraphicsContext context, int column, int row) {
        context.setFill(Color.BLACK);
        context.fillRect(column*width,row*height,width,height);
    }
}
