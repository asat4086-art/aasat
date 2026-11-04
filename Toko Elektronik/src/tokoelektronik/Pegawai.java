package tokoelektronik;

/** Class abstrak untuk pegawai toko. */
public abstract class Pegawai {
    private String id;
    private String nama;

    public Pegawai(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }

    public abstract String getJabatan();
}
