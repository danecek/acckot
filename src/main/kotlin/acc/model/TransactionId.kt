package acc.model

data class TransactionId(val id: Int) : Comparable<TransactionId> {
    override fun compareTo(other: TransactionId): Int =
            id.compareTo(other.id)

    override fun toString()= id.toString()
}