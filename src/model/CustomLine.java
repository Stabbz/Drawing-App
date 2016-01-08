package model;

import javafx.scene.Cursor;
import javafx.scene.shape.Line;

/**
 * Created by Damyan Damyanov <damyan.damyanov@scalefocus.com> on 1/6/16
 */
public class CustomLine extends Line {

    public double initX;
    public double initY;

    final Delta dragDelta = new Delta();

    public CustomLine() {
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
            event.consume();
        });
        this.setOnMouseReleased(event -> {
            this.setCursor(Cursor.HAND);
            event.consume();
        });
        this.setOnMouseDragged(event -> {
            this.setLayoutX(event.getSceneX() + dragDelta.x);
            this.setLayoutY(event.getSceneY() + dragDelta.y);
            event.consume();
        });
        this.setOnMouseEntered(event -> {
            this.setCursor(Cursor.HAND);
        });
    }
    public CustomLine(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);

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
