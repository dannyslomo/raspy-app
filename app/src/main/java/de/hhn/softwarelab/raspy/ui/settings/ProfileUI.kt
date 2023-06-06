package de.hhn.softwarelab.raspy.ui.settings

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.ui.settings.SettingUI.PreferenceState.email
import de.hhn.softwarelab.raspy.ui.settings.SettingUI.PreferenceState.username
import de.hhn.softwarelab.raspy.ui.theme.Purple40
import de.hhn.softwarelab.raspy.ui.theme.PurpleGrey80

/**
 * Profile card with username and email address
 * @param: check if darkMode and changes text colors
 */
@Composable
fun Profile(darkMode: Boolean) {

    var isEmailVerified by remember { mutableStateOf(false) }
    var isEditingProfile by remember { mutableStateOf(false) }

    if (isEditingProfile) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        if (darkMode) Color.DarkGray else Color.LightGray,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                ProfilePanel(
                    onSave = { username, email ->

                        SettingUI.PreferenceState.username.value = username
                        SettingUI.PreferenceState.email.value = email
                        isEditingProfile = false
                        isEmailVerified = true

                    },
                    onDismiss = {
                        // Cancel editing
                        isEditingProfile = false
                    },
                    darkMode = darkMode
                )
            }
        }
    } else {
        ProfileCardUI(
            username = username.value,
            email = email.value,
            onEditClick = { isEditingProfile = true },
            darkMode = darkMode
        )
    }
}

/**
 * Profile Card that displays Profile Picture , Username and Email
 * User can change email which also gets validated
 * User can change name (does not affect registration)
 * @param username:
 * @param email:
 * @param onEditClick:
 * @param darkMode: check if darkMode and changes text colors
 */
@Composable
fun ProfileCardUI(
    username: String,
    email: String,
    onEditClick: () -> Unit,
    darkMode: Boolean
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
                    color = if (darkMode) Color.White else Color.Black
                )

                Text(
                    text = email,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (darkMode) Color.White else Color.Gray
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
    onDismiss: () -> Unit,
    darkMode: Boolean
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
        Text(
            text = "Edit your information",
            color = if (darkMode) Color.White else Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 10.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
        TextField(
            value = editedUsername,
            onValueChange = { editedUsername = it },
            label = { Text("Username") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = if (darkMode) Color.LightGray else Color.White,
                textColor = if (darkMode) Color.Black else Color.DarkGray
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = editedEmail,
            onValueChange = { editedEmail = it },
            label = { Text("Email") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = if (darkMode) Color.LightGray else Color.White,
                textColor = if (darkMode) Color.Black else Color.DarkGray
            )
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
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (darkMode) PurpleGrey80 else Purple40
                )
            ) {
                Text("Save")
            }
            TextButton(
                onClick = { onDismiss() },
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (darkMode) PurpleGrey80 else Purple40
                )
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
fun isValidEmail(email: String): Boolean {
    return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}