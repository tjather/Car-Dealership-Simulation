// All the enums for the program in one spot
// I much prefer enums to magic numbers or constants

// Normally, I would do each enum in a file, but
// I found this nice suggestion for doing all the enums in one spot/file
// https://stackoverflow.com/questions/10017729/multiple-enum-classes-in-one-java-file
public class Enums {
    public enum Condition {Broken, Used, LikeNew}
    public enum Cleanliness {Dirty,Clean,Sparkling}
    public enum BuyerType {JustLooking,WantsOne,NeedsOne}
    public enum VehicleType {Car, PerfCar, Pickup, ElectricCars, MonsterTrucks, Motorcycles, SportsCars, DerbyCars, Limousine}
    public enum StaffType {Intern, Mechanic, Salesperson, Driver}
    public enum DayOfWeek {Mon,Tue,Wed,Thur,Fri,Sat,Sun}
    public enum WashingMethod {Chemical, ElbowGrease, Detailed}
    public enum WorkingStatus {Working, Damaged}
}
