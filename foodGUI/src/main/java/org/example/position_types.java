package org.example;

public class position_types
{
    private int position_type_id;
    private String food_type;

    public position_types(int position_type_id, String food_type) {
        this.position_type_id = position_type_id;
        this.food_type = food_type;
    }

    public int getPosition_type_id() {
        return position_type_id;
    }

    public void setPosition_type_id(int position_type_id) {
        this.position_type_id = position_type_id;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }
}
