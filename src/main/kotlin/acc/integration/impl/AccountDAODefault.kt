package acc.integration.impl

import acc.model.AccGroup
import acc.model.AnalAcc
import acc.model.AnalId
import acc.model.Osnova
import acc.util.AccException
import acc.util.Messages
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.streams.toList

object AccountDAODefault {

    private val accountById = TreeMap<AnalId, AnalAcc>()
    private val accountByName = TreeMap<String, AnalAcc>()

    init {
        try {
            create(Osnova.pocatecniUcetRozvazny, "001", "Pocatecni ucet rozvazny")
            create(Osnova.dodavatele, "001", "Dodavatele")
            create(Osnova.banka, "001", "Fio")
            create(Osnova.material, "001", "Material")
            create(Osnova.pokladna, "001", "Pokladna")
        } catch (ex: AccException) {
            Logger.getLogger(AccountDAODefault::class.java.name).log(Level.SEVERE, null, ex)
        }

    }

    val all: List<AnalAcc>
        @Throws(AccException::class)
        get() = ArrayList(accountById.values)

    val balancing: List<AnalAcc>
        @Throws(AccException::class)
        get() = accountById.values.stream()
                .filter { a: AnalAcc -> a.balanced }.toList()

    val dodavatele: List<AnalAcc>
        get() = accountById.values.stream()
                .filter{ it.syntAccount==Osnova.dodavatele}
                .toList()

    val pokladna: List<AnalAcc>
        get() = accountById.values.stream()
                .filter{ it.syntAccount==Osnova.pokladna}
                .toList()

    val pocatecniUcetRozvazny: AnalAcc
        @Throws(AccException::class)
        get() = getByNumber(Osnova.pocatecniUcetRozvazny.number + "001").get()


    @Throws(AccException::class)
    fun create(skupina: AccGroup, no: String, name: String) {
        val a = AnalAcc(skupina, no, name)
        if (accountById.containsKey(a.id))
            throw AccException(Messages.Ucet_jiz_existuje.cm())
        accountById[a.id] = a
        accountByName[a.number] = a
    }

    fun update(skupina: AccGroup, no: String, name: String) {
        val a = AnalAcc(skupina, no, name)
        accountById[a.id] = a
        accountByName[a.number] = a
    }

    @Throws(AccException::class)
    fun delete(id: AnalId) {
        accountById.remove(id)
    }

    @Throws(AccException::class)
    fun getByNumber(name: String): Optional<AnalAcc> {
        return Optional.ofNullable(accountByName[name])
    }

    @Throws(AccException::class)
    fun getByGroup(accg: AccGroup): List<AnalAcc> {
        return accountById.values.stream()
                .filter { a -> a.syntAccount == accg }
                .toList()//collect<List<AnalAcc>, Any>(Collectors.toList())
    }

    @Throws(AccException::class)
    fun getByClass(accg: AccGroup): List<AnalAcc> {
        return accountById.values.stream()
                .filter { a -> a.aClass == accg }
                .toList()//.collect<List<AnalAcc>, Any>(Collectors.toList())
    }

}
