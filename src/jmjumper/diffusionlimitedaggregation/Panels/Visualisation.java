package jmjumper.diffusionlimitedaggregation.Panels;

import javax.swing.*;
import java.awt.*;

public class Visualisation extends JFrame {

    private final VisualisationPanel visualisationPanel;
    private boolean running;
    public Visualisation ( int width, int height ) {
        super("Diffusion Limited Aggregation");
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        visualisationPanel = new VisualisationPanel( width, height);
        mainPanel.add(visualisationPanel);
        mainPanel.add(visualisationPanel.getSettingsComponent());
        add(mainPanel);

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
                while ( running ) {
                    visualisationPanel.update();
                }
                return null;
            }
        };

        swingWorker.execute();
    }

}
