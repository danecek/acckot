package acc.model

data class AnalId(val group : AccGroup, val anal : String) : Comparable<AnalId> {
    override fun compareTo(other: AnalId): Int {
        val x = other.group.compareTo(group)
        return if (x!=0)  x
        else
            other.anal.compareTo(anal)
    }
}