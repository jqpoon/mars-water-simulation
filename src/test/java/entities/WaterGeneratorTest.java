package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WaterGeneratorTest {

  @Test
  public void generatorCanGenerateWater() {
    WaterGenerator generator = new WaterGenerator(WaterQuality.HIGH, 5);
    assertEquals(0, generator.getCurrentVolume(WaterQuality.HIGH));
    generator.generate();
    assertEquals(5, generator.getCurrentVolume(WaterQuality.HIGH));
  }

  @Test
  public void generatorCanGenerateMultipleTimes() {
    WaterGenerator generator = new WaterGenerator(WaterQuality.HIGH, 5);
    generator.generate();
    generator.generate();
    generator.generate();
    assertEquals(15, generator.getCurrentVolume(WaterQuality.HIGH));
  }

}
