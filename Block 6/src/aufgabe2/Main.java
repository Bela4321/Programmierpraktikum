package aufgabe2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Block 6/src/aufgabe2/input.txt"));;

        Machine machine = new Machine();
        machine.setN(Integer.parseInt(br.readLine().split(" ")[0]));
        while (br.ready()) {
            String line = br.readLine();
            if (line.equals("")){
                continue;
            }
            String[] parts = line.split(" ");
            if (parts[0].equals("1")){
                machine.set(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
            } else {
                machine.get(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            }
        }
    }
}
