
package lk.ijse.thogakade.reservation;

import java.rmi.RemoteException;
import lk.ijse.thogakade.service.SuperService;


public interface Reservation {
    public boolean reserve(String customerId, SuperService service)throws RemoteException;
    
    public boolean release(String customerId)throws RemoteException;        
}
