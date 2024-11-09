package com.splitsquad.splitpay.Utils

import com.splitsquad.splitpay.Model.User

object Common {
    val USER_UID_SAVE_KEY: String = "SAVE_KEY"
    val USER_INFORMATION: String = "UserInformation"
    lateinit var loggedUser: User
    val TOKENS: String = "Tokens"
}