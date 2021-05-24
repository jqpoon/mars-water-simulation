package simulation.complex;

import entities.Human;
import simulation.framework.Event;

/* Human drinks water and excretes that water some time later. */
public class DrinkWaterEvent implements Event<ComplexSimulation> {

  @Override
  public void invoke(ComplexSimulation simulation) {
    Human human = simulation.getHuman();
    System.out.printf("Tried drinking water at: %.4f%n", simulation.getCurrentTime());
    human.drink(ComplexSimulation.HUMAN_CONSUMPTION_PER_DAY);

    /* Wait some time. */
    double waitTime = simulation.getRandomInstance().nextFloat();
    simulation.schedule(new ExcreteWasteEvent(),
        waitTime + simulation.getCurrentTime());
  }
}
