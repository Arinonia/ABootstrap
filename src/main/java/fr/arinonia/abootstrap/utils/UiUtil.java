package fr.arinonia.abootstrap.utils;

import fr.arinonia.abootstrap.Bootstrap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @author Arinonia
 * Created at 18/01/2022 - 01:30
 **/
public class UiUtil {
    /**
     *
     * @param path String
     * @return BufferedImage
     */
    public static BufferedImage getImage(String path){
        BufferedImage image = null;
        try {
            image = ImageIO.read(Bootstrap.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     *
     * @param path String
     * @return ImageIcon
     */
    public static ImageIcon getIconImage(final String path){
        final URL url = Bootstrap.class.getResource(path);

        if (url == null){
            System.err.println("Unable to load resource " + path);
            return null;
        }
        return new ImageIcon(url);
    }
}
