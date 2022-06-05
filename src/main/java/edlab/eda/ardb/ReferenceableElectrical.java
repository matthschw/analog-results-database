package edlab.eda.ardb;

/**
 * A class which implements this interface represents an object that is able to
 * provide an identifier that references a net, terminal or operating point
 */
public interface ReferenceableElectrical {

  /**
   * Type of the electrical property in the database
   */
  public enum ELECTRICAL_TYPE {
    /**
     * Net, voltage in volt
     */
    NET,
    /**
     * Terminal, current in ampere
     */
    TERMINAL,
    /**
     * Operating point
     */
    OPPOINT
  }

  /**
   * Get an identifier as string
   *
   * @return identifier
   */
  public String getNetlistIdentifier();

  /**
   * Get hierarchical address to the corresponding net, terminal or operating
   * point
   *
   * @return address
   */
  public String[] getAddress();

  /**
   * Get the type of the {@link ReferenceableElectrical} as enum
   * {@link ELECTRICAL_TYPE}
   *
   * @return type of the electrical property
   */
  public ELECTRICAL_TYPE getType();
}