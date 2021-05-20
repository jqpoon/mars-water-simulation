package entities;

import entities.tanks.HighQualityWaterTank;
import entities.tanks.LowQualityWaterTank;
import entities.tanks.WaterTank;

public class WaterGenerator {

  private final WaterTank tank;
  private final double volumePerGeneration;

  public WaterGenerator(WaterTank tank, double volumePerGeneration) {
    this.tank = tank;
    this.volumePerGeneration = volumePerGeneration;
  }

  public static WaterGenerator LowQualityWaterGenerator(double volumePerGeneration) {
    return new WaterGenerator(new LowQualityWaterTank(), volumePerGeneration);
  }

  public static WaterGenerator HighQualityWaterGenerator(double volumePerGeneration) {
    return new WaterGenerator(new HighQualityWaterTank(), volumePerGeneration);
  }

  public void generate() {
    tank.depositWater(volumePerGeneration);
  }

  public double getCurrentVolume() {
    return tank.getCurrentVolume();
  }
}
