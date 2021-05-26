package simulation.complex;

import entities.Human;
import entities.HumanBuilder;
import entities.WaterConverter;
import entities.WaterConverterBuilder;
import entities.WaterGenerator;
import entities.tanks.HighQualityWaterTank;
import entities.tanks.LowQualityWaterTank;
import entities.tanks.WaterTank;
import java.util.Random;
import simulation.complex.events.timed.DailyEvent;
import simulation.complex.events.timed.HourlyEvent;
import simulation.framework.Simulation;

/* Simulates a human drinking water and excreting into a waste tank.
 * Also has a generator that generates a trickle of water and a converter
 * to convert low quality water to high quality water. */
public class ConvertAndGenerateSim extends Simulation<ConvertAndGenerateSim> {

  public static final double LITRES_PRODUCED_FROM_GENERATOR = 30.0;
  public static final double INITIAL_STARTING_VOLUME = 50.0;
  public static final double CONVERTER_EFFICIENCY = 0.2;
  public static final double HUMAN_CONSUMPTION_PER_DAY = 2.5;
  public static final double HUMAN_WASTE_PER_DAY = 2.4;
  public static final double VOLUME_PER_CONVERSION = 5.0;
  public static final int HOURS_BETWEEN_RECYCLE_EVENT = 5;

  private final Random randomInst = new Random(42);
  private final WaterTank potableWaterTank = new HighQualityWaterTank();
  private final WaterTank wasteWaterTank = new LowQualityWaterTank();

  /* Water generator that direct deposits generated water into
   * the potableWaterTank. */
  private final WaterGenerator productionUnit = new WaterGenerator(
      potableWaterTank, LITRES_PRODUCED_FROM_GENERATOR);

  /* Water converter converts from wasteWaterTank to potableWaterTank. */
  private final WaterConverter waterRecycler = new WaterConverterBuilder()
      .withEfficiency(CONVERTER_EFFICIENCY)
      .withSourceTank(wasteWaterTank)
      .withDestinationTank(potableWaterTank)
      .withVolumePerConversion(VOLUME_PER_CONVERSION)
      .build();

  private final Human human = new HumanBuilder()
      .withPotableWaterTank(potableWaterTank)
      .withWasteWaterTank(wasteWaterTank).build();

  public Random getRandomInstance() {
    return randomInst;
  }
  public Human getHuman() {
    return human;
  }

  public WaterConverter getWaterRecycler() {
    return waterRecycler;
  }

  public WaterGenerator getProductionUnit() {
    return productionUnit;
  }

  @Override
  protected boolean stop() {
    return human.getStandardOfLiving() <= 0;
  }

  @Override
  protected ConvertAndGenerateSim getSimulationType() {
    return this;
  }

  @Override
  protected void initSimulation() {
    potableWaterTank.depositWater(INITIAL_STARTING_VOLUME);
    for (int i = 0; i < 2; i++) {
      schedule(new DailyEvent(), i * 24);
    }
  }

  private void printStatistics() {
    System.out.println("\n--------- END OF SIMULATION ---------");
    System.out.printf("Simulation lasted for %.4f hours. %n", getCurrentTime());
    System.out.printf("Water left in potable tank: %.4fL%n", potableWaterTank.getCurrentVolume());
    System.out.printf("Water in waste tank: %.4fL%n", wasteWaterTank.getCurrentVolume());
  }

  public static void main(String[] args) {
    ConvertAndGenerateSim simulation = new ConvertAndGenerateSim();
    simulation.initSimulation();
    simulation.simulate();
    simulation.printStatistics();
  }

}
