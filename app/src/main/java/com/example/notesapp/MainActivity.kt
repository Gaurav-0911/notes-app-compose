package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.data.entity.NoteEntity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: NotesViewModel = viewModel()
            NotesScreen(viewModel)
        }
    }
}

/* ---------------- SCREEN ---------------- */

@Composable
fun NotesScreen(viewModel: NotesViewModel) {

    val notes by viewModel.notes.collectAsState()

    NotesScreenUI(
        notes = notes,
        onAdd = viewModel::addNote,
        onDelete = viewModel::deleteNote,
        onUpdate = viewModel::updateNote
    )
}

/* ---------------- UI ---------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreenUI(
    notes: List<NoteEntity>,
    onAdd: (String, String) -> Unit,
    onDelete: (NoteEntity) -> Unit,
    onUpdate: (NoteEntity) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var editNote by remember { mutableStateOf<NoteEntity?>(null) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
            .systemBarsPadding(),

        topBar = {
            TopAppBar(
                title = { Text("My Notes", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color.Black
            ) {
                Text("+", color = Color.White)
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
        ) {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Text(note.description, color = Color.DarkGray)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {

                            IconButton(onClick = {
                                editNote = note
                            }) {
                                Icon(Icons.Default.Edit, null)
                            }

                            IconButton(onClick = {
                                onDelete(note)
                            }) {
                                Icon(Icons.Default.Delete, null)
                            }
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            NoteDialog(
                titleText = "",
                descText = "",
                dialogTitle = "Add Note",
                onDismiss = { showAddDialog = false },
                onSave = { t, d ->
                    onAdd(t, d)
                    showAddDialog = false
                }
            )
        }

        editNote?.let { note ->
            NoteDialog(
                titleText = note.title,
                descText = note.description,
                dialogTitle = "Update Note",
                onDismiss = { editNote = null },
                onSave = { t, d ->
                    onUpdate(note.copy(title = t, description = d))
                    editNote = null
                }
            )
        }
    }
}

/* ---------------- DIALOG ---------------- */

@Composable
fun NoteDialog(
    titleText: String,
    descText: String,
    dialogTitle: String,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var title by remember { mutableStateOf(titleText) }
    var desc by remember { mutableStateOf(descText) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        text = {
            Card {
                Column(Modifier.padding(16.dp)) {

                    Text(dialogTitle, style = MaterialTheme.typography.titleLarge)

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(title, { title = it }, label = { Text("Title") })
                    OutlinedTextField(desc, { desc = it }, label = { Text("Description") })

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (title.isNotBlank()) onSave(title, desc)
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    )
}
