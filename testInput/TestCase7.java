interface As {
    void msg();
}
interface Ds {}
class TestCase7 implements As, Ds {
    Scanner s = new Scanner(System.in);
    public void msg(){System.out.println("Hello");}
}
