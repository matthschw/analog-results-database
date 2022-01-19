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
public class RealResultsDatabase extends ResultsDatabase {

  private Map<String, RealValue> values = new HashMap<String, RealValue>();
  private Map<String, RealWaveform> waves = new HashMap<String, RealWaveform>();

  private RealResultsDatabase() {
  }

  /**
   * Create a {@link RealResultsDatabase}
   * 
   * @param value map of values
   * @param waves map of waves
   * @return database
   */
  public static RealResultsDatabase create(Map<String, RealValue> value,
      Map<String, RealWaveform> waves) {

    RealResultsDatabase retval = new RealResultsDatabase();

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
   * @return
   */
  public static RealResultsDatabase create(String[] valueNames,
      RealValue[] valuesKeys, String[] waveNames, RealWaveform[] waveKeys) {

    RealResultsDatabase retval = new RealResultsDatabase();

    for (int i = 0; i < Math.min(valueNames.length, valuesKeys.length); i++) {
      retval.values.put(valueNames[i], valuesKeys[i]);
    }

    return retval;
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
    return buildResultDatabase(plot, new DefaultTranslator());
  }

  /**
   * Builds a {@link RealResultsDatabase} from a {@link NutmegRealPlot}
   * 
   * @param plot       {@link NutmegRealPlot}
   * @param translator Translator for wave names
   * @return ComplexResultsDatabase
   */
  public static RealResultsDatabase buildResultDatabase(NutmegRealPlot plot,
      CharSequenceTranslator translator) {

    RealResultsDatabase retval = new RealResultsDatabase();

    if (plot.getNoOfPoints() == 1) {

      retval.values = new HashMap<String, RealValue>();

      for (String wave : plot.getWaves()) {

        retval.values.put(translator.translate(wave),
            new RealValue(plot.getWave(wave)[0], plot.getUnit(wave)));
      }
    } else {

      String refWave = plot.getRefWave();
      String refWaveUnit = plot.getUnit(refWave);

      retval.waves = new HashMap<String, RealWaveform>();

      double x[] = plot.getWave(refWave);

      for (String wave : plot.getWaves()) {

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

    if (this.values != null && !this.values.isEmpty()) {

      retval += "Values:";

      firstIteration = false;

      for (String name : this.values.keySet()) {

        retval += "\n- " + name + " = " + this.values.get(name);
      }
    }

    if (this.waves != null && !this.waves.isEmpty()) {

      if (!firstIteration) {
        retval += "\n";
      }

      retval += "Waves:";

      firstIteration = false;

      for (String name : this.waves.keySet()) {

        retval += "\n- " + name + " " + this.waves.get(name).getUnitY();
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

  /**
   * Identify whether an object is an instance of this class
   * 
   * @param o Object to be checked
   * @return <code>true</code> when the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(Object o) {
    return o instanceof RealResultsDatabase;
  }
}