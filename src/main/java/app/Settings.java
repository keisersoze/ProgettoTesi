package app;

import app.factory.DeploymentTypes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
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
    private JLabel labelField;

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
    private JFormattedTextField valFieldx;
    private JFormattedTextField valFieldy;
    private JFormattedTextField valFieldz;

    static JButton buttonStart = new JButton("Start");
    static JButton buttonStop = new JButton("Stop");

    private JComboBox deployType = new JComboBox(DeploymentTypes.getDeploymentTypes());
    private JComboBox graphicMode = new JComboBox(new String[]{"Graphic Mode", "Stats mode"});
    private List<String> deployStrings = new ArrayList<>();
    private List<String> graphicStrings = new ArrayList<>();

    private Settings () {
        super();
        setUpFormats();

        Color backgroudVal = new Color(189, 189, 189);

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
        labelField = new JLabel("Field (x,y,z): ");


        valSamples = new JFormattedTextField(amountFormat);
        valSamples.setBackground(backgroudVal);
        valSamples.setValue(H20Sim.N_SAMPLES);
        valSamples.setColumns(10);
        valSamples.addPropertyChangeListener("value", this);

        valThread = new JFormattedTextField(amountFormat);
        valThread.setBackground(backgroudVal);
        valThread.setValue(H20Sim.NTHREADS);
        valThread.setColumns(3);
        valThread.addPropertyChangeListener("value", this);

        valSensorBandwidth = new JFormattedTextField(amountFormat);
        valSensorBandwidth.setBackground(backgroudVal);
        valSensorBandwidth.setValue(H20Sim.SENSOR_BANDWIDTH);
        valSensorBandwidth.setColumns(10);
        valSensorBandwidth.addPropertyChangeListener("value", this);

        valMaxFrameSize = new JFormattedTextField(amountFormat);
        valMaxFrameSize.setBackground(backgroudVal);
        valMaxFrameSize.setValue(H20Sim.MAX_FRAME_SIZE);
        valMaxFrameSize.setColumns(10);
        valMaxFrameSize.addPropertyChangeListener("value", this);

        valRateMaxFrame = new JFormattedTextField(amountFormat);
        valRateMaxFrame.setBackground(backgroudVal);
        valRateMaxFrame.setValue(H20Sim.MAX_FRAME_RATE);
        valRateMaxFrame.setColumns(5);
        valRateMaxFrame.addPropertyChangeListener("value", this);

        valThreshold = new JFormattedTextField(amountFormat);
        valThreshold.setBackground(backgroudVal);
        valThreshold.setValue(H20Sim.THRESHOLD);
        valThreshold.setColumns(3);
        valThreshold.addPropertyChangeListener("value", this);

        valSensibility = new JFormattedTextField(amountFormat);
        valSensibility.setBackground(backgroudVal);
        valSensibility.setValue(H20Sim.SENSIBILITY);
        valSensibility.setColumns(3);
        valSensibility.addPropertyChangeListener("value", this);

        valPower = new JFormattedTextField(amountFormat);
        valPower.setBackground(backgroudVal);
        valPower.setValue(H20Sim.SENSOR_POWER);
        valPower.setColumns(3);
        valPower.addPropertyChangeListener("value", this);

        valFrequency = new JFormattedTextField(amountFormat);
        valFrequency.setBackground(backgroudVal);
        valFrequency.setValue(H20Sim.SENSOR_FREQUENCY);
        valFrequency.setColumns(3);
        valFrequency.addPropertyChangeListener("value", this);

        valLambda = new JFormattedTextField(amountFormat);
        valLambda.setBackground(backgroudVal);
        valLambda.setValue(H20Sim.LAMDA);
        valLambda.setColumns(15);
        valLambda.addPropertyChangeListener("value", this);

        valFieldx = new JFormattedTextField(amountFormat);
        valFieldx.setBackground(backgroudVal);
        valFieldx.setValue(H20Sim.FIELD_X);
        valFieldx.setColumns(15);
        valFieldx.addPropertyChangeListener("value", this);

        valFieldy = new JFormattedTextField(amountFormat);
        valFieldy.setBackground(backgroudVal);
        valFieldy.setValue(H20Sim.FIELD_Y);
        valFieldy.setColumns(15);
        valFieldx.addPropertyChangeListener("value", this);

        valFieldz = new JFormattedTextField(amountFormat);
        valFieldz.setBackground(backgroudVal);
        valFieldz.setValue(H20Sim.FIELD_Z);
        valFieldz.setColumns(15);
        valFieldz.addPropertyChangeListener("value", this);

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
        labelField.setLabelFor(valFieldx);
        labelField.setLabelFor(valFieldy);
        labelField.setLabelFor(valFieldz);


        JPanel gridPanel2 = new JPanel(new GridLayout(0, 4));

        gridPanel2.add(labelField);
        gridPanel2.add(valFieldx);
        gridPanel2.add(valFieldy);
        gridPanel2.add(valFieldz);

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

        gridPanel2.setBorder(new EmptyBorder(10, 10, 10, 10));
        gridPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JSplitPane splitPaneH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneH.setLeftComponent(gridPanel2);
        splitPaneH.setRightComponent(gridPanel);

        splitPaneH.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider () {
                return new BasicSplitPaneDivider(this) {
                    public void setBorder (Border b) {
                    }

                    @Override
                    public void paint (Graphics g) {
                        g.setColor(Color.GRAY);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });
        splitPaneH.setBorder(null);

        add(splitPaneH);

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
        }else if (source == valFieldx) {
            H20Sim.FIELD_X = ((Number) valFieldx.getValue()).doubleValue();
        }else if (source == valFieldy) {
            H20Sim.FIELD_Y = ((Number) valFieldy.getValue()).doubleValue();
        }else if (source == valFieldz) {
            H20Sim.FIELD_Z = ((Number) valFieldz.getValue()).doubleValue();
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
            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);
            H20Sim.STOPPED = true;
        }
    }

    private void setUpFormats () {
        amountFormat = NumberFormat.getNumberInstance();
        amountFormat.setMaximumFractionDigits(Integer.MAX_VALUE);
    }

}