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
}