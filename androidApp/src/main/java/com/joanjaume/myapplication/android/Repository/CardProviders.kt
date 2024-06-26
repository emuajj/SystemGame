package com.joanjaume.myapplication.android.Repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard


class CardProviders {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    fun getAllTaskCards(callback: (List<TaskCard>) -> Unit) {
        database.child("taskCards").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cards = snapshot.children.map { it.getValue(TaskCard::class.java)!! }
                callback(cards)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })
    }

    // Implement other methods similarly
}
