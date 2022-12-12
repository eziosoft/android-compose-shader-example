package com.eziosoft.shaderscompose

val SHADER6 = """
      uniform shader composable;
      uniform float2 iResolution;
      uniform float iTime;
      
 half4 main(vec2 fragcoord) { 
   	float x= fragcoord.x;
     float y= fragcoord.y;
    
    vec3 c = vec3(0,0,0);
    vec3 c1 = vec3(0,0,0);
    const half count = 1;
    for (half i = -count; i < count; i += 1) {
    	 for (half j = -count; j < count; j += 1) {
        c1 = composable.eval(
         vec2(fragcoord.x+j+sin(iTime+x/50)*20,
              fragcoord.y+i+sin(iTime+y/50)*20
             )).rgb;
        c+=c1/(4*count*count);
        
       
        
        
        
    	 }
    }
      
     return half4(c,1);
 }

""".trimIndent()