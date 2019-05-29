package acc.model

import java.util.*

class AccGroup(
        groupType: GroupEnum,
        number: String,
        name: String
) : AbstrAcc(null, groupType, number, name) {

    override val isActive: Boolean
        get() =
            if (parent != null)
                parent!!.isActive
            else number == "2"

    override val isPassive: Boolean
        get() =
            if (parent != null)
                parent!!.isPassive
            else when (number) {
                "3",
                "9" -> true
                else -> false
            }

    override val isLoss: Boolean
        get() {
             return if (parent != null)
                parent!!.isLoss
            else number == "5"
        }

    override val isProfit: Boolean
        get() =
            if (parent != null)
                parent!!.isProfit
            else number == "6"

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other !is AccGroup)
            return false
        return compareTo(other) == 0
    }

    override fun hashCode(): Int {
        var hash = 7
        hash = 71 * hash + Objects.hashCode(this.number)
        return hash
    }

    override fun compareTo(other: AbstrAcc): Int {
        return number.compareTo(other.number)
    }

}
