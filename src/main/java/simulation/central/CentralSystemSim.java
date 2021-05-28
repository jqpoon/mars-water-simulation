package simulation.central;

import entities.Human;
import entities.HumanBuilder;
import entities.WaterConverter;
import entities.WaterConverterBuilder;
import entities.WaterGenerator;
import entities.WaterUseCase;
import entities.tanks.CropWaterTank;
import entities.tanks.LowQualityWaterTank;
import entities.tanks.SmartWaterTank;
import entities.tanks.WaterTank;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import simulation.central.events.timed.DailyEvent;
import simulation.framework.Simulation;

public class CentralSystemSim extends Simulation<CentralSystemSim> {

  public static final int HOURS_IN_A_DAY = 24;

  /* Simulation parameters. */
  private final int population = 1;
  private final int simulationDayCount = 1;

  private static final double CROP_EFFICIENCY = 88.0/134.0;
  private static final double CONVERTER_EFFICIENCY = 0.935;
  private static final double VOLUME_PER_CONVERSION = 10000;

  /* Volume listed is per day per person. */
  private static final double DAILY_MAXIMUM_VOLUME_USABLE = 10;
  private static final double VOLUME_PER_GENERATION = 4.128;

  private final Map<Integer, Human> allHumans = new HashMap<>();
  private final SmartWaterTank centralWaterTank;
  private final CropWaterTank cropWaterTank;
  private final WaterTank wasteWaterTank;
  private final WaterGenerator productionUnit;
  private final WaterConverter waterRecycler;
  private final Random randomInst;

  public CentralSystemSim(double drinkingPercentage, double cropPercentage,
      double hygienePercentage, double laundryPercentage,
      double flushPercentage, double medicalPercentage,
      double electrolysisPercentage) {

    /* Check percentages sum to one. */
    if (drinkingPercentage + cropPercentage + hygienePercentage
        + laundryPercentage + flushPercentage + medicalPercentage
        + electrolysisPercentage != 1) {
      System.out.println("Percentages don't sum to one!");
      System.exit(1);
    }

    /* Initialise simulation related fields. */
    this.randomInst = new Random(42);

    /* Initialise tank entities. */
    cropWaterTank = new CropWaterTank(CROP_EFFICIENCY);
    centralWaterTank = new SmartWaterTank(
        DAILY_MAXIMUM_VOLUME_USABLE * population);
    wasteWaterTank = new LowQualityWaterTank();
    productionUnit = new WaterGenerator(centralWaterTank,
        VOLUME_PER_GENERATION * population);
    waterRecycler = new WaterConverterBuilder()
        .withEfficiency(CONVERTER_EFFICIENCY)
        .withSourceTank(wasteWaterTank)
        .withDestinationTank(centralWaterTank)
        .withVolumePerConversion(VOLUME_PER_CONVERSION * population)
        .build();

    /* Create humans. */
    for (int i = 0; i < population; i++) {
      Human human = new HumanBuilder()
          .withPotableWaterTank(centralWaterTank)
          .withWasteWaterTank(wasteWaterTank)
          .build();
      human.setHumanId(i);
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

  /* ------------ Domain related functions ------------ */
  public int getPopulation() {
    return population;
  }

  public double getRandomDouble() {
    return randomInst.nextDouble();
  }

  public WaterConverter getWaterRecycler() {
    return waterRecycler;
  }

  public Human getHumanById(int id) {
    return allHumans.get(id);
  }

  /* Should only be used for *colony* wide events, not human specific events. */
  public void useWaterFromCentralTank(WaterUseCase useCase, double volume) {
    for (Human human : allHumans.values()) {
      human.useWater(useCase, volume / population);
    }
  }

  public void generateWater() {
    productionUnit.generate();
  }

  public void resetWaterUsage() {
    centralWaterTank.resetLimit();
  }

  /* ------------ Simulation related functions ------------ */
  @Override
  protected boolean stop() {
    /* Simulation only stops after a certain number of days */
    return getCurrentTime() >= HOURS_IN_A_DAY * simulationDayCount;
  }

  @Override
  protected CentralSystemSim getSimulationType() {
    return this;
  }

  @Override
  protected void initSimulation() {
    centralWaterTank.depositWater(1000);
    schedule(new DailyEvent(), 0);
  }

  public void printStatistics() {
    double averageStandardOfLiving = allHumans.values().stream()
        .mapToDouble(Human::getStandardOfLiving)
        .reduce(Double::sum)
        .orElseThrow() / population;
    System.out.printf("Clean water: %.2f%n", centralWaterTank.getCurrentVolume());
    System.out.printf("Waste water: %.2f%n", wasteWaterTank.getCurrentVolume());
    System.out.printf("Average SOL: %.2f%n", averageStandardOfLiving);
    centralWaterTank.printWaterAvailable();
  }

  public static void main(String[] args) {
    double drinkingPercentage = 0.4;
    double cropPercentage = 0.0;
    double hygienePercentage = 0.0;
    double laundryPercentage = 0.0;
    double flushPercentage = 0.1;
    double medicalPercentage = 0.1;
    double electrolysisPercentage = 0.4;
    CentralSystemSim simulation = new CentralSystemSim(drinkingPercentage,
        cropPercentage, hygienePercentage, laundryPercentage, flushPercentage,
        medicalPercentage, electrolysisPercentage);

    simulation.initSimulation();
    simulation.simulate();
  }

}
