package jmjumper.diffusionlimitedaggregation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class displayPanel extends JPanel {

    private Gui gui;
    private Particle particle;
    Graphics2D g2d_old;

    public displayPanel(Gui gui) {
        this.gui = gui;
        particle = new Particle(this);
    }

    public void setupGui () {
        setPreferredSize(gui.getPreferredSize());
        setSize(gui.getSize());
        setBackground(Color.BLACK);
    }

    public void nextMove() {
        particle.generateDirection();
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        BufferedImage img = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);

        Graphics2D bufferedGraphics = null;
        try {
            bufferedGraphics = img.createGraphics();
            bufferedGraphics.setColor(Color.red);
            bufferedGraphics.fillRect(particle.getX(), particle.getY(), 5, 5);
        } finally {
            if (bufferedGraphics != null) {
                bufferedGraphics.dispose();
            }
        }

        g2d.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);
        g2d_old = (Graphics2D) g2d.getComposite();
    }

}
