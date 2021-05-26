package simulation.complex.events;

import entities.Human;
import simulation.complex.ConvertAndGenerateSim;
import simulation.framework.Event;

/* Human drinks water and excretes that water some time later. */
public class DrinkWaterEvent implements Event<ConvertAndGenerateSim> {

  @Override
  public void invoke(ConvertAndGenerateSim simulation) {
    Human human = simulation.getHuman();
    System.out.printf("Tried drinking water at: %.4f%n", simulation.getCurrentTime());
    human.drink(ConvertAndGenerateSim.HUMAN_CONSUMPTION_PER_DAY);

    /* Wait some time. */
    double waitTime = simulation.getRandomInstance().nextFloat();
    simulation.schedule(new ExcreteWasteEvent(),
        waitTime + simulation.getCurrentTime());
  }
}
