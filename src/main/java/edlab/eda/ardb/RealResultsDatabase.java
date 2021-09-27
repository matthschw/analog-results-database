package edlab.eda.ardb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edlab.eda.reader.nutmeg.NutmegRealPlot;

/**
 * Container consisting of real waves and values
 */
public class RealResultsDatabase extends ResultsDatabse {

  private Map<String, RealValue> values = null;
  private Map<String, RealWaveform> waves = null;

  private RealResultsDatabase() {
  }

  /**
   * Returns a real value for a given electrical reference
   * 
   * @param name of value
   * @return Real Value
   */
  public RealValue getRealValue(String name) {
    if (values != null) {
      return values.get(name);
    } else {
      System.err.println("Database does not contain values, only waveforms");
      return null;
    }
  }

  /**
   * Returns a real value with a given name
   * 
   * @param electrical Reference
   * @return Reference
   */
  public RealValue getRealValue(ReferenceableElectrical electrical) {
    return this.getRealValue(electrical.getIdentifier());
  }

  /**
   * Returns a real wave with a given name
   * 
   * @param name of wave
   * @return Real Wave
   */
  public RealWaveform getRealWaveform(String name) {
    if (waves != null) {
      return waves.get(name);
    } else {
      System.err.println("Database does not contain waveforms, only values");
      return null;
    }
  }

  /**
   * Returns a real wave for a given electrical reference
   * 
   * @param electrical Reference
   * @return Real Wave
   */
  public RealWaveform getRealWaveform(ReferenceableElectrical electrical) {
    return this.getRealWaveform(electrical.getIdentifier());
  }

  @Override
  public Set<String> getValueNames() {
    if (values != null) {
      return values.keySet();
    } else {
      System.err.println("Database does not contain waveforms, only values");
      return new HashSet<String>();
    }
  }

  @Override
  public Set<String> getWaveNames() {
    if (waves != null) {
      return waves.keySet();
    } else {
      System.err.println("Database does not contain waveforms, only values");
      return new HashSet<String>();
    }
  }

  /**
   * Builds a {@link RealResultsDatabase} from a {@link NutmegRealPlot}
   * 
   * @param plot {@link NutmegRealPlot}
   * @return ComplexResultsDatabase
   */
  public static RealResultsDatabase buildResultDatabase(NutmegRealPlot plot) {

    RealResultsDatabase retval = new RealResultsDatabase();

    if (plot.getNoOfPoints() == 1) {

      retval.values = new HashMap<String, RealValue>();

      for (String wave : plot.getWaves()) {

        retval.values.put(wave,
            new RealValue(plot.getWave(wave)[0], plot.getUnit(wave)));
      }
    } else {

      String refWave = plot.getRefWave();
      String refWaveUnit = plot.getUnit(refWave);

      retval.waves = new HashMap<String, RealWaveform>();

      double x[] = plot.getWave(refWave);

      for (String wave : plot.getWaves()) {

        RealWaveform.buildRealWaveform(x, plot.getWave(wave), refWaveUnit,
            plot.getUnit(wave));

        if (!wave.equals(refWave)) {
          retval.waves.put(wave, RealWaveform.buildRealWaveform(x,
              plot.getWave(wave), refWaveUnit, plot.getUnit(wave)));
        }
      }
    }
    return retval;
  }

  @Override
  public String toString() {

    String retval = "";

    boolean firstIteration = true;

    if (this.values.keySet() != null && !this.values.isEmpty()) {

      retval += "Values:";

      firstIteration = false;

      for (String name : this.values.keySet()) {

        retval += "\n- " + name + " = " + this.values.get(name) + " "
            + this.values.get(name).getUnit();
      }
    }

    if (this.waves.keySet() != null && !this.waves.isEmpty()) {

      if (!firstIteration) {
        retval += "\n";
      }

      retval += "Waves:";

      firstIteration = false;

      for (String name : this.waves.keySet()) {

        retval += "\n- " + name + " X=" + this.waves.get(name).getUnitX() + "/"
            + " X=" + this.waves.get(name).getUnitY();
      }
    }

    return retval;
  }
}