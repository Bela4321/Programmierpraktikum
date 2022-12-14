
public class SearchTree {
    public class Instance {
        int limit;
        MyGraph g;

    }
    private boolean solve(Instance i, long time) throws Exception {
        //check timing
        if (System.nanoTime()-time>(long)60*(long)1000000000){
            return true;
        }

        if (i.g.getEdgeCount() <= i.limit) {
            return true;
        }
        //simplify
        removeSingletonsv(i);
        removeDegone(i);
        removeHighDeg(i);

        if (i.limit < 0) {
            return false;
        }
        if (i.g.getEdgeCount() <= 0) {
            return true;
        }
        //G besitzt mindestens eine Kante {u, v}
        //get any edge
        int e1=-1;
        for (int key:i.g.AL.keySet()){
            if (i.g.AL.get(key)!=null &&i.g.AL.get(key).size()!=0){
                e1=key;
                break;
            }
        }
        if (e1==-1){
            throw new Exception("Graph is not empty but empty?");
        }

        //new instances
        Instance i1 = new Instance();
        i1.g= i.g.getCopy();
        i1.g.deleteVertex(e1);
        i1.limit= i.limit-1;

        Instance i2 = new Instance();
        i2.g= i.g.getCopy();
        i2.limit= i.limit-i2.g.getNeighbors(e1).size();

        for (int node:i2.g.getNeighbors(e1)){
            i2.g.deleteVertex(node);
        }

        return solve(i1, time)||solve(i2, time);

    }



    public int solve(MyGraph g, long time) throws Exception {
        int k=0;
        Instance i=new Instance();
        i.g=g.getCopy();
        i.limit=k;
        int[] stepsizes= {10,1};
        for (int step:stepsizes){
            while (!solve(i, time)){
                k+=step;
                i.limit=k;
                i.g=g.getCopy();
            }
            k-=step;
            i.limit=k;
            i.g=g.getCopy();
        }
        return k+1;
    }

    public void insightSolve(MyGraph g) throws Exception {
        int v=g.size();
        int e =g.getEdgeCount();
        int k;
        long time= System.nanoTime();
        k= solve(g, time);
        time =System.nanoTime()-time;
        double newTime=time*Math.pow(10,-9);
        if (newTime>60){
            newTime=-1;
        }

        System.out.println("|V| = "+v);
        System.out.println("|E| = "+e);
        System.out.println("k = "+k);
        System.out.printf("Rumtime = %.5fs\n",newTime);
        System.out.println("------------------------------");
    }

    public void removeSingletonsv (Instance i){
        for (int key:i.g.AL.keySet()){
            if (i.g.AL.get(key).size()==0){
                i.g.deleteVertex(key);
            }
        }
    }
    public void removeDegone(Instance i){
        for (int key:i.g.AL.keySet()){
            if(i.g.degree(key)==1) {
                i.g.deleteVertex(i.g.AL.get(key).get(0));
                i.limit--;
            }
        }
    }
    public void removeHighDeg(Instance i) {
        for (int key:i.g.AL.keySet()) {
            if(i.limit>0&&i.g.degree(key)>i.limit) {
                i.g.deleteVertex(key);
                i.limit--;
            }
        }
    }
}