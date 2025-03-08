// It annoys me to type SYSTEM.OUT.PRINTLN every time I want to print!!!!
// Sorry, I'll calm down now.
public interface SysOut {
    default void out(String msg) {
        System.out.println(msg);
    }
}
