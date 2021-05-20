package entities;

import entities.tanks.WaterTank;

public class WaterConverterBuilder {

  private double volumePerConversion;
  private double efficiency;
  private WaterTank sourceTank;
  private WaterTank destinationTank;

  public WaterConverterBuilder withVolumePerConversion(
      double volumePerConversion) {
    this.volumePerConversion = volumePerConversion;
    return this;
  }

  public WaterConverterBuilder withEfficiency(double efficiency) {
    this.efficiency = efficiency;
    return this;
  }

  public WaterConverterBuilder withSourceTank(WaterTank sourceTank) {
    this.sourceTank = sourceTank;
    return this;
  }

  public WaterConverterBuilder withDestinationTank(WaterTank destinationTank) {
    this.destinationTank = destinationTank;
    return this;
  }

  public WaterConverter build() {
    return new WaterConverter(volumePerConversion, efficiency, sourceTank,
        destinationTank);
  }
}