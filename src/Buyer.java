import java.util.Arrays;
import java.util.List;

// Quick little buyer class that can take care of its name and initialization
public class Buyer {
    String name;
    Enums.BuyerType type;
    Enums.VehicleType preference;
    static List<String> names = Arrays.asList("Luke","Leia","Han","Chewy");
    static Namer namer = new Namer(names);
    Buyer() {
        // even chances for these randoms - could weight them if needed
        preference = Utility.randomEnum(Enums.VehicleType.class);
        type = Utility.randomEnum(Enums.BuyerType.class);
        name = namer.getNext();
    }
}
