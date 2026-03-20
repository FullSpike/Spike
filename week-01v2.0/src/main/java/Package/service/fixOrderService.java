package Package.service;

public interface fixOrderService {
    void addOrder(String number);

    void searchStudentOrder(String number);

    void delOrder(String number);

    void evaluationOrder(String number);

    void selectAllOrders();

    void selectProcessedOrders();

    void selectUnprocessedOrders();

    void updateOrderStatus();

    void delOrderById();
}
