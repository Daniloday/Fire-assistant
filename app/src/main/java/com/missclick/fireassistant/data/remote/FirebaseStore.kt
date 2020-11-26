package com.missclick.fireassistant.data.remote

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseStore {

    val storageRef : StorageReference

    init {
        val storage = FirebaseStorage.getInstance()
        storageRef = storage.getReference("pictures")
    }

    fun uploadImage(id : String, byteArray: ByteArray){
        val ref = storageRef.child(id)

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