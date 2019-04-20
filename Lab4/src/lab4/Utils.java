package lab4;

import com.sun.j3d.utils.image.TextureLoader;

import javax.imageio.ImageIO;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utils {
    public static Image loadImage(String fileName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return img;
    }

    public static Appearance loadTexture(String fileName, boolean emit) {
        Appearance app = new Appearance();

        TextureLoader loader = new TextureLoader(Utils.loadImage(fileName), null);
        Texture2D texture = (Texture2D)loader.getTexture();
        texture.setMinFilter(texture.BASE_LEVEL_LINEAR);
        texture.setMagFilter(texture.BASE_LEVEL_LINEAR);

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        app.setTextureAttributes(texAttr);

        app.setTexture(texture);

        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f black = new Color3f(0f, 0f, 0f);
        if (emit)
            app.setMaterial(new Material(black, white, black, black, 4.0f));
        else
            app.setMaterial(new Material(white, black, white, white, 4.0f));

        return app;
    }
}
