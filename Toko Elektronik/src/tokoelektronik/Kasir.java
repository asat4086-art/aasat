package tokoelektronik;

/** INHERITANCE: Kasir mewarisi Pegawai. */
public class Kasir extends Pegawai {
    private String shift;

    public Kasir(String id, String nama, String shift) {
        super(id, nama);
        this.shift = shift;
    }

    public String getShift() { return shift; }

    @Override
    public String getJabatan() { return "Kasir"; }
}
