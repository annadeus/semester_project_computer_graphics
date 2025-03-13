package projekt;

//Alle Operationen aendern das Matrixobjekt selbst und geben das eigene Matrixobjekt zurueck
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);

public class Matrix4 {

	// float[][] values = new float[4][4];
	float[] values; // --> spaltenweise schreiben/betrachten (nach unten +1, nach rechts +4)

	public Matrix4() { // Constructor
		// TODO mit der Identitaetsmatrix initialisieren --> Einsen auf der Hauptdiagonalen
		values = new float[]
				{
						1f, 0f, 0f, 0f,
						0f, 1f, 0f, 0f,
						0f, 0f, 1f, 0f,
						0f, 0f, 0f, 1f
				};
	}

	public Matrix4(Matrix4 copy) { // Copy-Constructor
		// TODO neues Objekt mit den Werten von "copy" initialisieren --> kann erstmal weggelassen werden (Copy-Constructor in C++)
	}

	public Matrix4(float near, float far, float width, float height) { // Matrix für perspektivische Projektion (siehe Folie 137)
		// TODO erzeugt Projektionsmatrix mit Abstand zur nahen Ebene "near" (d) und Abstand zur fernen Ebene "far" (f), ggf. weitere Parameter hinzufuegen
		values = new float[] // Projektionsmatrix
				{
						((2*near)/width), 0f, 0f, 0f,
						0f, ((2*near)/height), 0f, 0f,
						0f, 0f, ((-far-near)/(far-near)), -1f,
						0f, 0f, ((-2*near*far)/(far-near)), 0f
				};
	}

	public Matrix4 multiply(Matrix4 other) { // Matrizenmultiplikation --> wichtigste
		// TODO hier Matrizenmultiplikation "this = other * this" einfuegen
		// Matrix links an 'this' ranfügen
		// das Ergebnis wieder in eine Matrix reinschreiben --> in die 'this'-Matrix schreiben
		float[] result = new float[values.length];

		for(int column=0; column<4; column++){
			for(int row=0; row<4; row++){
				result[column*4+row] = // index --> aktuelle Stelle von 0 (0*4+0) bis 15 (3*4+3)
						// geg.: column=0, row=0
						other.values[row] * this.values[column*4] + //c: 0, r: 0
						other.values[row+4] * this.values[column*4+1] + //c: 4, r: 1
						other.values[row+8] * this.values[column*4+2] + //c: 8, r: 2
						other.values[row+12] * this.values[column*4+3];  //c: 12, r: 3
			}
		}
		this.values = result; // Ergebnis in 'this' überschreiben
		return this;
	}

	// um die float[]-Matrizen als ein Matrix4-Objekt übergeben zu können
	public static Matrix4 fromValues(float[] values) {
		Matrix4 matrix = new Matrix4();
		matrix.values = values;
		return matrix;
	}

	public Matrix4 translate(float x, float y, float z) { // Matrix anlegen und 'multiply' aufrufen
		// TODO Verschiebung um x,y,z zu this hinzufuegen
		float[] translate = new float[]
				{
						1f, 0f, 0f, 0f,
						0f, 1f, 0f, 0f,
						0f, 0f, 1f, 0f,
						x, y, z, 1f,
				};

		Matrix4 translateMatrix = new Matrix4();
		translateMatrix.values = translate;
		this.multiply(translateMatrix);
		return this;
	}

	public Matrix4 scale(float uniformFactor) { // Matrix anlegen und 'multiply' aufrufen
		// TODO gleichmaessige Skalierung um Faktor "uniformFactor" zu this hinzufuegen
		float[] uni_scale = new float[]
				{
						uniformFactor, 0f, 0f, 0f,
						0f, uniformFactor, 0f, 0f,
						0f, 0f, uniformFactor, 0f,
						0f, 0f, 0f, 1f
				};

		Matrix4 uni_scaleMatrix = Matrix4.fromValues(uni_scale);
		return this.multiply(uni_scaleMatrix);
	}

	public Matrix4 scale(float sx, float sy, float sz) { // Matrix anlegen und 'multiply' aufrufen
		// TODO ungleichfoermige Skalierung zu this hinzufuegen
		float[] scale = new float[]
				{
						sx, 0f, 0f, 0f,
						0f, sy, 0f, 0f,
						0f, 0f, sz, 0f,
						0f, 0f, 0f, 1f
				};

		Matrix4 scaleMatrix = Matrix4.fromValues(scale);
		return this.multiply(scaleMatrix);
	}

	public Matrix4 rotateX(float angle) { // Matrix anlegen und 'multiply' aufrufen
		// TODO Rotation um X-Achse zu this hinzufuegen
		float[] rotation_x = new float[]
				{
						1f, 0f, 0f, 0f,
						0f, (float) Math.cos(angle), (float) Math.sin(angle), 0f,
						0f, (float) -(Math.sin(angle)), (float) Math.cos(angle), 0f,
						0f, 0f, 0f, 1f,
				};

		Matrix4 x_rotateMatrix = Matrix4.fromValues(rotation_x);
		return this.multiply(x_rotateMatrix);
	}

	public Matrix4 rotateY(float angle) { // Matrix anlegen und 'multiply' aufrufen
		// TODO Rotation um Y-Achse zu this hinzufuegen
		float[] rotation_y = new float[]
				{
						(float) Math.cos(angle), 0f, (float) Math.sin(angle), 0f,
						0f, 1f, 0f, 0f,
						(float) -(Math.sin(angle)), 0f, (float) Math.cos(angle), 0f,
						0f, 0f, 0f, 1f,
				};

		Matrix4 y_rotateMatrix = new Matrix4();
		y_rotateMatrix.values = rotation_y;
		this.multiply(y_rotateMatrix);
		return this;
	}

	public Matrix4 rotateZ(float angle) { // Matrix anlegen und 'multiply' aufrufen
		// TODO Rotation um Z-Achse zu this hinzufuegen
		float[] rotation_z = new float[]
				{
						(float) Math.cos(angle), (float) Math.sin(angle), 0f, 0f,
						(float) -(Math.sin(angle)), (float) Math.cos(angle), 0f, 0f,
						0f, 0f, 1f, 0f,
						0f, 0f, 0f, 1f,
				};

		Matrix4 z_rotateMatrix = Matrix4.fromValues(rotation_z);
		return this.multiply(z_rotateMatrix);
	}

	public float[] getValuesAsArray() { // Werte der Matrix als 1D-Array zurückgeben --> float[] values;
		// TODO hier Werte in einem Float-Array mit 16 Elementen (spaltenweise gefuellt) herausgeben
		return values;
	}
}
