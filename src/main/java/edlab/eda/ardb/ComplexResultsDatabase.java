package edlab.eda.ardb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.complex.Complex;

import edlab.eda.reader.nutmeg.NutmegComplexPlot;

public class ComplexResultsDatabase extends ResultsDatabse{

  private Map<String, ComplexValue> values = null;
  private Map<String, ComplexWaveform> waves = null;

  private ComplexResultsDatabase() {

  }

  public ComplexValue getRealValue(String name) {
    if (values != null) {
      return values.get(name);
    } else {
      System.err.println("Database does not contain values, only waveforms");
      return null;
    }
  }

  public ComplexWaveform getRealWaveform(String name) {
    if (waves != null) {
      return waves.get(name);
    } else {
      System.err.println("Database does not contain waveforms, only values");
      return null;
    }
  }

  public Set<String> getValueNames() {
    if (waves != null) {
      return values.keySet();
    } else {
      System.err.println("Database does not contain waveforms, only values");
      return new HashSet<String>();
    }
  }

  public Set<String> getWaveNames() {
    if (waves != null) {
      return waves.keySet();
    } else {
      System.err.println("Database does not contain waveforms, only values");
      return new HashSet<String>();
    }
  }

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
          retval.waves.put(wave, new ComplexWaveform(x, plot.getWave(wave),
              refWaveUnit, plot.getUnit(wave)));
        }
      }
    }
    return null;
  }

}
