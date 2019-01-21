package acc.util

import java.text.MessageFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Logger

enum class Messages {

    Aktiva,
    Analytika,
    Analytika_musi_byt_tri_cislice,
    Bankovni_vypis,
    Castka,
    Chyba,
    Chybne_datum,
    Cislo,
    Dal,
    Dal_celkem,
    Dal_nacitany,
    Datum,
    Do,
    Doklad,
    Doklad_je_pouzit_v_transakci,
    Doklady,
    Faktura,
    Fakturu,
    File,
    Id,
    Jmeno,
    Konecna,
    Konecna_aktiva,
    Konecna_pasiva,
    Konecny_stav,
    Ma_dati,
    Ma_dati_celkem,
    Ma_dati_nacitany,
    Mesic,
    Mesice,
    Nastav_pocatecni_stav,
    Nazev,
    Neni_vybran_zadny_ucet,
    Neplatna_analytika,
    Neplatna_castka,
    Neplatna_skupina,
    Neplatny_mesic,
    Neplatny_typ_dokumentu,
    Nezaplacene_faktury,
    Obrat,
    Obrat_nacitany,
    Od,
    Osnova,
    Ostatni,
    Parovy_doklad,
    Pasiva,
    Pocatecni,
    Pocatecni_aktiva,
    Pocatecni_pasiva,
    Pocatecni_stavy,
    Pocatecní_ucet_rozvazny,
    Polozka_vypisu,
    Popis,
    Popis_dokladu,
    Potvrd,
    Potvrd_a_zauctuj,
    Potvrd_a_dalsi,
    Poznamka,
    prazdna_skupina,
    prazdne_jmeno,
    Pridruzeny_doklad,
    Prijmovy_doklad,
    pro_doklad,
    pro_souvisejici_doklad,
    pro_ucet,
    Pro_ucet,
    rok_musi_byt_letosni,
    Rozvaha,
    Rozvaha_pro_mesic,
    S_uctem,
    Smaz,
    Souvisejici_faktura,
    Souvztaznost,
    Suma,
    Synteticky_ucet,
    Tisk,
    Tisk_rozvahy,
    Tiskarny,
    Transakce,
    Typ_dokladu,
    Ucet,
    Ucetni_udalost,
    Ucet_je_pouzit,
    Ucet_jiz_existuje,
    Ucetnictvi,
    Ucty,
    Ukonceni,
    Vcetne_pocatku,
    Vydajovy_doklad,
    Vypis_z_uctu,
    Vystupy,
    Zadej,
    Vytvor_doklad,
    //    Vytvor_fakturu,
    Vytvor_inicializaci,
    Vytvor_rozvahu,
    Vytvor_transakci,
    Vytvor_ucet,
    Zmen_doklad,
    Zmen_fakturu,
    Zmen_inicializaci,
    Zmen_pocatecni_stav,
    Zmen_transakci,
    Zmen_ucet,
    Zobraz_doklady,
    Zobraz_pocatecni_stavy,
    Zobraz_transakce,
    Zobraz_ucty,
    Zrus,
    Zrus_doklad,
    Zrus_fakturu,
    Zrus_inicializaci,
    Zrus_transakci,
    Zrus_ucet,
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

val String.withColon
    get() = this + ": "

val monthFormater = DateTimeFormatter
        .ofPattern("MMMM")
        .withLocale(Global.locale)