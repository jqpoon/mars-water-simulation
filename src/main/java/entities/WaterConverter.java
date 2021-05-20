package entities;

import entities.tanks.WaterTank;

public class WaterConverter {

  private final double efficiency;
  private final double volumePerConversion;
  private final WaterTank sourceTank;
  private final WaterTank destinationTank;

  public WaterConverter(double volumePerConversion,
      double efficiency, WaterTank sourceTank, WaterTank destinationTank) {
    this.efficiency = efficiency;
    this.volumePerConversion = volumePerConversion;
    this.sourceTank = sourceTank;
    this.destinationTank = destinationTank;
  }

  public double getSourceTankVolume() {
    return sourceTank.getCurrentVolume();
  }

  public double getDestinationTankVolume() {
    return destinationTank.getCurrentVolume();
  }

  public void convert() {
    double volumeToConvert =
        sourceTank.withdrawWater(volumePerConversion) * efficiency;
    destinationTank.depositWater(volumeToConvert);
  }

}
