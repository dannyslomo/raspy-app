package de.hhn.softwarelab.raspy.ui.loginUI.components

import de.hhn.softwarelab.raspy.R

enum class FormType(id: Int) {
    LOGIN(R.string.login),
    REGISTER(R.string.register);

    var id: Int = id

    fun getID():Int{
        return id
    }
}