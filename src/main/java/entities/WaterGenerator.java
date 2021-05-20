package entities;

import entities.tanks.HighQualityWaterTank;
import entities.tanks.LowQualityWaterTank;
import entities.tanks.WaterTank;

public class WaterGenerator {

  private final WaterTank tank;
  private final double volumePerGeneration;

  private WaterGenerator(WaterQuality quality, double volumePerGeneration) {
    switch (quality) {
      case LOW -> tank = new LowQualityWaterTank();
      case HIGH -> tank = new HighQualityWaterTank();
      default -> throw new RuntimeException();
    }
    this.volumePerGeneration = volumePerGeneration;
  }

  public static WaterGenerator LowQualityWaterGenerator(double volumePerGeneration) {
    return new WaterGenerator(WaterQuality.LOW, volumePerGeneration);
  }

  public static WaterGenerator HighQualityWaterGenerator(double volumePerGeneration) {
    return new WaterGenerator(WaterQuality.HIGH, volumePerGeneration);
  }

  public void generate() {
    tank.depositWater(volumePerGeneration);
  }

  public double getCurrentVolume() {
    return tank.getCurrentVolume();
  }
}
