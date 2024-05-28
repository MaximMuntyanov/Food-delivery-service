package org.example;

public class menu
{
        private int position_id;
        private int restaurants_id;
        private int positionTypes_id;
        private double price;
        private String foodName;

        public menu(int positionId, int restaurantsId, int positionTypesId, double price, String foodName) {
            this.position_id = positionId;
            this.restaurants_id = restaurantsId;
            this.positionTypes_id = positionTypesId;
            this.price = price;
            this.foodName = foodName;
        }

        public int getPositionId() {
            return position_id;
        }

        public void setPositionId(int positionId) {
            this.position_id = positionId;
        }

        public int getRestaurantsId() {
            return restaurants_id;
        }

        public void setRestaurantsId(int restaurantsId) {
            this.restaurants_id = restaurantsId;
        }

        public int getPositionTypesId() {
            return positionTypes_id;
        }

        public void setPositionTypesId(int positionTypesId) {
            this.positionTypes_id = positionTypesId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }
}