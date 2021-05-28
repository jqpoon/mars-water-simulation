package entities;

import entities.tanks.WaterTank;

public class HumanBuilder {

  private WaterTank potableWaterTank;
  private WaterTank wasteWaterTank;
  private WaterTank cropWaterTank;

  public HumanBuilder withPotableWaterTank(WaterTank potableWaterTank) {
    this.potableWaterTank = potableWaterTank;
    return this;
  }

  public HumanBuilder withWasteWaterTank(WaterTank wasteWaterTank) {
    this.wasteWaterTank = wasteWaterTank;
    return this;
  }

  public HumanBuilder withCropWaterTank(WaterTank cropWaterTank) {
    this.cropWaterTank = cropWaterTank;
    return this;
  }

  public Human build() {
    return new Human(potableWaterTank, wasteWaterTank, cropWaterTank);
  }
}