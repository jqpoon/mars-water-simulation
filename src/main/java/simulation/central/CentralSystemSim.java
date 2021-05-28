package simulation.central;

import entities.Human;
import entities.HumanBuilder;
import entities.WaterConverter;
import entities.WaterConverterBuilder;
import entities.WaterGenerator;
import entities.tanks.LowQualityWaterTank;
import entities.tanks.SmartWaterTank;
import entities.tanks.WaterTank;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import simulation.framework.Simulation;

public class CentralSystemSim extends Simulation<CentralSystemSim> {

  private static final int HOURS_IN_A_DAY = 24;

  private final int simulationDays;
  private final int population;

  private final Map<Integer, Human> allHumans = new HashMap<>();
  private final WaterTank centralWaterTank;
  private final WaterTank wasteWaterTank;
  private final WaterGenerator productionUnit;
  private final WaterConverter waterRecycler;
  private final Random randomInst;

  public CentralSystemSim(int simulationDays, int population,
      double maximumDailyVolume,
      double volumePerGeneration,
      double converterEfficiency, double volumePerConversion) {

    /* Initialise simulation related fields. */
    this.simulationDays = simulationDays;
    this.population = population;
    this.randomInst = new Random(42);

    /* Initialise tank entities. */
    centralWaterTank = new SmartWaterTank(maximumDailyVolume);
    wasteWaterTank = new LowQualityWaterTank();
    productionUnit = new WaterGenerator(centralWaterTank, volumePerGeneration);
    waterRecycler = new WaterConverterBuilder()
        .withEfficiency(converterEfficiency)
        .withSourceTank(wasteWaterTank)
        .withDestinationTank(centralWaterTank)
        .withVolumePerConversion(volumePerConversion)
        .build();

    /* Create humans. */
    for (int i = 0; i < population; i++) {
      Human human = new HumanBuilder()
          .withPotableWaterTank(centralWaterTank)
          .withWasteWaterTank(wasteWaterTank)
          .build();
      allHumans.put(i, human);
    }
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
    return getCurrentTime() > HOURS_IN_A_DAY * simulationDays;
  }

  @Override
  protected CentralSystemSim getSimulationType() {
    return this;
  }

  @Override
  protected void initSimulation() {

  }
}
