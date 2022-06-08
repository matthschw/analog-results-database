package edlab.eda.ardb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.text.translate.CharSequenceTranslator;

import edlab.eda.reader.nutmeg.NutmegComplexPlot;

/**
 * Container consisting of complex waves and values
 *
 */
public final class ComplexResultsDatabase extends ResultsDatabase {

  private Map<String, ComplexValue> values = new HashMap<>();
  private Map<String, ComplexWaveform> waves = new HashMap<>();

  private ComplexResultsDatabase() {
  }

  /**
   * Create a {@link ComplexResultsDatabase}
   *
   * @param value map of values
   * @param waves map of waves
   * @return database
   */
  public static ComplexResultsDatabase create(
      final Map<String, ComplexValue> value,
      final Map<String, ComplexWaveform> waves) {

    final ComplexResultsDatabase retval = new ComplexResultsDatabase();

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
   * @return database
   */
  public static ComplexResultsDatabase create(final String[] valueNames,
      final ComplexValue[] valuesKeys, final String[] waveNames,
      final RealWaveform[] waveKeys) {

    final ComplexResultsDatabase retval = new ComplexResultsDatabase();

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

    final ComplexResultsDatabase retval = new ComplexResultsDatabase();

    retval.values = new HashMap<>();
    retval.waves = new HashMap<>();

    return retval;
  }

  /**
   * Returns a complex value with a given name
   *
   * @param name of value
   * @return Complex Value
   */
  public ComplexValue getComplexValue(final String name) {
    if (this.values != null) {
      return this.values.get(name);
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
  public ComplexValue getComplexValue(
      final ReferenceableElectrical electrical) {
    return this.getComplexValue(electrical.getNetlistIdentifier());
  }

  /**
   * Returns a complex wave with a given name
   *
   * @param name of wave
   * @return Complex Wave
   */
  public ComplexWaveform getComplexWaveform(final String name) {
    if (this.waves != null) {
      return this.waves.get(name);
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
      final ReferenceableElectrical electrical) {
    return this.getComplexWaveform(electrical.getNetlistIdentifier());
  }

  @Override
  public Set<String> getValueNames() {
    if (this.values != null) {
      return this.values.keySet();
    } else {
      System.err.println("Database does not contain waveforms, only values");
      return new HashSet<>();
    }
  }

  @Override
  public String[] getValueNamesAsArray() {
    return ResultsDatabase.convert(this.getValueNames());
  }

  @Override
  public Set<String> getWaveNames() {
    if (this.waves != null) {
      return this.waves.keySet();
    } else {
      System.err.println("Database does not contain waveforms, only values");
      return new HashSet<>();
    }
  }

  @Override
  public String[] getWaveNamesAsArray() {
    return ResultsDatabase.convert(this.getWaveNames());
  }

  /**
   * Builds a {@link ComplexResultsDatabase} from a {@link NutmegComplexPlot}
   *
   * @param plot {@link NutmegComplexPlot}
   * @return ComplexResultsDatabase
   */
  public static ComplexResultsDatabase buildResultDatabase(
      final NutmegComplexPlot plot) {

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
      final NutmegComplexPlot plot, final CharSequenceTranslator translator) {

    final ComplexResultsDatabase retval = new ComplexResultsDatabase();

    if (plot.getNoOfPoints() == 1) {

      retval.values = new HashMap<>();

      for (final String wave : plot.getWaves()) {

        retval.values.put(translator.translate(wave), new ComplexValue(
            plot.getWave(wave)[0], translator.translate(plot.getUnit(wave))));
      }
    } else {

      final String refWave = plot.getRefWave();
      final String refWaveUnit = plot.getUnit(refWave);

      retval.waves = new HashMap<>();

      final Complex[] xComplex = plot.getWave(refWave);

      final double[] x = new double[xComplex.length];

      for (int i = 0; i < xComplex.length; i++) {
        x[i] = xComplex[i].getReal();
      }

      for (final String wave : plot.getWaves()) {

        if (!wave.equals(refWave)) {

          retval.waves.put(translator.translate(wave),
              ComplexWaveform.buildComplexWaveform(x, plot.getWave(wave),
                  translator.translate(refWaveUnit),
                  translator.translate(plot.getUnit(wave))));
        }
      }
    }
    return retval;
  }

  @Override
  public String toString() {

    final StringBuilder retval = new StringBuilder();

    boolean firstIteration = true;

    if ((this.values.keySet() != null) && !this.values.isEmpty()) {

      retval.append("Values:");

      firstIteration = false;

      for (final String name : this.values.keySet()) {

        retval.append("\n- ").append(name).append(" = ")
            .append(this.values.get(name));
      }
    }

    if ((this.waves.keySet() != null) && !this.waves.isEmpty()) {

      if (!firstIteration) {
        retval.append("\n");
      }

      retval.append("Waves:");

      firstIteration = false;

      for (final String name : this.waves.keySet()) {

        retval.append("\n- ").append(name).append(" X=")
            .append(this.waves.get(name).getUnitX()).append("/").append(" X=")
            .append(this.waves.get(name).getUnitY());
      }
    }

    return retval.toString();
  }

  @Override
  public boolean isValueName(final String name) {
    return this.values.containsKey(name);
  }

  @Override
  public boolean isWaveformName(final String name) {
    return this.waves.containsKey(name);
  }

  @Override
  public boolean isValue(final ReferenceableElectrical electrical) {
    return this.isValueName(electrical.getNetlistIdentifier());
  }

  @Override
  public boolean isWaveform(final ReferenceableElectrical electrical) {
    return this.isWaveformName(electrical.getNetlistIdentifier());
  }

  @Override
  public boolean isMember(final String name) {
    return ((this.values != null) && this.values.containsKey(name))
        || ((this.waves != null) && this.waves.containsKey(name));
  }

  @Override
  public boolean isMember(final ReferenceableElectrical electrical) {
    return ((this.values != null)
        && this.values.containsKey(electrical.getNetlistIdentifier()))
        || ((this.waves != null)
            && this.waves.containsKey(electrical.getNetlistIdentifier()));
  }

  @Override
  public Value getValue(final String name) {
    return this.values.get(name);
  }

  @Override
  public Value getValue(final ReferenceableElectrical electrical) {
    return this.values.get(electrical.getNetlistIdentifier());
  }

  @Override
  public Waveform getWaveform(final String name) {
    return this.waves.get(name);
  }

  @Override
  public Waveform getWaveform(final ReferenceableElectrical electrical) {
    return this.waves.get(electrical.getNetlistIdentifier());
  }

  @Override
  public Object get(final String name) {

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
  public Object get(final ReferenceableElectrical electrical) {

    if (electrical == null) {
      return null;
    }

    if (this.values.containsKey(electrical.getNetlistIdentifier())) {
      return this.values.get(electrical.getNetlistIdentifier());
    }

    if (this.waves.containsKey(electrical.getNetlistIdentifier())) {
      return this.waves.get(electrical.getNetlistIdentifier());
    }

    return null;
  }

  @Override
  public boolean isEmpty() {
    return this.values.isEmpty() && this.waves.isEmpty();
  }

  /**
   * Identify whether an object is an instance of this class
   *
   * @param o Object to be checked
   * @return <code>true</code> when the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(final Object o) {
    return o instanceof ComplexResultsDatabase;
  }
}