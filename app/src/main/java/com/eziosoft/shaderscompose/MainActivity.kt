package com.eziosoft.shaderscompose

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RuntimeShader
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.eziosoft.shaderscompose.ui.theme.ShadersComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val photo = BitmapFactory.decodeResource(resources, R.drawable.photo)
        val shader = RuntimeShader(SHADER7)

        setContent {
            ShadersComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Box(modifier = Modifier.background(Color.White).fillMaxSize()) {
//                        Shader(photo, shader)

                        val time by produceState(0f) {
                            while (true) {
                                withInfiniteAnimationFrameMillis {
                                    value = it / 1000f
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .background(Color.White)
                                .align(Alignment.Center)
                                .onSizeChanged { size ->
                                    shader.setFloatUniform(
                                        "iResolution",
                                        size.width.toFloat(),
                                        size.height.toFloat()
                                    )
                                }
                                .graphicsLayer {
                                    clip = true
                                    shader.setFloatUniform("iTime", time)
                                    renderEffect = android.graphics.RenderEffect
                                        .createRuntimeShaderEffect(shader, "composable")
                                        .asComposeRenderEffect()
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {

                            OutlinedTextField(
                                modifier = Modifier.padding(20.dp).background(Color.White),
                                value = "Name",
                                onValueChange = {})
                            OutlinedTextField(
                                modifier = Modifier.padding(20.dp).background(Color.White),
                                value = "Surname",
                                onValueChange = {})
                            OutlinedTextField(
                                modifier = Modifier.padding(20.dp).background(Color.White),
                                value = "Age",
                                onValueChange = {})
                            Button(onClick = { /*TODO*/ }) {
                                Text("Submit")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Shader(bitmap: Bitmap, shader: RuntimeShader) {
    val time by produceState(0f) {
        while (true) {
            withInfiniteAnimationFrameMillis {
                value = it / 1000f
            }
        }
    }

    Image(bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .background(Color.Black)
            .onSizeChanged { size ->
                shader.setFloatUniform(
                    "iResolution",
                    size.width.toFloat(),
                    size.height.toFloat()
                )
            }
            .graphicsLayer {
                clip = true
                shader.setFloatUniform("iTime", time)
                renderEffect = android.graphics.RenderEffect
                    .createRuntimeShaderEffect(shader, "composable")
                    .asComposeRenderEffect()
            }
    )
}