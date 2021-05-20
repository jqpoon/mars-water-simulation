package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entities.tanks.AbstractWaterTank;
import entities.tanks.HighQualityWaterTank;
import org.junit.jupiter.api.Test;

public class WaterTankTest {

  @Test
  public void tankInitiallyHasNoWater() {
    AbstractWaterTank tank = new HighQualityWaterTank();
    assertEquals(0, tank.getCurrentVolume());
    assertEquals(0, tank.getCurrentVolume());
  }

  @Test
  public void canDepositToWaterTank() {
    AbstractWaterTank tank = new HighQualityWaterTank();
    tank.depositWater(10);
    assertEquals(10, tank.getCurrentVolume());
  }

  @Test
  public void canWithdrawFromWaterTank() {
    AbstractWaterTank tank = new HighQualityWaterTank();
    tank.depositWater(10);
    assertEquals(10, tank.withdrawWater(10));
    assertEquals(0, tank.getCurrentVolume());
  }

  @Test
  public void cannotWithdrawWhenEmpty() {
    AbstractWaterTank tank = new HighQualityWaterTank();
    assertEquals(0, tank.withdrawWater(10));
    assertEquals(0, tank.getCurrentVolume());
  }
}
