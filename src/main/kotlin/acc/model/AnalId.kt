package acc.model

data class AnalId(val group : AccGroup, val anal : String) : Comparable<AnalId> {
    override fun compareTo(other: AnalId): Int {
        val x = other.group.compareTo(group)
        if (x!=0) return x
        else
            return other.anal.compareTo(anal)
    }
}