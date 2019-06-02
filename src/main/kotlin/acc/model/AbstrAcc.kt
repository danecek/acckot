package acc.model

import acc.Options

abstract class AbstrAcc(
        var parent: AccGroup?,
        val groupType: GroupEnum,
        open val number: String,
        var name: String
) : Comparable<AbstrAcc> {

    private fun croppedName() = name.take(Options.nameCrop)
    fun numberName()="$number : ${croppedName()}"

    abstract val isActive: Boolean
    abstract val isPassive: Boolean
    val isBalanced: Boolean
        get() = isPassive || isActive
    abstract val isLoss: Boolean
    abstract val isProfit: Boolean
    val isIncome: Boolean
        get() = isLoss || isProfit

    override fun toString() = number//numberName()
}