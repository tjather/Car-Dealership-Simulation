import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Staff implements SysOut {
    String name;
    Enums.Condition condition;

    double salary;  // daily salary
    double salaryEarned;
    double bonusEarned;
    Enums.StaffType type;
    int daysWorked;
    Staff() {
        salaryEarned = 0;
        bonusEarned = 0;
        daysWorked = 0;
    }

    // utility for getting Staff by Type
    // You could do this with getClass instead of Type, but I use the enum
    // because it's clearer to me - less Java-y
    static ArrayList<Staff> getStaffByType(ArrayList<Staff> staffList, Enums.StaffType t) {
        ArrayList<Staff> subclassInstances = new ArrayList<>();
        for (Staff s : staffList) {
            if (s.type == t) subclassInstances.add(s);
        }
        return subclassInstances;
    }

    //Utility for finding out how many of a Staff type there are
    static int howManyStaffByType(ArrayList<Staff> staffList, Enums.StaffType t) {
        int n = 0;
        for (Staff s: staffList) {
            if (s.type == t) n++;
        }
        return n;
    }
}

class Intern extends Staff {
    static List<String> names = Arrays.asList("Fred","Ethel","Lucy","Desi");
    static Namer namer = new Namer(names);
    Enums.WashingMethod washingMethod;
    private WashStrategy washStrategy;
    Intern() {
        super();
        type = Enums.StaffType.Intern;
        name = namer.getNext();  // every new intern gets a new name
        salary = 60; // daily salary
        washingMethod = Utility.randomEnum(Enums.WashingMethod.class);

        if(washingMethod == Enums.WashingMethod.Chemical){
            washStrategy = new ChemicalWashStrategy();
        }

        if(washingMethod == Enums.WashingMethod.ElbowGrease){
            washStrategy = new ElbowGreaseWashStrategy();
        }

        if(washingMethod == Enums.WashingMethod.Detailed){
            washStrategy = new DetailedWashStrategy();
        }
    }

    void washVehicles(ArrayList<Vehicle> vList) {
        washStrategy.washVehicles(vList, name, bonusEarned);
    }
}

class Mechanic extends Staff {
    static List<String> names = Arrays.asList("James", "Scotty", "Spock", "Uhura");
    static Namer namer = new Namer(names);
    Mechanic() {
        super();
        type = Enums.StaffType.Mechanic;
        name = namer.getNext();  // every new mechanic gets a new name
        salary = 120; // daily salary
    }

    // how Mechanics repair Vehicles - not as complicated as the Wash thing above
    void repairVehicles(ArrayList<Vehicle> vList) {
        int fixCount = 0;
        Enums.Condition startAs;
        // I'm just grabbing the first Vehicle I find - would be easy to randomly pick one
        for (Vehicle v: vList) {
            if (v.condition != Enums.Condition.LikeNew) {
                startAs = v.condition;
                if (v.cleanliness == Enums.Cleanliness.Clean) v.cleanliness = Enums.Cleanliness.Dirty;
                if (v.cleanliness == Enums.Cleanliness.Sparkling) v.cleanliness = Enums.Cleanliness.Clean;
                double chance = Utility.rnd();
                if (chance < .8) {
                    fixCount += 1;
                    if (v.condition == Enums.Condition.Used) {
                        v.condition = Enums.Condition.LikeNew;
                        v.price = v.price * 1.25;  // 25% increase for Used to Like New
                    }
                    if (v.condition == Enums.Condition.Broken) {
                        v.condition = Enums.Condition.Used;
                        v.price = v.price * 1.5;  // 50% increase for Broken to Used
                    }
                    v.working = Enums.WorkingStatus.Working;
                    bonusEarned += v.repair_bonus;
                    out("Mechanic "+name+" got a bonus of "+Utility.asDollar(v.repair_bonus)+"!");
                    out("Mechanic "+name+" fixed "+v.name+" "+startAs+" to "+v.condition);
                }
                else {
                    fixCount += 1;   // I'm saying a failed repair still took up a fix attempt
                    out("Mechanic "+name+" did not fix the "+v.condition+" "+v.name);
                }
            }
            if (fixCount==2) break;
        }
    }
}
class Salesperson extends Staff {
    static List<String> names = Arrays.asList("Rachel","Monica","Phoebe","Chandler","Ross","Joey");
    static Namer namer = new Namer(names);
    Salesperson() {
        super();
        type = Enums.StaffType.Salesperson;
        name = namer.getNext();  // every new salesperson gets a new name
        salary = 90; // daily salary
    }

    // Someone is asking this Salesperson to sell to this Buyer
    // We'll return any car we sell for the FNCD to keep track of (null if no sale)
    Vehicle sellVehicle(Buyer b, ArrayList<Vehicle> vList) {
        // buyer type determines initial purchase chance
        double saleChance = .7; // needs one
        if (b.type == Enums.BuyerType.WantsOne) saleChance = .4;
        if (b.type == Enums.BuyerType.JustLooking) saleChance = .1;
        // find the most expensive vehicle of the type the buyer wants that isn't broken
        // sales chance +10% if Like New, + 10% if Sparkling
        // if no vehicles of type, find remaining most expensive vehicle and sell at -20%
        ArrayList<Vehicle> desiredList = Vehicle.getVehiclesByType(vList, b.preference);
        Vehicle v;
        v = getMostExpensiveNotBroken(desiredList);  // could be null
        if (v == null) {
            // no unbroken cars of preferred type
            saleChance -= .2;
            v = getMostExpensiveNotBroken(vList);  // could still be null
        }
        if (v == null) {
            out("Salesperson "+name+" has no car for buyer "+b.name);
            return null;
        }
        else { //sell this car!
            if (v.condition == Enums.Condition.LikeNew) saleChance += .1;
            if (v.cleanliness == Enums.Cleanliness.Sparkling) saleChance += .1;
            double chance = Utility.rnd();
            if (chance<=saleChance) {  // sold!
                bonusEarned += v.sale_bonus;
                out("Buyer "+b.name+" is buying! Salesperson "+name+" gets a bonus of "+Utility.asDollar(v.sale_bonus)+"!");
                out("Buyer "+b.name+" bought "+v.cleanliness+" "+v.condition+" "+v.name+" for "+Utility.asDollar(v.price));
                return v;
            }
            else {  // no sale!
                out("Buyer "+b.name+" decided not to buy.");
                return null;
            }
        }
    }

    // Little helper for finding most expensive and not broken in a list of vehicles
    // Used twice by salespeople
    Vehicle getMostExpensiveNotBroken(ArrayList<Vehicle> vList) {
        double highPrice = 0;
        int selected = -1;
        for (int index=0;index<vList.size();++index) {
            Vehicle v = vList.get(index);
            if (v.price>highPrice) {
                if (v.condition != Enums.Condition.Broken) {
                    selected = index;
                    highPrice = v.price;
                }
            }
        }
        if (selected == -1) return null;
        else return vList.get(selected);
    }
}

class Driver extends Staff{
	static List<String> names = Arrays.asList("Dale","Mario","Lewis","AJ","Shirley","Kimi");
    static Namer namer = new Namer(names);

    int racesWon;
    Driver() {
        super();
        type = Enums.StaffType.Driver;
        name = namer.getNext();  // every new salesperson gets a new name
        salary = 100; // daily salary
        racesWon = 0;
    }
}