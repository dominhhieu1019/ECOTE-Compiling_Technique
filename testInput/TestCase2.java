import java.util.*;
interface As {
    void msg();
}
interface Ds {}
class TestCase2 implements As, Ds
    public void msg(){System.out.println("Hello");}
}
