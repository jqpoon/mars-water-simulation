package entities;

import java.util.HashMap;
import java.util.Map;

public class AllQualityWaterTank implements WaterTank {

  private final Map<WaterQuality, Double> allVolumes = new HashMap<>();

  public AllQualityWaterTank() {
    allVolumes.put(WaterQuality.LOW, 0.0);
    allVolumes.put(WaterQuality.HIGH, 0.0);
  }

  protected void updateVolume(double volume, WaterQuality quality) {
    allVolumes.put(quality, volume);
  }

  protected void addVolume(double volume, WaterQuality quality) {
    updateVolume(getCurrentVolume(quality) + volume, quality);
  }

  protected void removeVolume(double volume, WaterQuality quality) {
    updateVolume(getCurrentVolume(quality) - volume, quality);
  }

  @Override
  public double getCurrentVolume(WaterQuality quality) {
    return allVolumes.get(quality);
  }

  /* Returns actual volume of water withdrawn. */
  @Override
  public double withdrawWater(double volume, WaterQuality quality) {
    double currentVolume = getCurrentVolume(quality);

    if (volume >= currentVolume) {
      updateVolume(0, quality);
      return currentVolume;
    }

    removeVolume(volume, quality);
    return volume;
  }

  /* Returns actual volume of water deposited. */
  @Override
  public double depositWater(double volume, WaterQuality quality) {
    addVolume(volume, quality);
    return volume;
  }

}
