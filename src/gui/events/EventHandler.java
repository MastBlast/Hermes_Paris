package gui.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventHandler {
  static HashMap<String, ArrayList<EventHandlerInterface>> eventMap = new HashMap<>();

  /**
   * Adds an event handler to the event map.
   *
   * @param eventName The name of the event.
   * @param listener  The event handler.
   */
  public static void addListener(String eventName, EventHandlerInterface listener) {
    if (!eventMap.containsKey(eventName)) {
      eventMap.put(eventName, new ArrayList<>());
    }
    eventMap.get(eventName).add(listener);
  }

  /**
   * Fires an event.
   *
   * @param eventName The name of the event.
   * @param data      The data to pass to the event handler.
   */
  public static void fireEvent(String eventName, Object data) {
    if (eventMap.containsKey(eventName)) {
      for (EventHandlerInterface listener : eventMap.get(eventName)) {
        listener.fn(data);
      }
    }
  }
}
