package simulation.central.events.timed;

import static simulation.central.CentralSystemSim.HOURS_IN_A_DAY;

import simulation.central.CentralSystemSim;
import simulation.central.events.colony.GenerateWaterEvent;
import simulation.central.events.colony.LaundryEvent;
import simulation.central.events.colony.RecycleWaterEvent;
import simulation.central.events.human.DrinkWaterEvent;
import simulation.central.events.human.MedicalEvent;
import simulation.central.events.human.ShowerEvent;
import simulation.framework.Event;

public class DailyEvent implements Event<CentralSystemSim> {

  @Override
  public void invoke(CentralSystemSim simulation) {
    double currTime = simulation.getCurrentTime();

    /* Generate water at 12am everyday. */
    simulation.schedule(new GenerateWaterEvent(), currTime);

    /* Recycle water at 12am everyday. */
    simulation.schedule(new RecycleWaterEvent(), currTime);

    scheduleRandomDrinkEvents(simulation);
    scheduleRandomMedicalEvent(simulation);
    scheduleCropEvent(simulation);
    scheduleShowerEvents(simulation);
    scheduleLaundryEvent(simulation);

    /* Schedule next day's hourly events. */
    if ((int) currTime % HOURS_IN_A_DAY == 0) {
      for (int i = 0; i < HOURS_IN_A_DAY; i++) {
        simulation.schedule(new HourlyEvent(), currTime + i);
      }
    }

    /* Schedule the next daily event. */
    simulation.schedule(new DailyEvent(), currTime + HOURS_IN_A_DAY);
  }

  /* Schedule 10 random drink water events for each human everyday. */
  private void scheduleRandomDrinkEvents(CentralSystemSim simulation) {
    for (int humanId = 0; humanId < simulation.getPopulation(); humanId++) {
      for (int j = 0; j < simulation.DRINK_FREQUENCY; j++) {
        double randomTimeOffset = simulation.getRandomDouble() * HOURS_IN_A_DAY;
        simulation.schedule(new DrinkWaterEvent(humanId),
            simulation.getCurrentTime() + randomTimeOffset);
      }
    }
  }

  /* Schedule a medical event each day for each human with a probability of
   * 0.01, 0.2-1L each time. */
  private void scheduleRandomMedicalEvent(CentralSystemSim simulation) {
    for (int humanId = 0; humanId < simulation.getPopulation(); humanId++) {
      if (simulation.getRandomDouble() < 0.01) {
        simulation.schedule(new MedicalEvent(humanId),
            simulation.getCurrentTime());
      }
    }
  }

  /* Water crops for entire colony, once a day. */
  private void scheduleCropEvent(CentralSystemSim simulation) {

  }

  /* Schedule a shower event twice a day per human. */
  private void scheduleShowerEvents(CentralSystemSim simulation) {
    for (int humanId = 0; humanId < simulation.getPopulation(); humanId++) {
      for (int j = 0; j < simulation.SHOWER_FREQUENCY; j++) {
        double randomTimeOffset = simulation.getRandomDouble() * HOURS_IN_A_DAY;
        simulation.schedule(new ShowerEvent(humanId),
            simulation.getCurrentTime() + randomTimeOffset);
      }
    }
  }

  /* Schedule a colony-wide laundry event once a day. */
  private void scheduleLaundryEvent(CentralSystemSim simulation) {
    double randomTimeOffset = simulation.getRandomDouble() * HOURS_IN_A_DAY;
    simulation.schedule(new LaundryEvent(),
        simulation.getCurrentTime() + randomTimeOffset);
  }
}
