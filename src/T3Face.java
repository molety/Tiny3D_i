/**
 * �� (�|���S��)
 */
public class T3Face
{
	/** �ʖ@���x�N�g���v�Z�p */
	private static T3Vector tempV01 = new T3Vector();
	/** �ʖ@���x�N�g���v�Z�p */
	private static T3Vector tempV12 = new T3Vector();

	/** ���_�� */
	public int numOfVertex;
	/** ���_���̋t�� */
	public float invNum;

	/** ���_�z�� */
	public T3Vector[] vertex;

	/** �ʖ@���x�N�g�� */
	public T3Vector normal;
	/** �d�S */
	public T3Vector center;
	/** �d�S��z���W */
	public float centerZ;

	/** �����ɑ΂��锽�ˌW�� */
	public float[] ambient;
	/** �g�U���ˌW�� */
	public float[] diffuse;
	/** ���ʔ��ˌW�� */
	public float[] specular;
	/** �P���W�� */
	public int shininess;
	/** �����_�����O���ꂽ�F */
	public int[] renderedColor;

	/**
	 * �R���X�g���N�^
	 * @param vertex (IN ) ���_�z��
	 * @param index  (IN ) ���_�z��̒��ł��̖ʂ��\�����钸�_�̃C���f�b�N�X
	 * @param color  (IN ) �F���
	 */
	public T3Face(T3Vector[] vertex, int[] index, float[] color)
	{
		int i;

		numOfVertex = index.length;
		invNum = 1.0F / (float)numOfVertex;

		this.vertex = new T3Vector[numOfVertex];
		for (i = 0; i < numOfVertex; i++) {
			this.vertex[i] = vertex[index[i]];
		}

		normal = new T3Vector();
		center = new T3Vector();

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
		shininess = (int)color[9];
		renderedColor = new int[3];
	}

	/**
	 * ���[���h���ɔz�u����B
	 * @param world      (IN ) ���[���h�I�u�W�F�N�g
	 * @param doCullBack (IN ) �������̖ʂ��J�����O���邩
	 * @param doCullNear (IN ) ���e�ʂ��߂��ɂ���ʂ��J�����O���邩
	 */
	public void place(T3World world, boolean doCullBack, boolean doCullNear)
	{
		boolean visible = true;

		calcNormal();
		calcCenter();

		if (doCullBack) {
			// �ʖ@���x�N�g���̌����ɂ��J�����O
			// �ʖ@���x�N�g����z���������̖ʂ͌����Ȃ��Ƃ���B
			if (normal.z() < 0.0F) {
				visible = false;
			}
		}

		if (doCullNear) {
			// z���W�ɂ��J�����O
			// centerZ > windowDepth�̖ʂ͌����Ȃ��Ƃ���B
			if (centerZ > world.projection.windowDepth) {
				visible = false;
			}
		}

		if (visible) {
			world.addFace(this);
		}
	}

	/**
	 * �ʖ@���x�N�g�������߂�B
	 */
	private void calcNormal()
	{
		// �O�p�`��2�ӂ����߂�
		T3Vector.diff(tempV01, vertex[1], vertex[0]);
		T3Vector.diff(tempV12, vertex[2], vertex[1]);

		// �ʖ@���x�N�g�������߁A���K������
		T3Vector.outerProduct(normal, tempV01, tempV12);
		normal.normalize();
	}

	/**
	 * �d�S�����߂�B
	 */
	private void calcCenter()
	{
		float x = 0;
		float y = 0;
		float z = 0;
		int i;

		for (i = 0; i < numOfVertex; i++) {
			x += vertex[i].x();
			y += vertex[i].y();
			z += vertex[i].z();
		}
		x *= invNum;
		y *= invNum;
		z *= invNum;

		center.set(x, y, z);
		centerZ = z;
	}
}
