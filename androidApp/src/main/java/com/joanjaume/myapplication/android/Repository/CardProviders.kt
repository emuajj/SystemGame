package com.joanjaume.myapplication.android.Repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard


class CardProviders {

    private val db = FirebaseFirestore.getInstance()

    // For TaskCards
    fun getUserTaskCards(userId: String, callback: (List<TaskCard>?, FirebaseFirestoreException?) -> Unit) {
        db.collection("taskCards")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val cards = result.documents.mapNotNull { it.toObject(TaskCard::class.java) }
                callback(cards, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception as FirebaseFirestoreException)
            }
    }

    fun addUserTaskCard(taskCard: TaskCard, userId: String, callback: (Boolean, FirebaseFirestoreException?) -> Unit) {
        val taskCardWithUser = taskCard.apply { this.userId = userId } // Add userId to the card
        db.collection("taskCards")
            .add(taskCardWithUser)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { exception ->
                callback(false, exception as FirebaseFirestoreException)
            }
    }

    // For CpuCards
    fun getUserCpuCards(userId: String, callback: (List<CpuCard>?, FirebaseFirestoreException?) -> Unit) {
        db.collection("cpuCards")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val cards = result.documents.mapNotNull { it.toObject(CpuCard::class.java) }
                callback(cards, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception as FirebaseFirestoreException)
            }
    }

    fun addUserCpuCard(cpuCard: CpuCard, userId: String, callback: (Boolean, FirebaseFirestoreException?) -> Unit) {
        val cpuCardWithUser = cpuCard.apply { this.userId = userId } // Add userId to the card
        db.collection("cpuCards")
            .add(cpuCardWithUser)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { exception ->
                callback(false, exception as FirebaseFirestoreException)
            }
    }

    // For AlgorithmCards
    fun getUserAlgorithmCards(userId: String, callback: (List<AlgorithmCard>?, FirebaseFirestoreException?) -> Unit) {
        db.collection("algorithmCards")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val cards = result.documents.mapNotNull { it.toObject(AlgorithmCard::class.java) }
                callback(cards, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception as FirebaseFirestoreException)
            }
    }

    fun addUserAlgorithmCard(algorithmCard: AlgorithmCard, userId: String, callback: (Boolean, FirebaseFirestoreException?) -> Unit) {
        val algorithmCardWithUser = algorithmCard.apply { this.userId = userId } // Add userId to the card
        db.collection("algorithmCards")
            .add(algorithmCardWithUser)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { exception ->
                callback(false, exception as FirebaseFirestoreException)
            }
    }
}

