package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entities.tanks.SmartWaterTank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SmartWaterTankTest {

  private static final double MAX_DAILY_VOLUME = 50;
  SmartWaterTank waterTank;

  @BeforeEach
  public void setup() {
    waterTank = new SmartWaterTank(MAX_DAILY_VOLUME);
    waterTank.addUseCase(WaterUseCase.DRINK, 0.4);
    waterTank.addUseCase(WaterUseCase.CROP, 0.1);
    waterTank.addUseCase(WaterUseCase.HYGIENE, 0.1);
    waterTank.addUseCase(WaterUseCase.FLUSH, 0.1);
    waterTank.addUseCase(WaterUseCase.MEDICAL, 0.1);
    waterTank.addUseCase(WaterUseCase.LAUNDRY, 0.1);
    waterTank.addUseCase(WaterUseCase.ELECTROLYSIS, 0.1);
    waterTank.depositWater(MAX_DAILY_VOLUME);
  }

  @Test
  public void waterTankCanOnlyWithdrawUpToLimit() {
    double maximumDrink = MAX_DAILY_VOLUME * 0.4;
    waterTank.withdrawWaterWithReason(maximumDrink, WaterUseCase.DRINK);
    assertEquals(0, waterTank.withdrawWaterWithReason(5, WaterUseCase.DRINK));
  }

  @Test
  public void canContinueWithdrawingAfterReset() {
    double maximumDrink = MAX_DAILY_VOLUME * 0.4;
    waterTank.withdrawWaterWithReason(maximumDrink, WaterUseCase.DRINK);
    waterTank.withdrawWaterWithReason(maximumDrink, WaterUseCase.DRINK);
    waterTank.resetLimit();
    assertEquals(5, waterTank.withdrawWaterWithReason(5, WaterUseCase.DRINK));
  }

}
