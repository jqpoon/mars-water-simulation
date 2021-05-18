import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.jupiter.api.Test;

import org.junit.Rule;

public class HumanTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  WaterTank potableWaterTank = context.mock(WaterTank.class, "potableWater");
  WaterTank wasteWaterTank = context.mock(WaterTank.class, "wasteWater");
  Human human = new Human(potableWaterTank, wasteWaterTank);

  @Test
  public void humanDrinksWaterFromPotableWater() {
    context.checking(new Expectations() {{
      oneOf(potableWaterTank).withdrawWater(5);
    }});

    human.drink(5);
  }

  @Test
  public void humanHealthGoesDownIfNotEnoughWaterDrank() {
    context.checking(new Expectations() {{
      allowing(potableWaterTank).withdrawWater(with(any(Double.class)));
      will(returnValue((double) 0));
    }});

    human.drink(5);

    assertThat((int) human.getHealth(), is(lessThan((int) Human.INITIAL_HEALTH)));
  }

  @Test
  public void humanCanReleaseWasteWater() {
    context.checking(new Expectations() {{
      oneOf(wasteWaterTank).depositWater(1);
    }});

    human.excreteWaste(1);
  }

}
