package tokoelektronik;

public class Tunai implements Pembayaran {
    @Override
    public String getNama() { return "Tunai"; }

    @Override
    public double hitungBiayaAdmin(double subtotal) { return 0; }

    @Override
    public boolean butuhUangTunai() { return true; }
}
