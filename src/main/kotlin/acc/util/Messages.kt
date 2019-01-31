package acc.util

import java.text.MessageFormat
import java.util.*
import java.util.logging.Logger

enum class Messages {

    Aktiva,
    Analytika,
    Analytika_musi_byt_tri_cislice,
    Bankovni_vypis,
    Castka,
    Chybny_rok,
    Cislo,
    Dal,
    Datum,
    Do,
    Doklad,
    Doklad_je_pouzit_v_transakci,
    Doklady,
    FAK,
    Faktura,
    Fakturu,
    File,
    Id,
    Jmeno,
    Neplatna_velikost,
    Konec,
    Konecna,
    Nastaveni,
    Ma_dati,
    Mesice,
    //  Nastav_pocatecni_stav,
    Nazev,
    Neplatna_castka,
    Neplatny_rok,
    Nezaplacene_faktury,
    Obrat,
    Obrat_nacitany,
    Od,
    Odpovidajici_nezaplacena_faktura,
    Pasiva,
    Pocatecni,
    Pocatecni_stav,
    Popis,
    Potvrd,
    Potvrd_a_zauctuj,
    Potvrd_a_dalsi,
    Prazdna_skupina,
    PRI,
    Prijmovy_doklad,
    Pro_ucet,
    Rok,
    Rozvaha,
    Rozvaha_pro_mesic,
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
    Ucet_je_pouzit_v_transakci,
    Ucet_jiz_existuje,
    Ucty,
    UU,
    Velikost_pisma,
    Vsechny_typy,
    Vsechny,
    Vydajovy_doklad,
    VYD,
    VYP,
    Vytvor,
    Vytvor_doklad,
    // Vytvor_inicializaci,
    Vytvor_rozvahu,
    Vytvor_transakci,
    Vytvor_ucet,
    Zaplacena_faktura,
    Zauctuj_doklad,
    Zauctovane_transakce,
    Zauctuj_polozku_vypisu,
    Zmen_doklad,
    Zmen_transakci,
    Zmen_ucet,
    Zobraz_doklady,
    Zobraz_nezaplacene_faktury,
    Zobraz_vsechny_doklady,

    //  Zobraz_pocatecni_stavy,
    Zobraz_transakce,
    Zobraz_vsechny_transakce,
    Zobraz_ucty,
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
