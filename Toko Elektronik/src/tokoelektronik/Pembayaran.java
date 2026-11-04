package tokoelektronik;

/**
 * ABSTRACTION: interface metode pembayaran.
 * Setiap metode punya aturan biaya admin dan cara bayar sendiri.
 */
public interface Pembayaran {
    String getNama();
    double hitungBiayaAdmin(double subtotal);

    /** Hanya pembayaran tunai yang meminta input uang dari pelanggan. */
    boolean butuhUangTunai();

    default double hitungTotalAkhir(double subtotal) {
        return subtotal + hitungBiayaAdmin(subtotal);
    }
}
