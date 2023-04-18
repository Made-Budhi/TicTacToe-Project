import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Leaderboard { 
    Leaderboard(String userName) throws IOException {
        // Penyimpanan database sementara
        ArrayList<String> namaUser = new ArrayList<String>();
        ArrayList<Integer> score = new ArrayList<Integer>();

        // Instansiasi filereader.
        FileReader getFileReader = new FileReader("DbLeaderboard.txt");
        BufferedReader baca = new BufferedReader(getFileReader);

        // Instansiasi filewriter..
        FileWriter getFileWriter = new FileWriter("DbLeaderboard.txt", true);
        BufferedWriter tulis = new BufferedWriter(getFileWriter);

        try {
            String data = baca.readLine();
            // Instansiasi string tokenizer
            StringTokenizer token = new StringTokenizer(data, ",");

            // Memindahkan data dari database ke arraylist
            while (data != null) {
                token = new StringTokenizer(data, ",");
                namaUser.add(token.nextToken());
                score.add(Integer.parseInt(token.nextToken()));
                data = baca.readLine();
            }
            baca.close();

            // Linear Searching untuk mengecek duplikasi data
            int mark = 0;
            boolean duplikasi = false;

            for (int x = 0; x < namaUser.size(); x++) {
                if (userName.equals(namaUser.get(x))) {
                    duplikasi = true;
                    mark = x;
                    break;
                }
            }

            // Add nama user terbaru beserta pointnya ke dalam arraylist
            if (duplikasi == true) {
                score.set(mark, score.get(mark) + 1);
            } 

            else {
                namaUser.add(userName);
                score.add(1);
            }

            /**
             * 
             * Selection Sort untuk mengurutkan pemain
             * dari skor terbesar - terkecil.
             * 
             * @int max             Merupakan data skor tertinggi.
             * @String maxName      Merupakan username yang memiliki skor @int max.
             * @int x               Merupakan index yang memiliki skor tertinggi.
             * 
             */
            for (int i = 0; i < score.size(); i++) {
                int max = score.get(i);
                String maxName = namaUser.get(i);
                int x = 0;
                for (int j = i; j < score.size(); j++) {
                    if (max < score.get(j)) {
                        max = score.get(j);
                        maxName = namaUser.get(j);
                        x = j;
                    }
                }

                // Menukar posisi ArrayList apabila belum tersortir dengan benar.
                if (max != score.get(i)) {

                    String tempName = namaUser.get(i);
                    int temp = score.get(i);

                    namaUser.set(i, maxName);
                    score.set(i, max);

                    namaUser.set(x, tempName);
                    score.set(x, temp);
                }
            }

            /**
             * Menulis ulang data username dan skor yang telah tersortir sedemikian rupa.
             * Penulisan ulang dilakukan guna untuk mengetahui pemain dengan skor teringgi.
             */
            FileWriter getExistFile = new FileWriter("DbLeaderboard.txt");
            BufferedWriter writeAgain = new BufferedWriter(getExistFile);

            for (int i = 0; i < score.size(); i++) {
                writeAgain.write(namaUser.get(i) + "," + score.get(i));
                writeAgain.newLine();
            }
            writeAgain.close();

        } catch (Exception e) {
            tulis.write(userName + "," + 1);
            tulis.newLine();
            tulis.close();
        }
    }
}
