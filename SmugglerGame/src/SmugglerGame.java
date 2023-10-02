import java.util.*;
public class SmugglerGame {

    public static void main(String[] args) {
        List<Category> categories = createCategories();
        List<Item> items = createItems(categories);
        List<City> cities = createCities(items);

        int[] buyingPrices = createBuyingPrices(items);
        int[] sellingPrices = createSellingPrices(buyingPrices);
        // int totalSilver = createTotalSilver(totalSilver);

        Scanner scanner = new Scanner(System.in);

        City currentCity = cities.get(0);
        int money = 1000;
        List<Item> inventory = new ArrayList<>();


        System.out.println("~~ Welcome to the Smuggler Game! ~~");
        while (true) {
            System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~");
            System.out.println("Current City: " + currentCity.getName());
            System.out.println("Money: " + money + " shilling");
            System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~");
            System.out.println("1. View Inventory");
            System.out.println("2. Sell Items");
            System.out.println("3. Buy items");
            System.out.println("4. Travel to a New City");
            System.out.println("5. Quit");
            System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            System.out.println();


            switch (choice) {
                case 0:
                    System.out.println("Thank you for playing!");
                    System.exit(0);
                case 1:
                    viewInventory(inventory);
                    break;
                case 2:
                    sellItems(inventory, currentCity, money, scanner);
                    break;
                case 3:
                    buyItems(inventory, currentCity, money, scanner);
                    break;
                case 4:
                    currentCity = travelToNewCity(cities, currentCity, inventory);
                    break; /*
                case 5:
                    System.out.println("Exiting the game. Goodbye!");
                    return; */
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void viewInventory(List<Item> inventory) {
        System.out.println("Inventory: ");

        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            for (int i = 0; i<inventory.size(); i++) {
                Item item = inventory.get(i);
                System.out.println((i + 1) + ". " + item.getName() + " (Category: " + item.getCategory().getName() + ")");
            }
        }
        System.out.println();
    }

    private static void sellItems(List<Item> inventory, City currentCity, int money, Scanner scanner) {
        System.out.println("Items in your inventory:");
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }

        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (Category: " + item.getCategory().getName() + ")");
        }

        System.out.print("Enter the number of the item you want to sell (0 to cancel): ");
        int sellChoice = scanner.nextInt();
        System.out.println();

        if (sellChoice > 0 && sellChoice <= inventory.size()) {
            Item itemToSell = inventory.get(sellChoice - 1);
            int sellingPrice = currentCity.getSellingPrice(itemToSell);

            inventory.remove(itemToSell);
            money += sellingPrice;

            System.out.println("Sold " + itemToSell.getName() + " for $" + sellingPrice);
            System.out.println("Money: " + money + " shilling");
            System.out.println();
        } else if (sellChoice != 0) {
            System.out.println("Invalid item number. Please try again.");
            System.out.println();
        }
    }

    private static void buyItems(List<Item> inventory, City currentCity, int money, Scanner scanner) {
        System.out.println("Items available in " + currentCity.getName() + ":");
        List<Item> itemsInCity = currentCity.getItems();

        if (itemsInCity.isEmpty()) {
            System.out.println("There are no items available in this city.");
            return;
        }

        for (int i = 0; i < itemsInCity.size(); i++) {
            Item item = itemsInCity.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (Category: " + item.getCategory().getName() + ")");
            System.out.println("   Price: " + currentCity.getBuyingPrice(item) + " shilling");
        }

        System.out.print("Enter the number of the item you want to buy (0 to cancel): ");
        int buyChoice = scanner.nextInt();
        System.out.println();

        if (buyChoice > 0 && buyChoice <= itemsInCity.size()) {
            Item itemToBuy = itemsInCity.get(buyChoice - 1);
            int buyingPrice = currentCity.getBuyingPrice(itemToBuy);

            if (money >= buyingPrice) {
                inventory.add(itemToBuy);
                money -= buyingPrice;

                System.out.println("Bought " + itemToBuy.getName() + " for $" + buyingPrice);
                System.out.println("Money: " + money + " shilling");
                System.out.println();
            } else {
                System.out.println("Insufficient funds to buy " + itemToBuy.getName() + ".");
                System.out.println("Money: " + money + " shilling");
                System.out.println();
            }
        } else if (buyChoice != 0) {
            System.out.println("Invalid item number. Please try again.");
            System.out.println();
        }
    }

    private static City travelToNewCity(List<City> cities, City currentCity, List<Item> inventory) {
        Random random = new Random();
        int bustedChance = inventory.size() * 10;

        if (random.nextInt(100) < bustedChance) {
            System.out.println("Oh no, you've been busted! You're being taken in by the Bobbies.");
            System.out.println("All items of one category were confiscated!");

            if (inventory.isEmpty()) {
                System.out.println("You had no items to confiscate. You are safe... for now");
            } else {
                int randomIndex = random.nextInt(inventory.size());
                Item confiscatedItem = inventory.remove(randomIndex);
                System.out.println("The authorities confiscated your " + confiscatedItem.getName() + ".");

                int fine = 100 + (inventory.size() - 1) * 50;
                System.out.println("You were fined " + fine + " shilling");
                if (fine > inventory.size()) {
                    System.out.println("You don't have enough money to pay the fine. You lose!");
                    System.exit(0);
                }
            }
        } else {
            System.out.println("You successfully traveled to a new city!");
        }
        City newCity = currentCity;
        while (newCity == currentCity) {
            newCity = cities.get(random.nextInt(cities.size()));
        }

        System.out.println("You have arrived at " + newCity.getName() + ".");
        return newCity;
    }


    private static List<Category> createCategories() {
        List<Category> categories = new ArrayList<>();

        categories.add(new Category("Food"));
        categories.add(new Category("Tools/Weapons"));
        categories.add(new Category("Armor"));
        categories.add(new Category("Potions"));
        categories.add(new Category("Misc."));

        return categories;
    }


    private static List<Item> createItems(List<Category> categories){
        List<Item> items = new ArrayList<>();
        Random random = new Random();

        for (Category category : categories) {
            switch (category.getName()) {
                case "Food":
                    items.add(new Item("Bread", random.nextInt(50) + 50, category));
                    items.add(new Item("Grains", random.nextInt(50) + 50, category));
                    items.add(new Item("Fruit", random.nextInt(50) + 50, category));
                    items.add(new Item("Vegetable", random.nextInt(50) + 50, category));
                    items.add(new Item("Beverage", random.nextInt(50) + 50, category));
                    break;
                case "Tools/Weapons":
                    items.add(new Item("Sword", random.nextInt(50) + 50, category));
                    items.add(new Item("Axe", random.nextInt(50) + 50, category));
                    items.add(new Item("Hammer", random.nextInt(50) + 50, category));
                    items.add(new Item("Crossbow", random.nextInt(50) + 50, category));
                    items.add(new Item("Dagger", random.nextInt(50) + 50, category));
                    break;
                case "Armor":
                    items.add(new Item("Helmet", random.nextInt(50) + 50, category));
                    items.add(new Item("Chest plate", random.nextInt(50) + 50, category));
                    items.add(new Item("Leggings", random.nextInt(50) + 50, category));
                    items.add(new Item("Boots", random.nextInt(50) + 50, category));
                    items.add(new Item("Shield", random.nextInt(50) + 50, category));
                    break;
                case "Potions":
                    items.add(new Item("Healing elixir", random.nextInt(50) + 50, category));
                    items.add(new Item("Poison", random.nextInt(50) + 50, category));
                    items.add(new Item("Enchantment potion", random.nextInt(50) + 50, category));
                    items.add(new Item("Revival elixir", random.nextInt(50) + 50, category));
                    items.add(new Item("Strength potion", random.nextInt(50) + 50, category));
                    break;
                case "Misc.":
                    items.add(new Item("Orc Eye", random.nextInt(50) + 50, category));
                    items.add(new Item("Dragon Scale", random.nextInt(50) + 50, category));
                    items.add(new Item("Elven Spell Book", random.nextInt(50) + 50, category));
                    items.add(new Item("Ruby of Erebor", random.nextInt(50) + 50, category));
                    items.add(new Item("Wizard's Cape", random.nextInt(50) + 50, category));
                    break;
            }
        }
        return items;
    }

    private static List<City> createCities(List<Item> items){
        List<City> cities = new ArrayList<>();
        Random random = new Random();

        int numItems = items.size();
        int numCities = 5;

        for (int i=0; i < numCities; i++){
            String cityName;
            switch (i) {
                case 1:
                    cityName = "Mystic Mountains";
                    break;
                case 2:
                    cityName = "Mosswood";
                    break;
                case 3:
                    cityName = "Dragons' Oak";
                    break;
                case 4:
                    cityName = "Lunar Lands";
                    break;
                case 5:
                    cityName = "Copper Hills";
                    break;
                default:
                    cityName = "Lavendell" + i;
                    break;
            }



            List<Item> cityItems = new ArrayList<>();
            //Map<Item, Integer> buyingPrice = new HashMap<>();
            //Map<Item, Integer> sellingPrice = new HashMap<>();
            int[] buyingPrices = new int[numItems]; //random.nextInt(50) + 50;
            int[] sellingPrices = new int[numItems]; //random.nextInt(50) + 100;


            for (int j=0;j<numItems; j++){
                Item item = items.get(j);
                cityItems.add(item);


                int basePrice = item.getPrice();
                int buyingPrice = basePrice + random.nextInt(50) - 25;
                int sellingPrice = basePrice + random.nextInt(50) - 25;

                buyingPrices[j] = Math.max(0, buyingPrice);
                sellingPrices[j] = Math.max(0, sellingPrice);
            }
            cities.add(new City("Mystic Mountains" , cityItems, buyingPrices, sellingPrices));
            cities.add(new City("Mosswood", cityItems, buyingPrices, sellingPrices));
            cities.add(new City("Dragons' Oak", cityItems, buyingPrices, sellingPrices));
            cities.add(new City("Lunar Lands", cityItems, buyingPrices, sellingPrices));
            cities.add(new City("Copper Hills", cityItems, buyingPrices, sellingPrices));

        }
        return cities;
    }

    private static int[] createBuyingPrices(List<Item> items){
        Random random = new Random();
        int[] buyingPrices = new int[items.size()];

        for(int i= 0; i<items.size(); i++){
            buyingPrices[i] = random.nextInt(50) + 50;
        }
        return buyingPrices;
    }

    private static int[] createSellingPrices(int[] buyingPrices) {
        Random random = new Random();
        int[] sellingPrices = new int[buyingPrices.length];

        for (int i = 0; i < buyingPrices.length; i++) {
            sellingPrices[i] = buyingPrices[i] + random.nextInt(50) + 50;
        }

        return sellingPrices;
    }

}

/*
1.  Food
- Bread
- Grains
- Fruit
- Vegetable
- Beverage


2. Tools/Weapons
- Sword
- Axe
- Hammer
- Crossbow
- Dagger


3. Armor
- Helmet
- Chest plate
- Leggings
- Boots
- Shield


4. Potions
- Healing elixir
- Poison
- Enchantment potion
- Revival elixir
- Strength potion


5. Misc.
- Orc Eye
- Dragon Scale
- Elven Spell Book
- Ruby of Erebor
- Wizard's Cape
 */