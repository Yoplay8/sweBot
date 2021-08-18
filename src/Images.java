import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Images {
    Screen screen = new Screen();

    BufferedImage plinko;
    BufferedImage drop;

    int offsetY = 0;

    Images() {
        plinko = getBufferedImage("plinko3_E.PNG");
        drop = getBufferedImage("drop3_E.PNG");
    }

    public BufferedImage getBufferedImage(String fileName){
        try {
            return ImageIO.read(getAbsolutePath(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private URL getAbsolutePath(String fileName) {
        return getClass().getClassLoader().getResource(fileName);
    }

    public String whichTriggered() {
        BufferedImage chat = screen.captureScreen(1275, 731, 204, 20);
        //ImageIO.write(chat, "png", new File("C:\\Users\\Tyler\\Downloads\\temp.png"));

        if(verticalePassed(drop, chat)) {
            return whichHorizontal(plinko, drop, chat);
        } else {
            return "";
        }
    }

    private boolean verticalePassed(BufferedImage image1, BufferedImage image2) {
        long diff = 0;

        offsetY = 0;
        for(int y1 = 0, y2 = 0; y1 < image1.getHeight() && y2 < image2.getHeight(); y2++) {
            //Getting the RGB values of a pixel
            int pixel1 = image1.getRGB(10, y1);
            Color color1 = new Color(pixel1, true);
            int r1 = color1.getRed();
            int g1 = color1.getGreen();
            int b1 = color1.getBlue();
            int pixel2 = image2.getRGB(10, y2);
            Color color2 = new Color(pixel2, true);
            int r2 = color2.getRed();
            int g2 = color2.getGreen();
            int b2 = color2.getBlue();

            if(!(r2 == 24 && g2 == 24 && b2 == 27)){
                if(offsetY == 0) {
                    offsetY = y2;
                }
                long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                diff += data;
                y1++;
            }
        }

        if(diff == 0) {
            return true;
        }

        return false;
    }

    private String whichHorizontal(BufferedImage plinko, BufferedImage drop, BufferedImage chat) {
        long diff = 0;

        for(int x = 0; x < 200 ; x++) {
            //Getting the RGB values of a pixel
            int pixel1 = plinko.getRGB(x, 9);
            Color color1 = new Color(pixel1, true);
            int r1 = color1.getRed();
            int g1 = color1.getGreen();
            int b1 = color1.getBlue();
            int pixel2 = chat.getRGB(x, 9+offsetY);
            Color color2 = new Color(pixel2, true);
            int r2 = color2.getRed();
            int g2 = color2.getGreen();
            int b2 = color2.getBlue();

            long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
            diff += data;
        }

        if (diff == 0) {
            return "p";
        }

        diff = 0;
        for(int x = 0; x < 200 ; x++) {
            //Getting the RGB values of a pixel
            int pixel1 = drop.getRGB(x, 9);
            Color color1 = new Color(pixel1, true);
            int r1 = color1.getRed();
            int g1 = color1.getGreen();
            int b1 = color1.getBlue();
            int pixel2 = chat.getRGB(x, 9+offsetY);
            Color color2 = new Color(pixel2, true);
            int r2 = color2.getRed();
            int g2 = color2.getGreen();
            int b2 = color2.getBlue();

            long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
            diff += data;
        }

        if (diff == 0) {
            return "d";
        }

        return "";
    }
}
