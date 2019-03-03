import javafx.scene.paint.Color;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.shape.*;

public class Lab1FXApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lab1");
        Group root = new Group();
        Scene scene = new Scene (root, 420, 270);

        Color bodyColor = Color.rgb(128, 0, 255);
        Color eyeColor = Color.YELLOW;
        Color earColor = Color.BLUE;

        Rectangle footLeft = new Rectangle(145, 238, 65, 24);
        footLeft.setFill(bodyColor);

        Rectangle footRight = new Rectangle(240, 242, 56, 20);
        footRight.setFill(bodyColor);

        Rectangle legLeft = new Rectangle(140, 178,54, 84);
        legLeft.setFill(bodyColor);
        legLeft.setArcWidth(20);
        legLeft.setArcHeight(20);

        Rectangle legRight = new Rectangle(240, 178, 44, 64);
        legRight.setFill(bodyColor);

        Rectangle body = new Rectangle(140, 88, 144, 98);
        body.setFill(bodyColor);

        Rectangle leftHand = new Rectangle(108, 88, 34, 80);
        leftHand.setFill(bodyColor);

        Rectangle rightHand = new Rectangle(284, 88, 28, 74);
        rightHand.setFill(bodyColor);

        Rectangle head = new Rectangle(172, 40, 80, 48);
        head.setFill(bodyColor);

        Rectangle leftEye = new Rectangle(190, 54, 10, 7);
        leftEye.setFill(eyeColor);

        Rectangle rightEye = new Rectangle(224, 52, 9, 7);
        rightEye.setFill(eyeColor);

        Polygon leftEar = new Polygon(120, 20, 174, 4, 174, 46);
        leftEar.setFill(earColor);

        Polygon rightEar = new Polygon(250, 46, 310, 46, 250, 8);
        rightEar.setFill(earColor);

        root.getChildren().addAll(
                footLeft, footRight, legLeft, legRight, leftHand, rightHand,
                body, head, leftEye, rightEye, leftEar, rightEar);

        scene.setFill(Color.SILVER);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
