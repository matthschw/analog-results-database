package edlab.eda.ardb;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Test;

import edlab.eda.reader.nutmeg.NutReader;
import edlab.eda.reader.nutmeg.NutbinReader;
import edlab.eda.reader.nutmeg.NutmegComplexPlot;
import edlab.eda.reader.nutmeg.NutmegPlot;
import edlab.eda.reader.nutmeg.NutmegRealPlot;

@SuppressWarnings("unused")
class OpampTest {

  private static final double vs = 0.5;
  private static final double vsup = 3.3;
  private static final double dev = 1e-4;

  @Test
  void test() {

    final NutReader reader = NutbinReader
        .getNutReader("./src/test/resources/nutbin.raw");
    reader.read().parse();

    final List<NutmegPlot> plots = reader.getPlots();

    int resultIdentifier = 0;

    final RealResultsDatabase dcopResults = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    final RealResultsDatabase dcmatchResults = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    final NutmegComplexPlot plot = (NutmegComplexPlot) plots
        .get(resultIdentifier++);

    final ComplexResultsDatabase stb = ComplexResultsDatabase
        .buildResultDatabase(plot);

    final ComplexWaveform loopGain = stb.getComplexWaveform("loopGain");

    final RealWaveform loopGainAbs = loopGain.abs().db20();

    final RealWaveform loopGainPhase = loopGain.phaseDeg();

    final RealValue a0 = loopGainAbs.getValue(loopGainAbs.xmin());
    final RealValue ugbw = loopGainAbs.cross(0, 1);

    final RealValue pm = loopGainPhase.getValue(ugbw.getValue());
    final RealValue x = loopGainPhase.cross(0, 1);
    final RealValue gm = loopGainAbs.getValue(x.getValue());

    final RealResultsDatabase tran = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    final RealWaveform in = tran.getRealWaveform("INP");
    RealWaveform out = tran.getRealWaveform("OUT");

    in.add(out);
    in.add(1.0);
    in.add(BigDecimal.ONE);
    in.add(new Complex(1.0,1.0));
    
    in.subtract(out);
    in.subtract(1.0);
    in.subtract(BigDecimal.ONE);
    in.subtract(new Complex(1.0,1.0));
    
    in.multiply(out);
    in.multiply(1.0);
    in.multiply(BigDecimal.ONE);
    in.multiply(new Complex(1.0,1.0));
    
    
    in.divide(out);
    in.divide(1.0);
    in.divide(BigDecimal.ONE);
    in.divide(new Complex(1.0,1.0));

    final RealWaveform rising = out.clip(100e-9, 50e-6);
    final RealWaveform falling = out.clip(50.1e-6, 99.9e-6);

    final double lower = (0.1 * vs) - (vs / 2);
    final double upper = (0.9 * vs) - (vs / 2);

    RealValue point1 = rising.cross(lower, 1);
    RealValue point2 = rising.cross(upper, 1);

    final double sr_r = (upper - lower)
        / (point2.getValue() - point1.getValue());

    point1 = falling.cross(upper, 1);
    point2 = falling.cross(lower, 1);

    final double sr_f = (lower - upper)
        / (point2.getValue() - point1.getValue());

    final RealResultsDatabase noise = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    out = noise.getRealWaveform("out");

    final double vn_1Hz = out.getValue(1).getValue();
    final double vn_10Hz = out.getValue(10).getValue();
    final double vn_100Hz = out.getValue(1e2).getValue();
    final double vn_1kHz = out.getValue(1e3).getValue();
    final double vn_10kHz = out.getValue(1e4).getValue();
    final double vn_100kHz = out.getValue(1e5).getValue();

    final RealResultsDatabase outswing = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    out = outswing.getRealWaveform("OUT");
    final RealWaveform out_ideal = outswing.getRealWaveform("OUT_IDEAL");
    out = out.subtract(out.getValue(0));

    RealWaveform rel_dev = out.subtract(out_ideal);

    rel_dev = rel_dev.abs().divide(vsup);

    RealWaveform rel_dev_lower = rel_dev.clip(rel_dev.xmin().getValue(), 0);
    RealWaveform rel_dev_upper = rel_dev.clip(0, rel_dev.xmax().getValue());

    RealValue vil = rel_dev_lower.cross(dev, 1);
    RealValue vih = rel_dev_upper.cross(dev, 1);

    final RealValue voh = out.getValue(vih);
    final RealValue vol = out.getValue(vil);

    final double v_ol = vol.getValue() + (vsup / 2);
    final double v_oh = voh.getValue() + (vsup / 2);

    final ComplexResultsDatabase tf = ComplexResultsDatabase
        .buildResultDatabase((NutmegComplexPlot) plots.get(resultIdentifier++));

    final ComplexWaveform vsupp = tf.getComplexWaveform("VSUPP");
    final ComplexWaveform vsupn = tf.getComplexWaveform("VSUPN");
    final ComplexWaveform vid = tf.getComplexWaveform("VID");
    final ComplexWaveform vicm = tf.getComplexWaveform("VICM");

    final RealWaveform vsuppAbs = vsupp.abs().db20();
    final RealWaveform vsupnAbs = vsupn.abs().db20();
    final RealWaveform vidAbs = vid.abs().db20();
    final RealWaveform vicmAbs = vicm.abs().db20();

    final RealWaveform psrr_p = vidAbs.subtract(vsuppAbs);
    final RealWaveform psrr_n = vidAbs.subtract(vsupnAbs);
    final RealWaveform cmrr = vidAbs.subtract(vicmAbs);

    final double psrr_p0 = psrr_p.getValue(psrr_p.xmin()).getValue();
    final double psrr_n0 = psrr_n.getValue(psrr_n.xmin()).getValue();
    final double cmrr_0 = cmrr.getValue(cmrr.xmin()).getValue();

    final ComplexResultsDatabase inswing = ComplexResultsDatabase
        .buildResultDatabase((NutmegComplexPlot) plots.get(resultIdentifier++));

    out = inswing.getComplexWaveform("OUT").abs().db20();

    rel_dev_lower = out.clip(out.xmin().getValue(), 0);
    rel_dev_upper = out.clip(0, out.xmax().getValue());

    final RealValue amp = out.getValue(0);
    vil = rel_dev_lower.cross(amp.getValue() - 3, 1);
    vih = rel_dev_upper.cross(amp.getValue() - 3, 1);

    final double v_il = vil.getValue() + (vsup / 2);
    final double v_ih = vih.getValue() + (vsup / 2);

    final RealResultsDatabase outshortl = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    final double i_out_min = outshortl.getRealValue("DUT:O").getValue();

    final RealResultsDatabase outshorth = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    final double i_out_max = outshorth.getRealValue("DUT:O").getValue();

  }

}
