package simulation.framework;

public class ScheduledEvent<S> implements Comparable<ScheduledEvent<S>>{

  private final Event<S> event;
  private final double scheduledTime;

  public ScheduledEvent(Event<S> event, double scheduleTime) {
    this.event = event;
    this.scheduledTime = scheduleTime;
  }

  public Event<S> getEvent() {
    return event;
  }

  public double getScheduledTime() {
    return scheduledTime;
  }

  @Override
  public int compareTo(ScheduledEvent scheduledEvent) {
    return Double.compare(this.scheduledTime, scheduledEvent.scheduledTime);
  }
}
