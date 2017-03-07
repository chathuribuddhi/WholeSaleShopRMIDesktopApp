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
import lk.ijse.thogakade.controller.custom.CustomerController;
import lk.ijse.thogakade.controller.custom.EmployeeController;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.dto.EmployeeDTO;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.dto.SuperDTO;
import lk.ijse.thogakade.observers.Observer;
import lk.ijse.thogakade.service.ServiceFactory;
import lk.ijse.thogakade.service.custom.CustomerService;
import lk.ijse.thogakade.service.custom.EmployeeService;

/**
 *
 * @author CHATHURI
 */
public class EmployeeControllerImpl implements EmployeeController{

    private EmployeeService employeeService;
    
    public EmployeeControllerImpl() throws NotBoundException, MalformedURLException, RemoteException {
        employeeService = (EmployeeService) ServerConnectorFactory.getInstance().getService(ServiceFactory.ServiceTypes.EMPLOYEE);
    }
    
    @Override
    public boolean add(EmployeeDTO t) throws Exception {
        boolean result = employeeService.add(t);
        return result;
    }

    @Override
    public boolean update(EmployeeDTO t) throws Exception {
        boolean result = employeeService.update(t);
        return result;
    }

    @Override
    public boolean delete(EmployeeDTO t) throws Exception {
        boolean result = employeeService.delete(t);
        return result;
    }



    @Override
    public void registerObserver(Observer observer) throws RemoteException {
        employeeService.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws RemoteException {
        employeeService.unregisterObserver(observer);
    }

    @Override
    public boolean reserve(String id) throws RemoteException {
        System.out.println("controller reverse");
        return employeeService.reserve(id, employeeService);
    }

    @Override
    public boolean release(String id) throws RemoteException {
        return employeeService.release(id);
    }

    @Override
    public void update() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<EmployeeDTO> getAll() throws Exception {
        System.out.println("ControllerGettAll");
        ArrayList<EmployeeDTO> employee = employeeService.getAll();
        return employee;
    }

    public SuperDTO get(EmployeeDTO t) throws Exception {
        return employeeService.get(t.getId());
    }

    @Override
    public EmployeeDTO get(String id) throws Exception {
        System.out.println("controller");
        EmployeeDTO employee = employeeService.get(id);
        return  employee;
    }
}
