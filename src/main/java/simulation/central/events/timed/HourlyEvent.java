package simulation.central.events.timed;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

public class HourlyEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    double currTime = simulation.getCurrentTime();
    System.out.printf("--------- HOUR %.0f ---------%n", currTime);

    // electrolysis event here
  }
}
