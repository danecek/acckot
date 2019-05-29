package acc.model

import acc.Options

abstract class AbstrAcc(
        var parent: AccGroup?,
        val groupType: GroupEnum,
        open val number: String,
        var name: String
) : Comparable<AbstrAcc> {

    private fun croppedName() = name.take(Options.nameCrop)
    fun numberName(): String {
//        val sb = StringBuilder(number)
//        sb.append(" : ")
//        sb.append(croppedName())
//        sb.append(" ")
//        if (isActive)
//            sb.append("A")
//        if (isPassive)
//            sb.append("P")
//        if (isLoss)
//            sb.append("N")
//        if (isProfit)
//            sb.append("V")
//        return sb.toString()
        return "$number : ${croppedName()}"

    }

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