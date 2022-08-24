package PPClean.Similarity;

import java.util.HashSet;
import java.util.Set;

/**
 * Jaccard String similarity
 */
public class Jaccard implements StringSimilarity {

    int n;

    /**
     * @param n Length of substrings
     */
    public Jaccard(int n) {
        this.n = n;
    }

    /**
     * Calculates Jaccard String similarity for x and y, using ngrams of length {@link #n}
     * @param x
     * @param y
     * @return Similarity score in range [0,1]
     */
    @Override
    public double compare(String x, String y) {
        double res = 0;
        Set<String> ngramsX = new HashSet<>();
        Set<String> ngramsY = new HashSet<>();
        // BEGIN SOLUTION
        for (int k=0;k+n<=x.length();k++){
            ngramsX.add(x.substring(k,k+n));
        }
        for (int k=0;k+n<=y.length();k++){
            ngramsY.add(y.substring(k,k+n));
        }
        int overlapCount=0;
        Set<String> overlap= new HashSet<>();
        for (String key:ngramsX) {
            if (ngramsY.contains(key)&&!overlap.contains(key)){
                overlapCount++;
                overlap.add(key);
            }
        }
        res=(double)overlapCount/(ngramsX.size()+ngramsY.size()-overlapCount);

        // END SOLUTION
        return res;
    }
}
