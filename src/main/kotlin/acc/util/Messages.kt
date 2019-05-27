package acc.util

import java.text.MessageFormat
import java.util.*
import java.util.logging.Logger

enum class Messages {

    Ma_dati,            // Debit side
    Dal,                // Credit side
    Aktiva,             // Assets
    Pasiva,             // Liabilities
    Zisky_a_ztraty,         // Income statement/profit-and-loss statement
    Zisky_a_ztraty_za_mesic,
    Rozvaha,            // Statement sheet
    Rozvaha_pro_mesic,

    Analytika,
    Analytika_musi_byt_tri_cislice,
    Bankovni_vypis,
    Castka,
    Chybny_rok,
    Cislo,
    Datum,
    Do,
    Doklad,
    Doklad_je_pouzit_v_transakci,
    Doklady,
    FAK,
    Faktura,
    Fakturu,
    File,
    Filter_dokladu,
    Filter_transakci,
    Id,
    Jmeno,
    Konec,
    Konecna,
    Mesice,
    Nastaveni,
    Nazev,
    Neplatna_velikost,
    Neplatna_castka,
    Neplatny_rok,
    Nezaplacene_faktury,
    Obrat,
    Obrat_nacitany,
    Od,
    Odpovidajici_nezaplacena_faktura,
    Pocatecni,
    Pocatecni_stav,
    Popis,
    Poradi,
    Potvrd,
    Potvrd_a_zauctuj,
    Potvrd_a_dalsi,
    Prazdna_skupina,
    PRI,
    Prijmovy_doklad,
    Pro_ucet,
    Rok,
    //Rozvaha_pro_mesic,
    S_dokladem,
    S_uctem,
    Smaz,
    Soubor_uctyxxxx_json_je_poskozen,
    Synteticky_ucet,
    Tisk_rozvahy,
    Transakce,
    Typ,
    Typy,
    Ucet,
    Ucetnictvi,
    Ucetni_udalost,
    Ucet_jiz_existuje,
    Ucty,
    UU,
    Velikost_pisma,
    Vsechny_typy,
    Vydajovy_doklad,
    Hlavni,
    Vsechny,
    VYD,
    VYP,
    Vystup,
    Vytvor,
    Vytvor_doklad,
    Vytvor_rozvahu,
    Vytvor_zisky_a_ztraty,
    Vytvor_transakci,
    Vytvor_ucet,
    Zaplacena_faktura,
    Zauctuj_doklad,
    Zauctovane_transakce,
    Zauctuj_polozku_vypisu,
    Zmen_doklad,
    Zmen_transakci,
    Zmen_ucet,

    //Zobraz_nezaplacene_faktury,
    Vsechny_doklady,

    //  Zobraz_pocatecni_stavy,

    Vsechny_transakce,
    Zobraz_ucty,
    Zobraz_synteticke_ucty,
    Zrus,
    Zrus_doklad,
    //   Zrus_pocatecni_nastaveni,
    Zrus_transakci,
    Zrus_ucet,
    ;

    fun cm(vararg args: Any): String {
        return try {
            MessageFormat.format(rb.getString(this.name), *args)
        } catch (mre: MissingResourceException) {
            // LOG.warning(mre.toString());
            name.replace("_", " ")
        }

    }

    companion object {
        private val rb = ResourceBundle.getBundle("acc.util.messages", Locale("cs"))
        private val LOG = Logger.getLogger(Messages::class.java.name)
    }

}

const val COLDEL = ": "
val String.withColon
    get() = this + COLDEL
