package layout;

import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import model.Node;

import java.util.Random;


public class DrawableNode {
    private static final String FONT_FAMILY = "Segoe UI";
    public static final int CIRCLE_RADIUS = 10;

    private Node sourceNode;

    private boolean isFocused;

    private Circle shape;
    private Text name;


    public DrawableNode(Node sourceNode) {
        this.sourceNode = sourceNode;

        Random randomColorComponent = new Random(System.currentTimeMillis());

        shape = new Circle(
                CIRCLE_RADIUS, Color.color(
                randomColorComponent.nextDouble(),
                randomColorComponent.nextDouble(),
                randomColorComponent.nextDouble()
        ));
        configureShape();

        name = new Text();
        configureName();
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public Circle getShape() {
        return shape;
    }

    public Text getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    /*
        Configs
     */

    // Shape configs: events handling
    private void configureShape() {
        shape.setStrokeType(StrokeType.OUTSIDE);
        shape.setStrokeWidth(CIRCLE_RADIUS / 2);
        shape.setStroke(Color.TRANSPARENT);

        // Node moving
        shape.setOnMouseDragged(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shape.setCenterX(e.getX());
                shape.setCenterY(e.getY());
            }
        });

        // Node inner dark lightning when mouse entered
        shape.setOnMouseEntered(e -> {
            shape.setEffect(new Bloom());
            shape.toFront();
            isFocused = true;
        });

        // Remove lightning when mouse exited
        shape.setOnMouseExited(e -> {
            shape.setEffect(null);
            shape.toFront();
            isFocused = false;
        });
    }

    private void configureName() {
        name.setFont(Font.font(FONT_FAMILY, FontPosture.ITALIC, 4 * CIRCLE_RADIUS / 3));
        name.setText(sourceNode.getName());

        name.xProperty().bind(shape.centerXProperty().add(3 * CIRCLE_RADIUS / 2));
        name.yProperty().bind(shape.centerYProperty().add(-CIRCLE_RADIUS));
    }
}
