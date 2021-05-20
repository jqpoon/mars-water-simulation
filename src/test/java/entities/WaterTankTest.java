package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WaterTankTest {

  @Test
  public void tankInitiallyHasNoWater() {
    AllQualityWaterTank tank = new AllQualityWaterTank();
    assertEquals(0, tank.getCurrentVolume(WaterQuality.LOW));
    assertEquals(0, tank.getCurrentVolume(WaterQuality.HIGH));
  }

  @Test
  public void canDepositToWaterTank() {
    AllQualityWaterTank tank = new AllQualityWaterTank();
    tank.depositWater(10, WaterQuality.HIGH);
    assertEquals(10, tank.getCurrentVolume(WaterQuality.HIGH));
  }

  @Test
  public void canWithdrawFromWaterTank() {
    AllQualityWaterTank tank = new AllQualityWaterTank();
    tank.depositWater(10, WaterQuality.HIGH);
    assertEquals(10, tank.withdrawWater(10, WaterQuality.HIGH));
    assertEquals(0, tank.getCurrentVolume(WaterQuality.HIGH));
  }

  @Test
  public void cannotWithdrawWhenEmpty() {
    AllQualityWaterTank tank = new AllQualityWaterTank();
    assertEquals(0, tank.withdrawWater(10, WaterQuality.HIGH));
    assertEquals(0, tank.getCurrentVolume(WaterQuality.HIGH));
  }

  @Test
  public void addLowQualityWaterDoesNotAffectHighQualityLevel() {
    AllQualityWaterTank tank = new AllQualityWaterTank();
    tank.depositWater(10, WaterQuality.LOW);
    assertEquals(0, tank.getCurrentVolume(WaterQuality.HIGH));
  }

}
