package com.eziosoft.shaderscompose

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