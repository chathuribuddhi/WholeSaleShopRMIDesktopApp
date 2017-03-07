/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.views;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lk.ijse.thogakade.controller.ControllerFactory;
import lk.ijse.thogakade.controller.custom.CustomerController;
import lk.ijse.thogakade.controller.custom.ItemController;
import lk.ijse.thogakade.controller.custom.OrderDetailController;
import lk.ijse.thogakade.controller.custom.PlaceOrderController;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.dto.OrderDTO;
import lk.ijse.thogakade.dto.OrderDetailDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;


/**
 *
 * @author linux
 */
public class PlaceOrderForm extends javax.swing.JFrame {

    /**
     * Creates new form PlaceOrderForm
     */
    private CustomerController ctrlCustomer;
    private ItemController ctrlItem;
    private OrderDetailController ctrlOrderDetail;
    private PlaceOrderController ctrlOrder;
    
    
    public PlaceOrderForm() {
        initComponents();
        //setIconImage(image);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(new Date());
        txtDate.setText(formattedDate);
        
        
        try {
            ControllerFactory.getInstance().getRegisterd(this);
            ctrlOrder = (PlaceOrderController) ControllerFactory.getInstance().getController(ControllerFactory.ControllerTypes.PLACE_ORDER);
            
            ctrlCustomer = (CustomerController) ControllerFactory.getInstance().getController(ControllerFactory.ControllerTypes.CUSTOMER);
            //ctrlCustomer.registerObserver(this);
        } catch (NotBoundException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ctrlItem = (ItemController) ControllerFactory.getInstance().getController(ControllerFactory.ControllerTypes.ITEM);
        } catch (NotBoundException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ctrlOrderDetail = (OrderDetailController) ControllerFactory.getInstance().getController(ControllerFactory.ControllerTypes.ORDERDETAIL);
        } catch (NotBoundException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        loadDataIntoCombos();
        setOrderId();
        
    }
    
    private void setOrderId(){
        try {
            int a = (ctrlOrder.getAll().size()) + 1;
            System.out.println("D00"+a);
            if(a<10){
                txtOrderId.setText("D00"+a);
            }else if(a<100){
                txtOrderId.setText("D0"+a);
            }else{
                txtOrderId.setText("D"+a);
            }
            
//        try {
//            String orderId = ctrlOrder.getOrderId();
//            txtOrderId.setText(orderId);
//        } catch (RemoteException ex) {
//            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (Exception ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    private String formattedPrice(String price) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf.format(Double.parseDouble(price));
    }
    
    private void loadDataIntoCombos() {
        
        ArrayList<CustomerDTO> alCustomer;
        ArrayList<ItemDTO> alItem;
        
        try {
            alCustomer=ctrlCustomer.getAll();
            cmbCustomer.removeAllItems();
            for(CustomerDTO cdto:alCustomer){
                cmbCustomer.addItem(cdto.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
        try {
            alItem=ctrlItem.getAll();
            for(ItemDTO idto:alItem){
                cmbCode.addItem(idto.getCode());
            }
        } catch (Exception ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    
    private Date parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void calculateTotalAmount() {
        DefaultTableModel dtm = (DefaultTableModel) tblItems.getModel();

        double total = 0;

        for (int i = 0; i < dtm.getRowCount(); i++) {
            total += Double.parseDouble((String) dtm.getValueAt(i, 4));
        }
        txtAmount.setText(formattedPrice(String.valueOf(total)));

    }
    
    private void calculateBalance(){
        //txtChange.setText(Double.parseDouble((txtCash.getText()) - Double.parseDouble(txtAmount.toString())));
        Double cash =Double.parseDouble(txtCash.getText());
        Double total = Double.parseDouble(txtAmount.getText());
        txtChange.setText(String.valueOf(cash - total));
    }
    
    private void calQtyOnHand(){
        DefaultTableModel dtm = (DefaultTableModel) tblItems.getModel();
        for (int i = 0; i < dtm.getRowCount(); i++) {
            try {
                int qtyOld = ctrlItem.get(dtm.getValueAt(i, 0).toString()).getQtyOnHand();
                int qtyMinus = Integer.parseInt(dtm.getValueAt(i, 3).toString());
                int qtyNew = qtyOld - qtyMinus;
                ItemDTO itemDTO = new ItemDTO(
                dtm.getValueAt(i, 0).toString(), 
                dtm.getValueAt(i, 1).toString(), 
                Double.parseDouble(dtm.getValueAt(i, 2).toString()), 
                qtyNew
                );
                
                ctrlItem.update(itemDTO);
                
            } catch (Exception ex) {
                Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtUnitPrice = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtQtyOnHand = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cmbCode = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        cmbCustomer = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtOrderId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        removeButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtCash = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtChange = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(102, 0, 102));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)), "Item Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DejaVu Serif", 1, 14), new java.awt.Color(0, 204, 204))); // NOI18N

        jLabel6.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        jLabel6.setText("Code : ");

        jLabel7.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        jLabel7.setText("Description : ");

        txtDescription.setEditable(false);
        txtDescription.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        jLabel8.setText("Unit Price : ");

        txtUnitPrice.setEditable(false);
        txtUnitPrice.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        jLabel9.setText("Qty On Hand : ");

        txtQtyOnHand.setEditable(false);
        txtQtyOnHand.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N

        txtQty.setFont(new java.awt.Font("DejaVu Serif", 0, 14)); // NOI18N
        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        jLabel10.setText("Qty : ");

        cmbCode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCodeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(59, 59, 59)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUnitPrice)
                    .addComponent(cmbCode, 0, 197, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescription))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQtyOnHand)
                            .addComponent(txtQty))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtQtyOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)), "Customer Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DejaVu Serif", 1, 14), new java.awt.Color(0, 204, 204))); // NOI18N

        jLabel4.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        jLabel4.setText("ID : ");

        jLabel5.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        jLabel5.setText("Name : ");

        txtCustomerName.setEditable(false);
        txtCustomerName.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N

        cmbCustomer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCustomerItemStateChanged(evt);
            }
        });
        cmbCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184)
                .addComponent(jLabel5)
                .addGap(38, 38, 38)
                .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("DejaVu Serif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Place Order");

        txtOrderId.setEditable(false);
        txtOrderId.setFont(new java.awt.Font("DejaVu Serif", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        jLabel2.setText("Order ID : ");

        jLabel3.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        jLabel3.setText("Date : ");

        txtDate.setEditable(false);
        txtDate.setFont(new java.awt.Font("DejaVu Serif", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(44, 44, 44)
                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        removeButton.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        addButton.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        tblItems.setFont(new java.awt.Font("DejaVu Serif", 0, 12)); // NOI18N
        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Description", "Unit Price", "Qty", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblItems);

        jLabel11.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        jLabel11.setText("Total Amount : ");

        txtAmount.setEditable(false);
        txtAmount.setFont(new java.awt.Font("DejaVu Serif", 1, 16)); // NOI18N
        txtAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAmount.setText("0.0");

        saveButton.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Cash");

        txtCash.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Change");

        txtChange.setEditable(false);
        txtChange.setFont(new java.awt.Font("DejaVu Serif", 1, 16)); // NOI18N
        txtChange.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtChange.setText("0.0");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Confirm Order");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("DejaVu Serif", 1, 14)); // NOI18N
        cancelButton.setText("Exit");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removeButton))
            .addComponent(jScrollPane1)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtChange, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeButton)
                    .addComponent(addButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtChange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 129, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed

    }//GEN-LAST:event_txtQtyActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
            
        if (cmbCode.getSelectedIndex() != -1) {

            int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
            if (txtQty.getText().trim().isEmpty()) {
                return;
            }
            int qty = Integer.parseInt(txtQty.getText());

            DefaultTableModel dtm = (DefaultTableModel) tblItems.getModel();

            for (int i = 0; i < dtm.getRowCount(); i++) {
                if (dtm.getValueAt(i, 0).toString().equals(cmbCode.getSelectedItem().toString())) {
                    JOptionPane.showMessageDialog(this, " Item has already been added.");
                    return;
                }
            }

            if (qty <= qtyOnHand && qty > 0) {

                //double amount = qty * Double.parseDouble(txtUnitPrice.getText());

                Object[] rowData = {
                    cmbCode.getSelectedItem().toString(),
                    txtDescription.getText(),
                    txtUnitPrice.getText(),
                    txtQty.getText(),
                    String.valueOf(qty * Double.parseDouble(txtUnitPrice.getText()))
                };

                dtm.addRow(rowData);

            }
        }

        calculateTotalAmount();

    }//GEN-LAST:event_addButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tblItems.getModel();
        if (tblItems.getSelectedRow() != -1) {
            dtm.removeRow(tblItems.getSelectedRow());
        }
        calculateTotalAmount();
    }//GEN-LAST:event_removeButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tblItems.getModel();
        
            OrderDTO order = new OrderDTO(
                    txtOrderId.getText(), 
                    cmbCustomer.getSelectedItem().toString(), 
                    parseDate(txtDate.getText()));
                
            try {   
                boolean result = ctrlOrder.add(order);
                
            } catch (Exception ex) {
                Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        for (int i = 0; i < dtm.getRowCount(); i++) {
            OrderDetailDTO orderDetail = new OrderDetailDTO(
                    txtOrderId.getText(), 
                    (String) dtm.getValueAt(i, 0), 
                    Integer.parseInt((String) dtm.getValueAt(i, 3)), 
                    Double.parseDouble((String) dtm.getValueAt(i, 2)));

            try {
                boolean result = ctrlOrderDetail.add(orderDetail);
            } catch (Exception ex) {
                Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        calQtyOnHand();
        calculateBalance();


        
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cmbCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCustomerActionPerformed

    private void cmbCustomerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCustomerItemStateChanged
       if(cmbCustomer.getSelectedIndex()!=-1){
            try {
                CustomerDTO customerDTO=new CustomerDTO(cmbCustomer.getSelectedItem().toString());
                customerDTO = (CustomerDTO) ctrlCustomer.get(customerDTO.getId());
                txtCustomerName.setText(customerDTO.getName());
            } catch (Exception ex) {
                Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cmbCustomerItemStateChanged

    private void cmbCodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCodeItemStateChanged
        if(cmbCode.getSelectedIndex()!=-1){
            try {
                ItemDTO itemDTO=new ItemDTO((String) cmbCode.getSelectedItem());
                itemDTO=(ItemDTO) ctrlItem.get(itemDTO.getCode());
                txtDescription.setText(itemDTO.getDescription());
                txtQtyOnHand.setText(""+itemDTO.getQtyOnHand());
                txtUnitPrice.setText(""+itemDTO.getUnitPrice());
                
            } catch (Exception ex) {
                Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cmbCodeItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
//            JasperReport MainReport = (JasperReport) JRLoader.loadObject(PlaceOrderForm.class.getResourceAsStream("/lk/ijse/testjasper1/reports/bill.jasper"));
//            JasperReport SubReport = (JasperReport) JRLoader.loadObject(PlaceOrderForm.class.getResourceAsStream("/lk/ijse/testjasper1/reports/Sub.jasper"));
//            HashMap<String, Object> params = new HashMap<>();
//            params.put("Sub", SubReport);
//            params.put("total", "rbrt");
//            JasperPrint filledReport = JasperFillManager.fillReport(MainReport, params, new JREmptyDataSource());
//            JasperReportViewer myViewer = new JasperReportViewer(filledReport);
//            myViewer.setVisible(true);
                JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk\\ijse\\thogakade\\reports\\bill2.jasper"));
                HashMap<String, Object> params = new HashMap<>();
                params.put("total", txtAmount.getText());
                params.put("Cash", txtCash.getText());
                params.put("Balance", txtChange.getText());
                params.put("OrderId", cmbCustomer.getSelectedItem());

                JasperPrint filledReport = JasperFillManager.fillReport(compiledReport, params, new JRTableModelDataSource(tblItems.getModel()));
                JasperReportViewer myViewer = new JasperReportViewer(filledReport);
                myViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(PlaceOrderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

   
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

       

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PlaceOrderForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> cmbCode;
    private javax.swing.JComboBox<String> cmbCustomer;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtCash;
    private javax.swing.JTextField txtChange;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtOrderId;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtQtyOnHand;
    private javax.swing.JTextField txtUnitPrice;
    // End of variables declaration//GEN-END:variables

}