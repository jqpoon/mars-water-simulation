package entities;

import static entities.WaterUseCase.*;

import entities.tanks.SmartWaterTank;
import entities.tanks.WaterTank;
import java.util.HashMap;
import java.util.Map;

public class Human {

  public static final double INITIAL_SOL_VALUE = 5;
  public static final double MAX_SOL_VALUE = 10;
  private final Map<WaterUseCase, Double> importanceFactors = new HashMap<>();

  private final WaterTank potableWaterTank;
  private final WaterTank wasteWaterTank;
  private int humanId;
  private double standardOfLiving = INITIAL_SOL_VALUE;

  public Human(WaterTank potableWaterTank, WaterTank wasteWaterTank) {
    this.potableWaterTank = potableWaterTank;
    this.wasteWaterTank = wasteWaterTank;

    importanceFactors.put(DRINK, DRINK_IMPORTANCE);
    importanceFactors.put(CROP, FOOD_IMPORTANCE);
    importanceFactors.put(HYGIENE, HYGIENE_IMPORTANCE);
    importanceFactors.put(FLUSH, FLUSH_IMPORTANCE);
    importanceFactors.put(ELECTROLYSIS, ELECTROLYSIS_IMPORTANCE);
    importanceFactors.put(MEDICAL, MEDICAL_IMPORTANCE);
    importanceFactors.put(LAUNDRY, LAUNDRY_IMPORTANCE);
  }

  public void setHumanId(int humanId) {
    this.humanId = humanId;
  }

  public double getStandardOfLiving() {
    return standardOfLiving;
  }

  /* Attempts to use water from the potable water tank. If unable to use
   * the requested amount, then reduce SoL scaled to a constant. */
  public void useWater(WaterUseCase useCase, double volumeRequested) {

    double importance = importanceFactors.get(useCase);
    double volumeDrank;

    /* Potential for refactor here. How to deal with smart water tanks
     * with a specific method call? */
    if (potableWaterTank instanceof SmartWaterTank) {
      volumeDrank = ((SmartWaterTank) potableWaterTank)
          .withdrawWaterWithReason(volumeRequested, useCase);
    } else {
      volumeDrank = potableWaterTank.withdrawWater(volumeRequested);
    }

    if (volumeDrank != volumeRequested) {
      standardOfLiving -=
          (volumeRequested - volumeDrank) / volumeRequested * importance;
      standardOfLiving = Math.max(standardOfLiving, 0);

//      System.out.printf("Insufficient water for %s! for Blob %d%n", useCase.name(), humanId);
    } else {
      standardOfLiving += importance;
    }

    /* Cap standard of living to a certain value. */
    standardOfLiving = Math.min(standardOfLiving, MAX_SOL_VALUE);
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
}
