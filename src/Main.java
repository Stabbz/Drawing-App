import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

    private String chosenColor = "#FFFFFF";

    private String chosenBorderColor = "#000000";

    private double chosenSize = 60.00;

    Pane drawingPane = new Pane();

    BorderPane root = new BorderPane();

    // Rectangle creation and properties
    CustomRectangle rectangle;
    SimpleDoubleProperty rectangleInitX = new SimpleDoubleProperty();
    SimpleDoubleProperty rectangleInitY = new SimpleDoubleProperty();
    SimpleDoubleProperty rectangleX = new SimpleDoubleProperty();
    SimpleDoubleProperty rectangleY = new SimpleDoubleProperty();

    // Circle creation and properties
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
                                    case "ellipse":
                                        ellipse.radiusXProperty().bind(ellipseX.subtract(ellipseInitX));
                                        ellipse.radiusYProperty().bind(ellipseY.subtract(ellipseInitY));

                                        if(event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                                            ellipse.setCenterX(event.getX());
                                            ellipse.setCenterY(event.getY());

                                            ellipseInitX.set(event.getX());
                                            ellipseInitY.set(event.getY());

                                        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                                            ellipseX.set(event.getX());
                                            ellipseY.set(event.getY());
                                        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                                            // Clone the rectangle
                                            CustomEllipse r = new CustomEllipse();
                                            r.setCenterX(ellipse.getCenterX());
                                            r.setCenterY(ellipse.getCenterY());
                                            r.setRadiusY(ellipse.getRadiusY());
                                            r.setRadiusX(ellipse.getRadiusX());
                                            drawingPane.getChildren().add(r);
                                            // Hide the rectangle
                                            ellipseX.set(0);
                                            ellipseY.set(0);
                                        }
                                        break;
                                    case "rectangle":
                                        // Rectangle bindings...
                                        rectangle.widthProperty().bind(rectangleX.subtract(rectangleInitX));
                                        rectangle.heightProperty().bind(rectangleY.subtract(rectangleInitY));

                                        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                                            rectangle.setX(event.getX());
                                            rectangle.setY(event.getY());
                                            rectangleInitX.set(event.getX());
                                            rectangleInitY.set(event.getY());
                                        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                                            rectangleX.set(event.getX());
                                            rectangleY.set(event.getY());
                                        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                                            // Clone the rectangle
                                            CustomRectangle r = getNewCustomRectangle();
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
                                    case "line":
                                        CustomLine line = new CustomLine(event.getX(), event.getY(), event.getX(), event.getY());
                                        shapes.add(line);
                                        drawingPane.getChildren().add(line);
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

        rectangle = getNewCustomRectangle();
        ellipse = getEllipse();

        //Setting the handler for mouse clicks.
        drawingPane.setOnMouseClicked(mouseEvent);
        drawingPane.setOnMousePressed(mouseEvent);
        drawingPane.setOnMouseReleased(mouseEvent);
        drawingPane.setOnMouseDragged(mouseEvent);

        drawingPane.getChildren().add(ellipse);
        drawingPane.getChildren().add(rectangle);

        root.setCenter(drawingPane);
        root.setTop(addHBox());

        primaryStage.setResizable( false );

        Scene scene = new Scene(root, rootWidth, rootHeight);

        primaryStage.setTitle("Drawing Stuff");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private CustomRectangle getNewCustomRectangle() {
        CustomRectangle r = new CustomRectangle();
        r.setFill(Color.web(chosenColor));
        r.setStroke(Color.web(chosenBorderColor));
        return r;
    }

    private CustomEllipse getEllipse() {
        CustomEllipse e = new CustomEllipse();
        e.setFill(Color.web(chosenColor));
        e.setStroke(Color.web(chosenBorderColor));
        return e;
    }

    public HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #9acd32;");

        Button lineBtn = new Button("Line");
        lineBtn.setOnMouseClicked(event -> {
            this.chosenOperation = "drawShape";
            this.chosenShape = "line";
            this.chosenColor = "#FF0066";
        });
        lineBtn.setPrefSize(100, 20);

        Button circleBtn = new Button("Ellipse");
        circleBtn.setOnMouseClicked(event -> {
            this.chosenOperation = "drawShape";
            this.chosenShape = "ellipse";
            this.chosenColor = "#FF0066";
        });
        circleBtn.setPrefSize(100, 20);

        Button rectangleBtn = new Button("CustomRectangle");
        rectangleBtn.setOnMouseClicked(event -> {
            this.chosenOperation = "drawShape";
            this.chosenShape = "rectangle";
            this.chosenColor = "#09AC7C";
        });
        rectangleBtn.setPrefSize(100, 20);

        Button selectionBtn = new Button("Selection");
        selectionBtn.setOnMouseClicked(event -> {
            this.chosenOperation = "select";
        });
        hbox.getChildren().addAll(lineBtn, circleBtn, rectangleBtn, selectionBtn);

        return hbox;
    }

}

