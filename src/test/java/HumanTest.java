import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.jupiter.api.Test;

import org.junit.Rule;

public class HumanTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  WaterTank potableWaterTank = context.mock(WaterTank.class);
  Human human = new Human(potableWaterTank);

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

}
