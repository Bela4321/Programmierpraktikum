import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) throws Exception {
        SearchTree st = new SearchTree();
        //get files from res
        File folder = new File("Arbeitsblock1/res");
        File[] listOfFiles = folder.listFiles();
        Arrays.sort(listOfFiles,(f1, f2) ->(int) (f1.length()-f2.length()));

        for (File file: listOfFiles){
            MyGraph g = new MyGraph(file);
            System.out.println(file.getName());
            st.insightSolve(g);
        }
    }
}
