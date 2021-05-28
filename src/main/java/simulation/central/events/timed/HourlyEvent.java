package simulation.central.events.timed;

import simulation.central.CentralSystemSim;
import simulation.central.events.colony.ElectrolysisEvent;
import simulation.framework.Event;

public class HourlyEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    double currTime = simulation.getCurrentTime();
//    System.out.printf("--------- HOUR %.0f ---------%n", currTime);
//    simulation.printStatistics();
    simulation.recordStatistics();

    simulation.schedule(new ElectrolysisEvent(), currTime);
  }
}
