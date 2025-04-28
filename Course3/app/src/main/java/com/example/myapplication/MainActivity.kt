package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

// ViewModel để quản lý trạng thái tab
class TabViewModel : ViewModel() {
    var selectedTabIndex by mutableStateOf(0)
        private set

    fun selectTab(index: Int) {
        selectedTabIndex = index
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(viewModel: TabViewModel = viewModel()) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(LocalConfiguration.current.screenWidthDp.dp * 0.7f)
                        .clickable { scope.launch { drawerState.close() } }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Arcane", style = MaterialTheme.typography.headlineLarge, color = Color.Magenta)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_launcher_foreground),
                                contentDescription = "Arcane",
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                            )
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        TabRow(
                            selectedTabIndex = viewModel.selectedTabIndex,
                            containerColor = Color(0xFF800080),
                            contentColor = Color.White,
                            indicator = { tabPositions ->
                                // Tùy chỉnh indicator (thanh dưới tab)
                                TabRowDefaults.Indicator(
                                    modifier = Modifier.tabIndicatorOffset(tabPositions[viewModel.selectedTabIndex]),
                                    color = Color.White,
                                    height = 3.dp // Tăng độ dày của thanh trắng dưới tab
                                )
                            }
                        ) {
                            listOf("FLY", "SLEEP", "EAT").forEachIndexed { index, title ->
                                Tab(
                                    selected = viewModel.selectedTabIndex == index,
                                    onClick = { viewModel.selectTab(index) },
                                    modifier = if (viewModel.selectedTabIndex == index) {
                                        Modifier
                                            .border(2.dp, Color.White, shape = MaterialTheme.shapes.small) // Thêm viền trắng cho tab được chọn
                                            .padding(4.dp)
                                    } else {
                                        Modifier.padding(4.dp)
                                    },
                                    text = {
                                        Text(
                                            text = title,
                                            style = if (viewModel.selectedTabIndex == index) {
                                                MaterialTheme.typography.titleLarge // Kích thước chữ lớn hơn cho tab được chọn
                                            } else {
                                                MaterialTheme.typography.titleMedium // Kích thước chữ nhỏ hơn cho tab không được chọn
                                            }
                                        )
                                    },
                                    selectedContentColor = Color.White,
                                    unselectedContentColor = Color.Gray
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF800080),
                        titleContentColor = Color.White
                    )
                )
            },
            content = { innerPadding ->
                AnimatedContent(
                    targetState = viewModel.selectedTabIndex,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) { tabIndex ->
                    when (tabIndex) {
                        0 -> FlyContent()
                        1 -> SleepContent()
                        2 -> EatContent()
                    }
                }
            }
        )
    }
}

// Nội dung cho tab FLY
@Composable
fun FlyContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(15) { index ->
            Text(
                text = "Fly Item #$index",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFF800080), shape = MaterialTheme.shapes.small)
                    .padding(16.dp),
                color = Color.White
            )
        }
    }
}

// Nội dung cho tab SLEEP
@Composable
fun SleepContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(15) { index ->
            Text(
                text = "Sleep Item #$index",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFF800080), shape = MaterialTheme.shapes.small)
                    .padding(16.dp),
                color = Color.White
            )
        }
    }
}

// Nội dung cho tab EAT
@Composable
fun EatContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(15) { index ->
            Text(
                text = "Eat Item #$index",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFF800080), shape = MaterialTheme.shapes.small)
                    .padding(16.dp),
                color = Color.White
            )
        }
    }
}