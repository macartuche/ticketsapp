/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas.parqueadero;

import ventanas.ventas.*;
import com.sun.glass.events.KeyEvent;
import controllers.BillingJpaController;
import controllers.ClientProviderJpaController;
import controllers.ConfigurationsJpaController;
import controllers.PersonJpaController;
import controllers.ProductJpaController;
import controllers.exceptions.NonexistentEntityException;
import entities.Billing;
import entities.Configurations;
import entities.DetailBilling;
import entities.Product;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.joda.time.DateTime;
import org.joda.time.Period;
import ventanas.administracion.LoginApp;
import ventanas.mainForm;

/**
 *
 * @author macbookpro
 */
public class ventas extends javax.swing.JPanel {

    static BillingJpaController controller = null;
    static PersonJpaController controllerPerson = null;
    static ProductJpaController controllerProducto = null;
    static ClientProviderJpaController controllerClient = null;
    static ConfigurationsJpaController configController = null;
    public static List<Billing> ventas;

    /**
     * Creates new form ventas
     */
    public ventas() {
        initComponents();
        controller = new BillingJpaController();
        controllerPerson = new PersonJpaController();
        controllerProducto = new ProductJpaController();
        controllerClient = new ClientProviderJpaController();
        configController = new ConfigurationsJpaController();
        verTabla();
    }

    public static void verTabla() {
        dBTable1.createControlPanel();
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        ventas = controller.findByDayAndCollector2(date.getTime(), LoginApp.userLogged);
        dBTable1.setEditable(false);
        fijarDatos(ventas);

    }

    private static void fijarDatos(List<Billing> ventas) {
        try {
            labelResultado.setText("Resultado: " + ventas.size());
            String methodNames[] = {"getFecha", "getPlaca", "getCliente", "getInicio", "getFin", "getNumero", "getTotal", "getEstado"};
            dBTable1.refreshDataObject(ventas, methodNames);
            dBTable1.getColumn(0).setPreferredWidth(150);
            dBTable1.getColumn(1).setPreferredWidth(100);
            dBTable1.getColumn(2).setPreferredWidth(200);
            dBTable1.getColumn(3).setPreferredWidth(100);
            dBTable1.getColumn(4).setPreferredWidth(100);
            dBTable1.getColumn(5).setPreferredWidth(150);
            dBTable1.getColumn(6).setPreferredWidth(100);
            dBTable1.getColumn(7).setPreferredWidth(150);

            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);

            dBTable1.getColumn(3).setCellRenderer(rightRenderer);
            dBTable1.getColumn(4).setCellRenderer(rightRenderer);
        } catch (Exception ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        txtNumFactura = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtRucCi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        dateDesde = new com.toedter.calendar.JDateChooser();
        dateDesde.setDate(new Date());
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dateHasta = new com.toedter.calendar.JDateChooser();
        dateHasta.setDate(new Date());
        jSeparator1 = new javax.swing.JSeparator();
        btnCancelar = new javax.swing.JButton();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        dBTable1 = new quick.dbtable.DBTable();
        labelResultado = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnPago1 = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        btnPago = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Facturas de venta");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Número de factura");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("RUC/CI de cliente:");

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

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Desde:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Hasta:");

        btnCancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/wiping16.png"))); // NOI18N
        btnCancelar.setText("Limpiar");
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        labelResultado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        labelResultado.setText("Resultados: ");

        btnNuevo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/folder112.png"))); // NOI18N
        btnNuevo.setMnemonic('v');
        btnNuevo.setText("Nuevo");
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        btnNuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnNuevoKeyReleased(evt);
            }
        });

        btnPago1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnPago1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/salir.png"))); // NOI18N
        btnPago1.setMnemonic('S');
        btnPago1.setText("Salir");
        btnPago1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPago1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPago1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPago1ActionPerformed(evt);
            }
        });

        btnImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/printer67.png"))); // NOI18N
        btnImprimir.setMnemonic('I');
        btnImprimir.setText("Imprimir");
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnAnular.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/trash.png"))); // NOI18N
        btnAnular.setMnemonic('A');
        btnAnular.setText("Anular");
        btnAnular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAnular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });

        btnPago.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/Banknotes.png"))); // NOI18N
        btnPago.setMnemonic('P');
        btnPago.setText("Pagar");
        btnPago.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPago.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addComponent(labelResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPago, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPago1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dBTable1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPago1, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(btnImprimir)
                    .addComponent(btnAnular)
                    .addComponent(btnNuevo)
                    .addComponent(btnPago))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dBTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel2)
                                .addGap(9, 9, 9))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtRucCi)
                            .addComponent(dateDesde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(txtNumFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addGap(0, 137, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1))
                            .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtRucCi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(txtNumFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(dateHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscar)))
                .addGap(18, 18, 18)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        abrirFormTickets();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void abrirFormTickets() {
        Billing b = new Billing();
        b.setDetailBillingList(new ArrayList<DetailBilling>());
        b.setEmissiondate(new Date());
        b.setUser(LoginApp.userLogged);

        b.setTotal(BigDecimal.ZERO);
        b.setSubtotal(BigDecimal.ZERO);
        EntryParkingForm form = new EntryParkingForm(null, true, b);
        form.setLocationRelativeTo(null);
        form.setVisible(true);

        verTabla();
    }
    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed

        try {
            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int indice = dBTable1.getSelectedRow();
            Billing b = ventas.get(indice);
//            if (!b.getState().equals("GENERADA")) {
//                JOptionPane.showMessageDialog(this, "Sólo se pueden modificar facturas en estado \"GENERADA\".", "ERROR", JOptionPane.ERROR_MESSAGE);
//                return;
//            }

            int confirmado = JOptionPane.showOptionDialog(btnAnular,
                    "  Desea anular la factura con número,      \n"
                    + "   " + b.getNumber() + "!.\n"
                    + " \n", "Alerta",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No", "Cancelar"}, "Si");

            System.out.println("CONFIMADO >>> " + confirmado);
            if (JOptionPane.OK_OPTION == confirmado) {
                System.out.println("si");
                List<DetailBilling> listaDetalle = b.getDetailBillingList();
                BigDecimal cantidad;
                Product product;
                for (DetailBilling detalle : listaDetalle) {

                    cantidad = detalle.getQuantity();
                    product = detalle.getProductId();
                    cantidad = cantidad.add(product.getStock());
                    product.setStock(cantidad);
                    controllerProducto.edit(product);

                }
                b.setState("ANULADA");
                controller.edit(b);
                verTabla();
            } else {
                System.out.println("no");

            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    private void actualizarProductosPorFacturaAnulada() {

    }

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        int indice = dBTable1.getSelectedRow();
        System.out.println("======>" + indice);
        Billing b = ventas.get(indice);
        List<Billing> facturas = new ArrayList<>();
        facturas.add(b);
        Locale local = Locale.getDefault();
        ResourceBundle resource = ResourceBundle.getBundle("values", local);
        String reportPath = resource.getString("pathJasper") + "comPark.jasper";

        //parameters 
        Map<String, Object> params = new HashMap<>();
        params.put("code", "institutionName");
        List<Configurations> list = configController.namedQuery("Configurations.findByCode", params);
        
        Map<String, String> parameters = new HashMap<>();
        parameters.put("institution", list.get(0).getValor());

        try {
            FileInputStream fis = new FileInputStream(reportPath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(facturas);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), beanCollectionDataSource);
            // view report to UI
            JasperViewer.viewReport(jasperPrint, false);
//            JasperPrintManager.printReport(jasperPrint, false);
        } catch (JRException ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagoActionPerformed

        
        int indice = dBTable1.getSelectedRow();
        Billing b = ventas.get(indice);

        if(b.getState().equals("Pagada")){
            JOptionPane.showMessageDialog(this, "El ticket ya ha sido pagado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //calcular el tiempo de pago 
        Date timeStart = b.getDetailBillingList().get(0).getTimestart();

        DateTime start = new DateTime(timeStart);
        Date fin = new Date();
        DateTime end = new DateTime(fin);

        Period period = new Period(start, end);

        int hours = period.getHours();
        int minutes = period.getMinutes();

        String tiempo = period.getHours() + ":" + period.getMinutes() + ":" + period.getMinutes();

        Product product = b.getDetailBillingList().get(0).getProductId();
        BigDecimal price = product.getSaleprice();
        BigDecimal quantity = BigDecimal.ZERO;

        if (hours >= 1) {
            quantity = new BigDecimal(hours);
        }

        if (minutes > 0) {
            quantity = quantity.add(BigDecimal.ONE);
        }

        b.getDetailBillingList().get(0).setPercentageIva(product.getPercentageIva());
        b.getDetailBillingList().get(0).setUnitaryPrice(price);
        b.getDetailBillingList().get(0).setValueIva(price);

        BigDecimal totalIva = price.multiply(quantity).multiply(product.getPercentageIva());
        BigDecimal total = product.getSaleprice().multiply(quantity).add(totalIva);

        total = total.setScale(2, RoundingMode.HALF_UP);

        b.getDetailBillingList().get(0).setValueIva(totalIva);
        b.getDetailBillingList().get(0).setTotalWithTax(total);
        b.getDetailBillingList().get(0).setTotal(total);
        b.getDetailBillingList().get(0).setQuantity(quantity);
        b.getDetailBillingList().get(0).setTimeend(fin);
        b.setTotal(b.getDetailBillingList().get(0).getTotalWithTax());

        CobroParkForm dialog = new CobroParkForm(new javax.swing.JFrame(), Boolean.TRUE, b, tiempo);
        dialog.setVisible(true);
        verTabla();
        
    }//GEN-LAST:event_btnPagoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        String rucci = txtRucCi.getText();
        String numReceipt = txtNumFactura.getText();
        Map<String, Object> variables = new HashMap<String, Object>();
        Calendar start = Calendar.getInstance();
        start.setTime(dateDesde.getDate());
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);

        Calendar end = Calendar.getInstance();
        end.setTime(dateHasta.getDate());
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 0);

        variables.put("startDate", start.getTime());
        variables.put("endDate", end.getTime());
//        if (!rucci.trim().isEmpty()) {
        variables.put("rucci", rucci.toLowerCase() + "%");
//        }
//        if (!numReceipt.trim().isEmpty()) {
        variables.put("numReceipt", numReceipt.toLowerCase() + "%");
//        }
        ventas = controller.namedQuery("Billing.findByFilter", variables);
        System.out.println("=>" + ventas.size());
        fijarDatos(ventas);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        txtNumFactura.setText("");
        txtRucCi.setText("");
        verTabla();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnPago1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPago1ActionPerformed
        int index = mainForm.pestanias.getSelectedIndex();
        if (index != -1) {
            mainForm.pestanias.remove(index);
            mainForm.CerrarPestana(2);
        }
    }//GEN-LAST:event_btnPago1ActionPerformed

    private void btnNuevoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyReleased

    }//GEN-LAST:event_btnNuevoKeyReleased

    private void btnNuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyPressed
        System.out.println("GIM=>" + evt.getKeyCode());
        if (evt.getKeyCode() == KeyEvent.VK_N) {
            abrirFormTickets();
        }
    }//GEN-LAST:event_btnNuevoKeyPressed

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed

    }//GEN-LAST:event_jPanel1KeyPressed

    private void abrirVentana(final Billing b) {
        VentasForm dialog = new VentasForm(new javax.swing.JFrame(), true, b);
        dialog.setVisible(true);
        verTabla();

//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                final VentasForm dialog = new VentasForm(new javax.swing.JFrame(), true, b);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        dialog.dispose();
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPago;
    private javax.swing.JButton btnPago1;
    private static quick.dbtable.DBTable dBTable1;
    private com.toedter.calendar.JDateChooser dateDesde;
    private com.toedter.calendar.JDateChooser dateHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private static javax.swing.JLabel labelResultado;
    private javax.swing.JTextField txtNumFactura;
    private javax.swing.JTextField txtRucCi;
    // End of variables declaration//GEN-END:variables
}
