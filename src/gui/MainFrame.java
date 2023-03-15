package gui;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import controller.DataGenerator;
import database.DBStatus;
import gui.appointment.AppointmentPanel;
import gui.registration.CreateRegistrationPanel;
import gui.registration.EditRegistrationPanel;
import gui.registration.RegistrationPanel;

public class MainFrame extends JFrame {
  /**
   * Entry point for the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          DataGenerator.createRegistrations();

          new DBStatus().start();
          FlatLightLaf.setup();
          MainFrame frame = new MainFrame();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the MainFrame
   */
  public MainFrame() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Hermes Management System");
    setBounds(100, 100, 800, 600);
    setLocationRelativeTo(null);

    JPanel contentPane = new JPanel();
    contentPane.setLayout(new CardLayout(0, 0));
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);

    addPanelToContentPane(new LoginPanel(), "loginScreenPanel");
    addPanelToContentPane(new MainMenuPanel(), "mainMenuPanel");

    addPanelToContentPane(new RegistrationPanel(), "registrationPanel");
    addPanelToContentPane(new EditRegistrationPanel(), "editRegistrationPanel");
    addPanelToContentPane(new CreateRegistrationPanel(), "createRegistrationPanel");

    addPanelToContentPane(new AppointmentPanel(), "appointmentPanel");
  }

  /**
   * Adds a panel to the content pane.
   * 
   * @param panel The panel to add.
   * @param name  The name of the panel.
   */
  private void addPanelToContentPane(JPanel panel, String refName) {
    getContentPane().add(panel, refName);
  }
}
