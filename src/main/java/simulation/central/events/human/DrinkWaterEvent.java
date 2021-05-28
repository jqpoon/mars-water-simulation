package simulation.central.events.human;

import static entities.WaterUseCase.*;

import entities.Human;
import simulation.central.CentralSystemSim;
import simulation.framework.Event;

/* Human drinks water and excretes that water some time later. This event
 * is scheduled DRINK_FREQUENCY times everyday at random times. */
public class DrinkWaterEvent implements Event<CentralSystemSim> {

  private final int humanId;

  public DrinkWaterEvent(int humanId) {
    this.humanId = humanId;
  }

  @Override
  public void invoke(CentralSystemSim simulation) {
    Human human = simulation.getHumanById(humanId);
//    System.out.printf("Blob %d Tried drinking water at: %.4f%n", humanId, simulation.getCurrentTime());
    human.drink(DRINK.getDailyVolume() / DRINK.getDailyFrequency());

    /* Wait some time before going to the toilet. */
    double waitTime = simulation.getRandomDouble();
    simulation.schedule(new ExcreteWasteEvent(humanId),
        waitTime + simulation.getCurrentTime());
  }
}
