package com.joanjaume.myapplication.repository

class TypesProvider {

    fun getCardModalities(): Map<String, Int> {
        return modalityTypes
    }

    fun getCardAlgorithms(): Map<String, Int> {
        return alogirhtmTypes
    }

    fun getAlgorithmByName(name: String): Int {
        return alogirhtmTypes[name] ?: 0
    }


}


val alogirhtmTypes = mapOf(
    "Shortest Job First" to 1,
    "Priorities" to 2,
    "Round Robin" to 3,
    "Highest Response Ratio Next" to 4
)

val modalityTypes = mapOf(
    "PREEMPTIVE" to 1,
    "NON_PREEMPTIVE" to 2
)
