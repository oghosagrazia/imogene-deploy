package com.application.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GAInitPanel extends JPanel {

    // Singleton pattern
    private static GAInitPanel instance = new GAInitPanel();

    public static GAInitPanel getInstance() {
        if(instance == null)
            instance = new GAInitPanel();
        return instance;
    }

    public GAInitPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton btnInitialiseGA = new JButton("Initialise GA");
        btnInitialiseGA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calls helper method to display the correct card on the RightSidebar's child panel
                RightSidebar.showCard( "GA Params");
            }
        });

        add(btnInitialiseGA);

        btnInitialiseGA.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInitialiseGA.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnInitialiseGA.getPreferredSize().height));

    }

}
