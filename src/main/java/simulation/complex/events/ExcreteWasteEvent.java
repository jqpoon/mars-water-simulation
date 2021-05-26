package simulation.complex.events;

import entities.Human;
import simulation.complex.ConvertAndGenerateSim;
import simulation.framework.Event;

public class ExcreteWasteEvent implements Event<ConvertAndGenerateSim> {

  @Override
  public void invoke(ConvertAndGenerateSim simulation) {
    Human human = simulation.getHuman();
    human.excreteWaste(ConvertAndGenerateSim.HUMAN_WASTE_PER_DAY);
    System.out.printf("Tried going to toilet at: %.4f%n", simulation.getCurrentTime());
  }
}
