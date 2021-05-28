package simulation.central.events;

import entities.Human;
import simulation.central.CentralSystemSim;
import simulation.framework.Event;

public class ExcreteWasteEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    Human human = simulation.getHuman();
    human.excreteWaste(ConvertAndGenerateSim.HUMAN_WASTE_PER_DAY);
    System.out.printf("Tried going to toilet at: %.4f%n", simulation.getCurrentTime());

    // Add flush event here? or make it such that flushing uses some amt of water
  }
}
