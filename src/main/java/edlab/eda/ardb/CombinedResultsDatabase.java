package edlab.eda.ardb;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CombinedResultsDatabase extends ResultsDatabase {

  private RealResultsDatabase realResultsDatabase;
  private ComplexResultsDatabase complexResultsDatabase;

  private CombinedResultsDatabase() {
  }

  /**
   * Create a {@link CombinedResultsDatabase}
   *
   * @param realValues    map of real values
   * @param realWaves     map of real waves
   * @param complexValues map of complex values
   * @param complexWaves  map of complex waves
   * @return
   */
  public static CombinedResultsDatabase create(
      final Map<String, RealValue> realValues, final Map<String, RealWaveform> realWaves,
      final Map<String, ComplexValue> complexValues,
      final Map<String, ComplexWaveform> complexWaves) {

    final CombinedResultsDatabase retval = new CombinedResultsDatabase();

    retval.realResultsDatabase = RealResultsDatabase.create(realValues,
        realWaves);
    retval.complexResultsDatabase = ComplexResultsDatabase.create(complexValues,
        complexWaves);

    return retval;
  }

  /**
   * Create a {@link CombinedResultsDatabase}
   *
   * @param realResultsDatabase    real database
   * @param complexResultsDatabase complex database
   * @return database
   */
  public static CombinedResultsDatabase create(
      final RealResultsDatabase realResultsDatabase,
      final ComplexResultsDatabase complexResultsDatabase) {

    final CombinedResultsDatabase retval = new CombinedResultsDatabase();

    retval.realResultsDatabase = realResultsDatabase;
    retval.complexResultsDatabase = complexResultsDatabase;

    return retval;
  }

  /**
   * Create a {@link CombinedResultsDatabase}
   *
   * @param realValueNames    array of real value names
   * @param realValuesKeys    array of real value keys
   * @param realWaveNames     array of real wave names
   * @param realWaveKey       array of real wave keys
   * @param complexValueNames array of complex value names
   * @param complexValuesKeys array of complex value keys
   * @param complexWaveNames  array of complex wave names
   * @param complexWaveKeys   array of complex wave keys
   * @return database
   */
  public static CombinedResultsDatabase create(final String[] realValueNames,
      final RealValue[] realValuesKeys, final String[] realWaveNames,
      final RealWaveform[] realWaveKey, final String[] complexValueNames,
      final ComplexValue[] complexValuesKeys, final String[] complexWaveNames,
      final RealWaveform[] complexWaveKeys) {

    final CombinedResultsDatabase retval = new CombinedResultsDatabase();

    retval.realResultsDatabase = RealResultsDatabase.create(realValueNames,
        realValuesKeys, realWaveNames, realWaveKey);

    retval.complexResultsDatabase = ComplexResultsDatabase.create(
        complexValueNames, complexValuesKeys, complexWaveNames,
        complexWaveKeys);

    return retval;
  }

  /**
   * Create an empty {@link CombinedResultsDatabase}
   *
   * @return database
   */
  public static CombinedResultsDatabase create() {
    return CombinedResultsDatabase.create(RealResultsDatabase.create(),
        ComplexResultsDatabase.create());
  }

  @Override
  public Set<String> getValueNames() {

    final Set<String> retval = new HashSet<>();

    retval.addAll(this.realResultsDatabase.getValueNames());

    retval.addAll(this.complexResultsDatabase.getValueNames());

    return retval;
  }

  @Override
  public String[] getValueNamesAsArray() {
    return ResultsDatabase.convert(this.getValueNames());
  }

  @Override
  public Set<String> getWaveNames() {

    final Set<String> retval = new HashSet<>();

    retval.addAll(this.realResultsDatabase.getWaveNames());

    retval.addAll(this.complexResultsDatabase.getWaveNames());

    return retval;
  }

  @Override
  public String[] getWaveNamesAsArray() {
    return ResultsDatabase.convert(this.getWaveNames());
  }

  @Override
  public boolean isValueName(final String name) {

    if (this.realResultsDatabase.isValueName(name) || this.complexResultsDatabase.isValueName(name)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isWaveformName(final String name) {

    if (this.realResultsDatabase.isWaveformName(name) || this.complexResultsDatabase.isWaveformName(name)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isValue(final ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isValue(electrical) || this.complexResultsDatabase.isValue(electrical)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isWaveform(final ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isWaveform(electrical) || this.complexResultsDatabase.isWaveform(electrical)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isMember(final String name) {

    if (this.realResultsDatabase.isMember(name) || this.complexResultsDatabase.isMember(name)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isMember(final ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isMember(electrical) || this.complexResultsDatabase.isMember(electrical)) {
      return true;
    }

    return false;
  }

  @Override
  public Value getValue(final String name) {

    if (this.realResultsDatabase.isValueName(name)) {
      return this.realResultsDatabase.getValue(name);
    }

    if (this.complexResultsDatabase.isValueName(name)) {
      return this.complexResultsDatabase.getValue(name);
    }

    return null;
  }

  @Override
  public Value getValue(final ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isValue(electrical)) {
      return this.realResultsDatabase.getValue(electrical);
    }

    if (this.complexResultsDatabase.isValue(electrical)) {
      return this.complexResultsDatabase.getValue(electrical);
    }

    return null;
  }

  @Override
  public Waveform getWaveform(final String name) {

    if (this.realResultsDatabase.isWaveformName(name)) {
      return this.realResultsDatabase.getWaveform(name);
    }

    if (this.complexResultsDatabase.isWaveformName(name)) {
      return this.complexResultsDatabase.getWaveform(name);
    }

    return null;
  }

  @Override
  public Waveform getWaveform(final ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isWaveform(electrical)) {
      return this.realResultsDatabase.getWaveform(electrical);
    }

    if (this.complexResultsDatabase.isWaveform(electrical)) {
      return this.complexResultsDatabase.getWaveform(electrical);
    }

    return null;
  }

  @Override
  public Object get(final String name) {

    if (this.realResultsDatabase.isMember(name)) {
      return this.realResultsDatabase.get(name);
    }

    if (this.complexResultsDatabase.isMember(name)) {
      return this.realResultsDatabase.getWaveform(name);
    }

    return null;
  }

  @Override
  public Object get(final ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isMember(electrical)) {
      return this.realResultsDatabase.get(electrical);
    }

    if (this.complexResultsDatabase.isMember(electrical)) {
      return this.realResultsDatabase.getWaveform(electrical);
    }

    return null;
  }

  @Override
  public boolean isEmpty() {
    return this.realResultsDatabase.isEmpty()
        && this.complexResultsDatabase.isEmpty();
  }

  /**
   * Identify whether an object is an instance of this class
   *
   * @param o Object to be checked
   * @return <code>true</code> when the object is an instance of this class,
   *         <code>false</code> otherwise
   */
  public static boolean isInstanceOf(final Object o) {
    return o instanceof CombinedResultsDatabase;
  }
}
