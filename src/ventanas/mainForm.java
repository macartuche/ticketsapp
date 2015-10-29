/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import entities.Users;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import ventanas.administracion.UsuarioForm;
import ventanas.administracion.configuraciones;
import ventanas.administracion.usuarios;
import ventanas.compras.Compras;
import ventanas.compras.CtasPagar;
import ventanas.compras.Proveedores;
import ventanas.inventario.grupos;
import ventanas.inventario.products;
import ventanas.reportes.reporteCobros;
import ventanas.reportes.reporteMasVendido;
import ventanas.reportes.reporteVentas;
import ventanas.ventas.clientes;
import ventanas.ventas.ctasCobrar;
import ventanas.ventas.ventas;

/**
 *
 * @author macbookpro
 */
public class mainForm extends javax.swing.JFrame {

    public static List<Integer> pestanasAbiertas;

    private void createSubTree(javax.swing.tree.DefaultMutableTreeNode root,
            javax.swing.tree.DefaultMutableTreeNode sub, String key, String name) {

        if (UsuarioForm.llaves.containsKey(key) && UsuarioForm.llaves.get(key) == 1) {
            javax.swing.tree.DefaultMutableTreeNode nuevo = new javax.swing.tree.DefaultMutableTreeNode(name);
            sub.add(nuevo);
            root.add(sub);
        }
    }

    private void createTree(Users user) {
        //crear el mapa de permisos
        UsuarioForm.createHashMap(user.getPermissions());

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Inventario");
        createSubTree(treeNode1, treeNode2, "familia", "Familia");
        createSubTree(treeNode1, treeNode2, "producto", "Productos");

        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ventas");
        createSubTree(treeNode1, treeNode3, "clientes", "Clientes");
        createSubTree(treeNode1, treeNode3, "factura", "Facturas de venta");

        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Cuentas");
         createSubTree(treeNode1, treeNode4, "ctas", "Cuentas por cobrar");
         
        javax.swing.tree.DefaultMutableTreeNode treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Reportes");
        createSubTree(treeNode1, treeNode5, "reporteVentas", "Ventas realizadas");
        createSubTree(treeNode1, treeNode5, "reporteCta", "Ctas por cobrar");
        createSubTree(treeNode1, treeNode5, "reporteMas", "Productos más vendidos");
        
        javax.swing.tree.DefaultMutableTreeNode treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Administración");
    createSubTree(treeNode1, treeNode6, "usuarios", "Usuarios");
    createSubTree(treeNode1, treeNode6, "configuracion", "Configuraciones");
        //dibujar de acuerdo al caso
//        if (UsuarioForm.llaves.containsKey("producto") && UsuarioForm.llaves.get("producto") == 1) {
//            javax.swing.tree.DefaultMutableTreeNode prod = new javax.swing.tree.DefaultMutableTreeNode("Productos");
//            treeNode2.add(prod);
//            treeNode1.add(treeNode2);
//        }
//
//        if (UsuarioForm.llaves.containsKey("clientes") && UsuarioForm.llaves.get("clientes") == 1) {
//            javax.swing.tree.DefaultMutableTreeNode prod = new javax.swing.tree.DefaultMutableTreeNode("Productos");
//            treeNode2.add(prod);
//            treeNode1.add(treeNode2);
//        }
//
////        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Productos");
////        treeNode2.add(treeNode3);
////        treeNode1.add(treeNode2);
//        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Ventas");
//        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Clientes");
//        treeNode2.add(treeNode3);
//        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Facturas de venta");
//        treeNode2.add(treeNode3);
//        treeNode1.add(treeNode2);
//        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Cuentas");
//        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Cuentas por cobrar");
//        treeNode2.add(treeNode3);
//        treeNode1.add(treeNode2);
//        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Reportes");
//        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ventas realizadas");
//        treeNode2.add(treeNode3);
//        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ctas por cobrar");
//        treeNode2.add(treeNode3);
//        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Productos más vendidos");
//        treeNode2.add(treeNode3);
//        treeNode1.add(treeNode2);
//        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Administración");
//        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Usuarios");
//        treeNode2.add(treeNode3);
//        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Configuraciones");
//        treeNode2.add(treeNode3);
//        treeNode1.add(treeNode2);
        arbol.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
    }

    /**
     * Creates new form mainForm
     */
    public mainForm(Users user) {
        initComponents();
        createTree(user);
        pestanasAbiertas = new ArrayList<>();
//        pestanias.setUI(new CustomTabbedPaneUI());
        arbol.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                Object nodeInfo = node.getUserObject();
                String option = nodeInfo.toString();
                switch (option) {
//                    case "Facturas de compra":
//                        Compras comp = new Compras();
//                        crearPestana(comp, "Compras ",1); 
//                        break;
//                    case "Proveedores":
//                        Proveedores prov = new Proveedores();
//                        crearPestana(prov, "Proveedores  ",2); 
//                        break;
                    case "Clientes":
                        clientes panel = new clientes();
                        crearPestana(panel, "Clientes ", 1);
                        break;
                    case "Facturas de venta":
                        ventas ven = new ventas();
                        crearPestana(ven, "Facturas de venta", 2);
                        break;
                    case "Cuentas por cobrar":
                        ctasCobrar ctaCobrar = new ctasCobrar();
                        crearPestana(ctaCobrar, "Cuentas por cobrar", 3);
                        break;
                    case "Cuentas por pagar":
                        CtasPagar ctaPagar = new CtasPagar();
                        crearPestana(ctaPagar, "Cuentas por pagar", 4);
                        break;
                    case "Productos":
                        products prod = new products();
                        crearPestana(prod, "Productos ", 5);
                        break;

                    case "Usuarios":
                        usuarios pan = new usuarios();
                        crearPestana(pan, "Usuarios     ", 6);
                        break;
                    case "Familia":
                        grupos grup = new grupos();
                        crearPestana(grup, "Grupos de productos  ", 7);
                        break;
                    case "Configuraciones":
                        configuraciones config = new configuraciones();
                        crearPestana(config, "Configuraciones ", 8);
                        break;
                    case "Ventas realizadas":
                        reporteVentas ventas = new reporteVentas();
                        crearPestana(ventas, "Ventas realizadas  ", 9);
                        break;
                    case "Ctas por cobrar":
                        reporteCobros cobros = new reporteCobros();
                        crearPestana(cobros, "Ctas por cobrar  ", 10);
                        break;
                    case "Productos más vendidos":
                        reporteMasVendido masvendido = new reporteMasVendido();
                        crearPestana(masvendido, "Productos más vendidos", 11);
                        break;
                }
            }
        });
//        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
    }

    /**
     * verifica si la pestana ha sido abierta
     *
     * @param panel
     * @param titulo
     * @param pestana
     */
    private void crearPestana(JPanel panel, String titulo, Integer pestana) {
        if (!pestanasAbiertas.contains(pestana)) {
            pestanasAbiertas.add(pestana);
            pestanias.add(titulo, panel);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        arbol = new javax.swing.JTree();
        pestanias = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Facturación e Inventario");
        setBackground(new java.awt.Color(215, 225, 238));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Navegación");

        jScrollPane1.setBackground(new java.awt.Color(215, 225, 238));
        jScrollPane1.setForeground(new java.awt.Color(215, 225, 238));

        arbol.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        arbol.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        arbol.setMaximumSize(new java.awt.Dimension(180, 114));
        arbol.setPreferredSize(new java.awt.Dimension(150, 114));
        arbol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                arbolMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(arbol);

        jSplitPane1.setLeftComponent(jScrollPane1);

        pestanias.setBackground(new java.awt.Color(255, 255, 255));
        pestanias.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/logoGrande.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(142, 142, 142))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(72, 72, 72))
        );

        pestanias.addTab("Bienvenida", jPanel1);

        jSplitPane1.setRightComponent(pestanias);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSplitPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1)))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void arbolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arbolMouseClicked

    }//GEN-LAST:event_arbolMouseClicked

    public static void CerrarPestana(Integer numeroPestana) {
        pestanasAbiertas.remove(numeroPestana);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new mainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arbol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    public static javax.swing.JTabbedPane pestanias;
    // End of variables declaration//GEN-END:variables
}
