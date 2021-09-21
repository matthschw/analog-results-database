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
}