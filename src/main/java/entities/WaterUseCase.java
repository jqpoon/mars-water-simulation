package entities;

public enum WaterUseCase {
  DRINK(1.62, 0.95, Frequency.DRINK_FREQUENCY),
  CROP(1.34, 0, 2),
  HYGIENE(0.428, 0.4, 2),
  FLUSH(7.33, 0.35, Frequency.DRINK_FREQUENCY),
  MEDICAL(0.0535, 0, 0),
  LAUNDRY(7.33, 0.3, 1),
  ELECTROLYSIS(2.663, 1.2, 24),
  EAT(0.88, 0.75, Frequency.DRINK_FREQUENCY);

  private final double dailyVolume;
  private final double importance;
  private final double dailyFrequency;

  WaterUseCase(double dailyVolume, double importance, double dailyFrequency) {
    this.dailyVolume = dailyVolume;
    this.importance = importance;
    this.dailyFrequency = dailyFrequency;
  }

  public double getDailyVolume() {
    return dailyVolume;
  }

  public double getImportance() {
    return importance;
  }

  public double getDailyFrequency() {
    return dailyFrequency;
  }

  /* Possible refactor to put this into each simulation,
   * so that we can have constructors for each enum.
   * Whenever we add an enum, we need to update its importance value in Human
   * and its usage value in CentralSystemSim which is not ideal. */

  private static class Frequency {
    private static final int DRINK_FREQUENCY = 10;
  }
}
