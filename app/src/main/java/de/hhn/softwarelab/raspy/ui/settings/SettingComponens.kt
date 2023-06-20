package de.hhn.softwarelab.raspy.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.ui.theme.Purple40


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
    onSwitchStateChanged: (Boolean) -> Unit,
    darkMode: Boolean,
    infoNote: String
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(80.dp),
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
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier
                        .offset(y = (2).dp)
                        .width(100.dp)
                ) {
                    Text(
                        text = mainText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (darkMode) Color.White else Color.Black
                    )
                }
            }
            Switch(
                checked = switchState,
                onCheckedChange = onSwitchStateChanged,
                modifier = Modifier.padding(start = 110.dp)
            )
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info Icon"
                )
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = mainText) },
            text = { Text(text = infoNote) },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}


/**
 *
 * @param darkMode: check if DarkMode
 * @param currentVal: Current Value from Backend
 * @param onSave: save picked Number
 */
@Composable
fun NumberPicker(darkMode: Boolean, currentVal: MutableState<Int>, onSave: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(170.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = ShapeDefaults.Large,
    ) {
        var number by remember { mutableStateOf(currentVal.value) }
        var originalNumber by remember { mutableStateOf(currentVal.value) }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(18.dp)
                )
                Text(
                    text = stringResource(R.string.delete_interval),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (darkMode) Color.White else Color.Black,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { number-- },
                    enabled = number > 0,
                    modifier = Modifier.padding(end = 8.dp),
                    colors = if (darkMode) ButtonDefaults.textButtonColors(contentColor = Color.White) else ButtonDefaults.textButtonColors(
                        contentColor = Purple40
                    ),
                ) {
                    Text(text = "-", fontSize = 20.sp)
                }
                Text(
                    text = number.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (darkMode) Color.White else Color.Black
                )
                TextButton(
                    onClick = { number++ },
                    modifier = Modifier.padding(start = 8.dp),
                    colors = if (darkMode) ButtonDefaults.textButtonColors(contentColor = Color.White) else ButtonDefaults.textButtonColors(
                        contentColor = Purple40
                    ),
                ) {
                    Text(text = "+", fontSize = 20.sp)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        onSave(number)
                        originalNumber = number
                    },
                    modifier = Modifier.padding(top = 16.dp, end = 8.dp)
                ) {
                    Text(text = stringResource(R.string.save_button))
                }
                TextButton(
                    onClick = {
                        number = originalNumber
                    },
                    modifier = Modifier.padding(top = 16.dp, start = 8.dp)
                ) {
                    Text(text = stringResource(R.string.cancle_button))
                }
            }
        }
    }
}
