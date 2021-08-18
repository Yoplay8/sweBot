import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipBoard {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public void setClipboard(String contents) {
        clipboard.setContents(new StringSelection(contents), null);
    }
}
