package tokoelektronik;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        TokoElektronik toko = new TokoElektronik();
        Kasir kasir = new Kasir("K01", "aaasat", "Pagi");

        System.out.println("=== SISTEM POS TOKO ELEKTRONIK  ===");
        System.out.println("Kasir: " + kasir.getNama() + "");

        boolean jalan = true;
        while (jalan) {
            System.out.println("\n----------- MENU UTAMA -----------");
            System.out.println("1. Lihat daftar produk");
            System.out.println("2. Transaksi baru");
            System.out.println("3. Tambah stok produk");
            System.out.println("4. Tambah produk baru");
            System.out.println("5. Riwayat transaksi");
            System.out.println("0. Keluar");
            int pilih = bacaInt("Pilih menu: ");

            switch (pilih) {
                case 1: toko.tampilkanProduk(); break;
                case 2: transaksiBaru(toko, kasir); break;
                case 3: tambahStok(toko); break;
                case 4: tambahProdukBaru(toko); break;
                case 5: menuRiwayat(toko); break;
                case 0: jalan = false; break;
                default: System.out.println("Menu tidak valid!");
            }
        }
        System.out.println("Terima kasih. Program selesai.");
    }

    // ================= TRANSAKSI =================
    private static void transaksiBaru(TokoElektronik toko, Kasir kasir) {
        Transaksi trx = new Transaksi(kasir);
        toko.tampilkanProduk();
        System.out.println("Masukkan kode produk ('selesai' = checkout, 'batal' = batalkan)");

        while (true) {
            System.out.print("Kode produk: ");
            String kode = sc.nextLine().trim();
            if (kode.equalsIgnoreCase("selesai")) break;
            if (kode.equalsIgnoreCase("batal")) {
                System.out.println("Transaksi dibatalkan.");
                return;
            }

            Produk p = toko.cariProduk(kode);
            if (p == null) {
                System.out.println("Produk tidak ditemukan!");
                continue;
            }
            int sisa = p.getStok() - trx.getJumlahDiKeranjang(p.getKode());
            if (sisa <= 0) {
                System.out.println("Stok " + p.getNama() + " habis / sudah semua di keranjang!");
                continue;
            }
            int jumlah = bacaInt("Jumlah (maks " + sisa + "): ");
            if (jumlah <= 0 || jumlah > sisa) {
                System.out.println("Jumlah tidak valid!");
                continue;
            }
            trx.tambahItem(p, jumlah);
            trx.tampilkanKeranjang();
        }

        if (trx.isKosong()) {
            System.out.println("Keranjang kosong, transaksi dibatalkan.");
            return;
        }

        // Pilih metode pembayaran (POLYMORPHISM lewat interface Pembayaran)
        System.out.println("\nMetode pembayaran:");
        System.out.println("1. Tunai");
        System.out.println("2. Kartu Debit (admin 1%)");
        System.out.println("3. E-Wallet (admin Rp 500)");
        int m = bacaInt("Pilih: ");
        Pembayaran pembayaran;
        switch (m) {
            case 2: pembayaran = new KartuDebit(); break;
            case 3: pembayaran = new EWallet(); break;
            default: pembayaran = new Tunai();
        }
        trx.setPembayaran(pembayaran);

        double total = trx.getTotalAkhir();
        System.out.println("Metode: " + pembayaran.getNama());
        System.out.println("Total yang harus dibayar: " + Rupiah.format(total));

        double uang = total;
        if (pembayaran.butuhUangTunai()) {
            while (true) {
                uang = bacaLong("Uang diterima (0 = batal): ");
                if (uang == 0) {
                    System.out.println("Transaksi dibatalkan.");
                    return;
                }
                if (uang >= total) break;
                System.out.println("Uang kurang!");
            }
        }
        trx.setUangDiterima(uang);

        // Stok baru dikurangi setelah pembayaran berhasil
        for (ItemTransaksi item : trx.getItems()) {
            Produk p = toko.cariProduk(item.getKode());
            p.kurangiStok(item.getJumlah());
        }
        toko.simpanTransaksi(trx);
        trx.cetakStruk();
    }

    // ================= RIWAYAT =================
    private static void menuRiwayat(TokoElektronik toko) {
        toko.tampilkanRiwayat();
        if (!toko.adaRiwayat()) {
            return;
        }
        while (true) {
            System.out.print("\nKetik no transaksi (mis. TRX0001) untuk lihat struk, atau Enter untuk kembali: ");
            String no = sc.nextLine().trim();
            if (no.isEmpty()) {
                return;
            }
            Transaksi t = toko.cariTransaksi(no);
            if (t == null) {
                System.out.println("Transaksi tidak ditemukan!");
            } else {
                t.cetakStruk();
            }
        }
    }

    // ================= INVENTORI =================
    private static void tambahStok(TokoElektronik toko) {
        System.out.print("Kode produk: ");
        Produk p = toko.cariProduk(sc.nextLine().trim());
        if (p == null) {
            System.out.println("Produk tidak ditemukan!");
            return;
        }
        int jml = bacaInt("Jumlah tambahan stok: ");
        if (jml <= 0) {
            System.out.println("Jumlah harus lebih dari 0!");
            return;
        }
        p.tambahStok(jml);
        System.out.println("Stok " + p.getNama() + " sekarang: " + p.getStok());
    }

    private static void tambahProdukBaru(TokoElektronik toko) {
        System.out.println("Kategori: 1. Smartphone  2. Laptop  3. Aksesori");
        int k = bacaInt("Pilih: ");
        if (k < 1 || k > 3) {
            System.out.println("Kategori tidak valid!");
            return;
        }
        System.out.print("Kode: ");
        String kode = sc.nextLine().trim();
        if (kode.isEmpty() || toko.kodeSudahAda(kode)) {
            System.out.println("Kode kosong atau sudah dipakai!");
            return;
        }
        System.out.print("Nama: ");
        String nama = sc.nextLine().trim();
        long harga = bacaLong("Harga: ");
        int stok = bacaInt("Stok: ");

        try {
            if (k == 1) {
                int ram = bacaInt("RAM (GB): ");
                int rom = bacaInt("Penyimpanan (GB): ");
                toko.tambahProduk(new Smartphone(kode, nama, harga, stok, ram, rom));
            } else if (k == 2) {
                System.out.print("Prosesor: ");
                String cpu = sc.nextLine().trim();
                int ram = bacaInt("RAM (GB): ");
                toko.tambahProduk(new Laptop(kode, nama, harga, stok, cpu, ram));
            } else {
                System.out.print("Jenis: ");
                String jenis = sc.nextLine().trim();
                toko.tambahProduk(new Aksesori(kode, nama, harga, stok, jenis));
            }
            System.out.println("Produk berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    // ================= INPUT HELPER =================
    private static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }

    private static long bacaLong(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }
}
