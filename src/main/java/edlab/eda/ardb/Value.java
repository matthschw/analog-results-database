package edlab.eda.ardb;

public abstract class Value {

  private String unit;
  private boolean invalid = false;

  public Value(String unit) {
    this.unit = unit;
  }

  public Value() {
    this.invalid = true;
  }

  public String getUnit() {
    return unit;
  }

  public boolean isInvalid() {
    return invalid;
  }

  /**
   * Identify whether an object is an instance of this class
   * 
   * @param o Object to be checked
   * @return <code>true</code> when the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(Object o) {
    return o instanceof Value;
  }
}