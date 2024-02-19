package com.example.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment.ui.theme.AssignmentTheme
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssignmentTheme {
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

@Composable
fun Main() {
    var stations by remember { mutableStateOf(stops) }
    var progress by remember { mutableFloatStateOf(0f) }
    var distanceUnit by remember { mutableStateOf(true) }
    val reachedStations = remember { mutableStateListOf<Int>() }
    var distanceCovered by remember { mutableDoubleStateOf(0.0) }
    var totalDistance by remember { mutableDoubleStateOf(stops.maxOf { it.stopDistance }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffc4d3fd)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainUI(
            stops = stations,
            distanceUnit = distanceUnit,
            reachedStations = reachedStations,
            distanceCovered = distanceCovered,
            totalDistance = totalDistance,
            progress = progress
        )

        Button(
            onClick = {
                val unreachedStationIndex = stations.indexOfFirst { !it.reached }
                if (unreachedStationIndex != -1) {
                    stations[unreachedStationIndex].reached = true
                    reachedStations.add(unreachedStationIndex)
                    distanceCovered = stations[unreachedStationIndex].stopDistance
                    progress = reachedStations.size.toFloat() / stations.size.toFloat()
                }
            },
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                .width(380.dp),
            colors = ButtonDefaults.buttonColors(Color(0xff8095de)),
            border = BorderStroke(width = 2.dp, color = Color(0xFF4D70E9))
        ) {
            Text(
                text = "Reached",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        Row {
            Button(
                onClick = { distanceUnit = !distanceUnit },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(180.dp),
                colors = ButtonDefaults.buttonColors(Color(0xff8095de)),
                border = BorderStroke(width = 2.dp, color = Color(0xFF4D70E9))
            ) {
                if (distanceUnit) {
                    Text(
                        text = "Km",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                } else {
                    Text(
                        text = "Mi",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            Button(
                onClick = {
                    stations = if (stations == stops) lazyStops else stops
                    totalDistance = stations.maxOf { it.stopDistance }
                    reachedStations.clear()
                    progress = 0f
                    distanceCovered = 0.0
                    for (station in stations) station.reached = false
                },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(180.dp),
                colors = ButtonDefaults.buttonColors(Color(0xff8095de)),
                border = BorderStroke(width = 2.dp, color = Color(0xFF4D70E9))
            ) {
                Text(
                    text = "Switch",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }
}

private fun reduceToNDecimalPlace(n: Double): BigDecimal? {
    return BigDecimal((n)).setScale(2, RoundingMode.HALF_EVEN)
}

@Composable
private fun MainUI(
    modifier: Modifier = Modifier,
    distanceUnit: Boolean = true,
    stops: List<StopsStructure>,
    reachedStations: List<Int>,
    distanceCovered: Double,
    totalDistance: Double,
    progress: Float,
) {
    Column(
        modifier = modifier.padding(horizontal = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = Color(0xff8095de))
                .fillMaxWidth()
                .height(145.dp)
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(120.dp)
                    .height(135.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .border(
                        width = 2.dp, color = Color(0xFF4D70E9), shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text(
                    text = "Total",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 5.dp, start = 25.dp)
                )
                Text(
                    text = if (distanceUnit) reduceToNDecimalPlace(totalDistance).toString()
                    else {
                        if (stops.size == 11) BigDecimal((totalDistance * 0.621371)).setScale(
                            3, RoundingMode.HALF_EVEN
                        ).toString()
                        else reduceToNDecimalPlace(totalDistance * 0.621371).toString()
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 3.dp, start = 10.dp)
                )

                Text(
                    text = if (distanceUnit) "Km" else "Mi",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 2.dp, start = 34.dp)
                )
            }

            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                        .border(
                            width = 2.dp,
                            color = Color(0xFF4D70E9),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(5.dp)
                ) {
                    Column {
                        Text(
                            text = "Distance Covered",
                            textAlign = TextAlign.Start,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                        )

                        Text(
                            text = if (distanceUnit) reduceToNDecimalPlace(distanceCovered).toString() + " Km"
                            else reduceToNDecimalPlace(distanceCovered * 0.621371).toString() + " Mi",

                            textAlign = TextAlign.Start,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 0.dp, start = 5.dp)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Distance Covered",
                        tint = if (progress != 1f) Color.Black else Color.Green,
                        modifier = Modifier
                            .fillMaxSize(0.8f)
                            .padding(top = 10.dp, start = 25.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                        .border(
                            width = 2.dp,
                            color = Color(0xFF4D70E9),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(start = 5.dp, top = 2.dp)
                ) {
                    Column {
                        Text(
                            text = "Distance Left",
                            textAlign = TextAlign.Start,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                        )

                        Text(
                            text = if (distanceUnit) reduceToNDecimalPlace(totalDistance - distanceCovered).toString() + " Km"
                            else reduceToNDecimalPlace((totalDistance - distanceCovered) * 0.621371).toString() + " Mi",

                            textAlign = TextAlign.Start,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 0.dp, start = 5.dp, end = 3.dp)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Distance Left",
                        tint = if (progress != 1f) Color.Black else Color.Green,
                        modifier = Modifier
                            .fillMaxSize(0.8f)
                            .padding(top = 10.dp, start = 30.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .background(
                    color = Color(0xff8095de)
                )
                .fillMaxWidth()
                .height(40.dp)
        ) {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .width(380.dp)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .height(20.dp)
                    .border(
                        width = 2.dp, color = Color(0xFF4D70E9), shape = RoundedCornerShape(10.dp)
                    ),
                color = Color.Red,
                trackColor = Color.Green,
                strokeCap = StrokeCap.Round
            )
        }

        Column(
            modifier = Modifier
                .padding(8.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .border(width = 2.dp, color = Color(0xff8095de), shape = RoundedCornerShape(20.dp))
        ) {
            Surface(
                color = Color.LightGray,
                modifier = Modifier
                    .width(400.dp)
                    .height(50.dp)
                    .border(width = 2.dp, color = Color(0xff8095de)),
            ) {
                Row(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Stations",
                        modifier = Modifier.fillMaxSize(0.6f),
                        textAlign = TextAlign.Justify,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Distances",
                        textAlign = TextAlign.Justify,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            if (stops.size == 11) {
                NormalList(stops = stops, reachedStations = reachedStations, distanceUnit = distanceUnit)
            }

            else {
                LazyList(stops = stops, reachedStations = reachedStations, distanceUnit = distanceUnit)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    AssignmentTheme {
        Main()
    }
}