package entities;

public class HumanBuilder {

  private WaterTank potableWaterTank;
  private WaterTank wasteWaterTank;

  public HumanBuilder withPotableWaterTank(WaterTank potableWaterTank) {
    this.potableWaterTank = potableWaterTank;
    return this;
  }

  public HumanBuilder withWasteWaterTank(WaterTank wasteWaterTank) {
    this.wasteWaterTank = wasteWaterTank;
    return this;
  }

  public Human build() {
    return new Human(potableWaterTank, wasteWaterTank);
  }
}