package simulation.simple;

import entities.Human;
import java.util.Random;
import simulation.framework.Event;

public class ExcreteWasteEvent implements Event<SimpleSimulation> {

  Random random = new Random(50);

  @Override
  public void invoke(SimpleSimulation simulation) {
    Human human = simulation.getHuman();
    human.excreteWaste(5);
    System.out.println("Tried going to toilet at: " + simulation.getCurrentTime());

    /* Wait some time. */
    double waitTime = random.nextFloat();
    simulation.schedule(new DrinkWaterEvent(),
        waitTime + simulation.getCurrentTime());

  }
}
