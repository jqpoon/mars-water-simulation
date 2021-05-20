package entities;

import entities.tanks.WaterTank;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

public class WaterConverterTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  WaterTank sourceTank = context.mock(WaterTank.class, "sourceTank");
  WaterTank destinationTank = context.mock(WaterTank.class, "destinationTank");
  WaterConverter efficientConverter = new WaterConverter(3, 1
      , sourceTank, destinationTank);

  @Test
  public void converterCanConvertLowToHighQualityWater() {

    context.checking(new Expectations() {{
      oneOf(sourceTank).withdrawWater(3);
      will(returnValue((double) 3)); // Returns actual volume of water withdrawn
      oneOf(destinationTank).depositWater(3);
    }});

    efficientConverter.convert();
  }

  @Test
  public void conversionLimitedByCurrentWaterLevels() {
    context.checking(new Expectations() {{
      oneOf(sourceTank).withdrawWater(3);
      will(returnValue((double) 1)); // Returns actual volume of water withdrawn
      oneOf(destinationTank).depositWater(1);
    }});

    efficientConverter.convert();
  }

  @Test
  public void converterAdheresToEfficiency() {
    WaterConverter notEfficientConverter = new WaterConverter(3, 0.5
        , sourceTank, destinationTank);

    context.checking(new Expectations() {{
      oneOf(sourceTank).withdrawWater(3);
      will(returnValue((double) 3)); // Returns actual volume of water withdrawn
      oneOf(destinationTank).depositWater(1.5);
    }});

    notEfficientConverter.convert();
  }

}
