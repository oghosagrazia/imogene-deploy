package com.application.panels;

import com.GA.fitness.*;
import com.GA.mutation.*;

import javax.swing.*;
import java.awt.*;

public class RightSidebar extends JPanel {

    // Card layout elements visible to other application panels
    protected static final CardLayout layout = new CardLayout();

    // Singleton pattern
    private static final RightSidebar instance = new RightSidebar();

    public static RightSidebar getInstance() {
        return instance;
    }

    // Canvas sizing control fields
    private JTextField txtWidth;
    private JTextField txtHeight;
    private JTextField txtScale;
    private JButton btnApplyCanvas;

    private final JPanel gaCards;

    public RightSidebar() {
        super(new BorderLayout());
        // GA Panels Section
        this.gaCards = new JPanel(layout);

        // "Initialise GA" panel button
        JPanel pnlInitialiseGA = GAInitPanel.getInstance();
        // GA hyperparameters panel
        JPanel pnlGAHyperparameters = GAParametersPanel.getInstance();
        // GA generations and control panel buttons
        JPanel pnlGenerations = GAGenerationsPanel.getInstance();

        // Add panels to the right sidebar border layout
        gaCards.add(pnlInitialiseGA, "GA Init");
        gaCards.add(pnlGAHyperparameters, "GA Params");
        gaCards.add(pnlGenerations, "GA Generations");

        // Display
        layout.show(gaCards, "GA Init");

//---------------------------------------------------------------------------------------------//
        // Canvas sizing setting
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        controls.add(Box.createVerticalStrut(10));
        controls.add(new JSeparator(SwingConstants.HORIZONTAL));

        JLabel lblCanvas = new JLabel("Custom Canvas Size");
        controls.add(lblCanvas);

        // Width
        JPanel rowWidth = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        rowWidth.setAlignmentX(Component.CENTER_ALIGNMENT);
        rowWidth.add(new JLabel("Width:"));
        txtWidth = new JTextField(String.valueOf(ImageScreen.currentImageWidth), 5);
        rowWidth.add(txtWidth);
        controls.add(rowWidth);

        // Height
        JPanel rowHeight = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        rowHeight.setAlignmentX(Component.CENTER_ALIGNMENT);
        rowHeight.add(new JLabel("Height:"));
        txtHeight = new JTextField(String.valueOf(ImageScreen.currentImageHeight), 5);
        rowHeight.add(txtHeight);
        controls.add(rowHeight);

        // Scale
        JPanel rowScale = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        rowScale.setAlignmentX(Component.CENTER_ALIGNMENT);
        rowScale.add(new JLabel("Scale:"));
        txtScale = new JTextField("6", 5);
        rowScale.add(txtScale);
        controls.add(rowScale);

        // Apply button
        btnApplyCanvas = new JButton("Apply");
        btnApplyCanvas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnApplyCanvas.addActionListener(e -> {
            try {
                int w = Integer.parseInt(txtWidth.getText().trim());
                int h = Integer.parseInt(txtHeight.getText().trim());
                int s = Integer.parseInt(txtScale.getText().trim());

                ImageScreen screen = ImageScreen.getInstance();
                screen.setCustomDimensions(w, h);
                screen.setUpScale(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid integers.");
            }
        });
    controls.add(btnApplyCanvas);

    // Adds cards to border layout
        add(gaCards, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
    }
    public static void showCard(String name) {
        RightSidebar rs = getInstance();
        layout.show(rs.gaCards, name);
        rs.gaCards.revalidate();
        rs.gaCards.repaint();
    }
}