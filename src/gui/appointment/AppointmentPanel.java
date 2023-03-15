package gui.appointment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.AppointmentController;
import controller.RegistrationController;
import exceptions.EntityNotFoundException;
import gui.events.EventHandler;
import model.Appointment;
import model.Registration;
import model.Session;

public class AppointmentPanel extends JPanel {
  private JTable table;
  private AppointmentController appointmentController;
  private RegistrationController registrationController;
  private List<Appointment> appointments;
  private List<Registration> registrations;
  private AppointmentSearch searchField;

  private JPanel toolbarPanel;
  private JButton btnDraw;

  /**
   * Create the panel.
   */
  public AppointmentPanel() {
    appointmentController = new AppointmentController();
    registrationController = new RegistrationController();

    setLayout(new BorderLayout(0, 0));

    createToolbar();
    createTable();
  }

  /**
   * Create the toolbar.
   */
  private void createToolbar() {
    toolbarPanel = new JPanel();
    FlowLayout fl_toolbarPanel = (FlowLayout) toolbarPanel.getLayout();
    fl_toolbarPanel.setAlignment(FlowLayout.LEFT);
    add(toolbarPanel, BorderLayout.NORTH);

    JButton btnBack = new JButton("");
    btnBack.setPreferredSize(new Dimension(40, 40));
    ImageIcon imgBack = new ImageIcon(new ImageIcon(getClass().getResource("/gui/assets/back.png")).getImage()
        .getScaledInstance(25, 25, Image.SCALE_DEFAULT));
    btnBack.setIcon(imgBack);
    btnBack.addActionListener((e) -> onBackClick());
    toolbarPanel.add(btnBack);

    JLabel lblNewLabel = new JLabel("Appointments");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblNewLabel.setBorder(new EmptyBorder(0, 10, 0, 12));
    toolbarPanel.add(lblNewLabel);

    searchField = new AppointmentSearch();
    toolbarPanel.add(searchField);
    EventHandler.addListener("on-appointment-search", (value) -> {
      onSearch((String) value);
    });

    JButton btnRefresh = new JButton("Refresh");
    btnRefresh.setPreferredSize(new Dimension(80, 40));
    toolbarPanel.add(btnRefresh);
    btnRefresh.addActionListener((e) -> {
      rerenderTable();
    });
  }

  /**
   * Create the table.
   */
  private void createTable() {
    table = new JTable();
    table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, },
        new String[] { "ID number", "Customer identity no", "Date", "Delete" }) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });

    table.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = table.rowAtPoint(evt.getPoint());
        int col = table.columnAtPoint(evt.getPoint());
        String colName = table.getColumnName(col);
        if (row >= 0 && col >= 0 && colName == "Delete") {
          UUID id = (UUID) table.getModel().getValueAt(row, 0);
          int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this appointment?",
              "Warning", JOptionPane.YES_NO_OPTION);

          if (dialogResult == JOptionPane.YES_OPTION) {
            try {
              appointmentController.deleteAppointment(id);
            } catch (EntityNotFoundException e) {
              JOptionPane.showMessageDialog(null, e.getMessage());
            }
            rerenderTable();
          }
        }
      }
    });

    EventHandler.addListener("refresh-appointments", (i) -> {
      rerenderTable();
    });

    EventHandler.addListener("user-logged-in", (_n) -> {
      if (!Session.isEmployeeManager()) {
        table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, },
            new String[] { "ID number", "Customer identity no", "Date" }) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        });
      } else {
        table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, },
            new String[] { "ID number", "Customer identity no", "Date", "Delete" }) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(new Color(230, 230, 230));
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
      }
      rerenderTable();
      renderDrawAppointmentsButton();
    });

    rerenderTable();

    add(new JScrollPane(table), BorderLayout.CENTER);
  }

  /**
   * On back button click.
   */
  private void onBackClick() {
    CardLayout cl = (CardLayout) getParent().getLayout();
    cl.show(getParent(), "mainMenuPanel");
  }

  /**
   * Rerenders the table.
   */
  private void rerenderTable() {
    searchField.reset();
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);
    try {
      // Maybe extending appointments by registrations is a good idea... Might
      // implement it if this code occurs in multiple places
      appointments = appointmentController.getAllAppointments();
      registrations = registrationController.getAllRegistrations();
      for (Appointment appointment : appointments) {
        Registration registration = null;
        for (Registration reg : registrations) {
          if (reg.getId().equals(appointment.getRegistrationId())) {
            registration = reg;
            break;
          }
        }
        if (registration != null) {
          if (Session.isEmployeeManager()) {
            model.addRow(new Object[] { registration.getId(), registration.getCustomerIdentityNo(),
                appointment.getDate(), "Delete" });
          } else {
            model.addRow(
                new Object[] { registration.getId(), registration.getCustomerIdentityNo(), appointment.getDate() });
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Render draw appointments button.
   */
  private void renderDrawAppointmentsButton() {
    if (btnDraw != null) {
      toolbarPanel.remove(btnDraw);
      toolbarPanel.revalidate();
      toolbarPanel.repaint();
    }

    if (Session.isEmployeeManager()) {
      btnDraw = new JButton("Draw appointments");
      btnDraw.setPreferredSize(new Dimension(160, 40));
      toolbarPanel.add(btnDraw);
      btnDraw.addActionListener((e) -> {
        drawAppointments();
      });
    }
  }

  /**
   * On search.
   *
   * @param email the email to search for
   */
  private void onSearch(String email) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);
    if (appointments == null || registrations == null) {
      return;
    }

    for (Registration registration : registrations) {
      if (registration.getCustomerIdentityNo().contains(email)) {
        for (Appointment appointment : appointments) {
          if (appointment.getRegistrationId().equals(registration.getId())) {
            model.addRow(new Object[] { registration.getId(), registration.getCustomerIdentityNo(),
                appointment.getDate(), "Delete" });
          }
        }
      }
    }
  }

  /**
   * Draw appointments.
   */
  private void drawAppointments() {
    UUID storeId = Session.getEmployeeStoreId();
    try {
      int appointmentsDrawnLength = appointmentController.drawAppointmentsForTomorrow(storeId).size();
      JOptionPane.showMessageDialog(null, appointmentsDrawnLength + " Appointments have been drawn for tomorrow");
      rerenderTable();
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }
}
