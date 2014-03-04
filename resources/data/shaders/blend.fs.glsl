
varying vec3 v3Pos;
varying vec2 texCoord;

uniform sampler2D texture_0;
uniform sampler2D texture_1;

void main(void)
{
    
    vec4 texColor = texture( texture_1, texCoord );
    
    gl_FragColor = vec4( texColor );
	
}
