package de.hhn.softwarelab.raspy.ui.loginUI

import AuthenticationScreen
import android.content.Intent
import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import de.hhn.softwarelab.raspy.backend.Services.UserService
import de.hhn.softwarelab.raspy.backend.dataclasses.User
import de.hhn.softwarelab.raspy.ui.loginUI.components.FormType
import de.hhn.softwarelab.raspy.ui.settings.SettingUI
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import de.hhn.softwarelab.raspy.ui.theme.LightColorScheme
import android.app.ActivityManager
import android.content.Context


class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LightColorScheme.background,
                ) {
                    AuthenticationScreen(
                        formType = FormType.REGISTER,
                        authenticationAction = {username, password ->
                            registerUser(
                                username,
                                password
                            )
                        },
                        switchAuthentication = {switchToLogin()}
                    )
                }
        }
    }


    /**
     * Handles Registration of a new user
     * @author Stefano Solombrino
     * @param username username of the new user
     * @param password password od the new user
     */
    private fun registerUser(username: String, password: String) {
        val service =  UserService()
        service.register(User(null,null, username, password, null, null))
    }

    /**
     * Switch to LoginActivity
     * @author Stefano Solombrino
     */
    private fun switchToLogin(){
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Preview(showBackground = true, device = Devices.DEFAULT)
@Composable
fun RegisterPreview() {
    RaspSPYTheme(darkTheme = SettingUI.currentDarkModeState.value) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            //color = MaterialTheme.colors.background
        ) {
            AuthenticationScreen(
                formType = FormType.REGISTER,
                authenticationAction = { username,password ->
                    {}
                },
                switchAuthentication = {}
            )
        }
    }
}