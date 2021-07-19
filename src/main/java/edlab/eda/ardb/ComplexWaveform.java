package edlab.eda.ardb;

import org.apache.commons.math3.complex.Complex;

public class ComplexWaveform extends Waveform {

  private Complex[] y;

  private ComplexWaveform(double[] x, Complex[] y, String unitX, String unitY) {
    super(x, unitX, unitY);
    this.y = y;
  }

  public ComplexWaveform() {
    super();
  }

  public Complex[] getY() {
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
  public Value getValue(double pos) {

    if (pos < x[0]) {

      return new ComplexValue(
          y[0].add(y[1].subtract(y[0])
              .multiply(new Complex((pos - x[0]) / (x[1] - x[0])))),
          getUnitY());

    } else if (pos < x[x.length]) {

      return new ComplexValue(
          y[y.length - 1].add(y[y.length - 1].subtract(y[y.length - 2])
              .multiply(new Complex((pos - x[0]) / (x[1] - x[0])))),
          getUnitY());

    } else {

      for (int i = 0; i < x.length - 1; i++) {
        if ((x[i] - pos) * (x[i + 1] - pos) <= 0) {

          return new ComplexValue(
              y[i].add(y[i + 1].subtract(y[i])
                  .multiply(new Complex((pos - x[i]) / (x[i + 1] - x[i])))),
              getUnitY());

        }
      }
    }

    return null;
  }

  @Override
  public RealWaveform abs() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = y[i].abs();
    }

    return RealWaveform.buildRealWaveform(x, yVec, getUnitX(), getUnitY());
  }

  public RealWaveform phaseDeg() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = y[i].getArgument() / Math.PI * 180;
    }

    return RealWaveform.buildRealWaveform(x, yVec, getUnitX(), "deg");
  }

  @Override
  public RealWaveform db20() {

    RealWaveform realWave = this.abs();

    return realWave.db20();
  }

  @Override
  public int noOfVals() {
    return x.length;
  }

  @Override
  public Value cross(double val) {
    // TODO Auto-generated method stub
    return null;
  }

  public static ComplexWaveform buildRealWaveform(double[] x, Complex[] y,
      String unitX, String unitY) {

    if (x.length == y.length) {

      sortWaveElements(x, y);

      return new ComplexWaveform(x, y, unitX, unitY);

    } else {
      System.out.println("Length of arrays do not match");
      return null;
    }
  }

  private static void sortWaveElements(double[] x, Complex[] y) {

    double swapReal;
    Complex swapComplex;
    boolean swapPerformed = true;

    while (swapPerformed) {

      swapPerformed = false;

      for (int i = 0; i < x.length - 1; i++) {

        if (x[i] > x[i + 1]) {

          swapReal = x[i + 1];
          x[i + 1] = x[i];
          x[i] = swapReal;

          swapComplex = y[i + 1];
          y[i + 1] = y[i];
          y[i] = swapComplex;
          swapPerformed = true;
        }
      }
    }
  }
}