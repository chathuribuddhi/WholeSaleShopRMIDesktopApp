/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controller.custom;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import lk.ijse.thogakade.controller.SuperController;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.dto.OrderDTO;
import lk.ijse.thogakade.dto.OrderDetailDTO;
import lk.ijse.thogakade.dto.SuperDTO;

/**
 *
 * @author student
 */
public interface PlaceOrderController extends SuperController<OrderDTO>{
    public String getOrderId() throws RemoteException, SQLException;
}
