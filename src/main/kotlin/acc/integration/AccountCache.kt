package acc.integration

import acc.Options
import acc.model.AccGroup
import acc.model.AnalAcc
import acc.model.Osnova
import acc.util.AccException
import acc.util.Messages
import acc.util.fxAlert
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Files
import java.util.*
import kotlin.streams.toList

data class AnalAccDTO(
        val groupn: String,
        val anal: String,
        val name: String,
        val initAmount: Long)


object AccountCache {

    private val accountByNumber = TreeMap<String, AnalAcc>()
    private val klaxon = Klaxon()

    private fun save() {
        val fw = PrintWriter(FileWriter(Options.accountFile))
        fw.use {
            accountByNumber.values.forEach {
                val line = klaxon.toJsonString(AnalAccDTO(
                        groupn = it.parent!!.number,
                        anal = it.anal,
                        name = it.name,
                        initAmount = it.initAmount))
                fw.println(line)
            }
        }

    }

    private fun load() {
        try {
            if (Options.accountFile.exists())
                Files.lines(Options.accountFile.toPath())
                        .map {
                            klaxon.parse<AnalAccDTO>(it)
                        }
                        .forEach {
                            val g = Osnova.groupByNumber(it?.groupn!!)
                            val acc = AnalAcc(g, it.anal, it.name, it.initAmount)
                            accountByNumber[acc.number] = acc
                        }
        } catch (ex: KlaxonException) {
            fxAlert(Messages.Soubor_uctyxxxx_json_je_poskozen.cm())
        }
    }

    init {
        load()
    }

    val allAccs: List<AnalAcc>
        @Throws(AccException::class)
        get() = accountByNumber.values.toMutableList()

    val balanceAccs: List<AnalAcc>
        @Throws(AccException::class)
        get() = accountByNumber.values.stream()
                .filter { it.isBalanced }.toList()

    val incomeAccs: List<AnalAcc>
        @Throws(AccException::class)
        get() = accountByNumber.values.stream()
                .filter { it.isIncome }.toList()

    val dodavatele: List<AnalAcc>
        get() = accountByNumber.values.stream()
                .filter { it.syntAccount == Osnova.dodavatele }
                .toList()

    val pokladny: List<AnalAcc>
        get() = accountByNumber.values.stream()
                .filter { it.syntAccount == Osnova.pokladna }
                .toList()

    @Throws(AccException::class)
    fun createAcc(group: AccGroup, anal: String, name: String, initAmount: Long) {
        val a = AnalAcc(group, anal, name, initAmount)
        if (accountByNumber.containsKey(a.number))
            throw AccException(Messages.Ucet_jiz_existuje.cm())
        accountByNumber[a.number] = a
        save()
    }

    @Throws(AccException::class)
    fun accByNumber(accNumber: String):AnalAcc {
        return accountByNumber[accNumber]!!
    }

    fun updateAcc(acc: AnalAcc, _name: String, _initAmount: Long) {
        with(accByNumber(acc.number)) {
            name = _name
            initAmount = _initAmount
        }
        save()
    }

    @Throws(AccException::class)
    fun deleteAcc(acc: AnalAcc) {
        accountByNumber.remove(acc.number)
        save()
    }
}
