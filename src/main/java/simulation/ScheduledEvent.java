package simulation;

public class ScheduledEvent implements Comparable<ScheduledEvent>{

  private final Event event;
  private final double scheduledTime;

  public ScheduledEvent(Event event, double scheduleTime) {
    this.event = event;
    this.scheduledTime = scheduleTime;
  }

  public Event getEvent() {
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
