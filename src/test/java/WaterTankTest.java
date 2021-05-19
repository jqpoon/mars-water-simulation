import static org.junit.jupiter.api.Assertions.assertEquals;

import entities.SimpleWaterTank;
import org.junit.jupiter.api.Test;

public class WaterTankTest {

  @Test
  public void canDepositToWaterTank() {
    SimpleWaterTank tank = new SimpleWaterTank();
    tank.depositWater(10);
    assertEquals(10, tank.getCurrentVolume());
  }

  @Test
  public void canWithdrawFromWaterTank() {
    SimpleWaterTank tank = new SimpleWaterTank();
    tank.depositWater(10);
    assertEquals(10, tank.withdrawWater(10));
    assertEquals(0, tank.getCurrentVolume());
  }

  @Test
  public void cannotWithdrawWhenEmpty() {
    SimpleWaterTank tank = new SimpleWaterTank();
    assertEquals(0, tank.withdrawWater(10));
    assertEquals(0, tank.getCurrentVolume());
  }


}
