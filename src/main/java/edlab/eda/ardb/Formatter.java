package edlab.eda.ardb;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Class with formatters for numeric values
 */
public final class Formatter {

  /**
   * Format a number to a string in a engineering notation
   * 
   * @param num Number
   * @return string
   */
  public static String format(final double num) {
    return Formatter.format(new BigDecimal(num, MathContext.DECIMAL64));
  }

  /**
   * Format a number to a string in a engineering notation
   * 
   * @param num Number
   * @return string
   */
  public static String format(final BigDecimal num) {

    final BigDecimal number = new BigDecimal(num.toString());

    String retval = "";

    if (number.compareTo(BigDecimal.ZERO) == 0) {
      return "0";
    }

    final int prefix = (int) (3
        * Math.round((Math.log10(number.abs().doubleValue()) / 3.0) - 0.39));

    if (prefix > 0) {
      retval += number.divide(new BigDecimal("10").pow(prefix))
          .stripTrailingZeros().toPlainString();
    } else if (prefix == 0) {
      retval += number.stripTrailingZeros().toPlainString();
    } else {
      retval += number.multiply(new BigDecimal("10").pow(-prefix))
          .stripTrailingZeros().toPlainString();
    }

    switch (prefix) {
    case 24:
      retval += "Y";
      break;
    case 21:
      retval += "Z";
      break;
    case 18:
      retval += "E";
      break;
    case 15:
      retval += "P";
      break;
    case 12:
      retval += "T";
      break;
    case 9:
      retval += "G";
      break;
    case 6:
      retval += "M";
      break;
    case 3:
      retval += "k";
      break;
    case 0:
      retval += "";
      break;
    case -3:
      retval += "m";
      break;
    case -6:
      retval += "u";
      break;
    case -9:
      retval += "n";
      break;
    case -12:
      retval += "p";
      break;
    case -15:
      retval += "f";
      break;
    case -18:
      retval += "a";
      break;
    case -21:
      retval += "z";
      break;
    case -24:
      retval += "y";
      break;
    default:
      retval = number.toEngineeringString();
      break;
    }
    return retval;
  }
}