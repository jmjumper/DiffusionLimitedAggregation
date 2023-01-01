package jmjumper.diffusionlimitedaggregation.Panels;

import javax.swing.*;
import java.awt.*;

public class Settings extends Component {

    private final VisualisationPanel visualisation;
    private final int width, height;
    private JPanel innerPanel;
    public Settings ( VisualisationPanel visualisation, int width, int height ) {
        this.visualisation = visualisation;
        this.width = width;
        // this.height = height / 8;
        this.height = height;

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(this.width, this.height));

        init();
    }

    private void init () {
        innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout());
        JLabel speedLabel = new JLabel("Speed: ");
    }

    public void draw ( Graphics2D g2d ) {

    }

}
