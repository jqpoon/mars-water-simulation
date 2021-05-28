package simulation.central.events.colony;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

/* Water is generated for the entire colony every day at midnight. */
public class GenerateWaterEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    simulation.generateWater();
  }
}
