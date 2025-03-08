
public class StaffFactory {
	Staff hireStaff(Enums.StaffType s) {
		Staff staff = null;
		if(s==Enums.StaffType.Intern)
			staff=new Intern();
		else if(s==Enums.StaffType.Mechanic)
			staff=new Mechanic();
		else if(s==Enums.StaffType.Salesperson)
			staff=new Salesperson();
		else if(s==Enums.StaffType.Driver)
			staff=new Driver();
		else s=null;
		return staff;
	}
}
