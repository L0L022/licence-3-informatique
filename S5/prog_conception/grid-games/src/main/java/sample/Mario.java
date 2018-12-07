package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Mario implements Square {
    double width, height;
    Image img = new Image("/mario.jpeg",width,height,true,true);

    public Mario(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(GraphicsContext context, int column, int row) {
        context.drawImage(img,column*width,row*height,width,height);

    }
}
