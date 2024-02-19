package com.example.assignment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun NormalList(
    modifier: Modifier = Modifier,
    distanceUnit: Boolean = true,
    stops: List<StopsStructure>,
    reachedStations: List<Int>,
) {
    Column(
        modifier = modifier
            .width(400.dp)
            .height(350.dp)
    ) {
        stops.forEachIndexed { index, stop ->
            val backgroundColor = if (index in reachedStations) Color.Red else Color.Green
            Surface(color = backgroundColor, modifier = modifier.fillMaxWidth()) {
                Row(
                    Modifier
                        .padding(horizontal = 18.dp, vertical = 5.dp)
                        .height(22.dp)) {
                    Text(
                        text = stop.stopName,
                        modifier = Modifier
                            .padding(end = 0.dp)
                            .fillMaxWidth(0.7f),
                        textAlign = TextAlign.Justify,
                        color = Color.Black
                    )

                    val distance = if (distanceUnit) {
                        stop.stopDistance.toString() + " Km"
                    } else {
                        BigDecimal((stop.stopDistance * 0.621371)).setScale(
                            2, RoundingMode.HALF_EVEN
                        ).toString() + " Mi"
                    }
                    Text(
                        text = distance,
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(horizontal = 12.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}