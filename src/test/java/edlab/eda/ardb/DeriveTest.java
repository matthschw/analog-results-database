package edlab.eda.ardb;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class DeriveTest {

  public static final double COMP = 1e-12;

  @Test
  void test() {
    final double a = 3.0;
    final double b = 2.0;
    final double c = 1.0;

    final double step = 0.01;

    final double x[] = new double[1001];
    final double y[] = new double[1001];
    final double yDeriv[] = new double[1001];

    for (int i = 0; i < x.length; i++) {
      x[i] = (i * step) - ((x.length / 2) * step);
      y[i] = (a * Math.pow(x[i], 2.0)) + (b * x[i]) + c;
      yDeriv[i] = (2 * a * x[i]) + b;
    }

    final RealWaveform wave1 = RealWaveform.buildRealWaveform(x, y, "", "");
    final RealWaveform wave2 = RealWaveform.buildRealWaveform(x, yDeriv, "", "");

    final RealWaveform comp = RealWaveform.buildRealWaveform(
        new double[] { -x.length * step, x.length * step },
        new double[] { COMP, COMP }, "", "");

    if (!wave1.derive()
        .clip(step - ((x.length / 2) * step), ((x.length / 2) * step) - step)
        .subtract(
            wave2.clip(step - ((x.length / 2) * step), ((x.length / 2) * step) - step))
        .leq(comp)) {

      fail("Deriving failed");
    }
  }
}