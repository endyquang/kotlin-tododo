package com.ndq.tododo.composables

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ndq.tododo.models.TodoModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TodoItem {}

@Composable
fun TodoListItem(
    item: TodoModel,
    onPressed: () -> Unit,
    onCompletedChanged: (Boolean) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = LocalIndication.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onPressed,
            ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(
                    modifier = Modifier.height(4.dp),
                )
                Text(
                    (item.scheduleAt ?: item.updatedAt).toLocalDateTime(
                        TimeZone.UTC
                    ).date.toString()
                )
            }
            Spacer(
                modifier = Modifier.height(12.dp),
            )
            if (!item.isCancelled)
                Checkbox(
                    modifier = Modifier.offset(x = 8.dp),
                    checked = item.isDone,
                    onCheckedChange = onCompletedChanged,
                )
        }
    }
}
