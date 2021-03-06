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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import simulation.central.events.timed.DailyEvent;
import simulation.framework.Simulation;

public class CentralSystemSim extends Simulation<CentralSystemSim> {

  public static final int HOURS_IN_A_DAY = 24;

  /* Simulation parameters. */
  private final int population;
  private final int simulationDayCount;

  private static final double CROP_EFFICIENCY = 88.0 / 134.0;
  private static final double CONVERTER_EFFICIENCY = 0.935;
  private static final double VOLUME_PER_CONVERSION = 100000;

  /* Volume listed is per day per person. */
  private final double dailyMaximumDailyUsable;
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
      double electrolysisPercentage, int population, int simulationDayCount,
      double dailyMaximumDailyUsable) {

    this.population = population;
    this.simulationDayCount = simulationDayCount;
    this.dailyMaximumDailyUsable = dailyMaximumDailyUsable;

    /* Scale percentages. */
    double totalPercentages =
        drinkingPercentage + cropPercentage + hygienePercentage
            + laundryPercentage + flushPercentage + medicalPercentage
            + electrolysisPercentage;

    drinkingPercentage = drinkingPercentage / totalPercentages;
    cropPercentage = cropPercentage / totalPercentages;
    hygienePercentage = hygienePercentage / totalPercentages;
    laundryPercentage = laundryPercentage / totalPercentages;
    flushPercentage = flushPercentage / totalPercentages;
    medicalPercentage = medicalPercentage / totalPercentages;
    electrolysisPercentage = electrolysisPercentage / totalPercentages;

    /* Initialise simulation related fields. */
    this.randomInst = new Random(42);

    /* Initialise tank entities. */
    cropWaterTank = new CropWaterTank(CROP_EFFICIENCY);
    centralWaterTank = new SmartWaterTank(
        dailyMaximumDailyUsable * population);
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
          .withCropWaterTank(cropWaterTank)
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

  public void growCrops(double volume) {
    double waterAvailable = centralWaterTank
        .withdrawWaterWithReason(volume, WaterUseCase.CROP);
    cropWaterTank.depositWater(waterAvailable);
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
    centralWaterTank.depositWater(840 * population);
    schedule(new DailyEvent(), 0);
  }

  /* ------------ Simulation statistics ------------ */

  private final List<Double> standardOfLivingRecord = new ArrayList<>();

  private double getAverageStandardOfLiving() {
    double sum = 0;
    for (int i = 0; i < population; i++) {
      sum += getHumanById(i).getStandardOfLiving();
    }
    return sum / (double) population;
  }

  public void printStatistics() {
    double averageStandardOfLiving = getAverageStandardOfLiving();
    System.out
        .printf("Clean water: %.2f%n", centralWaterTank.getCurrentVolume());
    System.out.printf("Waste water: %.2f%n", wasteWaterTank.getCurrentVolume());
    System.out.printf("Average SOL: %.2f%n", averageStandardOfLiving);
    centralWaterTank.printWaterAvailable();
  }

  public void recordStatistics() {
    double averageStandardOfLiving = getAverageStandardOfLiving();
    assert(averageStandardOfLiving <= 10);
    standardOfLivingRecord.add(averageStandardOfLiving);
  }

  public void printResultStatistics() {
    StringBuilder outputString = new StringBuilder();
    for (Double value : standardOfLivingRecord) {
      outputString.append(String.format("%.5f ", value));
    }

    System.out.print(outputString.toString());
  }

  public static void main(String[] args) {
  /* Uncomment to use unoptimised, base values. */
//    double drinkingPercentage = 11.99;
//    double cropPercentage = 9.924;
//    double hygienePercentage = 3.17;
//    double laundryPercentage = 54.29;
//    double flushPercentage = 0.496;
//    double medicalPercentage = 0.396;
//    double electrolysisPercentage = 19.72;
//
//    int population = 100;
//    int simulationDayCount = 10;
//    double maximumDailyUsable = 10;

    if (args.length != 10) {
      System.out.println("Usage: ");
      System.out.println(
          "simulation.jar [drinking] [crop] [hygiene] [laundry] [flush] "
              + "[medical] [electrolysis] [population] [noOfDays] "
              + "[maximumDailyUsable]");
      System.exit(1);
    }

    double drinkingPercentage = Double.parseDouble(args[0]);
    double cropPercentage = Double.parseDouble(args[1]);
    double hygienePercentage = Double.parseDouble(args[2]);
    double laundryPercentage = Double.parseDouble(args[3]);
    double flushPercentage = Double.parseDouble(args[4]);
    double medicalPercentage = Double.parseDouble(args[5]);
    double electrolysisPercentage = Double.parseDouble(args[6]);
    int population = Integer.parseInt(args[7]);
    int simulationDayCount = Integer.parseInt(args[8]);
    double maximumDailyUsable = Double.parseDouble(args[9]);

    CentralSystemSim simulation = new CentralSystemSim(drinkingPercentage,
        cropPercentage, hygienePercentage, laundryPercentage, flushPercentage,
        medicalPercentage, electrolysisPercentage, population,
        simulationDayCount, maximumDailyUsable);

    simulation.initSimulation();
    simulation.simulate();
    simulation.printResultStatistics();
  }

}
