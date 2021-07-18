package edlab.eda.ardb;

public class RealValue extends Value {

  private double value;

  public RealValue(double value, String unit) {
    super(unit);
    this.value = value;
  }

  public RealValue() {
    super();
  }

  public double getValue() {
    if (isInvalid()) {
      return Double.NaN;
    } else {
      return value;
    }
  }

  public void flipSign() {
    if (!isInvalid()) {
      this.value = -value;
    }
  }

  public String toString() {
    if (getUnit() == null) {
      return "" + getValue();
    } else {
      return getValue() + " " + getUnit();
    }
  }
}