import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.*;

import java.io.File;
import java.io.IOException;

public class ConstructInheritanceTree {
    public static void main(String[] args) {
        if (args.length == 1)
            parseFile(args[0]);
        else {
            File testCaseFolder = new File("./testInput/");
            File[] files = testCaseFolder.listFiles((dir, name) -> name.endsWith(".java"));
            if (files == null)
                return;
            for (File file : files) {
                System.out.println("Test case " + file.getName().replaceAll("\\D+",""));
                parseFile(file.getPath());
                System.out.println("-".repeat(30) + "\n\n");
            }
        }
    }

    public static void parseFile(String... args) {
        if (args.length != 1) {
            System.out.println("Please enter ONE input file name");
            return;
        }

        try {
            String inputFile = args[0];

            // Throws IOException in case file was not found
            // Breaks file into character stream
            CharStream cs = CharStreams.fromFileName(inputFile);

            // Convert a sequence of characters into sequence of tokens
            JavaLexer lexer = new JavaLexer(cs);

            // Create a buffer of tokens from pulled from the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // Create a parser from tokens buffer
            JavaParser parser = new JavaParser(tokens);

            // Begin parsing at init rule
            parser.removeErrorListeners();
            parser.addErrorListener(ThrowingErrorListener.INSTANCE);

            // Parse a compilationUnit
            ParseTree tree = parser.compilationUnit();

            // Create a generic parse tree walker that can trigger listeners waiting for event
            ParseTreeWalker walker = new ParseTreeWalker();

            ConstructInheritanceTreeListener inheritanceTree = new ConstructInheritanceTreeListener();

            // Initiate walk of tree with listener
            walker.walk(inheritanceTree,tree);

            // Try to compile file input and check if it to compilable
            runProcess(inputFile);

            inheritanceTree.printInheritanceTree();


        } catch (IOException e) {
            System.out.println("File not found");
        } catch (NotRunnableProcess | InterruptedException e){
            System.out.println(e.toString());
        } catch (ParseCancellationException e){
            System.out.println(e.getMessage());
        }
    }

    private static void runProcess(String inputFile) throws NotRunnableProcess , IOException ,InterruptedException {
        Process process = Runtime.getRuntime().exec("javac " + inputFile);
        process.waitFor();
        if (process.exitValue() != 0) {
            throw new NotRunnableProcess(process);
        }
    }
}