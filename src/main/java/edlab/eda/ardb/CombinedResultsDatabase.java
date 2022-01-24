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
      Map<String, RealValue> realValues, Map<String, RealWaveform> realWaves,
      Map<String, ComplexValue> complexValues,
      Map<String, ComplexWaveform> complexWaves) {

    CombinedResultsDatabase retval = new CombinedResultsDatabase();

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
      RealResultsDatabase realResultsDatabase,
      ComplexResultsDatabase complexResultsDatabase) {

    CombinedResultsDatabase retval = new CombinedResultsDatabase();

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
  public static CombinedResultsDatabase create(String[] realValueNames,
      RealValue[] realValuesKeys, String[] realWaveNames,
      RealWaveform[] realWaveKey, String[] complexValueNames,
      ComplexValue[] complexValuesKeys, String[] complexWaveNames,
      RealWaveform[] complexWaveKeys) {

    CombinedResultsDatabase retval = new CombinedResultsDatabase();

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

    Set<String> retval = new HashSet<String>();

    for (String name : this.realResultsDatabase.getValueNames()) {
      retval.add(name);
    }

    for (String name : this.complexResultsDatabase.getValueNames()) {
      retval.add(name);
    }

    return retval;
  }
  
  @Override
  public String[] getValueNamesAsArray() {
    return ResultsDatabase.convert(this.getValueNames());
  }

  @Override
  public Set<String> getWaveNames() {

    Set<String> retval = new HashSet<String>();

    for (String name : this.realResultsDatabase.getWaveNames()) {
      retval.add(name);
    }

    for (String name : this.complexResultsDatabase.getWaveNames()) {
      retval.add(name);
    }

    return retval;
  }
  
  @Override
  public String[] getWaveNamesAsArray() {
    return ResultsDatabase.convert(this.getWaveNames());
  }

  @Override
  public boolean isValueName(String name) {

    if (this.realResultsDatabase.isValueName(name)) {
      return true;
    }

    if (this.complexResultsDatabase.isValueName(name)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isWaveformName(String name) {

    if (this.realResultsDatabase.isWaveformName(name)) {
      return true;
    }

    if (this.complexResultsDatabase.isWaveformName(name)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isValue(ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isValue(electrical)) {
      return true;
    }

    if (this.complexResultsDatabase.isValue(electrical)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isWaveform(ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isWaveform(electrical)) {
      return true;
    }

    if (this.complexResultsDatabase.isWaveform(electrical)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isMember(String name) {

    if (this.realResultsDatabase.isMember(name)) {
      return true;
    }

    if (this.complexResultsDatabase.isMember(name)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isMember(ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isMember(electrical)) {
      return true;
    }

    if (this.complexResultsDatabase.isMember(electrical)) {
      return true;
    }

    return false;
  }

  @Override
  public Value getValue(String name) {

    if (this.realResultsDatabase.isValueName(name)) {
      return this.realResultsDatabase.getValue(name);
    }

    if (this.complexResultsDatabase.isValueName(name)) {
      return this.complexResultsDatabase.getValue(name);
    }

    return null;
  }

  @Override
  public Value getValue(ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isValue(electrical)) {
      return this.realResultsDatabase.getValue(electrical);
    }

    if (this.complexResultsDatabase.isValue(electrical)) {
      return this.complexResultsDatabase.getValue(electrical);
    }

    return null;
  }

  @Override
  public Waveform getWaveform(String name) {

    if (this.realResultsDatabase.isWaveformName(name)) {
      return this.realResultsDatabase.getWaveform(name);
    }

    if (this.complexResultsDatabase.isWaveformName(name)) {
      return this.complexResultsDatabase.getWaveform(name);
    }

    return null;
  }

  @Override
  public Waveform getWaveform(ReferenceableElectrical electrical) {

    if (this.realResultsDatabase.isWaveform(electrical)) {
      return this.realResultsDatabase.getWaveform(electrical);
    }

    if (this.complexResultsDatabase.isWaveform(electrical)) {
      return this.complexResultsDatabase.getWaveform(electrical);
    }

    return null;
  }

  @Override
  public Object get(String name) {

    if (this.realResultsDatabase.isMember(name)) {
      return this.realResultsDatabase.get(name);
    }

    if (this.complexResultsDatabase.isMember(name)) {
      return this.realResultsDatabase.getWaveform(name);
    }

    return null;
  }

  @Override
  public Object get(ReferenceableElectrical electrical) {

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
  public static boolean isInstanceOf(Object o) {
    return o instanceof CombinedResultsDatabase;
  }
}
