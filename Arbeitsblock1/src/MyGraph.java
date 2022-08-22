
import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MyGraph implements Graph {
    Map<Integer, Boolean> nodes = new HashMap<Integer, Boolean>(){
        public Boolean get(Integer key) {
            if(! containsKey(key))
                return false;
            return super.get(key);
        }
    };
    HashMap<Integer,List<Integer>> AL;

    public MyGraph(){
        AL= new HashMap<>();
    }
    public MyGraph(Integer i){
        AL= new HashMap<>();
        for (int j =1;j<=i;j++){
            nodes.put(j,true);
        }
    }
    public MyGraph (File file) throws IOException {
        BufferedReader graphReader = new BufferedReader(new FileReader(file));
        FindMax findMax = new FindMax();
        graphReader.lines().forEach(findMax);

        AL= new HashMap<>();
        for (int j =1;j<=findMax.max;j++){
            nodes.put(j,true);
        }
        graphReader = new BufferedReader(new FileReader(file));
        graphReader.lines().forEach(this::addEdge);
    }

    public void addEdge(String line) {
        if (line.isEmpty()) return;
        String[] split = line.split("[ \t]");
        int from = Integer.parseInt(split[0]);
        int to = Integer.parseInt(split[1]);
        addEdge(from, to);
    }


    public void addVertex(Integer i){
        if (nodes.get(i)){
            return;
        } else {
            nodes.put(i,true);
            AL.put(i,new ArrayList<>());
        }
    }
    public void addEdge(Integer i, Integer j) {
        if (!AL.containsKey(i)){
            AL.put(i,new ArrayList<>());
            AL.get(i).add(j);
        } else {
            AL.get(i).add(j);
        }
        if (!AL.containsKey(j)){
            AL.put(j,new ArrayList<>());
            AL.get(j).add(i);
        } else {
            AL.get(j).add(i);
        }
    }

    @Override
    public void deleteVertex(Integer v) {
        if (contains(v)){
            nodes.remove(v);
        } else {
            return;
        }
        // remove all edges from v
        for (Integer neighbour : AL.get(v)) {
            AL.get(neighbour).remove(v);
        }
        AL.get(v).clear();
    }

    public void deleteEdge(Integer i, Integer j) {
            AL.get(i).remove(j);
            AL.get(j).remove(i);
        }

    @Override
    public boolean contains(Integer v) {
        if (nodes.get(v)==null){
            return false;
        } else {
            return nodes.get(v);
        }
    }

    @Override
    public int degree(Integer v) {
        if (AL.get(v)==null){
            return 0;
        } else {
            return AL.get(v).size();
        }
    }

    @Override
    public boolean adjacent(Integer v, Integer w) {
        if (AL.get(v).contains(w)) {
            return true;
        }
            else {
                return false;
        }
    }

    @Override
    public MyGraph getCopy() {
        MyGraph copiedGraph = new MyGraph();
        copiedGraph.AL = new HashMap<>();
        for (Integer key : AL.keySet()) {
            copiedGraph.AL.put(key, new ArrayList<>(AL.get(key)));
        }
        copiedGraph.nodes =  new HashMap<>();
        for (Integer key : nodes.keySet()) {
            copiedGraph.nodes.put(key, nodes.get(key));
        }
        return copiedGraph;
        }

    @Override
    public List<Integer> getNeighbors(Integer v) {
        List<Integer> neighbours = new ArrayList<>();
        neighbours.addAll(AL.get(v));
        return neighbours;
        }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public int getEdgeCount() {
        int sum = 0;
        for (Integer node:AL.keySet()){
            sum += AL.get(node).size();
        }
        return sum/2;
    }

    @Override
    public Set<Integer> getVertices() {
            return nodes.keySet().stream().collect(Collectors.toSet());
    }

    class FindMax implements Consumer<String> {
        int max=0;

        @Override
        public void accept(String s) {
            if (s.equals("")){
                return;
            }
            Arrays.stream(s.split("[ \t]")).map(s1->Integer.parseInt(s1)).forEach(s1->{if (s1>max){max=s1;}});
        }
    }
}
