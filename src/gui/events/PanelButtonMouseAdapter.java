package gui.events;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class PanelButtonMouseAdapter extends MouseAdapter {
  JButton button;

  /**
   * Constructor for PanelButtonMouseAdapter class.
   * 
   * @param button
   */
  public PanelButtonMouseAdapter(JButton button) {
    this.button = button;
  }

  /**
   * Method for mouseEntered event.
   * 
   * @param e
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    button.setBackground(new Color(255, 128, 51));

  }

  /**
   * Method for mouseExited event.
   * 
   * @param e
   */
  @Override
  public void mouseExited(MouseEvent e) {
    button.setBackground(new Color(255, 173, 51));

  }

  /**
   * Method for mousePressed event.
   * 
   * @param e
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    button.setBackground(new Color(255, 173, 50));

  }
}
