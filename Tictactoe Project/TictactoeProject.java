import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TictactoeProject {
	static ArrayList<Integer> posisiX = new ArrayList<>();
	static ArrayList<Integer> posisiO = new ArrayList<>();
	static Random acak = new Random();
	static Scanner input = new Scanner(System.in);
	static int giliranPemain = 0, pilihMode, pilihMenu;
	static String nama1, nama2, pemainPemenang;

	static char[][] papan = {
			{ ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ' },
			{ ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ' },
			{ ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ' },
			{ '=', '=', '=', '=', '+', '+', '=', '=', '=', '=', '=', '+', '+', '=', '=', '=', '=' },
			{ ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ' },
			{ ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ' },
			{ ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ' },
			{ '=', '=', '=', '=', '+', '+', '=', '=', '=', '=', '=', '+', '+', '=', '=', '=', '=' },
			{ ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ' },
			{ ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ' },
			{ ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ', ' ', ' ', '|', '|', ' ', ' ', ' ' },
	};

	public static void main(String[] args) throws IOException {
		ticTacToe();
		mainMenu();
	}

	/**
	 * Membaca file yang digunakan sebagai judul utama.
	 * Menggunakan BufferedReader dan FileReader.
	 * 
	 * @file TicTacToe.txt
	 * @throws IOException
	 */
	public static void ticTacToe() throws IOException {
		try {
			BufferedReader baca = new BufferedReader(new FileReader("TicTacToe.txt"));

			String database = baca.readLine();

			while (database != null) {
				System.out.println(database);
				database = baca.readLine();
			}
			baca.close();
		} catch (Exception e) {
			System.out.println("Tidak ada data");
		}
	}

	/**
	 * 1. Membaca file txt untuk menampilkan Menu Utama (Main Menu).
	 * 2. Berisi menu-menu yang dapat dipilih user dengan menginput nomor.
	 * 3. Setelah menginput nomor, method tertentu akan dipanggil sesuai dengan menu yang dipilih.
	 * 
	 * @file MainMenu.txt
	 * @throws IOException
	 */
	public static void mainMenu() throws IOException {
    	try {
			BufferedReader baca = new BufferedReader(new FileReader("MainMenu.txt"));

			String database = baca.readLine();

			while (database != null) {
				System.out.println(database);
				database = baca.readLine();
			}
			baca.close();
		} catch (Exception e) {
			System.out.println("Tidak ada data");
		}
		// User meng-input pilihan menu
		System.out.printf("\nPilih Menu : ");
		pilihMenu = input.nextInt();

		while (!(pilihMenu >= 1 && pilihMenu <= 5)) {
			System.out.print("Masukan pilihan menu dengan benar : ");
			pilihMenu = input.nextInt();
		}

		switch (pilihMenu) {
			case 1:
				System.out.printf("%nSilahkan pilih mode bermain!%n%n [ 1 ] Single Player %n [ 2 ] Multi Player%n%ninput : ");
				pilihMode = input.nextInt();
				while (!(pilihMode == 1 || pilihMode == 2)) {
					System.out
							.printf("%nMohon input mode yang tersedia!%n%n [ 1 ] Single Player %n [ 2 ] Multi Player%n%ninput : ");
					pilihMode = input.nextInt();
				}
		
				if (pilihMode == 1) {
					System.out.printf("%nAnda memilih mode Single Player%n");
					System.out.print("Silahkan masukan nama Player 1 : ");
					nama1 = input.next();
				
					while (nama1.equalsIgnoreCase("Computer")) {
						System.out.print("\nUsername terlarang terdeteksi, mohon masukkan Username lain : ");
						nama1 = input.next();
					}

					nama2 = "Computer";
				} else {
					System.out.printf("%nAnda memilih mode Multi Player%n");
					System.out.print("Silahkan masukan nama Player 1 : ");
					nama1 = input.next();
					System.out.print("Silahkan masukan nama Player 2 : ");
					nama2 = input.next();

					while (nama2.equalsIgnoreCase(nama1)) {
						System.out.print("\nMohon gunakan username yang berbeda : ");
						nama1 = input.next();
					}
				}
		
				printPapan();
				turn();
				break;
			case 2:
				tampilkanHistory();
				break;
			case 3:
				tampilkanLeaderboard();
				break;
			case 4:
				tutorial();
				break;
			case 5:
				System.exit(0);
				break;
		}
	}

	/**
	 * Method yang dipanggil apabila user memilih untuk bermain lagi
	 * dengan userName yang sama dan Mode bermain yang sama.
	 * 
	 */
	public static void playAgain() {
		printPapan();
		turn();
	}

	/**
	 * Melakukan output dari char[][] papan menggunakan for loop.
	 */
	public static void printPapan() {
		System.out.println("\n");
		for (int i = 0; i < papan.length; i++) {
			System.out.printf("%-15s","");
			for (int j = 0; j < papan[i].length; j++) {
				System.out.print(papan[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Menentukan pemain yang akan mendapatkan giliran selanjutnya.
	 * Tempat dimana pemain yang mendapat giliran menginput posisi kosong dalam tabel.
	 */
	public static void turn() {
		char simbol = 'X';
		int addX = 0, addO = 0;

		if (giliranPemain % 2 == 0) {
			simbol = 'X';
			System.out.printf("%n%s [ %s ] Pilih posisi. %n input : ",nama1, simbol);
			addX = input.nextInt();

			while (posisiX.contains(addX) || posisiO.contains(addX) || addX > 9 || addX < 1) {
				System.out.printf("Posisi sudah terisi atau keluar batas(1-9).%n Input kembali : ");
				addX = input.nextInt();
			}

			posisiX.add(addX);
			giliranPemain++;
			isiPosisi(addX, simbol);
		}

		if (pilihMode == 1) {
			simbol = 'O';
			while (posisiX.contains(addO) || posisiO.contains(addO) || addO > 9 || addO < 1) {
				addO = acak.nextInt(9);
			}

			System.out.printf("%nComputer [ %s ] memilih %d%n",simbol, addO);
			posisiO.add(addO);
			giliranPemain++;
			isiPosisi(addO, simbol);
		}

		else if (pilihMode == 2) {
			simbol = 'O';
			System.out.printf("%n%s [ %s ] Pilih posisi. %n input : ", nama2, simbol);
			addO = input.nextInt();

			while (posisiO.contains(addO) || posisiX.contains(addO) || addO > 9 || addO < 1) {
				System.out.printf("Posisi sudah terisi atau keluar batas(1-9).%n Input kembali : ");
				addO = input.nextInt();
			}

			posisiO.add(addO);
			giliranPemain++;
			isiPosisi(addO, simbol);
		}
	}

	/**
	 * Mengisi posisi kolom tabel tertentu (input dari user).
	 * 
	 * @param input		Merupakan data inputan user pada method turn().
	 * @param simbol	Data yang akan diproses untuk mengganti tabel ke-{input} menjadi {simbol} 
	 */

	public static void isiPosisi(int input, char simbol) {
		switch (input) {
			// Atas Horizontal
			case 1:
				papan[1][1] = simbol;
				break;
			case 2:
				papan[1][8] = simbol;
				break;
			case 3:
				papan[1][15] = simbol;
				break;

			// Tengah Horizontal
			case 4:
				papan[5][1] = simbol;
				break;
			case 5:
				papan[5][8] = simbol;
				break;
			case 6:
				papan[5][15] = simbol;
				break;
				
			// Bawah Horizontal
			case 7:
				papan[9][1] = simbol;
				break;
			case 8:
				papan[9][8] = simbol;
				break;
			case 9:
				papan[9][15] = simbol;
				break;
		}

		printPapan();
		boolean winner = cekPemenang(simbol);
		pemenang(winner, simbol);
	}

	/**
	 * Mengecek pemenang setiap kali pemain mengisi posisi.
	 * Pengecekan dilakukan secara Horizontal, Vertikal, dan Diagonal.
	 * 
	 * Return true apabila sudah ada pemain yang memenuhi salah satu syarat Menang
	 * sehingga permainan dapat langsung berhenti.
	 * 
	 * @param simbol
	 * @return
	 */

	public static boolean cekPemenang(char simbol) {
		// Check rows
		for (int i = 1; i < 10; i += 4) {
			if (papan[i][1] == simbol && papan[i][8] == simbol && papan[i][15] == simbol) {
				return true;
			}
		}

		// Check columns
		for (int i = 1; i < 16; i += 7) {
			if (papan[1][i] == simbol && papan[5][i] == simbol && papan[9][i] == simbol) {
				return true;
			}
		}

		// Check diagonals
		if (papan[1][1] == simbol && papan[5][8] == simbol && papan[9][15] == simbol) {
			return true;
		}
		if (papan[1][15] == simbol && papan[5][8] == simbol && papan[9][1] == simbol) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * 
	 * @param winner
	 * @param simbol
	 */
	public static void pemenang(boolean winner, char simbol) {
		switch (simbol) {
			case 'X':
				pemainPemenang = nama1;
				break;
			case 'O':
				pemainPemenang = nama2;
				break;
			default:
				break;
		}

		if (winner == true) {
			System.out.printf("%nSelamat, %s Menang!", pemainPemenang);
			try {
				afterGame(nama1, nama2, pemainPemenang);
			} catch (IOException e) {
				System.out.println("Can't update");
			}
			System.exit(0);
		}

		else if (posisiO.size() + posisiX.size() == 9) {
			System.out.printf("%nDraw!");
			try {
				afterGame(nama1, nama2, "N/A");
			} catch (IOException e) {
				System.out.println("Failed to update the database");
			}
			System.exit(0);
		}

		else {
			turn();
		}
	}

	public static void afterGame(String nama1, String nama2, String pemainPemenang) throws IOException {
		// Instansiasi Date and Time
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = now.format(formatter);

		new History(formattedDate, nama1, nama2, pemainPemenang);

		// Clear papan
		giliranPemain = 0;

		posisiX.clear();
		posisiO.clear();

		papan[1][1] = ' ';
		papan[1][8] = ' ';
		papan[1][15] = ' ';

		papan[5][1] = ' ';
		papan[5][8] = ' ';
		papan[5][15] = ' ';

		papan[9][1] = ' ';
		papan[9][8] = ' ';
		papan[9][15] = ' ';

		// Mengupdate leaderboard
		if (pilihMode == 2 && !(pemainPemenang.equalsIgnoreCase("N/A"))) {
			new Leaderboard(pemainPemenang);
		} else if (pilihMode == 1) {
			System.out.printf("%nLeaderboard hanya diperbaharui pada mode Multi Player.%n");
		}

		System.out.printf("\nApakah ingin bermain lagi?\n[Y] Ya \t [N] Tidak\n\nMasukkan pilihan : ");

		String mainLagi = input.next();

		while (!((mainLagi.equalsIgnoreCase("y")) || (mainLagi.equalsIgnoreCase("n")))) {
			System.out.printf("\n Mohon masukkan pilihan anda dengan benar! ");
			mainLagi = input.next();
		}
		if (mainLagi.equalsIgnoreCase("y")) {
			playAgain();
		} else {
			System.out.println("Terimakasih telah bermain! ");
			backToMainMenu();
		}
	}

	public static void tampilkanHistory() throws IOException {

		System.out.printf("%n|======================== %s ==========================|%n%n", "HISTORY");
		System.out.printf("%-20s || %-25s || %s%n", "Date and Time", "Players", "Winner");
		System.out.printf("-------------------------------------------------------------%n");

		try {
			FileReader getfile = new FileReader("DbHistory.txt");
			BufferedReader baca = new BufferedReader(getfile);

			String database = baca.readLine();
			StringTokenizer writeToScreen = new StringTokenizer(database, ",");

			int line = 1;
			while (database != null) {
				if (line <= 10) {
					writeToScreen = new StringTokenizer(database, ",");
					System.out.printf("%-20s || %-10s VS  %-10s || %s%n", writeToScreen.nextToken(), writeToScreen.nextToken(), 
																		writeToScreen.nextToken(), writeToScreen.nextToken());
				}

				database = baca.readLine();
				line++;
			}
			baca.close();
		} catch (Exception e) {
			System.out.println("Tidak ada data");
		}

		backToMainMenu();
	}

	public static void tampilkanLeaderboard() throws IOException {

		System.out.printf("%n|====================== %s ========================|%n%n", "LEADERBOARD");
		System.out.printf("%-20s || %-25s || %s%n", "Peringkat", "Username", "Skor");
		System.out.printf("-------------------------------------------------------------%n");

		int peringkat = 0;

		try {
			BufferedReader baca = new BufferedReader(new FileReader("DbLeaderboard.txt"));

			String database = baca.readLine();
			StringTokenizer writeToScreen = new StringTokenizer(database, ",");

			int line = 1;
			while (database != null) {
				if (line <= 10) {
					writeToScreen = new StringTokenizer(database, ",");
					System.out.printf("%-20d || %-25s || %s%n", ++peringkat, writeToScreen.nextToken(),
							writeToScreen.nextToken());
				}
				database = baca.readLine();
				line++;
			}
			baca.close();
		} catch (Exception e) {
			System.out.println("Tidak ada data");
		}

		backToMainMenu();
	}

	public static void tutorial() throws IOException {
		// mencoba membaca file tutorial
		try {
			FileReader fileTutorial = new FileReader("Tutorial.txt");
			BufferedReader baca = new BufferedReader(fileTutorial);

			String data = baca.readLine();

			while (data != null) {
				System.out.println(data);
				data = baca.readLine();
			}
			baca.close();
		} catch (Exception e) {
			System.out.println("File Tutorial Tidak Ditemukan");
		}

		backToMainMenu();
	}

	public static void backToMainMenu() throws IOException {
		System.out.print("\nKembali ke Main Menu? \n[Y] Ya \t [N] Tidak \n\nPilihan : ");
		String answer = input.next();

		while (!(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n"))) {
			System.out.println("Mohon Masukkan pilihan dengan benar! ");
			System.out.print("\nKembali ke Main Menu? \n[Y] Ya \t [N] Tidak \n\nPilihan : ");
			answer = input.next();
		}

		if (answer.equalsIgnoreCase("y")) {
			mainMenu();
		} else {
			System.out.println("\nTerimakasih dan Sampai Jumpa! :)");
			System.exit(0);
		}
	}
}
