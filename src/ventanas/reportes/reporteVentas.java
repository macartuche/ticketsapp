/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas.reportes;

import controllers.BillingJpaController;
import entities.Billing;
import entities.DetailBilling;
import entities.Product;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utilitarios.Utilitario;
import ventanas.administracion.LoginApp;
import ventanas.mainForm;

/**
 *
 * @author macbookpro
 */
public class reporteVentas extends javax.swing.JPanel {

    static BillingJpaController controller;
    static List<Billing> billings;
    static BigDecimal totalReport;

    /**
     * Creates new form reportes
     */
    public reporteVentas() {
        initComponents();
        controller = new BillingJpaController();
        printBTN.setEnabled(false);
        printUnificadoBTN.setEnabled(false);
        printTicketsBTN.setEnabled(false);
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
        fromDate.setDate(new Date());
        untilDate = new com.toedter.calendar.JDateChooser();
        untilDate.setDate(new Date());
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        criteria = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        dBTable1 = new quick.dbtable.DBTable();
        printBTN = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        totalLbl = new javax.swing.JLabel();
        printUnificadoBTN = new javax.swing.JButton();
        printTicketsBTN = new javax.swing.JButton();

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
        jLabel1.setText("Ventas realizadas");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("* Desde: ");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("* Hasta:  ");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Cédula/Ruc o nombre de cliente: ");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
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

        jXTitledPanel1.setTitle("Listado de ventas realizadas");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

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

        jButton5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/salir.png"))); // NOI18N
        jButton5.setText("Salir");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Total: ");

        totalLbl.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        totalLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        printUnificadoBTN.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        printUnificadoBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/printer67.png"))); // NOI18N
        printUnificadoBTN.setText("Imprimir por clientes");
        printUnificadoBTN.setEnabled(false);
        printUnificadoBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        printUnificadoBTN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        printUnificadoBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printUnificadoBTNActionPerformed(evt);
            }
        });

        printTicketsBTN.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        printTicketsBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/printer67.png"))); // NOI18N
        printTicketsBTN.setText("Imprimir por tickets");
        printTicketsBTN.setEnabled(false);
        printTicketsBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        printTicketsBTN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        printTicketsBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printTicketsBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dBTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(totalLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                                .addComponent(printTicketsBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(printUnificadoBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(printBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(17, 17, 17))
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTitledPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(printBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(printUnificadoBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(printTicketsBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dBTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(totalLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(criteria, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(untilDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jSeparator2)))
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(untilDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void printBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBTNActionPerformed

        String reportPath = Utilitario.getValue("pathJasper") + "ventasReport.jasper";

        try {
            Map parametersMap = new HashMap();
            parametersMap.put("totalReport", totalReport);

            FileInputStream fis = new FileInputStream(reportPath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(billings);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametersMap, beanCollectionDataSource);
            // view report to UI
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException ex) {
            Logger.getLogger(reporteVentas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(reporteVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_printBTNActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        criteria.setText("");
        fromDate.setDate(null);
        untilDate.setDate(null);
        printBTN.setEnabled(false);

        fijarDatos(new ArrayList<Billing>());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int index = mainForm.pestanias.getSelectedIndex();
        if (index != -1) {
            mainForm.pestanias.remove(index);
            mainForm.CerrarPestana(9);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String criterio = criteria.getText();

        if (fromDate.getDate() == null || untilDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione el rango de fechas", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Calendar start = Calendar.getInstance();
        start.setTime(fromDate.getDate());
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);

        Calendar end = Calendar.getInstance();
        end.setTime(untilDate.getDate());
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 0);

        Query q = controller.getEntityManager().createQuery("SELECT b FROM Billing b"
                + " WHERE b.emissiondate between :startDate"
                + " and :endDate"
                + " and (b.clientProviderid.personId.passport like :criteria or"
                + " lower(b.clientProviderid.personId.lastname) like :criteria)  and b.state=:state  and b.user=:user order by b.emissiondate desc");
        q.setParameter("startDate", start.getTime());
        q.setParameter("endDate", end.getTime());
        q.setParameter("criteria", criterio.toLowerCase() + "%");
        q.setParameter("state", "Pagada");
        q.setParameter("user", LoginApp.userLogged);
        BigDecimal totalReport = BigDecimal.ZERO;

        billings = q.getResultList();
        fijarDatos(billings);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void printUnificadoBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printUnificadoBTNActionPerformed
        if (!billings.isEmpty()) {

            //consultar nuevamente agrupando los usuarios
            Calendar start = Calendar.getInstance();
            start.setTime(fromDate.getDate());
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);

            Calendar end = Calendar.getInstance();
            end.setTime(untilDate.getDate());
            end.set(Calendar.HOUR_OF_DAY, 23);
            end.set(Calendar.MINUTE, 59);
            end.set(Calendar.SECOND, 0);

            String query = "select sum(b.total),  b.clientProviderid.personId.names, b.clientProviderid.personId.passport from Billing b "
                    + "where  b.emissiondate between :start "
                    + "and :end  and b.state=:state  and b.user=:user  group by b.clientProviderid.personId.names";

            Query q = controller.getEm().createQuery(query);
            q.setParameter("start", start.getTime());
            q.setParameter("end", end.getTime());
            q.setParameter("user", LoginApp.userLogged);
            q.setParameter("state", "Pagada");

            BigDecimal totalReport = BigDecimal.ZERO;

            List<Object[]> temp = q.getResultList();
            BigDecimal totalBilling = BigDecimal.ZERO;
            List<Billing> tickets = new ArrayList<>();

            for (Object[] bill : temp) {
                totalBilling = new BigDecimal(bill[0].toString());
                Billing newBilling = new Billing();
                newBilling.setTotal(totalBilling);
                newBilling.setClienteName(bill[1].toString());
                newBilling.setPassportClient(bill[2].toString());
                totalReport = totalReport.add(totalBilling);
                tickets.add(newBilling);
            }

            String reportPath = Utilitario.getValue("pathJasper") + "ventasA7.jasper";

            try {
                Map parametersMap = new HashMap();
                parametersMap.put("total", totalReport);
                parametersMap.put("desde", fromDate.getDate());
                parametersMap.put("hasta", untilDate.getDate());

                FileInputStream fis = new FileInputStream(reportPath);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
                JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(tickets);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametersMap, beanCollectionDataSource);
                // view report to UI 
//                JasperViewer.viewReport(jasperPrint, false);
                JasperPrintManager.printReport(jasperPrint, false);
            } catch (JRException ex) {
                Logger.getLogger(reporteVentas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(reporteVentas.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            printUnificadoBTN.setEnabled(false);
        }
    }//GEN-LAST:event_printUnificadoBTNActionPerformed

    private void printTicketsBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printTicketsBTNActionPerformed
        if (!billings.isEmpty()) {

            //consultar nuevamente agrupando los usuarios
            Calendar start = Calendar.getInstance();
            start.setTime(fromDate.getDate());
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);

            Calendar end = Calendar.getInstance();
            end.setTime(untilDate.getDate());
            end.set(Calendar.HOUR_OF_DAY, 23);
            end.set(Calendar.MINUTE, 59);
            end.set(Calendar.SECOND, 0);

            String query = "select sum(d.total),   d.unitaryPrice,  d.productId, sum(d.quantity) "
                    + "from DetailBilling d "
                    + "where d.billingId.emissiondate between :start "
                    + "and :end  and  d.billingId.state=:state  and d.billingId.user=:user  group by d.productId  ";
            Query q = controller.getEm().createQuery(query);
            q.setParameter("start", start.getTime());
            q.setParameter("end", end.getTime());
            q.setParameter("user", LoginApp.userLogged);
            q.setParameter("state", "Pagada");

            BigDecimal totalReport = BigDecimal.ZERO;

            List<Object[]> temp = q.getResultList();
            BigDecimal totalBilling = BigDecimal.ZERO;
            List<DetailBilling> tickets = new ArrayList<>();

            for (Object[] bill : temp) {
                totalBilling = new BigDecimal(bill[0].toString());
                DetailBilling newDetail = new DetailBilling();
                newDetail.setTotal(totalBilling);
                newDetail.setProductId((Product) bill[2]);
                newDetail.setUnitaryPrice(new BigDecimal(bill[1].toString()));
                newDetail.setQuantity(new BigDecimal(bill[3].toString()));
                tickets.add(newDetail);

                totalReport = totalReport.add(totalBilling);
            }

            String reportPath = Utilitario.getValue("pathJasper") + "ventasA7Tickets.jasper";

            try {
                Map parametersMap = new HashMap();
                parametersMap.put("total", totalReport);
                parametersMap.put("desde", fromDate.getDate());
                parametersMap.put("hasta", untilDate.getDate());;
                parametersMap.put("recaudador", LoginApp.userLogged.getNombres());

                FileInputStream fis = new FileInputStream(reportPath);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
                JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(tickets);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametersMap, beanCollectionDataSource);
                // view report to UI
//                JasperViewer.viewReport(jasperPrint, false);

                JasperPrintManager.printReport(jasperPrint, false);
            } catch (JRException ex) {
                Logger.getLogger(reporteVentas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(reporteVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            printUnificadoBTN.setEnabled(false);
        }
    }//GEN-LAST:event_printTicketsBTNActionPerformed

    public static void verTabla() {
        dBTable1.createControlPanel();
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        billings = controller.findByDayAndCollector(date.getTime(), LoginApp.userLogged);
        fijarDatos(billings);
    }

    private static void fijarDatos(List<Billing> billings) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Billing billing : billings) {
            sum = sum.add(billing.getTotal());
        }

        if (billings.size() > 0) {
            printBTN.setEnabled(true);
            printUnificadoBTN.setEnabled(true);
            printTicketsBTN.setEnabled(true);
        } else {
            printBTN.setEnabled(false);
            printUnificadoBTN.setEnabled(false);
            printTicketsBTN.setEnabled(false);
        }

        totalLbl.setText(sum.toString());
        totalReport = sum;

        try {
            String methodNames[] = {"getFecha", "getFactura", "getCliente", "getTotal"};
            dBTable1.refreshDataObject(billings, methodNames);
            dBTable1.getColumn(0).setPreferredWidth(200);
            dBTable1.getColumn(1).setPreferredWidth(250);
            dBTable1.getColumn(2).setPreferredWidth(200);
            dBTable1.getColumn(3).setPreferredWidth(100);

            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);

            dBTable1.getColumn(3).setCellRenderer(rightRenderer);
        } catch (Exception ex) {
            Logger.getLogger(reporteVentas.class.getName()).log(Level.SEVERE, null, ex);
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
    private static javax.swing.JButton printTicketsBTN;
    private static javax.swing.JButton printUnificadoBTN;
    private static javax.swing.JLabel totalLbl;
    private com.toedter.calendar.JDateChooser untilDate;
    // End of variables declaration//GEN-END:variables
}
