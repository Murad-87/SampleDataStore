package com.muslim.necodatastorelesson

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.necodatastorelesson.data.SettingsData
import com.muslim.necodatastorelesson.data_store.DataStoreManager
import com.muslim.necodatastorelesson.ui.theme.Blue
import com.muslim.necodatastorelesson.ui.theme.Green
import com.muslim.necodatastorelesson.ui.theme.NecoDataStoreLessonTheme
import com.muslim.necodatastorelesson.ui.theme.Red
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager = DataStoreManager(this)
        setContent {
            NecoDataStoreLessonTheme {
                val bgColorState = remember {
                    mutableStateOf(Red.value)
                }
                val textSizeState = remember {
                    mutableStateOf(40)
                }

                LaunchedEffect(key1 = true) {
                    dataStoreManager.getSettings().collect { settings ->
                        bgColorState.value = settings.bgColor.toULong()
                        textSizeState.value = settings.textSize
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(bgColorState.value)
                ) {
                    MainScreen(dataStoreManager, textSizeState)
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    dataStoreManager: DataStoreManager,
    textSizeState: MutableState<Int>
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(0.5f)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically)

        ) {
            Text(
                text = "Some text",
                color = Color.White,
                fontSize = textSizeState.value.sp
            )
        }

        Button(onClick = {
            scope.launch {
                dataStoreManager.saveSettings(
                    SettingsData(
                        15,
                        Blue.value.toLong()
                    )
                )
            }
        }) {
            Text(text = "Blue")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            scope.launch {
                dataStoreManager.saveSettings(
                    SettingsData(
                        30,
                        Red.value.toLong()
                    )
                )
            }
        }) {
            Text(text = "Red")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            scope.launch {
                dataStoreManager.saveSettings(
                    SettingsData(
                        40,
                        Green.value.toLong()
                    )
                )
            }
        }) {
            Text(text = "Green")
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
