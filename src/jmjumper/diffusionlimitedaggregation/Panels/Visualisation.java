package jmjumper.diffusionlimitedaggregation.Panels;

import javax.swing.*;
import java.awt.*;

public class Visualisation extends JFrame {

    private final VisualisationPanel mainPanel;
    private boolean running;
    public Visualisation ( int width, int height ) {
        super("Diffusion Limited Aggregation");
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        mainPanel = new VisualisationPanel( width, height);
        panel.add(mainPanel);
        // panel.add(mainPanel.getSettingsComponent());
        add(panel);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible( true );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
    }


    public void startVisualisation () {
        running = true;
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                while ( running )
                    mainPanel.update();
                return null;
            }
        };

        swingWorker.execute();
    }

}
