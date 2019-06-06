/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.model

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.stream.Collector
import java.util.stream.Collectors

//import kotlin.streams.toList

object Osnova {

    private val accGroupsByNumber = TreeMap<String, AccGroup>()

    const val Software = "013"
    const val Pokladna = "211"
    val Bankovni_ucty = "221"
    const val Penize_na_ceste = "261"
    const val Odberatele = "311"
    const  val Dodavatele = "321"
    const val Spotreba_materialu = "501"
    const val Pocatecni_ucet_rozvazny = "961"
    const val Konecny_ucet_rozvazny = "962"

    val tridaZuctovaciVztahy: AccGroup
        get() = (accGroupsByNumber["3"])!!

    val tridaNaklady: AccGroup
        get() = accGroupsByNumber["5"]!!

    val dodavatele: AccGroup
        get() = accGroupsByNumber[Dodavatele]!!

    val material: AccGroup
        get() = accGroupsByNumber[Spotreba_materialu]!!

    val pokladna: AccGroup
        get() = accGroupsByNumber[Pokladna]!!

    val banka: AccGroup
        get() = accGroupsByNumber[Bankovni_ucty]!!

    val pocatecniUcetRozvazny: AccGroup
        get() = accGroupsByNumber[Pocatecni_ucet_rozvazny]!!

    val groups: List<AccGroup>
        get() = accGroupsByNumber.values.toList()
    lateinit var pocUcetRozv: AnalAcc

    init {
        val gris = AccGroup::class.java.getResourceAsStream("/acc/model/osnova.csv")
        val lines = BufferedReader(InputStreamReader(gris, StandardCharsets.UTF_8)).lines()
        lines.forEach { line ->
            val fields = line.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val number = fields[0].trim { it <= ' ' }
            if (number != "REMOVE") {
                val name = fields[1].trim { it <= ' ' }
                val type: GroupEnum = when (number.length) {
                    1 -> GroupEnum.CLASS
                    2 -> GroupEnum.GROUP
                    3 -> GroupEnum.SYNT_ACCOUNT
                    else -> error("neplatna osnova")
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

    fun groupByNumber(number: String): AccGroup {
        return accGroupsByNumber[number]!!
    }

    val syntAccounts: List<AccGroup> by lazy {
        accGroupsByNumber.values//.stream()
                .filter { a -> a.groupType == GroupEnum.SYNT_ACCOUNT }.toList()
    }

    val mainSyntAccounts: List<AccGroup> by lazy {
        val msa = AccGroup::class.java.getResourceAsStream("/acc/model/osnovavyber.csv")
        val lines = BufferedReader(InputStreamReader(msa, StandardCharsets.UTF_8))
                .lines().collect(Collectors.toList())
        syntAccounts.filter {
            it.name in lines
        }

    }



}

