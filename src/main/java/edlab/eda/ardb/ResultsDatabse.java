package edlab.eda.ardb;

import edlab.eda.reader.nutmeg.NutmegComplexPlot;
import edlab.eda.reader.nutmeg.NutmegPlot;
import edlab.eda.reader.nutmeg.NutmegRealPlot;

public abstract class ResultsDatabse {

  public static ResultsDatabse buildResultDatabase(NutmegPlot plot) {

    if (plot instanceof NutmegRealPlot) {
      return RealResultsDatabase.buildResultDatabase((NutmegRealPlot) plot);
    } else if (plot instanceof NutmegComplexPlot) {
      return ComplexResultsDatabase
          .buildResultDatabase((NutmegComplexPlot) plot);
    }
    return null;
  }
}