package entities;

public enum WaterUseCase {
  DRINK,
  CROP,
  HYGIENE,
  FLUSH,
  MEDICAL,
  LAUNDRY,
  ELECTROLYSIS;
  /* Possible refactor to put this into each simulation,
   * so that we can have constructors for each enum.
   * Whenever we add an enum, we need to update its importance value in Human
   * and its usage value in CentralSystemSim which is not ideal. */

  public static final double DRINK_VOLUME = 1.62;
  public static final double CROP_VOLUME = 1.34;
  public static final double HYGIENE_VOLUME = 0.428;
  public static final double LAUNDRY_VOLUME = 7.33;
  public static final double FLUSH_VOLUME = 0.067;
  public static final double ELECTROLYSIS_VOLUME = 2.663;

  public static final double DRINK_IMPORTANCE = 0.95;
  public static final double FOOD_IMPORTANCE = 0.75;
  public static final double HYGIENE_IMPORTANCE = 0.4;
  public static final double LAUNDRY_IMPORTANCE = 0.3;
  public static final double FLUSH_IMPORTANCE = 0.35;
  public static final double ELECTROLYSIS_IMPORTANCE = 1.2;
  public static final double MEDICAL_IMPORTANCE = 1;
}
