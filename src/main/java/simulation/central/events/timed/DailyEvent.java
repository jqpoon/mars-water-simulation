package simulation.central.events.timed;

import simulation.central.CentralSystemSim;
import simulation.central.events.colony.GenerateWaterEvent;
import simulation.framework.Event;

public class DailyEvent implements Event<CentralSystemSim> {

  private static final int HOURS_IN_DAY = 24;

  @Override
  public void invoke(CentralSystemSim simulation) {
    double currTime = simulation.getCurrentTime();

    /* Generate water at 12am everyday. */
    simulation.schedule(new GenerateWaterEvent(), currTime);

    // Recycle water at 12am everyday.

    // Schedule 10 random drink water events for each human each day

    // Schedule a medical event each day for each human with a probability of
    // 0.01, 0.2-1L each time uniform distribution

    // Water crops for entire colony

    // Hygiene twice a day per person

    // Laundry done once per day

    /* Schedule next day's hourly events. */
    if ((int) currTime % HOURS_IN_DAY == 0) {
      for (int i = 0; i < HOURS_IN_DAY; i++) {
        simulation.schedule(new HourlyEvent(), currTime + i);
      }
    }
  }
}
