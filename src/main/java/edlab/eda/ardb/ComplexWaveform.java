package edlab.eda.ardb;

import org.apache.commons.math3.complex.Complex;

/**
 * Class for representing a complex waveform. Both axes and the units are
 * covered by the object.
 */
public class ComplexWaveform extends Waveform {

  private Complex[] y;

  private ComplexWaveform(double[] x, Complex[] y, String unitX, String unitY) {
    super(x, unitX, unitY);
    this.y = y;
  }

  /**
   * Create an empty complex waveform
   */
  public ComplexWaveform() {
    super();
  }

  /**
   * Get the y-values of a complex waveform
   * 
   * @return y-values
   */
  public Complex[] getY() {
    return y;
  }

  @Override
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
  public ComplexValue getValue(double pos) {

    if (pos <= x[0]) {

      return new ComplexValue(
          y[0].add(y[1].subtract(y[0])
              .multiply(new Complex((pos - x[0]) / (x[1] - x[0])))),
          getUnitY());

    } else if (pos >= x[x.length - 1]) {

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

  /**
   * Calculate the complex conjugate of a waveform
   * 
   * @return Waveform
   */
  public ComplexWaveform conjugate() {

    Complex[] yVec = new Complex[this.y.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = y[i].conjugate();
    }

    return ComplexWaveform.buildRealWaveform(x, yVec, getUnitX(), getUnitY());
  }

  @Override
  public RealWaveform abs() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = y[i].abs();
    }

    return RealWaveform.buildRealWaveform(x, yVec, getUnitX(), getUnitY());
  }

  /**
   * Extract the real-part of a waveform
   * 
   * @return Waveform
   */
  public RealWaveform real() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = y[i].getReal();
    }

    return RealWaveform.buildRealWaveform(x, yVec, getUnitX(), getUnitY());
  }

  /**
   * Extract the imaginary-part of a waveform
   * 
   * @return Waveform
   */
  public RealWaveform imag() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = y[i].getImaginary();
    }

    return RealWaveform.buildRealWaveform(x, yVec, getUnitX(), getUnitY());
  }

  /**
   * Extract the phase of a {@link ComplexWaveform}
   * 
   * @return Waveform
   */
  public RealWaveform phaseDeg() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = y[i].getArgument() / Math.PI * 180;
    }

    return RealWaveform.buildRealWaveform(x, yVec, getUnitX(), "deg");
  }

  @Override
  public RealWaveform db10() {

    RealWaveform realWave = this.abs();

    return realWave.db10();
  }

  @Override
  public RealWaveform db20() {

    RealWaveform realWave = this.abs();

    return realWave.db20();
  }

  /**
   * Create a new {@link RealWaveform}
   * 
   * @param x     x-values
   * @param y     y-values
   * @param unitX unit of x-values
   * @param unitY unit of y-values
   * @return {@link ComplexWaveform}
   */
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

  /**
   * Sort both <code>x</code> and <code>y</code> that <code>x</code> is sorted
   * in ascending order
   * 
   * @param x x-values
   * @param y y-values
   */
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

  /**
   * Check if an object is an instance of this class
   * 
   * @param o Object
   * @return <code>true</code> if the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(Object o) {
    return o instanceof ComplexWaveform;
  }
}