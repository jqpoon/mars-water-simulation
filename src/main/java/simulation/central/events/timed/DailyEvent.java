package simulation.central.events.timed;

import static entities.WaterUseCase.*;
import static simulation.central.CentralSystemSim.HOURS_IN_A_DAY;

import simulation.central.CentralSystemSim;
import simulation.central.events.colony.GenerateWaterEvent;
import simulation.central.events.colony.GrowCropsEvent;
import simulation.central.events.colony.LaundryEvent;
import simulation.central.events.colony.RecycleWaterEvent;
import simulation.central.events.individual.DrinkWaterEvent;
import simulation.central.events.individual.MedicalEvent;
import simulation.central.events.individual.ShowerEvent;
import simulation.framework.Event;

public class DailyEvent implements Event<CentralSystemSim> {

  private static final double MEDICAL_CHANCE = 0.01;

  @Override
  public void invoke(CentralSystemSim simulation) {
    double currTime = simulation.getCurrentTime();

    /* Reset water usage. */
    simulation.resetWaterUsage();

    /* Generate water at 12am everyday. */
    simulation.schedule(new GenerateWaterEvent(), currTime);

    /* Recycle water at 12am everyday. */
    simulation.schedule(new RecycleWaterEvent(), currTime);

    scheduleRandomDrinkEvents(simulation);
    scheduleRandomMedicalEvent(simulation);
    scheduleCropEvent(simulation);
    scheduleShowerEvents(simulation);
    scheduleLaundryEvent(simulation);

    /* Calculate daily SOLs for all humans. */
    for (int i = 0; i < simulation.getPopulation(); i++) {
      simulation.getHumanById(i).calculateEndOfDaySOL();
    }

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
      for (int j = 0; j < DRINK.getDailyFrequency(); j++) {
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
      if (simulation.getRandomDouble() < MEDICAL_CHANCE) {
        simulation.schedule(new MedicalEvent(humanId),
            simulation.getCurrentTime());
      }
    }
  }

  /* Schedule watering of crops twice a day. Colony-wide event. */
  private void scheduleCropEvent(CentralSystemSim simulation) {
    for (int i = 0; i < CROP.getDailyFrequency(); i++) {
      double randomTimeOffset = simulation.getRandomDouble() * HOURS_IN_A_DAY;
      simulation.schedule(new GrowCropsEvent(), simulation.getCurrentTime() + randomTimeOffset);
    }
  }

  /* Schedule a shower event twice a day per human. */
  private void scheduleShowerEvents(CentralSystemSim simulation) {
    for (int humanId = 0; humanId < simulation.getPopulation(); humanId++) {
      for (int j = 0; j < HYGIENE.getDailyFrequency(); j++) {
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
