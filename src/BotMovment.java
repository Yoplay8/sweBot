import java.awt.*;

public class BotMovment {
    Robot bot = null;

    BotMovment() {
        try {
            bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void moveMouse(int x, int y){
        bot.mouseMove(x, y);
    }

    public void clickButton(int key){
        bot.keyPress(key);
        bot.keyRelease(key);
    }

    public void clickMouse(int key){
        bot.mousePress(key);
        bot.mouseRelease(key);
    }

    public void pressKey(int key) {
        bot.keyPress(key);
    }

    public void releaseKey(int key) {
        bot.keyRelease(key);
    }
}
