package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.messagesui.ui.theme.MessagesUITheme


data class Message(
    val id: Int,
    val sender: String,
    val preview: String,
    val time: String,
    val title: String,
    val body: String,
    var isFavorite: Boolean = false,
    var isExpanded: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessagesUITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MessageList()
                    MainScreen()
                }
            }
        }
    }
}

sealed class BottomNavItem(val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("Home", Icons.Default.Home)
    object Library : BottomNavItem("Library", Icons.Default.MenuBook)
    object Messages : BottomNavItem("Messages", Icons.Default.Email)
    object Profile : BottomNavItem("Profile", Icons.Default.Person)
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Messages) }

    Scaffold(
        topBar = { SearchBar() },
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedTab,
                onItemSelected = { selectedTab = it }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* thêm action ở đây */ }) {
                Icon(Icons.Default.Edit, contentDescription = "Compose")
            }
        }
    ) { paddingValues ->
        // Nội dung trang theo tab hiện tại
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                is BottomNavItem.Home -> Text("Home Screen", modifier = Modifier.padding(16.dp))
                is BottomNavItem.Library -> Text("Library Screen", modifier = Modifier.padding(16.dp))
                is BottomNavItem.Messages -> MessageList() // nơi hiện các message
                is BottomNavItem.Profile -> Text("Profile Screen", modifier = Modifier.padding(16.dp))
                else -> {}
            }
        }
    }
}

@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Search replies") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF1F1F1),
            focusedContainerColor = Color(0xFFF1F1F1),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 30.dp)
    )
}


@Composable
fun BottomNavBar(selectedItem: BottomNavItem, onItemSelected: (BottomNavItem) -> Unit) {
    NavigationBar {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Library,
            BottomNavItem.Messages,
            BottomNavItem.Profile
        ).forEach { item ->
            NavigationBarItem(
                selected = item == selectedItem,
                onClick = { onItemSelected(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                        if (item == selectedItem) {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun MessageList() {
    val messages = remember {
        listOf(
            Message(1, "Google", "Cucumber Mask Facial has shipped .", "20 mins ago", "Title", "I'll be in your neighborhood doing errands and was hoping to catch up for a coffee this Saturday."),
            Message(2, "Amazon", "Your order has been delivered.", "1 hour ago", "Order Update", "Your package has been delivered at your front door."),
            Message(3, "Facebook", "New login from Chrome.", "2 hours ago", "Security Alert", "We noticed a login from a new device. If this was you, you can safely disregard this email."),
            Message(4, "Netflix", "New series you might like.", "5 hours ago", "Recommendations", "Check out the new drama series based on your watch history."),
            Message(5, "Twitter", "You have 5 new followers.", "8 hours ago", "Follower Update", "Your account is getting popular! You have 5 new followers."),
            Message(6, "Spotify", "Weekly Mix is ready.", "Yesterday", "Music Time", "Your customized Weekly Mix playlist is now available. Enjoy!"),
            Message(7, "LinkedIn", "You appeared in 10 searches this week.", "2 days ago", "Profile Update", "See who's been looking at your profile and how you can improve your visibility."),
            Message(8, "YouTube", "New video from your subscriptions.", "2 days ago", "Subscription Update", "A new video has been uploaded by a channel you're subscribed to."),
            Message(9, "Instagram", "New message request.", "3 days ago", "Message Alert", "Someone you don't follow sent you a message."),
            Message(10, "Slack", "You have unread messages.", "4 days ago", "Workspace Update", "You have unread messages in your workspace. Catch up now!")
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(messages) { message ->
            MessageItem(message)
        }
    }
}


@Composable
fun MessageItem(message: Message) {
    var expanded by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Luôn có avatar
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message.sender.first().toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = message.sender,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = message.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                IconButton(onClick = { isFavorite = !isFavorite }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = null,
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (!expanded) {
                Text(
                    text = message.preview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Text(
                    text = message.body,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { }) {
                        Text("Reply")
                    }
                    Button(onClick = { }) {
                        Text("Reply All")
                    }
                }
            }
        }
    }
}




