uniform float exposure;
uniform float decay;
uniform float density;
uniform float weight;
uniform vec2 lightPositionOnScreen;
uniform sampler2D texture_0;

uniform vec4 test;

const int NUM_SAMPLES = 100;

varying vec2 texCoord;

 void main()

 {	


 	vec2 deltaTextCoord = vec2( texCoord - lightPositionOnScreen.xy );

 	vec2 textCoo = texCoord;

 	deltaTextCoord *= 1.0 /  float(NUM_SAMPLES) * density;

 	float illuminationDecay = 1.0;

	

	vec3 colors = vec3(0.0, 0.0, 0.0);

 	for(int i=0; i < NUM_SAMPLES ; i++)

  	{

    			textCoo -= deltaTextCoord;

    			vec3 sample = texture(texture_0, textCoo ).xyz;
    			
    			sample *= illuminationDecay * weight;

    			
    			colors += sample;

    			
    			illuminationDecay *= decay;

 	}

 	colors *= exposure;
 	
 	//gl_FragColor = vec4( texture(texture_0, texCoord ) );
 	
 	//gl_FragColor = vec4(exposure, exposure, exposure, 1.0);
 	gl_FragColor = vec4( colors, 0.2 );
 	
 	//vec4 texColor = texture( texture_0, texCoord );
    //gl_FragColor = vec4( texColor );

}