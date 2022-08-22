public class SearchTree {
    public class Instance {
        int limit;
        MyGraph g;

    }
    private boolean solve(Instance i) {
        if (i.limit < 0) {
            return false;
        }
        if (i.g.getEdgeCount() == 0) {
            return true;
        }
        //G besitzt mindestens eine Kante {u, v}
        //get any edge
        int e1;
        int e2;
        for (int key:i.g.AL.keySet()){
            if (i.g.AL.get(key)!=null ||i.g.AL.get(key).size()!=0){
                e1=key;
                e2=i.g.AL.get(key).get(0);
                break;
            }
        }
        //simplify
        removeSingletonsv(i);
        removeDegone(i);
        removeHighDeg(i);

        //new instances
        Instance i1 = new Instance();
        i1.g= i.g.getCopy();
        i1.limit= i.limit-1;
        Instance i2 = new Instance();
        i2.g= i.g.getCopy();
        i2.limit= i.limit-1;


        return solve(i1)||solve(i2);

    }

    private void removeVertex(Instance i, Integer vertex){
        for (Integer neighbour :i.g.AL.get(vertex)) {
            i.g.AL.get(neighbour).remove(vertex);
        }
        i.g.AL.put(vertex, null);
        i.g.nodes.remove(vertex);
    }


    public int solve(MyGraph g) {
        int k=0;
        Instance i=new Instance();
        i.g=g;
        i.limit=k;
        while (!solve(i)){
            i.limit++;
        }
        return i.limit;
    }

    public void insightSolve(MyGraph g){
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
        for (int vertex: i.g.nodes) {
            if(i.g.AL.get(vertex)==null||i.g.AL.get(vertex).size()==0) {
                i.g.deleteVertex(vertex);
                i.limit--;
            }
        }
    }
    public void removeDegone(Instance i){
        for (int vertex: i.g.nodes) {
            if(i.g.degree(vertex)==1) {
                i.g.deleteVertex(i.g.AL.get(vertex).get(0));
                i.limit--;
            }
        }
    }
    public void removeHighDeg(Instance i) {
        for (int vertex: i.g.nodes) {
            if(i.g.degree(vertex)>i.limit) {
                i.g.deleteVertex(i.g.AL.get(vertex).get(0));
                i.limit--;
            }
        }
    }

}
