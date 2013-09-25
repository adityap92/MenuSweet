package com.menusweet;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

public class UserCart {

    ArrayList<CartItem> cartItems;
    int totalPrice, finalTotal;
    double tax;
    String email, name;

    public UserCart(double tax) {
        this.tax = tax;
        cartItems = new ArrayList<CartItem>();
        reset();
    }
    public List getList(){
    	return (List) cartItems.clone();
    }
    	
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof UserCart))return false;
        UserCart otherMyClass = (UserCart)other;

        return totalPrice == ((UserCart) other).totalPrice &&
                tax == ((UserCart) other).tax &&
                finalTotal == ((UserCart) other).finalTotal &&
                cartItems.equals(((UserCart) other).cartItems) &&
                email.equals(((UserCart) other).email) &&
                name.equals(((UserCart) other).name);
    }

    public int numberOf(int index) {
        if (index < 0 || index >= cartItems.size())
            throw new IndexOutOfBoundsException();

        return cartItems.get(index).quantity;
    }

    public boolean isEmpty() {
        int numOfItems = 0;

        for (CartItem item : cartItems)
            numOfItems += item.quantity;

        return numOfItems == 0 && totalPrice == 0;
    }

    void reset() {
        cartItems.clear();
        totalPrice = 0;
        finalTotal = 0;
        email = "";
        name = "";
    }

    public void changeQuantity(int index, int amount, ArrayList<View> cleanupViews) {
        if (index < 0 || index >= cartItems.size())
            throw new IndexOutOfBoundsException();
        else if (amount < 0)
            throw new IllegalArgumentException("Items can't have a negative quantity.");
        else if (amount == 0)
            removeItem(index, cleanupViews);
        else {
            CartItem item = cartItems.get(index);
            totalPrice += (amount - item.quantity) * item.baseItem.price;
            item.quantity = amount;
        }
    }

    public void testChangeQuantity(int index, int amount) {
        changeQuantity(index, amount, new ArrayList<View>());
    }

    public void addItem(Item item, int quantity, String comments) {
        if (quantity < 1)
            throw new IllegalArgumentException("Items can't have a negative or zero quantity while being added.");
        if (item == null)
            throw new NullPointerException();

        CartItem newItem = new CartItem(item, quantity, comments);
        boolean inCart = false;
        for (CartItem cartItem : cartItems) {
            if (cartItem.baseItem == item) {
                newItem = cartItem;
                inCart = true;
                break;
            }
        }

        if (inCart)
            newItem.quantity += quantity;
        else
            cartItems.add(newItem);

        totalPrice += item.price * quantity;
    }

    public void testAddItem(Item item, int quantity) {
        addItem(item, quantity, "");
    }

    public int incrementItem(int index) {
        if (index < 0 || index >= cartItems.size())
            throw new IndexOutOfBoundsException();

        CartItem item = cartItems.get(index);
        item.quantity++;
        totalPrice += item.baseItem.price;
        return item.quantity;
    }

    public int decrementItem(int index, ArrayList<View> cleanupViews) {
        if (index < 0 || index >= cartItems.size())
            throw new IndexOutOfBoundsException();
        CartItem item = cartItems.get(index);

        if (item.quantity == 0)
            throw new IllegalArgumentException("You can't have negative items.");

        if (--item.quantity == 0)
            removeItem(index, cleanupViews);

        if (item.quantity >= 0)
            totalPrice -= item.baseItem.price;

        return item.quantity;
    }

    public int testDecrementItem(int index) {
        return decrementItem(index, new ArrayList<View>());
    }


    public void removeItem(int index, ArrayList<View> cleanupViews) {
        if (index < 0 || index >= cartItems.size())
            throw new IndexOutOfBoundsException();
        for (View view : cleanupViews)
            view.setVisibility(View.GONE);

        CartItem item = cartItems.get(index);
        totalPrice -= item.quantity * item.baseItem.price;
        item.quantity = 0;
    }

    public void testRemoveItem(int index) {
        removeItem(index, new ArrayList<View>());
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getSubtotal() {
        return (int) Math.ceil(totalPrice * (1 + tax));
    }

    public void emailReceipt() {

    }

    public void printReceipt() {

    }

    public String toString() {
        StringBuilder retVal = new StringBuilder();
        for (CartItem i : cartItems) {
            retVal.append(i.toString());
            retVal.append("\n");
        }
        retVal.append("================\nSubtotal: $");
        retVal.append(totalPrice / 100);
        retVal.append(".");
        retVal.append(totalPrice % 100);
        retVal.append("\nTax:\t $");
        retVal.append((int) (tax * totalPrice / 100));
        retVal.append(".");
        retVal.append((int) (tax * totalPrice % 100));
        retVal.append("\nTotal:\t $");
        retVal.append(getSubtotal() / 100);
        retVal.append(".");
        retVal.append(getSubtotal() % 100);
        retVal.append("\n");


        return retVal.toString();
    }

}
