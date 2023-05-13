package de.hhn.softwarelab.raspy.livestreamUI

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import de.hhn.softwarelab.raspy.ImageLogsUI.ImageActivity
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.livestreamUI.components.DrawerBody
import de.hhn.softwarelab.raspy.livestreamUI.components.DrawerHeader
import de.hhn.softwarelab.raspy.livestreamUI.components.MenuItem
import de.hhn.softwarelab.raspy.loginUI.LoginActivity
import de.hhn.softwarelab.raspy.ui.settings.SettingList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
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
                        icon = Icons.Outlined.Home
                    ),
                    MenuItem(
                        id = "gallery",
                        title = "Gallery",
                        contentDescription = "Go to gallery screen",
                        icon = Icons.Outlined.Image
                    ),
                    MenuItem(
                        id = "settings",
                        title = "Settings",
                        contentDescription = "Go to settings screen",
                        icon = Icons.Outlined.Settings
                    ),
                    MenuItem(
                        id = "help",
                        title = "Help",
                        contentDescription = "Get help",
                        icon = Icons.Outlined.HelpOutline
                    ),
                    MenuItem(
                        id = "logout",
                        title = "Logout",
                        contentDescription = "Go to login screen",
                        icon = Icons.Outlined.Logout
                    )
                ),
                    onItemClick = { menuItem ->
                        when (menuItem.id) {
                            "home" -> {

                            }
                            "settings" -> {
                                val intent = Intent(context, SettingList::class.java)
                                context.startActivity(intent)
                            }
                            "help" -> {
                                // navigate to help screen
                            }
                            "logout" -> {
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                            }
                            "gallery" -> {
                                val intent = Intent(context, ImageActivity::class.java)
                                context.startActivity(intent)
                            }

                        }
                        scope.launch { drawerState.close() }
                    }
                )
            }
        },
        content = {
            InitButton()
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
