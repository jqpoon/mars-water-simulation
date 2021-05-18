public class WaterTank {

  private float currentVolume = 0;

  public float getCurrentVolume() {
    return currentVolume;
  }

  /* Returns actual volume of water withdrawn. */
  public float withdrawWater(float volume) {
    if (volume >= currentVolume) {
      float actualVolumeReturned = currentVolume;
      currentVolume = 0;
      return actualVolumeReturned;
    }

    currentVolume -= volume;
    return volume;
  }

  /* Returns actual volume of water deposited. */
  public float depositWater(float volume) {
    currentVolume += volume;
    return volume;
  }

}
