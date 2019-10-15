/**
 * ���f�� (����)
 */
public class T3Model extends T3Object
{
	/** adjustCenter()�p�e���|���� */
	private static T3Vector tempCenter = new T3Vector();

	/** ���_�� */
	private int numOfVertex;
	/** ���_�z�� */
	private T3Vector[] vertex;
	/** ���f�����O-�r���[�C���O�ϊ���̒��_�z�� */
	private T3Vector[] transformedVertex;

	/** �ʐ� */
	private int numOfFace;
	/** �� */
	private T3Face[] face;

	/**
	 * �R���X�g���N�^ (�ʐF�w��)
	 * @param nVertex (IN ) ���_��
	 * @param nFace   (IN ) �ʐ�
	 * @param vertex  (IN ) ���_���
	 * @param index   (IN ) �e�ʂ��\�����钸�_�̃C���f�b�N�X
	 * @param color   (IN ) �F���
	 */
	public T3Model(int nVertex, int nFace, float[][] vertex, int[][] index, float[][] color)
	{
		int i;

		numOfVertex = nVertex;
		numOfFace = nFace;

		setVertex(vertex);

		face = new T3Face[numOfFace];
		for (i = 0; i < numOfFace; i++) {
			face[i] = new T3Face(transformedVertex, index[i], color[i]);
		}
	}

	/**
	 * �R���X�g���N�^ (�ꊇ�F�w��)
	 * @param nVertex (IN ) ���_��
	 * @param nFace   (IN ) �ʐ�
	 * @param vertex  (IN ) ���_���
	 * @param index   (IN ) �e�ʂ��\�����钸�_�̃C���f�b�N�X
	 * @param color   (IN ) �F���
	 */
	public T3Model(int nVertex, int nFace, float[][] vertex, int[][] index, float[] color)
	{
		int i;

		numOfVertex = nVertex;
		numOfFace = nFace;

		setVertex(vertex);

		face = new T3Face[numOfFace];
		for (i = 0; i < numOfFace; i++) {
			face[i] = new T3Face(transformedVertex, index[i], color);
		}
	}

	/**
	 * ���_�z���ݒ肷��B
	 * �R���X�g���N�^�̉�����
	 * @param vertex (IN ) ���_���
	 */
	private void setVertex(float[][] vertex)
	{
		int i;
		float[] xyz;

		this.vertex = new T3Vector[numOfVertex];
		this.transformedVertex = new T3Vector[numOfVertex];
		for (i = 0; i < numOfVertex; i++) {
			xyz = vertex[i];
			this.vertex[i] = new T3Vector(xyz[0], xyz[1], xyz[2]);
			this.transformedVertex[i] = new T3Vector();
		}
	}

	/**
	 * ���[���h���ɔz�u����B
	 * @param world      (IN ) ���[���h�I�u�W�F�N�g
	 * @param doCullBack (IN ) �������̖ʂ��J�����O���邩
	 * @param doCullNear (IN ) ���e�ʂ��߂��ɂ���ʂ��J�����O���邩
	 */
	public void place(T3World world, boolean doCullBack, boolean doCullNear)
	{
		int i;

		super.place(world);

		for (i = 0; i < numOfVertex; i++) {
			T3Matrix.multVector(transformedVertex[i], world.modelView, vertex[i]);
		}

		for (i = 0; i < numOfFace; i++) {
			face[i].place(world, doCullBack, doCullNear);
		}
	}

	/**
	 * ���̂̏d�S�����_�ɗ���悤�ɕ␳����B
	 */
	public void adjustCenter()
	{
		int i;

		tempCenter.set(0.0F, 0.0F, 0.0F);
		for (i = 0; i < numOfVertex; i++) {
			tempCenter.add(vertex[i]);
		}
		for (i = 0; i < 3; i++) {
			tempCenter.multScalar(1.0F / (float)numOfVertex);
		}
		for (i = 0; i < numOfVertex; i++) {
			vertex[i].sub(tempCenter);
		}
	}
}
