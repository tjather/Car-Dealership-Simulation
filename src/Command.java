import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

//THIS SECTION USES THE COMMAND PATTERN TO MAKE A HUMAN-CONTROLLED INTERFACE TO ALLOW USER TO ACT AS BUYER

public class Command {
    enum Action {SELECT, GETNAME, GETTIME, CHANGESALESPERSON, GETINVENTORY, GETDETAILS, BUY, END}

    public Action action;
    public String name;
    public ArrayList<Staff> staff;  // folks working
    public ArrayList<Vehicle> inventory;   // vehicles at the FNCD

    public Vehicle chosenVehicle;
    public Salesperson seller;
    public Command(Action action, String name, ArrayList<Staff> staff,  ArrayList<Vehicle> inventory)
    {
        this.action = action;
        this.name = name;
        this.staff = staff;
        this.inventory = inventory;
    }
}

class Reciever
{
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    public void process(Command c)
    {
        switch (c.action)
        {
            case SELECT:
                System.out.println("You are currently at the" + c.name + " Dealership. Leave to go to the other one.");
                break;
            case GETNAME:
                ArrayList<Staff> salespeople = Staff.getStaffByType(c.staff, Enums.StaffType.Salesperson);
                int randomSeller = Utility.rndFromRange(0,salespeople.size()-1);
                c.seller = (Salesperson) salespeople.get(randomSeller);
                System.out.println("The salespersons name is " + c.seller.name);
                break;
            case GETTIME:
                System.out.println(dtf.format(now));
                break;
            case CHANGESALESPERSON:
                ArrayList<Staff> newsalespeople = Staff.getStaffByType(c.staff, Enums.StaffType.Salesperson);
                int newRandomSeller = Utility.rndFromRange(0,newsalespeople.size()-1);
                c.seller = (Salesperson) newsalespeople.get(newRandomSeller);
                System.out.println("The new salespersons name is " + c.seller.name);
                break;
            case GETINVENTORY:
                Scanner scanner = new Scanner(System.in);

                for (Vehicle v : c.inventory) {
                    System.out.println("We have "+v.name+", a "+v.cleanliness+" "+v.condition+" "+v.type+" for "+Utility.asDollar(v.cost));
                    System.out.println("Would you like to select this car? Click 'y' for yes and anything else for no.");
                    char choice = scanner.next(".").charAt(0);
                    if(choice == 'y'){
                        c.chosenVehicle = v;
                        break;
                    }
                }
                break;
            case GETDETAILS:
                if(c.chosenVehicle != null) {
                    System.out.println("You choose " + c.chosenVehicle.name + ", a " + c.chosenVehicle.cleanliness + " " + c.chosenVehicle.condition + " " + c.chosenVehicle.type + " for " + Utility.asDollar(c.chosenVehicle.cost));
                }else{
                    System.out.println("You have not chosen a car");
                }
                break;
            case BUY:
                if(c.chosenVehicle != null) {
                    System.out.println("You bought" + c.chosenVehicle.name + ", a " + c.chosenVehicle.cleanliness + " " + c.chosenVehicle.condition + " " + c.chosenVehicle.type + " for " + Utility.asDollar(c.chosenVehicle.cost));
                    c.inventory.removeIf ( n -> n.name == c.chosenVehicle.name);
                }else{
                    System.out.println("You have not chosen a car");
                }
                break;
            case END:
                System.out.println("Thank you for shopping!");
                break;
        }
    }
}