package de.hhn.softwarelab.raspy.ui.loginUI

import AuthenticationScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import de.hhn.softwarelab.raspy.ui.loginUI.components.FormType
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkMode = remember { mutableStateOf(false) }
            RaspSPYTheme(darkTheme = darkMode){
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
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
    }

    /**
     * Handles login for the user
     * @author Stefano Solombrino, Lui Tran
     * @param username name of the user
     * @param password password of the user
     */
    private fun loginUser(username: String, password: String) {

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
    val darkMode = remember { mutableStateOf(false)}
    RaspSPYTheme(darkTheme = darkMode) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            AuthenticationScreen(
                formType = FormType.LOGIN,
                authenticationAction = { username, password -> {} },
                switchAuthentication = { }
            )
        }
    }
}