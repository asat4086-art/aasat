package tokoelektronik;

import java.text.NumberFormat;
import java.util.Locale;

/** Class utilitas untuk format mata uang. */
public class Rupiah {
    private Rupiah() { }

    public static String format(double nilai) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        nf.setMaximumFractionDigits(0);
        nf.setMinimumFractionDigits(0);
        return nf.format(nilai);
    }
}
