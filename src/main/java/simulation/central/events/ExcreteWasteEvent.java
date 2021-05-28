package simulation.central.events;

import entities.Human;
import entities.WaterUseCase;
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
    human.excreteWaste(WaterUseCase.DRINK_VOLUME / simulation.DRINK_FREQUENCY);
    System.out.printf("Tried going to toilet at: %.4f%n", simulation.getCurrentTime());

    simulation.schedule(new FlushEvent(), simulation.getCurrentTime());
  }
}
