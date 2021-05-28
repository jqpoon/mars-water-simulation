package simulation.central.events.human;

import simulation.central.CentralSystemSim;
import simulation.framework.Event;

/* Use 0.2 - 1L of water for a medical issue randomly every day.
 * This event is scheduled randomly for every human, at most once per day. */
public class MedicalEvent implements Event<CentralSystemSim> {

  private static final double RANGE = 0.8;
  private static final double MIN_VALUE = 0.2;
  private final int humanId;

  public MedicalEvent(int humanId) {
    this.humanId = humanId;
  }

  @Override
  public void invoke(CentralSystemSim simulation) {
    double volumeRequired =
        simulation.getRandomInst().nextDouble() * RANGE + MIN_VALUE;
    simulation.getHumanById(humanId).medicalUse(volumeRequired);
  }
}
