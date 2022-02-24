package edlab.eda.ardb;

import org.apache.commons.math3.complex.Complex;

/**
 * Class for representing a complex values Both value and unit are covered by
 * the object.
 */
public final class ComplexValue extends Value {

  private final Complex value;

  /**
   * Constructor
   *
   * @param value of the ComplexValue
   * @param unit  of the ComplexValue
   */
  public ComplexValue(final Complex value, final String unit) {
    super(unit);
    this.value = value;
  }

  public ComplexValue() {
    super();
    this.value = Complex.NaN;
  }

  /**
   * Returns the value of a ComplexValue
   *
   * @return Complex
   */
  public Complex getValue() {
    return this.value;
  }

  /**
   * Returns the real part of a ComplexValue
   *
   * @return Real part (double)
   */
  public double real() {
    return this.value.getReal();
  }

  /**
   * Returns the imaginary part of a ComplexValue
   *
   * @return Imaginary part (double)
   */
  public double imag() {
    return this.value.getImaginary();
  }

  /**
   * Returns the conjugate of a ComplexValue
   *
   * @return Complex
   */
  public Complex conjugate() {
    return this.value.conjugate();
  }

  @Override
  public String toString() {
    if (this.getUnit() == null) {
      return "" + this.getValue().getReal() + " + j*"
          + this.getValue().getImaginary();
    } else {
      return "" + this.getValue().getReal() + " + j*"
          + this.getValue().getImaginary() + " " + this.getUnit();
    }
  }

  @Override
  public boolean isNaN() {
    return this.value.isNaN();
  }

  /**
   * Check if an object is an instance of this class
   *
   * @param o Object
   * @return <code>true</code> if the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(final Object o) {
    return o instanceof ComplexValue;
  }
}