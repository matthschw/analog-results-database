package edlab.eda.ardb;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.math3.complex.Complex;

/**
 * Class for representing a complex waveform. Both axes and the units are
 * covered by the object.
 */
public final class RealWaveform extends Waveform {

  private double[] y;

  private RealWaveform(final double[] x, final double[] y, final String unitX,
      final String unitY) {
    super(x, unitX, unitY);
    this.y = y;
  }

  /**
   * Creating an empty waveform
   */
  RealWaveform() {
    super();
    this.y = new double[0];
  }

  /**
   * Get the y-values of the waveform
   *
   * @return y-values of the waveform
   */
  public double[] getY() {
    return this.y;
  }

  @Override
  public String toString() {

    if (this.isEmpty()) {

      return "";

    } else {

      final StringBuilder builder = new StringBuilder();

      for (int i = 0; i < this.x.length; i++) {

        if (i > 0) {
          builder.append("\n");
        }

        builder.append("(").append(Formatter.format(this.x[i])).append(" ")
            .append(this.getUnitX()).append(" , ")
            .append(Formatter.format(this.y[i])).append(" ")
            .append(this.getUnitY()).append(")");
      }

      return builder.toString();
    }
  }

  @Override
  public RealWaveform clip(final double left, final double right) {

    if (this.isEmpty()) {
      return new RealWaveform();
    } else {
      final LinkedList<Double> newXVals = new LinkedList<>();
      final LinkedList<Double> newYVals = new LinkedList<>();

      for (int i = 0; i < this.x.length; i++) {
        if ((left <= this.x[i]) && (this.x[i] <= right)) {
          newXVals.addLast(this.x[i]);
          newYVals.addLast(this.y[i]);
        }
      }

      final RealValue leftValue = this.getValue(left);

      if (!leftValue.isNaN()) {

        if (newYVals.get(0) != leftValue.getValue()) {
          newYVals.addFirst(leftValue.getValue());
          newXVals.addFirst(left);
        }
      }

      final RealValue rightValue = this.getValue(right);

      if (!rightValue.isNaN()) {

        if (newYVals.get(newXVals.size() - 1) != rightValue.getValue()) {
          newYVals.addLast(rightValue.getValue());
          newXVals.addLast(right);
        }
      }

      if (newXVals.size() > 0) {

        final double[] newX = new double[newXVals.size()];
        final double[] newY = new double[newYVals.size()];

        for (int i = 0; i < newY.length; i++) {
          newX[i] = newXVals.get(i);
          newY[i] = newYVals.get(i);

        }

        return buildRealWaveform(newX, newY, getUnitX(), getUnitY());

      } else {
        return new RealWaveform();
      }
    }
  }

  @Override
  public RealWaveform add(final double value) {

    final double[] newX = new double[this.x.length];
    final double[] newY = new double[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i] + value;
    }

    return buildRealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  @Override
  public RealWaveform add(final BigDecimal value) {
    return this.add(value.round(MathContext.DECIMAL64).doubleValue());
  }

  /**
   * Add a value to the waveform
   *
   * @param value Value to be added
   * @return Waveform
   */
  public RealWaveform add(final RealValue value) {

    if (value.isNaN()) {
      return null;
    } else {
      return this.add(value.getValue());
    }
  }

  @Override
  public Waveform add(final Complex value) {
    return new ComplexWaveform(this).add(value);
  }

  @Override
  public Waveform add(final Value value) {
    if (value instanceof RealValue) {
      return this.add((RealValue) value);
    } else {
      return this.add(value);
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

    final double[] newX = new double[this.x.length];
    final double[] newY = new double[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i] + wave.y[i];
    }

    return buildRealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  @Override
  public Waveform add(final Waveform wave) {
    if (wave instanceof RealWaveform) {
      return this.add((RealWaveform) wave);
    } else {
      return ((ComplexWaveform) wave).add(this);
    }
  }

  /**
   * Divide a waveform by a value
   *
   * @param value Divisor
   * @return Waveform
   */
  public RealWaveform divide(final double value) {

    final double[] newX = new double[this.x.length];
    final double[] newY = new double[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i] / value;
    }

    return buildRealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  /**
   * Divide a waveform by a value
   *
   * @param value Divisor
   * @return Waveform
   */
  public RealWaveform divide(final BigDecimal value) {
    return this.divide(value.round(MathContext.DECIMAL64).doubleValue());
  }

  /**
   * Divide a waveform by a value
   *
   * @param value Divisor
   * @return Waveform
   */
  public RealWaveform divide(final RealValue value) {

    if (value.isNaN()) {
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
      wave = wave.resample(this.getX());
    }

    final double[] newX = new double[this.x.length];
    final double[] newY = new double[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i] / wave.y[i];
    }

    return buildRealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  @Override
  public RealValue getValue(final double pos) {

    double m;

    if (pos <= this.x[0]) {
      m = (this.y[1] - this.y[0]) / (this.x[1] - this.x[0]);

      return new RealValue(this.y[0] + (m * (pos - this.x[0])),
          this.getUnitY());

    } else if (pos >= this.x[this.x.length - 1]) {

      m = (this.y[this.y.length - 1] - this.y[this.y.length - 2])
          / (this.x[this.x.length - 1] - this.x[this.x.length - 2]);

      return new RealValue(
          this.y[this.y.length - 1] + (m * (pos - this.x[this.x.length - 1])),
          this.getUnitY());
    } else {

      for (int i = 0; i < (this.x.length - 1); i++) {
        if (((this.x[i] - pos) * (this.x[i + 1] - pos)) <= 0) {

          m = (this.y[i + 1] - this.y[i]) / (this.x[i + 1] - this.x[i]);

          return new RealValue(this.y[i] + (m * (pos - this.x[i])),
              this.getUnitY());

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
  public RealValue getValue(final RealValue val) {

    if (val.isNaN()) {
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
  public RealWaveform resample(final double[] newX) {

    final double[] yNew = new double[newX.length];

    for (int i = 0; i < (newX.length - 1); i++) {
      yNew[i] = this.getValue(newX[i]).getValue();
    }

    return new RealWaveform(this.x, yNew, this.getUnitX(), this.getUnitY());
  }

  /**
   * Resample a waveform
   *
   * @param wave x-values of waveform to be used
   * @return resampled waveform
   */
  public RealWaveform resample(final RealWaveform wave) {
    return this.resample(wave.x);
  }

  @Override
  public RealWaveform abs() {

    final double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = Math.abs(this.y[i]);
    }

    return new RealWaveform(this.x, yVec, this.getUnitX(), this.getUnitY());
  }

  /**
   * Exponentiate a waveform
   *
   * @param exponent Exponents
   * @return waveform
   */
  public RealWaveform pow(final double exponent) {

    final double[] pow = new double[this.y.length];

    for (int i = 0; i < pow.length; i++) {
      pow[i] = 10 * Math.pow(this.y[i], exponent);
    }

    return new RealWaveform(this.getX(), pow, this.getUnitX(), "");
  }

  /**
   * Calculate the natural logarithm of a waveform
   *
   * @return Waveform
   */
  public RealWaveform ln() {

    final double[] ln = new double[this.y.length];

    for (int i = 0; i < ln.length; i++) {
      ln[i] = 10 * Math.log(this.y[i]);
    }

    return new RealWaveform(this.getX(), ln, this.getUnitX(), "");
  }

  /**
   * Derive a waveform
   *
   * @return waveform
   */
  public RealWaveform derive() {

    final double y[] = new double[this.x.length];

    y[0] = (this.y[1] - this.y[0]) / (this.x[1] - this.x[0]);

    if (this.x.length > 2) {

      y[this.y.length
          - 1] = (this.y[this.y.length - 1] - this.y[this.y.length - 2])
              / (this.x[this.y.length - 1] - this.x[this.y.length - 2]);

      double a, b;

      for (int i = 1; i < (y.length - 1); i++) {

        a = (((this.y[i - 1] - this.y[i]) * (this.x[i - 1] - this.x[i + 1]))
            - ((this.y[i - 1] - this.y[i + 1]) * (this.x[i - 1] - this.x[i])))
            / (((Math.pow(this.x[i - 1], 2.0) - Math.pow(this.x[i], 2.0))
                * (this.x[i - 1] - this.x[i + 1]))
                - ((Math.pow(this.x[i - 1], 2.0) - Math.pow(this.x[i + 1], 2.0))
                    * (this.x[i - 1] - this.x[i])));

        b = ((this.y[i - 1] - this.y[i]) / (this.x[i - 1] - this.x[i]))
            - ((a * (Math.pow(this.x[i - 1], 2.0) - Math.pow(this.x[i], 2.0)))
                / (this.x[i - 1] - this.x[i]));

        y[i] = (2 * a * this.x[i]) + b;
      }
    }

    return new RealWaveform(this.x, y, this.getUnitX(), "");
  }

  /**
   * Integrate a waveform
   *
   * @return area under the waveform
   */
  public RealValue integrate() {

    double retval = 0;

    for (int i = 1; i < this.x.length; i++) {
      retval += ((this.x[i] - this.x[i - 1]) * (this.y[i] + this.y[i - 1]))
          / 2.0;
    }

    return new RealValue(retval, "");
  }

  @Override
  public RealWaveform db10() {

    final double[] db10 = new double[this.y.length];

    for (int i = 0; i < db10.length; i++) {
      db10[i] = 10 * Math.log10(this.y[i]);
    }

    return new RealWaveform(this.getX(), db10, this.getUnitX(), "");
  }

  @Override
  public RealWaveform db20() {

    final double[] db20 = new double[this.y.length];

    for (int i = 0; i < db20.length; i++) {
      db20[i] = 20 * Math.log10(this.y[i]);
    }

    return new RealWaveform(this.getX(), db20, this.getUnitX(), "");
  }

  /**
   * Calculate the sine of a waveform
   *
   * @return Waveform
   */
  public RealWaveform sin() {

    final double[] sin = new double[this.y.length];

    for (int i = 0; i < sin.length; i++) {
      sin[i] = 10 * Math.sin(this.y[i]);
    }

    return new RealWaveform(this.getX(), sin, this.getUnitX(), "");
  }

  /**
   * Calculate the arc-sine of a waveform
   *
   * @return Waveform
   */
  public RealWaveform asin() {

    final double[] asin = new double[this.y.length];

    for (int i = 0; i < asin.length; i++) {
      asin[i] = 10 * Math.asin(this.y[i]);
    }

    return new RealWaveform(this.getX(), asin, this.getUnitX(), "");
  }

  /**
   * Calculate the cosine of a waveform
   *
   * @return Waveform
   */
  public RealWaveform cos() {

    final double[] cos = new double[this.y.length];

    for (int i = 0; i < cos.length; i++) {
      cos[i] = 10 * Math.sin(this.y[i]);
    }

    return new RealWaveform(this.getX(), cos, this.getUnitX(), "");
  }

  /**
   * Calculate the arc-cosine of a waveform
   *
   * @return Waveform
   */
  public RealWaveform acos() {

    final double[] acos = new double[this.y.length];

    for (int i = 0; i < acos.length; i++) {
      acos[i] = 10 * Math.acos(this.y[i]);
    }

    return new RealWaveform(this.getX(), acos, this.getUnitX(), "");
  }

  /**
   * Calculate the tangent of a waveform
   *
   * @return Waveform
   */
  public RealWaveform tan() {

    final double[] tan = new double[this.y.length];

    for (int i = 0; i < tan.length; i++) {
      tan[i] = 10 * Math.tan(this.y[i]);
    }

    return new RealWaveform(this.getX(), tan, this.getUnitX(), "");
  }

  /**
   * Calculate the arc-tangent of a waveform
   *
   * @return Waveform
   */
  public RealWaveform atan() {

    final double[] atan = new double[this.y.length];

    for (int i = 0; i < atan.length; i++) {
      atan[i] = 10 * Math.atan(this.y[i]);
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
  public RealValue cross(final double val, final int edge) {

    int counter = 1;

    for (int i = 0; i < (this.x.length - 1); i++) {

      if (((this.y[i] - val) * (this.y[i + 1] - val)) <= 0) {

        if (counter == edge) {
          return new RealValue(
              this.x[i] + (((val - this.y[i]) / (this.y[i + 1] - this.y[i]))
                  * (this.x[i + 1] - this.x[i])),
              this.getUnitX());
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
  public RealValue cross(final RealValue val, final int edge) {

    if (val.isNaN()) {
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
  public RealWaveform cross(final double val) {

    final ArrayList<Double> newXVals = new ArrayList<>();
    final ArrayList<Double> newYVals = new ArrayList<>();

    for (int i = 0; i < (this.x.length - 1); i++) {
      if (((this.y[i] - val) * (this.y[i + 1] - val)) <= 0) {
        newXVals
            .add(this.x[i] + (((val - this.y[i]) / (this.y[i + 1] - this.y[i]))
                * (this.x[i + 1] - this.x[i])));
        newYVals.add(val);
      }
    }

    final double[] newX = new double[newXVals.size()];
    final double[] newY = new double[newYVals.size()];

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
  public RealWaveform cross(final RealValue val) {

    if (val.isNaN()) {
      return new RealWaveform();
    } else {
      return this.cross(val.getValue());
    }
  }

  public RealValue getSettlingTime(final double percentage) {

    final RealValue stopValue = this.getValue(this.xmax());

    final RealWaveform crossLower = this
        .cross(stopValue.getValue() * (1 - (percentage / 2)));
    final RealWaveform crossUpper = this
        .cross(stopValue.getValue() * (1 + (percentage / 2)));

    double retval = Double.NaN;

    for (int i = 0; i < crossLower.getX().length; i++) {
      retval = Math.max(retval, i);
    }

    for (int i = 0; i < crossUpper.getX().length; i++) {
      retval = Math.max(retval, i);
    }

    return new RealValue(retval - this.xmin().getValue(), "s");

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

    for (final double element : this.y) {
      min = Math.min(element, min);
    }

    return new RealValue(min, this.getUnitY());
  }

  /**
   * Get the maximal y-value of a waveform
   *
   * @return maximal y-value
   */
  public RealValue ymax() {

    double max = Double.MIN_VALUE;

    for (final double element : this.y) {
      max = Math.max(element, max);
    }

    return new RealValue(max, this.getUnitY());
  }

  /**
   * Concatenate waveform
   *
   * @param wave Waveform to be concatenated
   * @return Waveform
   */
  public RealWaveform concat(final RealWaveform wave) {

    final double[] newX = new double[this.x.length + wave.x.length];
    final double[] newY = new double[this.y.length + wave.y.length];

    for (int i = 0; i < this.x.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i];
    }

    for (int i = 0; i < wave.x.length; i++) {
      newX[i + this.x.length] = wave.x[i];
      newY[i + this.y.length] = wave.y[i];
    }

    sortWaveElements(newX, newY);

    return new RealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  /**
   * Check if this waveform is lower or equal than this waveform
   * 
   * @param wave waveform
   * @return <code>true</code> when this waveform is lower or equal than the
   *         other waveform, <code>false</code> otherwise
   */
  public boolean leq(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this.x);
    }

    for (int i = 0; i < this.x.length; i++) {
      if (this.y[i] > wave.y[i]) {
        return false;
      }
    }

    return true;
  }

  /**
   * Check if this waveform is less than this waveform
   * 
   * @param wave waveform
   * @return <code>true</code> when this waveform is less than the other,
   *         <code>false</code> otherwise
   */
  public boolean less(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this.x);
    }

    for (int i = 0; i < this.x.length; i++) {
      if (this.y[i] >= wave.y[i]) {
        return false;
      }
    }

    return true;
  }

  /**
   * Check if this waveform is greater or equal than the other waveform
   * 
   * @param wave waveform
   * @return <code>true</code> when this waveform is greater or equal than the
   *         other waveform, <code>false</code> otherwise
   */
  public boolean geq(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this.x);
    }

    for (int i = 0; i < this.x.length; i++) {
      if (this.y[i] < wave.y[i]) {
        return false;
      }
    }

    return true;
  }

  /**
   * Check if this waveform is greater than the other waveform
   * 
   * @param wave waveform
   * @return <code>true</code> when this waveform is less than the other,
   *         <code>false</code> otherwise
   */
  public boolean greater(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this.x);
    }

    for (int i = 0; i < this.x.length; i++) {
      if (this.y[i] <= wave.y[i]) {
        return false;
      }
    }

    return true;
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
  public static RealWaveform buildRealWaveform(final double[] x,
      final double[] y, final String unitX, final String unitY) {

    if (x.length == y.length) {

      sortWaveElements(x, y);

      return new RealWaveform(x, y, unitX, unitY);

    } else {
      System.err.println("Length of arrays do not match");
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
  private static void sortWaveElements(final double[] x, final double[] y) {

    double swap;
    boolean swapPerformed = true;

    while (swapPerformed) {

      swapPerformed = false;

      for (int i = 0; i < (x.length - 1); i++) {

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

  @Override
  public RealWaveform real() {
    return this;
  }

  @Override
  public RealWaveform imag() {

    final double[] x = new double[this.x.length];
    final double[] y = new double[this.y.length];

    for (int i = 0; i < this.x.length; i++) {
      x[i] = this.x[i];
      y[i] = 0.0;
    }

    return buildRealWaveform(x, y, this.getUnitX(), this.getUnitY());
  }

  @Override
  public RealWaveform phaseDeg() {

    final double[] x = new double[this.x.length];
    final double[] y = new double[this.y.length];

    for (int i = 0; i < this.x.length; i++) {
      x[i] = this.x[i];

      if (y[i] >= 0) {
        y[i] = 0.0;
      } else {
        y[i] = 180.0;
      }
    }

    return buildRealWaveform(x, y, this.getUnitX(), this.getUnitY());
  }

  @Override
  public RealWaveform conjugate() {

    final double[] x = new double[this.x.length];
    final double[] y = new double[this.y.length];

    for (int i = 0; i < this.x.length; i++) {
      x[i] = this.x[i];
      y[i] = this.y[i];
    }

    return buildRealWaveform(x, y, this.getUnitX(), this.getUnitY());
  }

  @Override
  public Waveform uminus() {

    final double[] x = new double[this.x.length];
    final double[] y = new double[this.y.length];

    for (int i = 0; i < this.x.length; i++) {
      x[i] = this.x[i];
      y[i] = -this.y[i];
    }

    return buildRealWaveform(x, y, this.getUnitX(), this.getUnitY());
  }

  @Override
  public Waveform uplus() {
    return this;
  }

  @Override
  public Waveform subtract(Waveform subtrahed) {

    if (subtrahed instanceof RealWaveform) {
      return this.subtract(((RealWaveform) subtrahed));
    } else {
      return this.subtract(((ComplexWaveform) subtrahed));
    }
  }

  /**
   * Subtract a {@link RealWaveform} from the waveform
   * 
   * @param subtrahend wave to be subtracted
   * @return wave
   */
  public RealWaveform subtract(RealWaveform subtrahend) {

    if (!this.sameAxis(subtrahend)) {
      subtrahend = subtrahend.resample(this);
    }

    final double[] newX = new double[this.x.length];
    final double[] newY = new double[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i] - subtrahend.y[i];
    }

    return buildRealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  /**
   * Subtract a {@link ComplexWaveform} from the waveform
   * 
   * @param subtrahend wave to be subtracted
   * @return wave
   */
  public ComplexWaveform subtract(ComplexWaveform subtrahend) {
    return new ComplexWaveform(this).subtract(subtrahend);
  }

  @Override
  public RealWaveform subtract(final double value) {

    final double[] newX = new double[this.x.length];
    final double[] newY = new double[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i] - value;
    }

    return buildRealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  @Override
  public RealWaveform subtract(final BigDecimal value) {
    return this.subtract(value.round(MathContext.DECIMAL64).doubleValue());
  }

  @Override
  public ComplexWaveform subtract(Complex subtrahed) {
    return new ComplexWaveform(this).subtract(subtrahed);
  }

  @Override
  public Waveform subtract(Value subtrahed) {

    if (subtrahed instanceof RealValue) {
      this.subtract((RealValue) subtrahed);
    } else {
      return new ComplexWaveform(this).subtract((ComplexValue) subtrahed);
    }

    return null;
  }

  /**
   * Subtract a {@link RealValue} from the waveform
   * 
   * @param subtrahend value to be subtracted
   * @return wave
   */
  public RealWaveform subtract(final RealValue subtrahend) {

    if (subtrahend.isNaN()) {
      return new RealWaveform();
    } else {
      return this.subtract(subtrahend.getValue());
    }
  }

  @Override
  public RealWaveform multiply(final double factor) {

    final double[] newX = new double[this.x.length];
    final double[] newY = new double[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i] * factor;
    }

    return new RealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  @Override
  public RealWaveform multiply(final BigDecimal factor) {
    return this.multiply(factor.round(MathContext.DECIMAL64).doubleValue());
  }

  /**
   * Multiply a waveform with a {@link RealValue}
   *
   * @param factor value to be multiplied
   * @return wave
   */
  public RealWaveform multiply(final RealValue factor) {

    if (factor.isNaN()) {
      return null;
    } else {
      return this.multiply(factor.getValue());
    }
  }

  @Override
  public ComplexWaveform multiply(Complex factor) {
    return new ComplexWaveform(this).multiply(factor);
  }

  @Override
  public Waveform multiply(Value factor) {
    if (factor instanceof RealValue) {
      return this.multiply((RealValue) factor);
    } else {
      return this.multiply((ComplexValue) factor);
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
      wave = wave.resample(this.getX());
    }

    final double[] newX = new double[this.x.length];
    final double[] newY = new double[this.y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = this.x[i];
      newY[i] = this.y[i] * wave.y[i];
    }

    return new RealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  @Override
  public Waveform multiply(Waveform factor) {

    if (factor instanceof RealWaveform) {
      return this.multiply((RealWaveform) factor);
    } else {
      return new ComplexWaveform(this).multiply(factor);
    }
  }

  @Override
  public boolean isEmpty() {
    return this.x == null || this.y == null || this.x.length == 0
        || this.y.length == 0;
  }

  /**
   * Check if an object is an instance of this class
   *
   * @param o Object
   * @return <code>true</code> if the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(final Object o) {
    return o instanceof RealWaveform;
  }
}