package de.hhn.softwarelab.raspy.livestreamUI

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.ui.ImageLogsUI.ImageActivity
import de.hhn.softwarelab.raspy.ui.infoUI.InfoActivity
import de.hhn.softwarelab.raspy.ui.languageUI.LanguageActivity
import de.hhn.softwarelab.raspy.ui.livestreamUI.components.DrawerBody
import de.hhn.softwarelab.raspy.ui.livestreamUI.components.DrawerHeader
import de.hhn.softwarelab.raspy.ui.livestreamUI.components.MenuItem
import de.hhn.softwarelab.raspy.ui.loginUI.LoginActivity
import de.hhn.softwarelab.raspy.ui.settings.SettingUI
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
                        title = stringResource(R.string.gallery),
                        contentDescription = "Go to gallery screen",
                        icon = Icons.Outlined.Image
                    ),
                    MenuItem(
                        id = "settings",
                        title = stringResource(R.string.settings),
                        contentDescription = "Go to settings screen",
                        icon = Icons.Outlined.Settings
                    ),
                    MenuItem(
                        id = "info",
                        title = "Info",
                        contentDescription = "Get info",
                        icon = Icons.Outlined.HelpOutline
                    ),
                    MenuItem(
                        id = "language",
                        title = stringResource(R.string.language),
                        contentDescription = "change language",
                        icon = Icons.Outlined.Language
                    ),
                    MenuItem(
                        id = "logout",
                        title = stringResource(R.string.logout),
                        contentDescription = "Go to login screen",
                        icon = Icons.Outlined.Logout
                    )
                ),
                    onItemClick = { menuItem ->
                        when (menuItem.id) {
                            "home" -> {
                            }
                            "settings" -> {
                                val intent = Intent(context, SettingUI::class.java)
                                context.startActivity(intent)
                            }
                            "info" -> {
                                val intent = Intent(context, InfoActivity()::class.java)
                                context.startActivity(intent)
                            }
                            "language" -> {
                                val intent = Intent(context, LanguageActivity()::class.java)
                                context.startActivity(intent)
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


