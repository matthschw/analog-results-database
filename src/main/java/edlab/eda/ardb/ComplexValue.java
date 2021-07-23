package edlab.eda.ardb;

import org.apache.commons.math3.complex.Complex;

/**
 * Class for representing a complex values Both value and unit are covered by
 * the object.
 */
public class ComplexValue extends Value {

  private Complex value;

  /**
   * Constructor
   * 
   * @param value of the ComplexValue
   * @param unit  of the ComplexValue
   */
  public ComplexValue(Complex value, String unit) {
    super(unit);
    this.value = value;
  }

  /**
   * Returns the value of a ComplexValue
   * 
   * @return Complex
   */
  public Complex getValue() {
    return value;
  }

  /**
   * Returns the real part of a ComplexValue
   * 
   * @return Real part (double)
   */
  public double real() {
    return value.getReal();
  }

  /**
   * Returns the imaginary part of a ComplexValue
   * 
   * @return Imaginary part (double)
   */
  public double imag() {
    return value.getImaginary();
  }

  /**
   * Returns the conjugate of a ComplexValue
   *  @return Complex
   */
  public Complex conjugate() {
    return value.conjugate();
  }

  
  /**
   * Provides a String-Representation if the ComplexValue
   */
  public String toString() {
    if (getUnit() == null) {
      return "" + getValue().getReal() + " + j*" + getValue().getImaginary();
    } else {
      return "" + getValue().getReal() + " + j*" + getValue().getImaginary()
          + " " + getUnit();
    }
  }
}