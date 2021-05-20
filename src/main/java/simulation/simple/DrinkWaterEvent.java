package simulation.simple;

import entities.Human;
import java.util.Random;
import simulation.framework.Event;

/* Simulation event that causes human to drink 10L of water. After drinking,
 * it will schedule a excreteWasteEvent a random time after the current time. */
public class DrinkWaterEvent implements Event<SimpleSimulation> {

  Random random = new Random(42);

  @Override
  public void invoke(SimpleSimulation simulation) {
    Human human = simulation.getHuman();
    System.out.println("Tried drinking water at: " + simulation.getCurrentTime());
    human.drink(10);

    /* Wait some time. */
    double waitTime = random.nextFloat();
    simulation.schedule(new ExcreteWasteEvent(),
        waitTime + simulation.getCurrentTime());
  }
}
