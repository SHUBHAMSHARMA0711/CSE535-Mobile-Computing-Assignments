package com.example.assignment3question1

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3question1.ui.theme.Assignment3Question1Theme

class MainActivity : ComponentActivity(), SensorEventListener {

    private var lastInsertTime: Long = 0
    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
        )
        setContent {
            Assignment3Question1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
        sensorManager = getSystemService(SensorManager::class.java)
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(
            this, sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 1000000
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val sensorData = it.values
                val currentTime = System.currentTimeMillis()

                setContent {
                    Main(sensorData, this)
                }

                if (currentTime - lastInsertTime >= 1000) {
                    lastInsertTime = currentTime

                    val orientationViewModel = OrientationViewModel(application)
                    orientationViewModel.insert(
                        OrientationData(
                            time = currentTime.toString(),
                            pitch = sensorData[1],
                            roll = sensorData[0],
                            yaw = sensorData[2]
                        )
                    )
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

@Composable
fun Main(
    sensorData: FloatArray? = null,
    context: MainActivity? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 70.dp))
        Text(
            text = "Accelerometer Sensor Data",
            modifier = Modifier.padding(5.dp),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 25.sp
        )
        if (sensorData != null) {
            Text(
                text = "Pitch: ${sensorData[1]}",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = "Roll: ${sensorData[0]}",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = "Yaw: ${sensorData[2]}",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp
            )

            ElevatedButton(
                onClick = {
                    context?.startActivity(Intent(context, GraphActivity::class.java))
                },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = Color(0xFFFFFFFF)
                ),
                border = BorderStroke(2.dp, Color.White),
                elevation = ButtonDefaults.buttonElevation(100.dp)
            ) {
                Text(text = "Generate Graphs", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment3Question1Theme {
        Main()
    }
}
