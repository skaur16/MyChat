package com.example.mychat

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class FirebaseRepo {

    val db = Firebase.firestore
    val storageRef = Firebase.storage.reference

    suspend fun sendProfile(profile: Profile ) {

        db.collection("Profile")
            .document(profile.mail)
            .set(profile)
            .addOnSuccessListener {
                Log.e("Success", "success")
            }


            storageRef.child("Image/${profile.mail}")
                .putFile(profile.displayImage.toUri())
                .await()


    }

    suspend fun getProfile(): List<Profile> {

        return db.collection("Profile")
            .get()
            .await()
            .toObjects(Profile::class.java)
    }

   /* suspend fun getImage(profile:Profile) : Uri{
        return storageRef.child("Image/${profile.mail}")
            .downloadUrl
            .await()

    }*/

    suspend fun sendMessage(message: Message, groupId: String , groupIdReverse : String) {

        val doc = db.collection("Chats")
            .document(groupId)
            .get()
            .await()

        val doc2 = db.collection("Chats")
            .document(groupIdReverse)
            .get()
            .await()

        if (doc.exists()) {
            db.collection("Chats")
                .document(groupId)
                .update("messageArray", FieldValue.arrayUnion(message))
        }

        else if (doc2.exists()) {
            db.collection("Chats")
                .document(groupIdReverse)
                .update("messageArray", FieldValue.arrayUnion(message))
        }

        else {
            db.collection("Chats")
                .document(groupId)
                .set(hashMapOf("messageArray" to message))
        }
    }

    suspend fun getMessage(groupId : String , groupIdReverse : String) : ArrayOfMessage? {


        val doc = db.collection("Chats")
            .document(groupId)
            .get()
            .await()

        val doc2 = db.collection("Chats")
            .document(groupIdReverse)
            .get()
            .await()

        if(doc.exists()) {
             return db.collection("Chats")
                   .document(groupId)
                   .get()
                   .addOnSuccessListener{ document ->
                       Log.e("success", document.toString())
                       Log.e("success", document.data?.values.toString())
                   }
                   .await()
                   .toObject(ArrayOfMessage()::class.java)
        }

        else {
              return  db.collection("Chats")
                .document(groupIdReverse)
                .get()
                .addOnSuccessListener { document ->
                    Log.e("success", document.toString())
                    Log.e("success", document.data?.values.toString())
                }
                .await()
                .toObject(ArrayOfMessage()::class.java)
        }




    }





}