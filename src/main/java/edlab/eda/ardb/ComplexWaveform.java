package edlab.eda.ardb;

import org.apache.commons.math3.complex.Complex;

/**
 * Class for representing a complex waveform. Both axes and the units are
 * covered by the object.
 */
public class ComplexWaveform extends Waveform {

  private Complex[] y;

  private ComplexWaveform(final double[] x, final Complex[] y,
      final String unitX, final String unitY) {
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
    return this.y;
  }

  @Override
  public String toString() {

    String res = "";

    for (int i = 0; i < this.x.length; i++) {

      if (i > 0) {
        res += "\n";
      }

      res += "(" + this.x[i] + " " + this.getUnitX() + " , " + this.y[i] + " "
          + this.getUnitY() + ")";
    }
    return res;
  }

  @Override
  public ComplexValue getValue(final double pos) {

    if (pos <= this.x[0]) {

      return new ComplexValue(
          this.y[0].add(this.y[1].subtract(this.y[0]).multiply(
              new Complex((pos - this.x[0]) / (this.x[1] - this.x[0])))),
          this.getUnitY());

    } else if (pos >= this.x[this.x.length - 1]) {

      return new ComplexValue(
          this.y[this.y.length - 1]
              .add(this.y[this.y.length - 1].subtract(this.y[this.y.length - 2])
                  .multiply(new Complex(
                      (pos - this.x[0]) / (this.x[1] - this.x[0])))),
          this.getUnitY());

    } else {

      for (int i = 0; i < (this.x.length - 1); i++) {

        if (((this.x[i] - pos) * (this.x[i + 1] - pos)) <= 0) {

          return new ComplexValue(
              this.y[i].add(this.y[i + 1].subtract(this.y[i])
                  .multiply(new Complex(
                      (pos - this.x[i]) / (this.x[i + 1] - this.x[i])))),
              this.getUnitY());

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

    final Complex[] yVec = new Complex[this.y.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = this.y[i].conjugate();
    }

    return ComplexWaveform.buildComplexWaveform(this.x, yVec, this.getUnitX(),
        this.getUnitY());
  }

  @Override
  public RealWaveform abs() {

    final double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = this.y[i].abs();
    }

    return RealWaveform.buildRealWaveform(this.x, yVec, this.getUnitX(),
        this.getUnitY());
  }

  @Override
  public RealWaveform real() {

    final double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = this.y[i].getReal();
    }

    return RealWaveform.buildRealWaveform(this.x, yVec, this.getUnitX(),
        this.getUnitY());
  }

  @Override
  public RealWaveform imag() {

    final double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = this.y[i].getImaginary();
    }

    return RealWaveform.buildRealWaveform(this.x, yVec, this.getUnitX(),
        this.getUnitY());
  }

  @Override
  public RealWaveform phaseDeg() {

    final double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = (this.y[i].getArgument() / Math.PI) * 180;
    }

    return RealWaveform.buildRealWaveform(this.x, yVec, this.getUnitX(), "deg");
  }

  @Override
  public RealWaveform db10() {

    final RealWaveform realWave = this.abs();

    return realWave.db10();
  }

  @Override
  public RealWaveform db20() {
    return this.abs().db20();
  }

  /**
   * Create a new {@link ComplexWaveform}
   *
   * @param x     x-values
   * @param y     y-values
   * @param unitX unit of x-values
   * @param unitY unit of y-values
   * @return {@link ComplexWaveform}
   */
  public static ComplexWaveform buildComplexWaveform(final double[] x,
      final Complex[] y, final String unitX, final String unitY) {

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
  private static void sortWaveElements(final double[] x, final Complex[] y) {

    double swapReal;
    Complex swapComplex;
    boolean swapPerformed = true;

    while (swapPerformed) {

      swapPerformed = false;

      for (int i = 0; i < (x.length - 1); i++) {

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
  public static boolean isInstanceOf(final Object o) {
    return o instanceof ComplexWaveform;
  }
}