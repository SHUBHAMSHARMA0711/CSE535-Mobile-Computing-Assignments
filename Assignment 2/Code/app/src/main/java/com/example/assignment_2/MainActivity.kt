package com.example.assignment_2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.assignment_2.ui.theme.Assignment_2Theme
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MainActivity : ComponentActivity() {
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
            Assignment_2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Main() {
    val context = LocalContext.current
    val date = rememberDatePickerState()
    val curTemp = remember { mutableStateOf("") }
    val minTemp = remember { mutableStateOf("") }
    val maxTemp = remember { mutableStateOf("") }
    val connectivityChecker = ConnectivityChecker(context)
    val dateError = remember { mutableStateOf(false) }
    val dataError = remember { mutableStateOf(false) }
    var showDate by remember { mutableStateOf(false) }
    var formattedDate by remember { mutableStateOf("") }
    var showCalender by remember { mutableStateOf(false) }
    val displayWeather = remember { mutableStateOf(false) }
    val currentYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())
    var weatherData by remember { mutableStateOf(WeatherData("", "", "", "")) }
    val weatherViewModel =
        ViewModelProvider(context as ComponentActivity)[WeatherViewModel::class.java]
    val networkStatus by connectivityChecker.observeStatus()
        .collectAsState(initial = ConnectivityChecker.Status.Lost)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.stary_sky),
            contentDescription = "Night Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
        ) {
            Spacer(modifier = Modifier.padding(35.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Delhi, India",
                        modifier = Modifier.padding(
                            start = 10.dp, end = 0.dp, top = 0.dp, bottom = 5.dp
                        ),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center
                    )

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Current Location",
                        modifier = Modifier
                            .width(50.dp)
                            .height(20.dp),
                        tint = Color(0xFFFFFFFF)
                    )
                }

                Icon(
                    imageVector = if (networkStatus == ConnectivityChecker.Status.Available) ImageVector.vectorResource(
                        id = R.drawable.baseline_wifi_24
                    ) else ImageVector.vectorResource(id = R.drawable.baseline_wifi_off_24),
                    contentDescription = "Network Status",
                    modifier = Modifier
                        .width(50.dp)
                        .height(30.dp)
                        .padding(end = 15.dp),
                    tint = Color(0xFFFFFFFF)
                )
            }

            if (displayWeather.value) {
                FlowRow(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.Center,
                    maxItemsInEachRow = 2
                ) {
                    Column(
                        Modifier.width(180.dp)
                    ) {
                        Text(
                            text = "Min Temp",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF),
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "%.2f".format(
                                (minTemp.value.toFloatOrNull()?.minus(32))?.times(
                                    0.555555
                                )
                            ) + "°",
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF),
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                    Column(
                        Modifier.width(180.dp)
                    ) {
                        Text(
                            text = "Max Temp",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF),
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "%.2f".format(
                                (maxTemp.value.toFloatOrNull()?.minus(32))?.times(
                                    0.555555
                                )
                            ) + "°",
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF),
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 0.dp)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                if (showDate) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (formattedDate != "") {
                                SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(
                                    SimpleDateFormat(
                                        "yyyy-MM-dd", Locale.getDefault()
                                    ).parse(formattedDate) ?: Date()
                                ).toString()
                            } else {
                                ""
                            },
                            modifier = Modifier.padding(
                                start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp
                            ),
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                ElevatedButton(
                    onClick = {
                        showCalender = !showCalender
                        displayWeather.value = false
                        dateError.value = false
                        dataError.value = false
                        showDate = false
                        formattedDate = ""
                    },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent, contentColor = Color(0xFFFFFFFF)
                    ),
                    border = BorderStroke(2.dp, Color.White),
                    elevation = ButtonDefaults.buttonElevation(100.dp),

                    ) {
                    Text(
                        text = "Select Date",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFFFFFF)
                    )
                }

                if (showCalender) {
                    DatePickerDialog(onDismissRequest = { showCalender = false; showDate = false },
                        confirmButton = {
                            Button(onClick = {
                                showCalender = false
                                showDate = true
                                dateError.value = false
                                if (date.selectedDateMillis != null) {
                                    val selectedDate = Calendar.getInstance()
                                        .apply { timeInMillis = date.selectedDateMillis!! }
                                    formattedDate =
                                        SimpleDateFormat("yyyy-MM-dd").format(selectedDate.time)
                                } else {
                                    formattedDate = ""
                                    dateError.value = true
                                }

                            }) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showCalender = false; showDate = false }) {
                                Text("Cancel")
                            }
                        },
                        content = { DatePicker(state = date) })
                }

                ElevatedButton(
                    onClick = {
                        if (networkStatus == ConnectivityChecker.Status.Available) {
                            val newDate: String
                            if (formattedDate != "") {
                                if (!dateChecker(formattedDate)) {
                                    newDate =
                                        currentYear + "-" + formattedDate.split("-")[1] + "-" + formattedDate.split(
                                            "-"
                                        )[2]

                                    CoroutineScope(Dispatchers.Main).launch {
                                        if (weatherViewModel.getWeatherByDate(newDate) != null) {
                                            calculateAvgTemperature(
                                                date = newDate,
                                                context = context,
                                                curTemp = curTemp,
                                                minTemp = minTemp,
                                                maxTemp = maxTemp,
                                                displayWeather = displayWeather,
                                                dateError = dateError,
                                                dataError = dataError
                                            )
                                            Log.d("DATA", "Data Found in DB for Future")
                                        } else {
                                            Log.d("DATA", "Data Not Found in DB for Future")
                                            val avgCurTemp = mutableDoubleStateOf(0.0)
                                            val avgMinTemp = mutableDoubleStateOf(0.0)
                                            val avgMaxTemp = mutableDoubleStateOf(0.0)
                                            weatherAPICaller(
                                                date = newDate,
                                                context = context,
                                                curTemp = curTemp,
                                                minTemp = minTemp,
                                                maxTemp = maxTemp,
                                                futureFlag = true,
                                                avgCurTemp = avgCurTemp,
                                                avgMinTemp = avgMinTemp,
                                                avgMaxTemp = avgMaxTemp
                                            )
                                        }
                                    }
                                } else {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        if (weatherViewModel.getWeatherByDate(formattedDate) != null) {
                                            weatherData =
                                                weatherViewModel.getWeatherByDate(formattedDate)!!
                                            curTemp.value = weatherData.curTemp
                                            minTemp.value = weatherData.minTemp
                                            maxTemp.value = weatherData.maxTemp
                                            Log.d("DATA", "Data Found in DB")
                                        } else {
                                            Log.d("DATA", "Data Not Found in DB")
                                            weatherAPICaller(
                                                date = formattedDate,
                                                context = context,
                                                curTemp = curTemp,
                                                minTemp = minTemp,
                                                maxTemp = maxTemp
                                            )
                                        }
                                    }
                                    displayWeather.value = true
                                    dateError.value = false
                                }

                            } else {
                                displayWeather.value = false
                                dateError.value = true
                            }

                        } else {
                            if (formattedDate == "") {
                                displayWeather.value = false
                                dateError.value = true
                            } else if (!dateChecker(formattedDate)) {
                                val newDate =
                                    currentYear + "-" + formattedDate.split("-")[1] + "-" + formattedDate.split(
                                        "-"
                                    )[2]
                                calculateAvgTemperature(
                                    date = newDate,
                                    context = context,
                                    curTemp = curTemp,
                                    minTemp = minTemp,
                                    maxTemp = maxTemp,
                                    displayWeather = displayWeather,
                                    dateError = dateError,
                                    dataError = dataError
                                )
                            } else {
                                CoroutineScope(Dispatchers.Main).launch {
                                    if (weatherViewModel.getWeatherByDate(formattedDate) != null) {
                                        weatherData =
                                            weatherViewModel.getWeatherByDate(formattedDate)!!
                                        curTemp.value = weatherData.curTemp
                                        minTemp.value = weatherData.minTemp
                                        maxTemp.value = weatherData.maxTemp
                                        displayWeather.value = true
                                        dateError.value = false
                                    } else {
                                        displayWeather.value = false
                                        dataError.value = true
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent, contentColor = Color(0xFFFFFFFF)
                    ),
                    border = BorderStroke(2.dp, Color.White),
                    elevation = ButtonDefaults.buttonElevation(100.dp)
                ) {
                    Text(
                        text = "Get Weather",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFFFFFF)
                    )
                }

                if (displayWeather.value) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Unit: °C",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                        )
                    }
                }

                if (dateError.value) {
                    Toast.makeText(context, "Please Select a Date First", Toast.LENGTH_SHORT).show()
                    dateError.value = false
                }

                if (dataError.value) {
                    Toast.makeText(
                        context, "No Data Available for Selected Date", Toast.LENGTH_SHORT
                    ).show()
                    dataError.value = false
                }
            }
        }
    }
}

private fun dateChecker(date: String): Boolean {
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    return date <= currentDate
}

private fun calculateAvgTemperature(
    date: String,
    context: Context,
    curTemp: MutableState<String>,
    minTemp: MutableState<String>,
    maxTemp: MutableState<String>,
    displayWeather: MutableState<Boolean>,
    dateError: MutableState<Boolean>,
    dataError: MutableState<Boolean>
) {
    val weatherViewModel =
        ViewModelProvider(context as ComponentActivity)[WeatherViewModel::class.java]
    Log.d("DATE", "Getting Avg. Weather for Date: $date")
    CoroutineScope(Dispatchers.Main).launch {
        if (weatherViewModel.getWeatherByDate(date) != null) {
            var avgCurTemp = 0.0
            var avgMinTemp = 0.0
            var avgMaxTemp = 0.0
            for (i in 0..10) {
                val date = date.split("-").let {
                    (it[0].toInt() - i).toString() + "-" + it[1] + "-" + it[2]
                }
                if (weatherViewModel.getWeatherByDate(date) != null) {
                    val weatherData = weatherViewModel.getWeatherByDate(date)!!
                    avgCurTemp += weatherData.curTemp.toDouble()
                    avgMinTemp += weatherData.minTemp.toDouble()
                    avgMaxTemp += weatherData.maxTemp.toDouble()
                }
            }
            curTemp.value = (avgCurTemp / 11).toString()
            minTemp.value = (avgMinTemp / 11).toString()
            maxTemp.value = (avgMaxTemp / 11).toString()

            displayWeather.value = true
            dateError.value = false
        } else {
            displayWeather.value = false
            dataError.value = true
        }
    }
}

fun weatherAPICaller(
    date: String,
    context: Context,
    curTemp: MutableState<String>,
    minTemp: MutableState<String>,
    maxTemp: MutableState<String>,
    futureFlag: Boolean = false,
    avgCurTemp: MutableState<Double> = mutableDoubleStateOf(0.0),
    avgMinTemp: MutableState<Double> = mutableDoubleStateOf(0.0),
    avgMaxTemp: MutableState<Double> = mutableDoubleStateOf(0.0)
) {
    for (i in 0..10) {
        val date = date.split("-").let {
            (it[0].toInt() - i).toString() + "-" + it[1] + "-" + it[2]
        }
        Log.d("DATE", "Getting Weather for Date: $date")
        val retrofit = Retrofit.Builder().baseUrl("https://weather.visualcrossing.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val weatherInterface: WeatherInterface = retrofit.create(WeatherInterface::class.java)

        weatherInterface.getWeather(date).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseString = response.body()?.string()
                    if (responseString != null) {
                        val jsonObject = Gson().fromJson(responseString, JsonObject::class.java)
                        val daysArray = jsonObject.getAsJsonArray("days")
                        if (daysArray.size() > 0) {
                            val dayObject = daysArray[0].asJsonObject
                            val curTempValue = dayObject.getAsJsonPrimitive("temp").asString
                            val minTempValue = dayObject.getAsJsonPrimitive("tempmin").asString
                            val maxTempValue = dayObject.getAsJsonPrimitive("tempmax").asString

                            if (i == 0 && !futureFlag) {
                                curTemp.value = curTempValue
                                minTemp.value = minTempValue
                                maxTemp.value = maxTempValue
                            } else {
                                avgCurTemp.value += curTempValue.toDouble()
                                avgMinTemp.value += minTempValue.toDouble()
                                avgMaxTemp.value += maxTempValue.toDouble()
                            }

                            if (i == 10 && futureFlag) {
                                curTemp.value = (avgCurTemp.value / 11).toString()
                                minTemp.value = (avgMinTemp.value / 11).toString()
                                maxTemp.value = (avgMaxTemp.value / 11).toString()
                            }

                            Log.d(
                                "API RESPONSE",
                                "Date: $date, Current Temp: $curTempValue °F, Min Temp: $minTempValue °F, Max Temp: $maxTempValue °F"
                            )

                            val weatherViewModel =
                                ViewModelProvider(context as ComponentActivity)[WeatherViewModel::class.java]
                            weatherViewModel.insert(
                                WeatherData(
                                    date = date,
                                    curTemp = curTempValue,
                                    minTemp = minTempValue,
                                    maxTemp = maxTempValue
                                )
                            )
                        }
                    }
                } else {
                    Log.d("API RESPONSE", "Failed to Get Response")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("API RESPONSE", "Failed to Get Response")
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    Assignment_2Theme {
        Main()
    }
}