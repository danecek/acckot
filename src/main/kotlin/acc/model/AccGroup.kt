package acc.model

import java.util.*

class AccGroup(
        groupType: GroupEnum,
        number: String,
        name: String
) : AbstrGroup(null, groupType, number, name) {

    override val isActive: Boolean
        get() {
            if (groupType === GroupEnum.CLASS) {
                return when (number) {
                    "2" -> true
                    else -> false
                }
            }
            return parent!!.isActive
        }

    override val isPassive: Boolean
        get() {
            if (groupType === GroupEnum.CLASS) {
                return when (number) {
                    "3",
                    "9" -> true
                    else -> false
                }
            }
            return parent!!.isPassive
        }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        return if (other !is AccGroup) {
            false
        } else compareTo((other as AccGroup?)!!) == 0
    }

    override fun hashCode(): Int {
        var hash = 7
        hash = 71 * hash + Objects.hashCode(this.number)
        return hash
    }

    override fun compareTo(other: AbstrGroup): Int {
        return number.compareTo(other.number)
    }

}
