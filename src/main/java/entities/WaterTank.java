package entities;

public interface WaterTank {

  double getCurrentVolume(WaterQuality quality);

  /* Returns actual volume of water withdrawn. */
  double withdrawWater(double volume, WaterQuality quality);

  /* Returns actual volume of water deposited. */
  double depositWater(double volume, WaterQuality quality);
}
