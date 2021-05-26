package simulation.complex.events.timed;

import simulation.complex.ConvertAndGenerateSim;
import simulation.complex.events.DrinkWaterEvent;
import simulation.complex.events.RecycleWaterEvent;
import simulation.framework.Event;

public class HourlyEvent implements Event<ConvertAndGenerateSim> {

  @Override
  public void invoke(ConvertAndGenerateSim simulation) {
    double currTime = simulation.getCurrentTime();
    System.out.printf("--------- HOUR %.0f ---------%n", currTime);
    if ((int) currTime % ConvertAndGenerateSim.HOURS_BETWEEN_RECYCLE_EVENT == 0) {
      simulation.schedule(new RecycleWaterEvent(), currTime);
    }

    simulation.schedule(new DrinkWaterEvent(), currTime);
  }
}
