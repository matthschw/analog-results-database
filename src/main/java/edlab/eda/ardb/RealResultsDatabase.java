package edlab.eda.ardb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.text.translate.CharSequenceTranslator;

import edlab.eda.reader.nutmeg.DefaultTranslator;
import edlab.eda.reader.nutmeg.NutmegRealPlot;

/**
 * Container consisting of real waves and values
 */
public final class RealResultsDatabase extends ResultsDatabase {

  private Map<String, RealValue> values = new HashMap<>();
  private Map<String, RealWaveform> waves = new HashMap<>();

  private RealResultsDatabase() {
  }

  /**
   * Create a {@link RealResultsDatabase}
   *
   * @param value map of values
   * @param waves map of waves
   * @return database
   */
  public static RealResultsDatabase create(final Map<String, RealValue> value,
      final Map<String, RealWaveform> waves) {

    final RealResultsDatabase retval = new RealResultsDatabase();

    retval.values = value;
    retval.waves = waves;

    return retval;
  }

  /**
   * Create a {@link RealResultsDatabase}
   *
   * @param valueNames array of value names
   * @param valuesKeys array of value keys
   * @param waveNames  array of wave names
   * @param waveKeys   array of wave keys
   * @return database
   */
  public static RealResultsDatabase create(final String[] valueNames,
      final RealValue[] valuesKeys, final String[] waveNames, final RealWaveform[] waveKeys) {

    final RealResultsDatabase retval = new RealResultsDatabase();

    for (int i = 0; i < Math.min(valueNames.length, valuesKeys.length); i++) {
      retval.values.put(valueNames[i], valuesKeys[i]);
    }

    return retval;
  }

  /**
   * Create an empty {@link RealResultsDatabase}
   *
   * @return database
   */
  public static RealResultsDatabase create() {

    final RealResultsDatabase retval = new RealResultsDatabase();

    retval.values = new HashMap<>();
    retval.waves = new HashMap<>();

    return retval;
  }

  /**
   * Returns a real value for a given electrical reference
   *
   * @param name of value
   * @return Real Value
   */
  public RealValue getRealValue(final String name) {
    if (this.values != null) {
      return this.values.get(name);
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
  public RealValue getRealValue(final ReferenceableElectrical electrical) {
    return this.getRealValue(electrical.getIdentifier());
  }

  /**
   * Returns a real wave with a given name
   *
   * @param name of wave
   * @return Real Wave
   */
  public RealWaveform getRealWaveform(final String name) {
    if (this.waves != null) {
      return this.waves.get(name);
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
  public RealWaveform getRealWaveform(final ReferenceableElectrical electrical) {
    return this.getRealWaveform(electrical.getIdentifier());
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
   * Builds a {@link RealResultsDatabase} from a {@link NutmegRealPlot}
   *
   * @param plot {@link NutmegRealPlot}
   * @return ComplexResultsDatabase
   */
  public static RealResultsDatabase buildResultDatabase(final NutmegRealPlot plot) {
    return buildResultDatabase(plot, new DefaultTranslator());
  }

  /**
   * Builds a {@link RealResultsDatabase} from a {@link NutmegRealPlot}
   *
   * @param plot       {@link NutmegRealPlot}
   * @param translator Translator for wave names
   * @return ComplexResultsDatabase
   */
  public static RealResultsDatabase buildResultDatabase(final NutmegRealPlot plot,
      final CharSequenceTranslator translator) {

    final RealResultsDatabase retval = new RealResultsDatabase();

    if (plot.getNoOfPoints() == 1) {

      retval.values = new HashMap<>();

      for (final String wave : plot.getWaves()) {

        retval.values.put(translator.translate(wave),
            new RealValue(plot.getWave(wave)[0], plot.getUnit(wave)));
      }
    } else {

      final String refWave = plot.getRefWave();
      final String refWaveUnit = plot.getUnit(refWave);

      retval.waves = new HashMap<>();

      final double x[] = plot.getWave(refWave);

      for (final String wave : plot.getWaves()) {

        RealWaveform.buildRealWaveform(x, plot.getWave(wave),
            translator.translate(refWaveUnit),
            translator.translate(plot.getUnit(wave)));

        if (!wave.equals(refWave)) {
          retval.waves.put(translator.translate(wave),
              RealWaveform.buildRealWaveform(x, plot.getWave(wave),
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

    if ((this.values != null) && !this.values.isEmpty()) {

      retval += "Values:";

      firstIteration = false;

      for (final String name : this.values.keySet()) {

        retval += "\n- " + name + " = " + this.values.get(name);
      }
    }

    if ((this.waves != null) && !this.waves.isEmpty()) {

      if (!firstIteration) {
        retval += "\n";
      }

      retval += "Waves:";

      firstIteration = false;

      for (final String name : this.waves.keySet()) {

        retval += "\n- " + name + " " + this.waves.get(name).getUnitY();
      }
    }

    return retval;
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
    return this.isValueName(electrical.getIdentifier());
  }

  @Override
  public boolean isWaveform(final ReferenceableElectrical electrical) {
    return this.isWaveformName(electrical.getIdentifier());
  }

  @Override
  public boolean isMember(final String name) {
    return ((this.values != null) && this.values.containsKey(name))
        || ((this.waves != null) && this.waves.containsKey(name));
  }

  @Override
  public boolean isMember(final ReferenceableElectrical electrical) {
    return ((this.values != null)
        && this.values.containsKey(electrical.getIdentifier()))
        || ((this.waves != null)
            && this.waves.containsKey(electrical.getIdentifier()));
  }

  @Override
  public Value getValue(final String name) {
    return this.values.get(name);
  }

  @Override
  public Value getValue(final ReferenceableElectrical electrical) {
    return this.values.get(electrical.getIdentifier());
  }

  @Override
  public Waveform getWaveform(final String name) {
    return this.waves.get(name);
  }

  @Override
  public Waveform getWaveform(final ReferenceableElectrical electrical) {
    return this.waves.get(electrical.getIdentifier());
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

    if (this.values.containsKey(electrical.getIdentifier())) {
      return this.values.get(electrical.getIdentifier());
    }

    if (this.waves.containsKey(electrical.getIdentifier())) {
      return this.waves.get(electrical.getIdentifier());
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
    return o instanceof RealResultsDatabase;
  }
}