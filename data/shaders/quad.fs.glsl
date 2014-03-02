
varying vec3 v3Pos;
varying vec2 texCoord;

uniform sampler2D texture_0;
uniform sampler2D texture_1;

uniform float sunFactor;

void main(void)
{
    
    vec4 texColor = texture( texture_0, texCoord );
    vec4 godColor = texture( texture_1, texCoord );
    
    if ( texColor.a < 0.2 ) {
     	discard;
    }
    
    if ( length(texColor.xyz) < 0.9 ) {
		texColor *= sunFactor;
		godColor *= sunFactor;
		//texColor = vec4(1.0, 0.0, 0.0, 1.0);
    }
    
    gl_FragColor = vec4( godColor.xyz + texColor.xyz, 1.0 );
    
    //gl_FragColor = vec4(texColor.xyz, 1.0);
	
}
