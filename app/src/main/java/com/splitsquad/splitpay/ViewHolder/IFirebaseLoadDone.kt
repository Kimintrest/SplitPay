package com.splitsquad.splitpay.ViewHolder

interface IFirebaseLoadDone {
    fun onFirebaseLoadUserDone(lstEmail:List<String>)
    fun onFirebaseLoadFailed(messege:String)
}