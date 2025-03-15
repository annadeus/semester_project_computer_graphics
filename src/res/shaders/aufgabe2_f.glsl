#version 330
in vec3 singleEdgeColor;
out vec3 triangleColor;

void main(){
    // triangleColor = vec3(1.0, 0.0, 1.0); // for 1./2. exercise
    triangleColor = singleEdgeColor;
}
