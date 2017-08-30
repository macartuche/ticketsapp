/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

/**
 *
 * @author josimar
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class MainGUI extends JFrame implements ActionListener {

    public MainGUI() throws HeadlessException {
    }

    public MainGUI(String title) {
        super(title);

        Collections.sort(DataModel.getData());
        JComboBox combo = new JComboBox(DataModel.getData().toArray());
        combo.setEditable(true);
        combo.setSelectedIndex(-1);
        JTextField field = (JTextField) combo.getEditor().getEditorComponent();
        field.setText("");
        field.addKeyListener(new ComboKeyHandler(combo));
//        field.addMouseListener(new ComboKeyHandler(combo));
        combo.addActionListener(this);
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Suggestion JComboBox"));
        p.add(combo, BorderLayout.NORTH);

        p.setPreferredSize(new Dimension(320, 180));

        this.getContentPane().add(p);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(320, 180));
        this.pack();
        this.setLocationRelativeTo(null);

        combo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboMouseClicked(evt);
            }
        });

    }

    private void comboMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:

        JComboBox combo = (JComboBox) evt.getComponent();

        JOptionPane.showMessageDialog(this, "seleccionado >>> " + combo.getSelectedIndex(), "Error!", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MainGUI gui = new MainGUI("Suggestion JComboBox");
                gui.setVisible(true);

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        System.out.println(">>>>>>>>");
    }

}
