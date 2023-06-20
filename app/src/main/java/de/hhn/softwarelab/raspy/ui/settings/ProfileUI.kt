package de.hhn.softwarelab.raspy.ui.settings

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.ui.theme.Purple40
import de.hhn.softwarelab.raspy.ui.theme.PurpleGrey80

/**
 * Profile card with username
 * @param: check if darkMode and changes text colors
 */
@Composable
fun Profile(darkMode: Boolean) {

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
                    onSave = { username ->
                        //TODO
                        //SettingUI.PreferenceState.username.value = username
                        isEditingProfile = false

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
            //TODO
            username = "username.value",
            onEditClick = { isEditingProfile = true },
            darkMode = darkMode
        )
    }
}

/**
 * Profile Card that displays Profile Picture and Username
 * User can change name (does not affect registration)
 * @param username:
 * @param onEditClick:
 * @param darkMode: check if darkMode and changes text colors
 */
@Composable
fun ProfileCardUI(
    username: String,
    onEditClick: () -> Unit,
    darkMode: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
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
                        text = stringResource(R.string.edit),
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
    onSave: (String) -> Unit,
    onDismiss: () -> Unit,
    darkMode: Boolean
) {

    var editedUsername by remember { mutableStateOf("") }

    fun saveChanges() {
        onSave(editedUsername)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.edit_username),
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
            label = { Text(stringResource(id = R.string.username)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = if (darkMode) Color.LightGray else Color.White,
                textColor = if (darkMode) Color.Black else Color.DarkGray
            )
        )

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
                Text(stringResource(id = R.string.save_button))
            }
            TextButton(
                onClick = { onDismiss() },
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (darkMode) PurpleGrey80 else Purple40
                )
            ) {
                Text(stringResource(id = R.string.cancel_button))
            }
        }
    }
}

