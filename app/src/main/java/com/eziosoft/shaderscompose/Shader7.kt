package com.eziosoft.shaderscompose

val SHADER7 = """
 uniform shader composable;
 uniform float2 iResolution;
 uniform float iTime;
      
 half4 main(vec2 fragcoord) { 
   	float x= fragcoord.x;
    float y= fragcoord.y;
    
    vec3 color = composable.eval(
         vec2(fragcoord.x+sin(iTime+x/50)*20,
              fragcoord.y+sin(iTime+y/50)*20
             )).rgb;
             
     return half4(color,1);
 }
""".trimIndent()