package tag1;

import java.io.*;
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NaturalGasBilling {
    private Date invoiceDate;
    private Date fromDate;
    private Date toDate;
    private int billingDays;
    private double billedGJ;
    private double basicCharge;
    private double deliveryCharges;
    private double storageAndTransport;
    private double commodityCharges;
    private double tax;
    private double cleanEnergyLevy;
    private double carbonTax;
    private double amount;

    public NaturalGasBilling(String line){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] split = line.split(",");
        try{
            this.invoiceDate = sdf.parse(split[0]);
            this.fromDate =  sdf.parse(split[1]);
            this.toDate = sdf.parse(split[2]);}
        catch(ParseException e){
            System.out.println("Error parsing date");
            e.printStackTrace();
        }
        this.billingDays = Integer.parseInt(split[3]);
        this.billedGJ = Double.parseDouble(split[4]);
        this.basicCharge = Double.parseDouble(split[5]);
        this.deliveryCharges = Double.parseDouble(split[6]);
        this.storageAndTransport = Double.parseDouble(split[7]);
        this.commodityCharges = Double.parseDouble(split[8]);
        this.tax = Double.parseDouble(split[9]);
        if (split[10].equals("")){
            this.cleanEnergyLevy = 0;
        }
        else{
            this.cleanEnergyLevy = Double.parseDouble(split[10]);
        }
        this.carbonTax = Double.parseDouble(split[11]);
        this.amount = Double.parseDouble(split[12]);
    }

    public static Stream<NaturalGasBilling> orderByInvoiceDateDesc(Stream<String> stream){
        return stream.map(NaturalGasBilling::new).sorted((a,b)->b.invoiceDate.compareTo(a.invoiceDate));
    }

    public Stream<Byte> toBytes(){
        return Stream.of(
                "\n",
                (invoiceDate.getYear()+1900)+"-"+(invoiceDate.getMonth()+1)+"-"+invoiceDate.getDate()+",",
                (fromDate.getYear()+1900)+"-"+(fromDate.getMonth()+1)+"-"+fromDate.getDate()+",",
                (toDate.getYear()+1900)+"-"+(toDate.getMonth()+1)+"-"+toDate.getDate()+",",
                Integer.toString(billingDays)+",",
                Double.toString(billedGJ)+",",
                Double.toString(basicCharge)+",",
                Double.toString(deliveryCharges)+",",
                Double.toString(storageAndTransport)+",",
                Double.toString(commodityCharges)+",",
                Double.toString(tax)+",",
                Double.toString(cleanEnergyLevy)+",",
                Double.toString(carbonTax)+",",
                Double.toString(amount)
        ).map(x->x.chars()).flatMap(x->x.mapToObj(y->(byte)y));
    }

    public static Stream<Byte> serialize(Stream<NaturalGasBilling> stream){
        return stream.flatMap(NaturalGasBilling::toBytes);
    }

    public static void sortAndSave(String filepath, String savepath) throws IOException {
        Stream<String> stream= StreamingJava.fileLines(filepath);
        Stream<NaturalGasBilling> stream2 = orderByInvoiceDateDesc(stream);
        Stream<Byte> stream3 = serialize(stream2);
        OutputStream outputStream = new FileOutputStream(savepath);
        outputStream.write(new String("Invoice Date,From Date,To Date,Billing Days,Billed GJ,Basic charge,Delivery charges,Storage and transport,Commodity charges,Tax,Clean energy levy,Carbon tax,Amount").getBytes());
        stream3.forEach(x-> {
            try {
                outputStream.write(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public static void main(String[] args) throws IOException {
        sortAndSave("Arbeitsblock 3/src/NaturalGasBilling.csv", "Arbeitsblock 3/src/NaturalGasBillingSortedCopy.csv");
    }

}
