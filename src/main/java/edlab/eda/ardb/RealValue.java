package edlab.eda.ardb;

/**
 * Representation of a real value
 */
public class RealValue extends Value {

  private double value;

  /**
   * Create a new real value
   * 
   * @param value Value
   * @param unit  Unit
   */
  public RealValue(double value, String unit) {
    super(unit);
    this.value = value;
  }

  /**
   * Create a real value with NaN
   */
  public RealValue() {
    super();
  }

  /**
   * Check if this value is lower or equal than another value
   * 
   * @param value Value that is used as reference
   * @return <code>true</code> when this value is smaller or equal than the
   *         parameter, <code>false</code> otherwise
   */
  public boolean leq(RealValue value) {

    if (this.isValid()) {
      return this.value <= value.value;
    } else {
      return false;
    }
  }

  /**
   * Check if this value is lower or equal than another value
   * 
   * @param value Value that is used as reference
   * @return <code>true</code> when this value is smaller or equals than the
   *         parameter, <code>false</code> otherwise
   */
  public boolean leq(double value) {
    if (this.isValid()) {
      return this.value <= value;
    } else {
      return false;
    }
  }

  /**
   * Check if this value is greater or equal than another value
   * 
   * @param value Value that is used as reference
   * @return <code>true</code> when this value is greater or equal than the
   *         parameter, <code>false</code> otherwise
   */
  public boolean geq(RealValue value) {

    if (this.isValid()) {
      return this.value >= value.value;
    } else {
      return false;
    }
  }

  /**
   * Check if this value is greater or equal than another value
   * 
   * @param value Value that is used as reference
   * @return <code>true</code> when this value is greater or equal than the
   *         parameter, <code>false</code> otherwise
   */
  public boolean geq(double value) {

    if (this.isValid()) {
      return this.value >= value;
    } else {
      return false;
    }
  }

  /**
   * Add a real value
   * 
   * @param value summand
   * @return sum
   */
  public RealValue add(RealValue value) {
    if (this.isValid() && value.isValid()) {
      return new RealValue(this.getValue() + value.getValue(), this.getUnit());
    } else {
      return new RealValue();
    }
  }

  /**
   * Add a double
   * 
   * @param value summand
   * @return sum
   */
  public RealValue add(double value) {
    if (this.isValid() && value != Double.NaN) {
      return new RealValue(this.getValue() + value, this.getUnit());
    } else {
      return new RealValue();
    }
  }

  /**
   * Subtract a real value
   * 
   * @param value subtrahend
   * @return difference
   */
  public RealValue subtract(RealValue value) {
    if (this.isValid() && value.isValid()) {
      return new RealValue(this.getValue() - value.getValue(), this.getUnit());
    } else {
      return new RealValue();
    }
  }

  /**
   * Subtract a double
   * 
   * @param value subtrahend
   * @return difference
   */
  public RealValue subtract(double value) {
    if (this.isValid() && value != Double.NaN) {
      return new RealValue(this.getValue() - value, this.getUnit());
    } else {
      return new RealValue();
    }
  }

  /**
   * Multiply with a real value
   * 
   * @param value multiplier
   * @return product
   */
  public RealValue multiply(RealValue value) {
    if (this.isValid() && value.isValid()) {
      return new RealValue(this.getValue() * value.getValue(), this.getUnit());
    } else {
      return new RealValue();
    }
  }

  /**
   * Multiply with a double
   * 
   * @param value multiplier
   * @return product
   */
  public RealValue multiply(double value) {
    if (this.isValid() && value != Double.NaN) {
      return new RealValue(this.getValue() * value, this.getUnit());
    } else {
      return new RealValue();
    }
  }

  /**
   * Divide through a real value
   * 
   * @param value divisor
   * @return quotient
   */
  public RealValue divide(RealValue value) {
    if (this.isValid() && value.isValid() && value.getValue() != 0.0) {
      return new RealValue(this.getValue() / value.getValue(), this.getUnit());
    } else {
      return new RealValue();
    }
  }

  /**
   * Divide through a double
   * 
   * @param value divisor
   * @return quotient
   */
  public RealValue divide(double value) {
    if (this.isValid() && value != Double.NaN && value != 0.0) {
      return new RealValue(this.getValue() / value, this.getUnit());
    } else {
      return new RealValue();
    }
  }

  /**
   * Get the value
   * 
   * @return value
   */
  public double getValue() {
    if (this.isInvalid()) {
      return Double.NaN;
    } else {
      return this.value;
    }
  }

  /**
   * Flip the sign of the value
   * 
   * @return <code>true</code> when the sign is changed successfully,
   *         <code>false</code> otherwise
   */
  public boolean flipSign() {
    if (this.isValid()) {
      this.value = -value;
    }
    return true;
  }

  @Override
  public String toString() {
    if (this.getUnit() == null) {
      return "" + getValue();
    } else {
      return getValue() + " " + getUnit();
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
    return o instanceof RealValue;
  }
}