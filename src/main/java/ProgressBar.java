import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgressBar {
    //progress bar
    public static void viewBar() {

        SwingGUI.progressBar.setStringPainted(true);
        SwingGUI.progressBar.setValue(0);

        int timerDelay = 10;
        new javax.swing.Timer(timerDelay , new ActionListener() {
            private int index = 0;
            private int maxIndex = 100;
            public void actionPerformed(ActionEvent e) {
                if (index < maxIndex) {
                    SwingGUI.progressBar.setValue(index);
                    index++;
                } else {
                    SwingGUI.progressBar.setValue(maxIndex);
                    ((javax.swing.Timer)e.getSource()).stop(); // stop the timer
                }
            }
        }).start();

        SwingGUI.progressBar.setValue(SwingGUI.progressBar.getMinimum());
    }
}

//end progress bar

