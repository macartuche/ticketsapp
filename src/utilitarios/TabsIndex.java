/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

/**
 *
 * @author macbookpro
 */
public enum TabsIndex {
    
    CLIENTES("Clientes ",  1),
    FACTURASVENTA("Facturas de venta", 2),
    CTASCOBRAR("Cuentas por cobrar",3),
    CTASPAGAR("Cuentas por pagar",4),
    PRODUCTOS("Productos ",5),
    USUARIOS("Usuarios     ",6),
    FAMILIA("Grupos de productos  ",7),
    CONFIGURACIONES("Configuraciones ",8),
    REPORTES_VENTASREALIZADAS("Ventas realizadas  ",9),
    REPORTES_CTASCOBRAR("Ctas por cobrar  ",10),
    REPORTES_PRODUCTOSMASVENDIDO("Productos más vendidos",11),
    PARQUEADERO_BOLETERIA("Boleteria",12),
    PARQUEADERO_CONTRATOS("Contratos ",13);
    
    private final String title;
    private final int index;

    private TabsIndex(String title, int index) {
        this.title = title;
        this.index = index;
    }

    
    public String getTitle() {
        return title;
    }
 

    public int getIndex() {
        return index;
    }
    
    
}
