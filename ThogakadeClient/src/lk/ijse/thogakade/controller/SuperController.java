/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import lk.ijse.thogakade.observers.Observer;

/**
 *
 * @author student
 */
public interface SuperController<T> extends Observer{
    
    public boolean add(T t) throws Exception;
    public boolean update(T t) throws Exception;
    public boolean delete(T t) throws Exception;
    public T get(String id) throws Exception;
    public ArrayList<T> getAll() throws Exception;
    public void registerObserver(Observer observer) throws RemoteException;
    public void unregisterObserver(Observer observer) throws RemoteException;
    public boolean reserve(String id) throws RemoteException;

    public boolean release(String id) throws RemoteException;
    
}
