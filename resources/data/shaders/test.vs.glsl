
varying vec3 v3Pos;

void main(void){

	v3Pos = gl_Vertex.xyz;

  	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
  	gl_TexCoord[0]  = gl_MultiTexCoord0;
}