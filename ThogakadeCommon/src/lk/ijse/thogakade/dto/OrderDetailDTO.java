/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.dto;

/**
 *
 * @author CHATHURI
 */
public class OrderDetailDTO extends SuperDTO{
    
    private  String orderId;
    private String code;
    private int qty;
    private double unitePrice;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderId, String code, int qty, double unitePrice) {
        this.orderId = orderId;
        this.code = code;
        this.qty = qty;
        this.unitePrice = unitePrice;
    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * @return the unitePrice
     */
    public double getUnitePrice() {
        return unitePrice;
    }

    /**
     * @param unitePrice the unitePrice to set
     */
    public void setUnitePrice(double unitePrice) {
        this.unitePrice = unitePrice;
    }
    
    
    
}
