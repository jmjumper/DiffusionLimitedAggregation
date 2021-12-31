package jmjumper.diffusionlimitedaggregation;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private boolean running;
    private displayPanel panel;

    public Gui () {
        super("Diffusion-limited aggregation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        panel = new displayPanel(this);
        add(panel);
        setVisible(true);
    }

    public void startingPoint () {
        running = true;
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                panel.setupGui();
                while (running) {
                    panel.repaint();
                    panel.nextMove();
                    Thread.sleep(10);
                }
                return null;
            }
        };

        swingWorker.execute();
    }

}
