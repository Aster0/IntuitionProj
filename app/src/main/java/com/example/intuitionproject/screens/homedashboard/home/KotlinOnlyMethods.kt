package com.example.intuitionproject.screens.homedashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.intuitionproject.models.Listing
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await

    suspend fun isChatExist(documentId: String): String? {
        val db = FirebaseFirestore.getInstance()
        val userName = FirebaseAuth.getInstance().currentUser!!.email
        val snapshots = db.collection("chats").whereEqualTo("target", documentId).whereEqualTo("username", userName).get().await()
        if(snapshots.isEmpty){
            return null
        }
        return snapshots.documents[0].id
    }
    fun getAllRequestsAsync(): LiveData<List<Listing>> = liveData {
        val db = FirebaseFirestore.getInstance()
        val returnList = ArrayList<Listing>()
        val snapshots = db.collection("requests").whereNotEqualTo("userid", FirebaseAuth.getInstance().currentUser?.email)
        val result = snapshots.get().await()
        for(i in result.documents){
            val data = i.data?.mapFirebaseToListingsModel(i.id)
            if(data != null){
                returnList.add(data)
            }
        }
        emit(returnList)
    }

    suspend fun Map<String, Any>.mapFirebaseToListingsModel(documentId: String): Listing{
        return Listing(
                if (get("dest-region") == null) "" else get("dest-region").toString(),
                if (get("details") == null) "" else get("details").toString(),
                if (get("meetup-region") == null) "" else get("meetup-region").toString(),
                get("payment").toString().toDouble(),
                if (get("picture-url") == null) "" else get("picture-url").toString(),
                if (get("title") == null) "" else get("title").toString(),
                if (get("userid") == null) "" else get("userid").toString(), get("timestamp").toString().toLong(),
                documentId,
                isChatExist(documentId))
    }

// emit as livedata
fun addNewChat(listingId: String) = liveData{
    val db = FirebaseFirestore.getInstance()
    emit(db.collection("chats").add(mapOf("target" to listingId, "username" to FirebaseAuth.getInstance().currentUser?.email)).await().id)
}
