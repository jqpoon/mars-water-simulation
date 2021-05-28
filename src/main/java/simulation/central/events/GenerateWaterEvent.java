package simulation.central.events;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

public class GenerateWaterEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    simulation.getProductionUnit().generate();
  }
}
