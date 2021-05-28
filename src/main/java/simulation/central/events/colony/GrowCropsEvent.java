package simulation.central.events.colony;

import static entities.WaterUseCase.CROP;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

public class GrowCropsEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    simulation.growCrops(
        CROP.getDailyVolume() / CROP.getDailyFrequency() * simulation
            .getPopulation());
  }
}
