package com.example.assignment3question1

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.assignment3question1.ui.theme.Assignment3Question1Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GraphActivity : AppCompatActivity() {

    private val timeList = mutableListOf<Long>()
    private val pitchList = mutableListOf<Float>()
    private val rollList = mutableListOf<Float>()
    private val yawList = mutableListOf<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
        )

        val orientationViewModel = OrientationViewModel(application)

        CoroutineScope(Dispatchers.IO).launch {
            val allOrientationData = orientationViewModel.getAllOrientationData()

            allOrientationData.forEach {
                timeList.add(it.time.toLong())
                pitchList.add(it.pitch)
                rollList.add(it.roll)
                yawList.add(it.yaw)
            }
        }

        setContent {
            Assignment3Question1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    GenerateGraph(
                        timeList.toLongArray(),
                        pitchList.toFloatArray(),
                        rollList.toFloatArray(),
                        yawList.toFloatArray()
                    )
                }
            }
        }
    }
}

@Composable
fun GenerateGraph(
    timeList: LongArray, pitchList: FloatArray, rollList: FloatArray, yawList: FloatArray
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 35.dp, start = 10.dp, end = 10.dp))
        Text(text = "GRAPHS", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 25.sp)

        ShowGraph(timeList, pitchList, rollList, yawList)
    }
}

@Composable
fun ShowGraph(
    timeList: LongArray, pitchList: FloatArray, rollList: FloatArray, yawList: FloatArray
) {
    val pitchPoints = mutableListOf<Point>()
    val rollPoints = mutableListOf<Point>()
    val yawPoints = mutableListOf<Point>()

    for (i in timeList.indices) {
        pitchPoints.add(Point(i.toFloat(), pitchList[i]))
        rollPoints.add(Point(i.toFloat(), rollList[i]))
        yawPoints.add(Point(i.toFloat(), yawList[i]))
    }

    // Pitch vs Time Graph
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp)
    ) {
        LineChart(modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
            lineChartData = LineChartData(
                linePlotData = LinePlotData(
                    lines = listOf(
                        Line(
                            dataPoints = pitchPoints, LineStyle(
                                color = Color.Red
                            ), IntersectionPoint(
                                color = Color.Black,
                                radius = 5.dp,
                            ),

                            SelectionHighlightPoint(color = Color.Red, radius = 5.dp),

                            ShadowUnderLine(
                                alpha = 0.5f, brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Red, Color.Transparent
                                    )
                                )
                            ), SelectionHighlightPopUp()
                        )
                    ),
                ),

                xAxisData = AxisData.Builder().axisStepSize(60.dp).backgroundColor(Color.White)
                    .steps(pitchPoints.size - 1)
                    .labelData { i -> timeList[i].toString().takeLast(7) }
                    .labelAndAxisLinePadding(15.dp).axisLineColor(Color.Black)
                    .axisLabelColor(Color.Black).build(),

                yAxisData = AxisData.Builder().steps(pitchPoints.size).backgroundColor(Color.White)
                    .labelAndAxisLinePadding(15.dp).labelData { i ->
                        val min = pitchPoints.minOf { it.y }
                        val max = pitchPoints.maxOf { it.y }
                        ((i * ((max - min) / pitchPoints.size)) + min).toString().take(4)
                    }.axisLineColor(Color.Black).axisLabelColor(Color.Black).build(),

                paddingTop = 10.dp,
                bottomPadding = 0.dp,
                paddingRight = 0.dp,
                containerPaddingEnd = 0.dp,
                backgroundColor = Color.White,
            )
        )

        Text(
            text = "Pitch vs Time",
            color = Color.Black,
            fontSize = 15.sp,
            modifier = Modifier.padding(end = 10.dp),
            fontWeight = FontWeight.Bold
        )
    }

    // Roll vs Time Graph
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        LineChart(modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
            lineChartData = LineChartData(
                linePlotData = LinePlotData(
                    lines = listOf(
                        Line(
                            dataPoints = rollPoints, LineStyle(
                                color = Color.Green
                            ), IntersectionPoint(
                                color = Color.Black,
                                radius = 5.dp,
                            ),

                            SelectionHighlightPoint(color = Color.Green, radius = 5.dp),

                            ShadowUnderLine(
                                alpha = 0.5f, brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Green, Color.Transparent
                                    )
                                )
                            ), SelectionHighlightPopUp()
                        )
                    ),
                ),

                xAxisData = AxisData.Builder().axisStepSize(60.dp).backgroundColor(Color.White)
                    .steps(rollPoints.size - 1)
                    .labelData { i -> timeList[i].toString().takeLast(7) }
                    .labelAndAxisLinePadding(15.dp).axisLineColor(Color.Black)
                    .axisLabelColor(Color.Black).build(),

                yAxisData = AxisData.Builder().steps(rollPoints.size).backgroundColor(Color.White)
                    .labelAndAxisLinePadding(15.dp).labelData { i ->
                        val min = rollPoints.minOf { it.y }
                        val max = rollPoints.maxOf { it.y }
                        ((i * ((max - min) / pitchPoints.size)) + min).toString().take(4)
                    }.axisLineColor(Color.Black).axisLabelColor(Color.Black).build(),

                paddingTop = 10.dp,
                bottomPadding = 0.dp,
                paddingRight = 0.dp,
                containerPaddingEnd = 0.dp,
                backgroundColor = Color.White,
            )
        )

        Text(
            text = "Roll vs Time",
            color = Color.Black,
            fontSize = 15.sp,
            modifier = Modifier.padding(end = 10.dp),
            fontWeight = FontWeight.Bold
        )
    }

    // Yaw vs Time Graph
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        LineChart(modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
            lineChartData = LineChartData(
                linePlotData = LinePlotData(
                    lines = listOf(
                        Line(
                            dataPoints = yawPoints, LineStyle(
                                color = Color.Blue
                            ), IntersectionPoint(
                                color = Color.Black,
                                radius = 5.dp,
                            ),

                            SelectionHighlightPoint(color = Color.Blue, radius = 5.dp),

                            ShadowUnderLine(
                                alpha = 0.5f, brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Blue, Color.Transparent
                                    )
                                )
                            ), SelectionHighlightPopUp()
                        )
                    ),
                ),

                xAxisData = AxisData.Builder().axisStepSize(60.dp).backgroundColor(Color.White)
                    .steps(yawPoints.size - 1).labelData { i -> timeList[i].toString().takeLast(7) }
                    .labelAndAxisLinePadding(15.dp).axisLineColor(Color.Black)
                    .axisLabelColor(Color.Black).build(),

                yAxisData = AxisData.Builder().steps(yawPoints.size).backgroundColor(Color.White)
                    .labelAndAxisLinePadding(15.dp).labelData { i ->
                        val min = yawPoints.minOf { it.y }
                        val max = yawPoints.maxOf { it.y }
                        ((i * ((max - min) / pitchPoints.size)) + min).toString().take(4)
                    }.axisLineColor(Color.Black).axisLabelColor(Color.Black).build(),

                paddingTop = 10.dp,
                bottomPadding = 0.dp,
                paddingRight = 0.dp,
                containerPaddingEnd = 0.dp,
                backgroundColor = Color.White,
            )
        )

        Text(
            text = "Yaw vs Time",
            color = Color.Black,
            fontSize = 15.sp,
            modifier = Modifier.padding(end = 10.dp),
            fontWeight = FontWeight.Bold
        )
    }
}
