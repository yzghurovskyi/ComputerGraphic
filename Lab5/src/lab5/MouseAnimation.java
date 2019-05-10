package lab5;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.*;
import javax.vecmath.Vector3f;
import java.awt.event.*;

public class MouseAnimation extends KeyAdapter implements ActionListener {
    private static final float DELTA_DISTANCE = 0.02f;
    private static final float DELTA_ANGLE = 0.05f;

    private TransformGroup mouseTransformGroup;
    private Transform3D transform3D = new Transform3D();

    private float xLoc = 0;
    private float yLoc = 0;

    private boolean isRotatedPosY = false;
    private boolean isRotatedNegY = false;

    private boolean pressedW = false;
    private boolean pressedS = false;
    private boolean pressedA = false;
    private boolean pressedD = false;
    private boolean pressedVKRight = false;
    private boolean pressedVKLeft = false;
    private boolean pressedVKUp = false;
    private boolean pressedVKDown = false;

    MouseAnimation(Mouse mouse) {
        this.mouseTransformGroup = mouse.getMouseTransformGroup();
        this.mouseTransformGroup.getTransform(this.transform3D);

        Timer timer = new Timer(20, this);
        timer.start();
    }

    private void Move() {
        if (pressedW)
            yLoc += DELTA_DISTANCE;

        if (pressedS)
            yLoc -= DELTA_DISTANCE;

        if (pressedA)
            xLoc -= DELTA_DISTANCE;

        if (pressedD)
            xLoc += DELTA_DISTANCE;

        transform3D.setTranslation(new Vector3f(xLoc, yLoc, 0));

        if (pressedVKRight) {
            Transform3D rotation = new Transform3D();
            rotation.rotZ(DELTA_ANGLE);
            transform3D.mul(rotation);
        }

        if (pressedVKLeft) {
            Transform3D rotation = new Transform3D();
            rotation.rotZ(-DELTA_ANGLE);
            transform3D.mul(rotation);
        }

        if (pressedVKUp) {
            Transform3D rotation = new Transform3D();
            rotation.rotX(-DELTA_ANGLE);
            transform3D.mul(rotation);
        }

        if (pressedVKDown) {
            Transform3D rotation = new Transform3D();
            rotation.rotX(DELTA_ANGLE);
            transform3D.mul(rotation);
        }

        if (isRotatedPosY) {
            Transform3D rotation = new Transform3D();
            rotation.rotY(degree(20));
            transform3D.mul(rotation);

            isRotatedPosY = false;
        }

        if (isRotatedNegY) {
            Transform3D rotation = new Transform3D();
            rotation.rotY(degree(-20));
            transform3D.mul(rotation);

            isRotatedNegY = false;
        }

        mouseTransformGroup.setTransform(transform3D);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Move();
    }

    @Override
    public void keyPressed(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case 87: // W
                pressedW = true;
                break;
            case 83: // S
                pressedS = true;
                break;
            case 65: // A
                pressedA = true;
                break;
            case 68: // D
                pressedD = true;
                break;
            case KeyEvent.VK_LEFT:
                pressedVKLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                pressedVKRight = true;
                break;
            case KeyEvent.VK_UP:
                pressedVKUp = true;
                break;
            case KeyEvent.VK_DOWN:
                pressedVKDown = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case 87: // W
                pressedW = false;
                break;
            case 83: // S
                pressedS = false;
                break;
            case 65: // A
                pressedA = false;
                break;
            case 68: // D
                pressedD = false;
                break;
            case KeyEvent.VK_RIGHT:
                pressedVKRight = false;
                break;
            case KeyEvent.VK_LEFT:
                pressedVKLeft = false;
                break;
            case KeyEvent.VK_UP:
                pressedVKUp = false;
                break;
            case KeyEvent.VK_DOWN:
                pressedVKDown = false;
                break;
        }
    }

    private float degree(float degrees) {
        return (float) (degrees * Math.PI / 180);
    }
}
