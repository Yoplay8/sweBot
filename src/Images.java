import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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

    public String whichTriggered() throws IOException, InterruptedException {
        BufferedImage chat = screen.captureScreen(1275, 731, 204, 20);
        //ImageIO.write(chat, "png", new File("C:\\Users\\Tyler\\Downloads\\temp.png"));

        if(verticalePassed(drop, chat)) {
            return whichHorizontal(chat);
        }

        return "";
    }

    // TEMP CODE TO FIND NEW IMAGES
//    private void imageFinder(BufferedImage image1, BufferedImage image2) throws IOException, InterruptedException {
//        int count = 0;
//        do {
//            BufferedImage chat = screen.captureScreen(1275, 731, 204, 20);
//            if(verticalePassed(drop, chat)) {
//                ImageIO.write(chat, "png", new File("C:\\Users\\Tyler\\OneDrive\\Documents\\SweepBotImages_"+count));
//                count++;
//                TimeUnit.MILLISECONDS.sleep(2000);
//            }
//        }while(true);
//    }

    private boolean verticalePassed(BufferedImage image1, BufferedImage image2) {
        long diff = 0;

        offsetY = 0;
        for(int y1 = 0, y2 = 0; y1 < image1.getHeight() && y2 < image2.getHeight(); y2++) {
            int pixel1 = image1.getRGB(10, y1);
            int pixel2 = image2.getRGB(10, y2);
            ArrayList<Integer> rgb1 = getRgb(pixel1);
            ArrayList<Integer> rgb2 = getRgb(pixel2);

            if(!(isBackground(rgb2.get(0), rgb2.get(1), rgb2.get(2)))){
                if(offsetY == 0) {
                    offsetY = y2;
                }
                diff += getData(rgb1, rgb2);
                y1++;
            }
        }

        if(diff == 0) {
            return true;
        }

        return false;
    }

    private ArrayList<Integer> getRgb(int pixel) {
        ArrayList<Integer> rgb = new ArrayList<>();
        Color color1 = new Color(pixel, true);
        rgb.add(color1.getRed());
        rgb.add(color1.getGreen());
        rgb.add(color1.getBlue());

        return rgb;
    }

    private boolean isBackground(int r, int g, int b) {
        return r == 24 && g == 24 && b == 27;
    }

    private long getData(ArrayList<Integer> rgb1, ArrayList<Integer> rgb2) {
        return Math.abs(rgb1.get(0) - rgb2.get(0)) + Math.abs(rgb1.get(1) - rgb2.get(1)) + Math.abs(rgb1.get(2) - rgb2.get(2));
    }

    private double getDifference(BufferedImage img1, BufferedImage img2) {
        long diff = 0;

        for(int x = 0; x < 200 ; x++) {
            int pixel1 = img1.getRGB(x, 9);
            int pixel2 = img2.getRGB(x, 9+offsetY);
            ArrayList<Integer> rgb1 = getRgb(pixel1);
            ArrayList<Integer> rgb2 = getRgb(pixel2);

            diff += getData(rgb1, rgb2);
        }

        double avg = diff/(200*3);
        double percentage = (avg/255)*100;
        return percentage;
    }

    private String whichHorizontal(BufferedImage chat) {
        double diffPlinko = getDifference(plinko, chat);
        double diffDrop = getDifference(drop, chat);

        if(diffPlinko < 9 || diffDrop < 9) {
            if (diffPlinko < diffDrop) {
                return "p";
            } else {
                return "d";
            }
        }

        return "";
    }
}
