package lab3;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

public class Smile extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);

        Color foreheadColor = Color.rgb(255, 250, 125);
        Color appleColor = Color.rgb(13, 167, 234);

        LinearGradient bodyGradient = new LinearGradient(0, 0, 0, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(246, 246, 24)),
                new Stop(1, Color.rgb(255, 153, 0)));

        Circle body = new Circle(300, 300, 100);
        body.setFill(bodyGradient);

        Ellipse forehead = new Ellipse(300, 245, 70, 30);
        forehead.setFill(foreheadColor);

        Arc leftWhite = new Arc(260, 295, 25, 45, 5, 180);
        leftWhite.setFill(Color.WHITE);

        Arc leftApple = new Arc(262, 294, 15, 23, 5, 180);
        leftApple.setFill(appleColor);

        Arc leftEye = new Arc(262, 294, 12, 18, 5, 180);

        Circle leftCrystalline = new Circle(268, 280, 3, Color.WHITE);

        QuadCurve leftEyebrow = new QuadCurve(235, 250, 258, 230, 270, 240);
        leftEyebrow.setFill(foreheadColor);
        leftEyebrow.setStroke(Color.BLACK);
        leftEyebrow.setStrokeWidth(3.5);

        Arc rightWhite = new Arc(340, 295, 25, 45, -5, 180);
        rightWhite.setFill(Color.WHITE);

        Arc rightApple = new Arc(340, 294, 15, 23, -5, 180);
        rightApple.setFill(appleColor);

        Arc rightEye = new Arc(340, 294, 12, 18, -5, 180);

        Circle rightCrystalline = new Circle(334, 280, 3, Color.WHITE);

        QuadCurve rightEyebrow = new QuadCurve(330, 240, 342, 230, 365, 250);
        rightEyebrow.setFill(foreheadColor);
        rightEyebrow.setStroke(Color.BLACK);
        rightEyebrow.setStrokeWidth(3.5);

        Shape lip = Shape.subtract(
                new Arc(300, 330, 60, 40, 0, -180),
                new Arc(300, 330, 60, 27, 0, -180));

        Shape teeth = Shape.subtract(
                new Arc(300, 330, 60, 27, 0, -180),
                new Arc(300, 330, 60, 15, 0, -180)
        );
        teeth.setFill(Color.WHITE);

        root.getChildren().addAll(
                body, forehead,
                leftWhite, leftApple, leftEye, leftCrystalline, leftEyebrow,
                rightWhite, rightApple, rightEye, rightCrystalline, rightEyebrow,
                lip, teeth);

        int transitionTimeSeconds = 5;

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(transitionTimeSeconds));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.1);

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(transitionTimeSeconds));
        rotateTransition.setByAngle(360);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(transitionTimeSeconds), root);
        scaleTransition.setToX(-2);
        scaleTransition.setToY(2);

        PathTransition pathTransition = new PathTransition(Duration.seconds(transitionTimeSeconds), getTrajectoryPath(), root);

        ParallelTransition parallelTransition = new ParallelTransition(root);
        parallelTransition.getChildren().addAll(fadeTransition, scaleTransition, rotateTransition, pathTransition);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.setAutoReverse(true);
        parallelTransition.play();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Lab3");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Path getTrajectoryPath() throws IOException {
        int numberOfPixels = 0;

        FileInputStream fileInputStream = new FileInputStream("./sources/111.bmp");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        HeaderBitmapImage image = new ReadingHeaderFromBitmapImage().Reading(bufferedInputStream);
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int half = (int) image.getHalfOfWidth();

        int let, let1, let2;
        char[][] map = new char[width][height];

        BufferedInputStream reader = new BufferedInputStream(new FileInputStream("pixels.txt"));

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < half; j++) {
                let = reader.read();
                let1 = (let & (0xf0)) >> 4;
                let2 = let & (0x0f);
                if (j * 2 < width) {
                    if (returnPixelColor(let1).equals("BLACK")) {
                        map[j * 2][height - 1 - i] = '1';
                        numberOfPixels++;
                    } else {
                        map[j * 2][height - 1 - i] = '0';
                    }
                }
                if (j * 2 + 1 < width) {
                    if (returnPixelColor(let2).equals("BLACK")) {
                        map[j * 2 + 1][height - 1 - i] = '1';
                        numberOfPixels++;
                    } else {
                        map[j * 2 + 1][height - 1 - i] = '0';
                    }
                }
            }
        }
        reader.close();

        int[][] black = new int[numberOfPixels][2];
        int lich = 0;

        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream("map.txt"));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++){
                if (map[j][i] == '1') {
                    black[lich][0] = j;
                    black[lich][1] = i;
                    lich++;
                }
                writer.write(map[j][i]);
            }
            writer.write(10);
        }
        writer.close();

        System.out.println("number of black color pixels = " + numberOfPixels);

        Path path = new Path();
        for (int l = 0; l < numberOfPixels - 1; l++) {
            path.getElements().addAll(new MoveTo(black[l][0], black[l][1]), new LineTo(black[l + 1][0], black[l + 1][1])
            );
        }
        return path;
    }

    private String returnPixelColor(int color)
    {
        String col = "BLACK";
        switch (color) {
            case 0: return "BLACK";     //BLACK;
            case 1: return "LIGHTCORAL";  //LIGHTCORAL;
            case 2: return "GREEN";     //GREEN
            case 3: return "BROWN";     //BROWN
            case 4: return "BLUE";      //BLUE;
            case 5: return "MAGENTA";   //MAGENTA;
            case 6: return "CYAN";      //CYAN;
            case 7: return "LIGHTGRAY"; //LIGHTGRAY;
            case 8: return "DARKGRAY";  //DARKGRAY;
            case 9: return "RED";       //RED;
            case 10: return "LIGHTGREEN";//LIGHTGREEN
            case 11: return "YELLOW";    //YELLOW;
            case 12: return "LIGHTBLUE"; //LIGHTBLUE;
            case 13: return "LIGHTPINK";    //LIGHTMAGENTA
            case 14: return "LIGHTCYAN";    //LIGHTCYAN;
            case 15: return "WHITE";    //WHITE;
        }
        return col;
    }
}
