/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.model

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.Optional
import java.util.TreeMap
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.streams.toList

object Osnova {

    private val accGroupsByNumber = TreeMap<String, AccGroup>()

    val tridaZuctovaciVztahy: AccGroup
        get() = (accGroupsByNumber["3"])!!

    val tridaNaklady: AccGroup
        get() = accGroupsByNumber["5"]!!

    val dodavetele: AccGroup
        get() = accGroupsByNumber["321"]!!

    val material: AccGroup
        get() = accGroupsByNumber["321"]!!

    val pocatecniUcetRozvazny: AccGroup
        get() = accGroupsByNumber["961"]!!

    val groups: List<AccGroup>
        get() = accGroupsByNumber.values
                .stream().toList()
    lateinit var pocUcetRozv: AnalAcc
    //       .collect<List<AccGroup>, Any>(Collectors.<AccGroup>toList())

    init {
        val gris = AccGroup::class.java.getResourceAsStream("/acc/model/osnova.csv")
        val lines = BufferedReader(InputStreamReader(gris, StandardCharsets.UTF_8)).lines()
        lines.forEach { line ->
            val fields = line.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val number = fields[0].trim { it <= ' ' }
            if (number != "REMOVE") {
                val name = fields[1].trim { it <= ' ' }
                val type: GroupEnum
                when (number.length) {
                    1 -> {
                        type = GroupEnum.CLASS
                    }
                    2 -> {
                        type = GroupEnum.GROUP
                    }
                    3 -> {
                        type = GroupEnum.SYNT_ACCOUNT
                    }
                    else -> throw RuntimeException()
                }
                val ac = AccGroup(type, number, name)
                accGroupsByNumber[ac.number] = ac
            }
            accGroupsByNumber.values.stream()
                    .filter { ag -> ag.groupType !== GroupEnum.CLASS }
                    .forEach { ag ->
                        val parNumber = ag.number.substring(0, ag.number.length - 1)
                        ag.parent = accGroupsByNumber[parNumber]
                    }

        }
    }

    fun getGroup(number: String): Optional<AccGroup> {
        return Optional.ofNullable(accGroupsByNumber[number])
    }

    fun syntAccounts(): List<AccGroup> {
        return accGroupsByNumber.values.stream()
                .filter { a -> a.groupType === GroupEnum.SYNT_ACCOUNT }.toList()
        //      .collect<List<AccGroup>, Any>(Collectors.toList())
    }

    fun getSubGroups(prefix: String): List<AccGroup> {
        return groups.stream()
                .filter { g -> g.number.startsWith(prefix) }.toList()
        //  .collect<List<AccGroup>, Any>(Collectors.toList())
    }

/*        companion object {

            val instance = Osnova()
        }*/
}

