public class SearchTree {
    public class Instance {
        int limit;
        MyGraph g;

    }
    private boolean solve(Instance i) throws Exception {
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
        int e2=-1;
        for (int key:i.g.AL.keySet()){
            if (i.g.AL.get(key)!=null &&i.g.AL.get(key).size()!=0){
                e1=key;
                e2=i.g.AL.get(key).get(0);
                break;
            }
        }
        if (e1==-1||e2==-1){
            throw new Exception("Graph is not empty but empty?");
        }

        //new instances
        Instance i1 = new Instance();
        i1.g= i.g.getCopy();
        i1.g.deleteVertex(e1);
        i1.limit= i.limit-1;

        Instance i2 = new Instance();
        i2.g= i.g.getCopy();
        i2.g.deleteVertex(e2);
        i2.limit= i.limit-1;


        return solve(i1)||solve(i2);

    }



    public int solve(MyGraph g) throws Exception {
        int k=0;
        Instance i=new Instance();
        i.g=g.getCopy();
        i.limit=k;
        while (!solve(i)){
            k++;
            i.limit=k;
            i.g=g.getCopy();
        }
        return k;
    }

    public void insightSolve(MyGraph g) throws Exception {
        int v=g.size();
        int e =g.getEdgeCount();
        int k;
        long time= System.nanoTime();
        k= solve(g);
        time =System.nanoTime()-time;
        double newTime=time*Math.pow(10,-9);

        System.out.println("------------------------------");
        System.out.println("|V| = "+v);
        System.out.println("|E| = "+e);
        System.out.println("k = "+k);
        System.out.printf("Rumtime = %.5f s",newTime);
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
