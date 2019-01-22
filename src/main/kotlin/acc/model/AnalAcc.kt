package acc.model

class AnalAcc(override var parent: AccGroup?,
              val anal: String,
              override val fullName: String
) : AbstrGroup() {

    val syntAccount: AccGroup
        get() = parent!!

    override val isPassive: Boolean
        get() =syntAccount.isPassive
    override val isActive: Boolean
        get() = syntAccount.isActive

    override val groupType = GroupEnum.ANAL

    val id = AnalId(syntAccount, anal)

    val isPocUcetRozv: Boolean
        get() = syntAccount == Osnova.pocatecniUcetRozvazny

    val numberName: String
        get() = if (isPocUcetRozv) {
            ""
        } else "$number - $name"

    override fun compareTo(other: AbstrGroup): Int {
        return number.compareTo(other.number)
    }

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
        get() = syntAccount.number + anal


    override fun toString(): String {
        return number
    }


}
