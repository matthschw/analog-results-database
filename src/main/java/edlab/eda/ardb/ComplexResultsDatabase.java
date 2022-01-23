package edlab.eda.ardb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.math3.complex.Complex;

import edlab.eda.reader.nutmeg.DefaultTranslator;
import edlab.eda.reader.nutmeg.NutmegComplexPlot;

/**
 * Container consisting of complex waves and values
 *
 */
public class ComplexResultsDatabase extends ResultsDatabase {

  private Map<String, ComplexValue> values = new HashMap<String, ComplexValue>();
  private Map<String, ComplexWaveform> waves = new HashMap<String, ComplexWaveform>();

  private ComplexResultsDatabase() {
  }

  /**
   * Create a {@link ComplexResultsDatabase}
   * 
   * @param value map of values
   * @param waves map of waves
   * @return database
   */
  public static ComplexResultsDatabase create(Map<String, ComplexValue> value,
      Map<String, ComplexWaveform> waves) {

    ComplexResultsDatabase retval = new ComplexResultsDatabase();

    retval.values = value;
    retval.waves = waves;

    return retval;
  }

  /**
   * Create a {@link ComplexResultsDatabase}
   * 
   * @param valueNames array of value names
   * @param valuesKeys array of value keys
   * @param waveNames  array of wave names
   * @param waveKeys   array of wave keys
   * @return
   */
  public static ComplexResultsDatabase create(String[] valueNames,
      ComplexValue[] valuesKeys, String[] waveNames, RealWaveform[] waveKeys) {

    ComplexResultsDatabase retval = new ComplexResultsDatabase();

    for (int i = 0; i < Math.min(valueNames.length, valuesKeys.length); i++) {
      retval.values.put(valueNames[i], valuesKeys[i]);
    }

    return retval;
  }

  /**
   * Create an empty {@link ComplexResultsDatabase}
   * 
   * @return database
   */
  public static ComplexResultsDatabase create() {

    ComplexResultsDatabase retval = new ComplexResultsDatabase();

    retval.values = new HashMap<String, ComplexValue>();
    retval.waves = new HashMap<String, ComplexWaveform>();

    return retval;
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

    return buildResultDatabase(plot, new DefaultTranslator());
  }

  /**
   * Builds a {@link ComplexResultsDatabase} from a {@link NutmegComplexPlot}
   * 
   * @param plot       {@link NutmegComplexPlot}
   * @param translator Translator for wave names
   * @return ComplexResultsDatabase
   */
  public static ComplexResultsDatabase buildResultDatabase(
      NutmegComplexPlot plot, CharSequenceTranslator translator) {

    ComplexResultsDatabase retval = new ComplexResultsDatabase();

    if (plot.getNoOfPoints() == 1) {

      retval.values = new HashMap<String, ComplexValue>();

      for (String wave : plot.getWaves()) {

        retval.values.put(translator.translate(wave), new ComplexValue(
            plot.getWave(wave)[0], translator.translate(plot.getUnit(wave))));
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

          retval.waves.put(translator.translate(wave),
              ComplexWaveform.buildRealWaveform(x, plot.getWave(wave),
                  translator.translate(refWaveUnit),
                  translator.translate(plot.getUnit(wave))));
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

        retval += "\n- " + name + " = " + this.values.get(name);
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

  @Override
  public boolean isValueName(String name) {
    return this.values.containsKey(name);
  }

  @Override
  public boolean isWaveformName(String name) {
    return this.waves.containsKey(name);
  }

  @Override
  public boolean isValue(ReferenceableElectrical electrical) {
    return this.isValueName(electrical.getIdentifier());
  }

  @Override
  public boolean isWaveform(ReferenceableElectrical electrical) {
    return this.isWaveformName(electrical.getIdentifier());
  }

  @Override
  public boolean isMember(String name) {
    return (this.values != null && this.values.containsKey(name))
        || (this.waves != null && this.waves.containsKey(name));
  }

  @Override
  public boolean isMember(ReferenceableElectrical electrical) {
    return (this.values != null
        && this.values.containsKey(electrical.getIdentifier()))
        || (this.waves != null
            && this.waves.containsKey(electrical.getIdentifier()));
  }

  @Override
  public Value getValue(String name) {
    return this.values.get(name);
  }

  @Override
  public Value getValue(ReferenceableElectrical electrical) {
    return this.values.get(electrical.getIdentifier());
  }

  @Override
  public Waveform getWaveform(String name) {
    return this.waves.get(name);
  }

  @Override
  public Waveform getWaveform(ReferenceableElectrical electrical) {
    return this.waves.get(electrical.getIdentifier());
  }

  /**
   * Identify whether an object is an instance of this class
   * 
   * @param o Object to be checked
   * @return <code>true</code> when the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(Object o) {
    return o instanceof ComplexResultsDatabase;
  }

  @Override
  public Object get(String name) {

    if (name == null) {
      return null;
    }

    if (this.values.containsKey(name)) {
      return this.values.get(name);
    }

    if (this.waves.containsKey(name)) {
      return this.waves.get(name);
    }

    return null;
  }

  @Override
  public Object get(ReferenceableElectrical electrical) {

    if (electrical == null) {
      return null;
    }

    if (this.values.containsKey(electrical.getIdentifier())) {
      return this.values.get(electrical.getIdentifier());
    }

    if (this.waves.containsKey(electrical.getIdentifier())) {
      return this.waves.get(electrical.getIdentifier());
    }

    return null;
  }
}