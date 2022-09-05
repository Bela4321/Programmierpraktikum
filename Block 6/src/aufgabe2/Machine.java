package aufgabe2;

import java.util.HashMap;
import java.util.Map;

public class Machine {
    int[] boxes;

    public void setN(int N){
        boxes = new int[N+1];
    }


    public void set(int L, int R, int A, int B){
        if (L > R||R>boxes.length||L<0) {
            throw new IllegalArgumentException();
        }
        int x = L;
        while (x <= R) {
            boxes[x] = ((x-L+1)*A)%B;
            x++;
        }
    }

    public void get(int L, int R){
        if (L > R||R>boxes.length||L<0) {
            throw new IllegalArgumentException();
        }
        int x = L;
        int sum = 0;
        while (x <= R) {
            sum += boxes[x];
            x++;
        }
        System.out.println(sum);
    }
}
