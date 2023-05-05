
package vistas;

import clases.productos;
import clases.venta;
import conexion.conexion;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
public class Frmprincipal extends javax.swing.JFrame {
conexion conex=new conexion();
DefaultTableModel modelproducto;
DefaultTableModel modelcate;
DefaultTableModel modelcliente;
DefaultTableModel modelusuario;
DefaultTableModel modelventa;
DefaultTableModel modelProdMasVendido;
DefaultTableModel modelclientefreuente;
TableRowSorter trs;
venta v=new venta();
boolean ventaa=false;
double tpagar;
    int cant;
    double precio;
    /**
     * Creates new form Frmprincipal
     */
    public Frmprincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        primerpanel();
        cargabotonesiniciales();
        txtdescripcionproducto.setLineWrap(true);
        txtdescripcionproducto.setWrapStyleWord(true);
        numeroventa();
        titulotablaproducto();
        titulotablacate();
        titulotablacliente();
        titulotablaUser();
        titulotablaVenta();
        bloqueaCamposProductos();
        cantidadClientes();
        cantidadProd();
        cantidadUSer();cantidadVentas();
        titulotablaProdMasVendi();
        titulotablaclienteFrecuente();
        mostrarpodMasVendidos();
        mostrarClienteFrecuente();
        cantStockBajo();
    }
    
    
    public void cantidadClientes(){
    try {
        ResultSet resultado=conex.consultar("select count(*)as cant from clientes");
        if(resultado!=null){
           String cantidad=resultado.getString("cant");
           txtnumClientes.setText(cantidad);
        }
    } catch (SQLException ex) {
        
    }
    }
    
    public void cantidadProd(){
    try {
        ResultSet resultado=conex.consultar("select count(*)as canti from productos");
        if(resultado!=null){
        String cantidad=resultado.getString("canti");
        txtnumpROD.setText(cantidad);
        }
    } catch (SQLException ex) {
        
    }
    }
    
    public void cantidadUSer(){
    try {
        ResultSet resultado=conex.consultar("select count(*)as canti from usuarios");
        if(resultado!=null){
            String cantidad=resultado.getString("canti");
            txtcantiusuarios.setText(cantidad);
        }
    } catch (SQLException ex) {
        
    }
    }
    
    public void cantidadVentas(){
    try {
        ResultSet resultado=conex.consultar("select count(*)as canti from ventas");
        if(resultado!=null){
            String cantidad=resultado.getString("canti");
            txtcantventas.setText(cantidad);
        }
    } catch (SQLException ex) {
        
    }
    }
    
    void bloqueaCamposProductos(){
        txtnombreproducto.setEnabled(false);
        txtdescripcionproducto.setEnabled(false);
        txtpcompraprod.setEnabled(false);
        txtpreventprod.setEnabled(false);
        txtstockprod.setEnabled(false);
        txtidcategoriaprod.setEnabled(false);
        txtcategoriaprod.setEnabled(false);
        btnGuardarProducto.setEnabled(false);
        btnmodificarproducto.setEnabled(false);
        btneliminarproducto.setEnabled(false);
    }
    
    public void numeroventa(){
        try {
             ResultSet resultado=conex.consultar("select max(numero)as num from ventas");
             String num=resultado.getString("num");
             int nu=Integer.parseInt(num);
             System.out.print(num+"  "+nu);
             if(num==null){
                 txtnumventa.setText("001");
             }else if(nu<9){
                 nu=nu+1;
                 txtnumventa.setText("00"+nu);
             }else if(nu>9 && nu<99){
                 int incremet=nu;
                 incremet=incremet+1;
                 txtnumventa.setText("0"+incremet);
            }else if(nu>=99){
                int incremet=nu;
                incremet=incremet+1;
                txtnumventa.setText(incremet+"");
            }
        } catch (Exception e) {
             
        }
    }

    public void titulotablaproducto(){
        String[] titulos = {"ID","Nombre","Descripcion","Pre. Compra","Prec. Venta","Stock","IDcategoria","Categoria"};
        modelproducto=new DefaultTableModel(null, titulos);
        tablaproductos.setModel(modelproducto);
        mostrarDatosProd("");
        limpiarcamposProductos();
    }
    
    public void titulotablacate(){
        String[] titulos = {"ID","Nombre"};
        modelcate=new DefaultTableModel(null, titulos);
        tablacategoria.setModel(modelcate);
        mostrarDatosCate("");
        limpiarcamposcategoria();
    }
    
    public void titulotablacliente(){
        String[] titulos = {"ID","Nombre","Apellido","correo","Direccion","Telefono","T.Documento","N.Documento"};
        modelcliente=new DefaultTableModel(null, titulos);
        tablacliente.setModel(modelcliente);
        mostrarDatosClient("");
        limpiarcamposClientes();
    }
    
    public void titulotablaUser(){
        String[] titulos = {"ID","Usuario","Password","Correo","T.Usuario","Nombre","Apellido"};
        modelusuario=new DefaultTableModel(null, titulos);
        tablausuario.setModel(modelusuario);
        mostrarDatosUser("");
        limpiarcamposUsuario();
    }
    
    public void titulotablaVenta(){
        String[] titulos = {"Cant.","ID.Prod","Producto","P.Unitario","Importe"};
        modelventa=new DefaultTableModel(null, titulos);
        tablaventa.setModel(modelventa);
    }
    
    public void titulotablaProdMasVendi(){
        String[] titulos = {"ID","Producto","Cantidad"};
        modelProdMasVendido=new DefaultTableModel(null, titulos);
        TprodMasVendidos.setModel(modelProdMasVendido);
    }
    
    public void titulotablaclienteFrecuente(){
        String[] titulos = {"ID","Nombre","Apellido","Cantidad"};
        modelclientefreuente=new DefaultTableModel(null, titulos);
        TclieMasfrecuentes.setModel(modelclientefreuente);
    }
    
    public void BarraProducto1(){
        int id=Integer.parseInt(TprodMasVendidos.getValueAt(0, 0).toString());
        int porcentaje=Integer.parseInt(TprodMasVendidos.getValueAt(0, 2).toString());
        String nombre=TprodMasVendidos.getValueAt(0, 1).toString();
        if (porcentaje<=90){
            barra2.setMaximum(100);
        }else if(porcentaje>90 && porcentaje<491){
            barra1.setMaximum(500);
        }else if(porcentaje>490 && porcentaje<991){
            barra1.setMaximum(1500);
        }else if(porcentaje>990 && porcentaje<3000){
            barra1.setMaximum(5000);
        }else{
            barra1.setMaximum(10000);
        }
        barra1.setValue(porcentaje);
        barra1.setString(porcentaje+"");
        masvendidoA.setText(nombre);
    }
    
    public void BarraProducto2(){
        int id=Integer.parseInt(TprodMasVendidos.getValueAt(1, 0).toString());
        int porcentaje=Integer.parseInt(TprodMasVendidos.getValueAt(1, 2).toString());
        String nombre=TprodMasVendidos.getValueAt(1, 1).toString();
        if (porcentaje<=90){
            barra2.setMaximum(100);
        }else if(porcentaje>90 && porcentaje<491){
            barra2.setMaximum(500);
        }else if(porcentaje>490 && porcentaje<991){
            barra2.setMaximum(1500);
        }else if(porcentaje>990 && porcentaje<3000){
            barra2.setMaximum(5000);
        }else{
            barra2.setMaximum(10000);
        }
        barra2.setValue(porcentaje);
        barra2.setString(porcentaje+"");
        masVendidaB.setText(nombre);
    }
    
    public void BarraProducto3(){
        int id=Integer.parseInt(TprodMasVendidos.getValueAt(2, 0).toString());
        int porcentaje=Integer.parseInt(TprodMasVendidos.getValueAt(2, 2).toString());
        String nombre=TprodMasVendidos.getValueAt(2, 1).toString();
        if (porcentaje<=90){
            barra3.setMaximum(100);
        }else if(porcentaje>90 && porcentaje<491){
            barra3.setMaximum(500);
        }else if(porcentaje>490 && porcentaje<991){
            barra3.setMaximum(1500);
        }else if(porcentaje>990 && porcentaje<3000){
            barra3.setMaximum(5000);
        }else{
            barra3.setMaximum(10000);
        }
        barra3.setValue(porcentaje);
        barra3.setString(porcentaje+"");
        masVendidaC.setText(nombre);
    }
    
    public void cliente1(){
        int id=Integer.parseInt(TclieMasfrecuentes.getValueAt(0, 0).toString());
            String nombre=TclieMasfrecuentes.getValueAt(0, 1).toString();
            String ape=TclieMasfrecuentes.getValueAt(0, 2).toString();
            int canti=Integer.parseInt(TclieMasfrecuentes.getValueAt(0, 3).toString());
            Frecuente1.setText(nombre);
            Frecuenteape1.setText(ape);
            FreCod1.setText(id+"");
            FreCant1.setText(canti+"");
    }
    
    public void cliente2(){
        if(!TclieMasfrecuentes.getSize().equals(0)){
            int id=Integer.parseInt(TclieMasfrecuentes.getValueAt(1, 0).toString());
            String nombre=TclieMasfrecuentes.getValueAt(1, 1).toString();
            String ape=TclieMasfrecuentes.getValueAt(1, 2).toString();
            int canti=Integer.parseInt(TclieMasfrecuentes.getValueAt(1, 3).toString());
            Frecuente2.setText(nombre);
            Frecuenteape2.setText(ape);
            FreCod2.setText(id+"");
            FreCant2.setText(canti+"");
        }
    }
    
    public void cliente3(){
            int id=Integer.parseInt(TclieMasfrecuentes.getValueAt(2, 0).toString());
            String nombre=TclieMasfrecuentes.getValueAt(2, 1).toString();
            String ape=TclieMasfrecuentes.getValueAt(2, 2).toString();
            int canti=Integer.parseInt(TclieMasfrecuentes.getValueAt(2, 3).toString());
            Frecuente3.setText(nombre);
            Frecuenteape3.setText(ape);
            FreCod3.setText(id+"");
            FreCant3.setText(canti+"");
    }
    
    public void cantStockBajo(){
        try {
            ResultSet resultado=conex.consultar("select count(*)as cant from productos p where p.stock<=10;");
            if(resultado!=null){
                String canti=resultado.getString("cant");
             stockBajo.setText(canti);
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "", "error", 1);
        }
    }
    
    private void primerpanel(){
        btnhome.setSelected(true);
        if(btnhome.isSelected()){
            btnclientes.setSelected(false);
            btnproductos.setSelected(false);
            btnusuarios.setSelected(false);
            btncategorias.setSelected(false);
            btnventas.setSelected(false);
        }
        paneles.setSelectedComponent(panelhome);
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
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel22 = new javax.swing.JLabel();
        btnhome = new RSMaterialComponent.RSButtonMaterialDos();
        btnproductos = new RSMaterialComponent.RSButtonMaterialDos();
        btnclientes = new RSMaterialComponent.RSButtonMaterialDos();
        btnusuarios = new RSMaterialComponent.RSButtonMaterialDos();
        btncategorias = new RSMaterialComponent.RSButtonMaterialDos();
        btnventas = new RSMaterialComponent.RSButtonMaterialDos();
        jLabel74 = new javax.swing.JLabel();
        btnAcercaDe = new RSMaterialComponent.RSButtonMaterialDos();
        paneles = new javax.swing.JTabbedPane();
        panelhome = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        btnsalir = new RSMaterialComponent.RSButtonMaterialDos();
        homenombre = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        homeiduser = new javax.swing.JLabel();
        panelClientes = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        txtnumClientes = new javax.swing.JLabel();
        PanelProductos = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        txtnumpROD = new javax.swing.JLabel();
        PanelUsuarios = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        txtcantiusuarios = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        rSCalendar2 = new rojerusan.RSCalendar();
        jPanel10 = new javax.swing.JPanel();
        barra1 = new rojerusan.RSProgressBar();
        barra2 = new rojerusan.RSProgressBar();
        barra3 = new rojerusan.RSProgressBar();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        masvendidoA = new javax.swing.JLabel();
        masVendidaB = new javax.swing.JLabel();
        masVendidaC = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TprodMasVendidos = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        TclieMasfrecuentes = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        stockBajo = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        Frecuente1 = new javax.swing.JLabel();
        FreCant1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        asdasd = new javax.swing.JLabel();
        FreCod1 = new javax.swing.JLabel();
        Frecuenteape1 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        Frecuente2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        FreCant2 = new javax.swing.JLabel();
        FreCod2 = new javax.swing.JLabel();
        asdasd1 = new javax.swing.JLabel();
        Frecuenteape2 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        Frecuente3 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        FreCant3 = new javax.swing.JLabel();
        FreCod3 = new javax.swing.JLabel();
        asdasd2 = new javax.swing.JLabel();
        Frecuenteape3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        PanelVentas = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        txtcantventas = new javax.swing.JLabel();
        panelproductos = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtidproducto = new javax.swing.JTextField();
        txtnombreproducto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtdescripcionproducto = new javax.swing.JTextArea();
        txtpcompraprod = new javax.swing.JTextField();
        txtpreventprod = new javax.swing.JTextField();
        txtstockprod = new javax.swing.JTextField();
        txtidcategoriaprod = new javax.swing.JTextField();
        txtcategoriaprod = new javax.swing.JTextField();
        btnnuevoproducto = new rojerusan.RSButtonHover();
        btnGuardarProducto = new rojerusan.RSButtonHover();
        btnbuscacategoriaprod = new rojerusan.RSButtonHover();
        btneliminarproducto = new rojerusan.RSButtonHover();
        btnmodificarproducto = new rojerusan.RSButtonHover();
        jPanel4 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        btnbuscaproducto = new rojerusan.RSButtonHover();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaproductos = new javax.swing.JTable();
        filtraproductos = new javax.swing.JTextField();
        panelcategoria = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        txtidcat = new javax.swing.JTextField();
        txtnomcategoria = new javax.swing.JTextField();
        btnnuevacat = new rojerusan.RSButtonHover();
        btnagregacat = new rojerusan.RSButtonHover();
        btnmodificacat = new rojerusan.RSButtonHover();
        btneliminacat = new rojerusan.RSButtonHover();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacategoria = new javax.swing.JTable();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        txtfiltracate = new javax.swing.JTextField();
        btnbuscaCatego = new rojerusan.RSButtonHover();
        panelclientes = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        btnsalir1 = new RSMaterialComponent.RSButtonMaterialDos();
        jPanel21 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txttelcliente = new javax.swing.JTextField();
        txtdirecccliente = new javax.swing.JTextField();
        txtcorreoclietne = new javax.swing.JTextField();
        txtapecliente = new javax.swing.JTextField();
        txtnomcliente = new javax.swing.JTextField();
        txtidcliente = new javax.swing.JTextField();
        txtnumdoccliente = new javax.swing.JTextField();
        cbotipcliente = new javax.swing.JComboBox<>();
        btnnuevocliente = new rojerusan.RSButtonHover();
        btnagregarcliente = new rojerusan.RSButtonHover();
        btnmodificarcliente = new rojerusan.RSButtonHover();
        btneliminarcliente = new rojerusan.RSButtonHover();
        jPanel22 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        txtfiltracliente = new javax.swing.JTextField();
        btnbuscarcliente = new RSMaterialComponent.RSButtonMaterialDos();
        panelusuarios = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        txtidusuario = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtusuario = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtcontra = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        cbotipousuario = new javax.swing.JComboBox<>();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        txtnomusuario = new javax.swing.JTextField();
        txtapeusuarip = new javax.swing.JTextField();
        txtcorreousuario = new javax.swing.JTextField();
        btnnuevousuario = new rojerusan.RSButtonHover();
        btnagregausuario = new rojerusan.RSButtonHover();
        btnmodificausuario = new rojerusan.RSButtonHover();
        btneliminausuario = new rojerusan.RSButtonHover();
        jPanel25 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablausuario = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        txtbuscausuario = new javax.swing.JTextField();
        btnbuscausuario = new rojerusan.RSButtonHover();
        panelventas = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        rSLabelFecha1 = new rojeru_san.RSLabelFecha();
        txtrucventa = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        txtnumventa = new javax.swing.JLabel();
        idUser = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        idclienventa = new javax.swing.JTextField();
        clienteventa = new javax.swing.JTextField();
        nomvendedorventa = new javax.swing.JTextField();
        idprodventa = new javax.swing.JTextField();
        productventa = new javax.swing.JTextField();
        buscaclienteventa = new RSMaterialComponent.RSButtonMaterialDos();
        btnnuevaVenta = new RSMaterialComponent.RSButtonMaterialDos();
        btnagregarprod = new RSMaterialComponent.RSButtonMaterialDos();
        buscaprodventa = new RSMaterialComponent.RSButtonMaterialDos();
        txtprecioprodventa = new javax.swing.JTextField();
        txtcantidadventa = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        txtstockventa = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaventa = new javax.swing.JTable();
        jLabel70 = new javax.swing.JLabel();
        txttotalapagar = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        txtpagacon = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        txtcambio = new javax.swing.JTextField();
        btnGeneraVenta = new RSMaterialComponent.RSButtonMaterialDos();
        btncalcular = new RSMaterialComponent.RSButtonMaterialDos();
        panelAcercaDe = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnTiktok = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setState(6);

        jPanel1.setBackground(new java.awt.Color(242, 238, 240));

        kGradientPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        kGradientPanel1.setkEndColor(new java.awt.Color(175, 122, 197));
        kGradientPanel1.setkGradientFocus(250);
        kGradientPanel1.setkStartColor(new java.awt.Color(100, 78, 128));
        kGradientPanel1.setkTransparentControls(false);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N

        btnhome.setBackground(new java.awt.Color(255, 255, 255));
        btnhome.setForeground(new java.awt.Color(0, 0, 0));
        btnhome.setText("Home");
        btnhome.setBackgroundHover(new java.awt.Color(175, 122, 197));
        btnhome.setForegroundText(new java.awt.Color(0, 0, 0));
        btnhome.setSelected(true);
        btnhome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhomeActionPerformed(evt);
            }
        });

        btnproductos.setBackground(new java.awt.Color(255, 255, 255));
        btnproductos.setForeground(new java.awt.Color(0, 0, 0));
        btnproductos.setText("Productos");
        btnproductos.setBackgroundHover(new java.awt.Color(175, 122, 197));
        btnproductos.setForegroundText(new java.awt.Color(0, 0, 0));
        btnproductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnproductosActionPerformed(evt);
            }
        });

        btnclientes.setBackground(new java.awt.Color(255, 255, 255));
        btnclientes.setForeground(new java.awt.Color(0, 0, 0));
        btnclientes.setText("CLIENTES");
        btnclientes.setBackgroundHover(new java.awt.Color(175, 122, 197));
        btnclientes.setForegroundText(new java.awt.Color(0, 0, 0));
        btnclientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclientesActionPerformed(evt);
            }
        });

        btnusuarios.setBackground(new java.awt.Color(255, 255, 255));
        btnusuarios.setForeground(new java.awt.Color(0, 0, 0));
        btnusuarios.setText("USUARIOS");
        btnusuarios.setBackgroundHover(new java.awt.Color(175, 122, 197));
        btnusuarios.setForegroundText(new java.awt.Color(0, 0, 0));
        btnusuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnusuariosActionPerformed(evt);
            }
        });

        btncategorias.setBackground(new java.awt.Color(255, 255, 255));
        btncategorias.setForeground(new java.awt.Color(0, 0, 0));
        btncategorias.setText("CATEGORIAS");
        btncategorias.setBackgroundHover(new java.awt.Color(175, 122, 197));
        btncategorias.setForegroundText(new java.awt.Color(0, 0, 0));
        btncategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncategoriasActionPerformed(evt);
            }
        });

        btnventas.setBackground(new java.awt.Color(255, 255, 255));
        btnventas.setForeground(new java.awt.Color(0, 0, 0));
        btnventas.setText("VENTAS");
        btnventas.setBackgroundHover(new java.awt.Color(175, 122, 197));
        btnventas.setForegroundText(new java.awt.Color(0, 0, 0));
        btnventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnventasActionPerformed(evt);
            }
        });

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setText("VENTA MAX");

        btnAcercaDe.setBackground(new java.awt.Color(255, 255, 255));
        btnAcercaDe.setForeground(new java.awt.Color(0, 0, 0));
        btnAcercaDe.setText("Acerca DE");
        btnAcercaDe.setBackgroundHover(new java.awt.Color(175, 122, 197));
        btnAcercaDe.setForegroundText(new java.awt.Color(0, 0, 0));
        btnAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcercaDeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnventas, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnusuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnclientes, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnhome, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAcercaDe, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel74)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel74)
                .addGap(38, 38, 38)
                .addComponent(btnhome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnproductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(btncategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnclientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnventas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnusuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAcercaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paneles.setBackground(new java.awt.Color(242, 238, 240));
        paneles.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        paneles.setEnabled(false);
        paneles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelesMouseClicked(evt);
            }
        });
        paneles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                panelesKeyPressed(evt);
            }
        });

        panelhome.setBackground(new java.awt.Color(242, 238, 240));
        panelhome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelhomeMouseClicked(evt);
            }
        });
        panelhome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                panelhomeKeyPressed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel21.setBackground(new java.awt.Color(0, 0, 0));
        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Bienvenido al Sistema:");

        btnsalir.setBackground(new java.awt.Color(153, 0, 0));
        btnsalir.setText("X");
        btnsalir.setAlignmentY(0.0F);
        btnsalir.setBackgroundHover(new java.awt.Color(255, 51, 51));
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        homenombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        homenombre.setText("jLabel73");

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel73.setText("Codigo: ");

        homeiduser.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        homeiduser.setText("jLabel75");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(homenombre, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(homeiduser, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnsalir, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnsalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(homenombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(homeiduser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelClientes.setBackground(new java.awt.Color(22, 146, 109));
        panelClientes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("CLIENTES");

        txtnumClientes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtnumClientes.setForeground(new java.awt.Color(255, 255, 255));
        txtnumClientes.setText("20");

        javax.swing.GroupLayout panelClientesLayout = new javax.swing.GroupLayout(panelClientes);
        panelClientes.setLayout(panelClientesLayout);
        panelClientesLayout.setHorizontalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtnumClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        panelClientesLayout.setVerticalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnumClientes)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        PanelProductos.setBackground(new java.awt.Color(245, 182, 0));
        PanelProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PanelProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelProductos.setPreferredSize(new java.awt.Dimension(247, 68));

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("PRODUCTOS");

        txtnumpROD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtnumpROD.setForeground(new java.awt.Color(255, 255, 255));
        txtnumpROD.setText("20");

        javax.swing.GroupLayout PanelProductosLayout = new javax.swing.GroupLayout(PanelProductos);
        PanelProductos.setLayout(PanelProductosLayout);
        PanelProductosLayout.setHorizontalGroup(
            PanelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelProductosLayout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addGroup(PanelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelProductosLayout.createSequentialGroup()
                        .addComponent(jLabel78)
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelProductosLayout.createSequentialGroup()
                        .addComponent(txtnumpROD, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))))
        );
        PanelProductosLayout.setVerticalGroup(
            PanelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnumpROD)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        PanelUsuarios.setBackground(new java.awt.Color(91, 75, 122));
        PanelUsuarios.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PanelUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelUsuarios.setPreferredSize(new java.awt.Dimension(247, 69));

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setText("USUARIOS");

        txtcantiusuarios.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtcantiusuarios.setForeground(new java.awt.Color(255, 255, 255));
        txtcantiusuarios.setText("2");

        javax.swing.GroupLayout PanelUsuariosLayout = new javax.swing.GroupLayout(PanelUsuarios);
        PanelUsuarios.setLayout(PanelUsuariosLayout);
        PanelUsuariosLayout.setHorizontalGroup(
            PanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUsuariosLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(txtcantiusuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelUsuariosLayout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addComponent(jLabel79)
                .addGap(83, 83, 83))
        );
        PanelUsuariosLayout.setVerticalGroup(
            PanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtcantiusuarios)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        rSCalendar2.setColorBackground(new java.awt.Color(91, 75, 122));
        rSCalendar2.setColorButtonHover(new java.awt.Color(165, 105, 189));
        rSCalendar2.setColorForeground(new java.awt.Color(245, 182, 0));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rSCalendar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(rSCalendar2, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        barra1.setForeground(new java.awt.Color(91, 75, 122));
        barra1.setValue(20);
        barra1.setColorSelForeground(new java.awt.Color(0, 0, 0));
        barra1.setHorizontalOrientacion(false);
        barra1.setString("20");

        barra2.setForeground(new java.awt.Color(245, 182, 0));
        barra2.setValue(15);
        barra2.setColorSelForeground(new java.awt.Color(0, 0, 0));
        barra2.setHorizontalOrientacion(false);
        barra2.setString("15");

        barra3.setForeground(new java.awt.Color(22, 146, 109));
        barra3.setValue(10);
        barra3.setColorSelForeground(new java.awt.Color(0, 0, 0));
        barra3.setHorizontalOrientacion(false);
        barra3.setString("10");

        jPanel11.setBackground(new java.awt.Color(91, 75, 122));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel12.setBackground(new java.awt.Color(245, 182, 0));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(22, 146, 109));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        masvendidoA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        masvendidoA.setText("Producto A   20 ");

        masVendidaB.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        masVendidaB.setText("Producto B  15");

        masVendidaC.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        masVendidaC.setText("Producto C  10");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Productos mas Vendidos");

        TprodMasVendidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(TprodMasVendidos);

        TclieMasfrecuentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(TclieMasfrecuentes);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(masvendidoA))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(masVendidaB))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(masVendidaC)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(239, 239, 239)))
                .addComponent(barra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(barra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(barra3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barra3, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barra1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barra2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(78, 78, 78)
                                        .addComponent(masVendidaB)
                                        .addGap(50, 50, 50)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(masVendidaC)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(masvendidoA, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(53, 53, 53)
                                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)))))))
                .addContainerGap(127, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Productos con nivel de stock bajo");

        jPanel32.setBackground(new java.awt.Color(236, 112, 99));
        jPanel32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("Stock Menor o igual a 10");

        stockBajo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        stockBajo.setForeground(new java.awt.Color(255, 255, 255));
        stockBajo.setText("2");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(stockBajo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel81)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel81)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stockBajo)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel16.setBackground(new java.awt.Color(22, 146, 109));
        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel16.setPreferredSize(new java.awt.Dimension(151, 131));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Frecuente1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Frecuente1.setForeground(new java.awt.Color(255, 255, 255));
        Frecuente1.setText("Julio Andrade");
        jPanel16.add(Frecuente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 13, -1, -1));

        FreCant1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        FreCant1.setForeground(new java.awt.Color(255, 255, 255));
        FreCant1.setText("30");
        jPanel16.add(FreCant1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 97, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Compras:");
        jPanel16.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 81, -1, -1));

        asdasd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        asdasd.setForeground(new java.awt.Color(255, 255, 255));
        asdasd.setText("COD:");
        jPanel16.add(asdasd, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 55, -1, -1));

        FreCod1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        FreCod1.setForeground(new java.awt.Color(255, 255, 255));
        FreCod1.setText("jLabel1");
        jPanel16.add(FreCod1, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 55, -1, -1));

        Frecuenteape1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Frecuenteape1.setForeground(new java.awt.Color(255, 255, 255));
        Frecuenteape1.setText("Julio Andrade");
        jPanel16.add(Frecuenteape1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 34, -1, -1));

        jPanel17.setBackground(new java.awt.Color(91, 75, 122));
        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Frecuente2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Frecuente2.setForeground(new java.awt.Color(255, 255, 255));
        Frecuente2.setText("Cesar Andrade");
        jPanel17.add(Frecuente2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 13, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Compras:");
        jPanel17.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        FreCant2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        FreCant2.setForeground(new java.awt.Color(255, 255, 255));
        FreCant2.setText("25");
        jPanel17.add(FreCant2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        FreCod2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        FreCod2.setForeground(new java.awt.Color(255, 255, 255));
        FreCod2.setText("jLabel1");
        jPanel17.add(FreCod2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        asdasd1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        asdasd1.setForeground(new java.awt.Color(255, 255, 255));
        asdasd1.setText("COD:");
        jPanel17.add(asdasd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        Frecuenteape2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Frecuenteape2.setForeground(new java.awt.Color(255, 255, 255));
        Frecuenteape2.setText("Julio Andrade");
        jPanel17.add(Frecuenteape2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 35, -1, -1));

        jPanel18.setBackground(new java.awt.Color(245, 182, 0));
        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel18.setPreferredSize(new java.awt.Dimension(151, 131));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Frecuente3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Frecuente3.setForeground(new java.awt.Color(255, 255, 255));
        Frecuente3.setText("Camila caceres");
        jPanel18.add(Frecuente3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 13, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Compras:");
        jPanel18.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 76, -1, -1));

        FreCant3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        FreCant3.setForeground(new java.awt.Color(255, 255, 255));
        FreCant3.setText("20");
        jPanel18.add(FreCant3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 92, -1, -1));

        FreCod3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        FreCod3.setForeground(new java.awt.Color(255, 255, 255));
        FreCod3.setText("jLabel1");
        jPanel18.add(FreCod3, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 55, -1, -1));

        asdasd2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        asdasd2.setForeground(new java.awt.Color(255, 255, 255));
        asdasd2.setText("COD:");
        jPanel18.add(asdasd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 55, -1, -1));

        Frecuenteape3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Frecuenteape3.setForeground(new java.awt.Color(255, 255, 255));
        Frecuenteape3.setText("Julio Andrade");
        jPanel18.add(Frecuenteape3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 34, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("3 Clientes ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Mas Frecuentes");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(11, 11, 11)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        PanelVentas.setBackground(new java.awt.Color(236, 112, 99));
        PanelVentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PanelVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelVentas.setPreferredSize(new java.awt.Dimension(247, 65));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("VENTAS");

        txtcantventas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtcantventas.setForeground(new java.awt.Color(255, 255, 255));
        txtcantventas.setText("2");

        javax.swing.GroupLayout PanelVentasLayout = new javax.swing.GroupLayout(PanelVentas);
        PanelVentas.setLayout(PanelVentasLayout);
        PanelVentasLayout.setHorizontalGroup(
            PanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVentasLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(PanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtcantventas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        PanelVentasLayout.setVerticalGroup(
            PanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel80)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcantventas)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelhomeLayout = new javax.swing.GroupLayout(panelhome);
        panelhome.setLayout(panelhomeLayout);
        panelhomeLayout.setHorizontalGroup(
            panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelhomeLayout.createSequentialGroup()
                .addGroup(panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelhomeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelhomeLayout.createSequentialGroup()
                                .addGroup(panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelhomeLayout.createSequentialGroup()
                                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelhomeLayout.createSequentialGroup()
                                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panelhomeLayout.createSequentialGroup()
                                .addComponent(panelClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(PanelProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(PanelUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(PanelVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        panelhomeLayout.setVerticalGroup(
            panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelhomeLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PanelProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addComponent(PanelUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(PanelVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelhomeLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelhomeLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        paneles.addTab("Home", panelhome);

        panelproductos.setBackground(new java.awt.Color(242, 238, 240));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel24.setText("ID:");

        jLabel25.setText("Nombre:");

        jLabel26.setText("Descripcion:");

        jLabel27.setText("Precio Compra:");

        jLabel28.setText("Precio Venta:");

        jLabel29.setText("Stock:");

        jLabel30.setText("IdCategoria:");

        jLabel31.setText("Categoria:");

        txtidproducto.setEnabled(false);

        txtdescripcionproducto.setColumns(20);
        txtdescripcionproducto.setRows(5);
        jScrollPane1.setViewportView(txtdescripcionproducto);

        btnnuevoproducto.setBackground(new java.awt.Color(245, 182, 0));
        btnnuevoproducto.setText("Nuevo");
        btnnuevoproducto.setColorHover(new java.awt.Color(255, 211, 84));
        btnnuevoproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoproductoActionPerformed(evt);
            }
        });

        btnGuardarProducto.setBackground(new java.awt.Color(22, 146, 109));
        btnGuardarProducto.setText("Guardar");
        btnGuardarProducto.setColorHover(new java.awt.Color(39, 223, 168));
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });

        btnbuscacategoriaprod.setText("...");
        btnbuscacategoriaprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscacategoriaprodActionPerformed(evt);
            }
        });

        btneliminarproducto.setBackground(new java.awt.Color(236, 112, 99));
        btneliminarproducto.setText("Eliminar");
        btneliminarproducto.setColorHover(new java.awt.Color(245, 172, 164));
        btneliminarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarproductoActionPerformed(evt);
            }
        });

        btnmodificarproducto.setBackground(new java.awt.Color(91, 75, 122));
        btnmodificarproducto.setText("Modificar");
        btnmodificarproducto.setColorHover(new java.awt.Color(126, 106, 166));
        btnmodificarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarproductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnnuevoproducto, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(btnGuardarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btneliminarproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnmodificarproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtidcategoriaprod, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnbuscacategoriaprod, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtstockprod)
                            .addComponent(txtpreventprod)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 4, Short.MAX_VALUE)
                        .addComponent(jLabel30)
                        .addGap(203, 203, 203))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(1, 1, 1)
                        .addComponent(txtpcompraprod))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcategoriaprod, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel24))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtnombreproducto, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                    .addComponent(txtidproducto)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtidproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtnombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtpcompraprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtpreventprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtstockprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel30)
                        .addComponent(txtidcategoriaprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnbuscacategoriaprod, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtcategoriaprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnnuevoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnmodificarproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneliminarproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(203, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel32.setText("Filtrar por nombre:");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setText("Lista de Productos");

        btnbuscaproducto.setText("...");
        btnbuscaproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscaproductoActionPerformed(evt);
            }
        });

        tablaproductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaproductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaproductosMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tablaproductos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane7)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 365, Short.MAX_VALUE)
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filtraproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnbuscaproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(btnbuscaproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtraproductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane7)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelproductosLayout = new javax.swing.GroupLayout(panelproductos);
        panelproductos.setLayout(panelproductosLayout);
        panelproductosLayout.setHorizontalGroup(
            panelproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelproductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelproductosLayout.setVerticalGroup(
            panelproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelproductosLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(panelproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        paneles.addTab("Prodcutos", panelproductos);

        panelcategoria.setBackground(new java.awt.Color(242, 238, 240));

        jPanel26.setBackground(new java.awt.Color(100, 78, 128));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("REGISTRO DE CATEGORIAS");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel55)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel55)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel56.setText("ID:");

        jLabel57.setText("Nombre:");

        btnnuevacat.setBackground(new java.awt.Color(245, 182, 0));
        btnnuevacat.setText("Nuevo");
        btnnuevacat.setColorHover(new java.awt.Color(255, 211, 84));
        btnnuevacat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevacatActionPerformed(evt);
            }
        });

        btnagregacat.setBackground(new java.awt.Color(22, 146, 109));
        btnagregacat.setText("Guardar");
        btnagregacat.setColorHover(new java.awt.Color(39, 223, 168));
        btnagregacat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregacatActionPerformed(evt);
            }
        });

        btnmodificacat.setBackground(new java.awt.Color(91, 75, 122));
        btnmodificacat.setText("Modificar");
        btnmodificacat.setColorHover(new java.awt.Color(126, 106, 166));
        btnmodificacat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificacatActionPerformed(evt);
            }
        });

        btneliminacat.setBackground(new java.awt.Color(236, 112, 99));
        btneliminacat.setText("Eliminar");
        btneliminacat.setColorHover(new java.awt.Color(245, 172, 164));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtnomcategoria, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(txtidcat))
                .addGap(29, 29, 29))
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnnuevacat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnagregacat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btneliminacat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnmodificacat, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(txtidcat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnomcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addGap(51, 51, 51)
                .addComponent(btnnuevacat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnagregacat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnmodificacat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btneliminacat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(372, Short.MAX_VALUE))
        );

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablacategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablacategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacategoriaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablacategoria);

        jLabel58.setText("LISTADO DE CATEGORIA");

        jLabel59.setText("Filtrar por nombre:");

        btnbuscaCatego.setText("...");
        btnbuscaCatego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscaCategoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel58)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfiltracate, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnbuscaCatego, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel58)
                .addGap(22, 22, 22)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(txtfiltracate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscaCatego, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelcategoriaLayout = new javax.swing.GroupLayout(panelcategoria);
        panelcategoria.setLayout(panelcategoriaLayout);
        panelcategoriaLayout.setHorizontalGroup(
            panelcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelcategoriaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelcategoriaLayout.setVerticalGroup(
            panelcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcategoriaLayout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 24, Short.MAX_VALUE))
        );

        paneles.addTab("Categoria", panelcategoria);

        panelclientes.setBackground(new java.awt.Color(242, 238, 240));

        jPanel20.setBackground(new java.awt.Color(100, 78, 128));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("RREGISTRO DE CLIENTES");

        btnsalir1.setBackground(new java.awt.Color(153, 0, 0));
        btnsalir1.setText("X");
        btnsalir1.setAlignmentY(0.0F);
        btnsalir1.setBackgroundHover(new java.awt.Color(255, 51, 51));
        btnsalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalir1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnsalir1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(btnsalir1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel35.setText("ID:");

        jLabel36.setText("Nombre:");

        jLabel37.setText("Apellido: ");

        jLabel38.setText("Correo:");

        jLabel39.setText("Direccion:");

        jLabel40.setText("Telefono:");

        jLabel41.setText("Documento:");

        jLabel42.setText("N° Docu.:");

        cbotipcliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "RUC", "PASAPORTE", "CARNET EXTRANJERO" }));

        btnnuevocliente.setBackground(new java.awt.Color(245, 182, 0));
        btnnuevocliente.setText("Nuevo");
        btnnuevocliente.setColorHover(new java.awt.Color(255, 211, 84));
        btnnuevocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoclienteActionPerformed(evt);
            }
        });

        btnagregarcliente.setBackground(new java.awt.Color(22, 146, 109));
        btnagregarcliente.setText("Guardar");
        btnagregarcliente.setColorHover(new java.awt.Color(39, 223, 168));
        btnagregarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarclienteActionPerformed(evt);
            }
        });

        btnmodificarcliente.setBackground(new java.awt.Color(91, 75, 122));
        btnmodificarcliente.setText("Modificar");
        btnmodificarcliente.setColorHover(new java.awt.Color(126, 106, 166));
        btnmodificarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarclienteActionPerformed(evt);
            }
        });

        btneliminarcliente.setBackground(new java.awt.Color(236, 112, 99));
        btneliminarcliente.setText("Eliminar");
        btneliminarcliente.setColorHover(new java.awt.Color(245, 172, 164));
        btneliminarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarclienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36)
                            .addComponent(jLabel37)
                            .addComponent(jLabel38)
                            .addComponent(jLabel39)
                            .addComponent(jLabel40)
                            .addComponent(jLabel42))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txttelcliente)
                            .addComponent(txtdirecccliente)
                            .addComponent(txtcorreoclietne)
                            .addComponent(txtapecliente)
                            .addComponent(txtnomcliente)
                            .addComponent(txtidcliente)
                            .addComponent(txtnumdoccliente)
                            .addComponent(cbotipcliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnnuevocliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnagregarcliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btneliminarcliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnmodificarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtnomcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txtapecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtcorreoclietne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtdirecccliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txttelcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(cbotipcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtnumdoccliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(btnnuevocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnagregarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmodificarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneliminarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(279, Short.MAX_VALUE))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setText("Listado de Clientes");

        tablacliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablacliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaclienteMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablacliente);

        jLabel44.setText("Buscar:");

        btnbuscarcliente.setText("...");
        btnbuscarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarclienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel44)
                        .addGap(18, 18, 18)
                        .addComponent(txtfiltracliente, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addGap(4, 4, 4)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtfiltracliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelclientesLayout = new javax.swing.GroupLayout(panelclientes);
        panelclientes.setLayout(panelclientesLayout);
        panelclientesLayout.setHorizontalGroup(
            panelclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelclientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelclientesLayout.setVerticalGroup(
            panelclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelclientesLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        paneles.addTab("Clientes", panelclientes);

        panelusuarios.setBackground(new java.awt.Color(242, 238, 240));

        jPanel23.setBackground(new java.awt.Color(100, 78, 128));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("REGISTRO DE USUARIOS");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel46.setText("ID: ");

        jLabel47.setText("Usuario:");

        jLabel48.setText("Contraseña: ");

        jLabel49.setText("Tipo Usuario:");

        cbotipousuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Vendedor", " " }));

        jLabel50.setText("Nombre:");

        jLabel51.setText("Apellido:");

        jLabel52.setText("Correo:");

        btnnuevousuario.setBackground(new java.awt.Color(245, 182, 0));
        btnnuevousuario.setText("Nuevo");
        btnnuevousuario.setColorHover(new java.awt.Color(255, 211, 84));
        btnnuevousuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevousuarioActionPerformed(evt);
            }
        });

        btnagregausuario.setBackground(new java.awt.Color(22, 146, 109));
        btnagregausuario.setText("Guardar");
        btnagregausuario.setColorHover(new java.awt.Color(39, 223, 168));
        btnagregausuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregausuarioActionPerformed(evt);
            }
        });

        btnmodificausuario.setBackground(new java.awt.Color(91, 75, 122));
        btnmodificausuario.setText("Modificar");
        btnmodificausuario.setColorHover(new java.awt.Color(126, 106, 166));
        btnmodificausuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificausuarioActionPerformed(evt);
            }
        });

        btneliminausuario.setBackground(new java.awt.Color(236, 112, 99));
        btneliminausuario.setText("Eliminar");
        btneliminausuario.setColorHover(new java.awt.Color(245, 172, 164));
        btneliminausuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminausuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnnuevousuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnagregausuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btneliminausuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnmodificausuario, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel24Layout.createSequentialGroup()
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel46))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtidusuario)
                                    .addComponent(txtusuario, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel24Layout.createSequentialGroup()
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel50)
                                    .addComponent(jLabel51))
                                .addGap(43, 43, 43)
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtapeusuarip, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                    .addComponent(txtnomusuario, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel24Layout.createSequentialGroup()
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel52))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtcorreousuario, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                    .addComponent(cbotipousuario, 0, 172, Short.MAX_VALUE)
                                    .addComponent(txtcontra))))
                        .addGap(18, 18, 18))))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtidusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txtcontra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(txtcorreousuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbotipousuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(txtnomusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(txtapeusuarip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(btnnuevousuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnagregausuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmodificausuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneliminausuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel53.setText("LISTA DE USUARIOS");

        tablausuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablausuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablausuarioMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablausuario);

        jLabel54.setText("Buscar:");

        btnbuscausuario.setBackground(new java.awt.Color(175, 122, 197));
        btnbuscausuario.setText(".....");
        btnbuscausuario.setColorHover(new java.awt.Color(223, 201, 232));
        btnbuscausuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscausuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(669, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtbuscausuario, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbuscausuario, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel53)
                .addGap(29, 29, 29)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(txtbuscausuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscausuario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelusuariosLayout = new javax.swing.GroupLayout(panelusuarios);
        panelusuarios.setLayout(panelusuariosLayout);
        panelusuariosLayout.setHorizontalGroup(
            panelusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelusuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelusuariosLayout.setVerticalGroup(
            panelusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelusuariosLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        paneles.addTab("Usuarios", panelusuarios);

        panelventas.setBackground(new java.awt.Color(242, 238, 240));

        jPanel29.setBackground(new java.awt.Color(100, 78, 128));

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Fecha:");

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("NUEVA VENTA");

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Numero Venta:");

        rSLabelFecha1.setForeground(new java.awt.Color(255, 255, 255));
        rSLabelFecha1.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N

        txtrucventa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtrucventa.setForeground(new java.awt.Color(255, 255, 255));
        txtrucventa.setText("25632589654");

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("RUC:");

        txtnumventa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtnumventa.setForeground(new java.awt.Color(255, 255, 255));
        txtnumventa.setText("001");

        idUser.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        idUser.setForeground(new java.awt.Color(100, 78, 128));
        idUser.setText("jLabel3");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnumventa)
                .addGap(146, 146, 146)
                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtrucventa)
                .addGap(135, 135, 135)
                .addComponent(idUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSLabelFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel61)
                .addGap(401, 401, 401))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel62)
                        .addComponent(txtrucventa)
                        .addComponent(jLabel64)
                        .addComponent(jLabel60)
                        .addComponent(txtnumventa)
                        .addComponent(idUser))
                    .addComponent(rSLabelFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel65.setText("ID_Cliente:");

        jLabel66.setText("Vendedor:");

        jLabel67.setText("Cliente:");

        jLabel68.setText("ID_Producto:");

        jLabel69.setText("Producto:");

        idclienventa.setEnabled(false);

        clienteventa.setEnabled(false);

        nomvendedorventa.setEnabled(false);

        idprodventa.setEnabled(false);

        productventa.setEnabled(false);

        buscaclienteventa.setBackground(new java.awt.Color(22, 146, 109));
        buscaclienteventa.setText("...");
        buscaclienteventa.setBackgroundHover(new java.awt.Color(37, 218, 164));
        buscaclienteventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscaclienteventaActionPerformed(evt);
            }
        });

        btnnuevaVenta.setBackground(new java.awt.Color(0, 153, 153));
        btnnuevaVenta.setText("Nuevo");
        btnnuevaVenta.setBackgroundHover(new java.awt.Color(0, 102, 255));
        btnnuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevaVentaActionPerformed(evt);
            }
        });

        btnagregarprod.setBackground(new java.awt.Color(0, 102, 255));
        btnagregarprod.setText("Agregar");
        btnagregarprod.setBackgroundHover(new java.awt.Color(186, 42, 26));
        btnagregarprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarprodActionPerformed(evt);
            }
        });

        buscaprodventa.setBackground(new java.awt.Color(245, 182, 0));
        buscaprodventa.setText("...");
        buscaprodventa.setBackgroundHover(new java.awt.Color(255, 204, 59));
        buscaprodventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscaprodventaActionPerformed(evt);
            }
        });

        txtprecioprodventa.setEnabled(false);

        jLabel75.setText("Precio:");

        jLabel76.setText("Cant.");

        jLabel77.setText("Stock:");

        txtstockventa.setEnabled(false);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel65)
                            .addComponent(jLabel67))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addGap(22, 22, 22)))
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(nomvendedorventa)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel30Layout.createSequentialGroup()
                        .addComponent(idclienventa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscaclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(clienteventa, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(83, 83, 83)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel68)
                    .addComponent(jLabel69)
                    .addComponent(jLabel75))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(idprodventa, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscaprodventa, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                                .addComponent(txtprecioprodventa, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel76)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcantidadventa, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(productventa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(txtstockventa, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnagregarprod, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel30Layout.createSequentialGroup()
                                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(271, 271, 271))
                            .addComponent(btnnuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(549, 549, 549))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel68)
                            .addComponent(idprodventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscaprodventa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productventa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel69))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtprecioprodventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcantidadventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel75)
                            .addComponent(jLabel76)
                            .addComponent(jLabel77)
                            .addComponent(txtstockventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel65)
                                .addComponent(idclienventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buscaclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel30Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(btnnuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel30Layout.createSequentialGroup()
                                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel67)
                                    .addComponent(clienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nomvendedorventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel66)))
                            .addComponent(btnagregarprod, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaventa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tablaventa);

        jLabel70.setText("Total a Pagar:");

        txttotalapagar.setEnabled(false);

        jLabel71.setText("Paga Con:");

        jLabel72.setText("Cambio:");

        txtcambio.setEnabled(false);

        btnGeneraVenta.setBackground(new java.awt.Color(100, 78, 128));
        btnGeneraVenta.setText("Generar Venta");
        btnGeneraVenta.setBackgroundHover(new java.awt.Color(145, 108, 192));
        btnGeneraVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeneraVentaActionPerformed(evt);
            }
        });

        btncalcular.setBackground(new java.awt.Color(100, 78, 128));
        btncalcular.setText("Calcular");
        btncalcular.setBackgroundHover(new java.awt.Color(147, 109, 196));
        btncalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalcularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(btnGeneraVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(597, 597, 597)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGap(186, 186, 186)
                                .addComponent(jLabel70)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                                .addComponent(jLabel71)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btncalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(txtpagacon, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel72)
                                .addGap(47, 47, 47)))
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txttotalapagar)
                            .addComponent(txtcambio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(txttotalapagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGeneraVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(txtpagacon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel72)
                    .addComponent(txtcambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btncalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout panelventasLayout = new javax.swing.GroupLayout(panelventas);
        panelventas.setLayout(panelventasLayout);
        panelventasLayout.setHorizontalGroup(
            panelventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelventasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 1201, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelventasLayout.setVerticalGroup(
            panelventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelventasLayout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        paneles.addTab("Ventas", panelventas);

        jPanel2.setBackground(new java.awt.Color(100, 78, 128));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Acerca De");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addContainerGap(1125, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 0, 0));
        jLabel4.setText("VentApps");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Realizado por:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 102, 0));
        jLabel3.setText("Helio Pizarro Puentes");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 102, 0));
        jLabel8.setText("GIUHEL - DEV");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/whatsapp.png"))); // NOI18N
        jLabel9.setText("931037416");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Redes Sociales:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Lima -  Perú");

        btnTiktok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tik-tok.png"))); // NOI18N
        btnTiktok.setText("TIKTOK");
        btnTiktok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiktokActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTiktok, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(53, 53, 53)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTiktok)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 435, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelAcercaDeLayout = new javax.swing.GroupLayout(panelAcercaDe);
        panelAcercaDe.setLayout(panelAcercaDeLayout);
        panelAcercaDeLayout.setHorizontalGroup(
            panelAcercaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAcercaDeLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(panelAcercaDeLayout.createSequentialGroup()
                .addGap(430, 430, 430)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAcercaDeLayout.setVerticalGroup(
            panelAcercaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAcercaDeLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        paneles.addTab("Acerca De.", panelAcercaDe);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paneles))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paneles))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnhomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhomeActionPerformed
        // TODO add your handling code here:
        btnhome.setSelected(true);
        if(btnhome.isSelected()){
            btnclientes.setSelected(false);
            btnproductos.setSelected(false);
            btnusuarios.setSelected(false);
            btncategorias.setSelected(false);
            btnventas.setSelected(false);
        }
        paneles.setSelectedComponent(panelhome);
    }//GEN-LAST:event_btnhomeActionPerformed

    private void btnproductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnproductosActionPerformed
        // TODO add your handling code here:
        mostrarDatosProd("");
        btnproductos.setSelected(true);
        if(btnproductos.isSelected()){
            btnclientes.setSelected(false);
            btnhome.setSelected(false);
            btnusuarios.setSelected(false);
            btncategorias.setSelected(false);
            btnventas.setSelected(false);
            btnAcercaDe.setSelected(false);
            btnAcercaDe.setSelected(false);
        }
        paneles.setSelectedComponent(panelproductos);
    }//GEN-LAST:event_btnproductosActionPerformed

    private void btnclientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclientesActionPerformed
        // TODO add your handling code here:
        mostrarDatosClient("");
        btnclientes.setSelected(true);
        if(btnclientes.isSelected()){
            btnproductos.setSelected(false);
            btnhome.setSelected(false);
            btnusuarios.setSelected(false);
            btncategorias.setSelected(false);
            btnventas.setSelected(false);
            btnAcercaDe.setSelected(false);
        }
        paneles.setSelectedComponent(panelclientes);
    }//GEN-LAST:event_btnclientesActionPerformed

    private void panelesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelesKeyPressed
        // TODO add your handling code here:  
    }//GEN-LAST:event_panelesKeyPressed

    private void panelesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelesMouseClicked

    }//GEN-LAST:event_panelesMouseClicked

    private void panelhomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelhomeMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_panelhomeMouseClicked

    private void panelhomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelhomeKeyPressed
        // TODO add your handling code here
        
    }//GEN-LAST:event_panelhomeKeyPressed

    private void btnusuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnusuariosActionPerformed
        // TODO add your handling code here:
         mostrarDatosUser("");
        btnusuarios.setSelected(true);
        if(btnusuarios.isSelected()){
            btnproductos.setSelected(false);
            btnhome.setSelected(false);
            btnclientes.setSelected(false);
            btncategorias.setSelected(false);
            btnventas.setSelected(false);
            btnAcercaDe.setSelected(false);
        }
        paneles.setSelectedComponent(panelusuarios);
    }//GEN-LAST:event_btnusuariosActionPerformed

    private void btncategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncategoriasActionPerformed
        // TODO add your handling code here:
        mostrarDatosCate("");
        btncategorias.setSelected(true);
        if(btncategorias.isSelected()){
            btnproductos.setSelected(false);
            btnhome.setSelected(false);
            btnclientes.setSelected(false);
            btnusuarios.setSelected(false);
            btnventas.setSelected(false);
            btnAcercaDe.setSelected(false);
        }
        paneles.setSelectedComponent(panelcategoria);
    }//GEN-LAST:event_btncategoriasActionPerformed

    private void btnventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnventasActionPerformed
        // TODO add your handling code here:
        btnventas.setSelected(true);
        if(btnventas.isSelected()){
            btnproductos.setSelected(false);
            btnhome.setSelected(false);
            btnclientes.setSelected(false);
            btnusuarios.setSelected(false);
            btncategorias.setSelected(false);
            btnAcercaDe.setSelected(false);
        }
        paneles.setSelectedComponent(panelventas);
    }//GEN-LAST:event_btnventasActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
        // TODO add your handling code here:
       // productos prod=datosdefrmProductos();
//        String SQL=String.format("insert into productos(idproducto,nombre,descripcion,preciocompra,precioventa,stock,idcategoria"
//                + ") values (null,'%s','%s',%d,%d,%d,%d)",prod.getNombre(),prod.getDescripcion(),prod.getPreciocom()
//                ,prod.getPreciovent(),prod.getStock(),prod.getIdcategoria());
        String SQL="insert into productos (nombre,descripcion,preciocompra,precioventa,stock,idcategoria) values"
                + "('"+txtnombreproducto.getText()+"','"+txtdescripcionproducto.getText()+"',"+txtpcompraprod.getText()+","+txtpreventprod.getText()+","+
                txtstockprod.getText()+","+txtidcategoriaprod.getText()+")";
        conex.ejecutarSentencia(SQL);
        mostrarDatosProd("");
        limpiarcamposProductos();
        bloqueaCamposProductos();
        cantidadProd();
        new vistas.RegistroExitoso(this, true).setVisible(true);
    }//GEN-LAST:event_btnGuardarProductoActionPerformed

    private void btnnuevoproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoproductoActionPerformed
        // TODO add your handling code here:
        
        limpiarcamposProductos();
    }//GEN-LAST:event_btnnuevoproductoActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
    try {
        // TODO add your handling code here:
        Login frm = new Login();
        frm.setVisible(true);
        dispose();
        finalize();
    } catch (Throwable ex) {
        Logger.getLogger(Frmprincipal.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btnsalirActionPerformed

    private void btnagregacatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregacatActionPerformed
        // TODO add your handling code here:
        String SQL="insert into categoria (nombre) values"
                + "('"+txtnomcategoria.getText()+"')";
        conex.ejecutarSentencia(SQL);
        mostrarDatosCate("");
        limpiarcamposcategoria();
    }//GEN-LAST:event_btnagregacatActionPerformed

    private void btnnuevacatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevacatActionPerformed
        // TODO add your handling code here:
        limpiarcamposcategoria();
    }//GEN-LAST:event_btnnuevacatActionPerformed

    private void btnmodificarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarproductoActionPerformed
        // TODO add your handling code here:
        String SQL=("update productos set nombre='"+txtnombreproducto.getText()+"',descripcion='"+txtdescripcionproducto.getText()+"',"
                + "preciocompra="+txtpcompraprod.getText()+",precioventa="+txtpreventprod.getText()+","
                + "stock="+txtstockprod.getText()+","
                + "idcategoria="+txtidcategoriaprod.getText()+" where idproducto="+txtidproducto.getText());
        conex.ejecutarSentencia(SQL);
        mostrarDatosProd("");
        limpiarcamposProductos();
        bloqueaCamposProductos();
        new vistas.RegistroModificado(this, true).setVisible(true);
    }//GEN-LAST:event_btnmodificarproductoActionPerformed

    private void btneliminarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarproductoActionPerformed
        // TODO add your handling code here:
        int confirmacion = JOptionPane.showConfirmDialog(rootPane, "¿Estas Seguro de ELiminar el Registro?","Confirmar",2);
        if (confirmacion == 0) {
            String SQL=("delete from productos where idproducto="+txtidproducto.getText());
            conex.ejecutarSentencia(SQL);
            mostrarDatosProd("");
            limpiarcamposProductos();
            bloqueaCamposProductos();
            new vistas.RegistroEliminado(this, true).setVisible(true);
            cantidadProd();
        }
    }//GEN-LAST:event_btneliminarproductoActionPerformed

    private void btnbuscaproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscaproductoActionPerformed
        // TODO add your handling code here:
        mostrarDatosProd(filtraproductos.getText());
    }//GEN-LAST:event_btnbuscaproductoActionPerformed

    private void tablaproductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproductosMouseClicked
        // TODO add your handling code here:
        limpiarcamposProductos();
        if(evt.getClickCount()==1){
            
            JTable receptor=(JTable) evt.getSource();
            txtidproducto.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 0).toString());
            txtnombreproducto.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 1).toString());
            txtdescripcionproducto.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 2).toString());
            txtpcompraprod.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 3).toString());
            txtpreventprod.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 4).toString());
            txtstockprod.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 5).toString());
            txtidcategoriaprod.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 6).toString());
            txtcategoriaprod.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 7).toString());
        }
        
        btnGuardarProducto.setEnabled(false);
        btnmodificarproducto.setEnabled(true);
        btneliminarproducto.setEnabled(true);
    }//GEN-LAST:event_tablaproductosMouseClicked

    private void btnbuscaCategoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscaCategoActionPerformed
        // TODO add your handling code here:
        mostrarDatosCate(txtfiltracate.getText());
    }//GEN-LAST:event_btnbuscaCategoActionPerformed

    private void btnmodificacatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificacatActionPerformed
        // TODO add your handling code here:
        String SQL=("update categoria set nombre='"+txtnomcategoria.getText()
                + "' where idcategoria="+txtidcat.getText());
        conex.ejecutarSentencia(SQL);
        mostrarDatosCate("");
        limpiarcamposcategoria();
    }//GEN-LAST:event_btnmodificacatActionPerformed

    private void tablacategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacategoriaMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==1){
            
            JTable receptor=(JTable) evt.getSource();
            txtidcat.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 0).toString());
            txtnomcategoria.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 1).toString());

        }
        btnagregacat.setEnabled(false);
        btnmodificacat.setEnabled(true);
        btneliminacat.setEnabled(true);
    }//GEN-LAST:event_tablacategoriaMouseClicked

    private void btnagregarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarclienteActionPerformed
        // TODO add your handling code here:
        String SQL="insert into Clientes (nombre,apellido,correo,direccion,telefono,documento,numDoc) values"
                + "('"+txtnomcliente.getText()+"','"+txtapecliente.getText()+"','"+txtcorreoclietne.getText()+
                "','"+txtdirecccliente.getText()+"','"+ txttelcliente.getText()+"','"
                +cbotipcliente.getSelectedItem().toString()+"','"+txtnumdoccliente.getText()+"')";
        conex.ejecutarSentencia(SQL);
        mostrarDatosClient("");
        limpiarcamposClientes();
        cantidadClientes();
    }//GEN-LAST:event_btnagregarclienteActionPerformed

    private void btnsalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalir1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnsalir1ActionPerformed

    private void btnnuevoclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoclienteActionPerformed
        // TODO add your handling code here:
        limpiarcamposClientes();
    }//GEN-LAST:event_btnnuevoclienteActionPerformed

    private void tablaclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaclienteMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==1){
            
            JTable receptor=(JTable) evt.getSource();
            txtidcliente.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 0).toString());
            txtnomcliente.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 1).toString());
            txtapecliente.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 2).toString());
            txtcorreoclietne.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 3).toString());
            txtdirecccliente.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 4).toString());
            txttelcliente.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 5).toString());
            cbotipcliente.setSelectedItem(receptor.getModel().getValueAt(receptor.getSelectedRow(), 6).toString());
            txtnumdoccliente.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 7).toString());
        }
        btnagregarcliente.setEnabled(false);
        btnmodificarcliente.setEnabled(true);
        btneliminarcliente.setEnabled(true);
    }//GEN-LAST:event_tablaclienteMouseClicked

    private void btnbuscarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarclienteActionPerformed
        // TODO add your handling code here:
        mostrarDatosClient(txtfiltracliente.getText());
    }//GEN-LAST:event_btnbuscarclienteActionPerformed

    private void btnmodificarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarclienteActionPerformed
        // TODO add your handling code here:
        String SQL=("update Clientes set nombre='"+txtnomcliente.getText()+"',apellido='"+txtapecliente.getText()+"',"
                + "correo='"+txtcorreoclietne.getText()+"',direccion='"+txtdirecccliente.getText()+"',"
                + "telefono='"+txttelcliente.getText()+"',"
                + "documento='"+cbotipcliente.getSelectedItem().toString()+"', numDoc='"+txtnumdoccliente.getText()+
                "' where idcliente="+txtidcliente.getText());
        conex.ejecutarSentencia(SQL);
        mostrarDatosClient("");
        limpiarcamposClientes();
    }//GEN-LAST:event_btnmodificarclienteActionPerformed

    private void btneliminarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarclienteActionPerformed
        // TODO add your handling code here:
        String SQL=("delete from Clientes where idcliente="+txtidcliente.getText());
        conex.ejecutarSentencia(SQL);
        mostrarDatosClient("");
        limpiarcamposClientes();
    }//GEN-LAST:event_btneliminarclienteActionPerformed

    private void btnnuevousuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevousuarioActionPerformed
        // TODO add your handling code here:
        limpiarcamposUsuario();
    }//GEN-LAST:event_btnnuevousuarioActionPerformed

    private void btnagregausuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregausuarioActionPerformed
        // TODO add your handling code here:
        String SQL="insert into usuarios (usuario,password,correo,tipousuario,nombre,apellido) values"
                + "('"+txtusuario.getText()+"','"+txtcontra.getText()+"','"+txtcorreousuario.getText()+"','"
                +cbotipousuario.getSelectedItem().toString()+"','"+
                txtnomusuario.getText()+"','"+txtapeusuarip.getText()+"')";
        conex.ejecutarSentencia(SQL);
        mostrarDatosUser("");
        limpiarcamposUsuario();
        cantidadUSer();
    }//GEN-LAST:event_btnagregausuarioActionPerformed

    private void btnmodificausuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificausuarioActionPerformed
        // TODO add your handling code here:
        String SQL=("update usuarios set usuario='"+txtusuario.getText()+"',password='"+txtcontra.getText()+"',"
                + "correo='"+txtcorreousuario.getText()+"',tipousuario='"+cbotipousuario.getSelectedItem().toString()+"',"
                + "nombre='"+txtnomusuario.getText()+"',"
                + "apellido='"+txtapeusuarip.getText()+"' where idusuario="+txtidusuario.getText());
        conex.ejecutarSentencia(SQL);
        mostrarDatosUser("");
        limpiarcamposUsuario();
    }//GEN-LAST:event_btnmodificausuarioActionPerformed

    private void tablausuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablausuarioMouseClicked
        // TODO add your handling code here:
         if(evt.getClickCount()==1){
            
            JTable receptor=(JTable) evt.getSource();
            txtidusuario.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 0).toString());
            txtusuario.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 1).toString());
            txtcontra.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 2).toString());
            txtcorreousuario.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 3).toString());
            cbotipousuario.setSelectedItem(receptor.getModel().getValueAt(receptor.getSelectedRow(), 4).toString());
            txtnomusuario.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 5).toString());
            txtapeusuarip.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 6).toString());
     
        }
        btnagregausuario.setEnabled(false);
        btnmodificausuario.setEnabled(true);
        btneliminausuario.setEnabled(true);
    }//GEN-LAST:event_tablausuarioMouseClicked

    private void btnbuscausuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscausuarioActionPerformed
        // TODO add your handling code here:
        mostrarDatosUser(txtbuscausuario.getText());
    }//GEN-LAST:event_btnbuscausuarioActionPerformed

    private void btneliminausuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminausuarioActionPerformed
        // TODO add your handling code here:
        String SQL=("delete from usuarios where idusuario="+txtidusuario.getText());
        conex.ejecutarSentencia(SQL);
        mostrarDatosUser("");
        limpiarcamposUsuario();
        cantidadUSer();
    }//GEN-LAST:event_btneliminausuarioActionPerformed

    private void btnagregarprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarprodActionPerformed
        // TODO add your handling code here:
        if(!idprodventa.getText().isEmpty()&&!txtcantidadventa.getText().isEmpty()){
            descontarStock();
            agregarProducto();
            limpiaPostAgregaVenta();
        }
    }//GEN-LAST:event_btnagregarprodActionPerformed

    private void btncalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalcularActionPerformed
        // TODO add your handling code here:
        if(!txtpagacon.getText().isEmpty()){
            double cambio;
            double pagacon=Double.parseDouble(txtpagacon.getText());
            cambio=pagacon-tpagar;
            txtcambio.setText(cambio+"");
        }
    }//GEN-LAST:event_btncalcularActionPerformed

    private void buscaclienteventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscaclienteventaActionPerformed
        // TODO add your handling code here:
        buscaCliente frm = new buscaCliente();
        frm.setVisible(true);
        
    }//GEN-LAST:event_buscaclienteventaActionPerformed

    private void buscaprodventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscaprodventaActionPerformed
        // TODO add your handling code here:
        buscaPRoducto frm = new buscaPRoducto();
        frm.setVisible(true);
        
    }//GEN-LAST:event_buscaprodventaActionPerformed

    private void btnGeneraVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeneraVentaActionPerformed
        // TODO add your handling code here:
        if(!idclienventa.getText().isEmpty()){
            guardarVenta();
            cantidadVentas();
            //----barrasHome
            mostrarpodMasVendidos();
            BarraProducto1();
            BarraProducto2();
            BarraProducto3();
            mostrarClienteFrecuente();
            ventaa=true;
        }
    }//GEN-LAST:event_btnGeneraVentaActionPerformed

    private void btnnuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevaVentaActionPerformed
        // TODO add your handling code here:
        if(tablaventa.getRowCount()>0){
            aumentarStock();
            nuevaVEnte();
        }else{
            nuevaVEnte();
            ventaa=false;
        }
    }//GEN-LAST:event_btnnuevaVentaActionPerformed

    private void btnbuscacategoriaprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscacategoriaprodActionPerformed
        // TODO add your handling code here:
        buscaCategoria frm = new buscaCategoria();
        frm.setVisible(true);
    }//GEN-LAST:event_btnbuscacategoriaprodActionPerformed

    private void btnAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcercaDeActionPerformed
        btnAcercaDe.setSelected(true);
        if(btnAcercaDe.isSelected()){
            btnproductos.setSelected(false);
            btnhome.setSelected(false);
            btnusuarios.setSelected(false);
            btncategorias.setSelected(false);
            btnventas.setSelected(false);
            btnclientes.setSelected(false);
        }
        paneles.setSelectedComponent(panelAcercaDe);
    }//GEN-LAST:event_btnAcercaDeActionPerformed

    private void btnTiktokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiktokActionPerformed
        // TODO add your handling code here:
        irRedes("https://www.tiktok.com/@giuhel_pip?_t=8ao5DT5HyLN&_r=1");
    }//GEN-LAST:event_btnTiktokActionPerformed

    void limpiaPostAgregaVenta(){
        idprodventa.setText("");
        productventa.setText("");
        txtprecioprodventa.setText("");
        txtcantidadventa.setText("");
        txtstockventa.setText("");
    }
    
    void nuevaVEnte(){
        numeroventa();
        idclienventa.setText("");
        clienteventa.setText("");
        txttotalapagar.setText("");
        txtpagacon.setText("");
        txtcambio.setText("");
        limpiaPostAgregaVenta();
        modelventa.setRowCount(0); 
        tablaventa.setModel(modelventa);
    }   
    
    void guardarVenta(){
        String numero=txtnumventa.getText();
        int idu=Integer.parseInt(idUser.getText());
        double monto=tpagar;
        int idc=Integer.parseInt(idclienventa.getText());
        String ruc=txtrucventa.getText();
    
        String SQL="insert into ventas (numero,idcliente,total,idusuario,RUC) values"
                + "('"+numero+"',"+idc+","+monto+","+idu+",'"+ruc+"')";
        conex.ejecutarSentencia(SQL);
        guardarDetalle();
    }
    
    void guardarDetalle(){
    try {
        ResultSet resultado=conex.consultar("select max(idventa)as nume from ventas");
        String idv=resultado.getString("nume");
        int idve;
        if(idv==null){
            idve=1;
        }else{
            idve =Integer.parseInt(idv);
        } 
        for (int i = 0; i < tablaventa.getRowCount(); i++) {
            int idpp=Integer.parseInt(tablaventa.getValueAt(i, 1).toString());
            int canti=Integer.parseInt(tablaventa.getValueAt(i, 0).toString());
            double pre=Double.parseDouble(tablaventa.getValueAt(i, 3).toString());
            String SQL="insert into detalleventa (idventa,idproducto,canti,precio) values"
                + "("+idve+","+idpp+","+canti+","+pre+")";
            conex.ejecutarSentencia(SQL);
        }
        JOptionPane.showMessageDialog(null, "venta guardada", "mensaje", 1);
        imprimirComprobante(txtnumventa.getText());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error", "error", 2);
        }
   }
    
   /*update usuarios set usuario='"+txtusuario.getText()+"',password='"+txtcontra.getText()+"',"
                + "correo='"+txtcorreousuario.getText()+"',tipousuario='"+cbotipousuario.getSelectedItem().toString()+"',"
                + "nombre='"+txtnomusuario.getText()+"',"
                + "apellido='"+txtapeusuarip.getText()+"' where idusuario="+txtidusuario.getText()*/
    void descontarStock(){
            int idpp=Integer.parseInt(idprodventa.getText());
            int canti=Integer.parseInt(txtcantidadventa.getText());
            String SQL="update productos set stock=stock-"+canti+" where idproducto="+idpp;
            conex.ejecutarSentencia(SQL);
    } 
    void aumentarStock(){
        if(ventaa==false){
            for (int i = 0; i < tablaventa.getRowCount(); i++) {
            int idpp=Integer.parseInt(tablaventa.getValueAt(i, 1).toString());
            int canti=Integer.parseInt(tablaventa.getValueAt(i, 0).toString());
            double pre=Double.parseDouble(tablaventa.getValueAt(i, 3).toString());
            String SQL="update productos set stock=stock+"+canti+" where idproducto="+idpp;
            conex.ejecutarSentencia(SQL);
            System.out.print("aumento stock");
            }
        }
    } 
    
    private Connection conection= new conexion().conexionreporte();
    private void imprimirComprobante(String text) {
        if(!text.isEmpty()){
            Map p=new HashMap();
            p.put("numventa", text);
            JasperReport report;
            JasperPrint print;
        
            try {
                report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+
                    "/src/reportes/Comprobante.jrxml");
                print=JasperFillManager.fillReport(report, p,conection);
                JasperViewer view=new JasperViewer(print,false);
                view.setTitle("Comprobante de Pago");
                view.setVisible(true);            
            
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    void agregarProducto(){
        double total;
        modelventa=(DefaultTableModel) tablaventa.getModel();
        int idp=Integer.parseInt(idprodventa.getText());
        String producto=productventa.getText();
        precio=Double.parseDouble(txtprecioprodventa.getText());
        cant=Integer.parseInt(txtcantidadventa.getText());
        int stock=Integer.parseInt(txtstockventa.getText());
        total=cant*precio;
        ArrayList lista=new ArrayList();
        if(stock>0 && cant<=stock){
            lista.add(cant);
            lista.add(idp);
            lista.add(producto);
            lista.add(precio);
            lista.add(total);
            Object[] ob=new Object[5];
            ob[0]=lista.get(0);
            ob[1]=lista.get(1);
            ob[2]=lista.get(2);
            ob[3]=lista.get(3);
            ob[4]=lista.get(4);
            modelventa.addRow(ob);
            tablaventa.setModel(modelventa);
            CalcularTotal();
        }else{
            JOptionPane.showMessageDialog(this,"Stock Insuficiente");
        }
    }
    
    void CalcularTotal(){
        tpagar=0;
        for (int i = 0; i <tablaventa.getRowCount(); i++) {
            cant=Integer.parseInt(tablaventa.getValueAt(i, 0).toString());
            precio=Double.parseDouble(tablaventa.getValueAt(i, 3).toString());
            tpagar=tpagar+(cant*precio);
        }
        txttotalapagar.setText(""+tpagar);
   }
    
    void cargabotonesiniciales(){
        btnhome.setSelected(true);
        if(btnhome.isSelected()){
            btnclientes.setSelected(false);
            btnproductos.setSelected(false);
        }
        
    }
    
    public productos datosdefrmProductos(){
        
        productos oprod=new productos();
        
        int ID=(txtidproducto.getText().isEmpty())?0: Integer.parseInt(txtidproducto.getText());
        
        oprod.setIdproducto(ID);
        oprod.setNombre(txtnombreproducto.getText());
        oprod.setDescripcion(txtdescripcionproducto.getText());
        Double precioC=Double.parseDouble(txtpcompraprod.getText());
        oprod.setPreciocom(precioC);
        Double preciov=Double.parseDouble(txtpreventprod.getText());
        oprod.setPreciovent(preciov);
        int sto=Integer.parseInt(txtstockprod.getText());
        oprod.setStock(sto);
        int idcat=Integer.parseInt(txtidcategoriaprod.getText());
        oprod.setIdcategoria(idcat);
        
        return oprod;
    }
    
    public void limpiarcamposProductos(){
        txtidproducto.setText("");
        txtnombreproducto.setText("");
        txtdescripcionproducto.setText("");
        txtpcompraprod.setText("");
        txtpreventprod.setText("");
        txtstockprod.setText("");
        txtidcategoriaprod.setText("");
        txtidcategoriaprod.setText("");
        txtcategoriaprod.setText("");
        txtnombreproducto.setEnabled(true);
        txtdescripcionproducto.setEnabled(true);
        txtpcompraprod.setEnabled(true);
        txtpreventprod.setEnabled(true);
        txtstockprod.setEnabled(true);
        txtidcategoriaprod.setEnabled(true);
        txtcategoriaprod.setEnabled(true);
        btnGuardarProducto.setEnabled(true);
        btnmodificarproducto.setEnabled(false);
        btneliminarproducto.setEnabled(false);
    }
    
    public void limpiarcamposcategoria(){
        txtidcat.setText("");
        txtnomcategoria.setText("");
      
        btnmodificacat.setEnabled(false);
        btneliminacat.setEnabled(false);
        btnagregacat.setEnabled(true);
    }
    
    public void limpiarcamposClientes(){
        txtidcliente.setText("");
        txtnomcliente.setText("");
        txtapecliente.setText("");
        txtcorreoclietne.setText("");
        txtdirecccliente.setText("");
        txttelcliente.setText("");
        txtnumdoccliente.setText("");
        btnagregarcliente.setEnabled(true);
        btnmodificarcliente.setEnabled(false);
        btneliminarcliente.setEnabled(false);
    }
    
    public void limpiarcamposUsuario(){
        txtidusuario.setText("");
        txtusuario.setText("");
        txtcontra.setText("");
        txtcorreousuario.setText("");
        txtnomusuario.setText("");
        txtapeusuarip.setText("");
        txtbuscausuario.setText("");
        btnmodificausuario.setEnabled(false);
        btneliminausuario.setEnabled(false);
        btnagregausuario.setEnabled(true);
    }
    
    public void mostrarDatosProd(String busca){
        while (modelproducto.getRowCount()>0) {            
            modelproducto.removeRow(0);
        }
        //empleadosDAL empd=new empleadosDAL();
        try {
            ResultSet resultado=conex.consultar("select p.*,ca.nombre as categoriaa from productos p inner join "
                    + "categoria ca on p.idcategoria=ca.idcategoria where p.nombre like '%"+busca+"%';");
            if(resultado==null){
            }else{
                while (resultado.next()) {
                Object[] ousuario={resultado.getString("idproducto"),resultado.getString("nombre"),
                    resultado.getString("descripcion"),resultado.getDouble("preciocompra"),resultado.getDouble("precioventa"),
                    resultado.getInt("stock"),resultado.getInt("idcategoria"),resultado.getString("categoriaa")};
                modelproducto.addRow(ousuario);
            }
            }
        } catch (Exception e) {
        }
    }
    
    public void mostrarDatosCate(String busca){
        while (modelcate.getRowCount()>0) {            
            modelcate.removeRow(0);
        }
        //empleadosDAL empd=new empleadosDAL();
        try {
            ResultSet resultado=conex.consultar("select * from categoria where nombre like '%"+busca+"%'");
            if(resultado==null){
            }else{
                while (resultado.next()) {
                Object[] ousuario={resultado.getString("idcategoria"),resultado.getString("nombre")};
                modelcate.addRow(ousuario);
            }
            }
        } catch (Exception e) {
        }
    }
    
    public void mostrarDatosClient(String busca){
        while (modelcliente.getRowCount()>0) {            
            modelcliente.removeRow(0);
        }
        try {
            ResultSet resultado=conex.consultar("select * from Clientes where numDoc like '%"+busca+"%'");
            if(resultado==null){
            }else{
                while (resultado.next()) {
                Object[] ocliente={resultado.getInt("idcliente"),resultado.getString("nombre"),
                    resultado.getString("apellido"),resultado.getString("correo"),resultado.getString("direccion"),
                    resultado.getString("telefono"),resultado.getString("documento"),resultado.getString("numDoc")};
                modelcliente.addRow(ocliente);
            }
            }
        } catch (Exception e) {
        }
    }
    
    public void mostrarDatosUser(String busca){
        while (modelusuario.getRowCount()>0) {            
            modelusuario.removeRow(0);
        }
        //empleadosDAL empd=new empleadosDAL();
        try {
            ResultSet resultado=conex.consultar(" select * from usuarios where usuario like '%"+busca+"%'");
            if(resultado==null){
            }else{
            while (resultado.next()) {
                Object[] ousuario={resultado.getString("idusuario"),resultado.getString("usuario"),
                    resultado.getString("password"),resultado.getString("correo"),resultado.getString("tipousuario"),
                    resultado.getString("nombre"),resultado.getString("apellido")};
                modelusuario.addRow(ousuario);
            }}
            
        } catch (Exception e) {
        }
    }
    
       public void mostrarpodMasVendidos(){
           while (modelProdMasVendido.getRowCount()>0) {            
            modelProdMasVendido.removeRow(0);
        }
        //empleadosDAL empd=new empleadosDAL();
        try {
            ResultSet resultado=conex.consultar("SELECT p.idproducto as id,p.nombre as nombre,sum(d.canti) AS TotalProd\n" +
                                "   FROM detalleventa d INNER JOIN productos p on d.idproducto=p.idproducto\n" +
                                "     GROUP BY d.idproducto\n" +
                                "   ORDER BY TotalProd DESC\n" +
                                "  LIMIT 0 , 3;");
            if(resultado!=null){
                while (resultado.next()) {
                    Object[] ousuario={resultado.getString("id"),resultado.getString("nombre"),
                    resultado.getString("TotalProd")};
                    modelProdMasVendido.addRow(ousuario);
                    BarraProducto1();
                    BarraProducto2();
                    BarraProducto3();
                }
            }
        } catch (Exception e) {
        }
       }
       
       public void mostrarClienteFrecuente(){
           while (modelclientefreuente.getRowCount()>0) {            
            modelclientefreuente.removeRow(0);
        }
        //empleadosDAL empd=new empleadosDAL();
        try {
            ResultSet resultado=conex.consultar("SELECT c.idcliente as id,c.nombre as nombre,c.apellido as apellido, count(v.idcliente) AS Totalcliente\n" +
                            "   FROM ventas v INNER JOIN clientes c on v.idcliente=c.idcliente\n" +
                            "   GROUP BY v.idcliente\n" +
                            "   ORDER BY Totalcliente DESC\n" +
                            "  LIMIT 0 , 3");
            if(resultado==null){
            }else{
                while (resultado.next()) {
                Object[] ousuario={resultado.getString("id"),resultado.getString("nombre"),
                    resultado.getString("apellido"),resultado.getString("Totalcliente")};
                modelclientefreuente.addRow(ousuario);
                cliente1();
                cliente2();
                cliente3();
            }
            }    
        } catch (Exception e) {
        }
       }
       
       
       void irRedes(String url){
           try{
               Desktop.getDesktop().browse(new URI(url));
           } catch (URISyntaxException | IOException e) {
               
           }
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frmprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frmprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frmprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frmprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frmprincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FreCant1;
    private javax.swing.JLabel FreCant2;
    private javax.swing.JLabel FreCant3;
    private javax.swing.JLabel FreCod1;
    private javax.swing.JLabel FreCod2;
    private javax.swing.JLabel FreCod3;
    private javax.swing.JLabel Frecuente1;
    private javax.swing.JLabel Frecuente2;
    private javax.swing.JLabel Frecuente3;
    private javax.swing.JLabel Frecuenteape1;
    private javax.swing.JLabel Frecuenteape2;
    private javax.swing.JLabel Frecuenteape3;
    private javax.swing.JPanel PanelProductos;
    private javax.swing.JPanel PanelUsuarios;
    private javax.swing.JPanel PanelVentas;
    private javax.swing.JTable TclieMasfrecuentes;
    private javax.swing.JTable TprodMasVendidos;
    private javax.swing.JLabel asdasd;
    private javax.swing.JLabel asdasd1;
    private javax.swing.JLabel asdasd2;
    private rojerusan.RSProgressBar barra1;
    private rojerusan.RSProgressBar barra2;
    private rojerusan.RSProgressBar barra3;
    public static RSMaterialComponent.RSButtonMaterialDos btnAcercaDe;
    private RSMaterialComponent.RSButtonMaterialDos btnGeneraVenta;
    private rojerusan.RSButtonHover btnGuardarProducto;
    private javax.swing.JButton btnTiktok;
    private rojerusan.RSButtonHover btnagregacat;
    private rojerusan.RSButtonHover btnagregarcliente;
    private RSMaterialComponent.RSButtonMaterialDos btnagregarprod;
    private rojerusan.RSButtonHover btnagregausuario;
    private rojerusan.RSButtonHover btnbuscaCatego;
    private rojerusan.RSButtonHover btnbuscacategoriaprod;
    private rojerusan.RSButtonHover btnbuscaproducto;
    private RSMaterialComponent.RSButtonMaterialDos btnbuscarcliente;
    private rojerusan.RSButtonHover btnbuscausuario;
    private RSMaterialComponent.RSButtonMaterialDos btncalcular;
    public static RSMaterialComponent.RSButtonMaterialDos btncategorias;
    private RSMaterialComponent.RSButtonMaterialDos btnclientes;
    private rojerusan.RSButtonHover btneliminacat;
    private rojerusan.RSButtonHover btneliminarcliente;
    private rojerusan.RSButtonHover btneliminarproducto;
    private rojerusan.RSButtonHover btneliminausuario;
    private RSMaterialComponent.RSButtonMaterialDos btnhome;
    private rojerusan.RSButtonHover btnmodificacat;
    private rojerusan.RSButtonHover btnmodificarcliente;
    private rojerusan.RSButtonHover btnmodificarproducto;
    private rojerusan.RSButtonHover btnmodificausuario;
    private RSMaterialComponent.RSButtonMaterialDos btnnuevaVenta;
    private rojerusan.RSButtonHover btnnuevacat;
    private rojerusan.RSButtonHover btnnuevocliente;
    private rojerusan.RSButtonHover btnnuevoproducto;
    private rojerusan.RSButtonHover btnnuevousuario;
    public static RSMaterialComponent.RSButtonMaterialDos btnproductos;
    private RSMaterialComponent.RSButtonMaterialDos btnsalir;
    private RSMaterialComponent.RSButtonMaterialDos btnsalir1;
    public static RSMaterialComponent.RSButtonMaterialDos btnusuarios;
    private RSMaterialComponent.RSButtonMaterialDos btnventas;
    private RSMaterialComponent.RSButtonMaterialDos buscaclienteventa;
    private RSMaterialComponent.RSButtonMaterialDos buscaprodventa;
    private javax.swing.JComboBox<String> cbotipcliente;
    private javax.swing.JComboBox<String> cbotipousuario;
    public static javax.swing.JTextField clienteventa;
    private javax.swing.JTextField filtraproductos;
    public static javax.swing.JLabel homeiduser;
    public static javax.swing.JLabel homenombre;
    public static javax.swing.JLabel idUser;
    public static javax.swing.JTextField idclienventa;
    public static javax.swing.JTextField idprodventa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel masVendidaB;
    private javax.swing.JLabel masVendidaC;
    private javax.swing.JLabel masvendidoA;
    public static javax.swing.JTextField nomvendedorventa;
    private javax.swing.JPanel panelAcercaDe;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JPanel panelcategoria;
    private javax.swing.JPanel panelclientes;
    private javax.swing.JTabbedPane paneles;
    private javax.swing.JPanel panelhome;
    private javax.swing.JPanel panelproductos;
    private javax.swing.JPanel panelusuarios;
    private javax.swing.JPanel panelventas;
    public static javax.swing.JTextField productventa;
    private rojerusan.RSCalendar rSCalendar2;
    private rojeru_san.RSLabelFecha rSLabelFecha1;
    private javax.swing.JLabel stockBajo;
    private javax.swing.JTable tablacategoria;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablaproductos;
    private javax.swing.JTable tablausuario;
    private javax.swing.JTable tablaventa;
    private javax.swing.JTextField txtapecliente;
    private javax.swing.JTextField txtapeusuarip;
    private javax.swing.JTextField txtbuscausuario;
    private javax.swing.JTextField txtcambio;
    private javax.swing.JTextField txtcantidadventa;
    private javax.swing.JLabel txtcantiusuarios;
    private javax.swing.JLabel txtcantventas;
    public static javax.swing.JTextField txtcategoriaprod;
    private javax.swing.JTextField txtcontra;
    private javax.swing.JTextField txtcorreoclietne;
    private javax.swing.JTextField txtcorreousuario;
    private javax.swing.JTextArea txtdescripcionproducto;
    private javax.swing.JTextField txtdirecccliente;
    private javax.swing.JTextField txtfiltracate;
    private javax.swing.JTextField txtfiltracliente;
    private javax.swing.JTextField txtidcat;
    public static javax.swing.JTextField txtidcategoriaprod;
    private javax.swing.JTextField txtidcliente;
    private javax.swing.JTextField txtidproducto;
    private javax.swing.JTextField txtidusuario;
    private javax.swing.JTextField txtnombreproducto;
    private javax.swing.JTextField txtnomcategoria;
    private javax.swing.JTextField txtnomcliente;
    private javax.swing.JTextField txtnomusuario;
    private javax.swing.JLabel txtnumClientes;
    private javax.swing.JTextField txtnumdoccliente;
    private javax.swing.JLabel txtnumpROD;
    private javax.swing.JLabel txtnumventa;
    private javax.swing.JTextField txtpagacon;
    private javax.swing.JTextField txtpcompraprod;
    public static javax.swing.JTextField txtprecioprodventa;
    private javax.swing.JTextField txtpreventprod;
    private javax.swing.JLabel txtrucventa;
    private javax.swing.JTextField txtstockprod;
    public static javax.swing.JTextField txtstockventa;
    private javax.swing.JTextField txttelcliente;
    private javax.swing.JTextField txttotalapagar;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables

    
}
