/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas.ventas;

import controllers.AccountJpaController;
import controllers.BillingJpaController;
import controllers.ClientProviderJpaController;
import controllers.ConfigurationsJpaController;
import controllers.DetailBillingJpaController;
import controllers.ProductJpaController;
import controllers.exceptions.NonexistentEntityException;
import entities.Account;
import entities.Billing;
import entities.ClientProvider;
import entities.Configurations;
import entities.DetailBilling;
import entities.Person;
import entities.Product;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

/**
 *
 * @author macbookpro
 */
public class VentasForm extends javax.swing.JDialog implements ActionListener, KeyListener {

    static BillingJpaController controller = null;
    static DetailBillingJpaController controllerDetail = null;
    static ClientProviderJpaController controllerClient = null;
    static AccountJpaController controllerAccount = null;
    static ConfigurationsJpaController controllerConfig;
    static ProductJpaController controllerProduct;
//    private List<DetailBilling> details;
    private DetailBilling d;
    private ClientProvider cliente;
    private Configurations config;
    private List<ClientProvider> clients;
    private List<String> list = new ArrayList<>();
    private int numSecuencial;
    private String mensaje;
    private int numberTickets;
    private String secuencial;

    /**
     * Creates new form VentasForm
     *
     * @param parent
     * @param modal
     */
    public VentasForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * Constructor del formulario de ventas, fijar los datos de la entidad en
     * los campos del formulario
     */
    public VentasForm(java.awt.Frame parent, boolean modal, Billing b) {
        super(parent, modal);
        initComponents();
        this.billing = b;
        controller = new BillingJpaController();
        controllerDetail = new DetailBillingJpaController();
        controllerClient = new ClientProviderJpaController();
        controllerAccount = new AccountJpaController();
        controllerConfig = new ConfigurationsJpaController();
        controllerProduct = new ProductJpaController();
        secuencial = "";
        fijarEntidad();
        checkInitVars();
        verTabla();
    }

    /**
     * Chequear las variables de configuracion necesarias
     */
    private void checkInitVars() {
        //numero de tickts de combobox
        List<Configurations> ticketsConfig = checkConfigurations("ticketsNumber");
        numberTickets = getValue(ticketsConfig);
        System.out.println("L=>" + numberTickets);
        List<Configurations> sequences = new ArrayList<>();
        //continuar secuencial o iniciar
        if (controller.getBillingCount() > 0) {
            sequences = checkConfigurations("SECUENCIAL_ACT");
        } else {
            sequences = checkConfigurations("SECUENCIAL_INI");
        }
        secuencial = sequences.get(0).getValue();
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

        List<Configurations> configs = controllerConfig.namedQuery("Configurations.findByCode", parameters);
        if (configs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No existe la variable de configuración: " + code, "ERROR", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
        return configs;
    }

    /**
     * Crear la tabla de detalle de factura
     */
    private void verTabla() {

        dBTable1.createControlPanel();

        //llenar el detalle de factura
        //con todos los productos en estado activo
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("state", Boolean.TRUE);

        List<Product> products = controllerProduct.namedQuery("Product.findAll", parameters);
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Product product : products) {
            DetailBilling detail = new DetailBilling();
            detail.setProductId(product);
            detail.setQuantity(BigDecimal.ONE);
            detail.setUnitaryPrice(product.getSaleprice());
            detail.setPercentageDiscount(BigDecimal.ZERO);
            detail.setValueDiscount(BigDecimal.ZERO);
            detail.setPercentageIva(product.getPercentageIva());

            BigDecimal total = detail.getProductId().getSaleprice().multiply(detail.getQuantity());

            BigDecimal iva = (product.getPercentageIva().multiply(total)).setScale(2, BigDecimal.ROUND_HALF_UP).
                    divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);

            BigDecimal totalConImpto = total.add(iva);
            detail.setTotalWithTax(totalConImpto);
            detail.setValueIva(iva);
            detail.setTotal(total);
            detail.setBillingId(billing);
            billing.getDetailBillingList().add(detail);
        }

        billing.setSubtotal(subtotal);
        fijarDatos();

    }

    /**
     * Fija los tamanos y las columnas de la tabla de detalle
     */
    private void fijarDatos() {
        try {
            String methodNames[] = {"getProducto", "getCantidad", "getPrecioUnitario", "getDescuento", "getPrecioTotal"};
            dBTable1.refreshDataObject(billing.getDetailBillingList(), methodNames);

            //alineacion derecha de algunas columnas
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
            dBTable1.getColumn(1).setCellRenderer(rightRenderer);
            dBTable1.getColumn(2).setCellRenderer(rightRenderer);
            dBTable1.getColumn(3).setCellRenderer(rightRenderer);
            dBTable1.getColumn(4).setCellRenderer(rightRenderer);

            //tamano de columnas 
            dBTable1.getColumn(0).setPreferredWidth(400);
            dBTable1.getColumn(1).setPreferredWidth(80);
            dBTable1.getColumn(2).setPreferredWidth(110);
            dBTable1.getColumn(3).setPreferredWidth(100);
            dBTable1.getColumn(4).setPreferredWidth(130);

            String[] values = new String[this.numberTickets];

            for (int i = 0; i < this.numberTickets; i++) {
                values[i] = "" + (i + 1);
            }

            TableColumn col = dBTable1.getColumn(1);
            col.setCellEditor(new MyComboBoxEditor(values));
            col.setCellRenderer(new MyComboBoxRenderer(values));

//                dBTable1.getColumn(1).setCellEditor(new DefaultCellEditor(comboQuantity));
            /*
             comboQuantity.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
             fijarCantidad(evt);
             }
             });
             */
             
//            combo.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    fijarCantidad(evt);
//                }
//            });
//

            //calcular los totales de la factura1
            calcularTotales();
        } catch (Exception ex) {
            Logger.getLogger(VentasForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param evt
     */
    private void fijarCantidad() {
        //obtener la fila que se cambio la cantidad 
        int rowSelected = dBTable1.getSelectedRow();

        System.out.println("ROw selected: " + rowSelected);
        if (rowSelected != -1) {
            Object value = dBTable1.getValueAt(rowSelected, 1);
            DetailBilling detail = billing.getDetailBillingList().get(rowSelected);
            BigDecimal quantity = new BigDecimal(value.toString());

            System.out.println("Value   : " + quantity);
            detail.setQuantity(quantity);

            BigDecimal total = detail.getProductId().getSaleprice().multiply(detail.getQuantity());
            BigDecimal iva = (detail.getProductId().getPercentageIva().multiply(total)).setScale(2, BigDecimal.ROUND_HALF_UP).
                    divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);

            BigDecimal totalConImpto = total.add(iva);
            detail.setTotalWithTax(totalConImpto);
            detail.setValueIva(iva);
            detail.setTotal(total);
            //fijarDatos();

            //actualizar datos de UI 
            dBTable1.setValueAt(total, rowSelected, 4);
            calcularTotales();
//            dBTable1.repaint();
         
        }
    }

    /**
     * Fijar los datos de la entidad pasada (nueva o editar) en los campos del
     * formulario
     */
    private void fijarEntidad() {
        cliente = billing.getClientProviderid();
        txtRucCi.setText(cliente.getPersonId().getPassport());
        txtNombres.setText(cliente.getPersonId().getNames() + cliente.getPersonId().getLastname());
        txtDireccion.setText(cliente.getPersonId().getPassport());
        if (billing.getId() != null) {

            txtTelefono.setText(cliente.getPersonId().getPhone());
            lblSubtotal.setText(billing.getSubtotal().toString());
            lblBaseIva0.setText(billing.getBaseiva0().toString());
            lblBaseIva12.setText(billing.getBaseiva12().toString());
            lblValorIva12.setText(billing.getIva0().toString());
            lblDescuento.setText(billing.getDiscount().toString());
            lblTotal.setText(billing.getTotal().toString());
        }
    }

    /**
     * CODIGO GENERADO POR NETBEASN
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtRucCi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnAgregarDetalle = new javax.swing.JButton();
        txtNombres = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        dBTable1 = new quick.dbtable.DBTable();
        dBTable1.setEditable(true);
        jLabel13 = new javax.swing.JLabel();
        lblBaseIva0 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        lblValorIva12 = new javax.swing.JLabel();
        lblBaseIva12 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        combito = new javax.swing.JComboBox();
        combito.setEditable(true);
        JTextComponent editor = (JTextComponent) combito.getEditor().getEditorComponent();
        editor.addKeyListener(this);
        combito.addActionListener(this);
        jLabel10 = new javax.swing.JLabel();
        lblDescuento = new javax.swing.JLabel();
        btnAgregarCliente = new javax.swing.JButton();
        btnRemoverDetalle = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("0,00");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Factura de venta");
        setLocationByPlatform(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Datos generales");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("RUC/CI: ");

        txtRucCi.setEditable(false);
        txtRucCi.setPreferredSize(new java.awt.Dimension(10, 26));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Nombres:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Dirección:");

        txtDireccion.setEditable(false);
        txtDireccion.setPreferredSize(new java.awt.Dimension(10, 26));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Telf.:");

        txtTelefono.setEditable(false);
        txtTelefono.setPreferredSize(new java.awt.Dimension(10, 26));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Detalle");

        btnAgregarDetalle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnAgregarDetalle.setText("Agregar detalle");
        btnAgregarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDetalleActionPerformed(evt);
            }
        });

        txtNombres.setEditable(false);
        txtNombres.setPreferredSize(new java.awt.Dimension(10, 26));
        txtNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombresActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Subtotal:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Total:");

        btnGuardar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Base iva 12%:");

        lblBaseIva0.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblBaseIva0.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBaseIva0.setText("0,00");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Base iva 0%:");

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("IVA 12%:");

        lblSubtotal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSubtotal.setText("0,00");

        lblValorIva12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblValorIva12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblValorIva12.setText("0,00");

        lblBaseIva12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblBaseIva12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBaseIva12.setText("0,00");

        lblTotal.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("0,00");

        combito.setPreferredSize(new java.awt.Dimension(37, 26));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total descuento:");

        lblDescuento.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblDescuento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDescuento.setText("0,00");

        btnAgregarCliente.setText("Ingresar cliente");
        btnAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarClienteActionPerformed(evt);
            }
        });

        btnRemoverDetalle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnRemoverDetalle.setText("Remover detalle");
        btnRemoverDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverDetalleActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Digite Cédula o Apellidos* : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemoverDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combito, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(42, 42, 42)
                                        .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtRucCi, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblValorIva12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSubtotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBaseIva0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDescuento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(lblBaseIva12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(37, 37, 37))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(dBTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarCliente)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(txtRucCi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregarDetalle)
                        .addComponent(btnRemoverDetalle)))
                .addGap(18, 18, 18)
                .addComponent(dBTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubtotal)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBaseIva0)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBaseIva12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValorIva12)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescuento)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(jLabel9))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar))
                .addGap(114, 114, 114))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void txtNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombresActionPerformed

    /**
     * BOton para salir del formulario
     *
     * @param evt
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Boton para guardar/editar la factura si el id es nulo se crea, caso
     * contrario se edita
     *
     */
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        try {

            if (!noValido()) {

                if (billing.getId() == null) {

              
                    
                            System.out.println("=====>"+billing.getSequential());
                            System.out.println("=====>"+billing.getShop_id());
                    controller.createBilling(billing);
                      generarSecuencial();
                     controller.edit(billing);
                    actualizarSecuencial();
                    actualizarStock();

                    //crear una nueva cta por cobrar
                    Account account = new Account();
                    account.setBillingId(billing);
                    account.setState("Abierta");
                    account.setBalance(billing.getTotal());
                    account.setTotal(billing.getTotal());
                    account.setDateCreation(new Date());

                    controllerAccount.create(account);

                    billing.getAccountCollection().add(account);
                    //abrir la ventana de cobro
                    CobroFacturaForm dialog = new CobroFacturaForm(new javax.swing.JFrame(), Boolean.TRUE, billing);
                    dialog.setVisible(true);
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    int x = (int) ((dimension.getWidth() - dialog.getWidth()) / 2);
                    int y = (int) ((dimension.getHeight() - dialog.getHeight()) / 2);
                    dialog.setLocation(x, y);
                    this.dispose();

                } else {

                }

            } else {
                JOptionPane.showMessageDialog(this, mensaje,
                        "ERROR", JOptionPane.ERROR_MESSAGE);

            }

        } catch (Exception ex) {
            Logger.getLogger(VentasForm.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * Obtiene el numero de secuencia de factura de venta
     */
    private void generarSecuencial() {

        numSecuencial = Integer.parseInt(secuencial);
        numSecuencial++;
        secuencial = formatoSecuencial(numSecuencial);
        billing.setShop_id("001");
        
        System.out.println("=====>"+secuencial);
        billing.setEmissionpoint_id("001");
        billing.setSequential(secuencial);
        billing.setNumber(billing.getShop_id() + "-" + billing.getEmissionpoint_id() + "-" + billing.getSequential());

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
     * Actualiza el secuencial de factura de venta
     */
    private void actualizarSecuencial() {
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("code", "SECUENCIAL_ACT");
            config = controllerConfig.namedQuery("Configurations.findByCode", variables).get(0);
            config.setValue(String.valueOf(numSecuencial));
            controllerConfig.edit(config);

        } catch (Exception ex) {
            Logger.getLogger(VentasForm.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Actualiza el stock de productos al seleccionar el detalle
     */
    private void actualizarStock() {
        try {
            BigDecimal nuevoStock;
            Product p;
            for (DetailBilling detail : billing.getDetailBillingList()) {
                p = controllerProduct.findProduct(detail.getProductId().getId());
                nuevoStock = p.getStock().subtract(detail.getQuantity());
                p.setStock(nuevoStock);
                controllerProduct.edit(p);

            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VentasForm.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VentasForm.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Accion del boton agregar detalle
     */
    private void btnAgregarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDetalleActionPerformed
        // TODO add your handling code here:
        d = new DetailBilling();
        abrirVentana();
    }//GEN-LAST:event_btnAgregarDetalleActionPerformed

    /**
     * Accion del boton agregar cliente
     *
     * @param evt
     */
    private void btnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClienteActionPerformed
        ClientProvider cp = new ClientProvider();
        cp.setClient(Boolean.TRUE);
        cp.setActiveclient(Boolean.TRUE);
        cp.setProvider(Boolean.FALSE);
        cp.setActiveprovider(Boolean.FALSE);
        cp.setPersonId(new Person());
        cp.getPersonId().setSex("M");
        ClienteForm2 dialog = new ClienteForm2(new javax.swing.JFrame(), true, cp);
        dialog.setVisible(Boolean.TRUE);
    }//GEN-LAST:event_btnAgregarClienteActionPerformed

    /**
     * Accion del boton remover detalle
     */
    private void btnRemoverDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverDetalleActionPerformed
        // TODO add your handling code here:

        if (billing.getDetailBillingList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int indice = dBTable1.getSelectedRow();

        System.out.println("si");
        billing.getDetailBillingList().remove(indice);
        fijarDatos();

    }//GEN-LAST:event_btnRemoverDetalleActionPerformed

    /**
     * Abre la venta para seleccionar el producto
     */
    private void abrirVentana() {

//        ItemForm dialog = new ItemForm(new javax.swing.JFrame(), true, d, billing.getDetailBillingList());
        ItemForm dialog = new ItemForm(new javax.swing.JFrame(), true, d, billing.getDetailBillingList());
        dialog.setVisible(Boolean.TRUE);
        fijarDatos();

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentasForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentasForm dialog = new VentasForm(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private static Billing billing;

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    /**
     * Permite calcular los totales de la factura
     */
    @SuppressWarnings("UnusedAssignment")
    private void calcularTotales() {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal descuento = BigDecimal.ZERO;
        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal baseIva12 = BigDecimal.ZERO;
        BigDecimal iva12 = BigDecimal.ZERO;
        BigDecimal baseIva0 = BigDecimal.ZERO;
        BigDecimal iva0 = BigDecimal.ZERO;

        for (DetailBilling detail : billing.getDetailBillingList()) {

            subTotal = subTotal.add(detail.getTotal());
            descuento = descuento.add(detail.getValueDiscount());
            if (detail.getPercentageIva().compareTo(BigDecimal.ZERO) == 0) {
                baseIva0 = baseIva0.add(detail.getTotal());
            } else {
                baseIva12 = baseIva12.add(detail.getTotal());
                iva12 = iva12.add(detail.getValueIva());
            }
        }
        total = subTotal.add(iva12);

        billing.setType("FACTURA");
        billing.setState("GENERADA");
        billing.setSubtotal(subTotal);
        billing.setBaseiva0(baseIva0);
        billing.setIva0(iva0);
        billing.setBaseiva12(baseIva12);
        billing.setIva12(iva12);
        billing.setPercentageDiscount(BigDecimal.ZERO);
        billing.setDiscount(descuento);
        billing.setTotal(total);
//        billing.setNumber(null);
//        billing.setSequential(null);
//        billing.setEmissionpoint_id(null);
//        billing.setShop_id(null);

        lblSubtotal.setText(subTotal.toString());
        lblBaseIva0.setText(baseIva0.toString());
        lblBaseIva12.setText(baseIva12.toString());
        lblValorIva12.setText(iva12.toString());
        lblDescuento.setText(descuento.toString());
        lblTotal.setText(total.toString());

    }

    /**
     * Permite validar los datos de la factura
     *
     * @return
     */
    private boolean noValido() {
        Boolean error = Boolean.FALSE;

        if (billing.getClientProviderid() == null) {
            error = Boolean.TRUE;
            mensaje = "- Seleccione un cliente. \n";
            return error;
        }

        if (billing.getDetailBillingList().isEmpty()) {
            error = Boolean.TRUE;
            mensaje = "- Agregue al menos un detalle a la factura. \n";
            return error;
        }

        return error;

    }

    class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {

        public MyComboBoxRenderer(String[] items) {
            super(items);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            if (isSelected) {
                    fijarCantidad();
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setSelectedItem(value);
            return this;
        }
        
//        @Override
//         public void actionPerformed(ActionEvent e) {
//            System.out.println("=0-090000---");
//              fijarCantidad(e);
//        }
    }

    class MyComboBoxEditor extends DefaultCellEditor {

 

        public MyComboBoxEditor(String[] items) {

//        JComboBox combo = new JComboBox(items);
            super(new JComboBox(items));
//            JComboBox combo = (JComboBox) super.getComponent();
//            combo.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    fijarCantidad(evt);
//                }
//            });

        }
 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCliente;
    private javax.swing.JButton btnAgregarDetalle;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRemoverDetalle;
    private javax.swing.JComboBox combito;
    private static quick.dbtable.DBTable dBTable1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblBaseIva0;
    private javax.swing.JLabel lblBaseIva12;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblValorIva12;
    private static javax.swing.JTextField txtDireccion;
    private static javax.swing.JTextField txtNombres;
    private static javax.swing.JTextField txtRucCi;
    private static javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

    /**
     * Accion al cambiar de opcion en el combobox de cliente
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("AQUI YEA >>>");
        System.out.println(">>> " + combito.getSelectedIndex());
        int clienteSel = combito.getSelectedIndex();
        if (clienteSel != -1) {
            cliente = clients.get(clienteSel);
            fijarCliente(cliente);
        }
    }

    /**
     * FIja los datos del cliente seleccinado en los campos de cliente
     *
     * @param cliente
     */
    public static void fijarCliente(ClientProvider cliente) {
        billing.setClientProviderid(cliente);
        txtRucCi.setText(cliente.getPersonId().getPassport());
        txtNombres.setText(cliente.getPersonId().getNames() + cliente.getPersonId().getLastname());
        txtDireccion.setText(cliente.getPersonId().getAddress());
        txtTelefono.setText(cliente.getPersonId().getPhone());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Evento para buscar datos de cliente al digitar alguna tecla
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        String text = ((JTextField) e.getSource()).getText();
        try {
            if (text.equals("")) {
                combito.setModel(getSuggestedModel(new Vector(), ""));
            } else {
                buscarClientes(text);
                setSuggestionModel(combito, getModelComboBox(list), text);
                combito.showPopup();
            }
        } catch (Exception ex) {
        }
    }

    /**
     * Metodo que consulta un cliente
     *
     * @param criterio
     */
    private void buscarClientes(String criterio) {
        if (!criterio.trim().isEmpty()) {
            Map<String, Object> variables = new HashMap<>();
            variables.put("criteria", criterio.toLowerCase() + "%");
            clients = controllerClient.namedQuery("ClientProvider.findByNamesOrPassport", variables);
            list = listaClientes(clients);
        }
    }

    /**
     * Retorna la lista de clientes encontrados en una busqueda
     *
     * @param lis
     * @return
     */
    public final List<String> listaClientes(List<ClientProvider> lis) {
        List<String> result = new ArrayList<>();
        String var;
        if (lis != null) {
            for (ClientProvider cp : lis) {
                var = cp.getPersonId().getNames() + " " + cp.getPersonId().getLastname() + " " + cp.getPersonId().getPassport();
                result.add(var);
            }
        }
        return result;
    }

    /**
     * Metodo auxiliar para modificar el combobox de java y permita la busqueda
     *
     * @param comboBox
     * @param mdl
     * @param str
     */
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

    private static ComboBoxModel getModelComboBox(List<String> list) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for (String val : list) {
            m.addElement(val);
        }
        return m;
    }

}
