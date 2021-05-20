package simulation.simple;

import entities.Human;
import entities.HumanBuilder;
import entities.tanks.HighQualityWaterTank;
import entities.tanks.LowQualityWaterTank;
import entities.tanks.WaterTank;
import simulation.framework.Simulation;

/* Simulates human drinking water until water tank is empty. */
public class SimpleSimulation extends Simulation<SimpleSimulation> {

  private final WaterTank potableWater = new HighQualityWaterTank();
  private final WaterTank wasteWater = new LowQualityWaterTank();
  private final Human joe = new HumanBuilder().withPotableWaterTank(potableWater)
      .withWasteWaterTank(wasteWater).build();

  public Human getHuman() {
    return joe;
  }

  @Override
  protected boolean stop() {
    return joe.getHealth() <= 0;
  }

  @Override
  protected SimpleSimulation getSimulationType() {
    return this;
  }

  @Override
  protected void initSimulation() {
    potableWater.depositWater(34);
    schedule(new DrinkWaterEvent(), 0);
  }

  public static void main(String[] args) {
    SimpleSimulation simulation = new SimpleSimulation();
    simulation.initSimulation();
    simulation.simulate();
  }

}
