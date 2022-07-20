import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {

        
        File file = new File("<FILEPATH>");
        UnweightedGraphs ug = new UnweightedGraphs(file);
        
        ug.breadthFirstSearch(5);
        ug.printUG();
    }
}