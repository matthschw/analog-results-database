package edlab.eda.ardb;

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
   * Create a real value with Nan
   */
  public RealValue() {
    super();
  }

  /**
   * Get the value
   * 
   * @return value
   */
  public double getValue() {
    if (isInvalid()) {
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
    if (!isInvalid()) {
      this.value = -value;
    }
    return true;
  }

  @Override
  public String toString() {
    if (getUnit() == null) {
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