package entities;

public class Human {

  public static final double INITIAL_HEALTH = 100;
  private static final double LACK_OF_WATER_HEALTH_REDUCTION_SCALE = 1;

  private final WaterTank potableWaterTank;
  private final WaterTank wasteWaterTank;
  private double health = INITIAL_HEALTH;

  public Human(WaterTank potableWaterTank, WaterTank wasteWaterTank) {
    this.potableWaterTank = potableWaterTank;
    this.wasteWaterTank = wasteWaterTank;
  }

  public double getHealth() {
    return health;
  }

  public void drink(double volume) {
    double volumeDrank = potableWaterTank.withdrawWater(volume, WaterQuality.HIGH);
    if (volumeDrank != volume) {
      health -= (volume - volumeDrank) * LACK_OF_WATER_HEALTH_REDUCTION_SCALE;
      health = Math.max(health, 0);
      System.out.println("Insufficient water drank! Current health: " + health);
    }

  }

  public void excreteWaste(double volume) {
    wasteWaterTank.depositWater(volume, WaterQuality.LOW);
  }

}
