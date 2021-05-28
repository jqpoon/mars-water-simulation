package simulation.central.events.colony;

import static entities.WaterUseCase.*;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

/* Generates oxygen from water. This event is scheduled every hour for the
 * entire colony. */
public class ElectrolysisEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    simulation.useWaterFromCentralTank(ELECTROLYSIS,
        ELECTROLYSIS.getDailyVolume()
            / ELECTROLYSIS.getDailyFrequency()
            * simulation.getPopulation());
  }
}
