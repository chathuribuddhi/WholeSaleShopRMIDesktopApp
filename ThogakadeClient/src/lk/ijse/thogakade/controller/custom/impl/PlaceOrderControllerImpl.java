/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controller.custom.impl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import lk.ijse.thogakade.controller.ServerConnectorFactory;
import lk.ijse.thogakade.controller.custom.PlaceOrderController;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.dto.OrderDTO;
import lk.ijse.thogakade.dto.OrderDetailDTO;
import lk.ijse.thogakade.dto.SuperDTO;
import lk.ijse.thogakade.observers.Observer;
import lk.ijse.thogakade.service.ServiceFactory;
import lk.ijse.thogakade.service.custom.CustomerService;
import lk.ijse.thogakade.service.custom.ItemService;
import lk.ijse.thogakade.service.custom.OrderService;
import lk.ijse.thogakade.views.PlaceOrderForm;

/**
 *
 * @author student
 */
public class PlaceOrderControllerImpl implements PlaceOrderController{
    
    private CustomerService customerService;
    private OrderService orderService;
    private ItemService itemService;
//    private PlaceOrderForm frame;
//
//    public PlaceOrderControllerImpl(JFrame frame) throws NotBoundException, MalformedURLException, RemoteException {
//        
//        this.frame=(PlaceOrderForm) frame;    
//    }

    public PlaceOrderControllerImpl() throws NotBoundException, MalformedURLException, RemoteException {
        customerService = (CustomerService) ServerConnectorFactory.getInstance().getService(ServiceFactory.ServiceTypes.CUSTOMER);
        orderService = (OrderService) ServerConnectorFactory.getInstance().getService(ServiceFactory.ServiceTypes.ORDER);
        itemService=(ItemService) ServerConnectorFactory.getInstance().getService(ServiceFactory.ServiceTypes.ITEM);
    }
    
    
    
    public String getOrderId() throws RemoteException, SQLException{
        
        OrderDTO dto=new OrderDTO();
        dto = orderService.get(dto);
        if(dto.getOrderId()==null){
            System.out.println("Order id is null");
        }
        return dto.getOrderId();
    }

    @Override
    public boolean add(OrderDTO t) throws Exception {
        boolean result = orderService.add(t);
        return result;    
    }

    @Override
    public boolean update(OrderDTO t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(OrderDTO t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderDTO get(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<OrderDTO> getAll() throws Exception {
        System.out.println("Come to the PlaceOrderController getAll");
        return orderService.getAll();
    }

    @Override
    public void registerObserver(Observer observer) throws RemoteException {
        orderService.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws RemoteException {
            orderService.unregisterObserver(observer);
    }

    @Override
    public void update() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public SuperDTO get(CustomerDTO t) throws Exception {
        return customerService.get(t.getId());
    }

    
    public SuperDTO get(ItemDTO t) throws Exception {
        return itemService.get(t.getCode());
    }

    @Override
    public boolean reserve(String id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean release(String id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
