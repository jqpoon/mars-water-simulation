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
import simulation.framework.Simulation;

/* Simulates a human drinking water and excreting into a waste tank.
 * Also has a generator that generates a trickle of water and a converter
 * to convert low quality water to high quality water. */
public class ComplexSimulation extends Simulation<ComplexSimulation> {

  public static final double LITRES_PRODUCED_FROM_GENERATOR = 45.0;
  public static final double INITIAL_STARTING_VOLUME = 840.0;
  public static final double CONVERTER_EFFICIENCY = 0.935;
  public static final double HUMAN_CONSUMPTION_PER_DAY = 2.5;
  public static final double HUMAN_WASTE_PER_DAY = 2.4;

  private final Random randomInst = new Random(42);
  private final WaterTank potableWaterTank = new HighQualityWaterTank();
  private final WaterTank wasteWaterTank = new LowQualityWaterTank();

  protected Random getRandomInstance() {
    return randomInst;
  }

  /* Water generator that direct deposits generated water into
   * the potableWaterTank. */
  private final WaterGenerator productionUnit = new WaterGenerator(
      potableWaterTank, LITRES_PRODUCED_FROM_GENERATOR);

  /* Water converter converts from wasteWaterTank to potableWaterTank. */
  private final WaterConverter waterRecycler = new WaterConverterBuilder()
      .withEfficiency(CONVERTER_EFFICIENCY)
      .withSourceTank(wasteWaterTank)
      .withDestinationTank(potableWaterTank)
      .withVolumePerConversion(100000) // Assume we can convert everything at once
      .build();

  private final Human human = new HumanBuilder()
      .withPotableWaterTank(potableWaterTank)
      .withWasteWaterTank(wasteWaterTank).build();

  public Human getHuman() {
    return human;
  }

  @Override
  protected boolean stop() {
    return human.getStandardOfLiving() <= 0;
  }

  @Override
  protected ComplexSimulation getSimulationType() {
    return this;
  }

  @Override
  protected void initSimulation() {
    potableWaterTank.depositWater(INITIAL_STARTING_VOLUME);
    for (int i = 0; i < 100; i++) {
      schedule(new DrinkWaterEvent(), i);
    }
  }

  public static void main(String[] args) {
    ComplexSimulation simulation = new ComplexSimulation();
    simulation.initSimulation();
    simulation.simulate();
  }

}
