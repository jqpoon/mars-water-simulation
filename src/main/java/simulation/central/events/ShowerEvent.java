package simulation.central.events;

import static entities.WaterUseCase.*;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

/* Uses water for hygiene purposes (includes shower, brushing teeth, etc).
 * This event is scheduled SHOWER_FREQUENCY times a day per human. */
public class ShowerEvent implements Event<CentralSystemSim> {

  private final int humanId;

  public ShowerEvent(int humanId) {
    this.humanId = humanId;
  }

  @Override
  public void invoke(CentralSystemSim simulation) {
    simulation.getHumanById(humanId).shower(HYGIENE_VOLUME);
  }
}
