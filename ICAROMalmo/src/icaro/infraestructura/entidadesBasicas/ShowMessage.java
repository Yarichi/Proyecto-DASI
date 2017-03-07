// *****************************************************************************

package icaro.infraestructura.entidadesBasicas;

import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * @author Andres Picazo Cuesta
 */
public class ShowMessage {
    public static void Warning (String title, String text) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void Exception (String title, String text, Exception e) {
        JOptionPane.showMessageDialog(null, text+"\n"+e.getMessage(), title, JOptionPane.ERROR_MESSAGE);
    }

    public static void Info (String title, String text) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void IconInfo (String title, String text, Icon icon) {
//        JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.PLAIN_MESSAGE, icon);
    }
}
// *****************************************************************************
