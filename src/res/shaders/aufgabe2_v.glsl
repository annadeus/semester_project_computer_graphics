#version 330
layout(location=0) in vec2 triangle;
layout(location=1) in vec3 mixedColors;
out vec3 singleEdgeColor;

void main(){

    singleEdgeColor = mixedColors;

    // wird für jede Ecke aufgerufen --> im FS für jeden PIXEL!!
    float a = 0.5;
    mat2 rotation_matrix = mat2(cos(a), sin(a), -sin(a), cos(a));

    gl_Position = vec4(rotation_matrix * triangle, 0.0, 1.0);

}

