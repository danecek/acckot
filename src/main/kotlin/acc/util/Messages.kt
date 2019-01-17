package acc.util

import java.text.MessageFormat
import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle
import java.util.logging.Logger

enum class Messages {
    Zrus,
    Potvrd,
    File,
    Mesic,
    Vytvor_ucet,
    Ucty,
    Zrus_ucet,
    Cislo,
    Nazev,
    Jmeno,
    Chyba,
    Synteticky_ucet, Id,
    Transakce,
    Datum,
    Castka,
    Dal,
    Poznamka,
    Ucet,
    Aktiva,
    Rozvaha,
    Pasiva,
    Tisk,
    Tisk_rozvahy,
    Vytvor_rozvahu,
    Vystupy,
    Analytika,
    Ma_dati, Ma_dati_nacitany,
    Dal_nacitany,

    Vytvor_transakci,
    Suma,
    Zmen_transakci,
    Zrus_transakci,
    Zobraz_transakce,
    Vytvor_inicializaci,
    Zmen_inicializaci,
    Zrus_inicializaci,
    Od,
    S_uctem,
    Do,
    Pridruzeny_doklad,
    Ucetnictvi,
    Ukonceni,
    pro_ucet,
    pro_doklad,
    Nastav_pocatecni_stav,
    Pocatecní_ucet_rozvazny,
    Zobraz_pocatecni_stavy,
    Pocatecni_stavy,
    Zmen_pocatecni_stav,
    Vcetne_pocatku,
    Zobraz_ucty,
    Obrat,
    Obrat_nacitany,
    Pocatecni_aktiva,
    Pocatecni_pasiva,
    Konecna_aktiva,
    Konecna_pasiva,
    Pocatecni,
    Konecna, Tiskarny,
    Margins,
    Rozvaha_pro_mesic,
    Ucet_je_pouzit,
    Souvztaznost,

    Ma_dati_celkem,
    Konecny_stav,
    Dal_celkem,
    Popis,
    // Doklady
    Typ_dokladu,
    Parovy_doklad,
    Doklady,
    Doklad,
    Popis_dokladu,
    Zobraz_doklady,
    Vytvor_doklad,
    Zrus_doklad,
    Zmen_doklady,
    Zmen_doklad,
    // Faktury
    Vytvor_fakturu,
    Zmen_fakturu,
    Zrus_fakturu,
    Datum_splatnosti,

    Vypis_z_uctu,
    Ostatni, Faktura,
    Souvisejici_doklad,
    pro_souvisejici_doklad,
    Zmen_ucet,
    // Validace
    rok_musi_byt_letosni,
    neplatna_castka,
    analytika_musi_byt_tri_cislice,
    prazdne_jmeno,
    prazdna_skupina,
    neplatna_analytika,
    neplatna_skupina,
    duplicitni_cislo_uctu,
    neplatny_mesic,
    neni_vybran_zadny_ucet,
    neplatny_typ_dokumentu,
    chybne_datum,
    Pro_ucet,
    ;




    fun cm(vararg args: Any): String {
        try {
            return MessageFormat.format(rb.getString(this.name), *args)
        } catch (mre: MissingResourceException) {
            // LOG.warning(mre.toString());
            return name.replace("_", " ")
        }

    }

    companion object {
        private val rb = ResourceBundle.getBundle("acc.util.messages", Locale("cs"))
        private val LOG = Logger.getLogger(Messages::class.java.name)
     }




}
