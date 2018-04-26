/*
 * Copyright (c) 2018. Tesi di laurea in Informatica Università Ca' Foscari di Venezia. Creato da Alessio Del Conte e Filippo Maganza
 */

package app.utils;

import app.H20Sim;
import app.factory.DeploymentTypes;
import app.sim.SimContext;
import app.stats.Collector;
import app.utils.charts.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

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
import java.util.Map;


@SuppressWarnings("unchecked")
public class Settings extends JPanel implements ActionListener, PropertyChangeListener {

    public static JButton buttonStart = new JButton("Start");
    public static JButton buttonStop = new JButton("Stop");
    private static final JProgressBar progressBar = new JProgressBar(0, 100);
    //Formats to format and parse numbers
    private NumberFormat amountFormat;
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

    private JFormattedTextField valMSpeed;
    private JFormattedTextField valMRadius;

    //charts
    private static ChartPanel chartPanel;
    private static JPanel east;
    private static Chart chartThrougput;
    private static Chart chartSR;
    private static Chart chartDSR;
    private static Chart chartRT;


    private static JComboBox deployType = new JComboBox(DeploymentTypes.getDeploymentTypes());
    private static JComboBox graphicMode = new JComboBox(new String[]{"Graphic Mode", "Stats mode"});
    private static JComboBox chartType = new JComboBox(new String[]{"Throughput", "Successful Rate", "Depth Successful Rate", "Response Time"});
    private static List<String> deployStrings = new ArrayList<>();
    private static List<String> graphicStrings = new ArrayList<>();
    private static List<String> chartStrings = new ArrayList<>();

    private static JFrame frame;

    private Settings () {
        super();
        setUpFormats();

        Color backgroudVal = new Color(189, 189, 189);

        //Labels to identify the fields
        JLabel labelSamples = new JLabel("Number of Samples: ");
        JLabel labelThread = new JLabel("Number of Thread: ");
        JLabel labelSensorBandwidth = new JLabel("Sensor Bandwidth (b/s): ");
        JLabel labelMaxFrameSize = new JLabel("Max Frame Size (b): ");
        JLabel labelRateMaxFrame = new JLabel("Rate of frame with max size (0-1): ");
        JLabel labelThreshod = new JLabel("Threshold (only 1, 3 protocols): ");
        JLabel labelSensibility = new JLabel("Sensibility of sensors (dbm): ");
        JLabel labelPower = new JLabel("Power of sensors (dbm): ");
        JLabel labelFrequency = new JLabel("Frequency of sensors (Hz): ");
        JLabel labelLambda = new JLabel("Lambda: ");
        JLabel labelField = new JLabel("Field (x,y,z): ");
        JLabel labelMSpeed = new JLabel("Move speed: ");
        JLabel labelMRadius = new JLabel("Move radius: ");

        valSamples = new JFormattedTextField(amountFormat);
        valSamples.setBackground(backgroudVal);
        valSamples.setValue(H20Sim.N_SAMPLES);
        valSamples.setColumns(3);
        valSamples.addPropertyChangeListener("value", this);

        valThread = new JFormattedTextField(amountFormat);
        valThread.setBackground(backgroudVal);
        valThread.setValue(H20Sim.NTHREADS);
        valThread.setColumns(3);
        valThread.addPropertyChangeListener("value", this);

        valSensorBandwidth = new JFormattedTextField(amountFormat);
        valSensorBandwidth.setBackground(backgroudVal);
        valSensorBandwidth.setValue(H20Sim.SENSOR_BANDWIDTH);
        valSensorBandwidth.setColumns(3);
        valSensorBandwidth.addPropertyChangeListener("value", this);

        valMaxFrameSize = new JFormattedTextField(amountFormat);
        valMaxFrameSize.setBackground(backgroudVal);
        valMaxFrameSize.setValue(H20Sim.MAX_FRAME_SIZE);
        valMaxFrameSize.setColumns(3);
        valMaxFrameSize.addPropertyChangeListener("value", this);

        valRateMaxFrame = new JFormattedTextField(amountFormat);
        valRateMaxFrame.setBackground(backgroudVal);
        valRateMaxFrame.setValue(H20Sim.MAX_FRAME_RATE);
        valRateMaxFrame.setColumns(3);
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
        valLambda.setColumns(3);
        valLambda.addPropertyChangeListener("value", this);

        valFieldx = new JFormattedTextField(amountFormat);
        valFieldx.setBackground(backgroudVal);
        valFieldx.setValue(H20Sim.FIELD_X);
        valFieldx.setColumns(3);
        valFieldx.addPropertyChangeListener("value", this);

        valFieldy = new JFormattedTextField(amountFormat);
        valFieldy.setBackground(backgroudVal);
        valFieldy.setValue(H20Sim.FIELD_Y);
        valFieldy.setColumns(3);
        valFieldy.addPropertyChangeListener("value", this);

        valFieldz = new JFormattedTextField(amountFormat);
        valFieldz.setBackground(backgroudVal);
        valFieldz.setValue(H20Sim.FIELD_Z);
        valFieldz.setColumns(3);
        valFieldz.addPropertyChangeListener("value", this);

        valMSpeed = new JFormattedTextField(amountFormat);
        valMSpeed.setBackground(backgroudVal);
        valMSpeed.setValue(H20Sim.MOVEMENT_SPEED);
        valMSpeed.setColumns(3);
        valMSpeed.addPropertyChangeListener("value", this);

        valMRadius = new JFormattedTextField(amountFormat);
        valMRadius.setBackground(backgroudVal);
        valMRadius.setValue(H20Sim.MOVE_RADIUS);
        valMRadius.setColumns(3);
        valMRadius.addPropertyChangeListener("value", this);

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
        labelMSpeed.setLabelFor(valMSpeed);
        labelMRadius.setLabelFor(valMRadius);

        JPanel gridPanelField = new JPanel(new GridLayout(0, 4));

        gridPanelField.add(labelField);
        gridPanelField.add(valFieldx);
        gridPanelField.add(valFieldy);
        gridPanelField.add(valFieldz);

        JPanel gridPanelSettings = new JPanel(new GridLayout(0, 2, 10, 10));
        gridPanelSettings.add(labelSamples);
        gridPanelSettings.add(valSamples);

        gridPanelSettings.add(labelThread);
        gridPanelSettings.add(valThread);

        gridPanelSettings.add(labelSensorBandwidth);
        gridPanelSettings.add(valSensorBandwidth);

        gridPanelSettings.add(labelMaxFrameSize);
        gridPanelSettings.add(valMaxFrameSize);

        gridPanelSettings.add(labelRateMaxFrame);
        gridPanelSettings.add(valRateMaxFrame);

        gridPanelSettings.add(labelThreshod);
        gridPanelSettings.add(valThreshold);

        gridPanelSettings.add(labelSensibility);
        gridPanelSettings.add(valSensibility);

        gridPanelSettings.add(labelPower);
        gridPanelSettings.add(valPower);

        gridPanelSettings.add(labelFrequency);
        gridPanelSettings.add(valFrequency);

        gridPanelSettings.add(labelLambda);
        gridPanelSettings.add(valLambda);

        gridPanelSettings.add(labelMSpeed);
        gridPanelSettings.add(valMSpeed);

        gridPanelSettings.add(labelMRadius);
        gridPanelSettings.add(valMRadius);

        deployType.setSelectedIndex(0);
        deployType.addActionListener(this);
        gridPanelSettings.add(deployType);

        graphicMode.setSelectedIndex(0);
        graphicMode.addActionListener(this);
        gridPanelSettings.add(graphicMode);

        buttonStart.addActionListener(this);
        gridPanelSettings.add(buttonStart);

        buttonStop.addActionListener(this);
        buttonStop.setEnabled(false);
        gridPanelSettings.add(buttonStop);

        JPanel progressPanel = new JPanel();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressPanel.add(progressBar);

        Dimension prefSize = progressBar.getPreferredSize();
        prefSize.height = 20;//some width
        prefSize.width = 500;
        progressBar.setPreferredSize(prefSize);

        gridPanelField.setBorder(new EmptyBorder(10, 10, 10, 10));
        gridPanelSettings.setBorder(new EmptyBorder(10, 10, 10, 10));

        JSplitPane splitPaneH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneH.setLeftComponent(gridPanelField);
        splitPaneH.setRightComponent(gridPanelSettings);

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

        chartPanel = new ChartPanel(null);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        prefSize = chartPanel.getPreferredSize();
        prefSize.height = 600;
        prefSize.width = 850;
        chartPanel.setPreferredSize(prefSize);

        JPanel west = new JPanel(new BorderLayout(3, 3));
        west.setBorder(new EmptyBorder(5, 5, 5, 5));

        west.add(splitPaneH, BorderLayout.NORTH);
        west.add(progressPanel, BorderLayout.SOUTH);

        east = new JPanel(new BorderLayout(3, 3));

        JPanel eastNorth = new JPanel(new BorderLayout(3, 3));
        chartType.setSelectedIndex(0);
        chartType.addActionListener(this);
        eastNorth.add(chartType);
        eastNorth.setBorder(new EmptyBorder(5, 5, 5, 5));

        east.add(eastNorth, BorderLayout.NORTH);
        east.add(chartPanel, BorderLayout.SOUTH);

        east.setVisible(false);

        JPanel gui = new JPanel(new BorderLayout(3, 3));
        gui.add(west, BorderLayout.WEST);
        gui.add(east, BorderLayout.EAST);
        add(gui);

        Collections.addAll(deployStrings, DeploymentTypes.getDeploymentTypes());
        Collections.addAll(graphicStrings, "Graphic Mode", "Stats mode");
        Collections.addAll(chartStrings, "Throughput", "Successful Rate", "Depth Successful Rate", "Response Time");
    }

    public static void createAndShowGUI () {
        //Create and set up the window.
        frame = new JFrame("Configurazione di simulazione");
        ImageIcon img = new ImageIcon("assets/Interface/settings.png");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(img.getImage());

        //Add contents to the window.
        frame.add(new Settings());

        //Display the window.
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
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
        } else if (source == valFieldx) {
            H20Sim.FIELD_X = ((Number) valFieldx.getValue()).floatValue();
        } else if (source == valFieldy) {
            H20Sim.FIELD_Y = ((Number) valFieldy.getValue()).floatValue();
        } else if (source == valFieldz) {
            H20Sim.FIELD_Z = ((Number) valFieldz.getValue()).floatValue();
        } else if (source == valMRadius) {
            H20Sim.MOVE_RADIUS = ((Number) valMRadius.getValue()).floatValue();
        } else if (source == valMSpeed) {
            H20Sim.MOVEMENT_SPEED = ((Number) valMSpeed.getValue()).floatValue();
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
        } else if (e.getSource() == chartType) {
            JComboBox cb = (JComboBox) e.getSource();
            String item = (String) cb.getSelectedItem();
            chartType.setSelectedIndex(chartStrings.indexOf(item));
            assert item != null;
            switch (item) {
                case "Throughput":
                    chartPanel.setChart(chartThrougput.getChart());
                    frame.pack();
                    break;
                case "Successful Rate":
                    chartPanel.setChart(Settings.chartSR.getChart());
                    frame.pack();
                    break;
                case "Depth Successful Rate":
                    chartPanel.setChart(Settings.chartDSR.getChart());
                    frame.pack();
                    break;
                case "Response Time":
                    chartPanel.setChart(Settings.chartRT.getChart());
                    frame.pack();
                    break;
            }
            RefineryUtilities.centerFrameOnScreen(frame);
        }
    }

    private void setUpFormats () {
        amountFormat = NumberFormat.getNumberInstance();
        amountFormat.setMaximumFractionDigits(Integer.MAX_VALUE);
    }

    public static void updateProgressBar (double percentage) {
        if ((int) percentage > progressBar.getValue()) {
            progressBar.setValue((int) percentage);
        }
    }

    public static void resetProgressBar () {
        progressBar.setValue(0);
    }

    public static void drawCharts (Collector collector, Map<Thread, SimContext> threads) {
        chartThrougput = new ChartThroughput(collector, threads);
        chartSR = new ChartSuccessfulRate(collector, threads);
        chartDSR = new ChartDepthSuccessRate(collector, threads);
        chartRT = new ChartResponseTime(collector, threads);

        JFreeChart chart = null;
        switch (chartStrings.get(chartType.getSelectedIndex())) {
            case "Throughput":
                chart = chartThrougput.getChart();
                break;
            case "Successful Rate":
                chart = Settings.chartSR.getChart();
                break;
            case "Depth Successful Rate":
                chart = Settings.chartDSR.getChart();
                break;
            case "Response Time":
                chart = Settings.chartRT.getChart();
                break;
        }
        chartPanel.setChart(chart);
        east.setVisible(true);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
    }
}