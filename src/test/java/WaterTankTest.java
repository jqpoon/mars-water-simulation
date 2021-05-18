import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WaterTankTest {

  @Test
  public void canDepositToWaterTank() {
    WaterTank tank = new WaterTank();
    tank.depositWater(10);
    assertEquals(10, tank.getCurrentVolume());
  }

  @Test
  public void canWithdrawFromWaterTank() {
    WaterTank tank = new WaterTank();
    tank.depositWater(10);
    assertEquals(10, tank.withdrawWater(10));
    assertEquals(0, tank.getCurrentVolume());
  }

  @Test
  public void cannotWithdrawWhenEmpty() {
    WaterTank tank = new WaterTank();
    assertEquals(0, tank.withdrawWater(10));
    assertEquals(0, tank.getCurrentVolume());
  }


}
