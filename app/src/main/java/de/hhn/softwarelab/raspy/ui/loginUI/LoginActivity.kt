package de.hhn.softwarelab.raspy.ui.loginUI

import AuthenticationScreen
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.MainThread
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import de.hhn.softwarelab.raspy.backend.Services.UserService
import de.hhn.softwarelab.raspy.backend.dataclasses.User
import de.hhn.softwarelab.raspy.ui.loginUI.components.FormType
import de.hhn.softwarelab.raspy.ui.settings.SettingUI
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme

class LoginActivity : ComponentActivity() {

    override fun onStart() {
        super.onStart()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                AuthenticationScreen(
                    formType = FormType.LOGIN,
                    authenticationAction = { username, password ->
                        loginUser(
                            username,
                            password
                        )
                    },
                    switchAuthentication = { switchToRegister() }
                )
            }
        }
    }

    /**
     * Handles login for the user
     * @author Stefano Solombrino, Lui Tran
     * @param username name of the user
     * @param password password of the user
     */
    private fun loginUser(username: String, password: String) {
        val service = UserService()
        service.login(User(null, null, username, password, null, null))
    }

    /**
     * Starts Registration Activity
     * @author Stefano Solombrino
     */
    private fun switchToRegister() {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Preview(showBackground = true, device = Devices.DEFAULT)
@Composable
fun LoginPreview() {
    RaspSPYTheme(darkTheme = SettingUI.currentDarkModeState.value) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            AuthenticationScreen(
                formType = FormType.LOGIN,
                authenticationAction = { username, password -> {} },
                switchAuthentication = { }
            )
        }
    }
}