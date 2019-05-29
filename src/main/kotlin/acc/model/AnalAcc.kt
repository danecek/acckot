package acc.model

class AnalAcc(parent: AccGroup?,
              val anal: String,
              name: String,
              var initAmount: Long) : AbstrAcc(
        parent = parent,
        groupType = GroupEnum.ANAL,
        number = "${parent!!.number}.$anal",
        name = name) {

    val syntAccount: AccGroup
        get() = parent!!

    val id = AnalId(syntAccount, anal)

    override val isLoss: Boolean
        get() = syntAccount.isLoss

    override val isProfit: Boolean
        get() = syntAccount.isProfit

    override val isPassive: Boolean
        get() = syntAccount.isPassive

    override val isActive: Boolean
        get() = syntAccount.isActive


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return false
        return if (other !is AnalAcc) {
            false
        } else other.id == id
    }

    override fun hashCode(): Int {
        return number.hashCode()
    }

    override fun compareTo(other: AbstrAcc): Int {
        return number.compareTo(other.number)
    }

    override fun toString(): String {
        return numberName()
    }

}
