package org.example;

public class ordered_food
{
    private int order_id;
    private int menu_position_id;
    private int count;


    public ordered_food(int orderId, int menuPositionId, int count) {
        this.order_id = orderId;
        this.menu_position_id = menuPositionId;
        this.count = count;
    }

    public int getOrderId() {
        return order_id;
    }

    public void setOrderId(int orderId) {
        this.order_id = orderId;
    }

    public int getMenuPositionId() {
        return menu_position_id;
    }

    public void setMenuPositionId(int menuPositionId) {
        this.menu_position_id = menuPositionId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
