package entities;

public class WaterGenerator extends AllQualityWaterTank {

  private final WaterQuality quality;
  private final double volumePerGeneration;

  public WaterGenerator(WaterQuality quality, double volumePerGeneration) {
    this.quality = quality;
    this.volumePerGeneration = volumePerGeneration;
  }

  public void generate() {
    addVolume(volumePerGeneration, quality);
  }
}
