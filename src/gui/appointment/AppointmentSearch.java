package gui.appointment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import gui.events.EventHandler;

public class AppointmentSearch extends JTextField {
  private static final String DEFAULT_TEXT = "Search by customer identity no...";

  /**
   * Constructor for the AppointmentSearch object
   */
  public AppointmentSearch() {
    setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
    setPreferredSize(new Dimension(280, 34));
    setMinimumSize(new Dimension(80, 34));
    setForeground(Color.GRAY);

    handleFocus();
    handleUpdate();
  }

  /**
   * Handles the focus event
   */
  private void handleFocus() {
    addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        if (getText().equals(DEFAULT_TEXT)) {
          setText("");
          setForeground(Color.BLACK);
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
          setForeground(Color.GRAY);
          setText(DEFAULT_TEXT);
        }
      }
    });
  }

  /**
   * Handles the update event
   */
  private void handleUpdate() {
    @FunctionalInterface
    interface SimpleDocumentListener extends DocumentListener {
      void update(DocumentEvent e);

      @Override
      default void insertUpdate(DocumentEvent e) {
        update(e);
      }

      @Override
      default void removeUpdate(DocumentEvent e) {
        update(e);
      }

      @Override
      default void changedUpdate(DocumentEvent e) {
        update(e);
      }
    }

    getDocument().addDocumentListener((SimpleDocumentListener) e -> {
      String email = getText();
      if (email.equals(DEFAULT_TEXT)) {
        email = "";
      }
      EventHandler.fireEvent("on-appointment-search", email);
    });
  }

  /**
   * Reset the search field
   */
  public void reset() {
    setText(DEFAULT_TEXT);
    setForeground(Color.GRAY);
  }
}
