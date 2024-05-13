package model;

/**
 * Bills is a mask of the database table bill
 * @param orderId
 * @param clientId
 * @param productId
 * @param productQuantity
 * @param totalPrice
 */
public record Bills(int orderId, int clientId, int productId, int productQuantity, Double totalPrice) {
}