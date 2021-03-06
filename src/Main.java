import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import model.CustomEllipse;
import model.CustomLine;
import model.CustomRectangle;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private final List<Shape> nodesToDrag = new ArrayList<>();

    private final List<Shape> shapes = new ArrayList<>();

    private static final double rootWidth = 800.00;

    private static final double rootHeight = 600.00;

    private String chosenOperation = "default";

    private String chosenShape = "rectangle";

    private String chosenBorderColor = "#000000";

    private double chosenSize = 60.00;

    final ColorPicker mainColorPicker = new ColorPicker(Color.WHITE);

    final ColorPicker borderColorPicker = new ColorPicker(Color.BLACK);

    Pane drawingPane = new Pane();

    BorderPane root = new BorderPane();

    // Line properties
    CustomLine line;
    SimpleDoubleProperty lineInitX = new SimpleDoubleProperty();
    SimpleDoubleProperty lineInitY = new SimpleDoubleProperty();
    SimpleDoubleProperty lineX = new SimpleDoubleProperty();
    SimpleDoubleProperty lineY = new SimpleDoubleProperty();

    // Rectangle properties
    CustomRectangle rectangle;
    SimpleDoubleProperty rectangleInitX = new SimpleDoubleProperty();
    SimpleDoubleProperty rectangleInitY = new SimpleDoubleProperty();
    SimpleDoubleProperty rectangleX = new SimpleDoubleProperty();
    SimpleDoubleProperty rectangleY = new SimpleDoubleProperty();

    // Circle properties
    CustomEllipse ellipse;
    SimpleDoubleProperty ellipseInitX = new SimpleDoubleProperty();
    SimpleDoubleProperty ellipseInitY = new SimpleDoubleProperty();
    SimpleDoubleProperty ellipseX = new SimpleDoubleProperty();
    SimpleDoubleProperty ellipseY = new SimpleDoubleProperty();

    // Mouse event handlers
    EventHandler<MouseEvent> mouseEvent = event -> {

        //Decides what to do on click for ex.: draw, select, resize etc..
        switch (chosenOperation) {
            case "drawShape" : switch (chosenShape) { //Decides which shape to draw.
                                    case "line":
                                        //Line bindings
                                        line.endXProperty().bind(lineX);
                                        line.endYProperty().bind(lineY);

                                        if(event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                                            line.setStartX(event.getX());
                                            line.setStartY(event.getY());
                                            line.setStroke(mainColorPicker.getValue());
                                            lineInitX.set(event.getX());
                                            lineInitY.set(event.getY());
                                        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                                            lineX.set(event.getX());
                                            lineY.set(event.getY());
                                        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                                            // Clone the line
                                            CustomLine l = getNewLine();
                                            l.setStartX(lineInitX.doubleValue());
                                            l.setStartY(lineInitY.doubleValue());
                                            l.setEndX(line.getEndX());
                                            l.setEndY(line.getEndY());
                                            drawingPane.getChildren().add(l);
                                            // Hide the line
                                            lineX.set(0);
                                            lineY.set(0);
                                            line.setStartX(0);
                                            line.setStartY(0);
                                        }
                                        break;
                                    case "ellipse":
                                        //Ellipse bindings
                                        ellipse.radiusXProperty().bind(ellipseX.subtract(ellipseInitX));
                                        ellipse.radiusYProperty().bind(ellipseY.subtract(ellipseInitY));

                                        if(event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                                            ellipse.setCenterX(event.getX());
                                            ellipse.setCenterY(event.getY());
                                            ellipse.setFill(mainColorPicker.getValue());
                                            ellipse.setStroke(borderColorPicker.getValue());
                                            ellipseInitX.set(event.getX());
                                            ellipseInitY.set(event.getY());

                                        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                                            ellipseX.set(event.getX());
                                            ellipseY.set(event.getY());
                                        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                                            // Clone the ellipse
                                            CustomEllipse r = getNewEllipse();
                                            r.setCenterX(ellipse.getCenterX());
                                            r.setCenterY(ellipse.getCenterY());
                                            r.setRadiusY(ellipse.getRadiusY());
                                            r.setRadiusX(ellipse.getRadiusX());
                                            drawingPane.getChildren().add(r);
                                            // Hide the ellipse
                                            ellipseX.set(0);
                                            ellipseY.set(0);
                                        }
                                        break;
                                    case "rectangle":
                                        // Rectangle bindings
                                        rectangle.widthProperty().bind(rectangleX.subtract(rectangleInitX));
                                        rectangle.heightProperty().bind(rectangleY.subtract(rectangleInitY));

                                        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                                            rectangle.setX(event.getX());
                                            rectangle.setY(event.getY());
                                            rectangle.setFill(mainColorPicker.getValue());
                                            rectangle.setStroke(borderColorPicker.getValue());
                                            rectangleInitX.set(event.getX());
                                            rectangleInitY.set(event.getY());
                                        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                                            rectangleX.set(event.getX());
                                            rectangleY.set(event.getY());
                                        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                                            // Clone the rectangle
                                            CustomRectangle r = getNewRectangle();
                                            r.setX(rectangle.getX());
                                            r.setY(rectangle.getY());
                                            r.setWidth(rectangle.getWidth());
                                            r.setHeight(rectangle.getHeight());

                                            drawingPane.getChildren().add(r);
                                            // Hide the rectangle
                                            rectangleX.set(0);
                                            rectangleY.set(0);
                                        }
                                        break;
                                    default:
                                        break;
                               }
                               break;
            case "select" : //TODO select logic..
                break;
            //TODO: more cases
            default: break;
        }
    };


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        line = getNewLine();
        rectangle = getNewRectangle();
        ellipse = getNewEllipse();

        //Setting the handler for mouse clicks.
        drawingPane.setOnMouseClicked(mouseEvent);
        drawingPane.setOnMousePressed(mouseEvent);
        drawingPane.setOnMouseReleased(mouseEvent);
        drawingPane.setOnMouseDragged(mouseEvent);

        drawingPane.getChildren().add(line);
        drawingPane.getChildren().add(ellipse);
        drawingPane.getChildren().add(rectangle);

        root.setCenter(drawingPane);
        root.setLeft(addVBox());

        primaryStage.setResizable( false );

        Scene scene = new Scene(root, rootWidth, rootHeight);

        primaryStage.setTitle("Drawing Stuff");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private CustomRectangle getNewRectangle() {
        CustomRectangle r = new CustomRectangle();
        r.setFill(mainColorPicker.getValue());
        r.setStroke(borderColorPicker.getValue());
        r.setStrokeWidth(2);
        return r;
    }

    private CustomEllipse getNewEllipse() {
        CustomEllipse e = new CustomEllipse();
        e.setFill(mainColorPicker.getValue());
        e.setStroke(borderColorPicker.getValue());
        e.setStrokeWidth(2);
        return e;
    }

    private CustomLine getNewLine() {
        CustomLine l = new CustomLine();
        l.setStroke(mainColorPicker.getValue());
        return l;
    }

    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #b4bec6; -fx-stroke-type: centered;");

        Button lineBtn = new Button();
        lineBtn.setGraphic(new ImageView("images/Line.png"));
        lineBtn.setOnMouseClicked(event -> {
            this.chosenOperation = "drawShape";
            this.chosenShape = "line";
        });

        Button circleBtn = new Button();
        circleBtn.setGraphic(new ImageView("images/Ellipse.png"));
        circleBtn.setOnMouseClicked(event -> {
            this.chosenOperation = "drawShape";
            this.chosenShape = "ellipse";
        });

        Button rectangleBtn = new Button();
        rectangleBtn.setGraphic(new ImageView("images/Rectangle.png"));
        rectangleBtn.setOnMouseClicked(event -> {
            this.chosenOperation = "drawShape";
            this.chosenShape = "rectangle";
        });

        Button selectionBtn = new Button();
//        selectionBtn.setStyle("-fx-base: #36454f;");
        selectionBtn.setGraphic(new ImageView("images/Hand.png"));
        selectionBtn.setOnMouseClicked(event -> {
            this.chosenOperation = "select";
        });

        TilePane shapesPane = new TilePane();
        shapesPane.setPadding(new Insets(5, 0, 5, 0));
        shapesPane.setVgap(4);
        shapesPane.setHgap(4);
        shapesPane.setPrefColumns(2);
        shapesPane.setStyle("-fx-background-color: #bbc4cb;");
        shapesPane.getChildren().addAll(lineBtn, circleBtn);
        shapesPane.getChildren().addAll(rectangleBtn, selectionBtn);

        // Labels
        Label mainColorLabel = new Label("Main color");
        Label borderColorLabel = new Label("Border color");

        vbox.getChildren().addAll(shapesPane, mainColorLabel, mainColorPicker, borderColorLabel, borderColorPicker);

        return vbox;
    }

}

