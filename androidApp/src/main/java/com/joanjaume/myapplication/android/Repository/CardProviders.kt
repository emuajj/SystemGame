package com.joanjaume.myapplication.android.Repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

class CardProviders {

    private val db = FirebaseFirestore.getInstance()

    fun getAllTaskCards(callback: (List<TaskCard>?, FirebaseFirestoreException?) -> Unit) {
        db.collection("taskCards")
            .get()
            .addOnSuccessListener { result ->
                val cards = result.documents.mapNotNull { it.toObject(TaskCard::class.java) }
                callback(cards, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception as FirebaseFirestoreException)
            }
    }

    fun addTaskCard(taskCard: TaskCard, callback: (Boolean, FirebaseFirestoreException?) -> Unit) {
        db.collection("taskCards")
            .add(taskCard)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { exception ->
                callback(false, exception as FirebaseFirestoreException)
            }
    }

    // Implement other methods similarly

    fun getAllCpuCards(callback: (List<CpuCard>?, FirebaseFirestoreException?) -> Unit) {
        db.collection("cpuCards")
            .get()
            .addOnSuccessListener { result ->
                val cards = result.documents.mapNotNull { it.toObject(CpuCard::class.java) }
                callback(cards, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception as FirebaseFirestoreException)
            }
    }

    fun addCpuCard(cpuCard: CpuCard, callback: (Boolean, FirebaseFirestoreException?) -> Unit) {
        db.collection("cpuCards")
            .add(cpuCard)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { exception ->
                callback(false, exception as FirebaseFirestoreException)
            }
    }

    // Methods for AlgorithmCard
    fun getAllAlgorithmCards(callback: (List<AlgorithmCard>?, FirebaseFirestoreException?) -> Unit) {
        db.collection("algorithmCards")
            .get()
            .addOnSuccessListener { result ->
                val cards = result.documents.mapNotNull { it.toObject(AlgorithmCard::class.java) }
                callback(cards, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception as FirebaseFirestoreException)
            }
    }

    fun addAlgorithmCard(algorithmCard: AlgorithmCard, callback: (Boolean, FirebaseFirestoreException?) -> Unit) {
        db.collection("algorithmCards")
            .add(algorithmCard)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { exception ->
                callback(false, exception as FirebaseFirestoreException)
            }
    }
}
