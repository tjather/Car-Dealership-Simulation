public class Event {
	String info;
	int type;
	double amount;
	public Event(String info, int type, double amount) { //0 for logger
		this.info=info;							// 1 for employees 2 for fncd
		this.type=type;
		this.amount=amount;
	}
}
