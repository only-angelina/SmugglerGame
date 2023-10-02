import java.util.*;

public class Item extends SmugglerGame {
    private String name;
    private int price;
    private Category category;

    public Item(String name, int price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {return name;}
    public int getPrice() {return price;}
    public Category getCategory() {return category;}
}
