package edlab.eda.ardb;

/**
 * Representation of a value
 */
public abstract class Value {

  private final String unit;
  private boolean invalid = false;

  public Value(final String unit) {
    this.unit = unit;
  }

  /**
   * Get an invalid value
   */
  public Value() {
    this.invalid = true;
    this.unit = "";
  }

  /**
   * Get the unit of the value
   *
   * @return unit
   */
  public String getUnit() {
    return this.unit;
  }

  /**
   * Check if the value is valid
   *
   * @return <code>true</code> when the value is valid, <code>false</code>
   *         otherwise
   */
  public boolean isValid() {
    return !this.invalid;
  }

  public boolean isInvalid() {
    return this.invalid;
  }

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