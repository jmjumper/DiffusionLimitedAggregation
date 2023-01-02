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
    private ArrayList<Node> tree; // finished nodes
    private ArrayList<Node> nodes; // unfinished nodes
    private double radius;
    private double standardRadius;
    private double minRadius;
    private double radiusFactor;
    private boolean changeRadius;
    private int maxNodes;
    private int speed;
    private Color startColor;
    private double r, g, b;
    private boolean showUnfinished;
    private boolean fixedNodeSize;
    private boolean isPause;

    public DiffusionLimitedAggregation (VisualisationPanel panel  ) {
        this.panel = panel;
        this.width = (int) panel.getPreferredSize().getWidth();
        this.height = (int) panel.getPreferredSize().getHeight();
        initDLA();
    }

    private void initDLA () {
        tree = new ArrayList<>();
        nodes = new ArrayList<>();
        standardRadius = 15;
        radius = standardRadius;
        minRadius = 5;
        maxNodes = 400;
        speed = 1000;
        radiusFactor = 0.9985;
        changeRadius = true;
        showUnfinished = true;
        fixedNodeSize = false;
        isPause = false;
        startColor = new Color(255, 5, 1);
        r = startColor.getRed();
        g = startColor.getGreen();
        b = startColor.getBlue();

        // initialize all nodes
        while (nodes.size() < maxNodes) {
            if ( radius > minRadius)
                radius *= radiusFactor;
            nodes.add(new Node(randomPosition(), radius, startColor, width, height));
        }

        // create root in center
        Vector center = new Vector(panel.getPreferredSize().getWidth() / 2, panel.getPreferredSize().getHeight() / 2);
        tree.add( new Node(center, radius, startColor, true, width, height) );
    }

    public void update () {
        if ( isPause ) return;
        fillNodes();

        for ( int i = 0; i < speed; i++ ) {
            Iterator<Node> iter = nodes.iterator();
            while ( iter.hasNext() ) {
                Node n = iter.next();
                n.move();
                if (n.checkFinished(tree)) {
                    double colorFactor = Vector.distance(n.getPos(), tree.get(0).getPos());
                    adjustColor(colorFactor);
                    n.setColor(startColor);
                    tree.add(n);
                    iter.remove();
                }
            }
        }
    }

    public void draw ( Graphics2D g2d ) {
        for ( Node n : tree ) {
            n.draw(g2d);
        }

        if ( showUnfinished ) {
            for ( Node n : nodes ) {
                n.draw(g2d);
            }
        }
    }

    private void fillNodes () {
        if ( !fixedNodeSize ) return;
        startColor = new Color((int) r, (int) g, (int) b);
        if ( changeRadius ) {
            if ( nodes.size() > 1 ) radius = nodes.get(nodes.size() - 1).getRadius();
            while (nodes.size() < maxNodes) {
                if ( radius > minRadius)
                    radius *= radiusFactor;
                nodes.add(new Node(randomPosition(), radius, startColor, width, height));
            }
        } else { // if radius is fixed
            while (nodes.size() < maxNodes) {
                nodes.add(new Node(randomPosition(), radius, startColor, width, height));
            }
        }
    }

    // Generates a new position on one of the four edges.
    private Vector randomPosition () {
        Vector v;

        int randomChoice = (int) (Math.random() * 4);
        int x = (int) (Math.random() * panel.getPreferredSize().getWidth());
        int y = (int) (Math.random() * panel.getPreferredSize().getHeight());

        switch (randomChoice) {
            case 0: v = new Vector(0, y); break;
            case 1: v = new Vector((int) (width - (2 * radius)), y); break;
            case 2: v = new Vector(x, 0); break;
            case 3: v = new Vector(x, height - 2*radius); break;
            default: v = new Vector(0, 0);
        }

        return v;
    }

    // Color change is based on the distance to the origin node.
    private void adjustColor ( double distanceColor ) {
        // 0.5 may be changed with a desired factor each.
        double rGrowth = 255 / Math.max( width, height * 0.5);
        double gGrowth = 255 / Math.max( width, height * 0.5);
        double bGrowth = 0;

        int newR = (int) (r - (distanceColor * rGrowth));
        int newG = (int) (g + (distanceColor * gGrowth));
        int newB = (int) (b + (distanceColor * bGrowth));

        if ( newR >= 0 )
            startColor = new Color( newR, startColor.getGreen(), newB );
        if ( newG <= 255 )
            startColor = new Color( startColor.getRed(), newG, newB );
    }

    public void restart () {
        tree.clear();
        nodes.clear();
        Vector center = new Vector(panel.getPreferredSize().getWidth() / 2, panel.getPreferredSize().getHeight() / 2);
        radius = standardRadius;
        startColor = new Color(255, 5, 1);
        tree.add( new Node(center, radius, startColor, true, width, height) );
        while (nodes.size() < maxNodes && radius > minRadius) {
            radius *= radiusFactor;
            nodes.add(new Node(randomPosition(), radius, startColor, width, height));
        }
    }

    public void pause ( boolean p ) {
       isPause = p;
    }


    // getter and setter

    public double getStandardRadius() {
        return standardRadius;
    }

    public void setStandardRadius(double radius) {
        this.standardRadius = radius;
    }

    public boolean isChangeRadius() {
        return changeRadius;
    }

    public void setChangeRadius(boolean changeRadius) {
        this.changeRadius = changeRadius;
    }

    public int getMaxNodes() {
        return maxNodes;
    }

    public void setMaxNodes(int maxNodes) {
        this.maxNodes = maxNodes;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isShowUnfinished() {
        return showUnfinished;
    }

    public void setShowUnfinished(boolean showUnfinished) {
        this.showUnfinished = showUnfinished;
    }

    public boolean isFixedNodeSize() {
        return fixedNodeSize;
    }

    public void setFixedNodeSize(boolean fixedNodeSize) {
        this.fixedNodeSize = fixedNodeSize;
    }
}
