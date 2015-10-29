/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas.reportes;

import controllers.AccountJpaController;
import controllers.BillingJpaController;
import controllers.ConfigurationsJpaController;
import entities.Account;
import entities.Billing;
import entities.Configurations;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utilitarios.Utilitario;
import ventanas.mainForm;

/**
 *
 * @author macbookpro
 */
public class reporteCobros extends javax.swing.JPanel {

    static AccountJpaController controller;
    static List<Account> account;
    static BigDecimal totalReport;
    static ConfigurationsJpaController controllerConfig = null;

    /**
     * Creates new form reportes
     */
    public reporteCobros() {
        initComponents();
        controller = new AccountJpaController();
        controllerConfig = new ConfigurationsJpaController();
        printBTN.setEnabled(false);
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

        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        fromDate = new com.toedter.calendar.JDateChooser();
        untilDate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        criteria = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        printBTN = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        dBTable1 = new quick.dbtable.DBTable();
        totalLbl = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jButton2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton2.setText("Realizar gráfico");

        setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/report1.png"))); // NOI18N
        jButton1.setText("Generar");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Cobros pendientes");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("* Desde: ");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("* Hasta:  ");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Cédula/Ruc o nombre de cliente: ");

        jLabel5.setFont(new java.awt.Font("Arial", 2, 10)); // NOI18N
        jLabel5.setText("Los campos marcados con (*) son obligatorios");

        jButton4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/wiping16.png"))); // NOI18N
        jButton4.setText("Limpiar");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        printBTN.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        printBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/printer67.png"))); // NOI18N
        printBTN.setText("Imprimir");
        printBTN.setEnabled(false);
        printBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        printBTN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        printBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBTNActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/salir.png"))); // NOI18N
        jButton5.setText("Salir");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        totalLbl.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        totalLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Total: ");

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addGap(489, 489, 489)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dBTable1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                                .addComponent(printBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(14, 14, 14))
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(printBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dBTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totalLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(criteria))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(untilDate, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(7, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(criteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(untilDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void printBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBTNActionPerformed
 
        String reportPath = Utilitario.getValue("pathJasper")+"cobrosReport.jasper"; 
        try {
            Map parametersMap = new HashMap();
            parametersMap.put("totalReport", totalReport);

            FileInputStream fis = new FileInputStream(reportPath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(account);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametersMap, beanCollectionDataSource);
            // view report to UI
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException ex) {
            Logger.getLogger(reporteCobros.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(reporteCobros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_printBTNActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        criteria.setText("");
        fromDate.setDate(null);
        untilDate.setDate(null);
        printBTN.setEnabled(false);
        verTabla();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int index = mainForm.pestanias.getSelectedIndex();
        if (index != -1) {
            mainForm.pestanias.remove(index);
            mainForm.CerrarPestana(10);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String criterio = criteria.getText();
        Date start = fromDate.getDate();
        Date end = untilDate.getDate();

             System.out.println("=>"+start);
            System.out.println("=>"+end);
        if (start != null && end != null) {
            Map<String, Object> filtro = new HashMap<>();
            filtro.put("start", start);
            filtro.put("end", end);
            filtro.put("criteria", criteria + "%");
            Query q = controller.getEntityManager().createQuery("SELECT a FROM Account a"
                    + " WHERE a.dateCreation between :startDate"
                    + " and :endDate"
                    + " and (a.billingId.clientProviderid.personId.passport like :criteria or"
                    + " lower(a.billingId.clientProviderid.personId.lastname) like :criteria)");
            q.setParameter("startDate", start);
            q.setParameter("endDate", end);
            q.setParameter("criteria", criterio.toLowerCase() + "%");
            account =  q.getResultList();
            //obtener la variable d enumero de cuotas
            System.out.println("=>"+start);
            System.out.println("=>"+end);
            fijarDatos(account);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione el rango de fechas", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void verTabla() {
        dBTable1.createControlPanel();
        account = controller.findAccountEntities();
        fijarDatos(account);
    }

    private static void fijarDatos(List<Account> accounts) {
        BigDecimal sum = BigDecimal.ZERO;
//        for (Account account : accounts) {
//        }
        Query q = controllerConfig.getEntityManager().createNamedQuery("Configurations.findByCode");
        q.setParameter("code", "quotesNumber");
        List<Configurations> configurations = q.getResultList();
        totalReport = BigDecimal.ZERO;
        System.out.println("====>" + configurations.size());
        if (configurations.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La variable quotesNumber, no se encuentra configurada", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            Integer quotesNumber = Integer.valueOf(configurations.get(0).getValue());
            for (Account account1 : account) {
                BigDecimal quote = account1.getTotal().divide(new BigDecimal(quotesNumber), 2, RoundingMode.HALF_UP);
                final BigDecimal finalQuote = quote.setScale(2, RoundingMode.HALF_UP);
                account1.setSaldo(finalQuote);
                sum = sum.add(finalQuote);
            }
        }

        if (accounts.size() > 0) {
            printBTN.setEnabled(true);
        }

        totalLbl.setText(sum.toString());
        totalReport = sum;

        try {
            String methodNames[] = {"getFecha", "getFactura", "getCliente", "getSaldo"};
            dBTable1.refreshDataObject(accounts, methodNames);
            dBTable1.getColumn(0).setPreferredWidth(150);
            dBTable1.getColumn(1).setPreferredWidth(200);
            dBTable1.getColumn(2).setPreferredWidth(200);
            dBTable1.getColumn(3).setPreferredWidth(125);
        } catch (Exception ex) {
            Logger.getLogger(reporteCobros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField criteria;
    private static quick.dbtable.DBTable dBTable1;
    private com.toedter.calendar.JDateChooser fromDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private static javax.swing.JButton printBTN;
    private static javax.swing.JLabel totalLbl;
    private com.toedter.calendar.JDateChooser untilDate;
    // End of variables declaration//GEN-END:variables
}
