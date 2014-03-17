package engine;

import Bacteria.BacteriaFrame;
import Flocking.FlockingFrame;
import capturetheflag.CTFFrame;
import Termites.TermitesFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame implements ActionListener {

  public static final int windowDecorationHeigth = 26;
  private static Main instance;

  public Main() {

    //Inizializzazione frame
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    setLayout(new GridBagLayout());

    GridBagConstraints gbc;

    JButton bnStartFlocking = new JButton("Flocking");
    bnStartFlocking.setActionCommand("StartFlocking");
    bnStartFlocking.addActionListener(this);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0f;
    gbc.weighty = 1.0f;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(10, 10, 10, 10);
    add(bnStartFlocking, gbc);

    JButton bnStartTermites = new JButton("Termites");
    bnStartTermites.setActionCommand("StartTermites");
    bnStartTermites.addActionListener(this);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0f;
    gbc.weighty = 1.0f;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(10, 10, 10, 10);
    add(bnStartTermites, gbc);

//        JButton bnStartBacteria = new JButton("Bacteria");
//        bnStartBacteria.setActionCommand("StartBacteria");
//        bnStartBacteria.addActionListener(this);
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.weightx = 1.0f;
//        gbc.weighty = 1.0f;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.insets = new Insets(10, 10, 10, 10);
//        add(bnStartBacteria, gbc);
//
//        JButton bnStartAnts = new JButton("Ants");
//        bnStartAnts.setActionCommand("StartAnts");
//        bnStartAnts.addActionListener(this);
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        gbc.weightx = 1.0f;
//        gbc.weighty = 1.0f;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.insets = new Insets(10, 10, 10, 10);
//				add(bnStartAnts, gbc);

    JButton bnStartPaP = new JButton("Capture The Flag");
    bnStartPaP.setActionCommand("StartCTF");
    bnStartPaP.addActionListener(this);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.weightx = 1.0f;
    gbc.weighty = 1.0f;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(10, 10, 10, 10);
    add(bnStartPaP, gbc);
    
    JButton bnStartTad = new JButton("Tadpoles");
    bnStartTad.setActionCommand("StartTadpoles");
    bnStartTad.addActionListener(this);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.weightx = 1.0f;
    gbc.weighty = 1.0f;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(10, 10, 10, 10);
    add(bnStartTad, gbc);

    setLocationRelativeTo(null);
    setSize(200, 200);
    setVisible(true);
  }

  public static void main(String[] args) {
    showWindow();
  }

  public static void showWindow() {
    if (instance == null) {
      instance = new Main();
    } else {
      instance.setVisible(true);
    }
  }

  public static void hideWindow() {
    if (instance != null) {
      instance.setVisible(false);
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("StartFlocking")) {
      FlockingFrame.showWindow();
      hideWindow();
    }
    if (e.getActionCommand().equals("StartTermites")) {
      TermitesFrame.showWindow();
      hideWindow();
    }
    if (e.getActionCommand().equals("StartBacteria")) {
      BacteriaFrame.showWindow();
      hideWindow();
    }
//    if (e.getActionCommand().equals("StartAnts")) {
//      AntFrame.showWindow();
//      hideWindow();
//    }
    if (e.getActionCommand().equals("StartCTF")) {
      CTFFrame.showWindow();
      hideWindow();
    }
//    if (e.getActionCommand().equals("StartTadpoles")) {
//      TadpolesFrame.showWindow();
//      hideWindow();
//    }
  }
}
