package com.example.composeexploration.ui.composables.conversation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.composeexploration.R
import com.example.composeexploration.data.conversation.MessageData

@Composable
fun Conversation(messages: List<MessageData>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message = message, modifier = modifier)
        }
    }
}

@Composable
fun MessageCard(message: MessageData, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(all = 12.dp)) {
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Author's profile picture",
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Spacer(modifier = modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
        val readMoreColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
        )

        Column {
            Text(
                text = message.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Column {
                    Text(
                        modifier = modifier.padding(all = 8.dp),
                        text = message.body,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (message.body.length > 60) {
                        Text(
                            modifier = modifier
                                .padding(all = 8.dp)
                                .clickable { isExpanded = !isExpanded },
                            text = if (isExpanded) "Show less..." else "Read more...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = readMoreColor
                        )
                    }
                }
            }
            Spacer(modifier = modifier.height(4.dp))
        }
    }
}