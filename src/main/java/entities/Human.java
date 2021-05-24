package entities;

import entities.tanks.WaterTank;

public class Human {

  public static final double INITIAL_SOL_VALUE = 100;
  private static final double LACK_OF_WATER_HEALTH_REDUCTION_SCALE = 1;

  private final WaterTank potableWaterTank;
  private final WaterTank wasteWaterTank;
  private double standardOfLiving = INITIAL_SOL_VALUE;

  public Human(WaterTank potableWaterTank, WaterTank wasteWaterTank) {
    this.potableWaterTank = potableWaterTank;
    this.wasteWaterTank = wasteWaterTank;
  }

  public double getStandardOfLiving() {
    return standardOfLiving;
  }

  /* Attempts to drink water from the potable water tank. If unable to drink
   * the requested amount, then reduce health scaled to a constant. */
  public void drink(double volume) {
    double volumeDrank = potableWaterTank.withdrawWater(volume);
    if (volumeDrank != volume) {
      standardOfLiving -=
          (volume - volumeDrank) * LACK_OF_WATER_HEALTH_REDUCTION_SCALE;
      standardOfLiving = Math.max(standardOfLiving, 0);
      System.out.printf(
          "Insufficient water drank! Current standard of living: %.02f%n", standardOfLiving);
    }
  }

  public void excreteWaste(double volume) {
    wasteWaterTank.depositWater(volume);
  }

}
