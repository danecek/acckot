package acc.integration

import acc.model.AccGroup
import acc.model.AnalAcc
import acc.model.Osnova
import acc.richclient.dialogs.ConfigInitDialog
import acc.util.AccException
import acc.util.Messages
import acc.util.accFail
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import java.nio.file.Files
import java.util.*
import kotlin.streams.toList

data class AnalAccDTO(
        val groupn: String,
        val anal: String,
        val name: String,
        val initAmount: Long)


object AccountCache {

    private val accountsByNumber = TreeMap<String, AnalAcc>()
    private val klaxon = Klaxon()

    private fun save() {
        Files.write(ConfigInitDialog.accountFile,
                accountsByNumber.values.map {
                    klaxon.toJsonString(AnalAccDTO(
                            groupn = it.parent!!.number,
                            anal = it.anal,
                            name = it.name,
                            initAmount = it.initAmount))
                })
    }


    private fun load() {
        try {
            if (Files.exists(ConfigInitDialog.accountFile))
                Files.lines(ConfigInitDialog.accountFile)
                        .map {
                            klaxon.parse<AnalAccDTO>(it)
                        }
                        .forEach {
                            val g = Osnova.groupByNumber(it?.groupn!!)
                            val acc = AnalAcc(g, it.anal, it.name, it.initAmount)
                            accountsByNumber[acc.number] = acc
                        }
        } catch (ex: KlaxonException) {
            accFail(ex)
        }
    }

    init {
        load()
    }

    val allAccs: List<AnalAcc>
        @Throws(AccException::class)
        get() = accountsByNumber.values.toMutableList()

    val balanceAccs: List<AnalAcc>
        @Throws(AccException::class)
        get() = accountsByNumber.values.stream()
                .filter { it.isBalanced }.toList()

    val incomeAccs: List<AnalAcc>
        @Throws(AccException::class)
        get() = accountsByNumber.values.stream()
                .filter { it.isIncome }.toList()

    val dodavatele: List<AnalAcc>
        get() = accountsByNumber.values.stream()
                .filter { it.syntAccount == Osnova.dodavatele }
                .toList()

    val pokladny: List<AnalAcc>
        get() = accountsByNumber.values.stream()
                .filter { it.syntAccount == Osnova.pokladna }
                .toList()

    @Throws(AccException::class)
    fun createAcc(group: AccGroup, anal: String, name: String, initAmount: Long) {
        val a = AnalAcc(group, anal, name, initAmount)
        if (accountsByNumber.containsKey(a.number))
            throw AccException(Messages.Ucet_jiz_existuje.cm())
        accountsByNumber[a.number] = a
        save()
    }

    @Throws(AccException::class)
    fun accByNumber(accNumber: String): AnalAcc {
        return accountsByNumber[accNumber]!!
    }

    @Throws(AccException::class)
    fun accByGroupNumber(group: AccGroup): String {
        val maxSynt = accountsByNumber.filter {
            it.value.parent == group
        }.map {
            it.value.anal
        }.max()?.toInt()?:0
        return "%03d".format(maxSynt+1)
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
        accountsByNumber.remove(acc.number)
        save()
    }
}
