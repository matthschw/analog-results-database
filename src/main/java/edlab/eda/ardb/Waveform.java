package edlab.eda.ardb;

import java.math.BigDecimal;

import org.apache.commons.math3.complex.Complex;

/**
 * Class for representing a waveform
 */
public abstract class Waveform {

  private String name = null;
  private String unitX;
  private String unitY;
  protected double[] x;
  protected boolean invalid = false;

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
    this.invalid = true;
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
   * Check if a waveform is valid (=non-empty)
   *
   * @return validity of the waveform
   */
  public boolean isInvalid() {
    return this.invalid;
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

    } else {

      return false;
    }
  }

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
   * Identify whether an object is an instance of this class
   *
   * @param o Object to be checked
   * @return <code>true</code> when the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(final Object o) {
    return o instanceof Value;
  }
}