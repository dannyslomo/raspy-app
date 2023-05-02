package de.hhn.softwarelab.raspy.ui.settings

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import de.hhn.softwarelab.raspspy.R
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme

class SettingList : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaspSPYTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    SettingsScreen(this)
                }
            }
        }
    }
}

/**
 *
 */
@Composable
fun SettingsScreen(context: Context) {
    var isSwitchEnabled1 by remember { mutableStateOf(true) }
    var isSwitchEnabled2 by remember { mutableStateOf(true) }
    var isSwitchEnabled3 by remember { mutableStateOf(true) }
    Column {
        //Title
        HeaderText()
        //Profile
        Profile()
        //Activate/Deactivate System with SWITCH
        CardWithSwitch(
            icon = R.drawable.user_profil_icon,
            mainText = "Security System ",
            switchState = isSwitchEnabled1,
            onSwitchStateChanged = { isEnabled ->
                isSwitchEnabled1 = isEnabled
                if (isEnabled) {
                    Toast.makeText(context, "1 ON", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "1 OFF", Toast.LENGTH_SHORT).show()
                }
            })
        //Activate/Deactivate Camera with SWITCH
        CardWithSwitch(
            icon = R.drawable.user_profil_icon,
            mainText = "Camera",
            switchState = isSwitchEnabled2,
            onSwitchStateChanged = { isEnabled ->
                isSwitchEnabled2 = isEnabled
                if (isEnabled) {
                    Toast.makeText(context, "2 ON", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "2 OFF", Toast.LENGTH_SHORT).show()
                }
            })
        CardWithSwitch(
            icon = R.drawable.user_profil_icon,
            mainText = "Dark Mode",
            switchState = isSwitchEnabled3,
            onSwitchStateChanged = { isEnabled ->
                isSwitchEnabled3 = isEnabled
                if (isEnabled) {
                    Toast.makeText(context, "2 ON", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "2 OFF", Toast.LENGTH_SHORT).show()
                }
            })

    }
}

/**
 * Header Text
 */
@Composable
fun HeaderText() {
    Text(
        text = "Settings",
        color = Color.Gray,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    )
}

/**
 *
 */
@Composable
fun Profile() {
    var editedUsername by remember { mutableStateOf("John") }
    var editedEmail by remember { mutableStateOf("john@example.com") }
    var isEmailVerified by remember { mutableStateOf(false) }
    var isEditingProfile by remember { mutableStateOf(false) }

    if (isEditingProfile) {
        ProfilePanel(
            onSave = { username, email ->
                editedUsername = username
                editedEmail = email
                isEditingProfile = false
                isEmailVerified = true
            },
            onDismiss = {
                // Cancel editing
                isEditingProfile = false
            }
        )
    } else {
        ProfileCardUI(
            username = editedUsername,
            email = editedEmail,
            onEditClick = { isEditingProfile = true }
        )
    }
}

/**
 *
 */
@Composable
fun ProfileCardUI(
    username: String,
    email: String,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = ShapeDefaults.Large,
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Hello $username",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = email,
                    color = Color.Gray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = onEditClick,
                    contentPadding = PaddingValues(horizontal = 30.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 2.dp
                    ),
                    shape = ShapeDefaults.Medium
                ) {
                    Text(
                        text = "Edit",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.user_profil_icon),
                contentDescription = "",
                modifier = Modifier
                    .height(120.dp)
                    .weight(3f)
                    .align(alignment = Alignment.Bottom)
            )

        }
    }
}

/**
 *
 */
@Composable
fun ProfilePanel(
    onSave: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var editedUsername by remember { mutableStateOf("") }
    var editedEmail by remember { mutableStateOf("") }
    var isEmailVerified by remember { mutableStateOf(false) }

    fun saveChanges() {
        isEmailVerified = if (isValidEmail(editedEmail)) {
            onSave(editedUsername, editedEmail)
            true
        } else {
            false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = editedUsername,
            onValueChange = { editedUsername = it },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = editedEmail,
            onValueChange = { editedEmail = it },
            label = { Text("Email") }
        )
        if (!isEmailVerified) {
            Text(
                text = "Please enter a valid email address",
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = { saveChanges() },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Save")
            }
            TextButton(
                onClick = { onDismiss() },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Cancel")
            }
        }
    }
}

/**
 * checks if the email is real
 * @return Boolean: true means the email is real
 */
private fun isValidEmail(email: String): Boolean {
    return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/**
 * Card that contains switch that can be triggered
 * @param icon:
 * @param mainText:
 * @param switchState:
 * @param onSwitchStateChanged:
 */
@Composable
fun CardWithSwitch(
    icon: Int,
    mainText: String,
    switchState: Boolean,
    onSwitchStateChanged: (Boolean) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(80.dp),
        //.background(if (switchState) Color(0xFF4CAF50) else Color.Gray),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = ShapeDefaults.Large,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = ShapeDefaults.Medium)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier
                        .offset(y = (2).dp)
                        .width(130.dp)
                ) {
                    Text(
                        text = mainText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.width(55.dp),
                text = if (switchState) "active" else "deactivate",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (switchState) Color(0xFF4CAF50) else Color.Gray
            )
            Switch(
                checked = switchState,
                onCheckedChange = onSwitchStateChanged,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
