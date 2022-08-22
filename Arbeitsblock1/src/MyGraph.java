
import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MyGraph implements Graph {
    List<Integer> nodes;
    HashMap<Integer,List<Integer>> AL;

    public MyGraph(){
        nodes= new ArrayList<>();
        AL= new HashMap<>();
    }
    public MyGraph(Integer i){
        nodes= new ArrayList<>();
        AL= new HashMap<>();
        for (int j =1;j<=i;j++){
            nodes.add(j);
        }
    }
    public MyGraph (File file) throws IOException {
        BufferedReader graphReader = new BufferedReader(new FileReader(file));
        FindMax findMax = new FindMax();
        graphReader.lines().forEach(findMax);

        nodes= new ArrayList<>();
        AL= new HashMap<>();
        for (int j =1;j<=findMax.max;j++){
            nodes.add(j);
        }
        graphReader = new BufferedReader(new FileReader(file));
        graphReader.lines().forEach(this::addEdge);
    }

    public void addEdge(String line) {
        String[] split = line.split(" ");
        int from = Integer.parseInt(split[0]);
        int to = Integer.parseInt(split[1]);
        addEdge(from, to);
    }


    public void addVertex(Integer i){
        if (nodes.contains(i)){
            return;
        } else {
            nodes.add(i);
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
        if (nodes.contains(v)){
            nodes.remove(v);
        } else {
            return;
        }
    }

    public void deleteEdge(Integer i, Integer j) {
            AL.get(i).remove(j);
            AL.get(j).remove(i);
        }

    @Override
    public boolean contains(Integer v) {
        return nodes.contains(v);
    }

    @Override
    public int degree(Integer v) {
        return AL.get(v).size();
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
        copiedGraph.AL = (HashMap<Integer, List<Integer>>) AL.clone();
        copiedGraph.nodes = new ArrayList<>(nodes);
        return copiedGraph;
        }

    @Override
    public List<Integer> getNeighbors(Integer v) {
        return AL.get(v);
        }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public int getEdgeCount() {
        int sum = 0;
        for (Integer node:nodes){
            sum += AL.get(node).size();
        }
        return sum/2;
    }

    @Override
    public Set<Integer> getVertices() {
            return nodes.stream().collect(Collectors.toSet());
    }

    class FindMax implements Consumer<String> {
        int max=0;

        @Override
        public void accept(String s) {
            Arrays.stream(s.split(" ")).map(s1->Integer.parseInt(s1)).forEach(s1->{if (s1>max){max=s1;}});
        }
    }
}
