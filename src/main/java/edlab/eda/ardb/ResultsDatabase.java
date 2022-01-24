package edlab.eda.ardb;

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
  public static ResultsDatabase buildResultDatabase(NutmegPlot plot) {

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
   * Returns a set of all wave names in the container
   * 
   * @return set of names
   */
  public abstract Set<String> getWaveNames();

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
   * @param electrical Reference to the electrical
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
   * Identify whether an object is an instance of this class
   * 
   * @param o Object to be checked
   * @return <code>true</code> when the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(Object o) {
    return o instanceof ResultsDatabase;
  }
}