package acc.model

import java.util.Objects
import java.util.Optional

class AccGroup(
        override val groupType: GroupEnum,
        override val number: String,
        override val fullName: String
        ) : AbstrGroup() {

    override var parent: AccGroup? = null
    val optParent: Optional<AccGroup>
        get() = Optional.ofNullable(parent)

    override val isActive: Boolean
        get() {
            if (groupType === GroupEnum.CLASS) {
                when (number) {
                    "2" -> return true
                    else -> return false
                }
            }
            return parent!!.isActive
        }

    override val isPassive: Boolean
        get() {
            if (groupType === GroupEnum.CLASS) {
                when (number) {
                    "3", "9" -> return true
                    else -> return false
                }
            }
            return parent!!.isPassive
        }



/*    fun setParent(parent: AccGroup) {
        this.parent = parent
    }*/

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

    override fun toString(): String {
        return "$number - $name"
    }

    override fun compareTo(other: AbstrGroup): Int {
        return number.compareTo((other as AccGroup).number)
    }

}
