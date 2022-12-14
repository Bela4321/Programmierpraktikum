package tag1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamingJava {
    // Aufgabe 2) a)
    public static <E> Stream<E> flatStreamOf(List<List<E>> list) {
        // TODO
        //return onedimensional stream
        return list
                .stream()
                .flatMap(Collection::stream);
    }

    // Aufgabe 2) b)
    public static <E> Stream<E> mergeStreamsOf(Stream<Stream<E>> stream) {
        // TODO
        //merge to one stream with reduce
        return stream.reduce(Stream.empty(), Stream::concat);
    }

    // Aufgabe 2) c)
    public static <E extends Comparable<? super E>> E minOf(List<List<E>> list) {
        // TODO
        //return min value of list
        return list.stream().flatMap(x -> x.stream()).min(Comparator.naturalOrder()).orElseThrow();
    }

    // Aufgabe 2) d)
    public static <E> E lastWithOf(Stream<E> stream, Predicate<? super E> predicate) {
        // TODO
        //return last element of stream with predicate
        return stream.filter(predicate).reduce(null, (first, second) -> second);
    }

    // Aufgabe 2) e)
    public static <E> Set<E> findOfCount(Stream<E> stream, int count) {
        // TODO
        //return set of elements with count
        return stream
                .collect(Collectors.groupingBy(x ->x , Collectors.counting()))
                .entrySet().stream()
                .filter(x -> x.getValue() == count)
                .map(x -> x.getKey())
                .collect(Collectors.toSet());
    }

    // Aufgabe 2) f)
    public static IntStream makeStreamOf(String[] strings) {
        // TODO
        //return stream of ints
        return Arrays.stream(strings).flatMapToInt(x -> x.chars());
    }

//-------------------------------------------------------------------------------------------------

    // Aufgabe 3) a)
    public static Stream<String> fileLines(String path) throws IOException {
        // TODO
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine();
        return reader.lines();
    }

    // Aufgabe 3) b)
    public static double averageCost(Stream<String> lines) {
        // TODO
        return lines.map(x -> x.split(";")).mapToDouble(x ->Double.parseDouble(x[12])).average().getAsDouble();
    }

    // Aufgabe 3) c)
    public static long countCleanEnergyLevy(Stream<String> stream) {
        // TODO
        return stream.map(x -> x.split(";")).filter(x -> x[10].isEmpty()||Double.parseDouble(x[10])==0).count();
    }

    // Aufgabe 3) d)
    // TODO:
    //  1. Create record "NaturalGasBilling".
    //  2. Implement static method: "Stream<NaturalGasBilling> orderByInvoiceDateDesc(Stream<String> stream)".

    static record NaturalGasBilling(String invoiceDate, String fromDate, String toDate, int billingDays, double billedGJ, double basicCharge, double deliveryCharges, double storageAndTransport, double commodityCharges, double tax, double cleanEnergyLevy, double carbonTax, double amount) {
        public static Stream<NaturalGasBilling> orderByInvoiceDateDesc(Stream<String> stream) {
            return stream.map(x -> x.split(";")).map(x -> new NaturalGasBilling(x[0], x[1], x[2], Integer.parseInt(x[3]), Double.parseDouble(x[4]), Double.parseDouble(x[5]), Double.parseDouble(x[6]), Double.parseDouble(x[7]), Double.parseDouble(x[8]), Double.parseDouble(x[9]), Double.parseDouble(x[10]), Double.parseDouble(x[11]), Double.parseDouble(x[12]))).sorted(Comparator.comparing(NaturalGasBilling::invoiceDate).reversed());
        }
    }

    // Aufgabe 3) e)
    // TODO: Implement object method: "Stream<Byte> toBytes()" for record "NaturalGasBilling".

    // Aufgabe 3) f)
    // TODO: Implement static method: "Stream<Byte> serialize(Stream<NaturalGasBilling> stream)".

    // Aufgabe 3) g)
    // TODO: Implement static method: "Stream<NaturalGasBilling> deserialize(Stream<Byte> stream)".
    // TODO: Execute the call: "deserialize(serialize(orderByInvoiceDateDesc(fileLines(Datei aus f))))"
    // TODO: in a main Method and print the output to the console.

    // Aufgabe 3) h)
    public static Stream<File> findFilesWith(String dir, String startsWith, String endsWith, int maxFiles) {
        File folder = new File(dir);
        return Arrays.stream(folder.listFiles()).filter(x -> x.getName().startsWith(startsWith) && x.getName().endsWith(endsWith)).sorted(Comparator.comparing(File::length)).limit(maxFiles);
    }

    private static void walkHelper(File file){
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                walkHelper(f);
            }
        } else if (file.getName().endsWith(".java")) {
            System.out.println(file.getName());
        }
    }

    public static void main(String[] args) {
        walkHelper(new File("Arbeitsblock 3"));
    }
}
