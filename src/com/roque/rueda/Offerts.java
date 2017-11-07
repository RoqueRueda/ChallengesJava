package com.roque.rueda;

import java.util.*;

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

        int numberOfItems = 5;

        // Display all possibilities
        Product[][] allPermutations = generateAllPermutations(products, numberOfItems);
        ArrayList<Revenue> revenueList = new ArrayList<>(allPermutations.length);

        // Get all the revenues of the combinations
        for (int i = 0; i < allPermutations.length; i ++) {
            ArrayList<Product> combination = new ArrayList<>(numberOfItems);
            for (int j = 0; j < numberOfItems; j ++) {
                combination.add(allPermutations[i][j]);
            }
            revenueList.add(new Revenue(combination));
        }

        Collections.sort(revenueList, Comparator.comparingInt(revenue -> (int) revenue.getTotalRevenue()));
        for (Revenue r :
                revenueList) {
            System.out.println(r);
        }

    }

    /**
     * Get all the possible permutations without repetitions
     * @param elements List of elements
     * @param numberOfItems Number of items per combination
     * @return Matrix with all the possible permutations
     */
    private static Product[][] generateAllPermutations(Product[] elements, int numberOfItems) {
        // Math formula to get the number of permutations
        double up = calculateFactorial(numberOfItems + elements.length - 1);
        double down = calculateFactorial(numberOfItems) * calculateFactorial(elements.length -1);

        double numberOfCombinations = up / down;

        Product[][] combinationMatriz = new Product[(int)numberOfCombinations][numberOfItems];

        int[] elementIndex = new int[numberOfItems];
        int elementCounter = numberOfItems -1;

        // Set the index to 0
        for (int i = 0; i < elementIndex.length; i ++) {
            elementIndex[i] = 0;
        }

        // Iterate over the matrix
        for (int i = 0; i < numberOfCombinations; i ++) {
            for (int j = 0; j < numberOfItems; j ++) {
                // Retrieve the element using the element index
                combinationMatriz[i][j] = elements[elementIndex[j]];
            }
            // Increase the index by one
            elementIndex[elementCounter] += 1;
            // Check if the index are ok or if they need to add one
            // to the next digit
            checkIndex(elementIndex, elements.length);
        }

        return combinationMatriz;

    }

    /**
     * Check if the index of the permutation are ok or if they
     * need to add another value to the previous digit
     * @param elementsIndex Index elements
     * @param numberOfElements number of elements allowed per combination
     */
    private static void checkIndex(int[] elementsIndex,int numberOfElements) {
        for (int i = elementsIndex.length - 1; i > 0; i--) {
            // Check the last element if its less than the number of elements
            if (elementsIndex[i] > numberOfElements-1) {
                // Add one to the left
                if (i-1 >= 0){
                    elementsIndex[i-1] ++;
                    int indexToChange = i;
                    do {
                        elementsIndex[indexToChange] = elementsIndex[i-1];
                        indexToChange++;
                    } while (indexToChange < elementsIndex.length);

                }
            }
        }
    }

    /**
     * Calculate the factorial of a number using a for loop
     * @param factorialBase Base of the factorial used to calculate the result
     * @return Factorial of the base
     */
    private static long calculateFactorial(long factorialBase) {
        if (factorialBase < 0) {
            throw new IllegalArgumentException("The factorial base is less than 0");
        }

        if (factorialBase < 2) {
            return 1;
        }

        long result = 1;
        for (long i = factorialBase; i > 0; i--) {
            result *= i;
        }

        return result;
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

class Revenue {
    private List<Product> items;
    private Product cheapProduct;

    private double totalPrice;
    private double revenue;
    private double lowestPrice;
    private double lowestRevenue;
    private double totalRevenue;

    public Revenue(List<Product> products) {
        this.items = products;
        loadValues();
    }

    /**
     * Load all the values for this class.
     */
    private void loadValues(){
        if (items != null && items.size() > 0) {

            // The first item will be consider as the cheapest one.
            cheapProduct = items.get(1);
            lowestPrice = cheapProduct.price;
            lowestRevenue = calculateRevenue(cheapProduct.price, cheapProduct.type.gain());

            // Iterate over all the products to determine the values
            for (int i = 0; i < items.size(); i++) {
                Product p = items.get(i);
                if (p.price < lowestPrice) {
                    // If we find a lower price product we must
                    // set it as the cheapest product
                    cheapProduct = p;
                    lowestPrice = cheapProduct.price;
                    lowestRevenue = calculateRevenue(cheapProduct.price, cheapProduct.type.gain());
                }

                // Calculate the total price of all products
                totalPrice += p.price;
                // Calculate the total revenue
                revenue += calculateRevenue(p.price, p.type.gain());
            }

            // The total price is the sum of all the prices except for the cheapest product
            totalPrice = totalPrice - cheapProduct.price;
            // Also the revenue will be the sum of all revenues except the cheapest product
            totalRevenue = revenue - lowestRevenue;
        } else {
            throw new IllegalArgumentException("You should provide a valid Product list " +
                    "(not null and with at least one item)");
        }
    }

    public String getProductNames(){
        StringBuilder sb = new StringBuilder("Combination of products{ ");
        for (Product p :
                items) {
            sb.append(p.name).append(" ");
        }
        sb.append("}");
        return sb.toString();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    private double calculateRevenue(double price, double gain) {
        return price * (gain / 100d);
    }

    @Override
    public String toString() {
        return "Revenue " + getProductNames() + " revenue is: " + totalRevenue + " and price is: " + totalPrice;
    }
}

