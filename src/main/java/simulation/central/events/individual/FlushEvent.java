package simulation.central.events.individual;

import static entities.WaterUseCase.*;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

/* After using the toilet, human flushes it. This event is scheduled the same
 * number of times drink is scheduled a day, since a flush event always happens
 * after a excrete waste event, which itself is scheduled after a drink event. */
public class FlushEvent implements Event<CentralSystemSim> {

  private final int humanId;

  public FlushEvent(int humanId) {
    this.humanId = humanId;
  }

  @Override
  public void invoke(CentralSystemSim simulation) {
    simulation.getHumanById(humanId)
        .flush(FLUSH.getDailyVolume() / FLUSH.getDailyFrequency());
  }
}
