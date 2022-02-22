package edlab.eda.ardb;

import java.util.List;
import java.util.Set;

import edlab.eda.reader.nutmeg.NutmegComplexPlot;
import edlab.eda.reader.nutmeg.NutmegPlot;
import edlab.eda.reader.nutmeg.NutmegRealPlot;

/**
 * Container consisting of waves and values
 */
public abstract class ResultsDatabase {

  /**
   * Builds a {@link ResultsDatabase} from a {@link NutmegPlot}
   *
   * @param plot {@link NutmegPlot}
   * @return ResultsDatabse
   */
  public static ResultsDatabase buildResultDatabase(final NutmegPlot plot) {

    if (plot instanceof NutmegRealPlot) {
      return RealResultsDatabase.buildResultDatabase((NutmegRealPlot) plot);
    } else if (plot instanceof NutmegComplexPlot) {
      return ComplexResultsDatabase
          .buildResultDatabase((NutmegComplexPlot) plot);
    }

    return null;
  }

  /**
   * Returns a set of all value names in the container
   *
   * @return set of names
   */
  public abstract Set<String> getValueNames();

  /**
   * Returns an array of all value names in the container
   *
   * @return array of names
   */
  public abstract String[] getValueNamesAsArray();

  /**
   * Returns a set of all wave names in the container
   *
   * @return set of names
   */
  public abstract Set<String> getWaveNames();

  /**
   * Returns an array of all wave names in the container
   *
   * @return array of names
   */
  public abstract String[] getWaveNamesAsArray();

  /**
   * Check if a the database contains a value with a given name
   *
   * @param name Name of the value
   *
   * @return <code>true</code> when the database contains a value with the given
   *         name, <code>false</code> otherwise.
   */
  public abstract boolean isValueName(String name);

  /**
   * Check if a the database contains a wave with a given name
   *
   * @param name Name of the waveform
   *
   * @return <code>true</code> when the database contains a waveform with the
   *         given name, <code>false</code> otherwise.
   */
  public abstract boolean isWaveformName(String name);

  /**
   * Check if a the database contains a value with a given electrical identifier
   *
   * @param electrical Reference to the electrical
   *
   * @return <code>true</code> when the database contains a value with the given
   *         name, <code>false</code> otherwise.
   */
  public abstract boolean isValue(ReferenceableElectrical electrical);

  /**
   * Check if a the database contains a wave with a given electrical identifier
   *
   * @param electrical Reference to the electrical
   *
   * @return <code>true</code> when the database contains a waveform with the
   *         given name, <code>false</code> otherwise.
   */
  public abstract boolean isWaveform(ReferenceableElectrical electrical);

  /**
   * Check if a the database contains a value or waveform with a given name
   *
   * @param name Name of the waveform or value
   *
   * @return <code>true</code> when the database contains a value or waveform
   *         with the given name, <code>false</code> otherwise.
   */
  public abstract boolean isMember(String name);

  /**
   * Check if a the database contains a value or waveform with a given
   * electrical identifier
   *
   * @param electrical Reference to the electrical
   *
   * @return <code>true</code> when the database contains a value or waveform
   *         with the given name, <code>false</code> otherwise.
   */
  public abstract boolean isMember(ReferenceableElectrical electrical);

  /**
   * Get a value of the database
   *
   * @param name Name of the value
   * @return value when available, <code>null</code> otherwise
   */
  public abstract Value getValue(String name);

  /**
   * Get a value of the database
   *
   * @param electrical Reference to the electrical
   * @return value when available, <code>null</code> otherwise
   */
  public abstract Value getValue(ReferenceableElectrical electrical);

  /**
   * Get a waveform from the database
   *
   * @param name Name of the waveform
   * @return waveform when available, <code>null</code> otherwise
   */
  public abstract Waveform getWaveform(String name);

  /**
   * Get a waveform from the database
   *
   * @param electrical Reference to the electrical
   * @return waveform when available, <code>null</code> otherwise
   */
  public abstract Waveform getWaveform(ReferenceableElectrical electrical);

  /**
   * Get a waveform or value from the database
   *
   * @param name Name of the waveform
   * @return waveform or value when available, <code>null</code> otherwise
   */
  public abstract Object get(String name);

  /**
   * Get a waveform or value from the database
   *
   * @param electrical Reference to the electrical
   * @return waveform or value when available, <code>null</code> otherwise
   */
  public abstract Object get(ReferenceableElectrical electrical);

  /**
   * Returns if the {@link RealResultsDatabase} is empty
   *
   * @return <code>true</code> when the database is empty, <code>false</code>
   *         otherwise
   */
  public abstract boolean isEmpty();

  /**
   * Convert a set of strings to an array
   *
   * @param set Set of strings
   * @return array of strings
   */
  static String[] convert(final Set<String> set) {

    final String[] array = new String[set.size()];

    int i = 0;

    for (final String val : set) {
      array[i++] = val;
    }

    return array;
  }

  /**
   * Convert a list of strings to an array
   *
   * @param list List of strings
   * @return array of strings
   */
  static String[] convert(final List<String> list) {

    final String[] array = new String[list.size()];

    int i = 0;

    for (final String val : list) {
      array[i++] = val;
    }

    return array;
  }

  /**
   * Identify whether an object is an instance of this class
   *
   * @param o Object to be checked
   * @return <code>true</code> when the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(final Object o) {
    return o instanceof ResultsDatabase;
  }
}