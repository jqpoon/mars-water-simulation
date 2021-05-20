package entities.tanks;

import entities.WaterQuality;

public class LowQualityWaterTank extends AbstractWaterTank {

  @Override
  public WaterQuality getTankType() {
    return WaterQuality.LOW;
  }
}
