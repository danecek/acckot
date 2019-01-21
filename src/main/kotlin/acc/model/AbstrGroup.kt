package acc.model

abstract class AbstrGroup : Comparable<AbstrGroup> {
         abstract val groupType: GroupEnum
         abstract val number: String
         abstract val fullName: String
         abstract var parent: AccGroup?

        val name: String
                get() = fullName.take(30)
        abstract val isActive: Boolean
        abstract val isPassive: Boolean
        open val balanced: Boolean
                get() = isPassive || isActive


}