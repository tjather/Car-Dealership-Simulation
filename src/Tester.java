import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Tester {
    FNCD fncdMock = new FNCD("Mock");
    Reciever recieverMock = new Reciever();
    ArrayList<Staff> staffMock = new ArrayList<>();
    ArrayList<Vehicle> inventoryMock = new ArrayList<>();

    void addStaffMock(Enums.StaffType t){
        StaffFactory s = new StaffFactory();
        Staff newStaff = s.hireStaff(t);
        staffMock.add(newStaff);
    }

    void addVehicleMock(Enums.VehicleType t){
        VehicleFactory vf = new VehicleFactory();
        Vehicle v=vf.orderVehicle(t);
        fncdMock.moneyOut(v.cost);  // pay for the vehicle
        inventoryMock.add(v);
    }

    Tester() {}
    @Test
    void budgetFNCDTest(){
        double budget = fncdMock.getBudget();
        assertEquals(budget, 100000);

        fncdMock.moneyIn(9000);
        budget = fncdMock.getBudget();
        assertEquals(budget, 109000);

        fncdMock.moneyOut(9000);
        budget = fncdMock.getBudget();
        assertEquals(budget, 100000);

        fncdMock.moneyOut(100000);
        budget = fncdMock.getBudget();
        assertEquals(budget, 250000);


    }
    @Test
    void commandGetnameTest() {
        final int numberInStaff = 3;
        for (Enums.StaffType t : Enums.StaffType.values()) {
            int typeInList = Staff.howManyStaffByType(staffMock, t);
            int need = numberInStaff - typeInList;
            for (int i = 1; i<=need; ++i) addStaffMock(t);
        }

        Command command = new Command(Command.Action.GETNAME, "North", this.staffMock, this.inventoryMock);
        recieverMock.process(command);
        assertNotNull(command.seller);
    }

    @Test
    void getStaffByTypeTest() {
        ArrayList<Staff> internInstances = new ArrayList<>();
        ArrayList<Staff> driverInstances = new ArrayList<>();

        addStaffMock(Enums.StaffType.Intern);
        addStaffMock(Enums.StaffType.Driver);
        addStaffMock(Enums.StaffType.Driver);

        for (Staff s : staffMock) {
            if (s.type == Enums.StaffType.Intern) internInstances.add(s);
            if (s.type == Enums.StaffType.Driver) driverInstances.add(s);

        }

        assertEquals(internInstances.size(), 1);
        assertEquals(driverInstances.size(), 2);
    }

    @Test
    void howManyStaffByTypeTest() {
        addStaffMock(Enums.StaffType.Driver);
        addStaffMock(Enums.StaffType.Intern);
        addStaffMock(Enums.StaffType.Driver);

        int n = 0;
        for (Staff s: staffMock) {
            if (s.type == Enums.StaffType.Driver) n++;
        }
        assertEquals(n, 2);
    }

    @Test
    void addStaffTest() {
        StaffFactory s=new StaffFactory();
        Staff newStaff=s.hireStaff(Enums.StaffType.Intern);
        staffMock.add(newStaff);

        assertEquals(staffMock.size(), 1);
    }
    @Test
    void hireNewStaffTest() {

        final int numberInStaff = 3;
        for (Enums.StaffType t : Enums.StaffType.values()) {
            int typeInList = Staff.howManyStaffByType(staffMock, t);
            assertEquals(typeInList, 0);


            int need = numberInStaff - typeInList;
            for (int i = 1; i<=need; ++i) addStaffMock(t);

            int n = 0;
            for (Staff s: staffMock) {
                if (s.type == t) n++;
            }
            assertEquals(n, 3);
        }

        int n = 0;
        for (Staff s: staffMock) {
            if (s.type == Enums.StaffType.Salesperson) n++;
        }
        assertEquals(n, 3);
    }

    @Test
    void addVehicleTest() {
        VehicleFactory vf = new VehicleFactory();
        Vehicle v = vf.orderVehicle(Enums.VehicleType.Car);
        assertEquals(v.type, Enums.VehicleType.Car);

        Vehicle v2 = vf.orderVehicle(Enums.VehicleType.DerbyCars);

        fncdMock.moneyOut(v.cost);
        inventoryMock.add(v);
        inventoryMock.add(v2);
        assertEquals(inventoryMock.size(), 2);
    }

    @Test
    void updateInventoryTest() {
        final int numberInInventory = 6;
        for (Enums.VehicleType t : Enums.VehicleType.values()) {
            int typeInList = Vehicle.howManyVehiclesByType(inventoryMock, t);
            assertEquals(typeInList, 0);

            int need = numberInInventory - typeInList;
            for (int i = 1; i<=need; ++i) addVehicleMock(t);
        }

        int n = 0;
        for (Vehicle v: inventoryMock) {
            if (v.type == Enums.VehicleType.ElectricCars) n++;
        }
        assertEquals(n, 6);

    }

}