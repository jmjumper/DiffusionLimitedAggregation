package jmjumper.diffusionlimitedaggregation.Components;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Node {

    private final Vector pos;
    private boolean finished;
    private final double radius;
    private Color color;
    private boolean isHead = false;
    private final int width, height;

    public Node (Vector pos, double radius, Color color, int width, int height ) {
        this.pos = pos;
        this.radius = radius;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public Node (Vector pos, double radius, Color color, boolean isHead, int width, int height ) {
        this.pos = pos;
        this.radius = radius;
        this.color = color;
        this.isHead = isHead;
        this.width = width;
        this.height = height;
    }

    // movement based on random walk
    public void move () {
        int velocityX = new Random().ints(-1, 2).findFirst().getAsInt();
        int velocityY = new Random().ints(-1, 2).findFirst().getAsInt();
        Vector vel = new Vector(velocityX, velocityY);
        Vector newPos = Vector.add(pos, vel);
        if (newPos.getX() <= width - vel.getX() && newPos.getX() > 0 && newPos.getY() <= height - vel.getY() && newPos.getY() > 0)
            pos.add(vel);
    }

    public boolean checkFinished ( ArrayList<Node> tree ) {
        for ( Node t : tree ) {
            // finished if the squared distance to another finished node is smaller
            // than the squared halved radii of both (finished and unfinished) nodes.
            if ( Vector.distanceSquared(pos, t.pos) < Math.pow((radius / 2) + (t.radius / 2), 2) )
                finished = true;
        }
        return finished;
    }

    public void draw (Graphics2D g2d ) {
        if ( !finished && !isHead )
            g2d.setColor(Color.WHITE);
        else
            g2d.setColor(color);
        g2d.translate( -radius / 2, -radius / 2);
        g2d.fillOval((int) pos.getX(), (int) pos.getY(), (int) radius, (int) radius);
        g2d.translate(radius / 2, radius / 2);
    }

    public void setColor ( Color color ) {
        this.color = color;
    }

    public Vector getPos() {
        return pos;
    }

    public double getRadius() {
        return radius;
    }
}
