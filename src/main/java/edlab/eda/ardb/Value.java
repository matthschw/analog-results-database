package edlab.eda.ardb;

/**
 * Representation of a value
 */
public abstract class Value {

  private final String unit;

  public Value(final String unit) {
    this.unit = unit;
  }

  /**
   * Get an invalid value
   */
  public Value() {
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
   * Is the value not a number
   * 
   * @return <code>true</code> when the value is not a number,
   *         <code>false</code> otherwise
   */
  public abstract boolean isNaN();

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