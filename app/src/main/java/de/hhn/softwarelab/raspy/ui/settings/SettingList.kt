package de.hhn.softwarelab.raspy.ui.settings

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme

class SettingList : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaspSPYTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
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

@Composable
fun SettingsScreen(context: Context) {
    var isSwitchEnabled1 by remember { mutableStateOf(false) }
    var isSwitchEnabled2 by remember { mutableStateOf(false) }
    Column {
        //Titel
        HeaderText()
        //Profil
        ProfileCardUI("Lui", "UI.Stack.YT@gmail.com")
        //IP
        var title by remember { mutableStateOf("Card Title") }
        EditableCards(title = "Hallo", onSaveClicked = { newTitle ->
            title = newTitle
        }) {}
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
        CardWithNumberPicker(context = context)
    }
}
//kamera aktive
//lösch Intervall
//darkmode
//sprache(?)
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
 * User Profil
 */
@Composable
fun ProfileCardUI(username: String, email: String) {
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
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 30.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 2.dp
                    ),
                    shape = ShapeDefaults.Medium
                ) {
                    Text(
                        text = "View",
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
 *  Card that expand if click on and show Textfield to edit Text
 *  funtion: edit IP-Address
 *  @param title: name
 *  @param onSaveClicked: save changed text in TextfField
 *  @param onCancelClicked: reset to text before if clicked on "cancle"
 */

private fun formatIp(input: String): String {
    // Remove all non-digit characters
    val digitsOnly = input.replace(Regex("\\D+"), "")

    // Pad the string with zeros if necessary
    val padded = "000000000".substring(0, 9 - digitsOnly.length) + digitsOnly

    // Insert dots between the groups of three digits
    return "${padded.substring(0, 3)}.${padded.substring(3, 6)}.${padded.substring(6)}"
}

@Composable
fun EditableCards(
    title: String,
    onSaveClicked: (String) -> Unit,
    onCancelClicked: () -> Unit
) {
    var editing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(title) }
    var lastValidText by remember { mutableStateOf(text) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(if (editing) 200.dp else 80.dp)
            .clickable { editing = !editing },
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(modifier = Modifier.padding(30.dp)) {
            Text(
                text = "IP :   $text",
                //style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
                fontSize = 17.sp
            )
            if (editing) {
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = text,
                    onValueChange = { newText ->
                        if (newText.length <= 11) {
                            lastValidText = newText
                            text = formatIpAddress(newText)
                        } else {
                            Log.d("INPUT ERROR", "DELETE ONE AND TELL IT IS ERROR")
                            text = lastValidText
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onSaveClicked(text)
                            editing = false
                        }
                    ),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            onCancelClicked()
                            editing = false
                            text = title
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                    TextButton(
                        onClick = {
                            onSaveClicked(text)
                            editing = false
                        }
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

private fun formatIpAddress(text: String): String {
    val digits = text.filter { it.isDigit() }
    return buildAnnotatedString {
        for (i in 0 until digits.length) {
            append(digits[i])
            if (i % 3 == 2 && i != digits.length - 1) {
                append(".")
            }
        }
    }.toString()
}


/**
 * Card that contains switch that can be triggered
 * @param icon:
 * @param mainText:
 */
@Composable
fun CardWithSwitch(
    icon: Int, mainText: String, switchState: Boolean,
    onSwitchStateChanged: (Boolean) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(80.dp)
            .background(if (switchState) Color(0xFF4CAF50) else Color.Gray),
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
                        .background(Color.Red)
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
                        .width(170.dp)
                ) {
                    Text(
                        text = mainText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                modifier = Modifier.width(55.dp),
                text = if (switchState) "active" else "deactive",
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

@Composable
fun CardWithNumberPicker(
    context: Context

) {
    var userValue = 1

    NumberPicker(
        context
    ).apply {
        minValue = 0
        maxValue = 20000
        value = userValue
        setOnValueChangedListener { numberPicker, oldValue, newValue ->
            userValue = newValue
        }
    }
}

/**
 * function that
 */
fun onCardClicked(switchState: Boolean, functionNumber: Int) {
    if (switchState) {
        Log.d("CardWithSwitchExample", "Switch is true")
        when (functionNumber) {
            1 -> print("x == 1")
            2 -> print("x == 2")
            3 -> print("x == 3")
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }
    } else {

    }
}
