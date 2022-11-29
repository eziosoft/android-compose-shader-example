package com.eziosoft.shaderscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import com.eziosoft.shaderscompose.ui.theme.ShadersComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShadersComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().,
                    color = MaterialTheme.colors.background
                ) {



                }
            }
        }
    }
}


val WOBBLE_SHADER ="""
    
""".trimIndent()

@Composable
fun Shader() {
    val time by produceState(0f) {
        while (true) {
            withInfiniteAnimationFrameMillis {
                value = it / 1000f
            }
        }
    }

    val shader = RuntimeShader(WOBBLE_SHADER)

//    Image(
//        vectorPainter, contentDescription = "",
//        modifier = Modifier
//            .fillMaxSize()
//            .background(largeRadialGradient)
//            .onSizeChanged { size ->
//                shader.setFloatUniform(
//                    "resolution",
//                    size.width.toFloat(),
//                    size.height.toFloat()
//                )
//            }
//            .graphicsLayer {
//                shader.setFloatUniform("time", time)
//                renderEffect = android.graphics.RenderEffect
//                    .createRuntimeShaderEffect(
//                        shader,
//                        "contents"
//                    )
//                    .asComposeRenderEffect()
//            }
//    )
}