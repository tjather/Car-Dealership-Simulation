import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// This represents the FNCD business and things they would control
public class FNCD implements SysOut {
    ArrayList<Staff> staff;  // folks working
    ArrayList<Staff> departedStaff;   // folks that left
    ArrayList<Vehicle> inventory;   // vehicles at the FNCD
    ArrayList<Vehicle> soldVehicles;   // vehicles the buyers bought
    private double budget;   // big money pile
    ArrayList<Observer> observers;
    String name;
	private Reciever reciever = new Reciever();

	//SINGLETON PATTERN USED HERE
    FNCD(String namer) {
    	this.name=namer;
        staff = new ArrayList<>();
        departedStaff = new ArrayList<>();
        inventory = new ArrayList<>();
        soldVehicles = new ArrayList<>();
        budget = 100000;  // I changed this just to see additions to the budget happen
		observers = new ArrayList();
		Logger l = Logger.getInstance(name);
		observers.add(l);
		Tracker t = Tracker.getInstance();
		observers.add(t);
    }
    double getBudget() {
        return budget;    // I'm keeping this private to be on the safe side
    }
    void moneyIn(double cash) {  // Nothing special about taking money in yet
        budget += cash;
    }
    void moneyOut(double cash) {   // I check for budget overruns on every payout
        budget -= cash;
        if (budget<=0) {
            budget += 250000;
            out("***Budget overrun*** Added $250K, budget now: " + Utility.asDollar(budget));
            addToObservers("250K added to budget.",2,250000);
        }
    }

    void addObserver(Observer o) {
    	observers.add(o);
    }

    void addToObservers(String str,int i,double j) {
		for (Observer o : observers) {
			o.addEvent(str, i, j);
		}
    }
    
    void updateObservers() throws FileNotFoundException {
		for (Observer o : observers) {
			o.update();
		}
    }
    // Here's where I process daily activities
    // I debated about moving the individual activities out to an Activity class
    // It would make the normal day less of a monster maybe, eh...

    void raceDay(Enums.DayOfWeek day) throws FileNotFoundException {
    	out("The FNCD is opening... for racing!");
    	
    	out("The FNCD drivers are racing...");
    	ArrayList<Staff> drivers = Staff.getStaffByType(staff, Enums.StaffType.Driver);
    	ArrayList<Vehicle> validVehicles = new ArrayList<Vehicle>();
    	for(int i=0;i<inventory.size();i++) {
    		Vehicle v=inventory.get(i);
    		if(v.type != Enums.VehicleType.Car && v.type != Enums.VehicleType.ElectricCars && v.condition != Enums.Condition.Broken) {
    			validVehicles.add(v);
    		}
    	}
    	ArrayList<Vehicle> racecars=new ArrayList<Vehicle>();
    	for(Staff s:drivers) {
    		if(validVehicles.size()>0) {
    			int choose=Utility.rndFromRange(0, validVehicles.size()-1);
    			Vehicle veh=validVehicles.get(choose);
    			out("Driver "+s.name+" is driving "+veh.type+" "+veh.name);
    			addToObservers("Driver "+s.name+" is driving "+veh.type+" "+veh.name,0,0);
    			validVehicles.remove(choose);
    			racecars.add(veh);
    			
    		}
    	}
    	ArrayList<Integer> placements=new ArrayList<Integer>();
    	for(int i=1;i<=20;i++)placements.add(i);
    	Collections.shuffle(placements);
    	for(int i=0;i<racecars.size();i++) {
    		out("Driver "+drivers.get(i).name+" ranked #"+placements.get(i)+" in the race!");
    		addToObservers("Driver "+drivers.get(i).name+" ranked #"+placements.get(i)+" in the race!",0,0);
    		if(placements.get(i)<=3) {
    			int bonus=Utility.rndFromRange(500, 2000);
    			out(drivers.get(i).name+" won $"+bonus);
    			drivers.get(i).bonusEarned+=bonus;
    			addToObservers("Driver "+drivers.get(i).name+" won $"+bonus,1,bonus);
    			Driver d = (Driver) drivers.get(i);
    			d.racesWon++;
    			racecars.get(i).racesWon++;
    			if(racecars.get(i).racesWon==1) racecars.get(i).cost*=1.1;
    		}
    		if(racecars.get(i).working == Enums.WorkingStatus.Damaged) {
    			double chance=Utility.rnd();
    			if(chance<0.3) {
    				out(drivers.get(i).name+" got injured and is leaving the FNCD!");
    				racecars.get(i).condition = Enums.Condition.Broken;
    				
    				String gone_name=drivers.get(i).name;
    				int indx=0;
    				for(indx=0;indx<staff.size();indx++) {
    					if(staff.get(indx).name.equals(gone_name)) break;
    				}
    				departedStaff.add(staff.get(indx));
    				staff.remove(indx);
    			}
    		}
    		if(placements.get(i)>=16) {
    			out(racecars.get(i).type+racecars.get(i).name+" is now damaged!");
    			racecars.get(i).working = Enums.WorkingStatus.Damaged;
    		}
    	}
    	payStaff();
    	updateObservers();
    }

    void normalDay(Enums.DayOfWeek day, int step) throws FileNotFoundException {  // On a normal day, we do all the activities

    	if(step==0) {
        // opening
    		out("The FNCD is opening...");
    		hireNewStaff();    // hire up to 3 of each staff type
    		
    	}
    	if(step==1) {
    		updateInventory();  // buy up to 4 of each type
    	}

    	else if(step==2) {
        // washing - tell the interns to do the washing up
    		out("The FNCD interns are washing...");
    		ArrayList<Staff> interns = Staff.getStaffByType(staff, Enums.StaffType.Intern);
    		for (Staff s:interns) {
    			Intern i = (Intern) s;
    			i.washVehicles(inventory);
    			addToObservers("Intern "+i.name+" tried washing a vehicle.",0,0);
    		}
    	}

    	else if(step==3) {
        // repairing - tell the mechanics to do their repairing
    		out("The FNCD mechanics are repairing...");
    		ArrayList<Staff> mechanics = Staff.getStaffByType(staff, Enums.StaffType.Mechanic);
    		for (Staff s:mechanics) {
    			Mechanic m = (Mechanic) s;
            	m.repairVehicles(inventory);
            	addToObservers("Mechanic "+m.name+" tried repairing a vehicle.",0,0);
    		}
    	}

    	else if(step==4) {
    		// selling
    		out("The FNCD salespeople are selling...");
    		ArrayList<Buyer> buyers = getBuyers(day);
    		ArrayList<Staff> salespeople = Staff.getStaffByType(staff, Enums.StaffType.Salesperson);
    		// tell a random salesperson to sell each buyer a car - may get bonus
    		for(Buyer b: buyers) {
    			out("Buyer "+b.name+" wants a "+b.preference+" ("+b.type+")");
    			int randomSeller = Utility.rndFromRange(0,salespeople.size()-1);
    			Salesperson seller = (Salesperson) salespeople.get(randomSeller);
    			Vehicle vSold = seller.sellVehicle(b, inventory);
    			// What the FNCD needs to do if a car is sold - change budget and inventory
    			if (vSold != null) {
    				addToObservers("Salesperson "+seller.name+" sold a vehicle for "+Utility.asDollar(vSold.price),2,vSold.price);
    				soldVehicles.add(vSold);
    				moneyIn(vSold.price);
    				inventory.removeIf ( n -> n.name == vSold.name);
    			}
    		}
    	}

    	else if(step==5) {
    		// ending
    		// pay all the staff their salaries
    		payStaff();
    		// anyone quitting? replace with an intern (if not an intern)
    		checkForQuitters();
    		// daily report
    		reportOut();
        
    		updateObservers();
    	}
    }

	void extraDay(Enums.DayOfWeek day, int step) throws FileNotFoundException {  // On a normal day, we do all the activities

		if(step==0) {
			// opening
			out("The FNCD is opening...");
			hireNewStaff();    // hire up to 3 of each staff type

		}
		if(step==1) {
			updateInventory();  // buy up to 4 of each type
		}

		else if(step==2) {
			// washing - tell the interns to do the washing up
			out("The FNCD interns are washing...");
			ArrayList<Staff> interns = Staff.getStaffByType(staff, Enums.StaffType.Intern);
			for (Staff s:interns) {
				Intern i = (Intern) s;
				i.washVehicles(inventory);
				addToObservers("Intern "+i.name+" tried washing a vehicle.",0,0);
			}
		}

		else if(step==3) {
			// repairing - tell the mechanics to do their repairing
			out("The FNCD mechanics are repairing...");
			ArrayList<Staff> mechanics = Staff.getStaffByType(staff, Enums.StaffType.Mechanic);
			for (Staff s:mechanics) {
				Mechanic m = (Mechanic) s;
				m.repairVehicles(inventory);
				addToObservers("Mechanic "+m.name+" tried repairing a vehicle.",0,0);
			}
		}
// THIS USES THE COMMAND PATTERN
		else if(step==4) {
			System.setOut(new PrintStream( new FileOutputStream(FileDescriptor.out)));
			// selling
			out("The FNCD salespeople are selling...TO YOU!!!");
			Scanner scanner = new Scanner(System.in);
			boolean menu = true;

			Reciever r = new Reciever();

			while (menu) {
				// Print the menu
				System.out.println("Select a command:");
				System.out.println("1. Select one of the FNCD locations");
				System.out.println("2. Ask the salesperson their name ");
				System.out.println("3. Ask the salesperson what time it is");
				System.out.println("4. Ask for a different salesperson");
				System.out.println("5. Ask the salesperson for current store inventory");
				System.out.println("6. Ask the salesperson for all details on a user selected inventory item");
				System.out.println("7. Buy a normal inventory item from the salesperson");
				System.out.println("8. End interactions");

				// Get the user's input
				int choice = scanner.nextInt();
				Command command = null;

				switch (choice) {
					case 1:
						command = new Command(Command.Action.SELECT, this.name, this.staff, this.inventory);
						break;
					case 2:
						command = new Command(Command.Action.GETNAME, this.name, this.staff, this.inventory);
						break;
					case 3:
						command = new Command(Command.Action.GETTIME, this.name, this.staff, this.inventory);
						break;
					case 4:
						command = new Command(Command.Action.CHANGESALESPERSON, this.name, this.staff, this.inventory);
						break;
					case 5:
						command = new Command(Command.Action.GETINVENTORY, this.name, this.staff, this.inventory);
						break;
					case 6:
						command = new Command(Command.Action.GETDETAILS, this.name, this.staff, this.inventory);
						break;
					case 7:
						command = new Command(Command.Action.BUY, this.name, this.staff, this.inventory);
						break;
					case 8:
						command = new Command(Command.Action.END, this.name, this.staff, this.inventory);
						menu = false;
						break;
					default:
						System.out.println("Invalid choice. Please try again.");
						break;
				}
				if (command != null) {
					r.process(command);
				}
			}
		}else if(step==5) {
			// ending
			// pay all the staff their salaries
			payStaff();
			// anyone quitting? replace with an intern (if not an intern)
			checkForQuitters();
			// daily report
			reportOut();

			updateObservers();
		}
	}
    // generate buyers
    ArrayList<Buyer> getBuyers(Enums.DayOfWeek day) {
        // 0 to 5 buyers arrive (2-8 on Fri/Sat)
        int buyerMin = 0;  //normal Mon-Thur
        int buyerMax = 5;
        if (day == Enums.DayOfWeek.Fri || day == Enums.DayOfWeek.Sat) {
            buyerMin = 2;
            buyerMax = 8;
        }
        ArrayList<Buyer> buyers = new ArrayList<Buyer>();
        int buyerCount = Utility.rndFromRange(buyerMin,buyerMax);
        for (int i=1; i<=buyerCount; ++i) buyers.add(new Buyer());
        out("The FNCD has "+buyerCount+" buyers today...");
        return buyers;
    }

    // see if we need any new hires
    void hireNewStaff() {
        final int numberInStaff = 3;
        for (Enums.StaffType t : Enums.StaffType.values()) {
            int typeInList = Staff.howManyStaffByType(staff, t);
            int need = numberInStaff - typeInList;
            for (int i = 1; i<=need; ++i) addStaff(t);
        }
    }

    // adding staff
    // smells like we need a factory or something...
    void addStaff(Enums.StaffType t) {
        StaffFactory s=new StaffFactory();
        Staff newStaff=s.hireStaff(t);
        out("Hired a new "+newStaff.type+" named "+ newStaff.name);
        staff.add(newStaff);
    }

    // see if we need any vehicles
    void updateInventory() {
        final int numberInInventory = 6;
        for (Enums.VehicleType t : Enums.VehicleType.values()) {
            int typeInList = Vehicle.howManyVehiclesByType(inventory, t);
            int need = numberInInventory - typeInList;
            for (int i = 1; i<=need; ++i) addVehicle(t);
        }

    }

    // add a vehicle of a type to the inventory
    void addVehicle(Enums.VehicleType t) {
    	//IMPLEMENTATION OF FACTORY PATTERN
        VehicleFactory vf = new VehicleFactory();
        Vehicle v=vf.orderVehicle(t);
        moneyOut(v.cost);  // pay for the vehicle
        out ("Bought "+v.name+", a "+v.cleanliness+" "+v.condition+" "+v.type+" for "+Utility.asDollar(v.cost));
        inventory.add(v);
    }

    // pay salary to staff and update days worked
    void payStaff() {
        for (Staff s: staff) {
        	double amount=s.salary+s.bonusEarned;
            moneyOut(amount);  // money comes from the FNCD
            s.salaryEarned += amount;  // they get paid
            addToObservers(Utility.asDollar(amount)+" paid to "+s.name+" for working at FNCD",1,amount);
            addToObservers("IGNORE",2,-amount);
            s.daysWorked += 1; // they worked another day
            s.bonusEarned=0;
        }
    }

    void checkForQuitters() {
    	ArrayList<Enums.StaffType> st = new ArrayList<Enums.StaffType>();
    	st.add(Enums.StaffType.Driver);
    	for(int i=0;i<staff.size();i++) {
    		
    		Staff s=staff.get(i);
    		boolean isin=false;
    		for(int j=0; j<st.size();j++) {
    			if(s.type == st.get(j)) isin=true;
    		}
   			double chance= Utility.rnd();
   			if(chance<=0.1 && !isin) {
    			st.add(s.type);
    			out(s.type+" "+s.name+" is leaving the FNCD!");
    			departedStaff.add(s);
    			staff.remove(i);
    			
    			if(s.type!=Enums.StaffType.Intern) {
    				boolean added=false;
    				int k=0;
    				while(!added) {
    					
    					double chance2=Utility.rnd();
    					if(staff.get(k).type==Enums.StaffType.Intern && chance2>0.5) {
    						out("Intern "+staff.get(k).name+" is now a "+s.type+"!");
    						
    						Staff newStaff=null;
    						if (s.type == Enums.StaffType.Mechanic) newStaff = new Mechanic();
    				        if (s.type == Enums.StaffType.Salesperson) newStaff = new Salesperson();
    						
    				        staff.remove(k);
    				        staff.add(k,newStaff);
    						
    						added=true;
    					}
    					
    					k++;
    					if(k==staff.size())k=0;
    				}
    			}
    			
    			i--;
    		}
    	}

        // I would check the percentages here
        // Move quitters to the departedStaff list
        // If an intern quits, you're good
        // If a mechanic or a salesperson quits
        // Remove an intern from the staff and use their properties to
        // create the new mechanic or salesperson
    }

    void reportOut() {
        // We're all good here, how are you?
        // Quick little summary of happenings, you could do better

        out("Vehicles in inventory "+inventory.size());
        out("Vehicles sold count "+soldVehicles.size());
        out("Money in the budget "+ Utility.asDollar(getBudget()));
        out("That's it for the day.");
    }
}
