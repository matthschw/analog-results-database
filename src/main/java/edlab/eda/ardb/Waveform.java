package edlab.eda.ardb;

public abstract class Waveform {

  private String name = null;
  private String unitX;
  private String unitY;
  protected double[] x;
  protected boolean invalid = false;

  public Waveform(double[] x, String unitX, String unitY) {
    this.name = "";
    this.x = x;
    this.unitX = unitX;
    this.unitY = unitY;
  }

  public Waveform(String name, double[] x, String unitX, String unitY) {
    this.name = name;
    this.x = x;
    this.unitX = unitX;
    this.unitY = unitY;
  }

  public Waveform() {
    this.invalid = true;
  }

  public String getUnitX() {
    return unitX;
  }

  public String getUnitY() {
    return unitY;
  }

  public String getName() {
    return name;
  }

  public double[] getX() {
    return x;
  }

  public boolean isInvalid() {
    return invalid;
  }

  public RealValue xmin() {

    return new RealValue(x[0], getUnitX());
  }

  public RealValue xmax() {
    return new RealValue(x[x.length - 1], getUnitX());
  }

  public abstract int noOfVals();

  public abstract Value getValue(double pos);

  public abstract RealWaveform abs();

  public abstract RealWaveform db20();

  public abstract Value cross(double val);

}