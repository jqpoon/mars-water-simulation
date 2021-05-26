package simulation.complex.events.timed;

import simulation.complex.ConvertAndGenerateSim;
import simulation.complex.events.GenerateWaterEvent;
import simulation.framework.Event;

public class DailyEvent implements Event<ConvertAndGenerateSim> {

  private static final int HOURS_IN_DAY = 24;

  @Override
  public void invoke(ConvertAndGenerateSim simulation) {
    double currTime = simulation.getCurrentTime();
    /* Generate water at 12am everyday. */
    simulation.schedule(new GenerateWaterEvent(), currTime);

    /* Schedule next day's hourly events. */
    if ((int) currTime % HOURS_IN_DAY == 0) {
      for (int i = 0; i < HOURS_IN_DAY; i++) {
        simulation.schedule(new HourlyEvent(), currTime + i);
      }
    }
  }
}
