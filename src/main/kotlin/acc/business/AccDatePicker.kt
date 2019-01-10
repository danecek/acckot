/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.business

import java.time.LocalDate
import java.util.Optional

import acc.util.Global
import javafx.scene.control.DatePicker
import javafx.util.StringConverter

class AccDatePicker() : DatePicker() {

    val optDate: Optional<LocalDate>
        get() = Optional.ofNullable(value)

    init {

        converter = object : StringConverter<LocalDate>() {
            override fun toString(date: LocalDate?): String {
                return if (date != null) {
                    Global.df.format(date)
                } else {
                    ""
                }
            }

            override fun fromString(string: String?): LocalDate? {
                return if (string != null && !string.isEmpty()) {
                    LocalDate.parse(string, Global.df)
                } else {
                    null
                }
            }
        }
    }

    constructor(localDate: LocalDate) : this() {
        value = localDate
    }


}
