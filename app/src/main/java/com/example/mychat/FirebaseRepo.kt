package com.example.mychat

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseRepo {

    val db = Firebase.firestore

    fun sendProfile(profile: Profile){

        db.collection("Profile")
            .document(profile.mail)
            .set(profile)
            .addOnSuccessListener {
                Log.e("Success","success")
            }

    }

    suspend fun getProfile() : List<Profile>{

        return db.collection("Profile")
            .get()
            .await()
            .toObjects(Profile::class.java)
    }

}