/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas.parqueadero;

import com.sun.glass.events.KeyEvent;
import ventanas.ventas.*;
import controllers.BillingJpaController;
import controllers.ClientProviderJpaController;
import controllers.ConfigurationsJpaController;
import controllers.PersonJpaController;
import controllers.ProductJpaController;
import controllers.UsersJpaController;
import controllers.exceptions.NonexistentEntityException;
import entities.Billing;
import entities.ClientProvider;
import entities.Configurations;
import entities.DetailBilling;
import entities.Product;
import entities.Users;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
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
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import utilitarios.TabsIndex;
import ventanas.administracion.LoginApp;
import ventanas.mainForm;

/**
 *
 * @author macbookpro
 */
public class ventas extends javax.swing.JPanel implements KeyListener {

    static BillingJpaController controller = null;
    static PersonJpaController controllerPerson = null;
    static ProductJpaController controllerProducto = null;
    static ClientProviderJpaController controllerClient = null;
    static ConfigurationsJpaController configController = null;
    public ProductJpaController productController;
    static UsersJpaController userscontroller;
    public static List<Billing> ventas;
    private String secuencial;
    private int numberTickets;
    private int numSecuencial;
    private Configurations config;

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
        productController = new ProductJpaController();
        userscontroller = new UsersJpaController();
        secuencial = "";
        checkInitVars();
        verTabla(false);
    }

    /**
     * Chequear las variables de configuracion necesarias
     */
    private void checkInitVars() {
        //verificar que el producto de parquimetro este configurado
        Query q = productController.getEm().createQuery("Select p from Product p where p.code =:code and p.active=true");
        q.setParameter("code", "park001");
        List<Product> productList = q.getResultList();
        if (productList.isEmpty()) {
            btnNuevo.setEnabled(false);
            JOptionPane.showMessageDialog(this, "El item parquímetro no está activo.\n Vaya a la sección Inventario > Productos", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            btnNuevo.setEnabled(true);
        }
    }

    /**
     *
     * @param configs
     * @return
     */
    private int getValue(List<Configurations> configs) {
        if (configs == null) {
            return 0;
        }
        return Integer.valueOf(configs.get(0).getValor());
    }

    /**
     * Chequear que haya variables de configuracion
     */
    private List<Configurations> checkConfigurations(String code) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("code", code);

        List<Configurations> configs = configController.namedQuery("Configurations.findByCode", parameters);

        if (configs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No existe la variable de configuración: " + code, "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        return configs;
    }

    /**
     *
     * @param refresh
     */
    public static void verTabla(boolean refresh ) {
        dBTable1.createControlPanel();
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        ventas = controller.findByDayAndCollector2(date.getTime(), LoginApp.userLogged, refresh);
        dBTable1.setEditable(false);
        dBTable1.setSortEnabled(false);
        
        setEmiterAndCollecter(ventas);
        fijarDatos(ventas);
    }

    /**
     *
     * @param ventas
     */
    private static void fijarDatos(List<Billing> ventas) {
        try {
            labelResultado.setText("Resultado: " + ventas.size());
            String methodNames[] = {"getFecha", "getPlaca", "getCliente", "getInicio", "getFin", "getNumero", "getTotal",
                "getEstado", "getEmisor", "getCobrador"};
            dBTable1.refreshDataObject(ventas, methodNames);
            dBTable1.getColumn(0).setPreferredWidth(150);
            dBTable1.getColumn(1).setPreferredWidth(100);
            dBTable1.getColumn(2).setPreferredWidth(200);
            dBTable1.getColumn(3).setPreferredWidth(100);
            dBTable1.getColumn(4).setPreferredWidth(100);
            dBTable1.getColumn(5).setPreferredWidth(80);
            dBTable1.getColumn(6).setPreferredWidth(60);
            dBTable1.getColumn(7).setPreferredWidth(80);
            dBTable1.getColumn(8).setPreferredWidth(80);
            dBTable1.getColumn(9).setPreferredWidth(80);

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

        contenedor = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
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
        searchTicketTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnNuevo1 = new javax.swing.JButton();

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        contenedor.setBackground(new java.awt.Color(255, 255, 255));
        contenedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contenedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                contenedorKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Parqueadero");

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

        jXTitledPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jXTitledPanel1KeyReleased(evt);
            }
        });

        labelResultado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        labelResultado.setText("Resultados: ");

        btnNuevo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/folder112.png"))); // NOI18N
        btnNuevo.setMnemonic('N');
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

        searchTicketTxt.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        searchTicketTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTicketTxtActionPerformed(evt);
            }
        });
        searchTicketTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchTicketTxtKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTicketTxtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTicketTxtKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Placa o Número de ticket:");

        btnNuevo1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/refresh.png"))); // NOI18N
        btnNuevo1.setMnemonic('N');
        btnNuevo1.setText("Actualizar");
        btnNuevo1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });
        btnNuevo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevo1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnNuevo1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dBTable1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTitledPanel1Layout.createSequentialGroup()
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                                .addGap(0, 233, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                                .addComponent(labelResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnNuevo1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPago, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPago1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(searchTicketTxt))))
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
                    .addComponent(btnPago)
                    .addComponent(btnNuevo1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchTicketTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dBTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout contenedorLayout = new javax.swing.GroupLayout(contenedor);
        contenedor.setLayout(contenedorLayout);
        contenedorLayout.setHorizontalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedorLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addGap(0, 137, Short.MAX_VALUE))
                    .addGroup(contenedorLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1))
                    .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        contenedorLayout.setVerticalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenedorLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(contenedorLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addGroup(contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dateDesde, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(dateHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))
                    .addGroup(contenedorLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnBuscar)
                            .addComponent(btnCancelar))
                        .addGap(28, 28, 28)))
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        abrirFormTickets();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void generarSecuencial() {
        List<Configurations> sequences = new ArrayList<>();

        Object data = configController.getEntityManager().createNativeQuery("Select valor from configuraciones where codigo = 'SECUENCIAL_ACT' ").getSingleResult();
        System.out.println(data);
        sequences = checkConfigurations("SECUENCIAL_ACT");

        if (!sequences.isEmpty()) {
            secuencial = sequences.get(0).getValue();
        }

//        secuencial = data.toString();
        numSecuencial = Integer.parseInt(secuencial);
        numSecuencial++;
//        secuencial = formatoSecuencial(numSecuencial);
    }

    /**
     * Formatear la secuancia de laf actura
     *
     * @param numero
     * @return
     */
    private String formatoSecuencial(int numero) {
        Formatter fmt = new Formatter();
        fmt.format("%09d", numero);
        System.out.println("El numero formateado " + fmt);
        return fmt.toString();
    }

    /**
     *
     */
    private void abrirFormTickets() {

        try {
            //limpiar el filtro
            if (!searchTicketTxt.getText().isEmpty()) {
                searchByTicketOrPlaca();
            }

//        generarSecuencial();
            //generar la hora de la bd
            Date initDate = convertToDate_DatabaseDate();

            Billing b = new Billing();
            b.setDetailBillingList(new ArrayList<DetailBilling>());
            b.setEmissiondate(initDate);

            b.setEmissiondate(new Date());
            b.setUser(LoginApp.userLogged);
            //fijar por defecto el contribuyente: CONSUMIDOR FINAL
            ClientProvider consumidorFinal = controllerClient.findClientProvider(2);
            b.setClientProviderid(consumidorFinal);
            b.setNumber(secuencial);
            b.setTotal(BigDecimal.ZERO);
            b.setSubtotal(BigDecimal.ZERO);
            
            //emisor
            b.setEmitterPerson(LoginApp.userLogged.getId());

            EntryParkingForm form = new EntryParkingForm(null, true, b);
            form.setLocationRelativeTo(null);
            form.setVisible(true);

            verTabla(true);
        } catch (ParseException ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed

        try {
            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //solo el usuario administrador puede anular
            if (!LoginApp.userLogged.getRol().equals("Administrador")) {
                JOptionPane.showMessageDialog(this, "Solo el administrador de la aplicación puede anular tickets\n. Contáctese a informática a la ext: 149", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int indice = dBTable1.getSelectedRow();
            Billing b = ventas.get(indice);

            //verificar que no sea de otro dia
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(new Date());
            currentDate.set(Calendar.HOUR_OF_DAY, 0);
            currentDate.set(Calendar.MINUTE, 0);
            currentDate.set(Calendar.SECOND, 0);
            if (b.getEmissiondate().before(currentDate.getTime())) {
                JOptionPane.showMessageDialog(this, "No puede anular tickets de fechas anteriores", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmado = JOptionPane.showOptionDialog(btnAnular,
                    "  Desea anular la factura con número,      \n"
                    + "   " + b.getNumber() + "!.\n"
                    + " \n", "Alerta",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No", "Cancelar"}, "Si");

            if (JOptionPane.OK_OPTION == confirmado) {
                System.out.println("si");
                List<DetailBilling> listaDetalle = b.getDetailBillingList();
                BigDecimal cantidad;
                Product product;
                /*
                 for (DetailBilling detalle : listaDetalle) {

                 cantidad = detalle.getQuantity();
                 product = detalle.getProductId();
                 cantidad = cantidad.add(product.getStock());
                 product.setStock(cantidad);
                 controllerProducto.edit(product);

                 }*/

                b.setState("ANULADA");
                controller.edit(b);
                verTabla(true);
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
//            JasperViewer.viewReport(jasperPrint, false);
            JasperPrintManager.printReport(jasperPrint, false);
        } catch (JRException ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    
    private static Date convertToDate_DatabaseDate() throws ParseException{
        Query q = controller.getEm().createNativeQuery("select now()");
        Object hourDB = q.getSingleResult();
        String hour = hourDB.toString().substring(0, hourDB.toString().length() - 2);
        System.out.println("=>" + hourDB);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.US);
        return format.parse(hour);
    }
    private void btnPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagoActionPerformed

        try {
            int indice = dBTable1.getSelectedRow();
            Billing b = ventas.get(indice);
            
            Object state = controller.getEm().
                    createNativeQuery("Select estado from factura where id="+b.getId()).getSingleResult();
            
            if (state.toString().equals("Pagada")) {
                JOptionPane.showMessageDialog(this, "El ticket ya ha sido PAGADO.\n Actualice la tabla de datos", "ERROR", JOptionPane.ERROR_MESSAGE);
                verTabla(true);
                return;
            }
            
            if (state.toString().equals("ANULADA")) {
                JOptionPane.showMessageDialog(this, "El ticket ha sido ANULADO.\n Actualice la tabla de datos", "ERROR", JOptionPane.ERROR_MESSAGE);
                verTabla(true);
                return;
            }
            
            if (state.equals("CONTRATO")) {
               JOptionPane.showMessageDialog(this, "El ticket es de tipo CONTRATO.\n No se puede pagar.", "ERROR", JOptionPane.ERROR_MESSAGE);
               verTabla(true);
               return;
            }
            
            //calcular el tiempo de pago
            Date timeStart = b.getDetailBillingList().get(0).getTimestart();
            DateTime start = new DateTime(timeStart);
            
            //obtener la fecha de la base de datos
            //generar la hora de la bd
            Date endJava = convertToDate_DatabaseDate();
            DateTime end = new DateTime(endJava);
            
            Period $period = new Period(start, end, PeriodType.yearMonthDayTime());
            int days = $period.getDays();
            int hours = $period.getHours();
            int minutes = $period.getMinutes();
            
            System.out.println("DIA " + $period.getDays());
            System.out.println("HORAS: " + $period.getHours());
            System.out.println("MINUTOS: " + $period.getMinutes());
            
            Product product = b.getDetailBillingList().get(0).getProductId();
            BigDecimal price = product.getSaleprice();
            BigDecimal quantity = BigDecimal.ZERO;
            String tiempo = days + ":" + hours + ":" + minutes;
            
            if (hours >= 1) {
                quantity = new BigDecimal(hours);
            }
            
            if (minutes > 0) {
                quantity = quantity.add(BigDecimal.ONE);
            }
            
            if (days >= 1) {
                int hoursPerDay = days * 24;
                quantity = quantity.add(new BigDecimal(hoursPerDay));
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
            b.getDetailBillingList().get(0).setTimeend(endJava);
            b.setTotal(b.getDetailBillingList().get(0).getTotalWithTax());
            
            CobroParkForm dialog = new CobroParkForm(new javax.swing.JFrame(), Boolean.TRUE, b,
                    tiempo, days, hours, minutes);
            dialog.setVisible(true);
            verTabla(true);
        } catch (ParseException ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnPagoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

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
        ventas = controller.namedQuery("Billing.findByFilter2", variables);
        
        setEmiterAndCollecter(ventas);
        fijarDatos(ventas);
    }//GEN-LAST:event_btnBuscarActionPerformed

    
    /**
     * Agregado para informacion de emisor y cobrador
     * @param ventas 
     */
    private static void setEmiterAndCollecter(List<Billing> ventas){
        Users emitter = null;
        Users collecter = null;
        for (Billing venta : ventas) {
            Integer emisorId = venta.getEmitterPerson();
            Integer collectorId = venta.getCollectorPerson();
            
            if(emisorId!=null && emisorId!= 0){
                emitter = userscontroller.findUsers(emisorId);
                venta.setEmisor(emitter.getUsuario());
            }
            
            if(collectorId!=null && collectorId!= 0){
                collecter = userscontroller.findUsers(collectorId);
                venta.setCobrador(collecter.getUsuario());
            }
        }
    }
    
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        searchTicketTxt.setText("");
        dateDesde.setDate(new Date());
        dateHasta.setDate(new Date());
        verTabla(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnPago1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPago1ActionPerformed
        int index = mainForm.pestanias.getSelectedIndex();
        if (index != -1) {
            mainForm.pestanias.remove(index);
            mainForm.CerrarPestana(TabsIndex.PARQUEADERO_BOLETERIA.getIndex());
        }
    }//GEN-LAST:event_btnPago1ActionPerformed

    private void btnNuevoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyReleased
//        System.out.println("GIM=>" + evt.getKeyCode());
        System.out.println("RELEASEGIM=>" + evt.getKeyCode());
        if (evt.getKeyCode() == KeyEvent.VK_N) {
            abrirFormTickets();
        }
    }//GEN-LAST:event_btnNuevoKeyReleased

    private void btnNuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyPressed
//        System.out.println("GIM=>" + evt.getKeyCode());
//        System.out.println("GIM=>" + evt.getKeyCode());
//        if (evt.getKeyCode() == KeyEvent.VK_N) {
//            abrirFormTickets();
//        }
    }//GEN-LAST:event_btnNuevoKeyPressed

    private void contenedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contenedorKeyPressed

    }//GEN-LAST:event_contenedorKeyPressed

    private void searchTicketTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTicketTxtActionPerformed

    }//GEN-LAST:event_searchTicketTxtActionPerformed

    private void searchTicketTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTicketTxtKeyTyped

    }//GEN-LAST:event_searchTicketTxtKeyTyped

    private void searchTicketTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTicketTxtKeyPressed

    }//GEN-LAST:event_searchTicketTxtKeyPressed

    private void searchTicketTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTicketTxtKeyReleased
        searchByTicketOrPlaca();
    }//GEN-LAST:event_searchTicketTxtKeyReleased

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        System.out.println("==>");
    }//GEN-LAST:event_formKeyReleased

    private void contenedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contenedorKeyReleased
        System.out.println("==>");
    }//GEN-LAST:event_contenedorKeyReleased

    private void jXTitledPanel1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jXTitledPanel1KeyReleased
        System.out.println("==>");
    }//GEN-LAST:event_jXTitledPanel1KeyReleased

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        verTabla(true);
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void btnNuevo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevo1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevo1KeyPressed

    private void btnNuevo1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevo1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevo1KeyReleased

    private void searchByTicketOrPlaca() {
        String text = searchTicketTxt.getText();
//        if(text.isEmpty()){
//            //buscar lo del dia
//            fijarDatos(ventas);
//            return;
//        }

        Date desde = dateDesde.getDate();
        Date hasta = dateHasta.getDate();

        Calendar calDesde = Calendar.getInstance();
        calDesde.setTime(desde);
        calDesde.set(Calendar.HOUR_OF_DAY, 0);
        calDesde.set(Calendar.MINUTE, 0);
        calDesde.set(Calendar.SECOND, 0);

        Calendar calHasta = Calendar.getInstance();
        calHasta.setTime(hasta);
        calHasta.set(Calendar.HOUR_OF_DAY, 23);
        calHasta.set(Calendar.MINUTE, 59);
        calHasta.set(Calendar.SECOND, 59);

        Map<String, Object> variables = new HashMap<>();
        variables.put("criteria", text.toLowerCase() + "%");
        variables.put("from", calDesde.getTime());
        variables.put("until", calHasta.getTime());
        ventas = controller.namedQuery("Billing.findByTicketOrPlaca", variables);
        
        setEmiterAndCollecter(ventas);
        fijarDatos(ventas);
    }

    private void abrirVentana(final Billing b) {
        VentasForm dialog = new VentasForm(new javax.swing.JFrame(), true, b);
        dialog.setVisible(true);
        verTabla(true);
    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        System.out.println("keyTyped: " + e);
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        System.out.println("keyPressed: " + e);
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        System.out.println("keyReleased: " + e);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JButton btnPago;
    private javax.swing.JButton btnPago1;
    public javax.swing.JPanel contenedor;
    private static quick.dbtable.DBTable dBTable1;
    private com.toedter.calendar.JDateChooser dateDesde;
    private com.toedter.calendar.JDateChooser dateHasta;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private static javax.swing.JLabel labelResultado;
    private javax.swing.JTextField searchTicketTxt;
    // End of variables declaration//GEN-END:variables

}
