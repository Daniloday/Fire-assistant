package com.missclick.fireassistant.data.remote

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class FirebaseStore {

    val storageRef : StorageReference
    val ONE_MEGABYTE = 1024 * 1024.toLong()


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

    suspend fun getImage(userId: String, photoId: String) : ByteArray?{
        return try {
            val image = storageRef
                .child(userId)
                .child(photoId)
                .getBytes(5 * ONE_MEGABYTE)
                .await()
            image
        } catch (e : java.lang.Exception){
            Log.e("FirebaseStore", "Error getImage")
            null
        }
    }

//    fun uploadImage(userId : String, photoId : String, byteArray: ByteArray){
//        val ref = storageRef.child(userId).child(photoId)
//
//        ref.putBytes(byteArray)
//            .addOnSuccessListener {
//                Log.e("Upload img", "Success upload image")
//                //todo toast
//            }
//            .addOnFailureListener{
//                Log.e("Upload img", "Failure upload image")
//                //todo toast
//            }
//    }

}