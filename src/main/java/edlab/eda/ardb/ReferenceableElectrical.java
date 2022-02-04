package edlab.eda.ardb;

/**
 * A class which implements this interface represents an object that is able to
 * provide an identifier that references a net, terminal or operating point
 */
public interface ReferenceableElectrical {

  public enum TYPE {
    NET, TERMINAL, OPPOINT
  }

  /**
   * Get an identifier as string
   *
   * @return identifier
   */
  public String getIdentifier();

  /**
   * Get hierarchical address to the corresponding net, terminal or operating
   * point
   * 
   * @return address
   */
  public String[] getAddress();

  /**
   * Get the type of the {@link ReferenceableElectrical} as enum {@link TYPE}
   * 
   * @return type of the electrical property
   */
  public TYPE getType();
}