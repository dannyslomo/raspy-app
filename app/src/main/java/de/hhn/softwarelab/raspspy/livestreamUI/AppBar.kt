package de.hhn.softwarelab.raspspy.livestreamUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.hhn.softwarelab.raspspy.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(items = listOf(
                    MenuItem(
                        id = "home",
                        title = "Home",
                        contentDescription = "Go to home screen",
                        icon = Icons.Default.Home
                    ),
                    MenuItem(
                        id = "settings",
                        title = "Settings",
                        contentDescription = "Go to settings screen",
                        icon = Icons.Default.Settings
                    ),
                    MenuItem(
                        id = "help",
                        title = "Help",
                        contentDescription = "Get help",
                        icon = Icons.Default.Info
                    ),
                ),
                    onItemClick = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        },
        content = {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier.padding(top = 100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MediaScreen()
                }
                Row(modifier = Modifier.padding(top = 60.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ElevatedButtons(text = "Picture saved!", imageVector = Icons.Default.CameraAlt, "Save picture")
                    ElevatedButtons(text = "Voice activated!", imageVector = Icons.Default.KeyboardVoice, "Activate voice")
                }
            }
        }
    )

        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            navigationIcon = {
                IconButton(onClick = {
                    if (drawerState.isClosed) {
                        scope.launch { drawerState.open() }

                    } else {
                        scope.launch { drawerState.close() }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Toggle drawer"
                    )
                }
            }
        )
    }
