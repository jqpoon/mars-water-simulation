package entities;

import static entities.WaterUseCase.*;

import entities.tanks.SmartWaterTank;
import entities.tanks.WaterTank;
import java.util.HashMap;
import java.util.Map;

public class Human {

  public static final double INITIAL_SOL_VALUE = 5;
  public static final double MAX_SOL_VALUE = 10;
  public static final double HUMAN_WATER_USE_EFFICIENCY = 220.0 / 250.0;

  /* Tracks whether this human has satisfied each component at the end
   * of the day. If yes, increase its standard of living by the component's
   * importance factor. */
  private final Map<WaterUseCase, Boolean> componentSatisfaction = new HashMap<>();
  private final WaterTank potableWaterTank;
  private final WaterTank wasteWaterTank;
  private final WaterTank cropWaterTank;
  private int humanId;
  private double standardOfLiving = INITIAL_SOL_VALUE;

  public Human(WaterTank potableWaterTank, WaterTank wasteWaterTank, WaterTank cropWaterTank) {
    this.potableWaterTank = potableWaterTank;
    this.wasteWaterTank = wasteWaterTank;
    this.cropWaterTank = cropWaterTank;
    resetComponentSatisfaction();
  }

  /* Human starts the day satisfied with each component. */
  public void resetComponentSatisfaction() {
    for (WaterUseCase useCase : WaterUseCase.values()) {
      componentSatisfaction.put(useCase, true);
    }
  }

  /* Calculate end of day SOL based on satisfaction in each component. */
  public void calculateEndOfDaySOL() {
    for (Map.Entry<WaterUseCase, Boolean> entry : componentSatisfaction.entrySet()) {
      if (entry.getValue()) {
        /* Component was satisfied, so we can add to the SOL. */
        standardOfLiving += entry.getKey().getImportance();
        standardOfLiving = Math.min(standardOfLiving, MAX_SOL_VALUE);
      }
    }
  }

  public void setHumanId(int humanId) {
    this.humanId = humanId;
  }

  public double getStandardOfLiving() {
    return standardOfLiving;
  }

  private void updateStandardOfLiving(WaterUseCase useCase, double volumeRequested,
      double volumeReceived) {

    double importance = useCase.getImportance();

    if (volumeReceived != volumeRequested) {
      standardOfLiving -=
          (volumeRequested - volumeReceived) / volumeRequested
              * importance / useCase.getDailyFrequency();
      standardOfLiving = Math.max(standardOfLiving, 0);

      componentSatisfaction.put(useCase, false);
//      System.out.printf("Insufficient water for %s! for Blob %d%n", useCase.name(), humanId);
    }

    /* Cap standard of living to a certain value. */
    standardOfLiving = Math.min(standardOfLiving, MAX_SOL_VALUE);

    /* Special case if medical usage is not fulfilled. */
    if (useCase.equals(MEDICAL) && !componentSatisfaction.get(MEDICAL)) {
      standardOfLiving = 0;
    }
  }

  /* Attempts to use water from the potable water tank. If unable to use
   * the requested amount, then reduce SoL scaled to a constant. */
  public void useWater(WaterUseCase useCase, double volumeRequested) {
    double volumeReceived;

    /* TODO: Potential for refactor here. How to deal with smart water tanks
     * with a specific method call? */
    if (potableWaterTank instanceof SmartWaterTank) {
      volumeReceived = ((SmartWaterTank) potableWaterTank)
          .withdrawWaterWithReason(volumeRequested, useCase);
    } else {
      volumeReceived = potableWaterTank.withdrawWater(volumeRequested);
    }

    /* Calculate new standard of living accordingly. */
    updateStandardOfLiving(useCase, volumeRequested, volumeReceived);
  }

  public void drink(double volume) {
    useWater(DRINK, volume);
  }

  public void shower(double volume) {
    useWater(HYGIENE, volume);
    wasteWaterTank.depositWater(volume);
  }

  public void flush(double volume) {
    useWater(FLUSH, volume);
    wasteWaterTank.depositWater(volume);
  }

  public void medicalUse(double volume) {
    useWater(MEDICAL, volume);
    /* All water used for medical purposes is lost. */
  }

  public void excreteWaste(double volume) {
    wasteWaterTank.depositWater(volume);
  }

  public void eatFood(double volume) {
    double waterReceived = cropWaterTank.withdrawWater(volume);
    updateStandardOfLiving(EAT, volume, waterReceived);
  }
}
