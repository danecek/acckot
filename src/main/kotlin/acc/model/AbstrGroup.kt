package acc.model

import acc.Options

abstract class AbstrGroup(
        var parent: AccGroup?,
        val groupType: GroupEnum,
        open val number: String,
        var name: String
) : Comparable<AbstrGroup> {

    private val cropName: String
        get() = name.take(Options.nameCrop)
    val numberName = "$number - $cropName"
    abstract val isActive: Boolean
    abstract val isPassive: Boolean
    val balanced: Boolean
        get() = isPassive || isActive

    override fun toString() = numberName


}