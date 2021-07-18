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

  public abstract int noOfVals();

  public abstract Value getValue(double pos);

  public abstract RealWaveform abs();

  public abstract RealWaveform db20();

  public abstract Value cross(double val);

}