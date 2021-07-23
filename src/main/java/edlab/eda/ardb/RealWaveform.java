package edlab.eda.ardb;

import java.util.ArrayList;
import java.util.LinkedList;

public class RealWaveform extends Waveform {

  private double[] y;

  private RealWaveform(double[] x, double[] y, String unitX, String unitY) {
    super(x, unitX, unitY);
    this.y = y;
  }

  public RealWaveform() {
    super();
  }

  public double[] getY() {
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
  public int noOfVals() {
    return x.length;
  }

  public RealWaveform clip(double left, double right) {

    LinkedList<Double> newXVals = new LinkedList<Double>();
    LinkedList<Double> newYVals = new LinkedList<Double>();

    for (int i = 0; i < x.length; i++) {
      if (left <= x[i] && x[i] <= right) {
        newXVals.addLast(x[i]);
        newYVals.addLast(y[i]);
      }
    }

    RealValue leftValue = this.getValue(left);

    if (!leftValue.isInvalid()) {
      if (newXVals.get(0) != leftValue.getValue()) {
        newYVals.addFirst(leftValue.getValue());
        newXVals.addFirst(left);
      }
    }

    RealValue rightValue = this.getValue(right);

    if (!rightValue.isInvalid()) {
      if (newXVals.get(newXVals.size() - 1) != rightValue.getValue()) {
        newYVals.addLast(rightValue.getValue());
        newXVals.addLast(right);
      }
    }

    if (newXVals.size() > 0) {

      double[] newX = new double[newXVals.size()];
      double[] newY = new double[newYVals.size()];

      for (int i = 0; i < newY.length; i++) {
        newX[i] = newXVals.get(i);
        newY[i] = newYVals.get(i);
      }

      return new RealWaveform(newX, newY, getUnitX(), getUnitY());
    } else {

      return new RealWaveform();
    }
  }

  public RealWaveform add(double value) {

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i] + value;
      newY[i] = y[i];
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  public RealWaveform add(RealValue value) {

    if (value.isInvalid()) {
      return null;
    } else {
      return this.add(value.getValue());
    }
  }

  public RealWaveform add(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this);
    }

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] + wave.y[i];
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  public RealWaveform subtract(double value) {

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] - value;
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  public RealWaveform subtract(RealValue value) {

    if (value.isInvalid()) {
      return null;
    } else {
      return this.subtract(value.getValue());
    }
  }

  public RealWaveform subtract(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this);
    }

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] - wave.y[i];
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  public RealWaveform multiply(double value) {

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] * value;
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  public RealWaveform multiply(RealValue value) {

    if (value.isInvalid()) {
      return null;
    } else {
      return this.multiply(value.getValue());
    }
  }

  public RealWaveform multiply(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this);
    }

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] * wave.y[i];
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  public RealWaveform divide(double value) {

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] / value;
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  public RealWaveform divide(RealValue value) {

    if (value.isInvalid()) {
      return null;
    } else {
      return this.divide(value.getValue());
    }
  }

  public RealWaveform divide(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this);
    }

    double[] newX = new double[x.length];
    double[] newY = new double[y.length];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = x[i];
      newY[i] = y[i] / wave.y[i];
    }

    return new RealWaveform(newX, newY, getUnitX(), getUnitY());
  }

  @Override
  public RealValue getValue(double pos) {

    double m;

    if (pos <= x[0]) {
      m = (y[1] - y[0]) / (x[1] - x[0]);

      return new RealValue(y[0] + m * (pos - x[0]), this.getUnitY());

    } else if (pos >= x[x.length - 1]) {

      m = (y[y.length - 1] - y[y.length - 2])
          / (x[x.length - 1] - x[x.length - 2]);

      return new RealValue(y[y.length - 1] + m * (pos - x[x.length - 1]),
          this.getUnitY());
    } else {

      for (int i = 0; i < x.length - 1; i++) {
        if ((x[i] - pos) * (x[i + 1] - pos) <= 0) {

          m = (y[i + 1] - y[i]) / (x[i + 1] - x[i]);

          return new RealValue(y[i] + m * (pos - x[i]), this.getUnitY());

        }
      }
    }

    return new RealValue();
  }

  public RealWaveform resample(double[] newX) {

    double[] yNew = new double[this.x.length];

    for (int i = 0; i < newX.length; i++) {
      yNew[i] = this.getValue(newX[i]).getValue();
    }

    return new RealWaveform(x, yNew, getUnitX(), getUnitY());
  }

  public RealWaveform resample(RealWaveform wave) {

    return this.resample(wave.x);
  }

  public boolean sameAxis(RealWaveform wave) {

    if (this.y.length == wave.x.length) {

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

  public RealValue getValue(RealValue val) {

    if (val.isInvalid()) {
      return new RealValue();
    } else {
      return this.getValue(val.getValue());
    }
  }

  @Override
  public RealWaveform abs() {

    double[] yVec = new double[this.x.length];

    for (int i = 0; i < yVec.length; i++) {
      yVec[i] = Math.abs(y[i]);
    }

    return new RealWaveform(x, yVec, getUnitX(), getUnitY());
  }

  public RealWaveform pow(double exponent) {

    double[] pow = new double[y.length];

    for (int i = 0; i < pow.length; i++) {
      pow[i] = 10 * Math.pow(y[i], exponent);
    }

    return new RealWaveform(this.getX(), pow, this.getUnitX(), "");
  }

  public RealWaveform ln() {

    double[] ln = new double[y.length];

    for (int i = 0; i < ln.length; i++) {
      ln[i] = 10 * Math.log(y[i]);
    }

    return new RealWaveform(this.getX(), ln, this.getUnitX(), "");
  }

  public RealWaveform db10() {

    double[] db10 = new double[y.length];

    for (int i = 0; i < db10.length; i++) {
      db10[i] = 10 * Math.log10(y[i]);
    }

    return new RealWaveform(this.getX(), db10, this.getUnitX(), "");
  }

  @Override
  public RealWaveform db20() {

    double[] db20 = new double[y.length];

    for (int i = 0; i < db20.length; i++) {
      db20[i] = 20 * Math.log10(y[i]);
    }

    return new RealWaveform(this.getX(), db20, this.getUnitX(), "");
  }

  public RealWaveform sin() {

    double[] sin = new double[y.length];

    for (int i = 0; i < sin.length; i++) {
      sin[i] = 10 * Math.sin(y[i]);
    }

    return new RealWaveform(this.getX(), sin, this.getUnitX(), "");
  }

  public RealWaveform asin() {

    double[] asin = new double[y.length];

    for (int i = 0; i < asin.length; i++) {
      asin[i] = 10 * Math.asin(y[i]);
    }

    return new RealWaveform(this.getX(), asin, this.getUnitX(), "");
  }

  public RealWaveform cos() {

    double[] cos = new double[y.length];

    for (int i = 0; i < cos.length; i++) {
      cos[i] = 10 * Math.sin(y[i]);
    }

    return new RealWaveform(this.getX(), cos, this.getUnitX(), "");
  }

  public RealWaveform acos() {

    double[] acos = new double[y.length];

    for (int i = 0; i < acos.length; i++) {
      acos[i] = 10 * Math.acos(y[i]);
    }

    return new RealWaveform(this.getX(), acos, this.getUnitX(), "");
  }

  public RealWaveform tan() {

    double[] tan = new double[y.length];

    for (int i = 0; i < tan.length; i++) {
      tan[i] = 10 * Math.tan(y[i]);
    }

    return new RealWaveform(this.getX(), tan, this.getUnitX(), "");
  }

  public RealWaveform atan() {

    double[] atan = new double[y.length];

    for (int i = 0; i < atan.length; i++) {
      atan[i] = 10 * Math.atan(y[i]);
    }

    return new RealWaveform(this.getX(), atan, this.getUnitX(), "");
  }

  public RealValue cross(double val, int edge) {

    int counter = 1;

    System.out.println("A:" + x.length);
    System.out.println("B:" + y.length);
    for (int i = 0; i < x.length - 1; i++) {

      if ((y[i] - val) * (y[i + 1] - val) <= 0) {

        if (counter == edge) {
          return new RealValue(
              x[i] + (val - y[i]) / (y[i + 1] - y[i]) * (x[i + 1] - x[i]),
              getUnitX());
        }

        counter++;

      }
    }
    return new RealValue();
  }

  public RealValue cross(RealValue val, int edge) {

    if (val.isInvalid()) {
      return new RealValue();
    } else {
      return this.cross(val.getValue(), edge);
    }
  }

  public RealWaveform cross(double val) {

    ArrayList<Double> newXVals = new ArrayList<Double>();
    ArrayList<Double> newYVals = new ArrayList<Double>();

    for (int i = 0; i < x.length - 1; i++) {
      if ((y[i] - val) * (y[i + 1] - val) <= 0) {
        newXVals
            .add(x[i] + (val - y[i]) / (y[i + 1] - y[i]) * (x[i + 1] - x[i]));
        newYVals.add(val);
      }
    }

    double[] newX = new double[newXVals.size()];
    double[] newY = new double[newYVals.size()];

    for (int i = 0; i < newY.length; i++) {
      newX[i] = newXVals.get(i);
      newY[i] = newYVals.get(i);
    }

    return new RealWaveform(newX, newY, this.getUnitX(), this.getUnitY());
  }

  public RealWaveform cross(RealValue val) {

    if (val.isInvalid()) {
      return new RealWaveform();
    } else {
      return this.cross(val.getValue());
    }
  }

  public RealWaveform waveVsWave(RealWaveform wave) {

    if (!this.sameAxis(wave)) {
      wave = wave.resample(this);
    }

    return new RealWaveform(this.getX(), wave.getY(), this.getUnitX(),
        wave.getUnitY());
  }

  public RealValue ymin() {

    double min = Double.MAX_VALUE;

    for (int i = 0; i < y.length; i++) {
      min = Math.min(y[i], min);
    }

    return new RealValue(min, getUnitY());
  }

  public RealValue ymax() {

    double max = Double.MIN_VALUE;

    for (int i = 0; i < y.length; i++) {
      max = Math.max(y[i], max);
    }

    return new RealValue(max, getUnitY());
  }

  public static RealWaveform buildRealWaveform(double[] x, double[] y,
      String unitX, String unitY) {

    if (x.length == y.length) {

      sortWaveElements(x, y);

      return new RealWaveform(x, y, unitX, unitY);

    } else {
      System.out.println("Length of arrays do not match");
      return null;
    }
  }

  private static void sortWaveElements(double[] x, double[] y) {

    double swap;
    boolean swapPerformed = true;

    while (swapPerformed) {

      swapPerformed = false;

      for (int i = 0; i < x.length - 1; i++) {

        if (x[i] > x[i + 1]) {

          swap = x[i + 1];
          x[i + 1] = x[i];
          x[i] = swap;

          swap = y[i + 1];
          y[i + 1] = y[i];
          y[i] = swap;
          swapPerformed = true;
        }
      }
    }

  }

}