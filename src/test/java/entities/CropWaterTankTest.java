package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entities.tanks.CropWaterTank;
import entities.tanks.WaterTank;
import org.junit.jupiter.api.Test;

public class CropWaterTankTest {

  @Test
  public void cropWaterTankEfficiencyLimitsWithdrawAmount() {
    WaterTank cropWaterTank = new CropWaterTank(0.5);
    cropWaterTank.depositWater(100);
    assertEquals(50, cropWaterTank.withdrawWater(100));
  }
}
