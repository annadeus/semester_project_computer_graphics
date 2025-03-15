package a2;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

public class Aufgabe2 extends AbstractOpenGLBase {

	public static void main(String[] args) {
		new Aufgabe2().start("CG Aufgabe 2", 700, 700);
	}

	@Override
	protected void init() {
		// folgende Zeile lädt automatisch "aufgabe2_v.glsl" (vertex) und "aufgabe2_f.glsl" (fragment)
		ShaderProgram shaderProgram = new ShaderProgram("aufgabe2");
		glUseProgram(shaderProgram.getId());

		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen


		// coordinates for each edge of the triangle
		float[] triangle = new float[] {
				//A, B, C --> KS 0,1
                0.0f, 0.0f, 0.0f, 0.9f, 0.9f, 0.0f
		};

		// color for every single edge
		float[] colorsForMixing = new float[] {
				//R, G, B --> 3 mal
				1.0f, 1.0f, 0.0f, // edge1
				1.0f, 0.0f, 1.0f, // edge2
				0.0f, 1.0f, 1.0f  // edge3
		};

		int vao1 = createVAO();
			createVBO(triangle, 0, 2);
			createVBO(colorsForMixing, 1, 3);

/*
		// VAO1
		int vao1 = glGenVertexArrays();
		glBindVertexArray(vao1);

		// VBO1
		int vbo1 = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo1);
		glBufferData(GL_ARRAY_BUFFER, triangle, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		//VBO2
		int vbo2 = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo2);
		glBufferData(GL_ARRAY_BUFFER, colorsForMixing, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		// index 1 --> "Speicherzelle" in VAO, size 3 --> 3 Werte gehören zusammen --> wegen RGB
		glEnableVertexAttribArray(1);
 */


	}

	@Override
	public void update() {
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT); // Zeichenflaeche leeren

		// hier vorher erzeugte VAOs zeichnen
		// draw the triangle
		glDrawArrays(GL_TRIANGLES, 0, 3);

	}

	private int createVAO(){
		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		return vaoId;
	}

	private void createVBO(float[] coordinates, int attributeLocation, int dimension){
		int vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, coordinates, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeLocation, dimension, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(attributeLocation);
	}

}
