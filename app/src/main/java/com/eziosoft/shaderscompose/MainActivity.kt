package com.eziosoft.shaderscompose

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RuntimeShader
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import com.eziosoft.shaderscompose.ui.theme.ShadersComposeTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val photo = BitmapFactory.decodeResource(resources, R.drawable.photo)


        setContent {
            ShadersComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Shader(photo)

                }
            }
        }
    }
}


val SHADER1 = """
    uniform shader composable;
    uniform float2 iResolution;
    uniform float iTime;
    
    float f(vec3 p) {
        p.z -= iTime * 10.;
        float a = p.z * .1;
        p.xy *= mat2(cos(a), sin(a), -sin(a), cos(a));
        return .1 - length(cos(p.xy) + sin(p.yz));
    }

    half4 main(vec2 fragcoord) { 
        vec3 d = .5 - fragcoord.xy1 / iResolution.y;
        vec3 p=vec3(0);
        for (int i = 0; i < 32; i++) {
          p += f(p) * d;
        }
        return ((sin(p) + vec3(2, 5, 9)) / length(p)).xyz1;
    }
""".trimIndent()

val SHADER2 = """
  uniform shader composable;
  uniform float2 iResolution;
  uniform float iTime;
  
 vec4 main(vec2 FC) {
  vec4 o = vec4(0);
  vec2 p = vec2(0), c=p, u=FC.xy*2.-iResolution.xy;
  float a;
  for (float i=0; i<4e2; i++) {
    a = i/2e2-1.;
    p = cos(i*2.4+iTime+vec2(0,11))*sqrt(1.-a*a);
    c = u/iResolution.y+vec2(p.x,a)/(p.y+2.);
    o += (cos(i+vec4(0,2,4,0))+1.)/dot(c,c)*(1.-p.y)/3e4;
  }
  return o;
}
""".trimIndent()

val SHADER3 = """
  uniform shader composable;
  uniform float2 iResolution;
  uniform float iTime;
  
 const float PI2 = 6.28318530718;
float F(vec2 c){
  return fract(sin(dot(c, vec2(12.9898, 78.233))) * 43758.5453);
}

half4 main(float2 FC) {
  vec4 o;
  float t = iTime;
  vec2 r = iResolution.xy * vec2(1, -1);
  vec3 R=normalize(vec3((FC.xy*2.-r)/r.y,1));
  for(float i=0; i<100; ++i) {
    float I=floor(t/.1)+i;
    float d=(I*.1-t)/R.z;
    vec2 p=d*R.xy+vec2(sin(t+F(I.xx)*PI2)*.3+F(I.xx*.9),t+F(I.xx*.8));
    if (F(I/100+ceil(p))<.2) {
      o+=smoothstep(.1,0.,length(fract(p)-.5))*exp(-d*d*.04);
    }
  }
  return o;
}

""".trimIndent()

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Shader(bitmap: Bitmap) {
    val time by produceState(0f) {
        while (true) {
            withInfiniteAnimationFrameMillis {
                value = it / 1000f
            }
        }
    }

    val shader = RuntimeShader(SHADER2)

    Image(bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .onSizeChanged { size ->
                shader.setFloatUniform(
                    "iResolution",
                    size.width.toFloat(),
                    size.height.toFloat()
                )
            }
            .graphicsLayer {
                clip =true
                shader.setFloatUniform("iTime", time)
                renderEffect = android.graphics.RenderEffect
                    .createRuntimeShaderEffect(shader, "composable")
                    .asComposeRenderEffect()
            }
    )
}