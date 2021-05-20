package entities.tanks;

import entities.WaterQuality;

public class HighQualityWaterTank extends AbstractWaterTank {

  @Override
  public WaterQuality getTankType() {
    return WaterQuality.HIGH;
  }
}
