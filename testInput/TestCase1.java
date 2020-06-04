import java.util.*;
interface As {
    void msg();
}
interface Ds {}
class TestCase1 implements As, Ds {
    public void msg(){System.out.println("Hello");}
}

class Anna extends B implements As {
    int a = 10;
    public void msg(){System.out.println("Hello");}
}

class B extends D {
    void msg(){System.out.println("Welcome");}
}
class C extends TestCase1 {
    public static void main(String args[]){
        C obj = new C();
        obj.msg();
    }
}

class D implements Ds {}
