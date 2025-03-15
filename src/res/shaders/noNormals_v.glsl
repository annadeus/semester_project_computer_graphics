#version 330
layout(location=0) in vec4 triangle;
layout(location=1) in vec3 mixedColors;
out vec3 singleEdgeColor;

void main(){

    singleEdgeColor = mixedColors;

    // wird für jede Ecke aufgerufen --> im FS für jeden PIXEL!!
    float a = 0.5;
    mat4 rotation_matrix_y = mat4(
    cos(a), 0.0, sin(a), 0.0,
    0.0,    1.0, 0.0,   0.0,
    -sin(a), 0.0, cos(a), 0.0,
    0.0,    0.0, 0.0,   1.0
    );

    mat4 translation_matrix = mat4(
    1.0, 0.0, 0.0, 0.0, // Dies verschiebt entlang der X-Achse
    0.0, 1.0, 0.0, 0.0,  // Y bleibt unverändert
    0.0, 0.0, 1.0, 0.0,  // Z bleibt unverändert
    0.0, 0.0, -4.0, 1.0   // Homogene Einheit
    );

    mat4 transformation_matrix = translation_matrix * rotation_matrix_y;

    gl_Position = transformation_matrix * triangle;

}

