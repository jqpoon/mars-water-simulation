package entities;

import static entities.WaterUseCase.*;

import entities.tanks.WaterTank;
import java.util.HashMap;
import java.util.Map;

public class Human {

  public static final double INITIAL_SOL_VALUE = 7;
  private final Map<WaterUseCase, Double> importanceFactors = new HashMap<>();

  private final WaterTank potableWaterTank;
  private final WaterTank wasteWaterTank;
  private double standardOfLiving = INITIAL_SOL_VALUE;

  public Human(WaterTank potableWaterTank, WaterTank wasteWaterTank) {
    this.potableWaterTank = potableWaterTank;
    this.wasteWaterTank = wasteWaterTank;

    importanceFactors.put(DRINK, DRINK_IMPORTANCE);
    importanceFactors.put(CROP, FOOD_IMPORTANCE);
    importanceFactors.put(HYGIENE, HYGIENE_IMPORTANCE);
    importanceFactors.put(LAUNDRY, LAUNDRY_IMPORTANCE);
    importanceFactors.put(FLUSH, FLUSH_IMPORTANCE);
    importanceFactors.put(ELECTROLYSIS, ELECTROLYSIS_IMPORTANCE);
    importanceFactors.put(MEDICAL, MEDICAL_IMPORTANCE);
  }

  public double getStandardOfLiving() {
    return standardOfLiving;
  }

  /* Attempts to use water from the potable water tank. If unable to use
   * the requested amount, then reduce SoL scaled to a constant. */
  private void useWater(WaterUseCase useCase, double volume) {
    double volumeDrank = potableWaterTank.withdrawWater(volume);
    if (volumeDrank != volume) {
      standardOfLiving -= (volume - volumeDrank) / volume * WaterUseCase.DRINK_IMPORTANCE;
      standardOfLiving = Math.max(standardOfLiving, 0);

      System.out.printf(
          "Insufficient water for %s! Current standard of living: %.02f%n",
          useCase.name(), standardOfLiving);
    }
  }

  public void drink(double volume) {
    useWater(WaterUseCase.DRINK, volume);
  }

  public void shower(double volume) {
    useWater(WaterUseCase.HYGIENE, volume);
  }

  public void doLaundry(double volume) {
    useWater(WaterUseCase.LAUNDRY, volume);
  }

  public void flush(double volume) {
    useWater(WaterUseCase.FLUSH, volume);
  }

  public void medicalUse(double volume) {
    useWater(WaterUseCase.MEDICAL, volume);
  }

  public void excreteWaste(double volume) {
    wasteWaterTank.depositWater(volume);
  }

}
