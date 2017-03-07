/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controller.custom.impl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import lk.ijse.thogakade.controller.ServerConnectorFactory;
import lk.ijse.thogakade.controller.custom.CustomerController;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.observers.Observer;
import lk.ijse.thogakade.service.ServiceFactory;
import lk.ijse.thogakade.service.custom.CustomerService;

/**
 *
 * @author student
 */
public class CustomerControllerImpl implements CustomerController {

    private CustomerService customerService;

    public CustomerControllerImpl() throws NotBoundException, MalformedURLException, RemoteException {
        customerService = (CustomerService) ServerConnectorFactory.getInstance().getService(ServiceFactory.ServiceTypes.CUSTOMER);
    }

    @Override
    public boolean add(CustomerDTO t) throws Exception {
        boolean result = customerService.add(t);
        return result;
    }

    @Override
    public boolean update(CustomerDTO t) throws Exception {
        boolean result = customerService.update(t);
        return result;
    }

    @Override
    public boolean delete(CustomerDTO t) throws Exception {
        boolean result = customerService.delete(t);
        return result;
    }

    @Override
    public CustomerDTO get(String id) throws Exception {
        CustomerDTO customer = customerService.get(id);
        return  customer;
    }

    @Override
    public ArrayList<CustomerDTO> getAll() throws Exception {
        System.out.println("Controller");
                ArrayList<CustomerDTO> customer = customerService.getAll();
                return customer;
        
    }

    @Override
    public void registerObserver(Observer observer) throws RemoteException {
        customerService.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws RemoteException {
            customerService.unregisterObserver(observer);
    }

    @Override
    public void update() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean reserve(String id) throws RemoteException {
        System.out.println("controller reverse");
        return customerService.reserve(id, customerService);
    }

    @Override
    public boolean release(String id) throws RemoteException {
        return customerService.release(id);
    }

}
