package entities.tanks;

import entities.WaterQuality;

public class CropWaterTank extends AbstractWaterTank {

  /* Crops can only produce so much "usable" water in the form of food.
   * Any other water is assumed to be lost to the environment. */
  private final double cropEfficiency;

  public CropWaterTank(double cropEfficiency) {
    this.cropEfficiency = cropEfficiency;
  }

  @Override
  public WaterQuality getTankType() {
    return WaterQuality.HIGH;
  }

  @Override
  public double depositWater(double volume) {
    return super.depositWater(volume * cropEfficiency);
  }
}
