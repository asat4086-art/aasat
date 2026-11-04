package tokoelektronik;

/**
 * ABSTRACTION  : class abstrak sebagai cetak biru semua produk elektronik.
 * ENCAPSULATION: atribut private, diakses lewat getter/setter dengan validasi.
 */
public abstract class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        setHarga(harga);
        setStok(stok);
    }

    // ---------- Getter & Setter (Encapsulation) ----------
    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }

    public void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif");
        }
        this.harga = harga;
    }

    public void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }
        this.stok = stok;
    }

    public void tambahStok(int jumlah) {
        if (jumlah > 0) {
            this.stok += jumlah;
        }
    }

    public boolean kurangiStok(int jumlah) {
        if (jumlah > 0 && jumlah <= stok) {
            this.stok -= jumlah;
            return true;
        }
        return false;
    }

    // ---------- Method abstrak (wajib di-override subclass) ----------
    public abstract String getKategori();
    public abstract double getPersenDiskon();
    public abstract int getMasaGaransiBulan();
    public abstract String getSpesifikasi();

    // ---------- Method konkret (diwariskan ke semua subclass) ----------
    public double getHargaSetelahDiskon() {
        return harga - (harga * getPersenDiskon() / 100);
    }

    @Override
    public String toString() {
        return String.format("%-6s %-11s %-22s %-24s %3d bln %15s %5d",
                kode, getKategori(), nama, getSpesifikasi(),
                getMasaGaransiBulan(), Rupiah.format(getHargaSetelahDiskon()), stok);
    }
}
