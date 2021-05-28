package simulation.central.events;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

public class RecycleWaterEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    simulation.getWaterRecycler().convert();
  }
}
