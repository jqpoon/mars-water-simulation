package simulation.complex;

import entities.Human;
import simulation.framework.Event;

public class ExcreteWasteEvent implements Event<ComplexSimulation> {

  @Override
  public void invoke(ComplexSimulation simulation) {
    Human human = simulation.getHuman();
    human.excreteWaste(5);
    System.out.printf("Tried going to toilet at: %.4f%n", simulation.getCurrentTime());
  }
}
