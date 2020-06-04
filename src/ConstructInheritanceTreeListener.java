import java.util.*;

public class ConstructInheritanceTreeListener extends JavaParserBaseListener {
    
    HashMap<String, ArrayList<String>> classHierarchy;
    HashMap<String, String> interfaceSet;
    private final String root = "Object";

    public ConstructInheritanceTreeListener() {
        classHierarchy = new HashMap<>();
        classHierarchy.put(root, new ArrayList<>());
        interfaceSet = new HashMap<>();
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) { 
        String className = ctx.IDENTIFIER().getText();
        String parentClass = "";
        if (ctx.EXTENDS() != null) {
            parentClass = ctx.typeType().getText();
        }  

        if (parentClass.isEmpty()) {
            classHierarchy.get(root).add(className);
        } else if (classHierarchy.containsKey(parentClass)) {
            classHierarchy.get(parentClass).add(className);
        } else {
            classHierarchy.put(parentClass, new ArrayList<>(Arrays.asList(className)));
        }

        String interfaceList = "";
        if (ctx.IMPLEMENTS() != null) {
            interfaceList = ctx.typeList().getText();
        }

        if (!interfaceList.isEmpty()) {
            interfaceSet.put(className, interfaceList);
        }
    }

    public void printTree(String cl, int depth) {
        System.out.print("\t".repeat(depth));
        if (depth != 0) {
            System.out.print("|-- ");
        }
        System.out.print(cl);

        if (interfaceSet.containsKey(cl)) {
            System.out.print(": " + interfaceSet.get(cl));
        }
        System.out.println();

        if (classHierarchy.containsKey(cl)) {
            for (String child : classHierarchy.get(cl)) {
                printTree(child, depth + 1);
            }
        }
    }

    public void printInheritanceTree() {
        printTree(root, 0);
    }

}