package tokoelektronik;

import java.util.ArrayList;
import java.util.List;

/** Mengelola inventori produk dan riwayat transaksi. */
public class TokoElektronik {
    private List<Produk> daftarProduk = new ArrayList<>();
    private List<Transaksi> riwayat = new ArrayList<>();

    public TokoElektronik() {
        // Data awal
        tambahProduk(new Smartphone("S001", "Galaxy A55", 5999000, 10, 8, 256));
        tambahProduk(new Smartphone("S002", "Redmi Note 13", 2999000, 15, 8, 128));
        tambahProduk(new Smartphone("S003", "iPhone 13", 10999000, 5, 4, 128));
        tambahProduk(new Laptop("L001", "Asus Vivobook 14", 7499000, 8, "Core i5", 8));
        tambahProduk(new Laptop("L002", "Lenovo IdeaPad 3", 6299000, 6, "Ryzen 5", 8));
        tambahProduk(new Laptop("L003", "Acer Aspire 5", 8999000, 4, "Core i7", 16));
        tambahProduk(new Aksesori("A001", "Charger 33W", 150000, 40, "Charger"));
        tambahProduk(new Aksesori("A002", "Earphone TWS", 250000, 25, "Audio"));
        tambahProduk(new Aksesori("A003", "Mouse Wireless", 120000, 30, "Periferal"));
    }

    // ---------- Inventori ----------
    public void tambahProduk(Produk p) {
        daftarProduk.add(p);
    }

    public Produk cariProduk(String kode) {
        for (Produk p : daftarProduk) {
            if (p.getKode().equalsIgnoreCase(kode)) {
                return p;
            }
        }
        return null;
    }

    public boolean kodeSudahAda(String kode) {
        return cariProduk(kode) != null;
    }

    public void tampilkanProduk() {
        String garis = "-".repeat(100);
        System.out.println(garis);
        System.out.printf("%-6s %-11s %-22s %-24s %-7s %15s %5s%n",
                "KODE", "KATEGORI", "NAMA", "SPESIFIKASI", "GARANSI", "HARGA*", "STOK");
        System.out.println(garis);
        // POLYMORPHISM: tiap subclass punya spesifikasi, diskon, dan garansi sendiri
        for (Produk p : daftarProduk) {
            System.out.println(p);
        }
        System.out.println(garis);
        System.out.println("*Harga sudah termasuk diskon kategori");
    }

    // ---------- Riwayat transaksi ----------
    public void simpanTransaksi(Transaksi t) {
        riwayat.add(t);
    }

    public boolean adaRiwayat() {
        return !riwayat.isEmpty();
    }

    public Transaksi cariTransaksi(String no) {
        for (Transaksi t : riwayat) {
            if (t.getNoTransaksi().equalsIgnoreCase(no)) {
                return t;
            }
        }
        return null;
    }

    public double getTotalPendapatan() {
        double total = 0;
        for (Transaksi t : riwayat) {
            total += t.getTotalAkhir();
        }
        return total;
    }

    public void tampilkanRiwayat() {
        if (riwayat.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        String garis = "-".repeat(86);
        System.out.println(garis);
        System.out.printf("%-9s %-20s %-8s %-12s %6s %17s%n",
                "NO", "WAKTU", "KASIR", "METODE", "UNIT", "TOTAL");
        System.out.println(garis);
        int totalUnit = 0;
        for (Transaksi t : riwayat) {
            System.out.printf("%-9s %-20s %-8s %-12s %6d %17s%n",
                    t.getNoTransaksi(), t.getWaktuTeks(), t.getKasir().getNama(),
                    t.getPembayaran().getNama(), t.getTotalUnit(),
                    Rupiah.format(t.getTotalAkhir()));
            totalUnit += t.getTotalUnit();
        }
        System.out.println(garis);
        System.out.printf("Jumlah transaksi : %d%n", riwayat.size());
        System.out.printf("Total unit terjual: %d%n", totalUnit);
        System.out.printf("Total pendapatan : %s%n", Rupiah.format(getTotalPendapatan()));
    }
}
