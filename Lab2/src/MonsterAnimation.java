import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;

public class MonsterAnimation extends JPanel implements ActionListener {
    private static int maxWidth;
    private static int maxHeight;
    private Timer timer;

    private double angle = 0;
    private double scale = 1;
    private double delta = 0.01;

    public MonsterAnimation(){
        timer = new Timer(10, this);
        timer.start();
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        g2d.setBackground(new Color(192, 192, 192));
        g2d.clearRect(0, 0, maxWidth, maxHeight);

        g2d.translate(maxWidth / 2, maxHeight / 2);

        BasicStroke bs2 = new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2d.setStroke(bs2);
        g2d.drawRect(-320, -320, 640, 640);

        double[][] leftEarPoints = {{10, -5}, {64, -21}, {64, 21}};
        GeneralPath leftEar = new GeneralPath();
        leftEar.moveTo(leftEarPoints[0][0], leftEarPoints[0][1]);
        for (int k = 1; k < leftEarPoints.length; k++)
            leftEar.lineTo(leftEarPoints[k][0], leftEarPoints[k][1]);
        leftEar.closePath();

        double[][] rightEarPoints = {{140, 21}, {200, 21}, {140, -17}};
        GeneralPath rightEar = new GeneralPath();
        rightEar.moveTo(rightEarPoints[0][0], rightEarPoints[0][1]);
        for (int k = 1; k < rightEarPoints.length; k++)
            rightEar.lineTo(rightEarPoints[k][0], rightEarPoints[k][1]);
        rightEar.closePath();

        // Draw antenna.
        GradientPaint gp = new GradientPaint(
                -20, -20,
                new Color(2, 227, 255),
                100, 20,
                Color.BLUE,
                true
        );
        g2d.setPaint(gp);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) scale));
        g2d.rotate(angle, 10, -5);

        g2d.fill(leftEar);
        g2d.fill(rightEar);

        g2d.setColor(new Color(128, 0, 255));
        g2d.fillRect(30, 213, 70, 24);
        g2d.fillRect(130, 217,56,20);
        g2d.fillRect(30, 161, 54, 52);
        g2d.fillRect(130, 161, 44, 56);
        g2d.fillRect(30, 63 ,144, 98);
        g2d.fillRect(-2, 63, 32, 80);
        g2d.fillRect(174, 63, 28, 74);
        g2d.fillRect(62, 15, 80 ,48);

        g2d.setColor(Color.YELLOW);
        g2d.fillRect(80, 29, 10, 7);
        g2d.fillRect(114, 27, 9, 7);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 750);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(new MonsterAnimation());

        frame.setVisible(true);

        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (scale < 0.01 || scale > 0.99)
            delta = -delta;

        scale += delta;
        angle += 0.01;

        repaint();
    }
}
