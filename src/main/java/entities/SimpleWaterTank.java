package entities;

public class SimpleWaterTank implements WaterTank {

  private double currentVolume = 0;

  @Override
  public double getCurrentVolume() {
    return currentVolume;
  }

  /* Returns actual volume of water withdrawn. */
  @Override
  public double withdrawWater(double volume) {
    if (volume >= currentVolume) {
      double actualVolumeReturned = currentVolume;
      currentVolume = 0;
      return actualVolumeReturned;
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
