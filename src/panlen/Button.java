
package panlen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Hiruw
 */
public class Button extends javax.swing.JPanel {

    public static JButton createButton(
            String text,
            int width,
            int height,
            Color bgColor,
            Color fgColor,
            Font font,
            ActionListener action) {

        JButton button = new JButton(text);

        Dimension size = new Dimension(width, height);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);

        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(font);

        button.setFocusPainted(false);  
        button.setBorderPainted(false); 
        button.setOpaque(true);
 
        if (action != null) {
            button.addActionListener(action);
        }

        return button;
    }
    
        public static JPanel wrapButton(JButton button, int padding) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel.add(button, BorderLayout.CENTER);
        return panel;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setAutoscrolls(true);
        setLayout(new java.awt.BorderLayout());
        add(jButton1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
