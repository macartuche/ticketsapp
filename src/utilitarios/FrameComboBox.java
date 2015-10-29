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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class FrameComboBox extends JFrame implements ActionListener, KeyListener, MouseListener {

    protected JComboBox jComboBox = new JComboBox(new String[]{"Barato", "Batidora", "Bruja", "Bruto", "Bueno", "Bonito", "GUJ", "OS"});
    protected JPanel jPanel = new JPanel();

    private boolean shouldHide = false;
    private final Vector list = new Vector();

    public FrameComboBox() throws HeadlessException {
        super();
        this.setDefaultCloseOperation(3);
        this.setPreferredSize(new Dimension(640, 480));
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        jComboBox.setEditable(true);
        JTextComponent editor = (JTextComponent) jComboBox.getEditor().getEditorComponent();
        editor.addKeyListener(this);
        jComboBox.addActionListener(this);
//        jComboBox.addMouseListener(this);
        /* jComboBox.addActionListener(this);*/
        /* jComboBox.addKeyListener(this);*/
        jPanel.add(jComboBox);
        Container container = this.getContentPane();
        container.add(jPanel);
    }

    public static void main(String[] args) {
        FrameComboBox fs = new FrameComboBox();
        fs.pack();
        fs.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        /* System.out.println ( jComboBox.getSelectedItem().toString()); */
        System.out.println("AQUI >>>");
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("HOLA");

//        for (int i = 0; i < jComboBox.getModel().getSize(); i++) {
//            list.addElement((String) jComboBox.getItemAt(i));
//        }
//        String text = ((JTextField) e.getSource()).getText();
//        if (text.length() == 0) {
//            setSuggestionModel(jComboBox, new DefaultComboBoxModel(list), "");
//            jComboBox.hidePopup();
//        } else {
//            ComboBoxModel m = getSuggestedModel(list, text);
//            if (m.getSize() == 0 || shouldHide) {
//                jComboBox.hidePopup();
//            } else {
//                setSuggestionModel(jComboBox, m, text);
//                jComboBox.showPopup();
//            }
//        }
        String text = ((JTextField) e.getSource()).getText();
        System.out.println("texto >>> " + text);
        try {
            if (text.equals("")) {
                System.out.println("no data");

                jComboBox.setModel(getSuggestedModel(new Vector(), ""));

            } else {
                System.out.println("data");
                jComboBox.setModel(getSuggestedModel(new Vector(), ""));
            }
        } catch (Exception ex) {
        }

    }

//    public void generarListaNoAprobados() {
//        bbddal = new Bbdd_Alumno();
//        try {
//
//            la = bbddal.consultarNombresAlumnosNoAprobados(jtxt_datos.getText(), isidro.conectar());
//            System.out.println("Hola");
//            jlista_noAprobados.setModel(new javax.swing.AbstractListModel() {
//
//                String[] strings = listaNoAprobados(la);
//
//                @Override
//                public int getSize() {
//                    return strings.length;
//                }
//
//                @Override
//                public Object getElementAt(int i) {
//                    return strings[i];
//                }
//            });
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Vista_SeleccionYAdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void keyTyped(KeyEvent e) {
        /*System.out.println ( jComboBox.getSelectedItem().toString());*/
    }

    private static void setSuggestionModel(JComboBox comboBox, ComboBoxModel mdl, String str) {
        comboBox.setModel(mdl);
        comboBox.setSelectedIndex(-1);
        ((JTextField) comboBox.getEditor().getEditorComponent()).setText(str);
    }

    private static ComboBoxModel getSuggestedModel(Vector list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for (Object obj : list) {
            String s = (String) obj;
            if (s.toUpperCase().contains(text.toUpperCase())) {
                m.addElement(s);
            }
        }
        return m;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("1");
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("2");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("3");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("4");
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("5");
    }

}
