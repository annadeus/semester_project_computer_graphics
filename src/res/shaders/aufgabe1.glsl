#version 330
out vec3 pixelColor;

//circles
bool isInCircle(){
    vec2 m = vec2(200.0, 200.0);
    if (distance(m, gl_FragCoord.xy) <= 100.0){
        pixelColor = vec3(0.8,0.4,1.0);
        return true;
    }
    vec2 n = vec2(450.0, 420.0);
    if (distance(n, gl_FragCoord.xy) <= 140.0){
        pixelColor = vec3(0.9,0.7,0.5);
        return true;
    }
    vec2 o = vec2(200.0, 600.0);
    if (distance(o, gl_FragCoord.xy) <= 50.0){
        pixelColor = vec3(0.3,0.8,0.9);
        return true;
    }
    return false;
}

//rectangles
bool isInRectangle(){
    if (gl_FragCoord.x >= 200 && gl_FragCoord.x <= 600){
        if (gl_FragCoord.y >= 100 && gl_FragCoord.y <= 400){
            pixelColor = vec3(0.8,0.8,0.0);
            return true;
        }
    }
    if (gl_FragCoord.x >= 500 && gl_FragCoord.x <= 680){
        if (gl_FragCoord.y >= 450 && gl_FragCoord.y <= 650){
            pixelColor = vec3(0.4,0.5,1.0);
            return true;
        }
    }
    if (gl_FragCoord.x >= 100 && gl_FragCoord.x <= 400){
        if (gl_FragCoord.y >= 350 && gl_FragCoord.y <= 600){
            pixelColor = vec3(0.9,0.4,0.7);
            return true;
        }
    }
    //line --> very thin rectangle
    if (gl_FragCoord.x >= 200 && gl_FragCoord.x <= 600){
        if (gl_FragCoord.y >= 680 && gl_FragCoord.y <= 681){
            pixelColor = vec3(0.9,1.0,0.7);
            return true;
        }
    }
    return false;
}


void main(){
    pixelColor = vec3(0.3,0.0,1.0);

    if(isInRectangle()==true){
        isInRectangle();
    }

    if(isInCircle()==true){
        isInCircle();
    }

        //rotated rectangle
        float a = 0.4;
        mat2 rotation_matrix = mat2(cos(a), sin(a), -sin(a), cos(a));
        vec2 rotated_point = rotation_matrix * vec2(gl_FragCoord.xy); //pixelcoordinate is being rotated
        if (rotated_point.x >= 150 && rotated_point.x <= 500){
            if (rotated_point.y >= 250 && rotated_point.y <= 550){
                pixelColor = vec3(0.1,0.5,1.0);
            }
        }
}
