
varying vec3 v3Pos;

float fOuterRadius = 10.25;
float fInnerRadius = 10.0;
	
float fCameraHeight = 10.05;
	
float fScale = 1 / (fOuterRadius - fInnerRadius );
float fScaleDepth = 0.25; // The scale depth (i.e. the altitude at which the atmosphere's average density is found)
float fScaleOverScaleDepth = fScale / fScaleDepth;

float fSamples = 100.0;
int nSamples = 100;

vec3 v3InvWavelength = vec3( 1.0 / pow(0.650, 4.0), 1.0 / pow(0.570, 4.0), 1.0 / pow(0.475, 4.0) );

float fKrESun = 0.0025 * 20.0;		// Kr * ESun
float fKmESun = 0.0015 * 20.0;		// Km * ESun
float fKr4PI = 0.0025 * 4.0 * 3.141592653;		// Kr * 4 * PI
float fKm4PI = 0.0015 * 4.0 * 3.141592653;		// Km * 4 * PI

float fg = -0.990;
float fg2 = fg * fg;
float fExposure = 1.0;

float scale(float fCos)
{
	float x = 1.0 - fCos;
	return fScaleDepth * exp(-0.00287 + x*(0.459 + x*(3.83 + x*(-6.80 + x*5.25))));
}

// Mie phase function
float getMiePhase(float fCos, float fCos2, float g, float g2)
{
   return 1.5 * ((1.0 - g2) / (2.0 + g2)) * (1.0 + fCos2) / pow(1.0 + g2 - 2.0*g*fCos, 1.5);
}

// Rayleigh phase function
float getRayleighPhase(float fCos2)
{   
   //return 0.75 + 0.75 * fCos2;
   return 0.75 * (2.0 + 0.5 * fCos2);
   
}

void main(void)
{


	vec3 v3CameraPos = vec3(0.5, 0.5, 1.0);
	vec3 v3LightPos = vec3(0.2, 0.2, 0.0) - v3CameraPos;	// The direction vector to the light source
	v3LightPos = normalize(v3LightPos);
	
	vec3 v3Ray = v3Pos - v3CameraPos;
	float fFar = length(v3Ray);
	v3Ray /= fFar;
	
	// Calculate the ray's starting position, then calculate its scattering offset
	vec3 v3Start = v3CameraPos;
	float fHeight = length(v3Start);
	float fDepth = exp(fScaleOverScaleDepth * (fInnerRadius - fCameraHeight));
	float fStartAngle = dot(v3Ray, v3Start) / fHeight;
	float fStartOffset = fDepth*scale(fStartAngle);
	
	// Initialize the scattering loop variables
	float fSampleLength = fFar / fSamples;
	float fScaledLength = fSampleLength * fScale;
	vec3 v3SampleRay = v3Ray * fSampleLength;
	vec3 v3SamplePoint = v3Start + v3SampleRay * 0.5;
	
	// Now loop through the sample rays
	vec3 v3FrontColor = vec3(0.0, 0.0, 0.0);
	for(int i=0; i<nSamples; i++)
	{
		float fHeight = length(v3SamplePoint);
		float fDepth = exp(fScaleOverScaleDepth * (fInnerRadius - fHeight));
		float fLightAngle = dot(v3LightPos, v3SamplePoint) / fHeight;
		float fCameraAngle = dot(v3Ray, v3SamplePoint) / fHeight;
		float fScatter = (fStartOffset + fDepth*(scale(fLightAngle) - scale(fCameraAngle)));
		vec3 v3Attenuate = exp(-fScatter * (v3InvWavelength * fKr4PI + fKm4PI));
		v3FrontColor += v3Attenuate * (fDepth * fScaledLength);
		v3SamplePoint += v3SampleRay;
	}
	

    // The "sun" factor brightens the atmosphere trying to cover the starfield
    //float sun = 0.90 + 2.0 * exp(-pow(fHeight,5.0)/pow(fOuterRadius,5.0));
    float sun = 1.0 + 6.50 * exp(-fHeight*fHeight/fOuterRadius*fOuterRadius);
	
	// Finally, scale the Mie and Rayleigh colors and set up the varying variables for the pixel shader        
    vec4 v4MieColor = vec4(v3FrontColor * fKmESun, 1.0);
    vec4 v4RayleighColor = vec4(v3FrontColor * (v3InvWavelength * fKrESun * sun), 1.0);
	vec3 v3Direction = v3CameraPos - v3Pos;


	float fCos = dot(v3LightPos, v3Direction) / length(v3Direction);
    float fCos2 = fCos*fCos;
    
    gl_FragColor = vec4( v4MieColor.xyz, 1.0 );
    
    //gl_FragColor = getRayleighPhase(fCos2) * v4RayleighColor + getMiePhase(fCos, fCos2, fg, fg2) * v4MieColor;
    //gl_FragColor.a = max(max(gl_FragColor.r, gl_FragColor.g), gl_FragColor.b);
    //gl_FragColor = 1.0 - exp(-fExposure * gl_FragColor);
	
}
