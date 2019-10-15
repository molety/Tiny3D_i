/**
 * ����
 */
public class T3Light extends T3Object
{
	/** �����x�N�g�� */
	private static T3Vector lightV = new T3Vector();
	/** �����x�N�g�� */
	private static T3Vector viewV = new T3Vector();
	/** �n�[�t�E�F�C�x�N�g�� */
	private static T3Vector halfwayV = new T3Vector();

	/** ���s���� */
	public static final int DIRECTIONAL = 0;
	/** �_���� */
	public static final int POINT = 1;
	/** ���[�h */
	private int mode = DIRECTIONAL;

	/** �Ǝ˕��� (���s����) */
	private T3Vector direction;
	/** �ʒu (�_����) / ���̌��_ (���s����) */
	private T3Vector position;
	/** ���f�����O-�r���[�C���O�ϊ���̏Ǝ˕��� */
	private T3Vector transformedDirection;
	/** ���f�����O-�r���[�C���O�ϊ���̈ʒu */
	private T3Vector transformedPosition;

	/** �������� */
	private float[] ambient;
	/** �g�U���ː��� */
	private float[] diffuse;
	/** ���ʔ��ː��� */
	private float[] specular;
	/** �������̒萔�� */
	private float constantAttenuation;
	/** ��������1���̍� */
	private float linearAttenuation;
	/** ��������2���̍� */
	private float quadraticAttenuation;
	/** �������v�Z���邩 */
	private boolean doAmbient;
	/** �g�U���ː������v�Z���邩 */
	private boolean doDiffuse;
	/** ���ʔ��ː������v�Z���邩 */
	private boolean doSpecular;

	/**
	 * �R���X�g���N�^
	 * @param mode   (IN ) ���[�h
	 * @param vector (IN ) �Ǝ˕��� (���s����) / �ʒu (�_����)
	 * @param color  (IN ) �F���
	 */
	public T3Light(int mode, float[] vector, float[] color)
	{
		int i;

		this.mode = mode;

		direction = new T3Vector();
		position = new T3Vector();
		transformedDirection = new T3Vector();
		transformedPosition = new T3Vector();

		switch (this.mode) {
		  case DIRECTIONAL:
			direction.set(vector[0], vector[1], vector[2]);
			position.set(0.0F, 0.0F, 0.0F);
			break;
		  case POINT:
			position.set(vector[0], vector[1], vector[2]);
			break;
		}

		specular = new float[3];
		diffuse = new float[3];
		ambient = new float[3];
		ambient[0] = color[0];
		ambient[1] = color[1];
		ambient[2] = color[2];
		diffuse[0] = color[3];
		diffuse[1] = color[4];
		diffuse[2] = color[5];
		specular[0] = color[6];
		specular[1] = color[7];
		specular[2] = color[8];
		constantAttenuation = color[9];
		linearAttenuation = color[10];
		quadraticAttenuation = color[11];
		doAmbient = (ambient[0] + ambient[1] + ambient[2] > 0.0F);
		doDiffuse = (diffuse[0] + diffuse[1] + diffuse[2] > 0.0F);
		doSpecular = (specular[0] + specular[1] + specular[2] > 0.0F);
	}

	/**
	 * ���[���h���ɔz�u����B
	 * @param world (IN ) ���[���h�I�u�W�F�N�g
	 */
	public void place(T3World world)
	{
		super.place(world);

		T3Matrix.multVector(transformedDirection, world.modelView, direction);
		T3Matrix.multVector(transformedPosition, world.modelView, position);
	}

	/**
	 * �F�����߂�B
	 * @param color (I/O) �F(���͒l�ɉ��Z���ďo�͂���)
	 * @param face  (IN ) ��
	 */
	public void calcColor(float[] color, T3Face face)
	{
		// �� http://marina.sys.wakayama-u.ac.jp/~tokoi/?date=20051007
		//    http://marupeke296.com/FBX_No7_UV.html ���Q�l�ɂ��܂����B

		int i;
		float ratio;
		float attenuation = 1.0F;
		float distance;

		switch (mode) {
		  case DIRECTIONAL:
			// �����x�N�g���͌����֌������x�N�g���Ƃ���B
			T3Vector.diff(lightV, transformedPosition, transformedDirection);
			lightV.normalize();
			attenuation = 1.0F;
			break;
		  case POINT:
			// �����x�N�g���͖ʂ̏d�S��������֌������x�N�g���Ƃ���B
			T3Vector.diff(lightV, transformedPosition, face.center);
			distance = lightV.norm();
			lightV.normalize();
			attenuation = 1.0F / (constantAttenuation
								  + linearAttenuation * distance
								  + quadraticAttenuation * distance * distance);
			break;
		}

		// ����
		if (doAmbient) {
			for (i = 0; i < 3; i++) {
				color[i] += ambient[i] * face.ambient[i] * attenuation;
			}
		}

		// �g�U���ˌ�
		if (doDiffuse) {
			ratio = Math.max(T3Vector.innerProduct(lightV, face.normal), 0.0F);
			for (i = 0; i < 3; i++) {
				color[i] += diffuse[i] * face.diffuse[i] * ratio * attenuation;
			}
		}

		// ���ʔ��ˌ�
		if (doSpecular) {
			// �����x�N�g���͖ʂ̏d�S���王�_(=���_)�֌������x�N�g���Ƃ���B
			T3Vector.makeInverse(viewV, face.center);
			viewV.normalize();
			T3Vector.sum(halfwayV, lightV, viewV);
			halfwayV.normalize();
			ratio = Math.max(T3Vector.innerProduct(halfwayV, face.normal), 0.0F);
			ratio = T3Math.power(ratio, face.shininess);
			for (i = 0; i < 3; i++) {
				color[i] += specular[i] * face.specular[i] * ratio * attenuation;
			}
		}
	}
}
