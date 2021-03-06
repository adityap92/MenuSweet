package com.menusweet;

public class CartItem {

    Item baseItem;
    int quantity;
    String comments;

    public CartItem(Item baseItem, int quantity, String comments) {
        this.baseItem = baseItem;
        this.quantity = quantity < 0 ? 0 : quantity;
        this.comments = comments;
    }

    public void clear() {
        quantity = 0;
        comments = "";
    }


    public String toString() {
        StringBuilder retVal = new StringBuilder();

        retVal.append(baseItem.name);
        retVal.append("\t\t\t");
        retVal.append(quantity);
        retVal.append(" x $");
        retVal.append(baseItem.price / 100);
        retVal.append(".");
        retVal.append(baseItem.price % 100);
        retVal.append(" = $");
        retVal.append(baseItem.price * quantity / 100);
        retVal.append(".");
        retVal.append(baseItem.price * quantity % 100);

        return retVal.toString();
    }
    public Item getItem(){
    	return baseItem;
    }
    public int getQuantity(){
    	return quantity;
    }
    public String getComments(){
    	return comments;
    }
}
