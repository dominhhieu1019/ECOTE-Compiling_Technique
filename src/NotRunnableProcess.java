import java.util.Scanner;

public class NotRunnableProcess extends Exception {

    Process proc;

    NotRunnableProcess(Process proc){
        this.proc = proc;
    }

    @Override
    public String toString() {
        String errorMsg = (new Scanner(proc.getErrorStream())).useDelimiter("\\A").next();
        return "Input file is not compilable \n" + errorMsg;
    }
}