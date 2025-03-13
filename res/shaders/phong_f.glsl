#version 330

in vec3 singleEdgeColor;
in vec3 normal;
in vec3 coord_p;

out vec3 triangleColor;

void main(){

    vec3 LichtPos = vec3(50.0, 100.0, 500.0);
    vec3 p = coord_p; // aus vertex

    vec3 L = normalize(LichtPos - p);
    vec3 V = normalize(-p);
    vec3 N = normal; // aus VBO; vertex
    vec3 R = reflect(-L, N);

    //I=I_a+I_d+I_s
    float I_a = 0.4; //ambient light
    float I_d = 0.3; //diffuse light
    float I_s = 1.0; //specular light

    //Intensität der Lichtquelle
    float I_L = 4;

    //materialabhängig: k_a + k_d + k_s = 1
    float k_a = 0.3;
    float k_d = 0.3;
    float k_s = 0.4;

    //Oberflächenhärte
    float n = 100;

    //Phong-Formel
    float I = I_a * k_a + I_L * (max(0, dot(L,N)) * k_d + pow(max(0, dot(R,V)), n) * k_s);

    // triangleColor = vec3(1.0, 0.0, 1.0); // für 1./2. exercise
    triangleColor = singleEdgeColor * I;

}

