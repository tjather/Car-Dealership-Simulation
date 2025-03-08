//IMPLEMENTATION OF FACTORY PATTERN
public class VehicleFactory {
	Vehicle orderVehicle(Enums.VehicleType vehicle_type) {
		Vehicle return_vehicle;
		if(vehicle_type==Enums.VehicleType.Car)
			return_vehicle=new Car();
		else if(vehicle_type==Enums.VehicleType.PerfCar)
			return_vehicle=new PerfCar();
		else if(vehicle_type==Enums.VehicleType.Pickup)
			return_vehicle=new Pickup();
		else if(vehicle_type==Enums.VehicleType.ElectricCars)
			return_vehicle=new ElectricCars();
		else if(vehicle_type==Enums.VehicleType.MonsterTrucks)
			return_vehicle=new MonsterTrucks();
		else if(vehicle_type==Enums.VehicleType.Motorcycles)
			return_vehicle=new Motorcycles();
		else if(vehicle_type==Enums.VehicleType.SportsCars)
			return_vehicle=new SportsCars();
		else if(vehicle_type==Enums.VehicleType.DerbyCars)
			return_vehicle=new DerbyCars();
		else if(vehicle_type==Enums.VehicleType.Limousine)
			return_vehicle=new Limousine();
		else return_vehicle=null;
		return return_vehicle;
	}
}
