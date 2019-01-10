package acc.integration.impl

import acc.integration.AccountDAO
import acc.model.AnalAcc
import acc.model.AccGroup
import acc.model.AccId
import acc.model.Osnova
import acc.util.AccException

import java.util.ArrayList
import java.util.Optional
import java.util.TreeMap
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.streams.toList

object AcountDAODefault : AccountDAO() {

    private val accountById = TreeMap<AccId, AnalAcc>()
    private val accountByName = TreeMap<String, AnalAcc>()

    override val all: List<AnalAcc>
        @Throws(AccException::class)
        get() = ArrayList(accountById.values)

    override val balancing: List<AnalAcc>
        @Throws(AccException::class)
        get() = accountById.values.stream().filter { a: AnalAcc -> a.balanced }.toList()//collect<List<AnalAcc>, Any>(Collectors.toList())

    override val pocatecniUcetRozvazny: AnalAcc
        @Throws(AccException::class)
        get() = getByNumber(Osnova.pocatecniUcetRozvazny.number + "001").get()

    init {
        try {
            create(Osnova.pocatecniUcetRozvazny, "001", "Pocatecni ucet rozvazny")
            create(Osnova.getGroup("321").get(), "001", "Dodavatele")
            create(Osnova.getGroup("221").get(), "001", "Fio")
            create(Osnova.getGroup("501").get(), "001", "Material")

        } catch (ex: AccException) {
            Logger.getLogger(AcountDAODefault::class.java.name).log(Level.SEVERE, null, ex)
        }

    }

    @Throws(AccException::class)
    override fun create(skupina: AccGroup, no: String, name: String) {
        val a = AnalAcc(keyC++, skupina, no, name)
        accountById[a.id] = a
        accountByName[a.number] = a
    }

    @Throws(AccException::class)
    override fun delete(id: AccId) {
        accountById.remove(id)
    }

    @Throws(AccException::class)
    override fun getByNumber(name: String): Optional<AnalAcc> {
        return Optional.ofNullable(accountByName[name])
    }

    @Throws(AccException::class)
    override fun getByGroup(accg: AccGroup): List<AnalAcc> {
        return accountById.values.stream()
                .filter { a -> a.syntAccount == accg }
                .toList()//collect<List<AnalAcc>, Any>(Collectors.toList())
    }

    @Throws(AccException::class)
    override fun getByClass(accg: AccGroup): List<AnalAcc> {
        return accountById.values.stream()
                .filter { a -> a.aClass == accg }
                .toList()//.collect<List<AnalAcc>, Any>(Collectors.toList())
    }

    var keyC = 1


}
