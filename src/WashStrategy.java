import java.util.ArrayList;

//Implementation of the Strategy Pattern
interface WashStrategy {
    double washVehicles(ArrayList<Vehicle> vList, String name, double bonusEarned);
}

//inheriting both WashStrategy, SysOut to allow us to print to the system as well to implement the Strategy Pattern
class ChemicalWashStrategy implements WashStrategy, SysOut {
    @Override
    public double washVehicles(ArrayList<Vehicle> vList, String name, double bonusEarned) {

        out("Chemical Washing Method in use");
        int washCount = 0;
        Enums.Cleanliness startAs;
        for (Vehicle v:vList) {
            // wash the first dirty car I see
            if (v.cleanliness == Enums.Cleanliness.Dirty) {
                washCount += 1;
                startAs = Enums.Cleanliness.Dirty;
                double washChance = Utility.rnd();
                double breakChance = Utility.rnd();

                if (washChance <= .8) v.cleanliness = Enums.Cleanliness.Clean;
                if (washChance >.8 && washChance <=.9) {
                    v.cleanliness = Enums.Cleanliness.Sparkling;
                    bonusEarned += v.wash_bonus;
                    out("Intern "+ name +" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                }
                out("Intern "+name+" washed "+v.name+" "+startAs+" to "+v.cleanliness);

                if (breakChance <= .1 && v.condition != Enums.Condition.Broken){
                    v.condition = Enums.Condition.Broken;
                    out("Intern "+name+" broke "+v.name);
                }

                if (washCount == 2) break;
            }
        }

        if (washCount<2) {
            for (Vehicle v:vList) {
                // wash the first clean car I see
                if (v.cleanliness == Enums.Cleanliness.Clean) {
                    washCount += 1;
                    startAs = Enums.Cleanliness.Clean;
                    double chance = Utility.rnd();
                    double breakChance = Utility.rnd();

                    if (chance <= .1) v.cleanliness = Enums.Cleanliness.Dirty;
                    if (chance >.1 && chance <=.2) {
                        v.cleanliness = Enums.Cleanliness.Sparkling;
                        bonusEarned += v.wash_bonus;
                        out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                    }
                    out("Intern "+name+" washed "+v.name+" "+startAs+" to "+v.cleanliness);

                    if (breakChance <= .1 && v.condition != Enums.Condition.Broken){
                        v.condition = Enums.Condition.Broken;
                        out("Intern "+name+" broke "+v.name);
                    }

                    if (washCount == 2) break;
                }
            }
        }
        return bonusEarned;
    }
}

//inheriting both WashStrategy, SysOut to allow us to print to the system as well to implement the Strategy Pattern
class ElbowGreaseWashStrategy implements WashStrategy, SysOut {
    @Override
    public double washVehicles(ArrayList<Vehicle> vList, String name, double bonusEarned) {

        out("Elbow Grease Washing Method in use");
        int washCount = 0;
        Enums.Cleanliness startAs;
        for (Vehicle v:vList) {
            // wash the first dirty car I see
            if (v.cleanliness == Enums.Cleanliness.Dirty) {
                washCount += 1;
                startAs = Enums.Cleanliness.Dirty;
                double washChance = Utility.rnd();
                double likeNewChance = Utility.rnd();

                if (washChance <= .7) v.cleanliness = Enums.Cleanliness.Clean;
                if (washChance >.7 && washChance <=.75) {
                    v.cleanliness = Enums.Cleanliness.Sparkling;
                    bonusEarned += v.wash_bonus;
                    out("Intern "+ name +" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                }
                out("Intern "+name+" washed "+v.name+" "+startAs+" to "+v.cleanliness);

                if (likeNewChance <= .1 && v.condition != Enums.Condition.LikeNew){
                    v.condition = Enums.Condition.LikeNew;
                    out("Intern "+name+" made "+v.name+" Like New");
                }

                if (washCount == 2) break;
            }
        }

        if (washCount<2) {
            for (Vehicle v:vList) {
                // wash the first clean car I see
                if (v.cleanliness == Enums.Cleanliness.Clean) {
                    washCount += 1;
                    startAs = Enums.Cleanliness.Clean;
                    double chance = Utility.rnd();
                    double likeNewChance = Utility.rnd();

                    if (chance <= .15) v.cleanliness = Enums.Cleanliness.Dirty;
                    if (chance >.15 && chance <=.30) {
                        v.cleanliness = Enums.Cleanliness.Sparkling;
                        bonusEarned += v.wash_bonus;
                        out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                    }
                    out("Intern "+name+" washed "+v.name+" "+startAs+" to "+v.cleanliness);

                    if (likeNewChance <= .1 && v.condition != Enums.Condition.LikeNew){
                        v.condition = Enums.Condition.LikeNew;
                        out("Intern "+name+" made "+v.name+" Like New");
                    }

                    if (washCount == 2) break;
                }
            }
        }
        return bonusEarned;
    }
}

//inheriting both WashStrategy, SysOut to allow us to print to the system as well to implement the Strategy Pattern
class DetailedWashStrategy implements WashStrategy, SysOut {
    @Override
    public double washVehicles(ArrayList<Vehicle> vList, String name, double bonusEarned) {

        out("Detailed Washing Method in use");

        int washCount = 0;
        Enums.Cleanliness startAs;

        for (Vehicle v:vList) {
            // wash the first dirty car I see
            if (v.cleanliness == Enums.Cleanliness.Dirty) {
                washCount += 1;
                startAs = Enums.Cleanliness.Dirty;
                double chance = Utility.rnd();
                if (chance <= .6) v.cleanliness = Enums.Cleanliness.Clean;
                if (chance >.6 && chance <=.8) {
                    v.cleanliness = Enums.Cleanliness.Sparkling;
                    bonusEarned += v.wash_bonus;
                    out("Intern "+ name +" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                }
                out("Intern "+name+" washed "+v.name+" "+startAs+" to "+v.cleanliness);
                if (washCount == 2) break;
            }
        }
        if (washCount<2) {
            for (Vehicle v:vList) {
                // wash the first clean car I see
                if (v.cleanliness == Enums.Cleanliness.Clean) {
                    washCount += 1;
                    startAs = Enums.Cleanliness.Clean;
                    double chance = Utility.rnd();
                    if (chance <= .05) v.cleanliness = Enums.Cleanliness.Dirty;
                    if (chance >.05 && chance <=.45) {
                        v.cleanliness = Enums.Cleanliness.Sparkling;
                        bonusEarned += v.wash_bonus;
                        out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                    }
                    out("Intern "+name+" washed "+v.name+" "+startAs+" to "+v.cleanliness);
                    if (washCount == 2) break;
                }
            }
        }
        return bonusEarned;
    }
}