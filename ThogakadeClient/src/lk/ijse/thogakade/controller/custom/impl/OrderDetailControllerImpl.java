/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controller.custom.impl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import lk.ijse.thogakade.controller.ServerConnectorFactory;
import lk.ijse.thogakade.controller.custom.OrderDetailController;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.dto.OrderDetailDTO;
import lk.ijse.thogakade.observers.Observer;
import lk.ijse.thogakade.service.ServiceFactory;
import lk.ijse.thogakade.service.custom.OrderDetailService;

/**
 *
 * @author CHATHURI
 */
public class OrderDetailControllerImpl implements OrderDetailController{
    
    private OrderDetailService orderDetailService;

    public OrderDetailControllerImpl() throws NotBoundException, MalformedURLException, RemoteException {
        orderDetailService = (OrderDetailService) ServerConnectorFactory.getInstance().getService(ServiceFactory.ServiceTypes.ORDERDETAIL);
    }
    
    

    @Override
    public boolean add(OrderDetailDTO t) throws Exception {
        boolean result = orderDetailService.add(t);
        return result;
    }

    @Override
    public boolean update(OrderDetailDTO t) throws Exception {
        boolean result = orderDetailService.update(t);
        return result;
    }

    @Override
    public boolean delete(OrderDetailDTO t) throws Exception {
        boolean result = orderDetailService.delete(t);
        return result;
    }

    @Override
    public OrderDetailDTO get(String id) throws Exception {
        OrderDetailDTO orderDetailDTO = orderDetailService.get(id);
        return  orderDetailDTO;
    }

    @Override
    public ArrayList<OrderDetailDTO> getAll() throws Exception {
        ArrayList<OrderDetailDTO> orderDetail = orderDetailService.getAll();
        return orderDetail;
    }

    @Override
    public void registerObserver(Observer observer) throws RemoteException {
        orderDetailService.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws RemoteException {
            orderDetailService.unregisterObserver(observer);
    }

    @Override
    public void update() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
