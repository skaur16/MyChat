package com.example.mychat

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseRepo {

    val db = Firebase.firestore

    fun sendProfile(profile: Profile) {

        db.collection("Profile")
            .document(profile.mail)
            .set(profile)
            .addOnSuccessListener {
                Log.e("Success", "success")
            }

    }

    suspend fun getProfile(): List<Profile> {

        return db.collection("Profile")
            .get()
            .await()
            .toObjects(Profile::class.java)
    }

    suspend fun sendMessage(message: Message, groupId: String) {

        val doc = db.collection("Chats")
            .document(groupId)
            .get()
            .await()

        if (doc.exists()) {
            db.collection("Chats")
                .document(groupId)
                .update("messageArray", FieldValue.arrayUnion(message))
        } else {
            db.collection("Chats")
                .document(groupId)
                .set(hashMapOf("messageArray" to message))
        }
    }

    suspend fun getMessage(groupId:String): ArrayOfMessage? {


        val array = db.collection("Chats")
            .document(groupId)
            .get()
            .addOnSuccessListener { document ->
                Log.e("success", document.toString())
                Log.e("success", document.data?.values.toString())
            }
            .await()
            .toObject(ArrayOfMessage()::class.java)

        return array

    }
}