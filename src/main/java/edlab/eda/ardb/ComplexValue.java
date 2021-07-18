package edlab.eda.ardb;

import org.apache.commons.math3.complex.Complex;

public class ComplexValue extends Value {

  private Complex value;

  public ComplexValue(Complex value, String unit) {
    super(unit);
    this.value = value;
  }

  public Complex getValue() {
    return value;
  }

  public String toString() {
    if (getUnit() == null) {
      return "" + getValue().getReal() + " + j*" + getValue().getImaginary();
    } else {
      return "" + getValue().getReal() + " + j*" + getValue().getImaginary()
          + " " + getUnit();
    }
  }
}