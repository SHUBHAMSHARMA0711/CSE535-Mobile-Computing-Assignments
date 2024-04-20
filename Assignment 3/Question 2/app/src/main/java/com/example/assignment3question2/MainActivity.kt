package com.example.assignment3question2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3question2.ml.MobilenetV110224Quant
import com.example.assignment3question2.ui.theme.Assignment3Question2Theme
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainActivity : ComponentActivity() {

    companion object {
        init {
            System.loadLibrary("assignment3question2")
        }
    }

    private external fun helloFromCpp(): String
    external fun scaleImage(bitmap: Bitmap): Bitmap

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val hello = helloFromCpp()
        Log.d("HelloFromCpp", hello)

        super.onCreate(savedInstanceState)

        val labels = applicationContext.assets.open("labels.txt").bufferedReader().readLines()

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
        )
        setContent {
            Assignment3Question2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Main(this, labels)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Main(activity: MainActivity, labels: List<String> = listOf()) {

    val predictionResult = remember { mutableStateOf("") }
    var imageUris by remember { mutableStateOf<List<Uri>>(listOf()) }
    val imageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
            imageUris = uris
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))

        LazyColumn(
            modifier = Modifier
                .height(400.dp)
                .width(400.dp)
                .background(color = Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(imageUris) { imageUri ->
                Image(
                    bitmap = ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            LocalContext.current.contentResolver, imageUri
                        )
                    ).asImageBitmap(),
                    contentDescription = "Image Loaded from Gallery",
                    modifier = Modifier.size(400.dp)
                )
            }
        }

        ElevatedButton(
            onClick = {
                imageLauncher.launch("image/*")
            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, contentColor = Color(0xFFFFFFFF)
            ),
            border = BorderStroke(2.dp, Color.White),
            elevation = ButtonDefaults.buttonElevation(100.dp)
        ) {
            Text(
                text = "Choose Images from Gallery",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        ElevatedButton(
            onClick = {
                if (imageUris.isNotEmpty()) {
                    val model = MobilenetV110224Quant.newInstance(activity)

                    val bitmap = scaleImage(getBitmapFromUri(activity, imageUris[0]))

                    val inputFeature0 =
                        TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
                    inputFeature0.loadBuffer(
                        TensorImage.fromBitmap(
                            bitmap.copy(
                                Bitmap.Config.ARGB_8888, true
                            )
                        ).buffer
                    )

                    val outputs = model.process(inputFeature0)
                    val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                    predictionResult.value =
                        labels[outputFeature0.floatArray.indexOf(outputFeature0.floatArray.maxOrNull())]

                    model.close()
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
                text = "Predict",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Text(
            text = predictionResult.value, color = Color.White, fontSize = 20.sp
        )
    }
}

private fun FloatArray.indexOf(maxOrNull: Float?): Int {
    for (i in this.indices) {
        if (this[i] == maxOrNull) {
            return i
        }
    }
    return -1
}

private fun scaleImage(bitmap: Bitmap): Bitmap {
    return Bitmap.createScaledBitmap(bitmap, 224, 224, true)
}

@RequiresApi(Build.VERSION_CODES.P)
fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
    return ImageDecoder.decodeBitmap(
        ImageDecoder.createSource(
            context.contentResolver, uri
        )
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun MainPreview() {
    Assignment3Question2Theme {
        Main(MainActivity())
    }
}