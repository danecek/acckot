package acc.integration

import acc.integration.impl.AcountDAODefault
import acc.model.AnalAcc
import acc.model.AccGroup
import acc.model.AnalId
import acc.util.AccException
import java.util.Optional

abstract class AccountDAO {

    abstract val all: List<AnalAcc>

    abstract val pocatecniUcetRozvazny: AnalAcc

    abstract val balancing: List<AnalAcc>

    @Throws(AccException::class)
    abstract fun create(skupina: AccGroup, no: String, name: String)

    @Throws(AccException::class)
    abstract fun getByNumber(name: String): Optional<AnalAcc>

    @Throws(AccException::class)
    abstract fun delete(id: AnalId)

    @Throws(AccException::class)
    abstract fun getByGroup(accg: AccGroup): List<AnalAcc>

    @Throws(AccException::class)
    abstract fun getByClass(accg: AccGroup): List<AnalAcc>

    companion object {

      //  var instance: AccountDAO = AcountDAODefault()
    }

}
