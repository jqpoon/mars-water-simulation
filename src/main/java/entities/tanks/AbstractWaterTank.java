package entities.tanks;

import entities.WaterQuality;

public abstract class AbstractWaterTank implements WaterTank {

  private double currentVolume = 0.0;

  public abstract WaterQuality getTankType();

  @Override
  public double getCurrentVolume() {
    return currentVolume;
  }

  /* Returns actual volume of water withdrawn. */
  @Override
  public double withdrawWater(double volume) {
    double volumeNow = getCurrentVolume();

    if (volume >= volumeNow) {
      currentVolume = 0;
      return volumeNow;
    }

    currentVolume -= volume;
    return volume;
  }

  /* Returns actual volume of water deposited. */
  @Override
  public double depositWater(double volume) {
    currentVolume += volume;
    return volume;
  }

}
