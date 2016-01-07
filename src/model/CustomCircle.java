package model;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Created by Damyan Damyanov on 1/5/16
 */
public class CustomCircle extends Circle {

    public boolean isSelected = false;

    final Delta dragDelta = new Delta();

    public CustomCircle(double centerX, double centerY, double radius, Paint fill) {
        super(centerX, centerY, radius, fill);

        this.setStrokeWidth(3);
        this.setStroke(Color.BLACK);

        this.setOnScroll(event ->  {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            this.setScaleX(this.getScaleX() * zoomFactor);
            this.setScaleY(this.getScaleY() * zoomFactor);
            event.consume();
        });
        this.setOnMousePressed(event -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = this.getLayoutX() - event.getSceneX();
            dragDelta.y = this.getLayoutY() - event.getSceneY();
            this.setCursor(Cursor.MOVE);
        });
        this.setOnMouseReleased(event -> {
            this.setCursor(Cursor.HAND);
        });
        this.setOnMouseDragged(event -> {
            this.setLayoutX(event.getSceneX() + dragDelta.x);
            this.setLayoutY(event.getSceneY() + dragDelta.y);
        });
        this.setOnMouseEntered(event -> {
            this.setCursor(Cursor.HAND);
        });
    }



}
