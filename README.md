# Car-Dealership-Simulation

# **Overview**

This project is the final iteration of the Friendly Neighborhood Car Dealership (FNCD) simulation. It expands upon previous versions by incorporating multiple dealership locations, a factory-based hiring and vehicle system, an advanced racing and sales model, and an improved command-based dealership operation system. The simulation is structured using **Object-Oriented Programming (OOP) principles** and various **Design Patterns**, making it robust and scalable.

This version extends the base FNCD implementation from Bruce Montgomery (2/19/23) and builds upond previous iterations that expanded functionality andstructure.

---

## **Team Members:**

- **Talha Ather**
- **Jake Davis**

## **Java Version:**

JDK 11

---

## **Simulation Operating Assumptions**

### **Multi-Branch FNCD Simulation**

-The simulation runs for 30 days, managing multiple FNCD branches, handling staff and vehicles dynamically. The simulation results and logs are stored in `SimResults.txt` and `/Logger/`.
- There are two dealership locations (North FNCD & South FNCD) operate independently.
- Each location has its own budget, staff, inventory, and sales performance.
- Each dealership automatically receive additional funding when needed to prevent.
- If no add-ons are bought, the customer gets the base package.

### **Staff Management & Promotions**

- Interns wash vehicles using different washing strategies.
- Mechanics repair vehicles with a chance-based success rate and earn bonuses.
- Salespeople negotiate sales and earn commission-based bonuses.
- Drivers participate in racing events to win prize money for their FNCD.
- Hiring & Promotion System is automated:
  - Interns get promoted when senior staff members leave.
  - Performance-based hiring and firings take place automatically.

### ** Vehicle Inventory & Management**

- Vehicles can arrive in 3 different conditions:
  - Broken, Used, Like New
- The vehicle types are as follows:
  - Cars, Performance Cars, Pickups, Electric Cars, Monster Trucks, Motorcycles, Sports Cars, Derby Cars, Limousines.
- Maintenance system works as follows:
  - Vehicles degrade if not regularly maintained.
  - Cleaning and repairs impact resale value.
  - Interns use 3 different washing techniques (Chemical, Elbow Grease, Detailed).

### ** Race Events & Damage Mechanics**

- Race winnings affect FNCD profits.
- Sundays are exclusive race days and so the FNCD does not operate sales. Rather they do the following:
  - Assigns drivers to vehicles.
  - Determines race results and financial rewards.
  - Tracks vehicle damage and potential value increases.
- Drivers can get injured and be unable to race and broken cars can not race.
- Repaired vehicles can race if they move up a stage.
---

## **Object-Oriented Programming Principles Used**

### Encapsulation

- All class data members are private and accessed via getters and setters, ensuring data integrity.
- Prevents unauthorized modifications to sensitive information such as vehicle conditions, staff salaries, and dealership financials.

### Abstraction

- **Abstract classes (such as `Vehicle` and `Staff`) define base properties while hiding implementation details.
- **Interfaces (such as `WashStrategy` and `Observer`) ensure flexibility and enforce design consistency.

### Inheritance

- `Car`, `Truck`, `PerformanceCar`, etc., inherit from `Vehicle`, allowing for shared attributes and behaviors.
- `Salesperson`, `Mechanic`, `Intern`, and `Driver` extend `Staff`, inheriting base characteristics.
- Eliminates redundant code and enhances maintainability.

### Polymorphism

- Method Overriding: Classes override functions like `sellVehicle()` or `washVehicle()` for specialized behaviors.
- Method Overloading: Different parameter versions of functions are used for dynamic input handling.
- Interfaces allow multiple implementations, making the system more extendable.

### Design Patterns Implemented

- **Observer Pattern:** Logger & Tracker monitor dealership details and racing events.
- **Factory Pattern:** Vehicles and Staff are dynamically created based on needs via `VehicleFactory` & `StaffFactory`.
- **Decorator Pattern:** Add-ons like warranties and satellite radios extend vehicle functionality.
- **Command Pattern:** FNCD operations such as buying, selling, and reparing vehicles are executed in a structured fashion.
- **Strategy Pattern:** Different washing techniques dynamically applied by interns.
- **Singleton Pattern:** Used in `Observer.java`, where Logger follows lazy instantiation and Tracker follows eager instantiation.

---

## **Project Structure**

```
/
│── /src
│   │── AddOnDecorator.java # Decorator Pattern for vehicle add-ons
│   │── Buyer.java         # Buyer class assigns names & purchase preferences
│   │── Command.java       # Implements command pattern to allow end user to act as a buyer
│   │── Enums.java         # Enum definitions (Condition, Cleanliness, BuyerType, etc.)
│   │── Event.java         # Event logging structure
│   │── FNCD.java          # Core dealership class, handles staff, inventory, sales
│   │── Main.java          # Entry point for running the simulation
│   │── Namer.java         # Helper class to create unique names
│   │── Observer.java      # Observer Pattern for the Logger and the Tracker. 
│   │── Simulator.java     # Cycles the multi-branch simulation for a select number of days
│   │── Staff.java         # Abstract & concrete staff roles (Intern, Mechanic, Salesperson, Driver)
│   │── StaffFactory.java  # Factory for dynamically hiring staff
│   │── SysOut.java        # Simplifies console output
│   │── Tester.java        # Contains unit tests
│   │── Utility.java       # Some static functions to call for randomness or fromatting
│   │── Vehicle.java       # Abstract & concrete vehicle types (Car, Pickup, Limousine, etc.)
│   │── VehicleFactory.java # Factory for creating vehicles dynamically
│   │── WashStrategy.java  # Strategy Pattern for different vehicle washing methods
│── First Project 4 UML.png  # Shows orginal planned UML diagram
│── Project 4 Final UML.png  # Shows final UML diagram
│── README.txt              # README
│── SimResults.txt          # File containing daily logs for FNCD transactions
│── UnitTestOutput.png      # Shows output of unit test

```

