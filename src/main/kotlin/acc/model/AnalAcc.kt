package acc.model

class AnalAcc(parent: AccGroup?,
              val anal: String,
              name: String,
              var initAmount: Long) : AbstrGroup(
        parent = parent,
        groupType = GroupEnum.ANAL,
        number = parent!!.number + anal,
        name = name) {

    val syntAccount: AccGroup
        get() = parent!!

    override val isPassive: Boolean
        get() = syntAccount.isPassive
    override val isActive: Boolean
        get() = syntAccount.isActive

    val id = AnalId(syntAccount, anal)

    override fun compareTo(other: AbstrGroup): Int {
        return number.compareTo(other.number)
    }

    override fun equals(other: Any?): Boolean {
        return if (other !is AnalAcc) {
            false
        } else other.id == id
    }

    override fun hashCode(): Int {
        return number.hashCode()
    }

//    override val number: String
//        get() = syntAccount.number + anal


    override fun toString(): String {
        return numberName
    }


}
