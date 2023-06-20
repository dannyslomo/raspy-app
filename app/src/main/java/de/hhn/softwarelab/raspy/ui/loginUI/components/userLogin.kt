import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.ui.livestreamUI.LivestreamActivity
import de.hhn.softwarelab.raspy.ui.loginUI.components.FormType
import de.hhn.softwarelab.raspy.ui.settings.SettingUI
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    formType: FormType,
    authenticationAction: (username: String, password: String) -> Unit,
    switchAuthentication: () -> Unit
) {
    RaspSPYTheme(darkTheme = SettingUI.currentDarkModeState.value) {
        Scaffold(topBar = { AuthenticationTopAppBar() }) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                AuthenticationHeading(formType = formType)
                Spacer(modifier = Modifier.padding(14.dp))
                AuthenticationInputForm(
                    formType = formType,
                    authenticateAction = authenticationAction
                )
                Spacer(modifier = Modifier.padding(20.dp))
                AuthenticationSwitcher(
                    formType = formType,
                    switchAuthentication = switchAuthentication
                )
            }
        }
    }
}

/**
 * TopAppBar
 * @author Stefano Solombrino
 */
@Composable
fun AuthenticationTopAppBar() {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
    )
}

@Composable
fun AuthenticationHeading(formType: FormType) {
    Text(
        text = stringResource(id = formType.getID()), style = MaterialTheme.typography.h1
    )
}

/**
 * Input Form depending on Authentication Type
 * @author Stefano Solombrino
 * @param formType Type of Authentication
 * @param authenticateAction onClick Action with username and password
 */
@Composable
fun AuthenticationInputForm(
    formType: FormType, authenticateAction: (username: String, password: String) -> Unit
) {
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val typeText = stringResource(id = formType.getID())
    val context = LocalContext.current
    // Username-Input
    OutlinedTextField(
        modifier = Modifier.testTag("usernameText"),
        value = usernameState.value,
        label = { Text(text = stringResource(id = R.string.username)) },
        onValueChange = { usernameState.value = it },
        leadingIcon = { Icon(Icons.Filled.Person, "") },
        singleLine = true,
    )
    Spacer(modifier = Modifier.padding(2.dp))
    // Password-Input
    OutlinedTextField(
        modifier = Modifier.testTag("passwordText"),
        value = passwordState.value,
        label = { Text(text = stringResource(R.string.password)) },
        onValueChange = { passwordState.value = it },
        leadingIcon = { Icon(Icons.Filled.Lock, "") },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
            }) {
                Icon(
                    if (passwordVisible.value) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff, ""
                )
            }
        },
        singleLine = true,
        visualTransformation =
        if (passwordVisible.value) VisualTransformation.None
        else PasswordVisualTransformation(),
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Button(
        onClick = {
            authenticateAction(usernameState.value, passwordState.value)
            val intent = Intent(context, LivestreamActivity::class.java)
            context.startActivity(intent)
            },
        modifier = Modifier
            .size(200.dp, 48.dp)
            .testTag("loginButton")
    ) {
        Text(text = typeText)
    }
}

/**
 * Footer for Switching Authentication depending on formType
 * @author Stefano Solombrino
 * @param formType Type of Authentication
 * @param switchAuthentication onClick passthrough
 */
@Composable
fun AuthenticationSwitcher(formType: FormType, switchAuthentication: () -> Unit) {
    when (formType) {
        FormType.LOGIN -> {
            Text(text = stringResource(id = R.string.notRegistered))
            AuthenticationSwitchButton(
                formType = FormType.REGISTER, switchAuthentication = switchAuthentication
            )
        }
        FormType.REGISTER -> {
            Text(text = stringResource(id = R.string.alreadyRegistered))
            AuthenticationSwitchButton(
                formType = FormType.LOGIN, switchAuthentication = switchAuthentication
            )
        }
    }
}

/**
 * Button for Switching to Login/Register
 * @author Stefano Solombrino
 * @param formType Type of Authentication
 * @param switchAuthentication onClick Action
 */
@Composable
fun AuthenticationSwitchButton(formType: FormType, switchAuthentication: () -> Unit) {
    Spacer(modifier = Modifier.padding(8.dp))
    Button(
        modifier = Modifier.testTag("switchButton"),
        onClick = { switchAuthentication() }) {
        Text(text = stringResource(id = formType.getID()))
    }
}

@Preview(showBackground = true, device = Devices.DEFAULT)
@Composable
fun AuthenticationPreview() {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AuthenticationScreen(formType = FormType.LOGIN,
                authenticationAction = { username, password -> {} },
                switchAuthentication = {})
        }
}