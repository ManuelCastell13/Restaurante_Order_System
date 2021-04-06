/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.Conexion;
import modelo.Platillo;

/**
 *
 * @author caste
 */
public class Ventana_NuevaOrden extends javax.swing.JFrame {

    public ArrayList<Platillo> ordenes;
    //public ArrayList<Platillo> mesa1 = new ArrayList<Platillo>();
    public int mesa;
    public int numOrden = 0;

    public void crearModeloTabla() {
        tablaOrden.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "n° Item", "Cantidad", "Platillo", "Precio"
                }
        ) {
            Class[] columnTypes = new Class[]{
                String.class, String.class, Integer.class, Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        }
        );
    }

    public void insertarOrdenNuevoPlatillo(Platillo platillo) {
        int idOrdenFinal = obtenerUltimoId();
        idOrdenFinal = idOrdenFinal + 1;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(dtf.format(now));

        PreparedStatement ps = null;
        try {
            Conexion con = new Conexion();
            Connection conexion = con.getConnection();

            //Codigo para insertar valores de menu chilaquiles
            ps = conexion.prepareStatement("INSERT INTO Orden (idOrden, numOrden, Cantidad, numMesa, platillo, complementos, extras, precio, estatus, fecha) "
                    + "values(?,?,?,?,?,?,?,?,?,?)");

            ps.setInt(1, idOrdenFinal);
            ps.setInt(2, numOrden);
            ps.setInt(3, platillo.getCantidad());
            ps.setInt(4, mesa);
            ps.setString(5, platillo.getPlatillo());
            ps.setString(6, platillo.getComplementos());
            ps.setString(7, platillo.getExtras());
            ps.setDouble(8, platillo.getPrecio());
            ps.setString(9, "Activo");
            ps.setString(10, dtf.format(now));

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void obtenerOrdenActual() {

        PreparedStatement ps = null;
        ResultSet rs = null;

        DefaultTableModel modeloTabla = new DefaultTableModel();
        tablaOrden.setModel(modeloTabla);
        try {
            Conexion con = new Conexion();
            Connection conexion = con.getConnection();

            //Codigo para obtener los datos de la tabla
            ps = conexion.prepareStatement("SELECT idOrden, Cantidad, platillo, precio FROM Orden where numOrden=?");
            ps.setInt(1, numOrden); //Obtenemos la informacion de la orden actual
            rs = ps.executeQuery();

            modeloTabla.addColumn("n° Item");
            modeloTabla.addColumn("Cantidad");
            modeloTabla.addColumn("Platillo");
            modeloTabla.addColumn("Precio");

            //Obtener toda la data de MySQL
            ResultSetMetaData rsMD = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMD.getColumnCount();

            int anchos[] = {10, 50, 140, 50};

            for (int i = 0; i < cantidadColumnas; i++) {
                tablaOrden.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }

            while (rs.next()) {
                //Agregando cada columna de la base de datos a objeto Fila
                Object fila[] = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
        }

    }

    public double obtenerPrecioPlatillosBD(String nombre) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        double precio = 0;

        Conexion con = new Conexion();
        Connection conexion = con.getConnection();

        try {
            ps = conexion.prepareStatement("SELECT precio FROM Platillos where nombre=?");
            ps.setString(1, nombre); //Obtenemos la informacion de los chilaquiles
            rs = ps.executeQuery();

            if (rs.next()) {
                precio = Double.parseDouble(rs.getString("precio"));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return precio;
    }

    public int obtenerUltimoId() {
        int idOrdenFinal = 0;

        PreparedStatement ps = null;
        ResultSet rs = null;

        Conexion con = new Conexion();
        Connection conexion = con.getConnection();

        try {

            //Codigo para obtener los datos de la tabla
            ps = conexion.prepareStatement("SELECT idOrden FROM  Orden where numOrden=?");
            ps.setInt(1, numOrden); //Obtenemos la informacion de la orden actual
            rs = ps.executeQuery();

            //Obtener toda la data de MySQL
            while (rs.next()) {
                idOrdenFinal = rs.getInt("idOrden");//Codigo para obtener el ultimo IDORDEN (Cuenta todas las columnas)
            }

        } catch (Exception e) {
            System.err.println(e);
        }

        return idOrdenFinal;
    }

    public void uncheckBox() {
        checkBoxAAdobada.setSelected(false);
        checkBoxACamarones.setSelected(false);
        checkBoxACebolla.setSelected(false);
        checkBoxAChampiñon.setSelected(false);
        checkBoxAChileGuero.setSelected(false);
        checkBoxAChileSerrano.setSelected(false);
        checkBoxAElote.setSelected(false);
        checkBoxAJitomate.setSelected(false);
        checkBoxANopales.setSelected(false);
        checkBoxANopales.setSelected(false);
        checkBoxAPimientos.setSelected(false);
        checkBoxAPiña.setSelected(false);
        checkBoxAPoblano.setSelected(false);
        checkBoxAPollo.setSelected(false);
        checkBoxAPuerco.setSelected(false);
        checkBoxARes.setSelected(false);
        checkBoxATofu.setSelected(false);
        checkBoxEAdobada.setSelected(false);
        rBotonEBalsamico.setSelected(false);
        checkBoxECamarones.setSelected(false);
        checkBoxECebolla.setSelected(false);
        checkBoxEChampiñon.setSelected(false);
        checkBoxEChileGuero.setSelected(false);
        checkBoxECrotones.setSelected(false);
        checkBoxEElote.setSelected(false);
        checkBoxEJitomate.setSelected(false);
        checkBoxEMixSemillas.setSelected(false);
        checkBoxENopales.setSelected(false);
        checkBoxEPimientos.setSelected(false);
        checkBoxEPiña.setSelected(false);
        checkBoxEPoblano.setSelected(false);
        checkBoxEPollo.setSelected(false);
        checkBoxEPuerco.setSelected(false);
        checkBoxERes.setSelected(false);
        checkBoxETofu.setSelected(false);
        checkBoxETortilla.setSelected(false);
        checkBoxEZanahoria.setSelected(false);
        checkBoxHAguacate.setSelected(false);
        checkBoxHAsadero.setSelected(false);
        checkBoxHCebolla.setSelected(false);
        checkBoxHChampiñones.setSelected(false);
        checkBoxHChileGuero.setSelected(false);
        checkBoxHJitomate.setSelected(false);
        checkBoxHLechuga.setSelected(false);
        checkBoxHManchego.setSelected(false);
        checkBoxHPiña.setSelected(false);
        checkBoxHTocino.setSelected(false);
    }

    /**
     * Creates new form Ventana_NuevaOrden
     */
    public Ventana_NuevaOrden() {
        initComponents();
        setLocationRelativeTo(null);

        crearModeloTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MesaDialog = new javax.swing.JDialog();
        etiquetaNumMesa = new javax.swing.JLabel();
        etiquetaNumMesa1 = new javax.swing.JLabel();
        cajaNumMesa = new javax.swing.JTextField();
        cajaCantPersonas = new javax.swing.JTextField();
        botonAceptar = new javax.swing.JButton();
        subMenu = new javax.swing.JDialog();
        panelMaster = new javax.swing.JPanel();
        panelEnsaladas = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        checkBoxERes = new javax.swing.JCheckBox();
        checkBoxEPuerco = new javax.swing.JCheckBox();
        checkBoxEPollo = new javax.swing.JCheckBox();
        checkBoxEAdobada = new javax.swing.JCheckBox();
        checkBoxECamarones = new javax.swing.JCheckBox();
        checkBoxETofu = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        checkBoxEPimientos = new javax.swing.JCheckBox();
        checkBoxEElote = new javax.swing.JCheckBox();
        checkBoxECebolla = new javax.swing.JCheckBox();
        checkBoxEPiña = new javax.swing.JCheckBox();
        checkBoxENopales = new javax.swing.JCheckBox();
        checkBoxEChampiñon = new javax.swing.JCheckBox();
        checkBoxEChileGuero = new javax.swing.JCheckBox();
        checkBoxEZanahoria = new javax.swing.JCheckBox();
        checkBoxEJitomate = new javax.swing.JCheckBox();
        checkBoxEPoblano = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        checkBoxEMixSemillas = new javax.swing.JCheckBox();
        checkBoxECrotones = new javax.swing.JCheckBox();
        checkBoxETortilla = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        rBotonEChipotle = new javax.swing.JRadioButton();
        rBotonEMostazaMiel = new javax.swing.JRadioButton();
        rBotonEBalsamico = new javax.swing.JRadioButton();
        botonAceptarE = new javax.swing.JButton();
        botonEBack = new javax.swing.JButton();
        panelHamburguesas = new javax.swing.JPanel();
        botonAceptarH = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        checkBoxHLechuga = new javax.swing.JCheckBox();
        checkBoxHCebolla = new javax.swing.JCheckBox();
        checkBoxHPiña = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        checkBoxHChampiñones = new javax.swing.JCheckBox();
        checkBoxHChileGuero = new javax.swing.JCheckBox();
        checkBoxHJitomate = new javax.swing.JCheckBox();
        rBotonHRes = new javax.swing.JRadioButton();
        rBotonHDRes = new javax.swing.JRadioButton();
        rBotonHVegetariana = new javax.swing.JRadioButton();
        checkBoxHTocino = new javax.swing.JCheckBox();
        checkBoxHAsadero = new javax.swing.JCheckBox();
        checkBoxHAguacate = new javax.swing.JCheckBox();
        checkBoxHManchego = new javax.swing.JCheckBox();
        botonHBack = new javax.swing.JButton();
        panelChilaquiles = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        rBotonChSencillos = new javax.swing.JRadioButton();
        rBotonChRes = new javax.swing.JRadioButton();
        rBotonChPuerco = new javax.swing.JRadioButton();
        rBotonChPollo = new javax.swing.JRadioButton();
        rBotonChAdobada = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        rBotonSalsaVerde = new javax.swing.JRadioButton();
        rBotonSalsaRoja = new javax.swing.JRadioButton();
        botonAceptarCh = new javax.swing.JButton();
        rBotonChTofu = new javax.swing.JRadioButton();
        botonChBack = new javax.swing.JButton();
        panelAlambres = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        checkBoxAPimientos = new javax.swing.JCheckBox();
        checkBoxAElote = new javax.swing.JCheckBox();
        checkBoxACebolla = new javax.swing.JCheckBox();
        checkBoxAPiña = new javax.swing.JCheckBox();
        checkBoxANopales = new javax.swing.JCheckBox();
        checkBoxAChampiñon = new javax.swing.JCheckBox();
        checkBoxAChileGuero = new javax.swing.JCheckBox();
        checkBoxAChileSerrano = new javax.swing.JCheckBox();
        checkBoxAJitomate = new javax.swing.JCheckBox();
        checkBoxAPoblano = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        checkBoxARes = new javax.swing.JCheckBox();
        checkBoxAPuerco = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        checkBoxAPollo = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        checkBoxAAdobada = new javax.swing.JCheckBox();
        checkBoxACamarones = new javax.swing.JCheckBox();
        rBotonAHarina = new javax.swing.JRadioButton();
        checkBoxATofu = new javax.swing.JCheckBox();
        rBotonAMaiz = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        rBotonAQueso = new javax.swing.JRadioButton();
        rBotonASQueso = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        rBotonAChico = new javax.swing.JRadioButton();
        rBotonAMediano = new javax.swing.JRadioButton();
        rBotonAGrande = new javax.swing.JRadioButton();
        botonAceptarA = new javax.swing.JButton();
        botonABack = new javax.swing.JButton();
        rBotonASalchichon = new javax.swing.JRadioButton();
        rBotonATocino = new javax.swing.JRadioButton();
        rBotonAChorizo = new javax.swing.JRadioButton();
        checkBoxAJitomate1 = new javax.swing.JCheckBox();
        checkBoxAPoblano1 = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        checkBoxAPimientos1 = new javax.swing.JCheckBox();
        checkBoxAElote1 = new javax.swing.JCheckBox();
        checkBoxACebolla1 = new javax.swing.JCheckBox();
        checkBoxAPiña1 = new javax.swing.JCheckBox();
        checkBoxANopales1 = new javax.swing.JCheckBox();
        checkBoxAChampiñon1 = new javax.swing.JCheckBox();
        checkBoxAChileGuero1 = new javax.swing.JCheckBox();
        checkBoxAChileSerrano1 = new javax.swing.JCheckBox();
        checkBoxATocino = new javax.swing.JCheckBox();
        checkBoxAChorizo = new javax.swing.JCheckBox();
        checkBoxASalchichon = new javax.swing.JCheckBox();
        panelChilaquiles1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        rBotonChSencillos1 = new javax.swing.JRadioButton();
        rBotonChRes1 = new javax.swing.JRadioButton();
        rBotonChPuerco1 = new javax.swing.JRadioButton();
        rBotonChPollo1 = new javax.swing.JRadioButton();
        rBotonChAdobada1 = new javax.swing.JRadioButton();
        jLabel23 = new javax.swing.JLabel();
        rBotonSalsaVerde1 = new javax.swing.JRadioButton();
        rBotonSalsaRoja1 = new javax.swing.JRadioButton();
        botonAceptarCh1 = new javax.swing.JButton();
        rBotonChTofu1 = new javax.swing.JRadioButton();
        botonChBack1 = new javax.swing.JButton();
        grupoBotonProteinaCh = new javax.swing.ButtonGroup();
        grupoBotonSalsasCh = new javax.swing.ButtonGroup();
        grupoBotonTamañoA = new javax.swing.ButtonGroup();
        grupoBotonTortillasA = new javax.swing.ButtonGroup();
        grupoBotonQuesoA = new javax.swing.ButtonGroup();
        grupoBotonProteinaH = new javax.swing.ButtonGroup();
        grupoBotonEAderezo = new javax.swing.ButtonGroup();
        grupoBotonACarneFria = new javax.swing.ButtonGroup();
        menuTacos = new javax.swing.JDialog();
        panelTacos = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        checkBoxTacoRes = new javax.swing.JCheckBox();
        checkBoxTacoPuerco = new javax.swing.JCheckBox();
        checkBoxTacoPollo = new javax.swing.JCheckBox();
        checkBoxTacoAdobada = new javax.swing.JCheckBox();
        checkBoxTacoCamarones = new javax.swing.JCheckBox();
        checkBoxTacoChorizo = new javax.swing.JCheckBox();
        checkBoxTacoTofu = new javax.swing.JCheckBox();
        cajaTextoTacoRes = new javax.swing.JTextField();
        cajaTextoTacoPuerco = new javax.swing.JTextField();
        cajaTextoTacoAdobada = new javax.swing.JTextField();
        cajaTextoTacoPollo = new javax.swing.JTextField();
        cajaTextoTacoTofu = new javax.swing.JTextField();
        cajaTextoTacoChorizo = new javax.swing.JTextField();
        cajaTextoTacoCamarones = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        botonTacosOk = new javax.swing.JButton();
        menuQuesadillas = new javax.swing.JDialog();
        panelQuesadillas = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        checkBoxQuesadillasRes = new javax.swing.JCheckBox();
        checkBoxQuesadillasPuerco = new javax.swing.JCheckBox();
        checkBoxQuesadillasPollo = new javax.swing.JCheckBox();
        checkBoxQuesadillasAdobada = new javax.swing.JCheckBox();
        checkBoxQuesadillasCamarones = new javax.swing.JCheckBox();
        checkBoxQuesadillasChorizo = new javax.swing.JCheckBox();
        checkBoxQuesadillasTofu = new javax.swing.JCheckBox();
        cajaTextoQuesadillaRes = new javax.swing.JTextField();
        cajaTextoQuesadillaPuerco = new javax.swing.JTextField();
        cajaTextoQuesadillaAdobada = new javax.swing.JTextField();
        cajaTextoQuesadillaPollo = new javax.swing.JTextField();
        cajaTextoQuesadillaTofu = new javax.swing.JTextField();
        cajaTextoQuesadillaChorizo = new javax.swing.JTextField();
        cajaTextoQuesadillaCamarones = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        botonQuesadillasOk = new javax.swing.JButton();
        menuCarnesAsadas = new javax.swing.JDialog();
        panelCarnes = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        checkBoxArrachera = new javax.swing.JCheckBox();
        checkBoxMuslo = new javax.swing.JCheckBox();
        checkBoxPechuga = new javax.swing.JCheckBox();
        checkBoxBistek = new javax.swing.JCheckBox();
        cajaTextoArrachera = new javax.swing.JTextField();
        cajaTextoMuslo = new javax.swing.JTextField();
        cajaTextoBistek = new javax.swing.JTextField();
        cajaTextoPechuga = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        botonCarnesOk = new javax.swing.JButton();
        menuAguas = new javax.swing.JDialog();
        panelAguas = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        checkBoxHorchata = new javax.swing.JCheckBox();
        checkBoxLimon = new javax.swing.JCheckBox();
        checkBoxNatural = new javax.swing.JCheckBox();
        checkBoxMineral = new javax.swing.JCheckBox();
        cajaTextoHorchata = new javax.swing.JTextField();
        cajaTextoLimon = new javax.swing.JTextField();
        cajaTextoMineral = new javax.swing.JTextField();
        cajaTextoNatural = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        botonAguasOk = new javax.swing.JButton();
        menuRefrescos = new javax.swing.JDialog();
        panelRefrescos = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        checkBoxCoca = new javax.swing.JCheckBox();
        checkBoxCocaLight = new javax.swing.JCheckBox();
        checkBoxFresca = new javax.swing.JCheckBox();
        checkBoxFantaN = new javax.swing.JCheckBox();
        cajaTextoCoca = new javax.swing.JTextField();
        cajaTextoCocaLight = new javax.swing.JTextField();
        cajaTextoFantaN = new javax.swing.JTextField();
        cajaTextoFresca = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        botonRefrescoOk = new javax.swing.JButton();
        checkBoxFantaR = new javax.swing.JCheckBox();
        checkBoxManzanita = new javax.swing.JCheckBox();
        checkBoxSprite = new javax.swing.JCheckBox();
        cajaTextoFantaR = new javax.swing.JTextField();
        cajaTextoManzanita = new javax.swing.JTextField();
        cajaTextoSprite = new javax.swing.JTextField();
        menuCerveza = new javax.swing.JDialog();
        panelCerveza = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        checkBoxVictoria = new javax.swing.JCheckBox();
        checkBoxCorona = new javax.swing.JCheckBox();
        checkBoxModeloNegra = new javax.swing.JCheckBox();
        checkBoxModeloEspecial = new javax.swing.JCheckBox();
        cajaTextoVictoria = new javax.swing.JTextField();
        cajaTextoCorona = new javax.swing.JTextField();
        cajaTextoModeloEspecial = new javax.swing.JTextField();
        cajaTextoModeloNegra = new javax.swing.JTextField();
        botonCervezaOk = new javax.swing.JButton();
        checkBoxModeloAmbar = new javax.swing.JCheckBox();
        checkBoxCucapa = new javax.swing.JCheckBox();
        checkBoxUltra = new javax.swing.JCheckBox();
        cajaTextoModeloAmbar = new javax.swing.JTextField();
        cajaTextoCucapa = new javax.swing.JTextField();
        cajaTextoUltra = new javax.swing.JTextField();
        cajaTextoVasoChelado = new javax.swing.JTextField();
        cajaTextoVasoMichelado = new javax.swing.JTextField();
        cajaTextoVasoGringa = new javax.swing.JTextField();
        checkBoxVasoChelado = new javax.swing.JCheckBox();
        checkBoxVasoMichelado = new javax.swing.JCheckBox();
        checkBoxVasoGringa = new javax.swing.JCheckBox();
        cajaTextoCubeta = new javax.swing.JTextField();
        checkBoxCubeta = new javax.swing.JCheckBox();
        menuBebidasAd = new javax.swing.JDialog();
        panelBebidasAd = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        checkBoxCafeAmericano = new javax.swing.JCheckBox();
        checkBoxCafeAmericanoRef = new javax.swing.JCheckBox();
        checkBoxTe = new javax.swing.JCheckBox();
        checkBoxTissanaFria = new javax.swing.JCheckBox();
        cajaTextoCafeAmericano = new javax.swing.JTextField();
        cajaTextoCafeAmericanoRef = new javax.swing.JTextField();
        cajaTextoTissanaFria = new javax.swing.JTextField();
        cajaTextoTe = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        botonBebidasAdOk = new javax.swing.JButton();
        checkBoxTissannaCaliente = new javax.swing.JCheckBox();
        cajaTextoTissanaCaliente = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        botonNuevaMesa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaOrden = new javax.swing.JTable();
        botonChilaquiles = new javax.swing.JButton();
        botonAlambres = new javax.swing.JButton();
        botonHamburguesas = new javax.swing.JButton();
        botonEnsaladas = new javax.swing.JButton();
        botonTacos = new javax.swing.JButton();
        botonQuesadillas = new javax.swing.JButton();
        botonCarnesAsadas = new javax.swing.JButton();
        botonAguaHorchata = new javax.swing.JButton();
        botonRefrescos = new javax.swing.JButton();
        botonCerveza = new javax.swing.JButton();
        botonTisana = new javax.swing.JButton();
        botonRegresar = new javax.swing.JButton();

        MesaDialog.setLocation(new java.awt.Point(500, 200));
        MesaDialog.setMinimumSize(new java.awt.Dimension(300, 300));

        etiquetaNumMesa.setText("Numero de Mesa");

        etiquetaNumMesa1.setText("Cantidad de Personas");

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MesaDialogLayout = new javax.swing.GroupLayout(MesaDialog.getContentPane());
        MesaDialog.getContentPane().setLayout(MesaDialogLayout);
        MesaDialogLayout.setHorizontalGroup(
            MesaDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MesaDialogLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(MesaDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonAceptar)
                    .addComponent(cajaCantPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetaNumMesa1)
                    .addGroup(MesaDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cajaNumMesa, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(etiquetaNumMesa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(176, Short.MAX_VALUE))
        );
        MesaDialogLayout.setVerticalGroup(
            MesaDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MesaDialogLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(etiquetaNumMesa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cajaNumMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(etiquetaNumMesa1)
                .addGap(18, 18, 18)
                .addComponent(cajaCantPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(botonAceptar)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        subMenu.setBackground(new java.awt.Color(255, 255, 102));
        subMenu.setMinimumSize(new java.awt.Dimension(650, 650));

        panelEnsaladas.setBackground(new java.awt.Color(153, 255, 255));

        jLabel4.setText("ENSALADAS");

        jLabel5.setText("TIPO PROTEINA");

        checkBoxERes.setText("Res");

        checkBoxEPuerco.setText("Puerco");

        checkBoxEPollo.setText("Pollo");

        checkBoxEAdobada.setText("Adobada");

        checkBoxECamarones.setText("Camarones");

        checkBoxETofu.setText("Tofu");

        jLabel6.setText("4 VERDURAS");

        checkBoxEPimientos.setText("Pimientos");

        checkBoxEElote.setText("Elote");

        checkBoxECebolla.setText("Cebolla");

        checkBoxEPiña.setText("Piña");

        checkBoxENopales.setText("Nopalitos");

        checkBoxEChampiñon.setText("Champiñones");

        checkBoxEChileGuero.setText("Chile Guero");

        checkBoxEZanahoria.setText("Zanahoria");

        checkBoxEJitomate.setText("Jitomate");

        checkBoxEPoblano.setText("Poblano");

        jLabel7.setText("SEMILLAS/ CEREALES");

        checkBoxEMixSemillas.setText("Mix Semillas");

        checkBoxECrotones.setText("Crotones");

        checkBoxETortilla.setText("Tiras Tortilla");

        jLabel8.setText("ADEREZO");

        grupoBotonEAderezo.add(rBotonEChipotle);
        rBotonEChipotle.setText("Chipotle");

        grupoBotonEAderezo.add(rBotonEMostazaMiel);
        rBotonEMostazaMiel.setText("Mostaza Miel");

        grupoBotonEAderezo.add(rBotonEBalsamico);
        rBotonEBalsamico.setText("Balsamico");

        botonAceptarE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_100x100.png"))); // NOI18N
        botonAceptarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarEActionPerformed(evt);
            }
        });

        botonEBack.setText("Back");
        botonEBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEnsaladasLayout = new javax.swing.GroupLayout(panelEnsaladas);
        panelEnsaladas.setLayout(panelEnsaladasLayout);
        panelEnsaladasLayout.setHorizontalGroup(
            panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEnsaladasLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEnsaladasLayout.createSequentialGroup()
                        .addComponent(botonEBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonAceptarE, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(panelEnsaladasLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelEnsaladasLayout.createSequentialGroup()
                        .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(checkBoxERes)
                            .addComponent(checkBoxEPuerco)
                            .addComponent(checkBoxEPollo)
                            .addComponent(checkBoxEAdobada)
                            .addComponent(checkBoxECamarones)
                            .addComponent(checkBoxETofu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                        .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(panelEnsaladasLayout.createSequentialGroup()
                                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxEPimientos)
                                    .addComponent(checkBoxEElote)
                                    .addComponent(checkBoxECebolla)
                                    .addComponent(checkBoxEPiña)
                                    .addComponent(checkBoxENopales))
                                .addGap(30, 30, 30)
                                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxEPoblano)
                                    .addComponent(checkBoxEJitomate)
                                    .addComponent(checkBoxEZanahoria)
                                    .addComponent(checkBoxEChileGuero)
                                    .addComponent(checkBoxEChampiñon))))
                        .addGap(73, 73, 73))
                    .addGroup(panelEnsaladasLayout.createSequentialGroup()
                        .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkBoxETortilla)
                            .addComponent(checkBoxECrotones)
                            .addComponent(checkBoxEMixSemillas)
                            .addComponent(jLabel7))
                        .addGap(82, 82, 82)
                        .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(rBotonEChipotle)
                            .addComponent(rBotonEMostazaMiel)
                            .addComponent(rBotonEBalsamico))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelEnsaladasLayout.setVerticalGroup(
            panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEnsaladasLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxERes)
                    .addComponent(checkBoxEPimientos)
                    .addComponent(checkBoxEChampiñon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxEPuerco)
                    .addComponent(checkBoxEElote)
                    .addComponent(checkBoxEChileGuero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxEPollo)
                    .addComponent(checkBoxECebolla)
                    .addComponent(checkBoxEZanahoria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxEAdobada)
                    .addComponent(checkBoxEPiña)
                    .addComponent(checkBoxEJitomate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxECamarones)
                    .addComponent(checkBoxENopales)
                    .addComponent(checkBoxEPoblano))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkBoxETofu)
                .addGap(35, 35, 35)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxEMixSemillas)
                    .addComponent(rBotonEChipotle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxECrotones)
                    .addComponent(rBotonEMostazaMiel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxETortilla)
                    .addComponent(rBotonEBalsamico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEnsaladasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonAceptarE, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEBack))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        panelHamburguesas.setBackground(new java.awt.Color(255, 51, 51));

        botonAceptarH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_100x100.png"))); // NOI18N
        botonAceptarH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarHActionPerformed(evt);
            }
        });

        jLabel17.setText("HAMBURGUESAS");

        jLabel18.setText("TIPO PROTEINA");

        jLabel20.setText("4 VERDURAS");

        checkBoxHLechuga.setText("Lechuga");
        checkBoxHLechuga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxHLechugaActionPerformed(evt);
            }
        });

        checkBoxHCebolla.setText("Cebolla");

        checkBoxHPiña.setText("Piña");

        jLabel22.setText("INGREDIENTES EXTRA");

        checkBoxHChampiñones.setText("Champiñones");

        checkBoxHChileGuero.setText("Chile Guero");

        checkBoxHJitomate.setText("Jitomate");

        grupoBotonProteinaH.add(rBotonHRes);
        rBotonHRes.setText("Res");
        rBotonHRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rBotonHResActionPerformed(evt);
            }
        });

        grupoBotonProteinaH.add(rBotonHDRes);
        rBotonHDRes.setText("Doble Res");

        grupoBotonProteinaH.add(rBotonHVegetariana);
        rBotonHVegetariana.setText("Vegetariana");

        checkBoxHTocino.setText("Tocino");

        checkBoxHAsadero.setText("Queso Asadero");

        checkBoxHAguacate.setText("Aguacate");

        checkBoxHManchego.setText("Queso Manchego");

        botonHBack.setText("Back");
        botonHBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonHBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelHamburguesasLayout = new javax.swing.GroupLayout(panelHamburguesas);
        panelHamburguesas.setLayout(panelHamburguesasLayout);
        panelHamburguesasLayout.setHorizontalGroup(
            panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHamburguesasLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHamburguesasLayout.createSequentialGroup()
                        .addComponent(botonHBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonAceptarH, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))
                    .addGroup(panelHamburguesasLayout.createSequentialGroup()
                        .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(rBotonHRes)
                            .addComponent(rBotonHDRes)
                            .addComponent(rBotonHVegetariana))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addGroup(panelHamburguesasLayout.createSequentialGroup()
                                .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxHLechuga)
                                    .addComponent(checkBoxHCebolla)
                                    .addComponent(checkBoxHPiña))
                                .addGap(30, 30, 30)
                                .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxHJitomate)
                                    .addComponent(checkBoxHChileGuero)
                                    .addComponent(checkBoxHChampiñones))))
                        .addGap(73, 73, 73))
                    .addGroup(panelHamburguesasLayout.createSequentialGroup()
                        .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkBoxHManchego)
                            .addComponent(checkBoxHAguacate)
                            .addComponent(checkBoxHAsadero)
                            .addComponent(checkBoxHTocino))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelHamburguesasLayout.createSequentialGroup()
                        .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 152, Short.MAX_VALUE))))
        );
        panelHamburguesasLayout.setVerticalGroup(
            panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHamburguesasLayout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxHLechuga)
                    .addComponent(checkBoxHChampiñones)
                    .addComponent(rBotonHRes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxHCebolla)
                    .addComponent(checkBoxHChileGuero)
                    .addComponent(rBotonHDRes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxHPiña)
                    .addComponent(checkBoxHJitomate)
                    .addComponent(rBotonHVegetariana))
                .addGap(41, 41, 41)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkBoxHTocino)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkBoxHManchego)
                .addGap(8, 8, 8)
                .addComponent(checkBoxHAsadero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkBoxHAguacate)
                .addGroup(panelHamburguesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHamburguesasLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(botonAceptarH, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(41, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHamburguesasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonHBack)
                        .addGap(51, 51, 51))))
        );

        panelChilaquiles.setBackground(new java.awt.Color(255, 255, 102));
        panelChilaquiles.setMinimumSize(new java.awt.Dimension(620, 601));

        jLabel1.setText("TIPO DE PROTEINA");

        jLabel2.setText("CHILAQUILES");

        grupoBotonProteinaCh.add(rBotonChSencillos);
        rBotonChSencillos.setText("SENCILLOS");

        grupoBotonProteinaCh.add(rBotonChRes);
        rBotonChRes.setText("RES");

        grupoBotonProteinaCh.add(rBotonChPuerco);
        rBotonChPuerco.setText("PUERCO");

        grupoBotonProteinaCh.add(rBotonChPollo);
        rBotonChPollo.setText("POLLO");

        grupoBotonProteinaCh.add(rBotonChAdobada);
        rBotonChAdobada.setText("ADOBADA");

        jLabel3.setText("SALSAS");

        grupoBotonSalsasCh.add(rBotonSalsaVerde);
        rBotonSalsaVerde.setText("VERDE");

        grupoBotonSalsasCh.add(rBotonSalsaRoja);
        rBotonSalsaRoja.setText("ROJA");

        botonAceptarCh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_100x100.png"))); // NOI18N
        botonAceptarCh.setBorder(null);
        botonAceptarCh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarChActionPerformed(evt);
            }
        });

        grupoBotonProteinaCh.add(rBotonChTofu);
        rBotonChTofu.setText("TOFU");

        botonChBack.setText("Back");
        botonChBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonChBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelChilaquilesLayout = new javax.swing.GroupLayout(panelChilaquiles);
        panelChilaquiles.setLayout(panelChilaquilesLayout);
        panelChilaquilesLayout.setHorizontalGroup(
            panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChilaquilesLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChilaquilesLayout.createSequentialGroup()
                        .addComponent(botonChBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonAceptarCh, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelChilaquilesLayout.createSequentialGroup()
                        .addGroup(panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelChilaquilesLayout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelChilaquilesLayout.createSequentialGroup()
                                .addGroup(panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(rBotonChPollo)
                                        .addComponent(rBotonChTofu)
                                        .addComponent(rBotonChRes)
                                        .addComponent(rBotonChAdobada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rBotonChPuerco, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rBotonChSencillos, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(117, 117, 117)
                                .addGroup(panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rBotonSalsaVerde)
                                    .addComponent(jLabel3)
                                    .addComponent(rBotonSalsaRoja))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelChilaquilesLayout.setVerticalGroup(
            panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChilaquilesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rBotonChAdobada)
                    .addComponent(rBotonSalsaVerde))
                .addGap(8, 8, 8)
                .addGroup(panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rBotonChRes)
                    .addComponent(rBotonSalsaRoja))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rBotonChPuerco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rBotonChPollo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rBotonChTofu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rBotonChSencillos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                .addGroup(panelChilaquilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChilaquilesLayout.createSequentialGroup()
                        .addComponent(botonAceptarCh, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChilaquilesLayout.createSequentialGroup()
                        .addComponent(botonChBack)
                        .addGap(37, 37, 37))))
        );

        panelAlambres.setBackground(new java.awt.Color(51, 255, 51));

        jLabel9.setText("4 VERDURAS");

        checkBoxAPimientos.setText("Pimientos");

        checkBoxAElote.setText("Elote");

        checkBoxACebolla.setText("Cebolla");

        checkBoxAPiña.setText("Piña");

        checkBoxANopales.setText("Nopalitos");

        checkBoxAChampiñon.setText("Champiñones");

        checkBoxAChileGuero.setText("Chile Guero");

        checkBoxAChileSerrano.setText("Chile Serrano");

        checkBoxAJitomate.setText("Jitomate");

        checkBoxAPoblano.setText("Poblano");

        jLabel10.setText("CARNE FRIA");

        jLabel11.setText("ALAMBRES");

        jLabel12.setText("TIPO PROTEINA");

        checkBoxARes.setText("Res");

        checkBoxAPuerco.setText("Puerco");
        checkBoxAPuerco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAPuercoActionPerformed(evt);
            }
        });

        checkBoxAPollo.setText("Pollo");

        jLabel13.setText("TORTILLAS");

        checkBoxAAdobada.setText("Adobada");

        checkBoxACamarones.setText("Camarones");

        grupoBotonTortillasA.add(rBotonAHarina);
        rBotonAHarina.setText("Harina");

        checkBoxATofu.setText("Tofu");

        grupoBotonTortillasA.add(rBotonAMaiz);
        rBotonAMaiz.setText("Maiz");

        jLabel14.setText("QUESO?");

        grupoBotonQuesoA.add(rBotonAQueso);
        rBotonAQueso.setText("Con Queso");

        grupoBotonQuesoA.add(rBotonASQueso);
        rBotonASQueso.setText("Sin Queso");

        jLabel15.setText("TAMAÑO");

        grupoBotonTamañoA.add(rBotonAChico);
        rBotonAChico.setText("Chico");

        grupoBotonTamañoA.add(rBotonAMediano);
        rBotonAMediano.setText("Mediano");

        grupoBotonTamañoA.add(rBotonAGrande);
        rBotonAGrande.setText("Grande");

        botonAceptarA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_100x100.png"))); // NOI18N
        botonAceptarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarAActionPerformed(evt);
            }
        });

        botonABack.setText("Back");
        botonABack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonABackActionPerformed(evt);
            }
        });

        grupoBotonACarneFria.add(rBotonASalchichon);
        rBotonASalchichon.setText("Salchichon");

        grupoBotonACarneFria.add(rBotonATocino);
        rBotonATocino.setText("Tocino");

        grupoBotonACarneFria.add(rBotonAChorizo);
        rBotonAChorizo.setText("Chorizo");

        checkBoxAJitomate1.setText("Jitomate");

        checkBoxAPoblano1.setText("Poblano");

        jLabel16.setText("EXTRAS");

        checkBoxAPimientos1.setText("Pimientos");

        checkBoxAElote1.setText("Elote");

        checkBoxACebolla1.setText("Cebolla");

        checkBoxAPiña1.setText("Piña");

        checkBoxANopales1.setText("Nopalitos");

        checkBoxAChampiñon1.setText("Champiñones");

        checkBoxAChileGuero1.setText("Chile Guero");

        checkBoxAChileSerrano1.setText("Chile Serrano");

        checkBoxATocino.setText("Tocino");

        checkBoxAChorizo.setText("Chorizo");

        checkBoxASalchichon.setText("Salchichon");

        javax.swing.GroupLayout panelAlambresLayout = new javax.swing.GroupLayout(panelAlambres);
        panelAlambres.setLayout(panelAlambresLayout);
        panelAlambresLayout.setHorizontalGroup(
            panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAlambresLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAlambresLayout.createSequentialGroup()
                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(checkBoxARes)
                            .addComponent(checkBoxAPuerco)
                            .addComponent(checkBoxAPollo)
                            .addComponent(checkBoxAAdobada)
                            .addComponent(checkBoxACamarones)
                            .addComponent(checkBoxATofu)
                            .addComponent(rBotonAMediano)
                            .addComponent(rBotonAChico)
                            .addComponent(jLabel15)
                            .addComponent(rBotonAGrande))
                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAlambresLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelAlambresLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                        .addComponent(jLabel9)
                                        .addGap(238, 238, 238))
                                    .addGroup(panelAlambresLayout.createSequentialGroup()
                                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(checkBoxAPimientos)
                                            .addComponent(checkBoxAElote)
                                            .addComponent(checkBoxACebolla)
                                            .addComponent(checkBoxAPiña)
                                            .addComponent(checkBoxANopales))
                                        .addGap(36, 36, 36)
                                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(checkBoxAJitomate)
                                            .addComponent(checkBoxAChileSerrano)
                                            .addComponent(checkBoxAChileGuero)
                                            .addComponent(checkBoxAChampiñon)
                                            .addComponent(checkBoxAPoblano))
                                        .addGap(73, 73, 73))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAlambresLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelAlambresLayout.createSequentialGroup()
                                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rBotonATocino)
                                            .addComponent(rBotonASalchichon)
                                            .addComponent(rBotonAChorizo))
                                        .addGap(30, 30, 30)
                                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(rBotonAHarina)
                                            .addComponent(rBotonAMaiz)))
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(rBotonASQueso)
                                    .addComponent(rBotonAQueso))
                                .addGap(89, 89, 89))))
                    .addGroup(panelAlambresLayout.createSequentialGroup()
                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(panelAlambresLayout.createSequentialGroup()
                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAlambresLayout.createSequentialGroup()
                                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxAPimientos1)
                                    .addComponent(checkBoxAElote1)
                                    .addComponent(checkBoxACebolla1)
                                    .addComponent(checkBoxAPiña1)
                                    .addComponent(checkBoxANopales1))
                                .addGap(30, 30, 30)
                                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxAJitomate1)
                                    .addComponent(checkBoxAPoblano1)
                                    .addGroup(panelAlambresLayout.createSequentialGroup()
                                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(checkBoxAChileSerrano1)
                                            .addComponent(checkBoxAChileGuero1)
                                            .addComponent(checkBoxAChampiñon1))
                                        .addGap(18, 18, 18)
                                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(checkBoxATocino)
                                            .addComponent(checkBoxASalchichon)
                                            .addComponent(checkBoxAChorizo))
                                        .addGap(44, 44, 44)
                                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(botonAceptarA, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(botonABack)))))
                            .addComponent(jLabel16))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelAlambresLayout.setVerticalGroup(
            panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAlambresLayout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxARes)
                    .addComponent(checkBoxAPimientos)
                    .addComponent(checkBoxAChampiñon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxAPuerco)
                    .addComponent(checkBoxAElote)
                    .addComponent(checkBoxAChileGuero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxAPollo)
                    .addComponent(checkBoxACebolla)
                    .addComponent(checkBoxAChileSerrano))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxAAdobada)
                    .addComponent(checkBoxAPiña)
                    .addComponent(checkBoxAJitomate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxACamarones)
                    .addComponent(checkBoxANopales)
                    .addComponent(checkBoxAPoblano))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkBoxATofu)
                .addGap(20, 20, 20)
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelAlambresLayout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(rBotonAChico)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(rBotonAMediano)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(rBotonAGrande))
                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAlambresLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rBotonAHarina)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rBotonAMaiz))
                            .addGroup(panelAlambresLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rBotonASalchichon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rBotonATocino)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rBotonAChorizo))))
                    .addGroup(panelAlambresLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rBotonAQueso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rBotonASQueso)))
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelAlambresLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(botonAceptarA, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonABack))
                    .addGroup(panelAlambresLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelAlambresLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(checkBoxAPimientos1)
                                    .addComponent(checkBoxAChampiñon1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(checkBoxAElote1)
                                    .addComponent(checkBoxAChileGuero1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(checkBoxACebolla1)
                                    .addComponent(checkBoxAChileSerrano1)))
                            .addGroup(panelAlambresLayout.createSequentialGroup()
                                .addComponent(checkBoxASalchichon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(checkBoxATocino)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(checkBoxAChorizo)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxAPiña1)
                    .addComponent(checkBoxAJitomate1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAlambresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxANopales1)
                    .addComponent(checkBoxAPoblano1))
                .addContainerGap(283, Short.MAX_VALUE))
        );

        panelChilaquiles1.setBackground(new java.awt.Color(255, 255, 102));
        panelChilaquiles1.setMinimumSize(new java.awt.Dimension(620, 601));

        jLabel19.setText("TIPO DE PROTEINA");

        jLabel21.setText("TACOS");

        grupoBotonProteinaCh.add(rBotonChSencillos1);
        rBotonChSencillos1.setText("SENCILLOS");

        grupoBotonProteinaCh.add(rBotonChRes1);
        rBotonChRes1.setText("RES");

        grupoBotonProteinaCh.add(rBotonChPuerco1);
        rBotonChPuerco1.setText("PUERCO");

        grupoBotonProteinaCh.add(rBotonChPollo1);
        rBotonChPollo1.setText("POLLO");

        grupoBotonProteinaCh.add(rBotonChAdobada1);
        rBotonChAdobada1.setText("ADOBADA");

        jLabel23.setText("SALSAS");

        grupoBotonSalsasCh.add(rBotonSalsaVerde1);
        rBotonSalsaVerde1.setText("VERDE");

        grupoBotonSalsasCh.add(rBotonSalsaRoja1);
        rBotonSalsaRoja1.setText("ROJA");

        botonAceptarCh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_100x100.png"))); // NOI18N
        botonAceptarCh1.setBorder(null);
        botonAceptarCh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarCh1ActionPerformed(evt);
            }
        });

        grupoBotonProteinaCh.add(rBotonChTofu1);
        rBotonChTofu1.setText("TOFU");

        botonChBack1.setText("Back");
        botonChBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonChBack1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelChilaquiles1Layout = new javax.swing.GroupLayout(panelChilaquiles1);
        panelChilaquiles1.setLayout(panelChilaquiles1Layout);
        panelChilaquiles1Layout.setHorizontalGroup(
            panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChilaquiles1Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChilaquiles1Layout.createSequentialGroup()
                        .addComponent(botonChBack1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonAceptarCh1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelChilaquiles1Layout.createSequentialGroup()
                        .addGroup(panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelChilaquiles1Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelChilaquiles1Layout.createSequentialGroup()
                                .addGroup(panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(rBotonChPollo1)
                                        .addComponent(rBotonChTofu1)
                                        .addComponent(rBotonChRes1)
                                        .addComponent(rBotonChAdobada1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rBotonChPuerco1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rBotonChSencillos1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(117, 117, 117)
                                .addGroup(panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rBotonSalsaVerde1)
                                    .addComponent(jLabel23)
                                    .addComponent(rBotonSalsaRoja1))))
                        .addGap(0, 243, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelChilaquiles1Layout.setVerticalGroup(
            panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChilaquiles1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rBotonChAdobada1)
                    .addComponent(rBotonSalsaVerde1))
                .addGap(8, 8, 8)
                .addGroup(panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rBotonChRes1)
                    .addComponent(rBotonSalsaRoja1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rBotonChPuerco1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rBotonChPollo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rBotonChTofu1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rBotonChSencillos1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                .addGroup(panelChilaquiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChilaquiles1Layout.createSequentialGroup()
                        .addComponent(botonAceptarCh1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChilaquiles1Layout.createSequentialGroup()
                        .addComponent(botonChBack1)
                        .addGap(37, 37, 37))))
        );

        javax.swing.GroupLayout panelMasterLayout = new javax.swing.GroupLayout(panelMaster);
        panelMaster.setLayout(panelMasterLayout);
        panelMasterLayout.setHorizontalGroup(
            panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 682, Short.MAX_VALUE)
            .addGroup(panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMasterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelChilaquiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMasterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelAlambres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMasterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelHamburguesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMasterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelEnsaladas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMasterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelChilaquiles1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        panelMasterLayout.setVerticalGroup(
            panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 665, Short.MAX_VALUE)
            .addGroup(panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMasterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelChilaquiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMasterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelAlambres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMasterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelHamburguesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMasterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelEnsaladas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(panelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMasterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelChilaquiles1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout subMenuLayout = new javax.swing.GroupLayout(subMenu.getContentPane());
        subMenu.getContentPane().setLayout(subMenuLayout);
        subMenuLayout.setHorizontalGroup(
            subMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(subMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelMaster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        subMenuLayout.setVerticalGroup(
            subMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(subMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelMaster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuTacos.setMinimumSize(new java.awt.Dimension(439, 590));
        menuTacos.setResizable(false);

        panelTacos.setBackground(new java.awt.Color(255, 255, 102));

        jLabel24.setText("TACOS");

        checkBoxTacoRes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxTacoRes.setText("Res");

        checkBoxTacoPuerco.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxTacoPuerco.setText("Puerco");

        checkBoxTacoPollo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxTacoPollo.setText("Pollo");

        checkBoxTacoAdobada.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxTacoAdobada.setText("Adobada");

        checkBoxTacoCamarones.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxTacoCamarones.setText("Camarones");

        checkBoxTacoChorizo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxTacoChorizo.setText("Chorizo");

        checkBoxTacoTofu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxTacoTofu.setText("Tofu");

        cajaTextoTacoCamarones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoTacoCamaronesActionPerformed(evt);
            }
        });

        jLabel25.setText("Cantidad");

        botonTacosOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_30x30.png"))); // NOI18N
        botonTacosOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTacosOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTacosLayout = new javax.swing.GroupLayout(panelTacos);
        panelTacos.setLayout(panelTacosLayout);
        panelTacosLayout.setHorizontalGroup(
            panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTacosLayout.createSequentialGroup()
                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTacosLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel24)
                        .addGap(87, 87, 87)
                        .addComponent(jLabel25)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelTacosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelTacosLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxTacoPuerco)
                                    .addComponent(checkBoxTacoRes)
                                    .addComponent(checkBoxTacoPollo)
                                    .addComponent(checkBoxTacoAdobada)
                                    .addComponent(checkBoxTacoCamarones, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(checkBoxTacoChorizo)
                                    .addComponent(checkBoxTacoTofu, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cajaTextoTacoPuerco, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoTacoRes, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoTacoPollo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoTacoAdobada, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoTacoCamarones, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoTacoTofu, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoTacoChorizo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(165, 165, 165)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTacosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonTacosOk)
                .addGap(92, 92, 92))
        );
        panelTacosLayout.setVerticalGroup(
            panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTacosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxTacoRes, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoTacoRes, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxTacoPuerco, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoTacoPuerco, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxTacoPollo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoTacoPollo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxTacoAdobada, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoTacoAdobada, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxTacoCamarones, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoTacoCamarones, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxTacoChorizo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoTacoChorizo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxTacoTofu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoTacoTofu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonTacosOk, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout menuTacosLayout = new javax.swing.GroupLayout(menuTacos.getContentPane());
        menuTacos.getContentPane().setLayout(menuTacosLayout);
        menuTacosLayout.setHorizontalGroup(
            menuTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTacos, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        menuTacosLayout.setVerticalGroup(
            menuTacosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTacos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        menuQuesadillas.setMinimumSize(new java.awt.Dimension(439, 590));
        menuQuesadillas.setResizable(false);

        panelQuesadillas.setBackground(new java.awt.Color(255, 255, 102));

        jLabel26.setText("Quesadillas");

        checkBoxQuesadillasRes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxQuesadillasRes.setText("Res");

        checkBoxQuesadillasPuerco.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxQuesadillasPuerco.setText("Puerco");

        checkBoxQuesadillasPollo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxQuesadillasPollo.setText("Pollo");

        checkBoxQuesadillasAdobada.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxQuesadillasAdobada.setText("Adobada");

        checkBoxQuesadillasCamarones.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxQuesadillasCamarones.setText("Camarones");

        checkBoxQuesadillasChorizo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxQuesadillasChorizo.setText("Chorizo");

        checkBoxQuesadillasTofu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxQuesadillasTofu.setText("Tofu");

        cajaTextoQuesadillaCamarones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoQuesadillaCamaronesActionPerformed(evt);
            }
        });

        jLabel27.setText("Cantidad");

        botonQuesadillasOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_30x30.png"))); // NOI18N
        botonQuesadillasOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonQuesadillasOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelQuesadillasLayout = new javax.swing.GroupLayout(panelQuesadillas);
        panelQuesadillas.setLayout(panelQuesadillasLayout);
        panelQuesadillasLayout.setHorizontalGroup(
            panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuesadillasLayout.createSequentialGroup()
                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelQuesadillasLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel26)
                        .addGap(87, 87, 87)
                        .addComponent(jLabel27)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelQuesadillasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelQuesadillasLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxQuesadillasPuerco)
                                    .addComponent(checkBoxQuesadillasRes)
                                    .addComponent(checkBoxQuesadillasPollo)
                                    .addComponent(checkBoxQuesadillasAdobada)
                                    .addComponent(checkBoxQuesadillasCamarones, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(checkBoxQuesadillasChorizo)
                                    .addComponent(checkBoxQuesadillasTofu, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cajaTextoQuesadillaPuerco, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoQuesadillaRes, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoQuesadillaPollo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoQuesadillaAdobada, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoQuesadillaCamarones, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoQuesadillaTofu, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoQuesadillaChorizo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(165, 165, 165)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelQuesadillasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonQuesadillasOk)
                .addGap(92, 92, 92))
        );
        panelQuesadillasLayout.setVerticalGroup(
            panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuesadillasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxQuesadillasRes, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoQuesadillaRes, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxQuesadillasPuerco, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoQuesadillaPuerco, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxQuesadillasPollo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoQuesadillaPollo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxQuesadillasAdobada, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoQuesadillaAdobada, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxQuesadillasCamarones, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoQuesadillaCamarones, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxQuesadillasChorizo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoQuesadillaChorizo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxQuesadillasTofu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoQuesadillaTofu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonQuesadillasOk, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout menuQuesadillasLayout = new javax.swing.GroupLayout(menuQuesadillas.getContentPane());
        menuQuesadillas.getContentPane().setLayout(menuQuesadillasLayout);
        menuQuesadillasLayout.setHorizontalGroup(
            menuQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelQuesadillas, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        menuQuesadillasLayout.setVerticalGroup(
            menuQuesadillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelQuesadillas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        menuCarnesAsadas.setMinimumSize(new java.awt.Dimension(412, 410));
        menuCarnesAsadas.setResizable(false);

        panelCarnes.setBackground(new java.awt.Color(255, 255, 102));

        jLabel28.setText("Carnes");

        checkBoxArrachera.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxArrachera.setText("Arrachera");

        checkBoxMuslo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxMuslo.setText("Muslo de Pollo");

        checkBoxPechuga.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxPechuga.setText("Pechuga de Pollo");

        checkBoxBistek.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxBistek.setText("Bistek");

        jLabel29.setText("Cantidad");

        botonCarnesOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_30x30.png"))); // NOI18N
        botonCarnesOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCarnesOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCarnesLayout = new javax.swing.GroupLayout(panelCarnes);
        panelCarnes.setLayout(panelCarnesLayout);
        panelCarnesLayout.setHorizontalGroup(
            panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCarnesLayout.createSequentialGroup()
                .addGroup(panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCarnesLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel28)
                        .addGap(87, 87, 87)
                        .addComponent(jLabel29)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelCarnesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelCarnesLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxMuslo)
                                    .addComponent(checkBoxArrachera)
                                    .addComponent(checkBoxPechuga)
                                    .addComponent(checkBoxBistek))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cajaTextoMuslo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoArrachera, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoPechuga, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoBistek, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(165, 165, 165)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCarnesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonCarnesOk)
                .addGap(91, 91, 91))
        );
        panelCarnesLayout.setVerticalGroup(
            panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCarnesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxArrachera, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoArrachera, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxMuslo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoMuslo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxPechuga, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoPechuga, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCarnesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxBistek, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoBistek, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(botonCarnesOk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(48, 48, 48))
        );

        javax.swing.GroupLayout menuCarnesAsadasLayout = new javax.swing.GroupLayout(menuCarnesAsadas.getContentPane());
        menuCarnesAsadas.getContentPane().setLayout(menuCarnesAsadasLayout);
        menuCarnesAsadasLayout.setHorizontalGroup(
            menuCarnesAsadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCarnes, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        menuCarnesAsadasLayout.setVerticalGroup(
            menuCarnesAsadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCarnes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        menuAguas.setMinimumSize(new java.awt.Dimension(360, 410));
        menuAguas.setResizable(false);

        panelAguas.setBackground(new java.awt.Color(255, 255, 102));

        jLabel30.setText("Aguas");

        checkBoxHorchata.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxHorchata.setText("Agua Horchata");

        checkBoxLimon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxLimon.setText("Aguas Limon");

        checkBoxNatural.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxNatural.setText("Agua Natural");

        checkBoxMineral.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxMineral.setText("Agua Mineral");

        jLabel31.setText("Cantidad");

        botonAguasOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_30x30.png"))); // NOI18N
        botonAguasOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAguasOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAguasLayout = new javax.swing.GroupLayout(panelAguas);
        panelAguas.setLayout(panelAguasLayout);
        panelAguasLayout.setHorizontalGroup(
            panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAguasLayout.createSequentialGroup()
                .addGroup(panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAguasLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel30)
                        .addGap(87, 87, 87)
                        .addComponent(jLabel31)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelAguasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAguasLayout.createSequentialGroup()
                                .addGroup(panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelAguasLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(botonAguasOk))
                                    .addGroup(panelAguasLayout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addGroup(panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(checkBoxLimon)
                                            .addComponent(checkBoxHorchata)
                                            .addComponent(checkBoxNatural)
                                            .addComponent(checkBoxMineral))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cajaTextoLimon, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cajaTextoHorchata, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cajaTextoNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cajaTextoMineral, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(165, 165, 165)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAguasLayout.setVerticalGroup(
            panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAguasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxHorchata, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoHorchata, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxLimon, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoLimon, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxMineral, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoMineral, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(botonAguasOk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout menuAguasLayout = new javax.swing.GroupLayout(menuAguas.getContentPane());
        menuAguas.getContentPane().setLayout(menuAguasLayout);
        menuAguasLayout.setHorizontalGroup(
            menuAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAguas, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        menuAguasLayout.setVerticalGroup(
            menuAguasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAguas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menuRefrescos.setMinimumSize(new java.awt.Dimension(355, 625));

        panelRefrescos.setBackground(new java.awt.Color(255, 255, 102));

        jLabel32.setText("Refrescos");

        checkBoxCoca.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxCoca.setText("Coca Cola");

        checkBoxCocaLight.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxCocaLight.setText("Coca Cola Light");

        checkBoxFresca.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxFresca.setText("Fresca");

        checkBoxFantaN.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxFantaN.setText("Fanta Naranja");

        jLabel33.setText("Cantidad");

        botonRefrescoOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_30x30.png"))); // NOI18N
        botonRefrescoOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRefrescoOkActionPerformed(evt);
            }
        });

        checkBoxFantaR.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxFantaR.setText("Fanta Fresa");

        checkBoxManzanita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxManzanita.setText("Manzanita");

        checkBoxSprite.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxSprite.setText("Sprite");

        javax.swing.GroupLayout panelRefrescosLayout = new javax.swing.GroupLayout(panelRefrescos);
        panelRefrescos.setLayout(panelRefrescosLayout);
        panelRefrescosLayout.setHorizontalGroup(
            panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRefrescosLayout.createSequentialGroup()
                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRefrescosLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel32)
                        .addGap(87, 87, 87)
                        .addComponent(jLabel33)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelRefrescosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRefrescosLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxCocaLight)
                                    .addComponent(checkBoxCoca)
                                    .addComponent(checkBoxFresca)
                                    .addComponent(checkBoxFantaN)
                                    .addComponent(checkBoxFantaR)
                                    .addComponent(checkBoxManzanita)
                                    .addComponent(checkBoxSprite))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cajaTextoCocaLight, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoCoca, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoFresca, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoFantaN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoFantaR, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoManzanita, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoSprite, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(165, 165, 165)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRefrescosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonRefrescoOk)
                .addGap(149, 149, 149))
        );
        panelRefrescosLayout.setVerticalGroup(
            panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRefrescosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxCoca, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoCoca, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxCocaLight, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoCocaLight, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxFresca, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoFresca, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxFantaN, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoFantaN, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxFantaR, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoFantaR, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxManzanita, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoManzanita, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxSprite, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoSprite, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(botonRefrescoOk, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout menuRefrescosLayout = new javax.swing.GroupLayout(menuRefrescos.getContentPane());
        menuRefrescos.getContentPane().setLayout(menuRefrescosLayout);
        menuRefrescosLayout.setHorizontalGroup(
            menuRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRefrescos, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );
        menuRefrescosLayout.setVerticalGroup(
            menuRefrescosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuRefrescosLayout.createSequentialGroup()
                .addComponent(panelRefrescos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        menuCerveza.setMinimumSize(new java.awt.Dimension(685, 625));

        panelCerveza.setBackground(new java.awt.Color(255, 255, 102));

        jLabel34.setText("Cerveza");

        checkBoxVictoria.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxVictoria.setText("Victoria");

        checkBoxCorona.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxCorona.setText("Corona");

        checkBoxModeloNegra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxModeloNegra.setText("Modelo Negra");

        checkBoxModeloEspecial.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxModeloEspecial.setText("Modelo Especial");

        botonCervezaOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_30x30.png"))); // NOI18N
        botonCervezaOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCervezaOkActionPerformed(evt);
            }
        });

        checkBoxModeloAmbar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxModeloAmbar.setText("Modelo Ambar");

        checkBoxCucapa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxCucapa.setText("Cucapa");

        checkBoxUltra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxUltra.setText("Ultra");

        checkBoxVasoChelado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxVasoChelado.setText("Vaso Chelado");

        checkBoxVasoMichelado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxVasoMichelado.setText("Vaso Michelado");

        checkBoxVasoGringa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxVasoGringa.setText("Vaso Gringa");

        checkBoxCubeta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxCubeta.setText("Cubeta");

        javax.swing.GroupLayout panelCervezaLayout = new javax.swing.GroupLayout(panelCerveza);
        panelCerveza.setLayout(panelCervezaLayout);
        panelCervezaLayout.setHorizontalGroup(
            panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCervezaLayout.createSequentialGroup()
                .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCervezaLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel34)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelCervezaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelCervezaLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxCorona)
                                    .addComponent(checkBoxVictoria)
                                    .addComponent(checkBoxModeloNegra)
                                    .addComponent(checkBoxModeloEspecial)
                                    .addComponent(checkBoxModeloAmbar)
                                    .addComponent(checkBoxCucapa)
                                    .addComponent(checkBoxUltra))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cajaTextoModeloEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoModeloAmbar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoUltra, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCervezaLayout.createSequentialGroup()
                                        .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cajaTextoCorona, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cajaTextoVictoria, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cajaTextoModeloNegra, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cajaTextoCucapa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                                        .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelCervezaLayout.createSequentialGroup()
                                                .addComponent(checkBoxCubeta)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cajaTextoCubeta, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panelCervezaLayout.createSequentialGroup()
                                                .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(checkBoxVasoMichelado)
                                                    .addComponent(checkBoxVasoChelado)
                                                    .addComponent(checkBoxVasoGringa))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cajaTextoVasoMichelado, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cajaTextoVasoChelado, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cajaTextoVasoGringa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))))))))
                .addGap(35, 35, 35))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCervezaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonCervezaOk)
                .addGap(149, 149, 149))
        );
        panelCervezaLayout.setVerticalGroup(
            panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCervezaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCervezaLayout.createSequentialGroup()
                        .addComponent(checkBoxVictoria, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkBoxCorona, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkBoxModeloNegra, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkBoxModeloEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkBoxModeloAmbar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkBoxCucapa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkBoxUltra, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCervezaLayout.createSequentialGroup()
                        .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCervezaLayout.createSequentialGroup()
                                .addComponent(cajaTextoVictoria, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cajaTextoCorona, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cajaTextoModeloNegra, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCervezaLayout.createSequentialGroup()
                                .addComponent(checkBoxVasoChelado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(checkBoxVasoMichelado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(checkBoxVasoGringa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCervezaLayout.createSequentialGroup()
                                .addComponent(cajaTextoVasoChelado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cajaTextoVasoMichelado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cajaTextoVasoGringa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(cajaTextoModeloEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cajaTextoModeloAmbar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cajaTextoCucapa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkBoxCubeta, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cajaTextoCubeta, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(cajaTextoUltra, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(botonCervezaOk, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout menuCervezaLayout = new javax.swing.GroupLayout(menuCerveza.getContentPane());
        menuCerveza.getContentPane().setLayout(menuCervezaLayout);
        menuCervezaLayout.setHorizontalGroup(
            menuCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCerveza, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );
        menuCervezaLayout.setVerticalGroup(
            menuCervezaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuCervezaLayout.createSequentialGroup()
                .addComponent(panelCerveza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        menuBebidasAd.setMinimumSize(new java.awt.Dimension(460, 465));

        panelBebidasAd.setBackground(new java.awt.Color(255, 255, 102));

        jLabel36.setText("Quesadillas");

        checkBoxCafeAmericano.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxCafeAmericano.setText("Cafe Americano");

        checkBoxCafeAmericanoRef.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxCafeAmericanoRef.setText("Cafe Americano Ref");

        checkBoxTe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxTe.setText("Te");

        checkBoxTissanaFria.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxTissanaFria.setText("Tissana Fria");

        jLabel37.setText("Cantidad");

        botonBebidasAdOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/palomita_30x30.png"))); // NOI18N
        botonBebidasAdOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBebidasAdOkActionPerformed(evt);
            }
        });

        checkBoxTissannaCaliente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBoxTissannaCaliente.setText("Tissana Caliente");

        javax.swing.GroupLayout panelBebidasAdLayout = new javax.swing.GroupLayout(panelBebidasAd);
        panelBebidasAd.setLayout(panelBebidasAdLayout);
        panelBebidasAdLayout.setHorizontalGroup(
            panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBebidasAdLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel36)
                .addGap(87, 87, 87)
                .addComponent(jLabel37)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelBebidasAdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBebidasAdLayout.createSequentialGroup()
                        .addGroup(panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelBebidasAdLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(botonBebidasAdOk))
                            .addGroup(panelBebidasAdLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkBoxCafeAmericanoRef)
                                    .addComponent(checkBoxCafeAmericano)
                                    .addComponent(checkBoxTe)
                                    .addComponent(checkBoxTissanaFria)
                                    .addComponent(checkBoxTissannaCaliente))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cajaTextoCafeAmericanoRef, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoCafeAmericano, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoTe, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoTissanaFria, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaTextoTissanaCaliente, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(76, 76, 76))))
        );
        panelBebidasAdLayout.setVerticalGroup(
            panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBebidasAdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxCafeAmericano, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoCafeAmericano, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxCafeAmericanoRef, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoCafeAmericanoRef, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxTe, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoTe, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxTissanaFria, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoTissanaFria, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxTissannaCaliente, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoTissanaCaliente, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(botonBebidasAdOk, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout menuBebidasAdLayout = new javax.swing.GroupLayout(menuBebidasAd.getContentPane());
        menuBebidasAd.getContentPane().setLayout(menuBebidasAdLayout);
        menuBebidasAdLayout.setHorizontalGroup(
            menuBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBebidasAd, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );
        menuBebidasAdLayout.setVerticalGroup(
            menuBebidasAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuBebidasAdLayout.createSequentialGroup()
                .addComponent(panelBebidasAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));

        botonNuevaMesa.setText("Nueva Mesa");
        botonNuevaMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevaMesaActionPerformed(evt);
            }
        });

        tablaOrden.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaOrden);

        botonChilaquiles.setText("Chilaquiles");
        botonChilaquiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonChilaquilesActionPerformed(evt);
            }
        });

        botonAlambres.setText("Alambres");
        botonAlambres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAlambresActionPerformed(evt);
            }
        });

        botonHamburguesas.setText("Hamburguesas");
        botonHamburguesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonHamburguesasActionPerformed(evt);
            }
        });

        botonEnsaladas.setText("Ensaladas");
        botonEnsaladas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEnsaladasActionPerformed(evt);
            }
        });

        botonTacos.setText("Tacos");
        botonTacos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTacosActionPerformed(evt);
            }
        });

        botonQuesadillas.setText("Quesadillas");
        botonQuesadillas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonQuesadillasActionPerformed(evt);
            }
        });

        botonCarnesAsadas.setText("Carnes Asadas");
        botonCarnesAsadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCarnesAsadasActionPerformed(evt);
            }
        });

        botonAguaHorchata.setText("Aguas");
        botonAguaHorchata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAguaHorchataActionPerformed(evt);
            }
        });

        botonRefrescos.setText("Refrescos");
        botonRefrescos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRefrescosActionPerformed(evt);
            }
        });

        botonCerveza.setText("Cerveza");
        botonCerveza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCervezaActionPerformed(evt);
            }
        });

        botonTisana.setText("Bebidas Calientes");
        botonTisana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTisanaActionPerformed(evt);
            }
        });

        botonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(botonEnsaladas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonChilaquiles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonAlambres, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonHamburguesas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botonCarnesAsadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonQuesadillas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonTacos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonTisana, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonCerveza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonRefrescos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonAguaHorchata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(botonNuevaMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(botonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonNuevaMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonChilaquiles, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonTacos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonAguaHorchata, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonAlambres, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonQuesadillas, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonRefrescos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonHamburguesas, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonCarnesAsadas, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonCerveza, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonEnsaladas, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonTisana, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57)
                .addComponent(botonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonNuevaMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevaMesaActionPerformed
        MesaDialog.setVisible(true);
        MesaDialog.setLocationRelativeTo(null);
        numOrden++;
    }//GEN-LAST:event_botonNuevaMesaActionPerformed

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        mesa = Integer.parseInt(cajaNumMesa.getText());
        MesaDialog.setVisible(false);
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonChilaquilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonChilaquilesActionPerformed
        subMenu.setVisible(true);
        panelAlambres.setVisible(false);
        panelChilaquiles.setVisible(true);
        panelEnsaladas.setVisible(false);
        panelHamburguesas.setVisible(false);
    }//GEN-LAST:event_botonChilaquilesActionPerformed

    private void botonAlambresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAlambresActionPerformed
        subMenu.setVisible(true);
        panelAlambres.setVisible(true);
        panelChilaquiles.setVisible(false);
        panelEnsaladas.setVisible(false);
        panelHamburguesas.setVisible(false);

    }//GEN-LAST:event_botonAlambresActionPerformed

    private void botonABackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonABackActionPerformed
        subMenu.dispose();
    }//GEN-LAST:event_botonABackActionPerformed

    private void checkBoxAPuercoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxAPuercoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxAPuercoActionPerformed

    private void botonChBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonChBackActionPerformed
        subMenu.dispose();
    }//GEN-LAST:event_botonChBackActionPerformed

    private void botonAceptarChActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarChActionPerformed
        String platillo = "Chilaquiles ";
        String complementos = null;
        String extras = null;
        int cantidad = 1;
        double precio = 0;

        if (rBotonChSencillos.isSelected()) {
            platillo += "sencillos";
        } else if (rBotonChRes.isSelected()) {
            platillo += "con res";

        } else if (rBotonChPuerco.isSelected()) {
            platillo += "con puerco";
        } else if (rBotonChPollo.isSelected()) {
            platillo += "con pollo";
        } else if (rBotonChAdobada.isSelected()) {
            platillo += "con adobada";
        }
            if (rBotonSalsaVerde.isSelected()) {
                complementos = "salsa verde";
            } else {
                complementos = "salsa roja";
            }

            precio = obtenerPrecioPlatillosBD(platillo);
            Platillo nPlatillo = new Platillo(platillo, cantidad, complementos, precio, mesa);
            insertarOrdenNuevoPlatillo(nPlatillo);

            obtenerOrdenActual();

            subMenu.dispose();
    }//GEN-LAST:event_botonAceptarChActionPerformed
    
    private void checkBoxHLechugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxHLechugaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxHLechugaActionPerformed

    private void botonEBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEBackActionPerformed
        subMenu.dispose();
    }//GEN-LAST:event_botonEBackActionPerformed

    private void botonHBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonHBackActionPerformed
        subMenu.dispose();
    }//GEN-LAST:event_botonHBackActionPerformed

    private void botonAceptarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarAActionPerformed
        String platillo = "Alambre ";
        String complementos = "";
        String extras = "";
        int cantidad = 1;
        double precio = 0;
        
        int contP=0;
        //Seleccion de tamaño de Alambre
        if (rBotonAChico.isSelected()) {
            platillo += "ch ";
        } else if (rBotonAMediano.isSelected()) {
            platillo += "med ";
        } else if (rBotonAGrande.isSelected()) {
            platillo += "gde ";
        }

        //Seleccion de proteina en Alambre
        if (checkBoxARes.isSelected()) {
            platillo += "res";
            contP += 1;
        }
        if (checkBoxAPuerco.isSelected()) {
            platillo += "puerco";
            contP += 1;
        }
        if (checkBoxAPollo.isSelected()) {
            platillo += "pollo";
            contP += 1;
        }
        if (checkBoxAAdobada.isSelected()) {
            platillo += "adobada";
            contP += 1;
        }
        if (checkBoxACamarones.isSelected()) {
            platillo += "camarones";
            contP += 1;
        }
        if (checkBoxATofu.isSelected()) {
            platillo += "tofu";
            contP += 1;
        }

        if (contP > 1) {
            platillo += "mixto";
        }

        precio = obtenerPrecioPlatillosBD(platillo);

        //Seleccion de Verdura en Alambre
        int contV = 0;
        if (checkBoxAPimientos.isSelected()) {
            complementos += "Pimientos ";
            contV += 1;
        }
        if (checkBoxAElote.isSelected()) {
            complementos += "Elote ";
            contV += 1;
        }
        if (checkBoxACebolla.isSelected()) {
            complementos += "Cebolla ";
            contV += 1;
        }
        if (checkBoxAPiña.isSelected()) {
            complementos += "Piña ";
            contV += 1;
        }
        if (checkBoxANopales.isSelected()) {
            complementos += "Nopalitos ";
            contV += 1;
        }
        if (checkBoxAChampiñon.isSelected()) {
            complementos += "Champiñones ";
            contV += 1;
        }
        if (checkBoxAChileGuero.isSelected()) {
            complementos += "ChileGuero ";
            contV += 1;
        }
        if (checkBoxAChileSerrano.isSelected()) {
            complementos += "ChileSerrano ";
            contV += 1;
        }
        if (checkBoxAJitomate.isSelected()) {
            complementos += "Jitomate ";
            contV += 1;
        }
        if (checkBoxAPoblano.isSelected()) {
            complementos += "Poblano ";
            contV += 1;
        }

        //Seleccion de carne fria en Alambre
        int contCF = 0;
        if (rBotonASalchichon.isSelected()) {
            complementos += "Salchichon ";
            contCF += 1;
        }
        else if (rBotonAChorizo.isSelected()) {
            complementos += "Chorizo ";
            contCF += 1;
        }
        if (rBotonATocino.isSelected()) {
            complementos += "Tocino ";
            contCF += 1;
        }

        //Sleccionado de extras (Verduras o CarnesFrias)
//        String extra1 = "";
//        String extra2 = "";
//        if (contV < 5) {
//            if (contCF == 3) {
//                extra1 = carneFria[1];
//                precio += 10;
//                extra2 = carneFria[2];
//                precio += 10;
//            } else if (contCF == 2) {
//                extra1 = carneFria[1];
//                precio += 10;
//            }
//        } else if (contV == 5) {
//            extra1 = verdura[4];
//            precio += 5;
//            if (contCF == 2) {
//                extra2 = carneFria[1];
//                precio += 10;
//            }
//        } else {
//            extra1 = verdura[4];
//            precio += 5;
//            extra2 = verdura[5];
//            precio += 5;
//        }

        //Seleccionado de Tortillas
        if (rBotonAMaiz.isSelected()) {
            complementos += "TortillaMaiz ";
        } else if (rBotonAHarina.isSelected()) {
            complementos += "TortillaHarina ";
        }

        //Seleccionado de Queso
        if (rBotonAQueso.isSelected()) {
            complementos += "ConQueso";
        } else if (rBotonASQueso.isSelected()) {
            complementos += "SinQueso";
        }

        Platillo nPlatillo = new Platillo(platillo, cantidad, complementos, extras, precio, mesa);
        insertarOrdenNuevoPlatillo(nPlatillo);

        obtenerOrdenActual();

        uncheckBox();
        subMenu.dispose();
    }//GEN-LAST:event_botonAceptarAActionPerformed

    private void botonHamburguesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonHamburguesasActionPerformed
        subMenu.setVisible(true);
        panelAlambres.setVisible(false);
        panelChilaquiles.setVisible(false);
        panelEnsaladas.setVisible(false);
        panelHamburguesas.setVisible(true);
    }//GEN-LAST:event_botonHamburguesasActionPerformed

    private void botonAceptarHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarHActionPerformed
        String platillo = "Hamburguesa ";
        String complementos = "";
        String extras = "";
        int cantidad = 1;
        double precio = 0;

        //Seleccion de proteina en Hamburguesa
        if (rBotonHRes.isSelected()) {
            platillo += "res";
        } else if (rBotonHDRes.isSelected()) {
            platillo += "doble res";
        } else if (rBotonHVegetariana.isSelected()) {
            platillo += " vegetariana";
        }

        precio = obtenerPrecioPlatillosBD(platillo);

        //Seleccion de Verdura en Hamburguesa
        int contV = 0;
        if (checkBoxHLechuga.isSelected()) {
            complementos += "Lechuga ";
            contV += 1;
        }
        if (checkBoxHCebolla.isSelected()) {
            complementos += "Cebolla ";
            contV += 1;
        }
        if (checkBoxHPiña.isSelected()) {
            complementos += "Piña ";
            contV += 1;
        }
        if (checkBoxHChampiñones.isSelected()) {
            complementos += "Champiñones ";
            contV += 1;
        }
        if (checkBoxHChileGuero.isSelected()) {
            complementos += "ChileGuero ";
            contV += 1;
        }
        if (checkBoxHJitomate.isSelected()) {
            complementos += "Jitomate ";
            contV += 1;
        }

        //Seleccionado de extras (Verduras o CarnesFrias)
        int contE = 0;
        if (checkBoxHTocino.isSelected()) {
            extras += "Tocino ";
            contE += 1;
        }
        if (checkBoxHManchego.isSelected()) {
            extras += "QuesoManchego ";
            contE += 1;
        }
        if (checkBoxHAsadero.isSelected()) {
            extras += "QuesoAsadero ";
            contE += 1;
        }
        if (checkBoxHAguacate.isSelected()) {
            extras += "Aguacate ";
            contE += 1;
        }

        Platillo nPlatillo = new Platillo(platillo, cantidad, complementos, extras, precio, mesa);
        insertarOrdenNuevoPlatillo(nPlatillo);

        obtenerOrdenActual();

        uncheckBox();
        subMenu.dispose();
    }//GEN-LAST:event_botonAceptarHActionPerformed

    private void botonAceptarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarEActionPerformed
        String platillo = "Ensalada ";
        String complementos = "";
        String extras = "";
        int cantidad = 1;
        double precio = 0;
        
        //Seleccion de proteina en Hamburguesa
        if (checkBoxERes.isSelected()) {
            platillo += "Res";
        }
        if (checkBoxEPuerco.isSelected()) {
            platillo +="Puerco";
        }
        if (checkBoxEPollo.isSelected()) {
            platillo += "Pollo";
        }
        if (checkBoxEAdobada.isSelected()) {
            platillo += "Adobada";
        }
        if (checkBoxECamarones.isSelected()) {
            platillo += "Camarones";
        }
        if (checkBoxETofu.isSelected()) {
            platillo += "Tofu";
        }

        precio = obtenerPrecioPlatillosBD(platillo);

        //Seleccion de Verdura en Hamburguesa
        int contV = 0;
        if (checkBoxEPimientos.isSelected()) {
            complementos += "Pimientos ";
            contV += 1;
        }
        if (checkBoxEElote.isSelected()) {
            complementos += "Elote ";
            contV += 1;
        }
        if (checkBoxECebolla.isSelected()) {
            complementos += "Cebolla ";
            contV += 1;
        }
        if (checkBoxEPiña.isSelected()) {
            complementos += "Piña ";
            contV += 1;
        }
        if (checkBoxENopales.isSelected()) {
            complementos += "Nopales ";
            contV += 1;
        }
        if (checkBoxEChampiñon.isSelected()) {
            complementos += "Champiñones ";
            contV += 1;
        }
        if (checkBoxEChileGuero.isSelected()) {
            complementos += "ChileGuero ";
            contV += 1;
        }
        if (checkBoxEZanahoria.isSelected()) {
            complementos += "Zanahoria ";
            contV += 1;
        }
        if (checkBoxEJitomate.isSelected()) {
            complementos += "Jitomate ";
            contV += 1;
        }
        if (checkBoxEPoblano.isSelected()) {
            complementos += "Poblano ";
            contV += 1;
        }

        //Seleccionado de Mix
        if (checkBoxEMixSemillas.isSelected()) {
            complementos += "MixSemillas ";
        }
        if (checkBoxECrotones.isSelected()) {
            complementos += "Crotones ";
        }
        if (checkBoxETortilla.isSelected()) {
            complementos += "TirasTortilla ";
        }

        //Seleccionado de Aderezo
        if (rBotonEChipotle.isSelected()) {
            complementos += "Chipotle ";
        }
        if (rBotonEMostazaMiel.isSelected()) {
            complementos += "MostazaMiel";
        }
        if (rBotonEBalsamico.isSelected()) {
            complementos += "Balsamico ";
        }

        Platillo nPlatillo = new Platillo(platillo, cantidad, complementos, extras, precio, mesa);
        insertarOrdenNuevoPlatillo(nPlatillo);

        obtenerOrdenActual();

        uncheckBox();
        subMenu.dispose();
    }//GEN-LAST:event_botonAceptarEActionPerformed

    private void botonEnsaladasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEnsaladasActionPerformed
        subMenu.setVisible(true);
        panelAlambres.setVisible(false);
        panelChilaquiles.setVisible(false);
        panelEnsaladas.setVisible(true);
        panelHamburguesas.setVisible(false);
    }//GEN-LAST:event_botonEnsaladasActionPerformed

    private void rBotonHResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rBotonHResActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rBotonHResActionPerformed

    private void botonTacosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTacosActionPerformed
      menuTacos.setVisible(true);
      menuTacos.setLocationRelativeTo(null);
    }//GEN-LAST:event_botonTacosActionPerformed

    private void botonAceptarCh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarCh1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAceptarCh1ActionPerformed

    private void botonChBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonChBack1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonChBack1ActionPerformed

    private void cajaTextoTacoCamaronesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaTextoTacoCamaronesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaTextoTacoCamaronesActionPerformed

    private void botonTacosOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTacosOkActionPerformed
        String platillo;
        double precio = 0;

        if (checkBoxTacoRes.isSelected()) {
            platillo = "Tacos de res";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoTacoRes.getText());
            precio = precio * cantidad;
            Platillo rplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(rplatillo);
        }
        if (checkBoxTacoPuerco.isSelected()) {
            platillo = "Tacos de puerco";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoTacoPuerco.getText());
            precio = precio * cantidad;
            Platillo puplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(puplatillo);
        }
        if (checkBoxTacoPollo.isSelected()) {
            platillo = "Tacos de pollo";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoTacoPollo.getText());
            precio = precio * cantidad;
            Platillo pplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(pplatillo);
        }
        if (checkBoxTacoAdobada.isSelected()) {
            platillo = "Tacos de adobada";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoTacoAdobada.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxTacoCamarones.isSelected()) {
            platillo = "Tacos de camarones";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoTacoCamarones.getText());
            precio = precio * cantidad;
            Platillo cplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(cplatillo);
        }
        if (checkBoxTacoChorizo.isSelected()) {
            platillo = "Tacos de chorizo";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoTacoChorizo.getText());
            precio = precio * cantidad;
            Platillo chplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(chplatillo);
        }
        if (checkBoxTacoTofu.isSelected()) {
            platillo = "Tacos de tofu";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoTacoTofu.getText());
            precio = precio * cantidad;
            Platillo tplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(tplatillo);
        }

        obtenerOrdenActual();
        uncheckBox();
        menuTacos.dispose();
    }//GEN-LAST:event_botonTacosOkActionPerformed

    private void botonQuesadillasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonQuesadillasActionPerformed
        menuQuesadillas.setVisible(true);
        menuQuesadillas.setLocationRelativeTo(null);
    }//GEN-LAST:event_botonQuesadillasActionPerformed

    private void cajaTextoQuesadillaCamaronesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaTextoQuesadillaCamaronesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaTextoQuesadillaCamaronesActionPerformed

    private void botonQuesadillasOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonQuesadillasOkActionPerformed
        String platillo;
        double precio = 0;

        if (checkBoxQuesadillasRes.isSelected()) {
            platillo = "Quesadillas de res";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoQuesadillaRes.getText());
            precio = precio * cantidad;
            Platillo rplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(rplatillo);
        }
        if (checkBoxQuesadillasPuerco.isSelected()) {
            platillo = "Quesadillas de puerco";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoQuesadillaPuerco.getText());
            precio = precio * cantidad;
            Platillo puplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(puplatillo);
        }
        if (checkBoxQuesadillasPollo.isSelected()) {
            platillo = "Quesadillas de pollo";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoQuesadillaPollo.getText());
            precio = precio * cantidad;
            Platillo pplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(pplatillo);
        }
        if (checkBoxQuesadillasAdobada.isSelected()) {
            platillo = "Quesadillas de adobada";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoQuesadillaAdobada.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxQuesadillasCamarones.isSelected()) {
            platillo = "Quesadillas de camarones";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoQuesadillaCamarones.getText());
            precio = precio * cantidad;
            Platillo cplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(cplatillo);
        }
        if (checkBoxQuesadillasChorizo.isSelected()) {
            platillo = "Quesadillas de chorizo";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoQuesadillaChorizo.getText());
            precio = precio * cantidad;
            Platillo chplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(chplatillo);
        }
        if (checkBoxQuesadillasTofu.isSelected()) {
            platillo = "Quesadillas de tofu";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoQuesadillaTofu.getText());
            precio = precio * cantidad;
            Platillo tplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(tplatillo);
        }

        obtenerOrdenActual();
        uncheckBox();
        menuQuesadillas.dispose();
    }//GEN-LAST:event_botonQuesadillasOkActionPerformed

    private void botonCarnesAsadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCarnesAsadasActionPerformed
        menuCarnesAsadas.setVisible(true);
        menuCarnesAsadas.setLocationRelativeTo(null);
    }//GEN-LAST:event_botonCarnesAsadasActionPerformed

    private void botonCarnesOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCarnesOkActionPerformed
        String platillo;
        double precio = 0;

        if (checkBoxArrachera.isSelected()) {
            platillo = "Arrachera";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoArrachera.getText());
            precio = precio * cantidad;
            Platillo rplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(rplatillo);
        }
        if (checkBoxMuslo.isSelected()) {
            platillo = "Muslo de Pollo";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoMuslo.getText());
            precio = precio * cantidad;
            Platillo puplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(puplatillo);
        }
        if (checkBoxPechuga.isSelected()) {
            platillo = "Pechuga de Pollo";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoPechuga.getText());
            precio = precio * cantidad;
            Platillo pplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(pplatillo);
        }
        if (checkBoxBistek.isSelected()) {
            platillo = "Bistek";
            precio = obtenerPrecioPlatillosBD(platillo);
            int cantidad =  Integer.parseInt(cajaTextoBistek.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(platillo, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }


        obtenerOrdenActual();
        uncheckBox();
        menuCarnesAsadas.dispose();
    }//GEN-LAST:event_botonCarnesOkActionPerformed

    private void botonAguaHorchataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAguaHorchataActionPerformed
        menuAguas.setVisible(true);
        menuAguas.setLocationRelativeTo(null);
    }//GEN-LAST:event_botonAguaHorchataActionPerformed

    private void botonAguasOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAguasOkActionPerformed
        String bebida;
        double precio = 0;

        if (checkBoxHorchata.isSelected()) {
            bebida = "Agua Horchata";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoArrachera.getText());
            precio = precio * cantidad;
            Platillo rplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(rplatillo);
        }
        if (checkBoxLimon.isSelected()) {
            bebida = "Agua Limon";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoLimon.getText());
            precio = precio * cantidad;
            Platillo puplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(puplatillo);
        }
        if (checkBoxNatural.isSelected()) {
            bebida = "Agua Natural";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoNatural.getText());
            precio = precio * cantidad;
            Platillo pplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(pplatillo);
        }
        if (checkBoxMineral.isSelected()) {
            bebida = "Agua Mineral";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoMineral.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }


        obtenerOrdenActual();
        uncheckBox();
        menuAguas.dispose();
    }//GEN-LAST:event_botonAguasOkActionPerformed

    private void botonRefrescosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRefrescosActionPerformed
        menuRefrescos.setVisible(true);
        menuRefrescos.setLocationRelativeTo(null);
    }//GEN-LAST:event_botonRefrescosActionPerformed

    private void botonRefrescoOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRefrescoOkActionPerformed
        String bebida;
        double precio = 0;

        if (checkBoxCoca.isSelected()) {
            bebida = "Coca Cola";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoCoca.getText());
            precio = precio * cantidad;
            Platillo rplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(rplatillo);
        }
        if (checkBoxCocaLight.isSelected()) {
            bebida = "Coca Light";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoCocaLight.getText());
            precio = precio * cantidad;
            Platillo puplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(puplatillo);
        }
        if (checkBoxFresca.isSelected()) {
            bebida = "Fresca";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoFresca.getText());
            precio = precio * cantidad;
            Platillo pplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(pplatillo);
        }
        if (checkBoxFantaN.isSelected()) {
            bebida = "Fanta Naranja";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoFantaN.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxFantaR.isSelected()) {
            bebida = "Fanta Roja";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoFantaR.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxManzanita.isSelected()) {
            bebida = "Manzanita";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoManzanita.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxSprite.isSelected()) {
            bebida = "Sprite";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoSprite.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }

        
        obtenerOrdenActual();
        uncheckBox();
        menuRefrescos.dispose();
    }//GEN-LAST:event_botonRefrescoOkActionPerformed

    private void botonCervezaOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCervezaOkActionPerformed
        String bebida;
        double precio = 0;

        if (checkBoxVictoria.isSelected()) {
            bebida = "Cerveza Victoria";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoVictoria.getText());
            precio = precio * cantidad;
            Platillo rplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(rplatillo);
        }
        if (checkBoxCorona.isSelected()) {
            bebida = "Cerveza Corona";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoCorona.getText());
            precio = precio * cantidad;
            Platillo puplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(puplatillo);
        }
        if (checkBoxModeloNegra.isSelected()) {
            bebida = "Cerveza Modelo Negra";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoModeloNegra.getText());
            precio = precio * cantidad;
            Platillo pplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(pplatillo);
        }
        if (checkBoxModeloEspecial.isSelected()) {
            bebida = "Cerveza Modelo Especial";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoModeloEspecial.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxModeloAmbar.isSelected()) {
            bebida = "Cerveza Modelo Ambar";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoModeloAmbar.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxCucapa.isSelected()) {
            bebida = "Cerveza Cucapa";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoCucapa.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxUltra.isSelected()) {
            bebida = "Cerveza Ultra";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoUltra.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxVasoChelado.isSelected()) {
            bebida = "Vaso Chelado";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoVasoChelado.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxVasoMichelado.isSelected()) {
            bebida = "Vaso Michelado";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoVasoMichelado.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxVasoGringa.isSelected()) {
            bebida = "Vaso Gringa";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoVasoGringa.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxCubeta.isSelected()) {
            bebida = "Cubeta";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoCubeta.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }

        obtenerOrdenActual();
        uncheckBox();
        menuCerveza.dispose();
    }//GEN-LAST:event_botonCervezaOkActionPerformed

    private void botonCervezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCervezaActionPerformed
        menuCerveza.setVisible(true);
        menuCerveza.setLocationRelativeTo(null);
    }//GEN-LAST:event_botonCervezaActionPerformed

    private void botonBebidasAdOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBebidasAdOkActionPerformed
        String bebida;
        double precio = 0;

        if (checkBoxCafeAmericano.isSelected()) {
            bebida = "Cafe Americano";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoCafeAmericano.getText());
            precio = precio * cantidad;
            Platillo rplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(rplatillo);
        }
        if (checkBoxCafeAmericanoRef.isSelected()) {
            bebida = "Cafe Americano Ref";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoCafeAmericanoRef.getText());
            precio = precio * cantidad;
            Platillo puplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(puplatillo);
        }
        if (checkBoxTe.isSelected()) {
            bebida = "Te";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoTe.getText());
            precio = precio * cantidad;
            Platillo pplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(pplatillo);
        }
        if (checkBoxTissanaFria.isSelected()) {
            bebida = "Tissana Fria";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoTissanaFria.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
        if (checkBoxTissannaCaliente.isSelected()) {
            bebida = "Tissana Caliente";
            precio = obtenerPrecioPlatillosBD(bebida);
            int cantidad =  Integer.parseInt(cajaTextoTissanaCaliente.getText());
            precio = precio * cantidad;
            Platillo aplatillo= new Platillo(bebida, cantidad, precio, mesa);
            insertarOrdenNuevoPlatillo(aplatillo);
        }
                
        obtenerOrdenActual();
        uncheckBox();
        menuBebidasAd.dispose();
    }//GEN-LAST:event_botonBebidasAdOkActionPerformed

    private void botonTisanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTisanaActionPerformed
        menuBebidasAd.setVisible(true);
        menuBebidasAd.setLocationRelativeTo(null);
    }//GEN-LAST:event_botonTisanaActionPerformed

    private void botonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresarActionPerformed
        dispose();
    }//GEN-LAST:event_botonRegresarActionPerformed

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
            java.util.logging.Logger.getLogger(Ventana_NuevaOrden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana_NuevaOrden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana_NuevaOrden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana_NuevaOrden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana_NuevaOrden().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog MesaDialog;
    private javax.swing.JButton botonABack;
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonAceptarA;
    private javax.swing.JButton botonAceptarCh;
    private javax.swing.JButton botonAceptarCh1;
    private javax.swing.JButton botonAceptarE;
    private javax.swing.JButton botonAceptarH;
    private javax.swing.JButton botonAguaHorchata;
    private javax.swing.JButton botonAguasOk;
    private javax.swing.JButton botonAlambres;
    private javax.swing.JButton botonBebidasAdOk;
    private javax.swing.JButton botonCarnesAsadas;
    private javax.swing.JButton botonCarnesOk;
    private javax.swing.JButton botonCerveza;
    private javax.swing.JButton botonCervezaOk;
    private javax.swing.JButton botonChBack;
    private javax.swing.JButton botonChBack1;
    private javax.swing.JButton botonChilaquiles;
    private javax.swing.JButton botonEBack;
    private javax.swing.JButton botonEnsaladas;
    private javax.swing.JButton botonHBack;
    private javax.swing.JButton botonHamburguesas;
    private javax.swing.JButton botonNuevaMesa;
    private javax.swing.JButton botonQuesadillas;
    private javax.swing.JButton botonQuesadillasOk;
    private javax.swing.JButton botonRefrescoOk;
    private javax.swing.JButton botonRefrescos;
    private javax.swing.JButton botonRegresar;
    private javax.swing.JButton botonTacos;
    private javax.swing.JButton botonTacosOk;
    private javax.swing.JButton botonTisana;
    private javax.swing.JTextField cajaCantPersonas;
    private javax.swing.JTextField cajaNumMesa;
    private javax.swing.JTextField cajaTextoArrachera;
    private javax.swing.JTextField cajaTextoBistek;
    private javax.swing.JTextField cajaTextoCafeAmericano;
    private javax.swing.JTextField cajaTextoCafeAmericanoRef;
    private javax.swing.JTextField cajaTextoCoca;
    private javax.swing.JTextField cajaTextoCocaLight;
    private javax.swing.JTextField cajaTextoCorona;
    private javax.swing.JTextField cajaTextoCubeta;
    private javax.swing.JTextField cajaTextoCucapa;
    private javax.swing.JTextField cajaTextoFantaN;
    private javax.swing.JTextField cajaTextoFantaR;
    private javax.swing.JTextField cajaTextoFresca;
    private javax.swing.JTextField cajaTextoHorchata;
    private javax.swing.JTextField cajaTextoLimon;
    private javax.swing.JTextField cajaTextoManzanita;
    private javax.swing.JTextField cajaTextoMineral;
    private javax.swing.JTextField cajaTextoModeloAmbar;
    private javax.swing.JTextField cajaTextoModeloEspecial;
    private javax.swing.JTextField cajaTextoModeloNegra;
    private javax.swing.JTextField cajaTextoMuslo;
    private javax.swing.JTextField cajaTextoNatural;
    private javax.swing.JTextField cajaTextoPechuga;
    private javax.swing.JTextField cajaTextoQuesadillaAdobada;
    private javax.swing.JTextField cajaTextoQuesadillaCamarones;
    private javax.swing.JTextField cajaTextoQuesadillaChorizo;
    private javax.swing.JTextField cajaTextoQuesadillaPollo;
    private javax.swing.JTextField cajaTextoQuesadillaPuerco;
    private javax.swing.JTextField cajaTextoQuesadillaRes;
    private javax.swing.JTextField cajaTextoQuesadillaTofu;
    private javax.swing.JTextField cajaTextoSprite;
    private javax.swing.JTextField cajaTextoTacoAdobada;
    private javax.swing.JTextField cajaTextoTacoCamarones;
    private javax.swing.JTextField cajaTextoTacoChorizo;
    private javax.swing.JTextField cajaTextoTacoPollo;
    private javax.swing.JTextField cajaTextoTacoPuerco;
    private javax.swing.JTextField cajaTextoTacoRes;
    private javax.swing.JTextField cajaTextoTacoTofu;
    private javax.swing.JTextField cajaTextoTe;
    private javax.swing.JTextField cajaTextoTissanaCaliente;
    private javax.swing.JTextField cajaTextoTissanaFria;
    private javax.swing.JTextField cajaTextoUltra;
    private javax.swing.JTextField cajaTextoVasoChelado;
    private javax.swing.JTextField cajaTextoVasoGringa;
    private javax.swing.JTextField cajaTextoVasoMichelado;
    private javax.swing.JTextField cajaTextoVictoria;
    private javax.swing.JCheckBox checkBoxAAdobada;
    private javax.swing.JCheckBox checkBoxACamarones;
    private javax.swing.JCheckBox checkBoxACebolla;
    private javax.swing.JCheckBox checkBoxACebolla1;
    private javax.swing.JCheckBox checkBoxAChampiñon;
    private javax.swing.JCheckBox checkBoxAChampiñon1;
    private javax.swing.JCheckBox checkBoxAChileGuero;
    private javax.swing.JCheckBox checkBoxAChileGuero1;
    private javax.swing.JCheckBox checkBoxAChileSerrano;
    private javax.swing.JCheckBox checkBoxAChileSerrano1;
    private javax.swing.JCheckBox checkBoxAChorizo;
    private javax.swing.JCheckBox checkBoxAElote;
    private javax.swing.JCheckBox checkBoxAElote1;
    private javax.swing.JCheckBox checkBoxAJitomate;
    private javax.swing.JCheckBox checkBoxAJitomate1;
    private javax.swing.JCheckBox checkBoxANopales;
    private javax.swing.JCheckBox checkBoxANopales1;
    private javax.swing.JCheckBox checkBoxAPimientos;
    private javax.swing.JCheckBox checkBoxAPimientos1;
    private javax.swing.JCheckBox checkBoxAPiña;
    private javax.swing.JCheckBox checkBoxAPiña1;
    private javax.swing.JCheckBox checkBoxAPoblano;
    private javax.swing.JCheckBox checkBoxAPoblano1;
    private javax.swing.JCheckBox checkBoxAPollo;
    private javax.swing.JCheckBox checkBoxAPuerco;
    private javax.swing.JCheckBox checkBoxARes;
    private javax.swing.JCheckBox checkBoxASalchichon;
    private javax.swing.JCheckBox checkBoxATocino;
    private javax.swing.JCheckBox checkBoxATofu;
    private javax.swing.JCheckBox checkBoxArrachera;
    private javax.swing.JCheckBox checkBoxBistek;
    private javax.swing.JCheckBox checkBoxCafeAmericano;
    private javax.swing.JCheckBox checkBoxCafeAmericanoRef;
    private javax.swing.JCheckBox checkBoxCoca;
    private javax.swing.JCheckBox checkBoxCocaLight;
    private javax.swing.JCheckBox checkBoxCorona;
    private javax.swing.JCheckBox checkBoxCubeta;
    private javax.swing.JCheckBox checkBoxCucapa;
    private javax.swing.JCheckBox checkBoxEAdobada;
    private javax.swing.JCheckBox checkBoxECamarones;
    private javax.swing.JCheckBox checkBoxECebolla;
    private javax.swing.JCheckBox checkBoxEChampiñon;
    private javax.swing.JCheckBox checkBoxEChileGuero;
    private javax.swing.JCheckBox checkBoxECrotones;
    private javax.swing.JCheckBox checkBoxEElote;
    private javax.swing.JCheckBox checkBoxEJitomate;
    private javax.swing.JCheckBox checkBoxEMixSemillas;
    private javax.swing.JCheckBox checkBoxENopales;
    private javax.swing.JCheckBox checkBoxEPimientos;
    private javax.swing.JCheckBox checkBoxEPiña;
    private javax.swing.JCheckBox checkBoxEPoblano;
    private javax.swing.JCheckBox checkBoxEPollo;
    private javax.swing.JCheckBox checkBoxEPuerco;
    private javax.swing.JCheckBox checkBoxERes;
    private javax.swing.JCheckBox checkBoxETofu;
    private javax.swing.JCheckBox checkBoxETortilla;
    private javax.swing.JCheckBox checkBoxEZanahoria;
    private javax.swing.JCheckBox checkBoxFantaN;
    private javax.swing.JCheckBox checkBoxFantaR;
    private javax.swing.JCheckBox checkBoxFresca;
    private javax.swing.JCheckBox checkBoxHAguacate;
    private javax.swing.JCheckBox checkBoxHAsadero;
    private javax.swing.JCheckBox checkBoxHCebolla;
    private javax.swing.JCheckBox checkBoxHChampiñones;
    private javax.swing.JCheckBox checkBoxHChileGuero;
    private javax.swing.JCheckBox checkBoxHJitomate;
    private javax.swing.JCheckBox checkBoxHLechuga;
    private javax.swing.JCheckBox checkBoxHManchego;
    private javax.swing.JCheckBox checkBoxHPiña;
    private javax.swing.JCheckBox checkBoxHTocino;
    private javax.swing.JCheckBox checkBoxHorchata;
    private javax.swing.JCheckBox checkBoxLimon;
    private javax.swing.JCheckBox checkBoxManzanita;
    private javax.swing.JCheckBox checkBoxMineral;
    private javax.swing.JCheckBox checkBoxModeloAmbar;
    private javax.swing.JCheckBox checkBoxModeloEspecial;
    private javax.swing.JCheckBox checkBoxModeloNegra;
    private javax.swing.JCheckBox checkBoxMuslo;
    private javax.swing.JCheckBox checkBoxNatural;
    private javax.swing.JCheckBox checkBoxPechuga;
    private javax.swing.JCheckBox checkBoxQuesadillasAdobada;
    private javax.swing.JCheckBox checkBoxQuesadillasCamarones;
    private javax.swing.JCheckBox checkBoxQuesadillasChorizo;
    private javax.swing.JCheckBox checkBoxQuesadillasPollo;
    private javax.swing.JCheckBox checkBoxQuesadillasPuerco;
    private javax.swing.JCheckBox checkBoxQuesadillasRes;
    private javax.swing.JCheckBox checkBoxQuesadillasTofu;
    private javax.swing.JCheckBox checkBoxSprite;
    private javax.swing.JCheckBox checkBoxTacoAdobada;
    private javax.swing.JCheckBox checkBoxTacoCamarones;
    private javax.swing.JCheckBox checkBoxTacoChorizo;
    private javax.swing.JCheckBox checkBoxTacoPollo;
    private javax.swing.JCheckBox checkBoxTacoPuerco;
    private javax.swing.JCheckBox checkBoxTacoRes;
    private javax.swing.JCheckBox checkBoxTacoTofu;
    private javax.swing.JCheckBox checkBoxTe;
    private javax.swing.JCheckBox checkBoxTissanaFria;
    private javax.swing.JCheckBox checkBoxTissannaCaliente;
    private javax.swing.JCheckBox checkBoxUltra;
    private javax.swing.JCheckBox checkBoxVasoChelado;
    private javax.swing.JCheckBox checkBoxVasoGringa;
    private javax.swing.JCheckBox checkBoxVasoMichelado;
    private javax.swing.JCheckBox checkBoxVictoria;
    private javax.swing.JLabel etiquetaNumMesa;
    private javax.swing.JLabel etiquetaNumMesa1;
    private javax.swing.ButtonGroup grupoBotonACarneFria;
    private javax.swing.ButtonGroup grupoBotonEAderezo;
    private javax.swing.ButtonGroup grupoBotonProteinaCh;
    private javax.swing.ButtonGroup grupoBotonProteinaH;
    private javax.swing.ButtonGroup grupoBotonQuesoA;
    private javax.swing.ButtonGroup grupoBotonSalsasCh;
    private javax.swing.ButtonGroup grupoBotonTamañoA;
    private javax.swing.ButtonGroup grupoBotonTortillasA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JDialog menuAguas;
    private javax.swing.JDialog menuBebidasAd;
    private javax.swing.JDialog menuCarnesAsadas;
    private javax.swing.JDialog menuCerveza;
    private javax.swing.JDialog menuQuesadillas;
    private javax.swing.JDialog menuRefrescos;
    private javax.swing.JDialog menuTacos;
    private javax.swing.JPanel panelAguas;
    private javax.swing.JPanel panelAlambres;
    private javax.swing.JPanel panelBebidasAd;
    private javax.swing.JPanel panelCarnes;
    private javax.swing.JPanel panelCerveza;
    private javax.swing.JPanel panelChilaquiles;
    private javax.swing.JPanel panelChilaquiles1;
    private javax.swing.JPanel panelEnsaladas;
    private javax.swing.JPanel panelHamburguesas;
    private javax.swing.JPanel panelMaster;
    private javax.swing.JPanel panelQuesadillas;
    private javax.swing.JPanel panelRefrescos;
    private javax.swing.JPanel panelTacos;
    private javax.swing.JRadioButton rBotonAChico;
    private javax.swing.JRadioButton rBotonAChorizo;
    private javax.swing.JRadioButton rBotonAGrande;
    private javax.swing.JRadioButton rBotonAHarina;
    private javax.swing.JRadioButton rBotonAMaiz;
    private javax.swing.JRadioButton rBotonAMediano;
    private javax.swing.JRadioButton rBotonAQueso;
    private javax.swing.JRadioButton rBotonASQueso;
    private javax.swing.JRadioButton rBotonASalchichon;
    private javax.swing.JRadioButton rBotonATocino;
    private javax.swing.JRadioButton rBotonChAdobada;
    private javax.swing.JRadioButton rBotonChAdobada1;
    private javax.swing.JRadioButton rBotonChPollo;
    private javax.swing.JRadioButton rBotonChPollo1;
    private javax.swing.JRadioButton rBotonChPuerco;
    private javax.swing.JRadioButton rBotonChPuerco1;
    private javax.swing.JRadioButton rBotonChRes;
    private javax.swing.JRadioButton rBotonChRes1;
    private javax.swing.JRadioButton rBotonChSencillos;
    private javax.swing.JRadioButton rBotonChSencillos1;
    private javax.swing.JRadioButton rBotonChTofu;
    private javax.swing.JRadioButton rBotonChTofu1;
    private javax.swing.JRadioButton rBotonEBalsamico;
    private javax.swing.JRadioButton rBotonEChipotle;
    private javax.swing.JRadioButton rBotonEMostazaMiel;
    private javax.swing.JRadioButton rBotonHDRes;
    private javax.swing.JRadioButton rBotonHRes;
    private javax.swing.JRadioButton rBotonHVegetariana;
    private javax.swing.JRadioButton rBotonSalsaRoja;
    private javax.swing.JRadioButton rBotonSalsaRoja1;
    private javax.swing.JRadioButton rBotonSalsaVerde;
    private javax.swing.JRadioButton rBotonSalsaVerde1;
    private javax.swing.JDialog subMenu;
    private javax.swing.JTable tablaOrden;
    // End of variables declaration//GEN-END:variables
}
