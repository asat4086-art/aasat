package tokoelektronik;

public class KartuDebit implements Pembayaran {
    @Override
    public String getNama() { return "Kartu Debit"; }

    @Override
    public double hitungBiayaAdmin(double subtotal) {
        return Math.round(subtotal * 0.01); // admin 1%
    }

    @Override
    public boolean butuhUangTunai() { return false; }
}
