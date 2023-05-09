package de.hhn.softwarelab.raspy.loginUI.components

import android.content.Context
import android.util.Log

class UserData {
    companion object {
        /**
         * var for user
         */
        private var userName = ""
        private var pw = ""
        private var userDescription = ""
        private var userId: String = ""


        /**
         * var for jason web token
         */
        private lateinit var jwt: String

        lateinit var SettingContext: Context

        /**
         * setter and getter method for the var's
         */


        fun setContext(context: Context) {
            this.SettingContext = context
        }

        fun getContext(): Context {
            return this.SettingContext
        }

        fun getUserDescription(): String {
            return userDescription
        }

        fun setUserName(userName: String) {
            Companion.userName = userName
        }

        fun getPw(): String {
            return pw
        }

        fun setPw(pw: String) {
            Companion.pw = pw
        }

        fun getUserId(): String {
            return userId
        }

        fun setUserId(userId: String) {
            Companion.userId = userId
        }
    }
}