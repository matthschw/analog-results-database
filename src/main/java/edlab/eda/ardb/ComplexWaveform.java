package edlab.eda.ardb;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedList;

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
   * Create an complex waveform from a real waveform
   */
  public ComplexWaveform(RealWaveform realWave) {
    super(realWave.getX(), realWave.getUnitX(), realWave.getUnitY());
    this.y = new Complex[realWave.getY().length];

    for (int i = 0; i < this.y.length; i++) {
      this.y[i] = new Complex(realWave.getY()[i]);
    }
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

  @Override
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

  @Override
  public Waveform clip(double left, double right) {
    final LinkedList<Double> newXVals = new LinkedList<>();
    final LinkedList<Complex> newYVals = new LinkedList<>();

    for (int i = 0; i < this.x.length; i++) {
      if ((left <= this.x[i]) && (this.x[i] <= right)) {
        newXVals.addLast(this.x[i]);
        newYVals.addLast(this.y[i]);
      }
    }

    final ComplexValue leftValue = this.getValue(left);

    if (!leftValue.isInvalid()) {

      if (!newXVals.get(0).equals(leftValue.getValue())) {
        newYVals.addFirst(leftValue.getValue());
        newXVals.addFirst(left);
      }
    }

    final ComplexValue rightValue = this.getValue(right);

    if (!rightValue.isInvalid()) {

      if (!newXVals.get(newXVals.size() - 1).equals(rightValue.getValue())) {
        newYVals.addLast(rightValue.getValue());
        newXVals.addLast(right);
      }
    }

    if (newXVals.size() > 0) {

      final double[] newX = new double[newXVals.size()];
      final Complex[] newY = new Complex[newYVals.size()];

      for (int i = 0; i < newY.length; i++) {
        newX[i] = newXVals.get(i);
        newY[i] = newYVals.get(i);
      }

      return new ComplexWaveform(newX, newY, this.getUnitX(), this.getUnitY());
    } else {
      return new ComplexWaveform();
    }
  }

  @Override
  public Waveform add(Waveform wave) {
    if (wave instanceof RealWaveform) {
      return this.add((RealWaveform) wave);
    } else {
      return this.add((ComplexWaveform) wave);
    }
  }

  public Waveform add(RealWaveform wave) {
    return this.add(new ComplexWaveform(wave));
  }

  public Waveform add(ComplexWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this.getX());
    }

    final double[] newX = new double[this.x.length];
    final Complex[] newY = new Complex[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i].add(wave.y[i]);
    }
    return new ComplexWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  private ComplexWaveform resample(final double[] newX) {

    // TODO Auto-generated method stub
    final Complex[] yNew = new Complex[newX.length];

    for (int i = 0; i < (newX.length - 1); i++) {
      yNew[i] = this.getValue(newX[i]).getValue();
    }

    return new ComplexWaveform(this.x, yNew, this.getUnitX(), this.getUnitY());
  }

  @Override
  public ComplexWaveform add(double value) {

    final double[] newX = new double[this.x.length];
    final Complex[] newY = new Complex[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i].add(value);
    }
    return new ComplexWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  @Override
  public Waveform add(BigDecimal value) {
    return this.add(value.round(MathContext.DECIMAL64).doubleValue());
  }

  public Waveform add(RealValue value) {
    return this.add(value.getValue());
  }

  public Waveform add(ComplexValue value) {
    return this.add(value.getValue());
  }

  @Override
  public Waveform add(Value value) {

    if (value instanceof RealValue) {
      return this.add((RealValue) value);
    } else {
      return this.add((ComplexValue) value);
    }
  }

  @Override
  public Waveform add(Complex value) {

    final double[] newX = new double[this.x.length];
    final Complex[] newY = new Complex[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i].add(value);
    }

    return new ComplexWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }
}