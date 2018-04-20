package app;

import app.factory.DeploymentTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class Settings extends JFrame implements ActionListener {

    private JLabel labelUsername = new JLabel("Enter username: ");
    private JTextField textUsername = new JTextField(20);
    private JButton buttonLogin = new JButton("Login");
    private List<String> strings;
    private JComboBox petList;


    Settings () {
        super("JPanel Demo Program");

        strings = new ArrayList<>();
        Collections.addAll(strings, DeploymentTypes.getDeploymentTypes());

        petList = new JComboBox(DeploymentTypes.getDeploymentTypes());
        // create a new panel with GridBagLayout manager
        JPanel newPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(50, 5, 5, 5);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(labelUsername, constraints);

        constraints.gridx = 1;
        newPanel.add(textUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        //newPanel.add(buttonLogin, constraints);

        //Create the combo box, select item at index 4.
        //Indices start at 0, so 4 specifies the pig.
        petList.setSelectedIndex(0);
        petList.addActionListener(this);
        newPanel.add(petList, constraints);

        // set border for the panel
        newPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Login Panel"));

        // add the panel to this frame
        add(newPanel);

        pack();
        setLocationRelativeTo(null);
    }

    public void actionPerformed (ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String petName = (String) cb.getSelectedItem();
        petList.setSelectedIndex(strings.indexOf(petName));
    }
}