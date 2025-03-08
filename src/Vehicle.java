import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Vehicle {
    String name;
    Enums.VehicleType type;
    Enums.Condition condition;
    Enums.Cleanliness cleanliness;
    Enums.WorkingStatus working;
    double cost;
    double price;
    double repair_bonus;
    double wash_bonus;
    double sale_bonus;
    int racesWon;
    Vehicle () {
        // all vehicles have the same cleanliness arrival chance
        double chance = Utility.rnd();
        if (chance <= .05) cleanliness = Enums.Cleanliness.Sparkling;
        else if (chance>.05 && chance<=.4) cleanliness = Enums.Cleanliness.Clean;
        else cleanliness = Enums.Cleanliness.Dirty;
        // all vehicles have the same condition arrival chance (even chance of any)
        condition = Utility.randomEnum(Enums.Condition.class);
        racesWon=0;
        if(condition == Enums.Condition.Broken) working=Enums.WorkingStatus.Damaged;
        else working=Enums.WorkingStatus.Working;
    }
    
    // utility for getting adjusted cost by condition
    double getCost(int low, int high) {
        double cost = Utility.rndFromRange(low, high);
        if (condition== Enums.Condition.Used) cost = cost*.8;
        if (condition== Enums.Condition.Broken) cost = cost*.5;
        return cost;
    }

    int getRange(int low, int high) {
        int range = Utility.rndFromRange(low, high);
        if (condition== Enums.Condition.LikeNew) range = range + 100;
        return range;
    }

    // utility for getting Vehicles by Type
    // You could do this with getClass instead of Type, but I use the enum
    // because it's clearer to me (less Java-y)
    static ArrayList<Vehicle> getVehiclesByType(ArrayList<Vehicle> vehicleList, Enums.VehicleType t) {
        ArrayList<Vehicle> subclassInstances = new ArrayList<>();
        for (Vehicle v : vehicleList) {
            if (v.type == t) subclassInstances.add(v);
        }
        return subclassInstances;
    }

    // Utility for finding out how many of a Vehicle there are
    static int howManyVehiclesByType(ArrayList<Vehicle> vehicleList, Enums.VehicleType t) {
        int n = 0;
        for (Vehicle v: vehicleList) {
            if (v.type == t) n++;
        }
        return n;
    }
}

class Car extends Vehicle {
    // could make the name list longer to avoid as many duplicates if you like...
    static List<String> names = Arrays.asList("Probe","Escort","Taurus","Fiesta");
    static Namer namer = new Namer(names);
    Car() {
        super();
        type = Enums.VehicleType.Car;
        name = namer.getNext();  // every new car gets a new name
        cost = getCost(10000,20000);
        price = cost * 2;
        repair_bonus = 100;
        wash_bonus = 20;
        sale_bonus = 500;
    }
}

class PerfCar extends Vehicle {
    static List<String> names = Arrays.asList("Europa","Cayman","Corvette","Mustang");
    static Namer namer = new Namer(names);
    PerfCar() {
        super();
        type = Enums.VehicleType.PerfCar;
        name = namer.getNext();  // every new perf car gets a unique new name
        cost = getCost(20000,40000);
        price = cost * 2;
        repair_bonus = 300;
        wash_bonus = 100;
        sale_bonus = 1000;
    }
}

class Pickup extends Vehicle {
    static List<String> names = Arrays.asList("Ranger","F-250","Colorado","Tundra");
    static Namer namer = new Namer(names);
    Pickup() {
        super();
        type = Enums.VehicleType.Pickup;
        name = namer.getNext();  // every new truck gets a unique new name
        cost = getCost(10000,40000);
        price = cost * 2;
        repair_bonus = 200;
        wash_bonus = 75;
        sale_bonus = 750;
    }
}

class ElectricCars extends Vehicle{
	static List<String> names = Arrays.asList("Taycan","Tesla Model 3","EQS","BMW i4");
    static Namer namer = new Namer(names);
    int range;
    ElectricCars() {
        super();
        type = Enums.VehicleType.ElectricCars;
        name = namer.getNext();  // every new truck gets a unique new name
        cost = getCost(30000,60000);
        price = cost * 2;
        repair_bonus = 600;
        wash_bonus = 20;
        sale_bonus = 800;
        range = getRange(60, 400);
    }
    

    int fixRange() {
    	if(condition == Enums.Condition.LikeNew) return range+100;
    	return range;
    }

}

class MonsterTrucks extends Vehicle{
	static List<String> names = Arrays.asList("Afterburner","Avenger","Bad News Travels Fast","Batman","Backwards Bob","Bear Foot","Bigfoot","Black Stallion","Blacksmith","Blue Thunder","Bounty Hunter","Brutus","Bulldozer","Captain's Curse","Cyborg","El Toro Loco","Grave Digger","Grinder","Gunslinger","Jurassic Attack","King Krunch","Crusader","Madusa","Max-D","Mohawk Warrior","Monster Mutt","Predator","Shell Camino","Raminator","Snake Bite","Stone Crusher","Sudden Impact","Swamp Thing","The Destroyer","The Felon","USA-1","War Wizard","WCW Nitro Machine","Zombie");
    static Namer namer = new Namer(names);

    String stageName;
    MonsterTrucks() {
        super();
        type = Enums.VehicleType.MonsterTrucks;
        name = namer.getNext();  // every new truck gets a unique new name
        cost = getCost(50000,80000);
        price = cost * 2;
        repair_bonus = 500;
        wash_bonus = 100;
        sale_bonus = 900;
    }
}

class Motorcycles extends Vehicle{
	static List<String> names = Arrays.asList("Yamaka","Kawasaki","Fancy Bicycle","Slingshot");
    static Namer namer = new Namer(names);
    double engine_size;

    Motorcycles() {
        super();
        type = Enums.VehicleType.Motorcycles;
        name = namer.getNext();  // every new truck gets a unique new name
        cost = getCost(20000,50000);
        price = cost * 2;
        repair_bonus = 150;
        wash_bonus = 5;
        sale_bonus = 400;
        Random rand=new Random();
        engine_size=Utility.rndFromRange(700, 300);
        if(engine_size<50) engine_size=50;
    }
}

class SportsCars extends Vehicle{
	static List<String> names = Arrays.asList("McLaren","Porche","Lightning McQueen");
	static Namer namer=new Namer(names);
	
	SportsCars(){
		super();
		type=Enums.VehicleType.SportsCars;
		name=namer.getNext();
		cost=getCost(40000,75000);
		price=cost*2;
		repair_bonus = 500;
		wash_bonus = 20;
		sale_bonus = 700;
	}
}

class DerbyCars extends Vehicle{
	static List<String> names = Arrays.asList("Pinewood Derby Car","Black Beetle","Speed Demon");
	static Namer namer=new Namer(names);
	
	DerbyCars(){
		super();
		type=Enums.VehicleType.DerbyCars;
		name=namer.getNext();
		cost=getCost(1000,15000);
		price=cost*2;
		repair_bonus = 10;
		wash_bonus = 5;
		sale_bonus = 100;
	}
}

class Limousine extends Vehicle{
	static List<String> names = Arrays.asList("Aeron Limo","Limo Bus","Black Tie Limo");
	static Namer namer=new Namer(names);
	
	Limousine(){
		super();
		type=Enums.VehicleType.Limousine;
		name=namer.getNext();
		cost=getCost(60000,90000);
		price=cost*2;
		repair_bonus = 800;
		wash_bonus = 500;
		sale_bonus = 1000;
	}
}