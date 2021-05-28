package simulation.central.events.colony;

import static entities.WaterUseCase.*;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

/* Wash clothes for humans. This event is scheduled once per day
 * for the entire colony. */
public class LaundryEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    simulation.useWaterFromCentralTank(LAUNDRY,
        LAUNDRY.getDailyVolume() * simulation.getPopulation());
  }
}
