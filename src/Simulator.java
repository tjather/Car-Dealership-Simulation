import java.io.FileNotFoundException;

// Simulator to cycle for select number of days
public class Simulator implements SysOut {
    final int numDays;
    Enums.DayOfWeek dayOfWeek;
    FNCD fncd_n = new FNCD("North");
    FNCD fncd_s = new FNCD("South");
    Simulator() {
        numDays = 15;  // magic number for days to run here
        dayOfWeek = Utility.randomEnum(Enums.DayOfWeek.class);  // we'll start on a random day (for fun)
    }

    // cycling endlessly through enum values
    // https://stackoverflow.com/questions/34159413/java-get-next-enum-value-or-start-from-first
    public Enums.DayOfWeek getNextDay(Enums.DayOfWeek e)
    {
        int index = e.ordinal();
        int nextIndex = index + 1;
        Enums.DayOfWeek[] days = Enums.DayOfWeek.values();
        nextIndex %= days.length;
        return days[nextIndex];
    }

    void run() throws FileNotFoundException {
        for (int day = 1; day <= numDays; ++day) {
            out(">>> Start Simulation Day "+day+" "+dayOfWeek);
            if (dayOfWeek == Enums.DayOfWeek.Sun || dayOfWeek == Enums.DayOfWeek.Wed) {
            	out("-----"+fncd_n.name+ " FNCD-----");
            	fncd_n.raceDay(dayOfWeek);
            	out("-----"+fncd_s.name+ " FNCD-----");
            	fncd_s.raceDay(dayOfWeek);
            }
            else {
            	int step=0;
            	for(step=0;step<6;step++) {
            		out("-----"+fncd_n.name+ " FNCD-----");
            		fncd_n.normalDay(dayOfWeek,step);
            		out("-----"+fncd_s.name+ " FNCD-----");
            		fncd_s.normalDay(dayOfWeek,step);
            	}
              // normal stuff on other days
            }

            out(">>> End Simulation Day "+day+" "+dayOfWeek+"\n");
            dayOfWeek = getNextDay(dayOfWeek);  // increment to the next day
        }

        out(">>> Start Simulation Day 31" + dayOfWeek);
        if (dayOfWeek == Enums.DayOfWeek.Sun || dayOfWeek == Enums.DayOfWeek.Wed) {
            out("It is Sunday so you cannot buy any cars!!!!");
            out("-----"+fncd_n.name+ " FNCD-----");
            fncd_n.raceDay(dayOfWeek);
            out("-----"+fncd_s.name+ " FNCD-----");
            fncd_s.raceDay(dayOfWeek);
            out("It is Sunday so you cannot buy any cars!!!!");
        }
        else {
            int step=0;
            for(step=0;step<6;step++) {
                out("-----"+fncd_n.name+ " FNCD-----");
                fncd_n.extraDay(dayOfWeek,step);
                out("-----"+fncd_s.name+ " FNCD-----");
                fncd_s.extraDay(dayOfWeek,step);
            }
            // normal stuff on other days
        }

        out(">>> End Simulation Day 31 " +dayOfWeek+"\n");
    }
}
