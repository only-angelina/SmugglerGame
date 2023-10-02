import java.util.*;

public class City extends SmugglerGame {
    private String name;
    private List<Item> items;
    private int[] buyingPrices;
    private int[] sellingPrices;
    //private int totalSilver;

    public City(String name, List<Item> items, int[] buyingPrices, int[] sellingPricesn  /*int totalSilver*/){
        this.name = name;
        this.items = items;
        this.buyingPrices = buyingPrices;
        this.sellingPrices = sellingPrices;
        //this.totalSilver = totalSilver;
    }

    public String getName() {return name;}
    public List<Item> getItems() {return items;}
    public int getBuyingPrice(Item item) {
        int index = items.indexOf(item);
        return buyingPrices[index];
    }

    public int getSellingPrice(Item item) {
        int index = items.indexOf(item);
        return sellingPrices[index];
    }

    //public int getTotalSilver() {return totalSilver;}
}
