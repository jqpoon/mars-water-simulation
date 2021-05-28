package simulation.central.events;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

/* Recycles waste water to clean water once a day at midnight, for the
 * whole colony. */
public class RecycleWaterEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    simulation.getWaterRecycler().convert();
  }
}
