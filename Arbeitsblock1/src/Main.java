import java.io.File;

public class Main {


    public static void main(String[] args) throws Exception {
        //get files from res
        File folder = new File("res");
        File[] listOfFiles = folder.listFiles();

        File testFile = new File("Arbeitsblock1/res/ca-sandi_authsmtx.sec");

        MyGraph g = new MyGraph(testFile);


        int k=1;
        k=2;

    }
}
