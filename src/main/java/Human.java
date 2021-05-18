public class Human {

  public static final double INITIAL_HEALTH = 100;
  private static final double LACK_OF_WATER_HEALTH_REDUCTION_SCALE = 1;

  private final WaterTank potableWaterTank;
  private double health = INITIAL_HEALTH;

  public Human(WaterTank potableWaterTank) {
    this.potableWaterTank = potableWaterTank;
  }

  public double getHealth() {
    return health;
  }

  public void drink(int volume) {
    double volumeDrank = potableWaterTank.withdrawWater(volume);
    if (volumeDrank != volume) {
      health -= (volume - volumeDrank) * LACK_OF_WATER_HEALTH_REDUCTION_SCALE;
      System.out.println("Insufficient water drank!");
    }
  }
}
