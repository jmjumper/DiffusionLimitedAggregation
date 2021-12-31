package jmjumper.diffusionlimitedaggregation;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Vector;

public class Particle {

    private displayPanel panel;

    private int x, y;

    public Particle (displayPanel panel) {
        this.panel = panel;
        x = 800 / 2;
        y = 800 / 2;
    }

    public void generateDirection () {
        Random r = new Random();
        int direction = r.nextInt(0, 4);
        switch (direction) {
            case 0 -> x += 1;
            case 1 -> x -= 1;
            case 2 -> y += 1;
            case 3 -> y -= 1;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
