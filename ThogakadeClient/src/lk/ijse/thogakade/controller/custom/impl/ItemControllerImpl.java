/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controller.custom.impl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import lk.ijse.thogakade.controller.ServerConnectorFactory;
import lk.ijse.thogakade.controller.SuperController;
import lk.ijse.thogakade.controller.custom.ItemController;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.observers.Observer;
import lk.ijse.thogakade.service.ServiceFactory;
import lk.ijse.thogakade.service.custom.ItemService;

/**
 *
 * @author CHATHURI
 */
public class ItemControllerImpl extends UnicastRemoteObject implements ItemController{
    
    private ItemService itemService;

    public ItemControllerImpl() throws NotBoundException, MalformedURLException, RemoteException {
        itemService = (ItemService) ServerConnectorFactory.getInstance().getService(ServiceFactory.ServiceTypes.ITEM);
        
    }
    
    

    @Override
    public boolean add(ItemDTO t) throws Exception {
        boolean result = itemService.add(t);
        return result;
    }

    @Override
    public boolean update(ItemDTO t) throws Exception {
        boolean result = itemService.update(t);
        return result;
    }

    @Override
    public boolean delete(ItemDTO t) throws Exception {
        boolean result = itemService.delete(t);
        return result;
    }

    @Override
    public ItemDTO get(String code) throws Exception {
        ItemDTO item = itemService.get(code);
        return  item;
    }

    @Override
    public ArrayList<ItemDTO> getAll() throws Exception {
        ArrayList<ItemDTO> item = itemService.getAll();
                return item;
    }

    @Override
    public void registerObserver(Observer observer) throws RemoteException {
        itemService.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws RemoteException {
            itemService.unregisterObserver(observer);
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
