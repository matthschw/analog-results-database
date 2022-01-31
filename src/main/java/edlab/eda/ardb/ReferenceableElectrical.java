package edlab.eda.ardb;

/**
 * A class which implements this interface represents an object that is able to
 * provide an identifier that references an electrical magnitude (voltage,
 * current, operating point) in a circuit.
 */
public interface ReferenceableElectrical {

  /**
   * Get an identifier as string
   *
   * @return identifier
   */
  public String getIdentifier();
}