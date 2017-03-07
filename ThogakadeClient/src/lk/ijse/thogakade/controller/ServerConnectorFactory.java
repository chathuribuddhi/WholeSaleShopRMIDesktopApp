/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controller;

import com.mysql.jdbc.util.ServerController;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import lk.ijse.thogakade.service.ServiceFactory;
import lk.ijse.thogakade.service.SuperService;
import lk.ijse.thogakade.service.custom.CustomerService;
import lk.ijse.thogakade.service.custom.EmployeeService;
import lk.ijse.thogakade.service.custom.ItemService;
import lk.ijse.thogakade.service.custom.OrderDetailService;
import lk.ijse.thogakade.service.custom.OrderService;

/**
 *
 * @author Pramod Dilshan
 */
public class ServerConnectorFactory {
    private static ServerConnectorFactory serverConnector;
    private static ServiceFactory serviceFactory;
    
    private static CustomerService customerService;
    private static ItemService itemService;
    private static OrderDetailService orderDetailService;
    private static OrderService orderService;
    private static EmployeeService employeeService;
    

    private  ServerConnectorFactory() throws NotBoundException, MalformedURLException, RemoteException {
        serviceFactory = (ServiceFactory) Naming.lookup("rmi://localhost:5051/ThogakadeServer");
        customerService = (CustomerService) serviceFactory.getService(ServiceFactory.ServiceTypes.CUSTOMER);
        itemService = (ItemService) serviceFactory.getService(ServiceFactory.ServiceTypes.ITEM);
        orderDetailService = (OrderDetailService) serviceFactory.getService(ServiceFactory.ServiceTypes.ORDERDETAIL);
        orderService = (OrderService) serviceFactory.getService(ServiceFactory.ServiceTypes.ORDER);
        employeeService = (EmployeeService) serviceFactory.getService(ServiceFactory.ServiceTypes.EMPLOYEE);
    }
    
    public static ServerConnectorFactory getInstance() throws NotBoundException, MalformedURLException, RemoteException{
        if (serverConnector==null) {
            serverConnector = new ServerConnectorFactory();
        }
        return serverConnector;
    
    }
    
    public SuperService getService(ServiceFactory.ServiceTypes serviceType){
        switch(serviceType){
            case CUSTOMER:
                return customerService;
            case ORDER:
                return orderService;
            case ITEM:
                return itemService;
            case ORDERDETAIL:
                return orderDetailService;
            case EMPLOYEE:
                return employeeService;
            default:
                return null;
        }
    }
    
   
}
