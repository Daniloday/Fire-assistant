package com.missclick.fireassistant.data.remote

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.*

class FirebaseStore {

    val storageRef : StorageReference

    init {
        val storage = FirebaseStorage.getInstance()
        storageRef = storage.getReference("pictures")
    }

    suspend fun uploadImageSuspend(userId : String, photoId : String, byteArray: ByteArray) : Boolean{
        return try {
            val data = storageRef
                    .child(userId)
                    .child(photoId)
                    .putBytes(byteArray)
                    .await()
            true
        } catch (e : Exception){
            false
        }
    }

    fun uploadImage(userId : String, photoId : String, byteArray: ByteArray){
        val ref = storageRef.child(userId).child(photoId)

        ref.putBytes(byteArray)
            .addOnSuccessListener {
                Log.e("Upload img", "Success upload image")
                //todo toast
            }
            .addOnFailureListener{
                Log.e("Upload img", "Failure upload image")
                //todo toast
            }
    }

    fun getImage(){

    }
}