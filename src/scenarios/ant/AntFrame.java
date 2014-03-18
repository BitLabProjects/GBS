package scenarios.ant;

import engine.Main;
import engine.awt.Board;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class AntFrame extends JFrame implements ActionListener, ChangeListener {

  private static AntFrame instance;
  private Board board;
  private JButton bnRestart, bnBack;
  private JToggleButton bnMultiplyX10;
  private JLabel l;
  private JSpinner spinner, redNum;
  private SpinnerModel spinnerModel;
  private JSlider slider, foodAttraction, foodSightDistance, pherAttraction, homeAttraction, pherSightDistance, homeSightDistance;
  private BoundedRangeModel boundedModel;
  private JPanel optionPanel, p;

  public AntFrame() {
    setTitle("Group Behaviour Simulator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLayout(new GridBagLayout());

    GridBagConstraints gbc;

    //Inizializzazione della board
    board = new Board();
    board.setWorld(new AntWorld(700, 700));
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    add(board, gbc);


    //Inizializzazione pannello opzioni
    optionPanel = new JPanel();
    optionPanel.setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    add(optionPanel, gbc);

    //Pannello robot rossi
    p = new JPanel();
    p.setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(12, 12, 12, 12);
    optionPanel.add(p, gbc);
    {

      l = new JLabel("Ant:");
      l.setFont(new Font("Default", Font.BOLD, 12));
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(6, 6, 3, 6);
      p.add(l, gbc);

      l = new JLabel("Number:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      redNum = new JSpinner(new SpinnerNumberModel(40, 1, 80, 1));
      redNum.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 1;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(redNum, gbc);

      l = new JLabel("Pheromone Attraction:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      pherAttraction = new JSlider(JSlider.HORIZONTAL, 0, 100, 1);
      pherAttraction.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 2;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(pherAttraction, gbc);

      l = new JLabel("Food Attraction:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      foodAttraction = new JSlider(JSlider.HORIZONTAL, 0, 60, 50);
      foodAttraction.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 3;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(foodAttraction, gbc);

      l = new JLabel("Home Attraction:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 4;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      homeAttraction = new JSlider(JSlider.HORIZONTAL, 0, 100, 5);
      homeAttraction.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 4;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(homeAttraction, gbc);

      l = new JLabel("Pheromone Sight Distance:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 5;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      pherSightDistance = new JSlider(JSlider.HORIZONTAL, 0, 100, 12);
      pherSightDistance.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 5;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(pherSightDistance, gbc);

      l = new JLabel("Food Sight Distance:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 6;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      foodSightDistance = new JSlider(JSlider.HORIZONTAL, 0, 100, 12);
      foodSightDistance.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 6;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(foodSightDistance, gbc);

      l = new JLabel("Home Sight Distance:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 7;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      homeSightDistance = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
      homeSightDistance.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 7;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(homeSightDistance, gbc);
    }

    bnRestart = new JButton("Reset");
    bnRestart.setActionCommand("resetClicked");
    bnRestart.addActionListener(this);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.ipadx = 30;
    gbc.insets = new Insets(12, 12, 12, 12);
    optionPanel.add(bnRestart, gbc);

    bnBack = new JButton("Back");
    bnBack.setActionCommand("backClicked");
    bnBack.addActionListener(this);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.ipadx = 30;
    gbc.insets = new Insets(12, 12, 12, 12);
    optionPanel.add(bnBack, gbc);

    bnMultiplyX10 = new JToggleButton("Speed x10");
    bnMultiplyX10.setActionCommand("speedx10Clicked");
    bnMultiplyX10.addActionListener(this);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.ipadx = 30;
    gbc.insets = new Insets(12, 12, 12, 12);
    optionPanel.add(bnMultiplyX10, gbc);

    //Pannello separatore
    p = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.insets = new Insets(12, 12, 12, 12);
    optionPanel.add(p, gbc);
    pack();
    setSize(board.boardWidth + getInsets().left + getInsets().right + 300, board.boardHeight + getInsets().top + getInsets().bottom);
    //<setSize(board.w + 300, board.h + Main.windowDecorationHeigth);
    setLocationRelativeTo(null);
    setVisible(true);

    setAllRobotsParams();
  }

  public static void showWindow() {
    if (instance == null) {
      instance = new AntFrame();
    } else {
      instance.board.getEngine().resetSimulation();
      instance.setAllRobotsParams();
      instance.setVisible(true);
    }
  }

  public static void hideWindow() {
    if (instance != null) {
      instance.board.getEngine().endSimulation();
      instance.setVisible(false);
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("resetClicked")) {
      board.getEngine().resetSimulation();
      setAllRobotsParams();
    }
    if (e.getActionCommand().equals("backClicked")) {
      Main.showWindow();
      hideWindow();
    }
    if (e.getActionCommand().equals("speedx10Clicked")) {
      if (board.getEngine().getStepsMultiplier() > 1) {
        board.getEngine().setStepsMultiplier(1);
      } else {
        board.getEngine().setStepsMultiplier(10);
      }
    }
  }

  private void setAllRobotsParams() {
    stateChanged(new ChangeEvent(pherAttraction));
    stateChanged(new ChangeEvent(foodAttraction));
    stateChanged(new ChangeEvent(homeAttraction));
    stateChanged(new ChangeEvent(pherSightDistance));
    stateChanged(new ChangeEvent(homeSightDistance));
    stateChanged(new ChangeEvent(foodSightDistance));
  }

  public void stateChanged(ChangeEvent e) {
    if (e.getSource() instanceof JSpinner) {
      spinner = (JSpinner) (e.getSource());
      spinnerModel = spinner.getModel();

      Color col = Color.RED;

      int val = (Integer) spinnerModel.getValue();
      //TODO board.setRobotsCount(val, col);
      setAllRobotsParams();
    }

    if (e.getSource() instanceof JSlider) {
      slider = (JSlider) (e.getSource());
      boundedModel = slider.getModel();

      float value = boundedModel.getValue();


      if (slider.equals(pherAttraction)) {
        value = value / 100.0f;
        //TODO board.setPheromoneAttractionStrength(value);
      }

      if (slider.equals(foodAttraction)) {
        value = value / 100.0f;
        //TODO board.setFoodAttractionStrength(value);
      }

      if (slider.equals(homeAttraction)) {
        value = value / 100.0f;
        //TODO board.setHomeAttractionStrength(value);
      }

      if (slider.equals(pherSightDistance)) {
        value = value * 10.0f;
        //TODO board.setPheromoneSightDistance(value);
      }

      if (slider.equals(foodSightDistance)) {
        value = value * 10.0f;
        //TODO board.setFoodSightDistance(value);
      }

      if (slider.equals(homeSightDistance)) {
        value = value * 20.0f;
        //TODO board.setHomeSightDistance(value);
      }
    }
  }
}
