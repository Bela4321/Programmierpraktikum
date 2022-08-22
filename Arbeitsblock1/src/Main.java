import java.io.File;

public class Main {


    public static void main(String[] args) throws Exception {
        SearchTree st = new SearchTree();
        //get files from res
        File folder = new File("res");
        File[] listOfFiles = folder.listFiles();

        File testFile = new File("Arbeitsblock1/res/sample2.sec");


        MyGraph g = new MyGraph(testFile);
        long time= System.nanoTime();
        System.out.println(st.solve(g));
        time =System.nanoTime()-time;
        System.out.println(time*Math.pow(10,-9));


    }
}
