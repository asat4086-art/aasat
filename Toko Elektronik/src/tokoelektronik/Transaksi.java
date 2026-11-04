package tokoelektronik;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Transaksi {
    private static final DateTimeFormatter FORMAT_WAKTU =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static int counter = 0;

    private String noTransaksi;
    private LocalDateTime waktu;
    private Kasir kasir;
    private List<ItemTransaksi> items = new ArrayList<>();
    private Pembayaran pembayaran;
    private double uangDiterima;

    public Transaksi(Kasir kasir) {
        counter++;
        this.noTransaksi = String.format("TRX%04d", counter);
        this.waktu = LocalDateTime.now();
        this.kasir = kasir;
    }

    // ---------- Getter / Setter ----------
    public String getNoTransaksi() { return noTransaksi; }
    public String getWaktuTeks() { return waktu.format(FORMAT_WAKTU); }
    public Kasir getKasir() { return kasir; }
    public Pembayaran getPembayaran() { return pembayaran; }
    public List<ItemTransaksi> getItems() { return items; }
    public boolean isKosong() { return items.isEmpty(); }

    public void setPembayaran(Pembayaran pembayaran) { this.pembayaran = pembayaran; }
    public void setUangDiterima(double uangDiterima) { this.uangDiterima = uangDiterima; }

    // ---------- Keranjang ----------
    public void tambahItem(Produk produk, int jumlah) {
        for (ItemTransaksi item : items) {
            if (item.getKode().equals(produk.getKode())) {
                item.tambahJumlah(jumlah);
                return;
            }
        }
        items.add(new ItemTransaksi(produk, jumlah));
    }

    public int getJumlahDiKeranjang(String kode) {
        for (ItemTransaksi item : items) {
            if (item.getKode().equals(kode)) {
                return item.getJumlah();
            }
        }
        return 0;
    }

    public int getTotalUnit() {
        int total = 0;
        for (ItemTransaksi item : items) {
            total += item.getJumlah();
        }
        return total;
    }

    public double getSubtotal() {
        double total = 0;
        for (ItemTransaksi item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    /** POLYMORPHISM: biaya admin dihitung sesuai objek Pembayaran yang dipilih. */
    public double getBiayaAdmin() {
        return pembayaran.hitungBiayaAdmin(getSubtotal());
    }

    public double getTotalAkhir() {
        return pembayaran.hitungTotalAkhir(getSubtotal());
    }

    public double getKembalian() {
        return uangDiterima - getTotalAkhir();
    }

    // ---------- Tampilan ----------
    public void tampilkanKeranjang() {
        System.out.println("--- Keranjang ---");
        int no = 1;
        for (ItemTransaksi item : items) {
            System.out.printf("%d. %-22s %d x %s = %s%n", no++, item.getNamaProduk(),
                    item.getJumlah(), Rupiah.format(item.getHargaSatuan()),
                    Rupiah.format(item.getSubtotal()));
        }
        System.out.println("Subtotal: " + Rupiah.format(getSubtotal()));
    }

    public void cetakStruk() {
        String garis = "=".repeat(60);
        String tipis = "-".repeat(60);
        System.out.println();
        System.out.println(garis);
        System.out.println("            TOKO ELEKTRONIK \"TEKNO JAYA\"");
        System.out.println(garis);
        System.out.println("No Transaksi : " + noTransaksi);
        System.out.println("Waktu        : " + getWaktuTeks());
        System.out.println("Kasir        : " + kasir.getNama() + " (" + kasir.getShift() + ")");
        System.out.println(tipis);
        for (ItemTransaksi item : items) {
            System.out.println(item.getNamaProduk() + " [" + item.getKategori() + "]");
            String diskon = item.getPersenDiskon() > 0
                    ? " (diskon " + (int) item.getPersenDiskon() + "%)" : "";
            System.out.printf("   %d x %s%s = %s%n", item.getJumlah(),
                    Rupiah.format(item.getHargaSatuan()), diskon,
                    Rupiah.format(item.getSubtotal()));
            System.out.println("   Garansi " + item.getGaransiBulan() + " bulan");
        }
        System.out.println(tipis);
        System.out.printf("%-20s: %s%n", "Subtotal", Rupiah.format(getSubtotal()));
        System.out.printf("%-20s: %s%n", "Biaya admin", Rupiah.format(getBiayaAdmin()));
        System.out.printf("%-20s: %s%n", "TOTAL", Rupiah.format(getTotalAkhir()));
        System.out.printf("%-20s: %s%n", "Metode bayar", pembayaran.getNama());
        System.out.printf("%-20s: %s%n", "Uang diterima", Rupiah.format(uangDiterima));
        System.out.printf("%-20s: %s%n", "Kembalian", Rupiah.format(getKembalian()));
        System.out.println(garis);
        System.out.println("   Simpan struk ini sebagai bukti garansi. Terima kasih!");
        System.out.println(garis);
    }
}
