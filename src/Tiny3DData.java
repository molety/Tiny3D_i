/**
 * �f�[�^��`
 */
public class Tiny3DData
{
	/**
	 * ���f��1(����\�ʑ�)�𐶐�����B
	 * @return �����������f��
	 */
	public T3Model generateModel1()
	{
		return new T3Model(12, 20, vertex1, index1, faceColor1);
	}

	/** ���_��� */
	private float[][] vertex1 = {
		{-0.27F,   0.0F,  0.43F},
		{ 0.27F,   0.0F,  0.43F},
		{-0.27F,   0.0F, -0.43F},
		{ 0.27F,   0.0F, -0.43F},
		{  0.0F,  0.43F,  0.27F},
		{  0.0F,  0.43F, -0.27F},
		{  0.0F, -0.43F,  0.27F},
		{  0.0F, -0.43F, -0.27F},
		{ 0.43F,  0.27F,   0.0F},
		{-0.43F,  0.27F,   0.0F},
		{ 0.43F, -0.27F,   0.0F},
		{-0.43F, -0.27F,   0.0F}
	};

	/** �ʑ���� */
	private int[][] index1 = {
		{ 0,  1,  4},
		{ 0,  4,  9},
		{ 9,  4,  5},
		{ 4,  8,  5},
		{ 4,  1,  8},
		{ 8,  1, 10},
		{ 8, 10,  3},
		{ 5,  8,  3},
		{ 5,  3,  2},
		{ 2,  3,  7},
		{ 7,  3, 10},
		{ 7, 10,  6},
		{ 7,  6, 11},
		{11,  6,  0},
		{ 0,  6,  1},
		{ 6, 10,  1},
		{ 9, 11,  0},
		{ 9,  2, 11},
		{ 9,  5,  2},
		{ 7, 11,  2}
	};

	/** �F��� */
	private float[][] faceColor1 = {
		// Ra    Ga    Ba    Rd    Gd    Bd    Rs    Gs    Bs    Sh
		{0.1F, 0.1F, 0.1F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.0F, 0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.5F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.0F, 0.5F, 0.5F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.5F, 0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.1F, 0.1F, 0.1F, 0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F}
	};

	/**
	 * ���f��2(������)�𐶐�����B
	 * @return �����������f��
	 */
	public T3Model generateModel2()
	{
		return new T3Model(8, 6, vertex2, index2, faceColor2);
	}

	/** ���_��� */
	private float[][] vertex2 = {
		{-0.5F,  0.5F,  0.5F},
		{-0.5F, -0.5F,  0.5F},
		{ 0.5F, -0.5F,  0.5F},
		{ 0.5F,  0.5F,  0.5F},
		{-0.5F,  0.5F, -0.5F},
		{-0.5F, -0.5F, -0.5F},
		{ 0.5F, -0.5F, -0.5F},
		{ 0.5F,  0.5F, -0.5F}
	};

	/** �ʑ���� */
	private int[][] index2 = {
		{0, 1, 2, 3},
		{4, 5, 1, 0},
		{7, 6, 5, 4},
		{3, 2, 6, 7},
		{4, 0, 3, 7},
		{1, 5, 6, 2}
	};

	/** �F��� */
	private float[][] faceColor2 = {
		// Ra    Ga    Ba    Rd    Gd    Bd    Rs    Gs    Bs    Sh
		{0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F},
		{0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F}
	};

	/**
	 * ���f��3(�����ʑ�)�𐶐�����B
	 * @return �����������f��
	 */
	public T3Model generateModel3()
	{
		return new T3Model(6, 8, vertex3, index3, faceColor3);
	}

	/** ���_��� */
	// �킴�Əd�S�����_���炸�炵�Ă���B
	private float[][] vertex3 = {
		{ 1.0F,  0.5F,  0.5F},
		{ 0.0F,  0.5F,  0.5F},
		{ 0.5F,  1.0F,  0.5F},
		{ 0.5F,  0.0F,  0.5F},
		{ 0.5F,  0.5F,  1.0F},
		{ 0.5F,  0.5F,  0.0F}
	};

	/** �ʑ���� */
	private int[][] index3 = {
		{0, 2, 4},
		{2, 1, 4},
		{1, 3, 4},
		{3, 0, 4},
		{0, 3, 5},
		{3, 1, 5},
		{1, 2, 5},
		{2, 0, 5}
	};

	/** �F��� */
	private float[] faceColor3 = {
		//Ra    Ga    Ba    Rd    Gd    Bd    Rs    Gs    Bs    Sh
		0.2F, 0.2F, 0.2F, 0.3F, 0.3F, 0.3F, 0.9F, 0.9F, 0.9F, 5.0F
	  };

	/**
	 * ����1(���s����)�𐶐�����B
	 * @return ������������
	 */
	public T3Light generateLight1()
	{
		return new T3Light(T3Light.DIRECTIONAL, lightDir1, lightColor1);
	}

	/** �Ǝ˕��� */
	private float[] lightDir1 = {3.0F, -0.5F, -1.0F};

	/** �F��� */
/*	private float[] lightColor1 = {
		//  Ra      Ga      Ba      Rd      Gd      Bd      Rs      Gs      Bs
		200.0F, 200.0F, 200.0F, 200.0F, 200.0F, 200.0F, 200.0F, 200.0F, 200.0F,
		//Ca    La    Qa
		1.0F, 0.0F, 0.0F
	  };
*/
	/** �F��� */
	private float[] lightColor1 = {
		//  Ra      Ga      Ba      Rd      Gd      Bd      Rs      Gs      Bs
		200.0F, 200.0F, 200.0F, 200.0F, 200.0F, 200.0F,   0.0F,   0.0F,   0.0F,
		//Ca    La    Qa
		1.0F, 0.0F, 0.0F
	  };


	/**
	 * ����2�𐶐�����B
	 * @return ������������
	 */
	public T3Light generateLight2()
	{
//		return new T3Light(T3Light.POINT, lightPos2, lightColor2);
		return new T3Light(T3Light.DIRECTIONAL, lightDir2, lightColor2);
	}

	/** �ʒu */
//	private float[] lightPos2 = {3.0F, -0.5F, 1.0F};
	/** �Ǝ˕��� */
	private float[] lightDir2 = {-3.0F, 0.5F, -1.0F};

	/** �F��� */
/*	private float[] lightColor2 = {
		//  Ra      Ga      Ba      Rd      Gd      Bd      Rs      Gs      Bs
		255.0F, 100.0F, 100.0F, 255.0F, 100.0F, 100.0F, 255.0F, 100.0F, 100.0F,
		//Ca    La    Qa
		1.0F, 0.1F, 0.0F
	  };
*/
	/** �F��� */
	private float[] lightColor2 = {
		//  Ra      Ga      Ba      Rd      Gd      Bd      Rs      Gs      Bs
		255.0F, 100.0F, 100.0F, 255.0F, 100.0F, 100.0F,   0.0F,   0.0F,   0.0F,
		//Ca    La    Qa
		1.0F, 0.1F, 0.0F
	  };

}
