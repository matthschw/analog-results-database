package edlab.eda.ardb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.complex.Complex;

import edlab.eda.reader.nutmeg.NutmegComplexPlot;

/**
 * Container consisting of complex waves and values
 *
 */
public class ComplexResultsDatabase extends ResultsDatabse {

  private Map<String, ComplexValue> values = null;
  private Map<String, ComplexWaveform> waves = null;

  private ComplexResultsDatabase() {
  }

  /**
   * Returns a complex value with a given name
   * 
   * @param name of value
   * @return Complex Value
   */
  public ComplexValue getComplexValue(String name) {
    if (values != null) {
      return values.get(name);
    } else {
      System.err.println("Database does not contain values, only waveforms");
      return null;
    }
  }

  /**
   * Returns a complex value for a given electrical reference
   * 
   * @param electrical Reference
   * @return Complex Value
   */
  public ComplexValue getComplexValue(ReferenceableElectrical electrical) {
    return this.getComplexValue(electrical.getIdentifier());
  }

  /**
   * Returns a complex wave with a given name
   * 
   * @param name of wave
   * @return Complex Wave
   */
  public ComplexWaveform getComplexWaveform(String name) {
    if (waves != null) {
      return waves.get(name);
    } else {
      System.err.println("Database does not contain waveforms, only values");
      return null;
    }
  }

  /**
   * Returns a complex value for a given electrical reference
   * 
   * @param electrical Reference
   * @return Complex Wave
   */
  public ComplexWaveform getComplexWaveform(
      ReferenceableElectrical electrical) {
    return this.getComplexWaveform(electrical.getIdentifier());
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
   * Builds a {@link ComplexResultsDatabase} from a {@link NutmegComplexPlot}
   * 
   * @param plot {@link NutmegComplexPlot}
   * @return ComplexResultsDatabase
   */
  public static ComplexResultsDatabase buildResultDatabase(
      NutmegComplexPlot plot) {

    ComplexResultsDatabase retval = new ComplexResultsDatabase();

    if (plot.getNoOfPoints() == 1) {

      retval.values = new HashMap<String, ComplexValue>();

      for (String wave : plot.getWaves()) {
        retval.values.put(wave,
            new ComplexValue(plot.getWave(wave)[0], plot.getUnit(wave)));
      }
    } else {

      String refWave = plot.getRefWave();
      String refWaveUnit = plot.getUnit(refWave);

      retval.waves = new HashMap<String, ComplexWaveform>();

      Complex[] xComplex = plot.getWave(refWave);

      double[] x = new double[xComplex.length];

      for (int i = 0; i < xComplex.length; i++) {
        x[i] = xComplex[i].getReal();
      }

      for (String wave : plot.getWaves()) {
        if (!wave.equals(refWave)) {

          retval.waves.put(wave, ComplexWaveform.buildRealWaveform(x,
              plot.getWave(wave), refWaveUnit, plot.getUnit(wave)));
        }
      }
    }
    return retval;
  }
}