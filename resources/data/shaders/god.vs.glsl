
varying vec3 v3Pos;
varying vec2 texCoord;

void main(void){

	v3Pos = gl_Vertex.xyz;

  	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
  	texCoord  = gl_MultiTexCoord0.xy;
  	
  	//texCoord = vec2( v3Pos.x, 1.0 - v3Pos.y );
  	
}