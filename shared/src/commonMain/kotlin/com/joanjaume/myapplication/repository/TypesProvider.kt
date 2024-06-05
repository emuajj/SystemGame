package com.joanjaume.myapplication.repository

import com.joanjaume.myapplication.models.interfaces.cardInterface.*

class TypesProvider {

    fun getCardModalities(): Map<String, Int> {
        return modalityTypes
    }

    fun getCardAlgorithms(): Map<String, Int>{
        return alogirhtmTypes
    }


}


val alogirhtmTypes = mapOf(
    "SJF" to 1,
    "PRIORITIES" to 2
)

val modalityTypes = mapOf(
    "PREEMPTIVE" to 1,
    "NON_PREEMPTIVE" to 2
)
