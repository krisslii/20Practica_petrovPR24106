package com.example.pracitca20_pavlenko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseDatabase.getInstance().getReference("User")
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreen(db)
                }
            }
        }
    }
}

@Composable
fun AppScreen(db: DatabaseReference) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var displayText by remember { mutableStateOf("Данные появятся здесь") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text("Фамилия") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Телефон") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Возраст") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Город") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    val user = User(name, surname, email, phone, age, city)
                    db.push().setValue(user)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Сохранить")
            }

            Button(
                onClick = {
                    db.limitToLast(1).get().addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            for (child in snapshot.children) {
                                val u = child.getValue(User::class.java)
                                displayText = "Последний: ${u?.name} ${u?.surname}"
                            }
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Читать")
            }
        }

        Text(
            text = displayText,
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}