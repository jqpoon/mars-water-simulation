package simulation.framework;

import java.util.PriorityQueue;
import java.util.Queue;

public abstract class Simulation<S> {
  private double currentSimulationTime = 0;
  private final Queue<ScheduledEvent<S>> eventsQueue = new PriorityQueue<>();

  /* Condition for simulation to be stopped. */
  protected abstract boolean stop();

  /* Returns instance of simulation. Used by events. */
  protected abstract S getSimulationType();

  /* Called once before running a simulation. Sets initial values of simulation,
   * including scheduling any events if necessary. */
  protected abstract void initSimulation();

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
