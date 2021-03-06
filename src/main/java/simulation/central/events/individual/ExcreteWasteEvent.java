package simulation.central.events.individual;

import static entities.WaterUseCase.*;

import entities.Human;
import simulation.central.CentralSystemSim;
import simulation.framework.Event;

/* Human excretes water through sweat, breathing, toilet usage. This
 * event is scheduled a random time after every drinking event. */
public class ExcreteWasteEvent implements Event<CentralSystemSim> {

  private final int humanId;

  public ExcreteWasteEvent(int humanId) {
    this.humanId = humanId;
  }

  @Override
  public void invoke(CentralSystemSim simulation) {
    Human human = simulation.getHumanById(humanId);
    human.excreteWaste(DRINK.getDailyVolume() / DRINK.getDailyFrequency()
        * Human.HUMAN_WATER_USE_EFFICIENCY);

    simulation.schedule(new FlushEvent(humanId), simulation.getCurrentTime());
  }
}
