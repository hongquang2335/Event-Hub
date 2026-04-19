package com.example.eventticket.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.eventticket.ui.components.FlashSaleBanner
import com.example.eventticket.ui.theme.VibeGreenDeep

@Composable
fun WelcomeScreen(
    onLogin: () -> Unit,
    onGuest: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically)
    ) {
        Text("FanZone", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold, color = VibeGreenDeep)
        Text(
            "Đặt vé sự kiện, theo dõi cộng đồng fan và quản lý resale trong một ứng dụng.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        FlashSaleBanner()
        Button(onClick = onLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Đăng nhập / Đăng ký")
        }
        OutlinedButton(onClick = onGuest, modifier = Modifier.fillMaxWidth()) {
            Text("Vào app nhanh")
        }
    }
}

@Composable
fun LoginRegisterScreen(
    onLogin: (String, String) -> Unit,
    onRegister: (String, String, String) -> Unit
) {
    var isRegister by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    text = if (isRegister) "Tạo tài khoản" else "Chào mừng trở lại",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                if (isRegister) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Tên hiển thị") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Mật khẩu") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                Button(
                    onClick = {
                        if (isRegister) onRegister(name, email, password) else onLogin(email, password)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isRegister) "Đăng ký" else "Đăng nhập")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(if (isRegister) "Đã có tài khoản?" else "Chưa có tài khoản?")
                    OutlinedButton(onClick = { isRegister = !isRegister }) {
                        Text(if (isRegister) "Đăng nhập" else "Đăng ký")
                    }
                }
            }
        }
    }
}
