package tokoelektronik;

/** INHERITANCE: Smartphone mewarisi Produk. */
public class Smartphone extends Produk {
    private int ramGb;
    private int penyimpananGb;

    public Smartphone(String kode, String nama, double harga, int stok,
                      int ramGb, int penyimpananGb) {
        super(kode, nama, harga, stok);
        this.ramGb = ramGb;
        this.penyimpananGb = penyimpananGb;
    }

    public int getRamGb() { return ramGb; }
    public int getPenyimpananGb() { return penyimpananGb; }

    @Override
    public String getKategori() { return "Smartphone"; }

    @Override
    public double getPersenDiskon() { return 5; }

    @Override
    public int getMasaGaransiBulan() { return 12; }

    @Override
    public String getSpesifikasi() {
        return ramGb + "GB RAM / " + penyimpananGb + "GB";
    }
}
