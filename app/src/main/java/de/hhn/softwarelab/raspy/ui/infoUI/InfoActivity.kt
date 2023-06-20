package de.hhn.softwarelab.raspy.ui.infoUI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.ui.settings.SettingUI
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme

/**
 * The InfoActivity class displays information about the surveillance app and its privacy policy.
 */
class InfoActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RaspSPYTheme(darkTheme = SettingUI.currentDarkModeState.value) {
                val scrollState = rememberScrollState()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = getString(R.string.info_title)) },
                            navigationIcon = {
                                IconButton(onClick = {
                                    onBackPressed()
                                }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                                }
                            },
                            backgroundColor = if (SettingUI.currentDarkModeState.value) Color.Gray else Color.White
                        )
                    },
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier
                                .padding(paddingValues)
                                .verticalScroll(scrollState)
                                .background(
                                    if (SettingUI.currentDarkModeState.value) Color.Gray else Color(
                                        0xFFd2bfd6
                                    )
                                )
                        ) {
                            AboutUsHeader()
                            AboutUsText()
                            PolicyHeaderText()
                            PolicyText()
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun AboutUsHeader() {
        val text = stringResource(R.string.about_us_header)
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
    }

    @Composable
    fun AboutUsText() {
        val text = stringResource(R.string.about_us_text)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }

    @Composable
    fun PolicyHeaderText() {
        val text = stringResource(R.string.privacy_policy_title)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
    }


    @Composable
    fun PolicyText() {
        val text = buildAnnotatedString {
            append(stringResource(id = R.string.Info_text_1))
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                append(stringResource(id = R.string.Info_text_2))
            }
            append(stringResource(id = R.string.Info_text_3))
            append(stringResource(id = R.string.Info_text_4))
            append(stringResource(id = R.string.Info_text_5))
            append(stringResource(id = R.string.Info_text_6))
            append(stringResource(id = R.string.Info_text_7))
            append(stringResource(id = R.string.Info_text_8))
            append(stringResource(id = R.string.Info_text_9))
            append(stringResource(id = R.string.Info_text_10))
            append(stringResource(id = R.string.Info_text_11))
            append(stringResource(id = R.string.Info_text_12))
            append(stringResource(id = R.string.Info_text_13))
            append(stringResource(id = R.string.Info_text_14))

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                append(stringResource(id = R.string.Info_text_15))
            }
            append(stringResource(id = R.string.Info_text_16))
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(id = R.string.Info_text_17))
            }
            append(stringResource(id = R.string.Info_text_18))
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(id = R.string.Info_text_19))
            }
            append(stringResource(id = R.string.Info_text_20))
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(id = R.string.Info_text_21))
            }
            append(stringResource(id = R.string.Info_text_22))

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                append(stringResource(id = R.string.Info_text_23))
            }
            append(stringResource(id = R.string.Info_text_24))
            append(stringResource(id = R.string.Info_text_25))
            append(stringResource(id = R.string.Info_text_26))
            append(stringResource(id = R.string.Info_text_27))

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                append(stringResource(id = R.string.Info_text_28))
            }
            append(stringResource(id = R.string.Info_text_29))

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                append(stringResource(id = R.string.Info_text_30))
            }
            append(stringResource(id = R.string.Info_text_31))

            append(stringResource(id = R.string.Info_text_32))
            append(stringResource(id = R.string.Info_text_33))

            append(stringResource(id = R.string.Info_text_34))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Justify
            )
        }
    }


    @Preview
    @Composable
    fun PreviewInformationActivity() {
        InfoActivity()
    }
}