package edlab.eda.ardb;

import java.util.ArrayList;

public class RealWaveform extends Waveform {

  private double[] y;

  private RealWaveform(double[] x, double[] y, String unitX, String unitY) {
    super(x, unitX, unitY);
    this.y = y;
  }

  public double[] getY() {
    return y;
  }

  public String toString() {
    String res = "";
    for (int i = 0; i < x.length; i++) {
      if (i > 0) {
        res += "\n";
      }
      res += "(" + x[i] + " " + getUnitX() + " , " + y[i] + " " + getUnitY()
          + ")";
    }
    return res;
  }

  @Override
  public int noOfVals() {
    return x.length;
  }

  public RealWaveform clip(double left, double right) {

    ArrayList<Double> newXVals = new ArrayList<Double>();
    ArrayList<Double> newYVals = new ArrayList<Double>();

    for (int i = 0; i < x.length; i++) {
      if (left <= x[i] && x[i] <= right) {
        newXVals.add(x[i]);
        newYVals.add(y[i]);
      }
    }

    double[] newX = new double[newXVals.size()];
    double[] newY = new double[newYVals.size()];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = newXVals.get(i);
      newY[i] = newYVals.get(i);
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  @Override
  public RealValue getValue(double pos) {

    double m;

    if (pos < x[0]) {

      m = (y[1] - y[0]) / (x[1] - x[0]);

      return new RealValue(y[0] + m * (pos - x[0]), this.getUnitY());

    } else if (pos < x[x.length]) {

      m = (y[y.length - 1] - y[y.length - 2])
          / (x[x.length - 1] - x[x.length - 2]);

      return new RealValue(y[y.length - 1] + m * (pos - x[x.length - 1]),
          this.getUnitY());
    } else {

      for (int i = 0; i < x.length - 1; i++) {
        if ((x[i] - pos) * (x[i + 1] - pos) < 0) {

          m = (y[i + 1] - y[i]) / (x[i + 1] - x[i]);

          return new RealValue(y[i] + m * (pos - x[i]), this.getUnitY());

        }
      }
    }

    return new RealValue();
  }

  public RealValue getValue(RealValue val) {

    if (val.isInvalid()) {
      return new RealValue();
    } else {
      return this.getValue(val.getValue());
    }
  }

  @Override
  public RealWaveform abs() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = Math.abs(y[i]);
    }

    return new RealWaveform(x, yVec, getUnitX(), getUnitY());
  }

  @Override
  public RealWaveform db20() {

    double[] db20 = new double[y.length];

    for (int i = 0; i < db20.length; i++) {
      db20[i] = 20 * Math.log10(y[i]);
    }
    return new RealWaveform(this.getX(), db20, this.getUnitX(), "");
  }

  public RealValue cross(double val) {

    for (int i = 0; i < x.length - 1; i++) {

      if ((y[i] - val) * (y[i + 1] - val) <= 0) {
        return new RealValue(
            x[i] + (val - y[i]) / (y[i + 1] - y[i]) * (x[i + 1] - x[i]),
            getUnitX());
      }
    }
    return new RealValue();
  }

  public RealValue cross(RealValue val) {

    if (val.isInvalid()) {
      return new RealValue();
    } else {
      return this.cross(val.getValue());
    }
  }

  public RealValue ymin() {

    double min = Double.MAX_VALUE;

    for (int i = 0; i < y.length; i++) {
      min = Math.min(y[i], min);
    }

    return new RealValue(min, getUnitY());
  }

  public RealValue ymax() {

    double max = Double.MIN_VALUE;

    for (int i = 0; i < y.length; i++) {
      max = Math.max(y[i], max);
    }

    return new RealValue(max, getUnitY());
  }

  public static RealWaveform buildRealWaveform(double[] x, double[] y,
      String unitX, String unitY) {

    if (x.length == y.length) {

      sortWaveElements(x, y);

      return new RealWaveform(x, y, unitX, unitY);

    } else {
      System.out.println("Length of arrays do not match");
      return null;
    }
  }

  private static void sortWaveElements(double[] x, double[] y) {

    double swap;
    boolean swapPerformed = true;

    while (swapPerformed) {

      swapPerformed = false;

      for (int i = 0; i < x.length - 1; i++) {

        if (x[i] > x[i + 1]) {

          swap = x[i + 1];
          x[i + 1] = x[i];
          x[i] = swap;

          swap = y[i + 1];
          y[i + 1] = y[i];
          y[i] = swap;
          swapPerformed = true;
        }
      }
    }

  }

}