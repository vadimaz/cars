package com.android.cars.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DialogSingleChoice(
    title: String,
    options: List<String>,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onIndexSelected: (index: Int) -> Unit,
    onDismiss: () -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor)
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // TITLE
                Text(text = title, style = MaterialTheme.typography.subtitle1)

                // ITEMS
                var newSelectedIndex by rememberSaveable { mutableStateOf(selectedIndex) }

                Spacer(modifier = Modifier.height(24.dp))

                // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
                Column(Modifier.selectableGroup()) {
                    options.forEachIndexed { idx, text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (idx == newSelectedIndex),
                                    onClick = { newSelectedIndex = idx },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (idx == newSelectedIndex),
                                onClick = null
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }

                // BUTTONS
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(id = android.R.string.cancel))
                    }
                    TextButton(onClick = { onIndexSelected(newSelectedIndex) }) {
                        Text(text = stringResource(id = android.R.string.ok))
                    }
                }
            }
        }
    }
}