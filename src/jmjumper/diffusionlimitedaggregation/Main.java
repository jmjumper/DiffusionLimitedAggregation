package jmjumper.diffusionlimitedaggregation;

import jmjumper.diffusionlimitedaggregation.Panels.Visualisation;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        int width = 800, height = 800;
        SwingUtilities.invokeLater(() -> new Visualisation(width, height).startVisualisation());
    }

}
