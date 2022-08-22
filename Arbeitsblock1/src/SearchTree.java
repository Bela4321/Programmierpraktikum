public class SearchTree {
    public class Instance {
        int limit;
        MyGraph g;

    }
    private boolean solve(Instance i) {
        int x = 0;
        if (i.limit < 0) {
            return false;
        }
        if (i.g.nodes == null) {
            return true;
        }
        //G besitzt mindestens eine Kante {u, v}
        i.g.deleteVertex(i.g.nodes.get(x));

        return true;

    }

    public int solve(vertexcover.Graph g) {
        return 0;
    }

    public MyGraph removeSingletonsv (MyGraph graph){
        while(!graph.nodes.isEmpty() ) {
            String element = graph.nodes.get(0) ;
            graph.nodes = graph.nodes.subList(1,graph.nodes.size());
            System.out.println(element);
    }

}
