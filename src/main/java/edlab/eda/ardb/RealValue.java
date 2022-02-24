package edlab.eda.ardb;

/**
 * Representation of a real value
 */
public final class RealValue extends Value {

  private double value;

  /**
   * Create a new real value
   *
   * @param value Value
   * @param unit  Unit
   */
  public RealValue(final double value, final String unit) {
    super(unit);
    this.value = value;
  }

  /**
   * Create a real value with NaN
   */
  public RealValue() {
    super();
    this.value = Double.NaN;
  }

  /**
   * Check if this value is lower or equal than another value
   *
   * @param value Value that is used as reference
   * @return <code>true</code> when this value is smaller or equal than the
   *         parameter, <code>false</code> otherwise
   */
  public boolean leq(final RealValue value) {

    if (!this.isNaN()) {
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
  public boolean leq(final double value) {
    if (!this.isNaN()) {
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
  public boolean geq(final RealValue value) {

    if (!this.isNaN()) {
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
  public boolean geq(final double value) {

    if (!this.isNaN()) {
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
  public RealValue add(final RealValue value) {
    if ((!this.isNaN()) && (!this.isNaN())) {
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
  public RealValue add(final double value) {
    if ((!this.isNaN()) && (value != Double.NaN)) {
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
  public RealValue subtract(final RealValue value) {
    if ((!this.isNaN()) && (!this.isNaN())) {
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
  public RealValue subtract(final double value) {
    if ((!this.isNaN()) && (value != Double.NaN)) {
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
  public RealValue multiply(final RealValue value) {
    if ((!this.isNaN()) && (!this.isNaN())) {
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
  public RealValue multiply(final double value) {
    if ((!this.isNaN()) && (value != Double.NaN)) {
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
  public RealValue divide(final RealValue value) {
    if (!this.isNaN() && !value.isNaN() && (value.getValue() != 0.0)) {
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
  public RealValue divide(final double value) {
    if (!this.isNaN() && (value != Double.NaN) && (value != 0.0)) {
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
    return this.value;
  }

  /**
   * Flip the sign of the value
   *
   * @return <code>true</code> when the sign is changed successfully,
   *         <code>false</code> otherwise
   */
  public boolean flipSign() {
    if (!this.isNaN()) {
      this.value = -this.value;
    }
    return true;
  }

  @Override
  public String toString() {
    if (this.getUnit() == null) {
      return Formatter.format(this.getValue());
    } else {
      return Formatter.format(this.getValue()) + " " + this.getUnit();
    }
  }

  @Override
  public boolean isNaN() {
    return Double.isNaN(this.value);
  }

  /**
   * Check if an object is an instance of this class
   *
   * @param o Object
   * @return <code>true</code> if the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(final Object o) {
    return o instanceof RealValue;
  }
}