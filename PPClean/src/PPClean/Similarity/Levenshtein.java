package PPClean.Similarity;

/**
 * Levenshtein String similarity
 */
public class Levenshtein implements StringSimilarity {
    public Levenshtein() {
    }

    /**
     * Calculates Levenshtein String similarity for x and y
     * @param x
     * @param y
     * @return Similarity score in range [0,1]
     */
    @Override
    public double compare(String x, String y) {
        double res = 0;
        int m = x.length();
        int n = y.length();
        // BEGIN SOLUTION
        if(x.equals(y)){return 1;}
        if(n==0){return 1-(double)m/Math.max(m,n);}
        if(m==0){return 1-(double)n/Math.max(m,n);}

        //create matrix
        int[][] matrix = new int[m+1][n+1];
        for (int i=0;i<m+1;i++) {
            matrix[i][0]=i;
        }
        for (int i=0;i<n+1;i++) {
            matrix[0][i]=i;
        }

        for (int i=1;i<=m;i++){
            for (int j=1; j<=n;j++){
                if (x.charAt(i-1)==y.charAt(j-1)){
                    matrix[i][j]=matrix[i-1][j-1];
                } else
                    matrix[i][j]=1+Math.min(matrix[i][j-1],Math.min(matrix[i-1][j],matrix[i-1][j-1]));
            }
        }

        res= 1-(double)matrix[m][n]/Math.max(m,n);
        // END SOLUTION
        return res;
    }
}
