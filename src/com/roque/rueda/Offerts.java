package com.roque.rueda;

import java.util.Arrays;
import java.util.List;

public class Offerts {

    public static void main(String... args) {
        Product products[] = new Product[]{
                new Product("Sabritas", 10.00, Product.Type.chips),
                new Product("Pepsi", 10.00, Product.Type.soda),
                new Product("CarlosV", 12.00, Product.Type.chocolate),
                new Product("Doritos", 8.00, Product.Type.chips),
                new Product("Cocacola", 11.00, Product.Type.soda),
                new Product("Kinder", 13.00, Product.Type.chocolate),
                new Product("Chetos", 6.00, Product.Type.chips),
                new Product("Squirt", 12.00, Product.Type.soda),
                new Product("Snickers", 14.00, Product.Type.chocolate),
                new Product("Rancheritos", 7.00, Product.Type.chips),
                new Product("7Up", 9.00, Product.Type.soda),
                new Product("Turin", 15.00, Product.Type.chocolate),
                new Product("Churrumais", 5.50, Product.Type.chips),
                new Product("Fanta", 7.00, Product.Type.soda),
                new Product("Abuelita", 16.00, Product.Type.chocolate)};

        System.out.println(Arrays.asList(products));

        getRevenue(Arrays.asList(products[1], products[1], products[1], products[1], products[1]));

        // Iterate over the items
        // Add the price of the items
        // Substract the lowest price
        // Get the revenue

        // Display all possibilities
        // Order the possibilities by biggest to lower



    }

    public static double getRevenue(List<Product> productList) {
        double totalPrice = 0;
        double revenue = 0;
        Product firstItem = productList.get(1);
        double lowestPrice = firstItem.price;
        double minusRevenue =  firstItem.price * (firstItem.type.gain() / 100d);
        for (Product p :
                productList) {
            if (p.price < lowestPrice) {
                lowestPrice = p.price;
                minusRevenue = p.price * ((double)p.type.gain() / 100d);
            }
            totalPrice += p.price;
            revenue += p.price * ((double)p.type.gain() / 100d);
        }

        System.out.println("Total Price: " +  totalPrice + " - " +
                "lowest price: " + lowestPrice + " revenue:" + revenue + " minus revenue:" + minusRevenue);
        System.out.println("Result Price: " +  ( totalPrice - lowestPrice ) );
        return revenue - minusRevenue;
    }

}

class Product {

    enum Type {
        chips(30), soda(25), chocolate(20);

        private int gain;
        Type(int gain) {
            this.gain = gain;
        }
        public int gain() {
            return gain;
        }
    }

    String name;
    Double price;
    Type type;

    public Product(String name, Double price, Type type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String toString() {
        String string = String.format(
                "NAME: %1s, PRICE: %2.2f, TYPE: %3s \n",
                name, price, type.name());
        System.out.println(string);
        return string;
    }
}
