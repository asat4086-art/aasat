package tokoelektronik;

/** INHERITANCE: Laptop mewarisi Produk. */
public class Laptop extends Produk {
    private String prosesor;
    private int ramGb;

    public Laptop(String kode, String nama, double harga, int stok,
                  String prosesor, int ramGb) {
        super(kode, nama, harga, stok);
        this.prosesor = prosesor;
        this.ramGb = ramGb;
    }

    public String getProsesor() { return prosesor; }
    public int getRamGb() { return ramGb; }

    @Override
    public String getKategori() { return "Laptop"; }

    @Override
    public double getPersenDiskon() { return 3; }

    @Override
    public int getMasaGaransiBulan() { return 24; }

    @Override
    public String getSpesifikasi() {
        return prosesor + " / " + ramGb + "GB RAM";
    }
}
