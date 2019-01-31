package acc.integration

import acc.Options
import acc.model.AccGroup
import acc.model.AnalAcc
import acc.model.AnalId
import acc.model.Osnova
import acc.util.AccException
import acc.util.Messages
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import javafx.scene.control.Alert
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

    private val accountById = TreeMap<AnalId, AnalAcc>()
    private val klaxon = Klaxon()

    private  fun save() {
        val fw = PrintWriter(FileWriter(Options.accountFile))
        accountById.values.forEach {
            fw.println(klaxon.toJsonString(AnalAccDTO(
                    groupn = it.id.group.number,
                    anal = it.anal,
                    name = it.name,
                    initAmount = it.initAmount)))
        }
        fw.close()
    }

    fun load() {
        try {
            if (Options.accountFile.exists())
                Files.lines(Options.accountFile.toPath())
                        .map {
                            klaxon.parse<AnalAccDTO>(it)
                        }
                        .forEach {
                            val g = Osnova.groupByNumber(it?.groupn!!)
                            val acc = AnalAcc(g, it.anal, it.name, it.initAmount)
                            accountById[acc.id] = acc
                        }
        } catch (ex: KlaxonException) {
            Alert(Alert.AlertType.ERROR,
                    Messages.Soubor_uctyxxxx_json_je_poskozen.cm()).show()
        }
    }


    init {
        load()
    }

    val allAccs: List<AnalAcc>
        @Throws(AccException::class)
        get() = accountById.values.toMutableList()

    val balanceAccs: List<AnalAcc>
        @Throws(AccException::class)
        get() = accountById.values.stream()
                .filter { a: AnalAcc -> a.balanced }.toList()

    val dodavatele: List<AnalAcc>
        get() = accountById.values.stream()
                .filter { it.syntAccount == Osnova.dodavatele }
                .toList()

    val pokladny: List<AnalAcc>
        get() = accountById.values.stream()
                .filter { it.syntAccount == Osnova.pokladna }
                .toList()

    @Throws(AccException::class)
    fun createAcc(group: AccGroup, anal: String, name: String, initAmount: Long) {
        val a = AnalAcc(group, anal, name, initAmount)
        if (accountById.containsKey(a.id))
            throw AccException(Messages.Ucet_jiz_existuje.cm())
        accountById[a.id] = a
        save()
    }

    @Throws(AccException::class)
    fun accById(id: AnalId) = accountById[id]!!

    fun updateAcc(id: AnalId, name: String, initAmount: Long) {
        with (accById(id)){
            this.name = name
            this.initAmount = initAmount
        }
        save()
    }

    @Throws(AccException::class)
    fun deleteAcc(id: AnalId) {
        accountById.remove(id)
        save()
    }
}
