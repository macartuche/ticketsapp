/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas.ventas;

import controllers.ClientProviderJpaController;
import controllers.PersonJpaController;
import controllers.exceptions.NonexistentEntityException;
import entities.ClientProvider;
import entities.Person;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utilitarios.TabsIndex;
import ventanas.administracion.usuarios;
import ventanas.mainForm;

/**
 *
 * @author macbookpro
 */
public class clientes extends javax.swing.JPanel {

    static ClientProviderJpaController controller = null;
    static PersonJpaController controllerPerson = null;
    public static List<ClientProvider> clientProviders;

    /**
     * Creates new form clientes
     */
    public clientes() {
        initComponents();
        controller = new ClientProviderJpaController();
        controllerPerson = new PersonJpaController();
        verTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtCriterio = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        resultados = new javax.swing.JLabel();
        dBTable1 = new quick.dbtable.DBTable();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnActivarDesativar = new javax.swing.JButton();
        btnActivarDesativar1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Criterio: ");

        btnBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/magnifier12.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Clientes");

        btnLimpiar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/wiping16.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLimpiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jXTitledPanel1.setTitle("Listado de clientes");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        resultados.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        resultados.setText("Resultados: ");

        btnNuevo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/folder112.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/edit26.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnActivarDesativar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnActivarDesativar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/trash.png"))); // NOI18N
        btnActivarDesativar.setText("Act/Des");
        btnActivarDesativar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnActivarDesativar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnActivarDesativar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarDesativarActionPerformed(evt);
            }
        });

        btnActivarDesativar1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnActivarDesativar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/salir.png"))); // NOI18N
        btnActivarDesativar1.setText("Salir");
        btnActivarDesativar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnActivarDesativar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnActivarDesativar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarDesativar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addComponent(resultados, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(251, 251, 251)
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActivarDesativar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActivarDesativar1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(dBTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)))
                .addGap(23, 23, 23))
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnActivarDesativar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnActivarDesativar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dBTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(101, 101, 101))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                .addGap(6, 6, 6)))
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar)
                        .addGap(232, 232, 232))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        int indice = dBTable1.getSelectedRow();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            ClientProvider cp = clientProviders.get(indice);
            abrirVentana(cp);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnActivarDesativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarDesativarActionPerformed

        int indice = dBTable1.getSelectedRow();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            ClientProvider cp = clientProviders.get(indice);
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Desea activar/desactivar el registro", "Confirmación", dialogButton);
            if (dialogResult == 0) {
                //System.out.println("Yes option");
                Boolean state = (cp.getActiveclient()) ? false : true;
                cp.setActiveclient(state);
                actualizar(cp);
                fijarDatos(clientProviders);
            } else {
                //System.out.println("No Option");
            }
        }

    }//GEN-LAST:event_btnActivarDesativarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        ClientProvider cp = new ClientProvider();
        cp.setClient(Boolean.TRUE);
        cp.setActiveclient(Boolean.TRUE);
        cp.setProvider(Boolean.FALSE);
        cp.setActiveprovider(Boolean.FALSE);
        cp.setPersonId(new Person());
        cp.getPersonId().setSex("M");
        abrirVentana(cp);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtCriterio.setText("");
        verTabla();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    public static void verTabla() {
        dBTable1.createControlPanel();
        dBTable1.setEditable(false);
        Map<String, Object> filtro = new HashMap<>();
        filtro.put("client", Boolean.TRUE);
        clientProviders = controller.getClients("ClientProvider.findByClient",
                filtro);
        fijarDatos(clientProviders);

    }

    private static void fijarDatos(List<ClientProvider> clientProviders) {
        try {
            resultados.setText("Resultados: " + clientProviders.size());
            String methodNames[] = {"getNombres", "getApellidos", "getIdentificacion", "getEstado"};
            dBTable1.refreshDataObject(clientProviders, methodNames);
            dBTable1.getColumn(0).setPreferredWidth(300);
            dBTable1.getColumn(1).setPreferredWidth(300);
            dBTable1.getColumn(2).setPreferredWidth(120);
            dBTable1.getColumn(3).setPreferredWidth(100);
        } catch (Exception ex) {
            Logger.getLogger(clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void abrirVentana(final ClientProvider cp) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                final ClienteForm dialog = new ClienteForm(new javax.swing.JFrame(), true, cp);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        dialog.dispose();
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        String ident = txtCriterio.getText();
        if (!ident.trim().isEmpty()) {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("client", Boolean.TRUE);
            variables.put("criteria", ident.toLowerCase() + "%");
            List<ClientProvider> busqueda = controller.namedQuery("ClientProvider.findByClientCriteria", variables);
            System.out.println("=>" + busqueda.size());
            fijarDatos(busqueda);
        }else{
            verTabla();
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActivarDesativar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarDesativar1ActionPerformed
        int index = mainForm.pestanias.getSelectedIndex();
        if (index != -1) {
            mainForm.pestanias.remove(index);
            mainForm.CerrarPestana(TabsIndex.CLIENTES.getIndex());
        }
         
    }//GEN-LAST:event_btnActivarDesativar1ActionPerformed

    public static void crear(ClientProvider cp) {
        controllerPerson.create(cp.getPersonId());
        controller.create(cp);
        verTabla();
    }

    public static void actualizar(ClientProvider cp) {
        try {
            controllerPerson.edit(cp.getPersonId());
            controller.edit(cp);
            verTabla();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActivarDesativar;
    private javax.swing.JButton btnActivarDesativar1;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevo;
    private static quick.dbtable.DBTable dBTable1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private static javax.swing.JLabel resultados;
    private javax.swing.JTextField txtCriterio;
    // End of variables declaration//GEN-END:variables
}
