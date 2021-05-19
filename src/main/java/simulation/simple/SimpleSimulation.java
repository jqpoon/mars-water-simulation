package simulation.simple;

import entities.Human;
import entities.SimpleWaterTank;
import entities.WaterTank;
import simulation.framework.Simulation;

/* Simulates human drinking water until water tank is empty. */
public class SimpleSimulation extends Simulation<SimpleSimulation> {

  private final WaterTank potableWater = new SimpleWaterTank();
  private final WaterTank wasteWater = new SimpleWaterTank();
  private final Human joe = new Human(potableWater, wasteWater);

  public SimpleSimulation() {
    potableWater.depositWater(34);
  }

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

  public static void main(String[] args) {
    SimpleSimulation simulation = new SimpleSimulation();
    simulation.schedule(new DrinkWaterEvent(), 0);
    simulation.simulate();
  }

}
