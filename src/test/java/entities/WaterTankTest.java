package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WaterTankTest {

  @Test
  public void tankInitiallyHasNoWater() {
    SimpleWaterTank tank = new SimpleWaterTank();
    assertEquals(0, tank.getCurrentVolume(WaterQuality.LOW));
    assertEquals(0, tank.getCurrentVolume(WaterQuality.HIGH));
  }

  @Test
  public void canDepositToWaterTank() {
    SimpleWaterTank tank = new SimpleWaterTank();
    tank.depositWater(10, WaterQuality.HIGH);
    assertEquals(10, tank.getCurrentVolume(WaterQuality.HIGH));
  }

  @Test
  public void canWithdrawFromWaterTank() {
    SimpleWaterTank tank = new SimpleWaterTank();
    tank.depositWater(10, WaterQuality.HIGH);
    assertEquals(10, tank.withdrawWater(10, WaterQuality.HIGH));
    assertEquals(0, tank.getCurrentVolume(WaterQuality.HIGH));
  }

  @Test
  public void cannotWithdrawWhenEmpty() {
    SimpleWaterTank tank = new SimpleWaterTank();
    assertEquals(0, tank.withdrawWater(10, WaterQuality.HIGH));
    assertEquals(0, tank.getCurrentVolume(WaterQuality.HIGH));
  }

  @Test
  public void addLowQualityWaterDoesNotAffectHighQualityLevel() {
    SimpleWaterTank tank = new SimpleWaterTank();
    tank.depositWater(10, WaterQuality.LOW);
    assertEquals(0, tank.getCurrentVolume(WaterQuality.HIGH));
  }

}
