public interface WaterTank {

  double getCurrentVolume();

  /* Returns actual volume of water withdrawn. */
  double withdrawWater(double volume);

  /* Returns actual volume of water deposited. */
  double depositWater(double volume);
}
