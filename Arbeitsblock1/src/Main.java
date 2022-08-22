import java.io.File;

public class Main {


    public static void main(String[] args) throws Exception {
        SearchTree st = new SearchTree();
        //get files from res
        File folder = new File("Arbeitsblock1/res");
        File[] listOfFiles = folder.listFiles();

        for (File file: listOfFiles){
            MyGraph g = new MyGraph(file);
            st.insightSolve(g);
        }
    }
}
