package tokoelektronik;

/**
 * Satu baris barang dalam transaksi.
 * Data produk disalin (snapshot) saat dibeli, sehingga riwayat tetap akurat
 * walaupun harga/stok produk berubah di kemudian hari.
 */
public class ItemTransaksi {
    private String kode;
    private String namaProduk;
    private String kategori;
    private double hargaSatuan;   // harga setelah diskon, saat transaksi
    private double persenDiskon;
    private int garansiBulan;
    private int jumlah;

    public ItemTransaksi(Produk produk, int jumlah) {
        this.kode = produk.getKode();
        this.namaProduk = produk.getNama();
        this.kategori = produk.getKategori();
        this.hargaSatuan = produk.getHargaSetelahDiskon();
        this.persenDiskon = produk.getPersenDiskon();
        this.garansiBulan = produk.getMasaGaransiBulan();
        this.jumlah = jumlah;
    }

    public String getKode() { return kode; }
    public String getNamaProduk() { return namaProduk; }
    public String getKategori() { return kategori; }
    public double getHargaSatuan() { return hargaSatuan; }
    public double getPersenDiskon() { return persenDiskon; }
    public int getGaransiBulan() { return garansiBulan; }
    public int getJumlah() { return jumlah; }

    public void tambahJumlah(int tambahan) { this.jumlah += tambahan; }

    public double getSubtotal() { return hargaSatuan * jumlah; }
}
