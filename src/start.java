import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.awt.event.KeyEvent.*;

public class start {
    // Monitor only where chat is at
        // Trigger on resets only by streamlabs
                // Type in both commands then seconds later one of the 2 commands I need to retype. Makes me look like a bot.
                // Tried typing in the same message twice in chat.
            // Reset drop --- Make ticks if missed --- add random delays
                // Type in chat to drop
            // Reset plinko
                // Type in chat to plinko

        // Trigger some bank heists
            // After some time. Or jump in when other start one.
                // Issue running heist seconds after someone else litterally just finished.

    // Trigger on channel points chest
            // click on chest
                // Clicks after each command. Not a high priority to make this optimal.
                // Constantly monitor that part of the screen till it changes

    public static void main(String args[]) {
        try {
            Images image =  new Images();
            BotMovment bot = new BotMovment();
            ClipBoard clipboard = new ClipBoard();

            String dropCommand = "!drop", plinkoCommand = "!plinko", heistCommand = "!bankheist 1000";

            int tick = 500;
            //int countDownHeist = 10*48*(1000/tick);
            int missedDrop = resetTimer(0,  40, tick);
            int missedPlinko = resetTimer(0,  40, tick);

            while(true){
                milliSecondSleep(tick);
//                countDownHeist--;
//
//                if(countDownHeist < 0) {
//                    countDownHeist = 10*48*(1000/tick);
//                    printText(heistCommand, bot, clipboard);
//                }

                missedDrop--;
                if(missedDrop < 0) {
                    missedDrop = resetTimer(2,  0, tick);
                    printText(dropCommand, bot, clipboard);
                }

                missedPlinko--;
                if(missedPlinko < 0) {
                    missedPlinko = resetTimer(2,  0, tick);
                    printText(plinkoCommand, bot, clipboard);
                }

                switch(image.whichTriggered()) {
                    case "d":
                        printText(dropCommand, bot, clipboard);
                        missedDrop = resetTimer(3,  0, tick);
                        break;
                    case "p":
                        printText(plinkoCommand, bot, clipboard);
                        missedPlinko = resetTimer(3,  0, tick);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void milliSecondSleep(int milliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int resetTimer(int minutes, int seconds, int tickPerSecond) {
        return ((60*minutes)+seconds)*(1000/tickPerSecond);
    }

    private static void printText(String text, BotMovment bot, ClipBoard clipboard) {
        Random rand = new Random();
        int randDelay = rand.nextInt(10000);
        clipboard.setClipboard(text);

        bot.moveMouse(1310, 785);
        bot.clickMouse(BUTTON1_MASK);
        bot.pressKey(VK_CONTROL);
        bot.pressKey(VK_V);
        bot.releaseKey(VK_CONTROL);
        bot.releaseKey(VK_V);
        milliSecondSleep(4000+randDelay);
        bot.clickButton(VK_ENTER);
        milliSecondSleep(1000);

        clickChest(bot);
    }

    private static void clickChest(BotMovment bot) {
        bot.moveMouse(1355, 835);
        milliSecondSleep(100);
        bot.clickMouse(BUTTON1_MASK);
    }
}
