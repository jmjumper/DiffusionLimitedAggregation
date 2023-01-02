package jmjumper.diffusionlimitedaggregation.Panels;

import jmjumper.diffusionlimitedaggregation.DiffusionLimitedAggregation;

import java.awt.*;

public class VisualisationPanel extends Component {

    private final int width, height;
    private final DiffusionLimitedAggregation dla;
    private final Settings settingsComponent;

    public VisualisationPanel ( int width, int height ) {
        this.width = width;
        this.height = height - height / 8;

        setPreferredSize(new Dimension(this.width, this.height));

        dla = new DiffusionLimitedAggregation(this ); // initialises algorithm
        settingsComponent = new Settings(dla, width, height);
    }

    public void update () {
        repaint();
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor( Color.BLACK );
        g2d.fillRect( 0, 0, width, height );
        dla.update();
        dla.draw(g2d);
    }

    public Component getSettingsComponent () {
        return settingsComponent;
    }
}
