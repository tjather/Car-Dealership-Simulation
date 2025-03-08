//IMPLEMENTS THE DECORATOR PATTER

public abstract class AddoOnDecorator extends Vehicle {
    static double originalPrice = 0;
    abstract void getPrice();
}
class Warranty extends AddoOnDecorator {
    Vehicle v;
    Warranty(Vehicle v) {
        originalPrice = v.price;
        type = v.type;
        name = v.name;
        cost = v.cost;
        price =  v.price;
        repair_bonus = v.repair_bonus;
        wash_bonus = v.wash_bonus;
        sale_bonus = v.sale_bonus;
        vehicleAddons = v.vehicleAddons + " + Warranty";
    }

    void getPrice(){
        originalPrice = v.price;
    }
}

class Undercoating extends AddoOnDecorator {
    Vehicle v;
    Undercoating(Vehicle v) {
        type = v.type;
        name = v.name;
        cost = v.cost;
        price =  v.price + originalPrice * .05;
        repair_bonus = v.repair_bonus;
        wash_bonus = v.wash_bonus;
        sale_bonus = v.sale_bonus;
        vehicleAddons = v.vehicleAddons + " + Warranty";
    }

    void getPrice(){
        originalPrice = v.price;
    }
}

class RoadRescueCoverage extends AddoOnDecorator {
    Vehicle v;
    RoadRescueCoverage(Vehicle v) {
        type = v.type;
        name = v.name;
        cost = v.cost;
        price =  v.price + originalPrice * .02;
        repair_bonus = v.repair_bonus;
        wash_bonus = v.wash_bonus;
        sale_bonus = v.sale_bonus;
        vehicleAddons = v.vehicleAddons + " + Warranty";
    }

    void getPrice(){
        originalPrice = v.price;
    }
}

class Radio extends AddoOnDecorator {
    Vehicle v;
    Radio(Vehicle v) {
        type = v.type;
        name = v.name;
        cost = v.cost;
        price =  v.price + originalPrice * .05;
        repair_bonus = v.repair_bonus;
        wash_bonus = v.wash_bonus;
        sale_bonus = v.sale_bonus;
        vehicleAddons = v.vehicleAddons + " + Warranty";
    }

    void getPrice(){
        originalPrice = v.price;
    }
}