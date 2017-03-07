/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.service;

import java.rmi.Remote;
import java.util.ArrayList;
import lk.ijse.thogakade.observers.Subject;
import lk.ijse.thogakade.reservation.Reservation;

/**
 *
 * @author student
 */
public interface SuperService<T> extends Remote, Subject, Reservation{
    public boolean add(T t) throws Exception;

    public boolean update(T t) throws Exception;

    public boolean delete(T t) throws Exception;

    public T get(String id) throws Exception;

    public ArrayList<T> getAll() throws Exception;
}
