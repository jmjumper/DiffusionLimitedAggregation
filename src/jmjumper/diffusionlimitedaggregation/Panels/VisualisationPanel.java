package jmjumper.diffusionlimitedaggregation.Panels;

import jmjumper.diffusionlimitedaggregation.Components.Node;
import jmjumper.diffusionlimitedaggregation.DiffusionLimitedAggregation;

import java.awt.*;
import java.util.ArrayList;

public class VisualisationPanel extends Component {

    private final int width, height;
    private final DiffusionLimitedAggregation dla;
    private final Settings settingsComponent;

    public VisualisationPanel ( int width, int height ) {
        this.width = width;
        // this.height = height - height / 8;
        this.height = height;

        setPreferredSize(new Dimension(this.width, this.height));

        settingsComponent = new Settings(this, width, height);
        dla = new DiffusionLimitedAggregation(this ); // initialises algorithm
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
        settingsComponent.draw(g2d);
    }

    public Component getSettingsComponent () {
        return settingsComponent;
    }
}
