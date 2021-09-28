package edlab.eda.ardb;

/**
 * Class for representing a waveform
 */
public abstract class Waveform {

  private String name = null;
  private String unitX;
  private String unitY;
  protected double[] x;
  protected boolean invalid = false;

  protected Waveform(double[] x, String unitX, String unitY) {
    this.name = "";
    this.x = x;
    this.unitX = unitX;
    this.unitY = unitY;
  }

  /**
   * Create an empty waveform
   */
  public Waveform() {
    this.invalid = true;
  }

  /**
   * Get unit of x-axis
   * 
   * @return unit of x-axis
   */
  public String getUnitX() {
    return unitX;
  }

  /**
   * Get unit of y-axis
   * 
   * @return unit of y-axis
   */
  public String getUnitY() {
    return unitY;
  }

  /**
   * Get name of waveform
   * 
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the x-values of the waveform
   * 
   * @return x-values
   */
  public double[] getX() {
    return x;
  }

  /**
   * Check if a waveform is valid (=non-empty)
   * 
   * @return validity of the waveform
   */
  public boolean isInvalid() {
    return invalid;
  }

  /**
   * Get the minimal x-value
   * 
   * @return minimal x-value
   */
  public RealValue xmin() {

    return new RealValue(x[0], getUnitX());
  }

  /**
   * Get the maximal x-value
   * 
   * @return maximal x-value
   */
  public RealValue xmax() {
    return new RealValue(x[x.length - 1], getUnitX());
  }

  /**
   * Get the number of points in the waveform
   * 
   * @return number of points
   */
  public int noOfVals() {
    return this.x.length;
  }

  /**
   * Check if the x-values of two waveforms are equal
   * 
   * @param wave Waveform to be compared
   * @return <code>true</code> if the x-values are equal <code>false</code>
   *         otherwise
   */
  public boolean sameAxis(RealWaveform wave) {

    if (this.x.length == wave.x.length) {

      for (int i = 0; i < x.length; i++) {
        if (x[i] != wave.x[i]) {
          return false;
        }
      }

      return true;

    } else {

      return false;
    }
  }

  /**
   * Evaluate a waveform at a value
   * 
   * @param pos x-value where the waveform is evaluated
   * @return value
   */
  public abstract Value getValue(double pos);

  /**
   * Calculate the absolute value of a waveform
   * 
   * @return Waveform
   */
  public abstract RealWaveform abs();

  /**
   * Calculate db10 of a waveform
   * 
   * @return Waveform
   */
  public abstract RealWaveform db10();

  /**
   * Calculate db10 of a waveform
   * 
   * @return Waveform
   */
  public abstract RealWaveform db20();

}