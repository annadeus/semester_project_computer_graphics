#version 330
layout(location=0) in vec4 triangle;
layout(location=1) in vec3 mixedColors;
layout(location=2) in vec3 normals;
layout(location=3) in vec2 uv_coord;

out vec3 singleEdgeColor;
out vec3 normal;
out vec3 coord_p;
out vec2 texture_coord;

uniform mat4 rotationMatrix;
uniform mat4 projectionMatrix;

void main(){

    singleEdgeColor = mixedColors;

    normal = inverse(transpose(mat3(rotationMatrix))) * normals;

    coord_p = vec3(rotationMatrix * triangle);

    // wird für jede Ecke aufgerufen --> im FS für jeden PIXEL!!

    texture_coord = uv_coord;

    gl_Position = projectionMatrix * rotationMatrix * vec4(triangle);

}