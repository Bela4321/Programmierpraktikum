public class SearchTree {
    public class Instance {
        int limit;
        MyGraph g;
        int k;

    }
    private boolean solve(Instance i) {
        if (i.limit < 0) {
            return false;
        }
        if (i.g.nodes.size() == 0) {
            return true;
        }
        //G besitzt mindestens eine Kante {u, v}
        //get any edge





        return false;

    }

    public int solve(Graph g) {
        return 0;
    }

    public MyGraph removeSingletonsv (MyGraph graph){
        while(!graph.nodes.isEmpty() ) {
            String element = graph.nodes.get(0) ;
            graph.nodes = graph.nodes.subList(1,graph.nodes.size());
            System.out.println(element);
    }

}
