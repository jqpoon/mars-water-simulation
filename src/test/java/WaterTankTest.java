import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WaterTankTest {

  @Test
  public void canWithdrawFromWaterTank() {
    WaterTank tank = new WaterTank();
    tank.depositWater(10);
    assertEquals(tank.withdrawWater(5), 5);
  }
}
