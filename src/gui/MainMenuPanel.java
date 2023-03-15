package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gui.events.EventHandler;
import model.Session;

public class MainMenuPanel extends JPanel {
  private JPanel topPanel;
  private JPanel centerPanel;

  /**
   * Create the frame.
   */
  public MainMenuPanel() {
    setLayout(new BorderLayout(0, 0));

    topPanel = new JPanel();
    add(topPanel, BorderLayout.NORTH);
    topPanel.setLayout(new BorderLayout(0, 0));
    topPanel.setPreferredSize(new java.awt.Dimension(0, 50));

    ImageIcon dotGreen = new ImageIcon(new ImageIcon(getClass().getResource("/gui/assets/green.png")).getImage()
        .getScaledInstance(22, 22, Image.SCALE_DEFAULT));
    ImageIcon dotRed = new ImageIcon(new ImageIcon(getClass().getResource("/gui/assets/red.png")).getImage()
        .getScaledInstance(22, 22, Image.SCALE_DEFAULT));

    JLabel lblNewLabel = new JLabel("DB Connected");
    lblNewLabel.setIcon(dotGreen);
    lblNewLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));

    topPanel.add(lblNewLabel, BorderLayout.WEST);

    EventHandler.addListener("db-connection-status-changed", (isConnected) -> {
      if ((boolean) isConnected) {
        lblNewLabel.setIcon(dotGreen);
        lblNewLabel.setText("DB Connected");
      } else {
        lblNewLabel.setIcon(dotRed);
        lblNewLabel.setText("DB Disconnected");
      }
    });

    JLabel mainMenuLabel = new JLabel("Main Menu");
    mainMenuLabel.setHorizontalAlignment(SwingConstants.CENTER);
    mainMenuLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
    topPanel.add(mainMenuLabel);

    JButton btnNewButton = new JButton("Log out");
    topPanel.add(btnNewButton, BorderLayout.EAST);
    btnNewButton.addActionListener(e -> {
      onLogOutClick();
    });
    centerPanel = new JPanel();
    centerPanel.setLayout(new GridLayout());
    add(centerPanel, BorderLayout.CENTER);

    addMenuItem("REGISTRATIONS", () -> {
      Container parent = getParent();
      CardLayout cl = (CardLayout) parent.getLayout();
      cl.show(parent, "registrationPanel");
    });
    addMenuItem("APPOINTMENTS", () -> {
      Container parent = getParent();
      CardLayout cl = (CardLayout) parent.getLayout();
      cl.show(parent, "appointmentPanel");
    });
  }

  /**
   * Adds a menu item to the main menu.
   * 
   * @param text   The text inside the menu item.
   * @param action The action to be performed when the menu item is clicked.
   */
  private void addMenuItem(String text, Runnable action) {
    JButton newButton = new JButton(text);
    newButton.setFont(new Font("Sitka Subheading", Font.BOLD | Font.ITALIC, 12));

    newButton.addActionListener(e -> action.run());

    centerPanel.add(newButton);
  }

  /**
   * Called when the log out button is clicked.
   */
  private void onLogOutClick() {
    Session.delete();
    Container parent = getParent();
    CardLayout cl = (CardLayout) parent.getLayout();
    cl.show(parent, "loginScreenPanel");
  }
}
