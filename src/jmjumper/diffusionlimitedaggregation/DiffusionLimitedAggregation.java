package jmjumper.diffusionlimitedaggregation;

import jmjumper.diffusionlimitedaggregation.Components.Node;
import jmjumper.diffusionlimitedaggregation.Components.Vector;
import jmjumper.diffusionlimitedaggregation.Panels.VisualisationPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DiffusionLimitedAggregation {

    private final VisualisationPanel panel;
    private final int width, height;
    private ArrayList<Node> tree;
    private ArrayList<Node> nodes;
    private double radius;
    private int maxNodes;
    private int speed;
    private boolean changeRadius;
    private double radiusFactor;
    private Color startColor;
    private double r, g, b;
    private boolean showUnfinished;

    public DiffusionLimitedAggregation (VisualisationPanel panel  ) {
        this.panel = panel;
        this.width = (int) panel.getPreferredSize().getWidth();
        this.height = (int) panel.getPreferredSize().getHeight();
        initDLA();
    }

    private void initDLA () {
        tree = new ArrayList<>();
        nodes = new ArrayList<>();
        radius = 10;
        maxNodes = 200;
        speed = 100;
        changeRadius = true;
        radiusFactor = 0.998;
        startColor = new Color(255, 5, 1);
        r = startColor.getRed();
        g = startColor.getGreen();
        b = startColor.getBlue();
        showUnfinished = true;

        while (nodes.size() < maxNodes && radius > 1) {
            adjustColor();
            radius *= radiusFactor;
            nodes.add(new Node(randomPosition(), radius, startColor, width, height));
        }

        Vector center = new Vector(panel.getPreferredSize().getWidth() / 2, panel.getPreferredSize().getHeight() / 2);
        tree.add( new Node(center, radius, startColor, true, width, height) );
    }

    public void update () {
        fillNodes();

        for ( int i = 0; i < speed; i++ ) {
            Iterator<Node> iter = nodes.iterator();
            while ( iter.hasNext() ) {
                Node n = iter.next();
                n.move();
                if (n.checkFinished(tree)) {
                    tree.add(n);
                    iter.remove();
                }
            }
        }
    }

    public void draw ( Graphics2D g2d ) {
        for (Node n : tree) {
            n.draw(g2d);
        }

        if ( showUnfinished ) {
            for (Node n : nodes) {
                n.draw(g2d);
            }
        }
    }

    private void fillNodes () {

        startColor = new Color((int) r, (int) g, (int) b);
        if ( changeRadius ) {
            radius = nodes.get(nodes.size() - 1).getRadius();
            while (nodes.size() < maxNodes && radius > 1) {
                adjustColor();
                radius *= radiusFactor;
                nodes.add(new Node(randomPosition(), radius, startColor, width, height));
            }
        } else {
            while (nodes.size() < maxNodes) {
                adjustColor();
                nodes.add(new Node(randomPosition(), radius, startColor, width, height));
            }
        }
    }

    private Vector randomPosition () {
        int x = (int) (Math.random() * panel.getPreferredSize().getWidth());
        int y = (int) (Math.random() * panel.getPreferredSize().getHeight());
        return new Vector(x, y);
    }

    private void adjustColor () {
        double rGrowth = 0.999;
        if ( r * rGrowth < 255 )
            r *= rGrowth;
        double gGrowth = 1.015;
        if ( g * gGrowth < 255 )
            g *= gGrowth;
        startColor = new Color((int) r, (int) g, (int) b);
    }


}
