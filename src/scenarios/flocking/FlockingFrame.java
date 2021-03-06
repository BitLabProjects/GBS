package scenarios.flocking;

import engine.Main;
import engine.awt.Board;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class FlockingFrame extends JFrame implements ActionListener, ChangeListener {

  private static FlockingFrame instance;
  private Board board;
  private JButton bnRestart, bnBack;
  private JToggleButton bnMultiplyX10;
  private JLabel l;
  private JSpinner spinner, redNum, greenNum, blueNum;
  private SpinnerModel spinnerModel;
  private JSlider slider, redCoe, greenCoe, blueCoe, redDis, greenDis, blueDis;
  private BoundedRangeModel boundedModel;
  private JPanel optionPanel, p;

  public FlockingFrame() {
    setTitle("Group Behaviour Simulator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLayout(new GridBagLayout());

    GridBagConstraints gbc;

    //Inizializzazione della board
    board = new Board();
    board.setWorld(new FlockingWorld(700, 700));
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

      l = new JLabel("Red Tadpole:");
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

      l = new JLabel("Cohesion:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      redCoe = new JSlider(JSlider.HORIZONTAL, 0, 100, 30);
      redCoe.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 2;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(redCoe, gbc);

      l = new JLabel("Sight Distance:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      redDis = new JSlider(JSlider.HORIZONTAL, 0, 100, 40);
      redDis.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 3;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(redDis, gbc);
    }

    //Pannello robot verdi
    p = new JPanel();
    //p.setPreferredSize(new Dimension(292, 342));
    p.setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(12, 12, 12, 12);
    optionPanel.add(p, gbc);

    {

      l = new JLabel("Green Tadpole:");
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

      greenNum = new JSpinner(new SpinnerNumberModel(40, 1, 80, 1));
      greenNum.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 1;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(greenNum, gbc);

      l = new JLabel("Cohesion:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      greenCoe = new JSlider(JSlider.HORIZONTAL, 0, 100, 30);
      greenCoe.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 2;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(greenCoe, gbc);

      l = new JLabel("Sight Distance:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      greenDis = new JSlider(JSlider.HORIZONTAL, 0, 100, 40);
      greenDis.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 3;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(greenDis, gbc);
    }

    //Pannello robot blu
    p = new JPanel();
    //p.setPreferredSize(new Dimension(292, 342));
    p.setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(12, 12, 12, 12);
    optionPanel.add(p, gbc);
    {

      l = new JLabel("Blue Tadpole:");
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

      blueNum = new JSpinner(new SpinnerNumberModel(40, 1, 80, 1));
      blueNum.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 1;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(blueNum, gbc);

      l = new JLabel("Cohesion:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      blueCoe = new JSlider(JSlider.HORIZONTAL, 0, 100, 30);
      blueCoe.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 2;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(blueCoe, gbc);

      l = new JLabel("Sight Distance:");
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(l, gbc);

      blueDis = new JSlider(JSlider.HORIZONTAL, 0, 100, 40);
      blueDis.addChangeListener(this);
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 3;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(3, 6, 3, 6);
      p.add(blueDis, gbc);
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
      instance = new FlockingFrame();
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
    stateChanged(new ChangeEvent(redCoe));
    stateChanged(new ChangeEvent(blueCoe));
    stateChanged(new ChangeEvent(greenCoe));
    stateChanged(new ChangeEvent(redDis));
    stateChanged(new ChangeEvent(blueDis));
    stateChanged(new ChangeEvent(greenDis));
  }

  public void stateChanged(ChangeEvent e) {
    if (e.getSource() instanceof JSpinner) {
      spinner = (JSpinner) (e.getSource());
      spinnerModel = spinner.getModel();

      Color col = Color.RED;

      if (spinner.equals(blueNum)) {
        col = Color.BLUE;
      } else if (spinner.equals(greenNum)) {
        col = Color.GREEN;
      }

      int val = (Integer) spinnerModel.getValue();
      //TODO board.setRobotsCount(val, col);
      setAllRobotsParams();
    }

    if (e.getSource() instanceof JSlider) {
      slider = (JSlider) (e.getSource());
      boundedModel = slider.getModel();

      float value = boundedModel.getValue();
      boolean isCoesion = false;

      if (slider.equals(redCoe) || slider.equals(blueCoe) || slider.equals(greenCoe)) {
        value = value / 1000.0f;
        isCoesion = true;
      } else {
        value = (value / 100) * 200 + 50;
      }

      Color col = Color.RED;

      if (slider.equals(blueCoe) || slider.equals(blueDis)) {
        col = Color.BLUE;
      } else if (slider.equals(greenCoe) || slider.equals(greenDis)) {
        col = Color.GREEN;
      }

      if (isCoesion) {
        //TODO board.setCohesion(value, col);
      } else {
        //TODO board.setSightDistance(value, col);
      }
    }
  }
}
