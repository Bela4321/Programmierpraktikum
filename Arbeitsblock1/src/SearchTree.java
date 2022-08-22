public class SearchTree {
    public class Instance {
        int limit;
        MyGraph g;

    }
    private boolean solve(Instance i) throws Exception {
        //simplify
        removeSingletonsv(i);
        removeDegone(i);
        removeHighDeg(i);

        if (i.limit < 0) {
            return false;
        }
        if (i.g.getEdgeCount() == 0) {
            return true;
        }



        //G besitzt mindestens eine Kante {u, v}
        //get any edge
        int e1=-1;
        int e2=-1;
        for (int key:i.g.AL.keySet()){
            if (i.g.AL.get(key)!=null ||i.g.AL.get(key).size()!=0){
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

    private void removeVertex(Instance i, Integer vertex){
        for (Integer neighbour :i.g.AL.get(vertex)) {
            i.g.AL.get(neighbour).remove(vertex);
        }
        i.g.AL.put(vertex, null);
        i.g.nodes.remove(vertex);
    }


    public int solve(MyGraph g) throws Exception {
        int k=g.size()+1;
        Instance i=new Instance();
        i.g=g;
        i.limit=k;
        while (k>=0){
            i.limit=--k;
            if (solve(i)){
                return k;
            }
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
        for (int j=0;j<i.g.nodes.size();j++) {
            if(i.g.AL.get(i.g.nodes.get(j))==null||i.g.AL.get(i.g.nodes.get(j)).size()==0) {
                i.g.deleteVertex(i.g.nodes.get(j));
                i.limit--;
            }
        }
    }
    public void removeDegone(Instance i){
        for (int j=0;j<i.g.nodes.size();j++){
            if(i.g.degree(i.g.nodes.get(j))==1) {
                i.g.deleteVertex(i.g.AL.get(i.g.nodes.get(j)).get(0));
                i.limit--;
            }
        }
    }
    public void removeHighDeg(Instance i) {
        for (int j=0;j<i.g.nodes.size();j++) {
            if(i.g.degree(i.g.nodes.get(j))>i.limit) {
                i.g.deleteVertex(i.g.AL.get(i.g.nodes.get(j)).get(0));
                i.limit--;
            }
        }
    }

}
