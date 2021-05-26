package simulation.complex.events;

import simulation.complex.ConvertAndGenerateSim;
import simulation.framework.Event;

public class GenerateWaterEvent implements Event<ConvertAndGenerateSim> {

  @Override
  public void invoke(ConvertAndGenerateSim simulation) {
    simulation.getProductionUnit().generate();
  }
}
