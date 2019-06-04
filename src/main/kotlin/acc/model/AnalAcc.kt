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

    override val isLoss: Boolean
        get() = syntAccount.isLoss

    override val isProfit: Boolean
        get() = syntAccount.isProfit

    override val isPassive: Boolean
        get() = syntAccount.isPassive

    override val isActive: Boolean
        get() = syntAccount.isActive

    override fun equals(other: Any?) =
            if (this === other)
                true
            else if (other !is AnalAcc) {
                false
            } else compareTo(other) == 0

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
