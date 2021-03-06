package edlab.eda.ardb;

import java.math.BigDecimal;

import org.apache.commons.math3.complex.Complex;

/**
 * Class for representing a waveform
 */
public abstract class Waveform {

  private String name = "";
  private final String unitX;
  private final String unitY;

  protected final double[] x;

  protected Waveform(final double[] x, final String unitX, final String unitY) {
    this.name = "";
    this.x = x;
    this.unitX = unitX;
    this.unitY = unitY;
  }

  /**
   * Create an empty waveform
   */
  public Waveform() {
    this.x = new double[0];
    this.unitX = "";
    this.unitY = "";
  }

  /**
   * Get unit of x-axis
   *
   * @return unit of x-axis
   */
  public String getUnitX() {
    return this.unitX;
  }

  /**
   * Get unit of y-axis
   *
   * @return unit of y-axis
   */
  public String getUnitY() {
    return this.unitY;
  }

  /**
   * Get name of waveform
   *
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the x-values of the waveform
   *
   * @return x-values
   */
  public double[] getX() {
    return this.x;
  }

  /**
   * Get the minimal x-value
   *
   * @return minimal x-value
   */
  public RealValue xmin() {
    return new RealValue(this.x[0], this.getUnitX());
  }

  /**
   * Get the maximal x-value
   *
   * @return maximal x-value
   */
  public RealValue xmax() {
    return new RealValue(this.x[this.x.length - 1], this.getUnitX());
  }

  /**
   * Get the number of points in the waveform
   *
   * @return number of points
   */
  public int noOfVals() {
    return this.x.length;
  }

  /**
   * Clip a waveform to a range
   *
   * @param left  Left boundary of range
   * @param right Right boundary of range
   * @return Clipped waveform
   */
  public abstract Waveform clip(final double left, final double right);

  /**
   * Check if the x-values of two waveforms are equal
   *
   * @param wave Waveform to be compared
   * @return <code>true</code> if the x-values are equal <code>false</code>
   *         otherwise
   */
  public boolean sameAxis(final Waveform wave) {

    if (this.x.length == wave.x.length) {

      for (int i = 0; i < this.x.length; i++) {
        if (this.x[i] != wave.x[i]) {
          return false;
        }
      }

      return true;

    }

    return false;
  }

  /**
   * Check if the waveform in valid
   * 
   * @return <code>true</code> when the waveform in valid, <code>false</code>
   *         otherwise
   */
  public abstract boolean isEmpty();

  /**
   * Calculate the complex conjugate of a waveform
   *
   * @return Waveform
   */
  public abstract Waveform conjugate();

  /**
   * Evaluate a waveform at a value
   *
   * @param pos x-value where the waveform is evaluated
   * @return value
   */
  public abstract Value getValue(double pos);

  /**
   * Resample a waveform
   *
   * @param newX New x-values to be used
   * @return resampled waveform
   */
  public abstract Waveform resample(final double[] newX);

  /**
   * Extract the real-part of a waveform
   *
   * @return Waveform
   */
  public abstract RealWaveform real();

  /**
   * Extract the imag-part of a waveform
   *
   * @return Waveform
   */
  public abstract RealWaveform imag();

  /**
   * Extract the phase of a {@link ComplexWaveform}
   *
   * @return Waveform
   */
  public abstract RealWaveform phaseDeg();

  /**
   * Calculate the absolute value of a waveform
   *
   * @return Waveform
   */
  public abstract RealWaveform abs();

  /**
   * Calculate db10 of a waveform
   *
   * @return Waveform
   */
  public abstract RealWaveform db10();

  /**
   * Calculate db10 of a waveform
   *
   * @return Waveform
   */
  public abstract RealWaveform db20();

  /**
   * Identify if the y values of this waveform are less than of another waveform
   * 
   * @param wave Waveform reference
   * @return <code>true</code> when valid, <code>false</code> otherwise
   */
  public boolean lt(final Waveform wave) {
    return this.lessThan(wave);
  }

  /**
   * Identify if the y values of this waveform are less than of another waveform
   * 
   * @param wave Waveform reference
   * @return <code>true</code> when valid, <code>false</code> otherwise
   */
  public abstract boolean lessThan(final Waveform wave);

  /**
   * Identify if the y values of this waveform are greater than of another
   * waveform
   * 
   * @param wave Waveform reference
   * @return <code>true</code> when valid, <code>false</code> otherwise
   */
  public boolean gt(final Waveform wave) {
    return this.greaterThan(wave);
  }

  /**
   * Identify if the y values of this waveform are greater than of another
   * waveform
   * 
   * @param wave Waveform reference
   * @return <code>true</code> when valid, <code>false</code> otherwise
   */
  public abstract boolean greaterThan(final Waveform wave);

  /**
   * Identify if the y values of this waveform are less than or equals of
   * another waveform
   * 
   * @param wave Waveform reference
   * @return <code>true</code> when valid, <code>false</code> otherwise
   */
  public abstract boolean lessThanOrEqualTo(final Waveform wave);

  /**
   * Identify if the y values of this waveform are greater than or equal of
   * another waveform
   * 
   * @param wave Waveform reference
   * @return <code>true</code> when valid, <code>false</code> otherwise
   */
  public abstract boolean greaterThanOrEqualTo(final Waveform wave);

  /**
   * Unary minus of the waveform
   *
   * @return wave
   */
  public abstract Waveform uminus();

  /**
   * Unary plus of the waveform
   *
   * @return wave
   */
  public abstract Waveform uplus();

  /**
   * Add two waveforms together
   *
   * @param wave waveform to be added
   * @return sum
   */
  public abstract Waveform add(final Waveform wave);

  /**
   * Add a constant to a waveform
   *
   * @param value value to be added
   * @return wave
   */
  public abstract Waveform add(final double value);

  /**
   * Add a constant to a waveform
   *
   * @param value value to be added
   * @return wave
   */
  public abstract Waveform add(final BigDecimal value);

  /**
   * Add a constant to a waveform
   *
   * @param value value to be added
   * @return wave
   */
  public abstract Waveform add(final Complex value);

  /**
   * Add a {@link RealValue} to a waveform
   *
   * @param value value to be added
   * @return wave
   */
  public abstract Waveform add(final Value value);

  /**
   * Subtract two waveforms
   *
   * @param subtrahed waveform to be subtracted
   * @return difference
   */
  public abstract Waveform subtract(final Waveform subtrahed);

  /**
   * Subtract a constant from a waveform
   *
   * @param subtrahed value to be subtracted
   * @return difference
   */
  public abstract Waveform subtract(final double subtrahed);

  /**
   * Subtract a constant from a waveform
   *
   * @param subtrahed value to be subtracted
   * @return difference
   */
  public abstract Waveform subtract(final BigDecimal subtrahed);

  /**
   * Subtract a constant from a waveform
   *
   * @param subtrahed value to be subtracted
   * @return difference
   */
  public abstract Waveform subtract(final Complex subtrahed);

  /**
   * Subtract a {@link RealValue} from a waveform
   *
   * @param subtrahed value to be subtracted
   * @return difference
   */
  public abstract Waveform subtract(final Value subtrahed);

  /**
   * Multiply two waveforms
   *
   * @param factor waveform to be multiplied
   * @return product
   */
  public abstract Waveform multiply(final Waveform factor);

  /**
   * Multiply a constant with a waveform
   *
   * @param factor waveform to be multiplied
   * @return difference
   */
  public abstract Waveform multiply(final double factor);

  /**
   * Multiply a constant with a waveform
   *
   * @param factor value to be multiplied
   * @return product
   */
  public abstract Waveform multiply(final BigDecimal factor);

  /**
   * Multiply a constant with a waveform
   *
   * @param factor value to be multiplied
   * @return product
   */
  public abstract Waveform multiply(final Complex factor);

  /**
   * Subtract a {@link RealValue} from a waveform
   *
   * @param factor value to be multiplied
   * @return product
   */
  public abstract Waveform multiply(final Value factor);

  /**
   * Divide a waveform through a {@link Waveform}
   *
   * @param divisor waveform to be used as divisor
   * @return quotient
   */
  public abstract Waveform divide(final Waveform divisor);

  /**
   * Divide a waveform through a double
   *
   * @param divisor value to be used as divisor
   * @return quotient
   */
  public abstract Waveform divide(final double divisor);

  /**
   * Divide a waveform through a {@link BigDecimal}
   *
   * @param divisor value to be used as divisor
   * @return quotient
   */
  public abstract Waveform divide(final BigDecimal divisor);

  /**
   * Divide a waveform through a {@link Complex}
   *
   * @param divisor value to be used as divisor
   * @return quotient
   */
  public abstract Waveform divide(final Complex divisor);

  /**
   * Divide a waveform through a {@link RealValue}
   *
   * @param divisor value to be used as divisor
   * @return quotient
   */
  public abstract Waveform divide(final Value divisor);

  /**
   * Divide a waveform through a {@link RealValue}
   *
   * @param divisor value to be used as divisor
   * @return quotient
   */
  public abstract Waveform divide(final RealValue divisor);

  /**
   * Divide a waveform through a {@link ComplexValue}
   *
   * @param divisor value to be used as divisor
   * @return quotient
   */
  public abstract Waveform divide(final ComplexValue divisor);

  /**
   * Create a waveform with the same x-axis but a constant y value
   * 
   * @param value y value
   * @return wave
   */
  public Waveform createConstantWave(final Value value) {

    if (value instanceof RealValue) {
      return this.createConstantWave((RealValue) value);
    } else if (value instanceof ComplexValue) {
      return this.createConstantWave((ComplexValue) value);
    }

    return null;
  }

  /**
   * Create a waveform with the same x-axis but a constant y value
   * 
   * @param value y value
   * @return wave
   */
  public Waveform createConstantWave(final RealValue value) {

    final double[] x = this.getX();

    final double[] y = new double[x.length];

    for (int i = 0; i < y.length; i++) {
      y[i] = value.getValue();
    }

    return RealWaveform.buildRealWaveform(x, y, this.unitX, value.getUnit());
  }

  /**
   * Create a waveform with the same x-axis but a constant y value
   * 
   * @param value y value
   * @return wave
   */
  public Waveform createConstantWave(final ComplexValue value) {

    final double[] x = this.getX();

    final Complex[] y = new Complex[x.length];

    for (int i = 0; i < y.length; i++) {
      y[i] = value.getValue();
    }

    return ComplexWaveform.buildComplexWaveform(x, y, this.unitX,
        value.getUnit());
  }

  /**
   * Create a waveform with the same x-axis but a constant y value
   * 
   * @param value y value
   * @return wave
   */
  public Waveform createConstantWave(final BigDecimal value) {

    final double[] x = this.getX();

    final double[] y = new double[x.length];

    for (int i = 0; i < y.length; i++) {
      y[i] = value.doubleValue();
    }

    return RealWaveform.buildRealWaveform(x, y, this.unitX, "");
  }

  /**
   * Create a waveform with the same x-axis but a constant y value
   * 
   * @param value y value
   * @return wave
   */
  public Waveform createConstantWave(final double value) {
    final double[] x = this.getX();

    final double[] y = new double[x.length];

    for (int i = 0; i < y.length; i++) {
      y[i] = value;
    }

    return RealWaveform.buildRealWaveform(x, y, this.unitX, "");
  }

  /**
   * Create a waveform with the same x-axis but a constant y value
   * 
   * @param value y value
   * @return wave
   */
  public Waveform createConstantWave(final Complex value) {

    final double[] x = this.getX();

    final Complex[] y = new Complex[x.length];

    for (int i = 0; i < y.length; i++) {
      y[i] = value;
    }

    return ComplexWaveform.buildComplexWaveform(x, y, this.unitX, "");
  }

  /**
   * Identify whether an object is an instance of this class
   *
   * @param o Object to be checked
   * @return <code>true</code> when the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(final Object o) {
    return o instanceof Waveform;
  }
}