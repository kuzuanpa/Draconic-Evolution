#version 120

varying vec3 position;
void main()
{
//    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;TODO I dont think i actually need this
//    position = (gl_ModelViewMatrix * gl_Vertex).xyz;

    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_FrontColor = gl_Color;

    position = (gl_ModelViewMatrix * gl_Vertex).xyz;
}
 
