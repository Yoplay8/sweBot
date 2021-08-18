import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen {

    public BufferedImage captureScreen(int x, int y, int width, int height) {
        try {
            return new Robot().createScreenCapture(new Rectangle(x, y, width, height));
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return null;
    }
}
