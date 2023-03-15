package gui.components;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import javax.swing.JPanel;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class DatePicker extends JPanel {
  JDatePickerImpl datePicker;

  /**
   * Create the panel.
   */
  public DatePicker() {
    UtilDateModel model = new UtilDateModel();
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
    datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    add(datePicker);
  }

  /**
   * @return LocalDate object representing the date selected in the date picker
   */
  public LocalDate getDate() {
    Date date = (Date) datePicker.getModel().getValue();
    if (date == null) {
      return null;
    }
    return LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
  }

  /**
   * sets the date in the date picker
   *
   * @param date
   */
  public void setDate(LocalDate newDate) {
    if (newDate == null) {
      datePicker.getModel().setValue(null);
    } else {
      UtilDateModel model = (UtilDateModel) datePicker.getModel();
      model.setValue(Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
  }

  /**
   * @return true if the date picker is enabled, false otherwise
   */
  public void reset() {
    setDate(null);
  }
}
