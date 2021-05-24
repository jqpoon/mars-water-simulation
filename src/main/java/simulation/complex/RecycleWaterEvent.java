package simulation.complex;

import simulation.framework.Event;

public class RecycleWaterEvent implements Event<ComplexSimulation> {

  @Override
  public void invoke(ComplexSimulation simulation) {
    simulation.getWaterRecycler().convert();
  }
}
