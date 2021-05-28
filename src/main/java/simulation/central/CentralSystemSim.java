package simulation.central;

import entities.Human;
import entities.HumanBuilder;
import entities.WaterConverter;
import entities.WaterConverterBuilder;
import entities.WaterGenerator;
import entities.WaterUseCase;
import entities.tanks.LowQualityWaterTank;
import entities.tanks.SmartWaterTank;
import entities.tanks.WaterTank;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import simulation.framework.Simulation;

public class CentralSystemSim extends Simulation<CentralSystemSim> {

  private static final int HOURS_IN_A_DAY = 24;

  /* Simulation parameters. */
  private static final int POPULATION = 1;
  private static final int NO_OF_SIMULATION_DAYS = 1;

  private static final double VOLUME_PER_GENERATION = 4.128;
  private static final double CONVERTER_EFFICIENCY = 0.935;
  private static final double VOLUME_PER_CONVERSION = 10000;

  /* Volumes listed are per day per person. */
  private static final double DAILY_MAXIMUM_VOLUME_USABLE = 10;

  /* Frequency of events. If not listed, this event is scheduled
   * once a day. */
  public static final int DRINK_FREQUENCY = 10;
  public static final int HYGIENE_FREQUENCY = 2;

  private final Map<Integer, Human> allHumans = new HashMap<>();
  private final SmartWaterTank centralWaterTank;
  private final WaterTank wasteWaterTank;
  private final WaterGenerator productionUnit;
  private final WaterConverter waterRecycler;
  private final Random randomInst;

  public CentralSystemSim(double drinkingPercentage, double cropPercentage,
      double hygienePercentage, double laundryPercentage,
      double flushPercentage, double medicalPercentage,
      double electrolysisPercentage) {

    /* Initialise simulation related fields. */
    this.randomInst = new Random(42);

    /* Initialise tank entities. */
    centralWaterTank = new SmartWaterTank(DAILY_MAXIMUM_VOLUME_USABLE);
    wasteWaterTank = new LowQualityWaterTank();
    productionUnit = new WaterGenerator(centralWaterTank,
        VOLUME_PER_GENERATION);
    waterRecycler = new WaterConverterBuilder()
        .withEfficiency(CONVERTER_EFFICIENCY)
        .withSourceTank(wasteWaterTank)
        .withDestinationTank(centralWaterTank)
        .withVolumePerConversion(VOLUME_PER_CONVERSION)
        .build();

    /* Create humans. */
    for (int i = 0; i < POPULATION; i++) {
      Human human = new HumanBuilder()
          .withPotableWaterTank(centralWaterTank)
          .withWasteWaterTank(wasteWaterTank)
          .build();
      allHumans.put(i, human);
    }

    /* Add ratios for water allocation. */
    centralWaterTank.addUseCase(WaterUseCase.DRINK, drinkingPercentage);
    centralWaterTank.addUseCase(WaterUseCase.CROP, cropPercentage);
    centralWaterTank.addUseCase(WaterUseCase.HYGIENE, hygienePercentage);
    centralWaterTank.addUseCase(WaterUseCase.LAUNDRY, laundryPercentage);
    centralWaterTank.addUseCase(WaterUseCase.FLUSH, flushPercentage);
    centralWaterTank.addUseCase(WaterUseCase.MEDICAL, medicalPercentage);
    centralWaterTank.addUseCase(WaterUseCase.ELECTROLYSIS,
        electrolysisPercentage);
  }

  public Random getRandomInst() {
    return randomInst;
  }

  public WaterGenerator getProductionUnit() {
    return productionUnit;
  }

  public WaterConverter getWaterRecycler() {
    return waterRecycler;
  }

  public Human getHumanById(int id) {
    return allHumans.get(id);
  }

  @Override
  protected boolean stop() {
    /* Simulation only stops after a certain number of days */
    return getCurrentTime() > HOURS_IN_A_DAY * NO_OF_SIMULATION_DAYS;
  }

  @Override
  protected CentralSystemSim getSimulationType() {
    return this;
  }

  @Override
  protected void initSimulation() {

  }

  public static void main(String[] args) {

  }

}
