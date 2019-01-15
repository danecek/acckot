package acc.model

import java.io.Serializable

data class Address(val address: String) : Serializable {

    override fun toString(): String {
        return this.address
    }
}
