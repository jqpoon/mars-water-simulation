package simulation.complex.events;

import simulation.complex.ConvertAndGenerateSim;
import simulation.framework.Event;

public class RecycleWaterEvent implements Event<ConvertAndGenerateSim> {

  @Override
  public void invoke(ConvertAndGenerateSim simulation) {
    simulation.getWaterRecycler().convert();
  }
}
