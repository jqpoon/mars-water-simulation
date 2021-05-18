public class WaterTank {

  private float currentVolume = 0;

  /* Returns actual volume of water withdrawn. */
  public float withdrawWater(float volume) {
    if (currentVolume < volume) {
      return volume - currentVolume;
    }

    return volume;
  }

  /* Returns actual volume of water deposited. */
  public float depositWater(float volume) {
    currentVolume += volume;
    return volume;
  }

}
