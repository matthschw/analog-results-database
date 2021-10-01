package edlab.eda.ardb;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class for representing a complex waveform. Both axes and the units are
 * covered by the object.
 */
public class RealWaveform extends Waveform {

  private double[] y;

  private RealWaveform(double[] x, double[] y, String unitX, String unitY) {
    super(x, unitX, unitY);
    this.y = y;
  }

  /**
   * Creating an empty waveform
   */
  public RealWaveform() {
    super();
  }

  /**
   * Get the y-values of the waveform
   * 
   * @return y-values of the waveform
   */
  public double[] getY() {
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

  /**
   * Clip a waveform to a range
   * 
   * @param left  Left boundary of range
   * @param right Right boundary of range
   * @return CLipped waveform
   */
  public RealWaveform clip(double left, double right) {

    LinkedList<Double> newXVals = new LinkedList<Double>();
    LinkedList<Double> newYVals = new LinkedList<Double>();

    for (int i = 0; i < x.length; i++) {
      if (left <= x[i] && x[i] <= right) {
        newXVals.addLast(x[i]);
        newYVals.addLast(y[i]);
      }
    }

    RealValue leftValue = this.getValue(left);

    if (!leftValue.isInvalid()) {
      if (newXVals.get(0) != leftValue.getValue()) {
        newYVals.addFirst(leftValue.getValue());
        newXVals.addFirst(left);
      }
    }

    RealValue rightValue = this.getValue(right);

    if (!rightValue.isInvalid()) {
      if (newXVals.get(newXVals.size() - 1) != rightValue.getValue()) {
        newYVals.addLast(rightValue.getValue());
        newXVals.addLast(right);
      }
    }

    if (newXVals.size() > 0) {

      double[] newX = new double[newXVals.size()];
      double[] newY = new double[newYVals.size()];

      for (int i = 0; i < newY.length; i++) {
        newX[i] = newXVals.get(i);
        newY[i] = newYVals.get(i);
      }

      return new RealWaveform(newX, newY, getUnitX(), getUnitY());
    } else {

      return new RealWaveform();
    }
  }

  /**
   * Add a value to the waveform
   * 
   * @param value Value to be added
   * @return Waveform
   */
  public RealWaveform add(double value) {

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i] + value;
      newY[i] = y[i];
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  /**
   * Add a value to the waveform
   * 
   * @param value Value to be added
   * @return Waveform
   */
  public RealWaveform add(RealValue value) {

    if (value.isInvalid()) {
      return null;
    } else {
      return this.add(value.getValue());
    }
  }

  /**
   * Add two waveforms
   * 
   * @param wave Waveform to be added
   * @return Waveform
   */
  public RealWaveform add(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this);
    }

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] + wave.y[i];
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  /**
   * Subtract a value from a waveform
   * 
   * @param value Value to be subtracted
   * @return Waveform
   */
  public RealWaveform subtract(double value) {

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] - value;
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  /**
   * Subtract a value from a waveform
   * 
   * @param value Value to be subtracted
   * @return Waveform
   */
  public RealWaveform subtract(RealValue value) {

    if (value.isInvalid()) {
      return new RealWaveform();
    } else {
      return this.subtract(value.getValue());
    }
  }

  /**
   * Subtract a waveform from a waveform
   * 
   * @param wave Waveform to be subtracted
   * @return Waveform
   */
  public RealWaveform subtract(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this);
    }

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] - wave.y[i];
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  /**
   * Multiply a value with a waveform
   * 
   * @param value Multiplier
   * @return Waveform
   */
  public RealWaveform multiply(double value) {

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] * value;
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  /**
   * Multiply a value with a waveform
   * 
   * @param value Multiplier
   * @return Waveform
   */
  public RealWaveform multiply(RealValue value) {

    if (value.isInvalid()) {
      return null;
    } else {
      return this.multiply(value.getValue());
    }
  }

  /**
   * Multiply two waveforms
   * 
   * @param wave Waveform to be multiplied
   * @return Waveform
   */
  public RealWaveform multiply(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this);
    }

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] * wave.y[i];
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  /**
   * Divide a waveform by a value
   * 
   * @param value Divisor
   * @return Waveform
   */
  public RealWaveform divide(double value) {

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] / value;
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  /**
   * Divide a waveform by a value
   * 
   * @param value Divisor
   * @return Waveform
   */
  public RealWaveform divide(RealValue value) {

    if (value.isInvalid()) {
      return null;
    } else {
      return this.divide(value.getValue());
    }
  }

  /**
   * Divide a waveform by another waveform
   * 
   * @param wave Divisor Waveform
   * @return Waveform
   */
  public RealWaveform divide(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this);
    }

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] / wave.y[i];
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  @Override
  public RealValue getValue(double pos) {

    double m;

    if (pos <= x[0]) {
      m = (y[1] - y[0]) / (x[1] - x[0]);

      return new RealValue(y[0] + m * (pos - x[0]), this.getUnitY());

    } else if (pos >= x[x.length - 1]) {

      m = (y[y.length - 1] - y[y.length - 2])
          / (x[x.length - 1] - x[x.length - 2]);

      return new RealValue(y[y.length - 1] + m * (pos - x[x.length - 1]),
          this.getUnitY());
    } else {

      for (int i = 0; i < x.length - 1; i++) {
        if ((x[i] - pos) * (x[i + 1] - pos) <= 0) {

          m = (y[i + 1] - y[i]) / (x[i + 1] - x[i]);

          return new RealValue(y[i] + m * (pos - x[i]), this.getUnitY());

        }
      }
    }

    return new RealValue();
  }

  /**
   * Evaluate a waveform at a value
   * 
   * @param val x-value where the waveform is evaluated
   * @return y-value
   */
  public RealValue getValue(RealValue val) {

    if (val.isInvalid()) {
      return new RealValue();
    } else {
      return this.getValue(val.getValue());
    }
  }

  /**
   * Resample a waveform
   * 
   * @param newX New x-values to be used
   * @return resampled waveform
   */
  public RealWaveform resample(double[] newX) {

    double[] yNew = new double[this.x.length];

    for (int i = 0; i < newX.length; i++) {
      yNew[i] = this.getValue(newX[i]).getValue();
    }

    return new RealWaveform(x, yNew, getUnitX(), getUnitY());
  }

  /**
   * Resample a waveform
   * 
   * @param wave x-values of waveform to be used
   * @return resampled waveform
   */
  public RealWaveform resample(RealWaveform wave) {

    return this.resample(wave.x);
  }

  @Override
  public RealWaveform abs() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = Math.abs(y[i]);
    }

    return new RealWaveform(x, yVec, getUnitX(), getUnitY());
  }

  /**
   * Exponentiate a waveform
   * 
   * @param exponent
   * @return Waveform
   */
  public RealWaveform pow(double exponent) {

    double[] pow = new double[y.length];

    for (int i = 0; i < pow.length; i++) {
      pow[i] = 10 * Math.pow(y[i], exponent);
    }

    return new RealWaveform(this.getX(), pow, this.getUnitX(), "");
  }

  /**
   * Calculate the natural logarithm of a waveform
   * 
   * @return Waveform
   */
  public RealWaveform ln() {

    double[] ln = new double[y.length];

    for (int i = 0; i < ln.length; i++) {
      ln[i] = 10 * Math.log(y[i]);
    }

    return new RealWaveform(this.getX(), ln, this.getUnitX(), "");
  }

  @Override
  public RealWaveform db10() {

    double[] db10 = new double[y.length];

    for (int i = 0; i < db10.length; i++) {
      db10[i] = 10 * Math.log10(y[i]);
    }

    return new RealWaveform(this.getX(), db10, this.getUnitX(), "");
  }

  @Override
  public RealWaveform db20() {

    double[] db20 = new double[y.length];

    for (int i = 0; i < db20.length; i++) {
      db20[i] = 20 * Math.log10(y[i]);
    }

    return new RealWaveform(this.getX(), db20, this.getUnitX(), "");
  }

  /**
   * Calculate the sine of a waveform
   * 
   * @return Waveform
   */
  public RealWaveform sin() {

    double[] sin = new double[y.length];

    for (int i = 0; i < sin.length; i++) {
      sin[i] = 10 * Math.sin(y[i]);
    }

    return new RealWaveform(this.getX(), sin, this.getUnitX(), "");
  }

  /**
   * Calculate the arc-sine of a waveform
   * 
   * @return Waveform
   */
  public RealWaveform asin() {

    double[] asin = new double[y.length];

    for (int i = 0; i < asin.length; i++) {
      asin[i] = 10 * Math.asin(y[i]);
    }

    return new RealWaveform(this.getX(), asin, this.getUnitX(), "");
  }

  /**
   * Calculate the cosine of a waveform
   * 
   * @return Waveform
   */
  public RealWaveform cos() {

    double[] cos = new double[y.length];

    for (int i = 0; i < cos.length; i++) {
      cos[i] = 10 * Math.sin(y[i]);
    }

    return new RealWaveform(this.getX(), cos, this.getUnitX(), "");
  }

  /**
   * Calculate the arc-cosine of a waveform
   * 
   * @return Waveform
   */
  public RealWaveform acos() {

    double[] acos = new double[y.length];

    for (int i = 0; i < acos.length; i++) {
      acos[i] = 10 * Math.acos(y[i]);
    }

    return new RealWaveform(this.getX(), acos, this.getUnitX(), "");
  }

  /**
   * Calculate the tangent of a waveform
   * 
   * @return Waveform
   */
  public RealWaveform tan() {

    double[] tan = new double[y.length];

    for (int i = 0; i < tan.length; i++) {
      tan[i] = 10 * Math.tan(y[i]);
    }

    return new RealWaveform(this.getX(), tan, this.getUnitX(), "");
  }

  /**
   * Calculate the arc-tangent of a waveform
   * 
   * @return Waveform
   */
  public RealWaveform atan() {

    double[] atan = new double[y.length];

    for (int i = 0; i < atan.length; i++) {
      atan[i] = 10 * Math.atan(y[i]);
    }

    return new RealWaveform(this.getX(), atan, this.getUnitX(), "");
  }

  /**
   * Calculate the nth intersection of the waveform with a constant value
   * 
   * @param val  y-value if the intersection
   * @param edge Nth occurrence of the intersecetion
   * @return Intersection
   */
  public RealValue cross(double val, int edge) {

    int counter = 1;

    for (int i = 0; i < x.length - 1; i++) {

      if ((y[i] - val) * (y[i + 1] - val) <= 0) {

        if (counter == edge) {
          return new RealValue(
              x[i] + (val - y[i]) / (y[i + 1] - y[i]) * (x[i + 1] - x[i]),
              getUnitX());
        }

        counter++;

      }
    }
    return new RealValue();
  }

  /**
   * Calculate the nth intersection of the waveform with a constant value
   * 
   * @param val  y-value if the intersection
   * @param edge Nth occurrence of the intersection
   * @return Intersection
   */
  public RealValue cross(RealValue val, int edge) {

    if (val.isInvalid()) {
      return new RealValue();
    } else {
      return this.cross(val.getValue(), edge);
    }
  }

  /**
   * Calculate the intersections of the waveform with a constant value
   * 
   * @param val y-value if the intersection
   * @return Waveform of the intersections
   */
  public RealWaveform cross(double val) {

    ArrayList<Double> newXVals = new ArrayList<Double>();
    ArrayList<Double> newYVals = new ArrayList<Double>();

    for (int i = 0; i < x.length - 1; i++) {
      if ((y[i] - val) * (y[i + 1] - val) <= 0) {
        newXVals
            .add(x[i] + (val - y[i]) / (y[i + 1] - y[i]) * (x[i + 1] - x[i]));
        newYVals.add(val);
      }
    }

    double[] newX = new double[newXVals.size()];
    double[] newY = new double[newYVals.size()];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = newXVals.get(i);
      newY[i] = newYVals.get(i);
    }

    return new RealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  /**
   * Calculate the intersections of the waveform with a constant value
   * 
   * @param val y-value if the intersection
   * @return Waveform of the intersections
   */
  public RealWaveform cross(RealValue val) {

    if (val.isInvalid()) {
      return new RealWaveform();
    } else {
      return this.cross(val.getValue());
    }
  }

  /**
   * Combine two waveforms by taking the y-values of both waveforms
   * 
   * @param wave y-values to be used
   * @return Waveform
   */
  public RealWaveform waveVsWave(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this);
    }

    return new RealWaveform(this.getY(), wave.getY(), this.getUnitX(),
        wave.getUnitY());
  }

  /**
   * Get the minimal y-value of a waveform
   * 
   * @return minimal y-value
   */
  public RealValue ymin() {

    double min = Double.MAX_VALUE;

    for (int i = 0; i < y.length; i++) {
      min = Math.min(y[i], min);
    }

    return new RealValue(min, getUnitY());
  }

  /**
   * Get the maximal y-value of a waveform
   * 
   * @return maximal y-value
   */
  public RealValue ymax() {

    double max = Double.MIN_VALUE;

    for (int i = 0; i < y.length; i++) {
      max = Math.max(y[i], max);
    }

    return new RealValue(max, getUnitY());
  }

  /**
   * Concatenate waveform
   * 
   * @param wave Waveform to be concatenated
   * @return Waveform
   */
  public RealWaveform concat(RealWaveform wave) {

    double[] newX = new double[this.x.length + wave.x.length];
    double[] newY = new double[this.y.length + wave.y.length];

    for (int i = 0; i < x.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i];
    }

    for (int i = 0; i < wave.x.length; i++) {
      newX[i + x.length] = wave.x[i];
      newY[i + y.length] = wave.y[i];
    }

    sortWaveElements(newX, newY);

    return new RealWaveform(newX, newY, this.getUnitX(), this.getUnitY());

  }

  /**
   * Create a new {@link RealWaveform}
   * 
   * @param x     x-values
   * @param y     y-values
   * @param unitX unit of x-values
   * @param unitY unit of y-values
   * @return {@link RealWaveform}
   */
  public static RealWaveform buildRealWaveform(double[] x, double[] y,
      String unitX, String unitY) {

    if (x.length == y.length) {

      sortWaveElements(x, y);

      return new RealWaveform(x, y, unitX, unitY);

    } else {
      System.out.println("Length of arrays do not match");
      return new RealWaveform();
    }
  }

  /**
   * Sort both <code>x</code> and <code>y</code> that <code>x</code> is sorted
   * in ascending order
   * 
   * @param x x-values
   * @param y y-values
   */
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
  
  /**
   * Check if an object is an instance of this class
   * 
   * @param o Object
   * @return <code>true</code> if the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(Object o) {
    return o instanceof RealWaveform;
  }
}