/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author josimar
 */
import utilitarios.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MainTest extends JFrame {

    public MainTest() throws HeadlessException {
    }

    public MainTest(String title) {
        super(title);

        Collections.sort(DataModel.getData());
        JComboBox combo = new JComboBox(DataModel.getData().toArray());
        combo.setEditable(true);
        combo.setSelectedIndex(-1);
//        JTextField field = (JTextField) combo.getEditor().getEditorComponent();
//        field.setText("");
//        field.addKeyListener(new ComboKeyHandler(combo));
//        field.addMouseListener(new ComboKeyHandler(combo));

        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Suggestion JComboBox"));
        p.add(combo, BorderLayout.NORTH);

        p.setPreferredSize(new Dimension(320, 180));

        this.getContentPane().add(p);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(320, 180));
        this.pack();
        this.setLocationRelativeTo(null);

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

                MainTest gui = new MainTest("Suggestion JComboBox");
                gui.setVisible(true);

            }
        });
    }

}
