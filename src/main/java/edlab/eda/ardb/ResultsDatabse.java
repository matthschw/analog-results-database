package edlab.eda.ardb;

import java.util.Set;

import edlab.eda.reader.nutmeg.NutmegComplexPlot;
import edlab.eda.reader.nutmeg.NutmegPlot;
import edlab.eda.reader.nutmeg.NutmegRealPlot;

/**
 * Container consisting of waves and values
 */
public abstract class ResultsDatabse {

  /**
   * Builds a {@link ResultsDatabse} from a {@link NutmegPlot}
   * 
   * @param plot {@link NutmegPlot}
   * @return ResultsDatabse
   */
  public static ResultsDatabse buildResultDatabase(NutmegPlot plot) {

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
}