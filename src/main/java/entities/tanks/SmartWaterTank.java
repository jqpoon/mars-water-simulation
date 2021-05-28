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

  /* If there is any unused water allocated to a certain use case at the end
   * of the day, we want to add this to next day's quota. */
  public void resetLimit() {
    for (Map.Entry<WaterUseCase, Double> entry : originalWaterDistribution.entrySet()) {
      double volumeToAdd = entry.getValue();
      double currentVolumeLeft = currentWaterAvailable.get(entry.getKey());
      currentWaterAvailable.put(entry.getKey(), currentVolumeLeft + volumeToAdd);
    }
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
  public double withdrawWaterWithReason(double volumeRequested, WaterUseCase reason) {
    double volumeLeft = currentWaterAvailable.get(reason);

    /* Remove volume we have withdrawn from our water available. */
    currentWaterAvailable.put(reason, Math.max(volumeLeft - volumeRequested, 0));

    /* Withdraw up to the maximum allocated only. */
    return withdrawWater(Math.min(volumeRequested, volumeLeft));
  }

  public void printWaterAvailable() {
    for (Map.Entry<WaterUseCase, Double> entry : currentWaterAvailable.entrySet()) {
      System.out.printf("%s - %.2f%n", entry.getKey().name(), entry.getValue());
    }
  }
}
