package model;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Created by Damyan Damyanov <damyan.damyanov@scalefocus.com> on 1/6/16
 */
public class CustomRectangle extends Rectangle {

    public boolean isSelected = false;

    final Delta dragDelta = new Delta();

    public CustomRectangle(){
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
    };

    public CustomRectangle(double x, double y, double width, double height, Paint fill) {
        super(x, y, width, height);

        this.setFill(fill);
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
