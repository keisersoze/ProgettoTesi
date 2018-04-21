package app;

import app.factory.DeploymentTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SuppressWarnings("unchecked")
class Settings extends JPanel implements ActionListener, PropertyChangeListener {

    //Formats to format and parse numbers
    private NumberFormat amountFormat;

    //Labels to identify the fields
    private JLabel labelSamples;
    private JLabel labelThread;
    private JLabel labelSensorBandwidth;
    private JLabel labelMaxFrameSize;
    private JLabel labelRateMaxFrame;
    private JLabel labelThreshod;
    private JLabel labelSensibility;
    private JLabel labelPower;
    private JLabel labelFrequency;
    private JLabel labelLambda;

    private JFormattedTextField valSamples;
    private JFormattedTextField valThread;
    private JFormattedTextField valSensorBandwidth;
    private JFormattedTextField valMaxFrameSize;
    private JFormattedTextField valRateMaxFrame;
    private JFormattedTextField valThreshold;
    private JFormattedTextField valSensibility;
    private JFormattedTextField valPower;
    private JFormattedTextField valFrequency;
    private JFormattedTextField valLambda;

    private JButton buttonStart = new JButton("Start");
    private JButton buttonStop = new JButton("Stop");

    private JComboBox deployType = new JComboBox(DeploymentTypes.getDeploymentTypes());
    private JComboBox graphicMode = new JComboBox(new String[]{"Graphic Mode", "Stats mode"});
    private List<String> deployStrings = new ArrayList<>();
    private List<String> graphicStrings = new ArrayList<>();


    private Settings () {
        super();
        setUpFormats();

        labelSamples = new JLabel("Number of Samples: ");
        labelThread = new JLabel("Number of Thread: ");
        labelSensorBandwidth = new JLabel("Sensor Bandwidth (b/s): ");
        labelMaxFrameSize = new JLabel("Max Frame Size (b): ");
        labelRateMaxFrame = new JLabel("Rate of frame with max size (0-1): ");
        labelThreshod = new JLabel("Threshold (only 1, 3 protocols): ");
        labelSensibility = new JLabel("Sensibility of sensors (dbm): ");
        labelPower = new JLabel("Power of sensors (dbm): ");
        labelFrequency = new JLabel("Frequency of sensors (Hz): ");
        labelLambda = new JLabel("Lambda: ");


        valSamples = new JFormattedTextField(amountFormat);
        valSamples.setValue(H20Sim.N_SAMPLES);
        valSamples.setColumns(10);
        valSamples.addPropertyChangeListener("value", this);

        valThread = new JFormattedTextField(amountFormat);
        valThread.setValue(H20Sim.NTHREADS);
        valThread.setColumns(3);
        valThread.addPropertyChangeListener("value", this);

        valSensorBandwidth = new JFormattedTextField(amountFormat);
        valSensorBandwidth.setValue(H20Sim.SENSOR_BANDWIDTH);
        valSensorBandwidth.setColumns(10);
        valSensorBandwidth.addPropertyChangeListener("value", this);

        valMaxFrameSize = new JFormattedTextField(amountFormat);
        valMaxFrameSize.setValue(H20Sim.MAX_FRAME_SIZE);
        valMaxFrameSize.setColumns(10);
        valMaxFrameSize.addPropertyChangeListener("value", this);

        valRateMaxFrame = new JFormattedTextField(amountFormat);
        valRateMaxFrame.setValue(H20Sim.MAX_FRAME_RATE);
        valRateMaxFrame.setColumns(5);
        valRateMaxFrame.addPropertyChangeListener("value", this);

        valThreshold = new JFormattedTextField(amountFormat);
        valThreshold.setValue(H20Sim.THRESHOLD);
        valThreshold.setColumns(3);
        valThreshold.addPropertyChangeListener("value", this);

        valSensibility = new JFormattedTextField(amountFormat);
        valSensibility.setValue(H20Sim.SENSIBILITY);
        valSensibility.setColumns(3);
        valSensibility.addPropertyChangeListener("value", this);

        valPower = new JFormattedTextField(amountFormat);
        valPower.setValue(H20Sim.SENSOR_POWER);
        valPower.setColumns(3);
        valPower.addPropertyChangeListener("value", this);

        valFrequency = new JFormattedTextField(amountFormat);
        valFrequency.setValue(H20Sim.SENSOR_FREQUENCY);
        valFrequency.setColumns(3);
        valFrequency.addPropertyChangeListener("value", this);

        valLambda = new JFormattedTextField(amountFormat);
        valLambda.setValue(H20Sim.LAMDA);
        valLambda.setColumns(15);
        valLambda.addPropertyChangeListener("value", this);

        labelSamples.setLabelFor(valSamples);
        labelThread.setLabelFor(valThread);
        labelSensorBandwidth.setLabelFor(valSensorBandwidth);
        labelMaxFrameSize.setLabelFor(valMaxFrameSize);
        labelRateMaxFrame.setLabelFor(valRateMaxFrame);
        labelThreshod.setLabelFor(valThreshold);
        labelSensibility.setLabelFor(valSensibility);
        labelPower.setLabelFor(valPower);
        labelFrequency.setLabelFor(valFrequency);
        labelLambda.setLabelFor(valLambda);


        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        gridPanel.add(labelSamples);
        gridPanel.add(valSamples);

        gridPanel.add(labelThread);
        gridPanel.add(valThread);

        gridPanel.add(labelSensorBandwidth);
        gridPanel.add(valSensorBandwidth);

        gridPanel.add(labelMaxFrameSize);
        gridPanel.add(valMaxFrameSize);

        gridPanel.add(labelRateMaxFrame);
        gridPanel.add(valRateMaxFrame);

        gridPanel.add(labelThreshod);
        gridPanel.add(valThreshold);

        gridPanel.add(labelSensibility);
        gridPanel.add(valSensibility);

        gridPanel.add(labelPower);
        gridPanel.add(valPower);

        gridPanel.add(labelFrequency);
        gridPanel.add(valFrequency);

        gridPanel.add(labelLambda);
        gridPanel.add(valLambda);

        deployType.setSelectedIndex(0);
        deployType.addActionListener(this);
        gridPanel.add(deployType);

        graphicMode.setSelectedIndex(0);
        graphicMode.addActionListener(this);
        gridPanel.add(graphicMode);

        buttonStart.addActionListener(this);
        gridPanel.add(buttonStart);

        buttonStop.addActionListener(this);
        buttonStop.setEnabled(false);
        gridPanel.add(buttonStop);

        add(gridPanel);
        add(gridPanel);

        Collections.addAll(deployStrings, DeploymentTypes.getDeploymentTypes());
        Collections.addAll(graphicStrings, "Graphic Mode", "Stats mode");
    }

    static void createAndShowGUI () {
        //Create and set up the window.
        JFrame frame = new JFrame("Configurazione di simulazione");
        ImageIcon img = new ImageIcon("assets/Interface/settings.png");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(img.getImage());

        //Add contents to the window.
        frame.add(new Settings());

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    public void propertyChange (PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == valSamples) {
            H20Sim.N_SAMPLES = ((Number) valSamples.getValue()).intValue();
        } else if (source == valThread) {
            H20Sim.NTHREADS = ((Number) valThread.getValue()).intValue();
        } else if (source == valMaxFrameSize) {
            H20Sim.MAX_FRAME_SIZE = ((Number) valMaxFrameSize.getValue()).intValue();
        } else if (source == valRateMaxFrame) {
            H20Sim.MAX_FRAME_RATE = ((Number) valRateMaxFrame.getValue()).doubleValue();
        } else if (source == valSensorBandwidth) {
            H20Sim.SENSOR_BANDWIDTH = ((Number) valSensorBandwidth.getValue()).intValue();
        } else if (source == valThreshold) {
            H20Sim.THRESHOLD = ((Number) valThreshold.getValue()).intValue();
        } else if (source == valSensibility) {
            H20Sim.SENSIBILITY = ((Number) valSensibility.getValue()).doubleValue();
        } else if (source == valPower) {
            H20Sim.SENSOR_POWER = ((Number) valPower.getValue()).doubleValue();
        } else if (source == valFrequency) {
            H20Sim.SENSOR_FREQUENCY = ((Number) valFrequency.getValue()).doubleValue();
        } else if (source == valLambda) {
            H20Sim.LAMDA = ((Number) valLambda.getValue()).doubleValue();
        }
    }

    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == deployType) {
            JComboBox cb = (JComboBox) e.getSource();
            String item = (String) cb.getSelectedItem();
            deployType.setSelectedIndex(deployStrings.indexOf(item));
            H20Sim.DEPLOYMENT_TYPE = item;
        } else if (e.getSource() == graphicMode) {
            JComboBox cb = (JComboBox) e.getSource();
            String item = (String) cb.getSelectedItem();
            graphicMode.setSelectedIndex(graphicStrings.indexOf(item));
            assert item != null;
            H20Sim.CANVAS_MODE = item.equals("Graphic Mode");
        } else if (e.getSource() == buttonStart) {
            buttonStart.setEnabled(false);
            buttonStop.setEnabled(true);
            H20Sim.START = true;
        } else if (e.getSource() == buttonStop) {
            for (Thread thread : H20Sim.threadContextMap.keySet()) {
                thread.stop();
                H20Sim.threadContextMap.remove(thread);
            }
            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);
        }
    }

    private void setUpFormats () {
        amountFormat = NumberFormat.getNumberInstance();
        amountFormat.setMaximumFractionDigits(Integer.MAX_VALUE);
    }
}