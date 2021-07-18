package edlab.eda.ardb;

import org.apache.commons.math3.complex.Complex;

public class ComplexWaveform extends Waveform {

  private Complex[] y;

  public ComplexWaveform(double[] x, Complex[] y, String unitX, String unitY) {
    super(x, unitX, unitY);
    this.y = y;
  }

  public ComplexWaveform(double[] x, double[] yReal, double[] yImag,
      String unitX, String unitY) {
    super(x, unitX, unitY);

    this.y = new Complex[yReal.length];

    for (int i = 0; i < yReal.length; i++) {
      this.y[i] = new Complex(yReal[i], yImag[i]);
    }
  }

  public ComplexWaveform(String name, double[] x, Complex[] y, String unitX,
      String unitY) {
    super(x, unitX, unitY);
    this.x = x;
    this.y = y;
  }

  public ComplexWaveform(String name, double[] x, double[] yReal,
      double[] yImag, String unitX, String unitY) {
    super(x, unitX, unitY);

    this.x = x;
    this.y = new Complex[yReal.length];

    for (int i = 0; i < yReal.length; i++) {
      this.y[i] = new Complex(yReal[i], yImag[i]);
    }
  }

  public Complex[] getY() {
    return y;
  }

  public String toString() {
    String res = "";
    for (int i = 0; i < x.length; i++) {
      if (i > 0) {
        res += "\n";
      }
      res += "(" + x[i] + " " + getUnitX() + " , " + y[i] + " " + getUnitY()
          + ")";
    }
    return res;
  }

  @Override
  public Value getValue(double pos) {

    for (int i = 0; i < x.length - 1; i++) {
      if ((x[i] - pos) * (x[i + 1] - pos) <= 0) {

        return new ComplexValue(
            y[i].add(y[i + 1].subtract(y[i])
                .multiply(new Complex((pos - x[i]) / (x[i + 1] - x[i])))),
            getUnitY());

      }
    }
    return null;
  }

  @Override
  public RealWaveform abs() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = y[i].abs();
    }

    return new RealWaveform(x, yVec, getUnitX(), getUnitY());
  }

  public RealWaveform phaseDeg() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = y[i].getArgument() / Math.PI * 180;
    }

    return new RealWaveform(x, yVec, getUnitX(), "deg");
  }

  @Override
  public RealWaveform db20() {

    RealWaveform realWave = this.abs();

    return realWave.db20();
  }

  @Override
  public int noOfVals() {
    return x.length;
  }

  @Override
  public Value cross(double val) {
    // TODO Auto-generated method stub
    return null;
  }
}