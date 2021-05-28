package entities.tanks;

import entities.WaterQuality;
import entities.WaterUseCase;
import java.util.HashMap;
import java.util.Map;

public class SmartWaterTank extends AbstractWaterTank {

  private final Map<WaterUseCase, Double> originalWaterDistribution = new HashMap<>();
  private Map<WaterUseCase, Double> currentWaterAvailable = new HashMap<>();
  private final double maximumDailyVolume;

  public SmartWaterTank(double maximumDailyVolume) {
    super();
    this.maximumDailyVolume = maximumDailyVolume;
  }

  public void resetLimit() {
    currentWaterAvailable = new HashMap<>(originalWaterDistribution);
  }

  public void addUseCase(WaterUseCase useCase, double percentage) {
    originalWaterDistribution.put(useCase, percentage * maximumDailyVolume);
    currentWaterAvailable.put(useCase, percentage * maximumDailyVolume);
  }

  @Override
  public WaterQuality getTankType() {
    return WaterQuality.HIGH;
  }

  /* Instead of a normal withdrawal, ensure that we stick to the limits
   * as defined by water use case. */
  public double withdrawWaterWithReason(double volume, WaterUseCase reason) {
    double volumeLeft = currentWaterAvailable.get(reason);

    /* Remove volume we have withdrawn from our water available. */
    currentWaterAvailable.put(reason, Math.max(volumeLeft - volume, 0));

    /* Limit reached. */
    if (volumeLeft < volume) {
      return 0;
    }

    /* Else withdraw as per normal. */
    return withdrawWater(volume);
  }

  public void printWaterAvailable() {
    for (Map.Entry<WaterUseCase, Double> entry : currentWaterAvailable.entrySet()) {
      System.out.printf("%s - %.2f%n", entry.getKey().name(), entry.getValue());
    }
  }
}
