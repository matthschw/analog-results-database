package edlab.eda.ardb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import edlab.eda.reader.nutmeg.NutReader;
import edlab.eda.reader.nutmeg.NutbinReader;
import edlab.eda.reader.nutmeg.NutmegComplexPlot;
import edlab.eda.reader.nutmeg.NutmegPlot;
import edlab.eda.reader.nutmeg.NutmegRealPlot;

class OpampTest {

  private static final double vs = 0.5;
  private static final double vsup = 3.3;
  private static final double dev = 1e-4;

  @Test
  void test() {

    NutReader reader = NutbinReader
        .getNutReader("./src/test/resources/nutbin.raw");
    reader.read().parse();

    List<NutmegPlot> plots = reader.getPlots();

    int resultIdentifier = 0;

    RealResultsDatabase dcopResults = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    RealResultsDatabase dcmatchResults = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    NutmegComplexPlot plot = (NutmegComplexPlot) plots.get(resultIdentifier++);

    ComplexResultsDatabase stb = ComplexResultsDatabase
        .buildResultDatabase(plot);

    ComplexWaveform loopGain = stb.getComplexWaveform("loopGain");

    RealWaveform loopGainAbs = loopGain.abs().db20();

    RealWaveform loopGainPhase = loopGain.phaseDeg();

    RealValue a0 = loopGainAbs.getValue(loopGainAbs.xmin());
    RealValue ugbw = loopGainAbs.cross(0, 1);

    RealValue pm = loopGainPhase.getValue(ugbw.getValue());
    RealValue x = loopGainPhase.cross(0, 1);
    RealValue gm = loopGainAbs.getValue(x.getValue());

    RealResultsDatabase tran = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    RealWaveform out = tran.getRealWaveform("OUT");

    RealWaveform rising = out.clip(100e-9, 50e-6);
    RealWaveform falling = out.clip(50.1e-6, 99.9e-6);

    double lower = 0.1 * vs - vs / 2;
    double upper = 0.9 * vs - vs / 2;

    RealValue point1 = rising.cross(lower, 1);
    RealValue point2 = rising.cross(upper, 1);

    double sr_r = (upper - lower) / (point2.getValue() - point1.getValue());

    point1 = falling.cross(upper, 1);
    point2 = falling.cross(lower, 1);

    double sr_f = (lower - upper) / (point2.getValue() - point1.getValue());

    RealResultsDatabase noise = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    out = noise.getRealWaveform("out");

    double vn_1Hz = out.getValue(1).getValue();
    double vn_10Hz = out.getValue(10).getValue();
    double vn_100Hz = out.getValue(1e2).getValue();
    double vn_1kHz = out.getValue(1e3).getValue();
    double vn_10kHz = out.getValue(1e4).getValue();
    double vn_100kHz = out.getValue(1e5).getValue();

    RealResultsDatabase outswing = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    out = outswing.getRealWaveform("OUT");
    RealWaveform out_ideal = outswing.getRealWaveform("OUT_IDEAL");
    out = out.subtract(out.getValue(0));

    RealWaveform rel_dev = out.subtract(out_ideal);

    rel_dev = rel_dev.abs().divide(vsup);

    RealWaveform rel_dev_lower = rel_dev.clip(rel_dev.xmin().getValue(), 0);
    RealWaveform rel_dev_upper = rel_dev.clip(0, rel_dev.xmax().getValue());

    RealValue vil = rel_dev_lower.cross(dev, 1);
    RealValue vih = rel_dev_upper.cross(dev, 1);

    RealValue voh = out.getValue(vih);
    RealValue vol = out.getValue(vil);

    double v_ol = vol.getValue() + vsup / 2;
    double v_oh = voh.getValue() + vsup / 2;

    ComplexResultsDatabase tf = ComplexResultsDatabase
        .buildResultDatabase((NutmegComplexPlot) plots.get(resultIdentifier++));

    ComplexWaveform vsupp = tf.getComplexWaveform("VSUPP");
    ComplexWaveform vsupn = tf.getComplexWaveform("VSUPN");
    ComplexWaveform vid = tf.getComplexWaveform("VID");
    ComplexWaveform vicm = tf.getComplexWaveform("VICM");

    RealWaveform vsuppAbs = vsupp.abs().db20();
    RealWaveform vsupnAbs = vsupn.abs().db20();
    RealWaveform vidAbs = vid.abs().db20();
    RealWaveform vicmAbs = vicm.abs().db20();

    RealWaveform psrr_p = vidAbs.subtract(vsuppAbs);
    RealWaveform psrr_n = vidAbs.subtract(vsupnAbs);
    RealWaveform cmrr = vidAbs.subtract(vicmAbs);

    double psrr_p0 = psrr_p.getValue(psrr_p.xmin()).getValue();
    double psrr_n0 = psrr_n.getValue(psrr_n.xmin()).getValue();
    double cmrr_0 = cmrr.getValue(cmrr.xmin()).getValue();

    ComplexResultsDatabase inswing = ComplexResultsDatabase
        .buildResultDatabase((NutmegComplexPlot) plots.get(resultIdentifier++));

    out = inswing.getComplexWaveform("OUT").abs().db20();

    rel_dev_lower = out.clip(out.xmin().getValue(), 0);
    rel_dev_upper = out.clip(0, out.xmax().getValue());

    RealValue amp = out.getValue(0);
    vil = rel_dev_lower.cross(amp.getValue() - 3, 1);
    vih = rel_dev_upper.cross(amp.getValue() - 3, 1);

    double v_il = vil.getValue() + vsup / 2;
    double v_ih = vih.getValue() + vsup / 2;

    RealResultsDatabase outshortl = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    double i_out_min = outshortl.getRealValue("DUT:O").getValue();

    RealResultsDatabase outshorth = RealResultsDatabase
        .buildResultDatabase((NutmegRealPlot) plots.get(resultIdentifier++));

    double i_out_max = outshorth.getRealValue("DUT:O").getValue();

  }

}
