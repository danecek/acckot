package acc.util

import java.text.MessageFormat
import java.util.*
import java.util.logging.Logger

enum class Messages {

    VYDAD,
    BANKV,
    UCETU,
    PRIJD,
    FAKT,

    Ma_dati,            // Debit side
    Dal,                // Credit side
    Aktiva,             // Assets
    Pasiva,             // Liabilities
    Zisky_a_ztraty,         // Income statement/profit-and-loss statement
    Zisky_a_ztraty_za_mesic,
    Rozvaha,            // Statement sheet
    Rozvaha_pro_mesic,


    Adresar_s_daty,
    Analytika,
    Alespon_jeden_typ_musi_byt_vybran,
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

    Faktura,
    Fakturu,
    File,
    Filter_transakci,
    Hlavni,
    Id,
    Jmeno,
    Konec,
    Konecna,
    Mesice,
    Nastav_filter_dokladu,
    Nastav_filter_transakci,
    Nastaveni,
    Nazev,
    Neni_vyberan_zadny_doklad,
    Neexistuje_pozadovany_analyticky_ucet,
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
    Potvrd_a_dalsi_polozka,
    Prazdna_skupina,
    Prijmovy_doklad,
    Pro_ucet,
    Rok,
    S_dokladem,
    S_uctem,
    Smaz,
    Soubor_uctyxxxx_json_je_poskozen,
    Synteticke_ucty,
    Syntetika,
    Tisk_rozvahy,
    Transakce,
    Typ,
    Ucet,
    Ucetnictvi,
    Ucetni_udalost,
    Ucet_jiz_existuje,
    Ucty,
    UcetUdal,
    Velikost_pisma,
    Vsechny_typy,
    Vsechny_transakce,
    Vytvor_transakci_pro_vybrany_doklad,
    Vsechny_doklady,
    Vydajovy_doklad,
    Vsechny,
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

    Zobraz_nezaplacene_faktury,
    Zobraz_vsechny_doklady,
    Zobraz_vsechny_transakce,
    Zobraz_transakce_s_uctem,
    Zobraz_transakce_souvisejici_s_dokladem,
    Zobrazene_typy_dokladu,
    Zobraz_ucty,
    Zobraz_synteticke_ucty,
    Zrus,
    Zrus_doklad,
    Zrus_transakci,
    Zrus_ucet,
    Zvol_adresar
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
