package simulation.framework;

import java.util.PriorityQueue;
import java.util.Queue;

public abstract class Simulation<S> {
  private double currentSimulationTime = 0;
  private final Queue<ScheduledEvent<S>> eventsQueue = new PriorityQueue<>();

  protected abstract boolean stop();

  protected abstract S getSimulationType();

  public void schedule(Event<S> event, double scheduledTime) {
    eventsQueue.add(new ScheduledEvent<>(event, scheduledTime));
  }

  public void simulate() {
    while (!eventsQueue.isEmpty() && !stop()) {
      ScheduledEvent<S> currentEvent = eventsQueue.poll();
      assert(currentEvent != null);
      currentSimulationTime = currentEvent.getScheduledTime();
      currentEvent.getEvent().invoke(getSimulationType());
    }
  }

  public double getCurrentTime() {
    return currentSimulationTime;
  }

}
