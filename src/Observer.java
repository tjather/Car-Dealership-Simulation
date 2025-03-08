import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//THIS SECTION USES THE OBSERVER PATTERN TO MAKE LOGGER AND TRACKER WORK
//SINGLETON PATTERN USED HERE. LOGGER USES LAZY INSTANTIATION AND TRACKER USES EAGER INSTANTIATION

public abstract class Observer implements SysOut{
	ArrayList<Event> events=null;
	int day;
	public Observer() {
		events=new ArrayList<Event>();
		day=1;
	}
	void addEvent(String str,int i,double j) {
		Event e=new Event(str,i,j);
		events.add(e);
	}
	abstract void update();
}

class Logger extends Observer{
	String tag;
	private static Logger l;
	private Logger(String name) {
		tag=name.substring(0,1).toLowerCase();
	}
	public static Logger getInstance(String name) {
		if (l == null) {
			l = new Logger(name);
		}
		return l;
	}
	
	void update(){
		String output="Day "+day+"\n";
		for(Event e: events) {
			if(!e.info.equals("IGNORE"))
				output+=e.info+"\n";
		}
		File myObj = new File("./Logger/Logger-"+day+"-"+tag+".txt");
		try {
			if(myObj.delete()) {
				
			}
			if(myObj.createNewFile())
			{
				FileWriter f=new FileWriter("./Logger/Logger-"+day+"-"+tag+".txt");
				f.write(output);
				f.close();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		day++;
		events=new ArrayList<Event>();
	}
}

class Tracker extends Observer{
	private static Tracker t;
	private int count;

	private Tracker(){
		super();
	}

	public static Tracker getInstance() {
		if (t == null) {
			t = new Tracker();
		}
		return t;
	}
	void update() {
		double amount_workers=0;
		double amount_fncd=0;
		
		for(Event e:events) {
			if(e.type==1) {
				amount_workers+=e.amount;
			}
			else if(e.type==2) {
				amount_fncd+=e.amount;
			}
		}
		out("----- Tracker -----");
		out("Total money earned by staff: "+Utility.asDollar(amount_workers));
		out("Total money earned by the FNCD: "+Utility.asDollar(amount_fncd));
		
		day++;
		events=new ArrayList<Event>();
	}
}