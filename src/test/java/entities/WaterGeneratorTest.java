package entities;

import static entities.WaterGenerator.HighQualityWaterGenerator;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WaterGeneratorTest {

  @Test
  public void generatorCanGenerateWater() {
    WaterGenerator generator = HighQualityWaterGenerator(5);
    assertEquals(0, generator.getCurrentVolume());
    generator.generate();
    assertEquals(5, generator.getCurrentVolume());
  }

  @Test
  public void generatorCanGenerateMultipleTimes() {
    WaterGenerator generator = HighQualityWaterGenerator(5);
    generator.generate();
    generator.generate();
    generator.generate();
    assertEquals(15, generator.getCurrentVolume());
  }

}
