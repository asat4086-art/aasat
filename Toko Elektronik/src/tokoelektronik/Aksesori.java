package tokoelektronik;

/** INHERITANCE: Aksesori mewarisi Produk (charger, earphone, mouse, dll). */
public class Aksesori extends Produk {
    private String jenis;

    public Aksesori(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    public String getJenis() { return jenis; }

    @Override
    public String getKategori() { return "Aksesori"; }

    @Override
    public double getPersenDiskon() { return 10; }

    @Override
    public int getMasaGaransiBulan() { return 6; }

    @Override
    public String getSpesifikasi() { return jenis; }
}
