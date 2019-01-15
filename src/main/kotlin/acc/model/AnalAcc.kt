package acc.model

class AnalAcc(override var parent: AccGroup?,
              val anal: String,
              override val fullName: String
) : AbstrGroup() {
    override val isPassive: Boolean
        get() = parent!!.isPassive
    override val isActive: Boolean
        get() = parent!!.isActive

    override val groupType = GroupEnum.ANAL

    val id = AnalId(parent!!, anal)

    val isPocUcetRozv: Boolean
        get() = parent == Osnova.pocatecniUcetRozvazny

    val numberName: String
        get() = if (isPocUcetRozv) {
            ""
        } else "$number - $name"

    override fun compareTo(other: AbstrGroup): Int {
        return number.compareTo(other.number)
    }

    val syntAccount: AccGroup
        get() = parent!!

    val aClass: AccGroup
        get() = syntAccount
                .optParent.get()
                .optParent.get()


    override fun equals(other: Any?): Boolean {
        return if (other !is AnalAcc) {
            false
        } else other.id == id
    }

    override val number: String
        get() = parent!!.number + anal


    override fun toString(): String {
        return number
    }



}
