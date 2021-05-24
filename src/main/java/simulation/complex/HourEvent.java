package simulation.complex;

import simulation.framework.Event;

public class HourEvent implements Event<ComplexSimulation> {

  @Override
  public void invoke(ComplexSimulation simulation) {
    double currTime = simulation.getCurrentTime();
    System.out.printf("--------- HOUR %.0f ---------%n", currTime);
    if ((int) currTime % ComplexSimulation.HOURS_BETWEEN_RECYCLE_EVENT == 0) {
      simulation.schedule(new RecycleWaterEvent(), currTime);
    }

    simulation.schedule(new DrinkWaterEvent(), currTime);
  }
}
