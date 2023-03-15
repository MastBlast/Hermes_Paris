package services;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import controller.AppointmentController;
import controller.StoreController;
import model.Store;

public class DrawAppointmentsService {
  /**
   * Entry point for the service.
   */
  public static void main(String[] args) {
    DrawAppointmentsService service = new DrawAppointmentsService();
    try {
      service.start();
      System.out.println("DrawAppointmentsService started...");
    } catch (Exception e) {
      onError(e);
    }
  }

  /**
   * Starts the service.
   */
  public void start() throws SQLException {
    long timeInMsUntil6pm = getTimeInMsUntil6pm();
    long timeInMsBetweenDraw = 24 * 60 * 60 * 1000;

    AppointmentController appointmentController = new AppointmentController();
    StoreController storeController = new StoreController();

    List<Store> stores = storeController.getAllStores();

    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        for (Store store : stores) {
          try {
            appointmentController.drawAppointmentsForTomorrow(store.getId());
          } catch (SQLException e) {
            onError(e);
          }
        }
      }
    }, timeInMsUntil6pm, timeInMsBetweenDraw);
  }

  /**
   * Returns the time in milliseconds until 6pm.
   */
  private long getTimeInMsUntil6pm() {
    return Duration.between(LocalTime.now(), LocalTime.of(18, 0)).toMillis();
  }

  /**
   * Called when an error occurs.
   */
  private static void onError(Exception e) {
    // TODO: Notify manager there was an error (send email)?
    e.printStackTrace();
  }
}
