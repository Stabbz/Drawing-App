package model;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by Damyan Damyanov <damyan.damyanov@scalefocus.com> on 1/6/16
 */
public class CustomLine extends Line {

    public double initX;
    public double initY;

    public CustomLine(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);

        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                //System.out.println("Clicked, x:" + me.getSceneX() + " y:" + me.getSceneY());
                //the event will be passed only to the circle which is on front
                initX = me.getSceneX();
                initY = me.getSceneY();
                me.consume();
            }
        });
        this.setOnMouseDragged(event -> {

            this.setStartX(initX);
            this.setStartY(initY);
            this.setEndX(event.getSceneX());
            this.setEndY(event.getSceneY());
            this.setStroke(Color.BLACK);
            this.setStrokeWidth(2);

            initX = event.getSceneX();
            initY = event.getSceneY();

        });
    }


}
