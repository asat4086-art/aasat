package tokoelektronik;

public class EWallet implements Pembayaran {
    @Override
    public String getNama() { return "E-Wallet"; }

    @Override
    public double hitungBiayaAdmin(double subtotal) { return 500; } // flat

    @Override
    public boolean butuhUangTunai() { return false; }
}
