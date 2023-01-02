package jmjumper.diffusionlimitedaggregation.Panels;

import jmjumper.diffusionlimitedaggregation.DiffusionLimitedAggregation;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Settings extends JPanel implements ChangeListener, ItemListener, ActionListener {

    private final DiffusionLimitedAggregation dla;
    private final int width, height;
    private JPanel innerPanel;
    private double radius;
    private int maxNodes;
    private int speed;
    private boolean changeRadius;
    private boolean showUnfinished;
    private boolean fixedNodeSize;
    private boolean isPause;

    private JLabel radiusLabel;
    private JLabel maxNodeLabel;
    private JLabel speedLabel;

    private JSlider radiusSlider;
    private JSlider maxNodeSlider;
    private JSlider speedSlider;
    private JCheckBox changeRadiusCheckbox;
    private JCheckBox showUnfinishedCheckbox;
    private JCheckBox fixedNodeSizeCheckbox;
    private JButton pause;
    private JButton restart;

    public Settings ( DiffusionLimitedAggregation dla,  int width, int height ) {
        this.width = width;
        this.height = height / 8;
        this.dla = dla;

        isPause = false;
        radius = dla.getStandardRadius();
        maxNodes = dla.getMaxNodes();
        speed = dla.getSpeed();
        changeRadius = dla.isChangeRadius();
        showUnfinished = dla.isShowUnfinished();
        fixedNodeSize = dla.isFixedNodeSize();

        setPreferredSize(new Dimension(this.width, this.height));
        init();
    }

    private void init () {
        innerPanel = new JPanel();
        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        innerPanel.add( initSpeedGui(), c );

        c.gridx = 1;
        c.gridy = 0;
        innerPanel.add( initMaxNodeGui(), c );

        c.gridx = 2;
        c.gridy = 0;
        innerPanel.add( initRadiusPanel(), c );

        c.gridx = 3;
        c.gridy = 0;
        innerPanel.add( initCheckSettings(), c );

        add(innerPanel);
    }

    private JPanel initSpeedGui () {
        JPanel speedPanel = new JPanel(new GridLayout(2, 1));
        speedLabel = new JLabel("Speed: " + speed, JLabel.CENTER);
        speedPanel.add(speedLabel);
        speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 1200, speed);
        speedSlider.setMajorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.addChangeListener(this);
        speedPanel.add(speedSlider);
        return speedPanel;
    }

    private JPanel initMaxNodeGui () {
        JPanel maxNodePanel = new JPanel(new GridLayout(2, 1));
        maxNodeLabel = new JLabel("Max Nodes: " + maxNodes, JLabel.CENTER);
        maxNodePanel.add(maxNodeLabel);
        maxNodeSlider = new JSlider(JSlider.HORIZONTAL, 1, 200, maxNodes);
        maxNodeSlider.setMajorTickSpacing(100);
        maxNodeSlider.setPaintTicks(true);
        maxNodeSlider.addChangeListener(this);
        maxNodePanel.add(maxNodeSlider);
        return maxNodePanel;
    }

    private JPanel initRadiusPanel () {
        JPanel radiusPanel = new JPanel(new GridLayout(2, 1));
        radiusLabel = new JLabel("Size: " + radius, JLabel.CENTER);
        radiusPanel.add(radiusLabel);
        radiusSlider = new JSlider(JSlider.HORIZONTAL, 1, 15, (int) radius);
        radiusSlider.setMajorTickSpacing(5);
        radiusSlider.setPaintTicks(true);
        radiusSlider.addChangeListener(this);
        radiusPanel.add(radiusSlider);
        return radiusPanel;
    }

    private JPanel initCheckSettings () {
        JPanel checkboxPanel = new JPanel(new GridLayout(3, 2));
        changeRadiusCheckbox = new JCheckBox("fixed size", false);
        changeRadiusCheckbox.addItemListener(this);

        showUnfinishedCheckbox = new JCheckBox("show unfinished", true);
        showUnfinishedCheckbox.addItemListener(this);

        fixedNodeSizeCheckbox = new JCheckBox("fixed node amount", true);
        fixedNodeSizeCheckbox.addItemListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        pause = new JButton("Pause");
        pause.addActionListener(this);
        restart = new JButton("Restart");
        restart.addActionListener(this);
        buttonPanel.add(pause);
        buttonPanel.add(restart);

        // checkboxPanel.add(changeRadiusCheckbox);
        checkboxPanel.add(showUnfinishedCheckbox);
        checkboxPanel.add(fixedNodeSizeCheckbox);
        checkboxPanel.add(buttonPanel);
        return checkboxPanel;
    }

    private void updateSpeedLabel () {
        speedLabel.setText("Speed: " + speed);
    }

    private void updateRadiusLabel () {
        radiusLabel.setText("Radius: " + radius);
    }

    private void updateMaxNodesLabel () {
        maxNodeLabel.setText("Max Nodes: " + maxNodes);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            if (source == radiusSlider) {
                radius = source.getValue();
                dla.setStandardRadius(radius);
                updateRadiusLabel();
            } else if (source == maxNodeSlider) {
                maxNodes = source.getValue();
                dla.setMaxNodes(maxNodes);
                updateMaxNodesLabel();
            } else if (source == speedSlider) {
                speed = source.getValue();
                dla.setSpeed(speed);
                updateSpeedLabel();
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if ( e.getSource() == changeRadiusCheckbox ) {
            changeRadius = changeRadiusCheckbox.isSelected();
            dla.setChangeRadius(changeRadius);
        } else if ( e.getSource() == showUnfinishedCheckbox ) {
            showUnfinished = showUnfinishedCheckbox.isSelected();
            dla.setShowUnfinished(showUnfinished);
        } else if ( e.getSource() == fixedNodeSizeCheckbox ) {
            fixedNodeSize = fixedNodeSizeCheckbox.isSelected();
            dla.setFixedNodeSize(fixedNodeSize);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == pause ) {
            isPause = !isPause;
            dla.pause( isPause );
        } else if ( e.getSource() == restart ) {
            dla.restart();
        }
    }
}
