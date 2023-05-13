package de.hhn.softwarelab.raspy.ui.settings

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.backend.Services.SettingsService
import de.hhn.softwarelab.raspy.backend.dataclasses.Settings
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
    darkMode: Boolean
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
                        .width(120.dp)
                ) {
                    Text(
                        text = mainText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (darkMode) Color.White else Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.width(70.dp),
                text = if (switchState) "active" else "deactivate",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (switchState) Color(0xFF4CAF50) else Color.Gray,
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
fun NumberPicker(darkMode: Boolean, currentVal: Int, onSave: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(170.dp),
        //.background(if (switchState) Color(0xFF4CAF50) else Color.Gray),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = ShapeDefaults.Large,
    ) {
        var number by remember {
            if (currentVal == 0) mutableStateOf(30) else mutableStateOf(currentVal)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.user_profil_icon),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(start = 15.dp)
                    .size(18.dp)
            )
            Text(
                text = "Delete Interval",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (darkMode) Color.White else Color.Black,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(start = 10.dp)
            )
        }
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = number.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = if (darkMode) Color.White else Color.Black
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.width(70.dp))
                TextButton(
                    onClick = { number-- },
                    enabled = number > 0,
                    modifier = Modifier.padding(8.dp),
                    colors = if (darkMode) ButtonDefaults.textButtonColors(contentColor = Color.White) else ButtonDefaults.textButtonColors(
                        contentColor = Purple40
                    ),
                ) {
                    Text(text = "-", fontSize = 20.sp)
                }
                TextButton(
                    onClick = { number++ },
                    modifier = Modifier.padding(8.dp),
                    colors = if (darkMode) ButtonDefaults.textButtonColors(contentColor = Color.White) else ButtonDefaults.textButtonColors(
                        contentColor = Purple40
                    ),
                ) {
                    Text(text = "+", fontSize = 20.sp)
                }
                TextButton(
                    onClick = { onSave(number) }
                ) {
                    Text(text = "save")
                }
            }
        }
    }
}
