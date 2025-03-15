package projekt;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Projekt extends AbstractOpenGLBase {

	Matrix4 rotationMatrix = new Matrix4();
	Matrix4 rotationMatrix2 = new Matrix4();
	Matrix4 rotationMatrix3 = new Matrix4();
	float angle;

	private int vao1;
	private int vao2;
	private int vao3;

	private ShaderProgram shaderProgram;
	private ShaderProgram shaderProgram_texture;

	public static void main(String[] args) {
		new Projekt().start("CG Projekt", 1300, 1300);
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("phong");
		shaderProgram_texture = new ShaderProgram("phong_texture");

		/*
		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen --> von Aufgabe 2 übernehmen
		// coordinates for each edge of the triangle
		float[] triangle = new float[] {
				//A, B, C --> KS 0,1
				//x, y, z
				0.0f, 0.0f, 0.0f,
				0.9f, 0.0f, 0.0f,
				0.0f, 0.9f, 0.0f
		};

		// color for every single edge
		float[] colorsForMixing = new float[] {
				//R, G, B --> 3 mal
				1.0f, 1.0f, 0.0f, // edge1
				1.0f, 0.0f, 1.0f, // edge2
				0.0f, 1.0f, 1.0f  // edge3
		};
		 */

		float[] tetraeder = new float[] {
				// Dreieck ABC
				0.9f, 0.0f, 0.0f, // Punkt A
				0.0f, 0.0f, 0.0f, // Punkt B
				0.0f, 0.9f, 0.0f, // Punkt C

				// Dreieck ADB
				0.9f, 0.0f, 0.0f, // Punkt A
				0.0f, 0.0f, 0.9f, // Punkt D
				0.0f, 0.0f, 0.0f, // Punkt B

				// Dreieck DCB
				0.0f, 0.0f, 0.9f, // Punkt D
				0.0f, 0.9f, 0.0f, // Punkt C
				0.0f, 0.0f, 0.0f, // Punkt B

				// Dreieck ACD
				0.9f, 0.0f, 0.0f, // Punkt A
				0.0f, 0.9f, 0.0f, // Punkt C
				0.0f, 0.0f, 0.9f  // Punkt D
		};

		float[] pyramid = new float[] {
				// boden1
				0.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,
				// boden2
				0.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 1.0f,
				// links
				0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f,
				0.5f, 1.0f, 0.5f,
				// oben
				0.0f, 0.0f, 0.0f,
				0.5f, 1.0f, 0.5f,
				1.0f, 0.0f, 0.0f,
				// rechts
				1.0f, 0.0f, 0.0f,
				0.5f, 1.0f, 0.5f,
				1.0f, 0.0f, 1.0f,
				// unten
				1.0f, 0.0f, 1.0f,
				0.5f, 1.0f, 0.5f,
				0.0f, 0.0f, 1.0f
		};

		// color for every single edge
		float[] colorsForMixing_tet = new float[] {
				//R, G, B --> 3 mal
				1.0f, 1.0f, 0.0f, // edge1
				1.0f, 0.0f, 1.0f, // edge2
				0.0f, 1.0f, 1.0f, // edge3

				1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 1.0f,
				1.0f, 0.0f, 1.0f,

				1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 1.0f,
				1.0f, 0.0f, 1.0f,

				1.0f, 1.0f, 0.0f,
				1.0f, 0.0f, 1.0f,
				0.0f, 1.0f, 1.0f
		};

		/*
		float[] cube_color = new float[] {
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,

				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,

				-1.0f, 1.0f, 0.0f,
				-1.0f, 1.0f, 0.0f,
				-1.0f, 1.0f, 0.0f,

				0.0f, 1.0f, -1.0f,
				0.0f, 1.0f, -1.0f,
				0.0f, 1.0f, -1.0f,

				1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f,

				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
		};

		float[] colorsForMixing_pyr = new float[] {
				//R, G, B --> 3 mal
				1.0f, 1.0f, 0.0f,
				1.0f, 0.0f, 1.0f,
				0.0f, 1.0f, 1.0f,

				1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 1.0f,
				1.0f, 0.0f, 1.0f,

				1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 1.0f,
				1.0f, 0.0f, 1.0f,

				1.0f, 1.0f, 0.0f,
				1.0f, 0.0f, 1.0f,
				0.0f, 1.0f, 1.0f,

				1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 1.0f,
				1.0f, 0.0f, 1.0f,

				1.0f, 1.0f, 0.0f,
				1.0f, 0.0f, 1.0f,
				0.0f, 1.0f, 1.0f
		};

		float[] normals_tet = new float[] {
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,

				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,

				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,

				0.5f, 0.5f, 0.5f,
				0.5f, 0.5f, 0.5f,
				0.5f, 0.5f, 0.5f,
		};

		float[] normals_pyr = new float[] {
				//b1
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				//b2
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				//l
				-1.0f, 1.0f, 0.0f,
				-1.0f, 1.0f, 0.0f,
				-1.0f, 1.0f, 0.0f,
				//o
				0.0f, 1.0f, -1.0f,
				0.0f, 1.0f, -1.0f,
				0.0f, 1.0f, -1.0f,
				//r
				1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
				//u
				0.0f, 1.0f, 1.0f,
				0.0f, 1.0f, 1.0f,
				0.0f, 1.0f, 1.0f
		};
		 */

		//(unten dann) neues VBO mit UV-Koordinaten
		//diese Koord. in vertex entgegennehmen und weiter an fragment weiterrreichen
		float[] uv_coord_pyr = new float[] {
				// boden1
				0.0f, 0.0f,
				1.0f, 1.0f,
				0.0f, 1.0f,
				// boden2
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f,
				// links
				0.0f, 0.0f,
				0.0f, 1.0f,
				0.5f, 0.5f,
				// oben
				0.0f, 0.0f,
				0.5f, 0.5f,
				1.0f, 0.0f,
				// rechts
				1.0f, 0.0f,
				0.5f, 0.5f,
				1.0f, 1.0f,
				// unten
				1.0f, 1.0f,
				0.5f, 0.5f,
				0.0f, 1.0f
		};


		// Bilddatei einlesen, noch nicht angewendet auf Objekt
		Texture texture_under = new Texture("bricks.jpg");
		Texture texture_over = new Texture("groß.jpg");
		Texture texture_normal = new Texture("gitter.jpg", 3);

		glBindTexture(GL_TEXTURE_2D, texture_under.getId());


		vao1 = createVAO();
			createVBO(tetraeder, 0, 3);
			createVBO(colorsForMixing_tet, 1,3);
			createVBO(calculateNormals(tetraeder), 2,3);

		vao2 = createVAO();
			createVBO(pyramid, 0, 3);
			//createVBO(colorsForMixing_pyr, 1,3);
			createVBO(calculateNormals(pyramid), 2,3);
			createVBO(uv_coord_pyr, 3,2);

			float[] cube = createCube(0.7f,0.7f,8);

		vao3 = createVAO();
			createVBO(cube, 0, 3);
			createVBO(generateRandomColors(cube.length), 1,3);
			createVBO(calculateNormals(cube), 2,3);


		// Projektionsmatrix übergeben
		Matrix4 projectionMatrix = new Matrix4(1, 100, 2, 2);
		glUseProgram(shaderProgram.getId());
		int loc1= glGetUniformLocation(shaderProgram.getId(), "projectionMatrix");
		glUniformMatrix4fv(loc1,false,projectionMatrix.getValuesAsArray());

		glUseProgram(shaderProgram_texture.getId());
		int loc2= glGetUniformLocation(shaderProgram_texture.getId(), "projectionMatrix");
		glUniformMatrix4fv(loc2,false,projectionMatrix.getValuesAsArray());

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren
	}


	@Override
	public void update() {
		// Transformation durchfuehren (Matrix anpassen)
		rotationMatrix = new Matrix4().translate(-0.5f, 0, -0.5f).scale(2).rotateY(angle).rotateX(-0.25f).translate(0.0f, 0.0f, -4f); // im Raum "verschieben"
		rotationMatrix2 = new Matrix4().multiply(rotationMatrix).translate(5f,0f,-6f);
		//rotationMatrix2 = new Matrix4().translate(-0.5f, 0, -0.5f).scale(2).rotateY(angle).rotateX(-0.25f).translate(3.0f, 3.0f, -7f); // im Raum "verschieben"
		rotationMatrix3 = new Matrix4().scale(2).rotateY(angle).rotateX(-0.5f).translate(-5.0f, 0.0f, -8f);
		angle += 0.01f;
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // GL_DEPTH_BUFFER_BIT auf Unendlich initialisieren

		/*
		glBindVertexArray(vao1);
		// Matrix an Shader uebertragen --> von Folien übernehmen
		// VAOs zeichnen
		int loc= glGetUniformLocation(shaderProgram.getId(), "rotationMatrix");
		glUniformMatrix4fv(loc,false,rotationMatrix.getValuesAsArray());

		// hier vorher erzeugte VAOs zeichnen
		// draw the triangle
		glDrawArrays(GL_TRIANGLES, 0, 12);
		 */

		glUseProgram(shaderProgram.getId());
		glBindVertexArray(vao1);
		int loc1= glGetUniformLocation(shaderProgram.getId(), "rotationMatrix");
		glUniformMatrix4fv(loc1,false,rotationMatrix2.getValuesAsArray());
		glDrawArrays(GL_TRIANGLES, 0, 18);

		glBindVertexArray(vao3);
		glUniformMatrix4fv(loc1,false,rotationMatrix3.getValuesAsArray());
		glDrawArrays(GL_TRIANGLES, 0, 3000);


		glUseProgram(shaderProgram_texture.getId());
		glBindVertexArray(vao2);
		int loc2= glGetUniformLocation(shaderProgram.getId(), "rotationMatrix");
		glUniformMatrix4fv(loc2,false,rotationMatrix.getValuesAsArray());
		glDrawArrays(GL_TRIANGLES, 0, 18);


	}


	private float[] createCube(float radius, float height, int segments) {

		ArrayList<Float> coordCube = new ArrayList<>();

		float sectorStep = (float) ((2 * Math.PI) / segments);

		for (int i = 0; i < segments; i++) {
			double angleTurn = i * sectorStep;
			float x = (float) (radius * Math.cos(angleTurn));
			float z = (float) (radius * Math.sin(angleTurn));

			coordCube.add(x);
			coordCube.add(0.0f);
			coordCube.add(z);

			coordCube.add(x);
			coordCube.add(height);
			coordCube.add(z);
		}

		ArrayList<Float> triangles = new ArrayList<>();

		for (int i = 0; i < (coordCube.size()/3)-2; i++) {
			if(i%2==1) {
				triangles.add(coordCube.get(3*i));
				triangles.add(coordCube.get(3*i + 1));
				triangles.add(coordCube.get(3*i + 2));

				triangles.add(coordCube.get(3*i + 6));
				triangles.add(coordCube.get(3*i + 7));
				triangles.add(coordCube.get(3*i + 8));

				triangles.add(coordCube.get(3*i + 3));
				triangles.add(coordCube.get(3*i + 4));
				triangles.add(coordCube.get(3*i + 5));
			} else {
				triangles.add(coordCube.get(3*i));
				triangles.add(coordCube.get(3*i + 1));
				triangles.add(coordCube.get(3*i + 2));

				triangles.add(coordCube.get(3*i + 3));
				triangles.add(coordCube.get(3*i + 4));
				triangles.add(coordCube.get(3*i + 5));

				triangles.add(coordCube.get(3*i + 6));
				triangles.add(coordCube.get(3*i + 7));
				triangles.add(coordCube.get(3*i + 8));
			}
		}

		triangles.add(coordCube.get(coordCube.size()-6));
		triangles.add(coordCube.get(coordCube.size()-5));
		triangles.add(coordCube.get(coordCube.size()-4));

		triangles.add(coordCube.get(coordCube.size()-3));
		triangles.add(coordCube.get(coordCube.size()-2));
		triangles.add(coordCube.get(coordCube.size()-1));

		triangles.add(coordCube.get(0));
		triangles.add(coordCube.get(1));
		triangles.add(coordCube.get(2));

		triangles.add(coordCube.get(coordCube.size()-3));
		triangles.add(coordCube.get(coordCube.size()-2));
		triangles.add(coordCube.get(coordCube.size()-1));

		triangles.add(coordCube.get(3));
		triangles.add(coordCube.get(4));
		triangles.add(coordCube.get(5));

		triangles.add(coordCube.get(0));
		triangles.add(coordCube.get(1));
		triangles.add(coordCube.get(2));


		float x_center_b = 0.0f;
		float z_center_b = 0.0f;
		for (int i = 0; i < segments; i++) {
			triangles.add(x_center_b);
			triangles.add(0.0f);
			triangles.add(z_center_b);

			triangles.add(coordCube.get(i*6));
			triangles.add(coordCube.get(i*6 + 1));
			triangles.add(coordCube.get(i*6 + 2));

			triangles.add(coordCube.get(((i+1) % segments)*6));
			triangles.add(0.0f);
			triangles.add(coordCube.get(((i+1) % segments)*6 + 2));
		}

		float x_center_t = 0.0f;
		float z_center_t = 0.0f;
		for (int i = 0; i < segments; i++) {
			triangles.add(x_center_t);
			triangles.add(height);
			triangles.add(z_center_t);

			triangles.add(coordCube.get(i*6 + 3));
			triangles.add(coordCube.get(i*6 + 4));
			triangles.add(coordCube.get(i*6 + 5));

			triangles.add(coordCube.get(((i+1) % segments)*6 + 3));
			triangles.add(height);
			triangles.add(coordCube.get(((i+1) % segments)*6 + 5));
		}


		float[] vertices = new float[triangles.size()];
		for (int i = 0; i < triangles.size(); i++) {
			vertices[i] = triangles.get(i);
		}
		return vertices;
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

	public static float[] generateRandomColors(int numberOfColors) {
		Random random = new Random();
		float[] colors = new float[numberOfColors * 3];

		for (int i = 0; i < numberOfColors; i++) {
			float r = random.nextFloat();
			float g = random.nextFloat();
			float b = random.nextFloat();

			colors[i * 3] = r;
			colors[i * 3 + 1] = g;
			colors[i * 3 + 2] = b;
		}
		return colors;
	}

	private float[] calculateNormals(float[] vertices) {

		float[] normals = new float[vertices.length];

		int i = 0;
		while(i + 8 < vertices.length){

			float ax = vertices[i];
			float ay = vertices[i + 1];
			float az = vertices[i + 2];

			float bx = vertices[i + 3];
			float by = vertices[i + 4];
			float bz = vertices[i + 5];

			float cx = vertices[i + 6];
			float cy = vertices[i + 7];
			float cz = vertices[i + 8];

			float ux = bx - ax;
			float uy = by - ay;
			float uz = bz - az;

			float vx = cx - ax;
			float vy = cy - ay;
			float vz = cz - az;

			float nx = uy * vz - uz * vy;
			float ny = uz * vx - ux * vz;
			float nz = ux * vy - uy * vx;

			for(int j = 0; j < 3; j++){
				int baseIndex = i + j * 3;
				normals[baseIndex] += nx;
				normals[baseIndex+2] += ny;
				normals[baseIndex+2] += nz;

			}
			i+=9;
		}
		return normals;
	}
}
