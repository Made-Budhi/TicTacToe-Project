import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class History {
    History(String formattedDate, String nama1, String nama2, String pemainPemenang) throws IOException {
        LinkedList<String> tanggalWaktu = new LinkedList<>();
        LinkedList<String> namaSatu = new LinkedList<>();
        LinkedList<String> namaDua = new LinkedList<>();
        LinkedList<String> pemenang = new LinkedList<>();

        // Instansiasi filereader.
        FileReader getFileReader = new FileReader("DbHistory.txt");
        BufferedReader baca = new BufferedReader(getFileReader);

        // Instansiasi filewriter..
        FileWriter getFileWriter = new FileWriter("DbHistory.txt", true);
        BufferedWriter tulis = new BufferedWriter(getFileWriter);
        
        try {
            String data = baca.readLine();
            // Instansiasi string tokenizer
            StringTokenizer token = new StringTokenizer(data, ",");
            
            // Memindahkan data dari DbHistory ke LinkedList
            while (data != null) {
                if (namaSatu.size() < 10) {
                    token = new StringTokenizer(data, ",");
                    tanggalWaktu.add(token.nextToken());
                    namaSatu.add(token.nextToken());
                    namaDua.add(token.nextToken());
                    pemenang.add(token.nextToken());
                    data = baca.readLine();
                } else {
                    break;
                }
            }
            baca.close();

            tanggalWaktu.add(0, formattedDate);
            namaSatu.add(0, nama1);
            namaDua.add(0, nama2);
            pemenang.add(0, pemainPemenang);

            FileWriter getExistFile = new FileWriter("DbHistory.txt");
            BufferedWriter writeAgain = new BufferedWriter(getExistFile);

            for (int i = 0; i < namaSatu.size(); i++) {
                writeAgain.write(tanggalWaktu.get(i) + "," +  namaSatu.get(i) + "," + namaDua.get(i) + "," + pemenang.get(i));
                writeAgain.newLine();
            }
            writeAgain.close();
            
        } catch (Exception e) {
            tulis.write(formattedDate + "," +  nama1 + "," + nama2 + "," + pemainPemenang);
            tulis.newLine();
            tulis.close();
        }
    }
}
