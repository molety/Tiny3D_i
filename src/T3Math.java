/**
 * ���w�֐�
 */
public class T3Math
{
	// ��float-->int�̍����ȕϊ��Asin,cos�̃e�[�u������
	//   http://www.yamagami-planning.com/soft/optimize/optimize01/ ���Q�l�ɂ��܂����B
	//   �������̋t���̍�������
	//   http://garugari.jugem.jp/?eid=433
	//   http://niffy-you-nats.hp.infoseek.co.jp/iappli/LunaMath.java ���Q�l�ɂ��܂����B

	/** 2�� */
	public static final float PI2 = (float)(2.0 * Math.PI);
	/** sin�e�[�u���̃T�C�Y */
	private static final int SIN_TABLE_SIZE = 2048;
	/** cos�����߂鎞�̃I�t�Z�b�g */
	private static final int COS_OFFSET = SIN_TABLE_SIZE / 4;
	/** �p�x���C���f�b�N�X�ϊ����̃X�P�[�����O�l */
	private static final float TWO_PI_SCALE = (float)SIN_TABLE_SIZE / PI2;
	/** float-->int�ϊ�����float�ɑ����o�C�A�X */
	private static final float FTOI_BIAS_F = 12582912.0F;
	/** float-->int�ϊ�����int��������o�C�A�X */
	private static final int FTOI_BIAS_I = 0x4b400000;
	/** sin�e�[�u�� */
	private static float sinTable[] = new float[SIN_TABLE_SIZE];
	/** acos�e�[�u���̃T�C�Y */
	private static final int ACOS_TABLE_SIZE = 2048;
	/** acos�̕���\ */
	private static final int ACOS_RESOLUTION = 8192;
	/** acos�e�[�u�� */
	private static float acosTable[] = new float[ACOS_TABLE_SIZE];

	/**
	 * static�C�j�V�����C�U
	 */
	static
	{
		double angle;

		// sin�e�[�u���̏�����
		for (int i = 0; i < SIN_TABLE_SIZE; i++) {
			angle = (double)i / TWO_PI_SCALE;
			sinTable[i] = (float)Math.sin(angle);
		}

		// acos�e�[�u���̏�����
		angle = Math.PI;
		double targetCos;
		for (int i = 0, j = 0; i < ACOS_TABLE_SIZE; i++) {
			targetCos = (double)i / (double)(ACOS_TABLE_SIZE - 1) * 2.0 - 1.0;
			while (Math.cos(angle) < targetCos) {
				angle = (1.0 - (double)j / (double)ACOS_RESOLUTION) * Math.PI;
				if (j >= ACOS_RESOLUTION) {
					break;			// ������ɂ����Z�덷�Ŗ������[�v�ɂȂ�Ȃ��悤�ɂ��Ă���
				}
				j++;
			}
			acosTable[i] = (float)angle;
//			System.out.println(angle);
		}
	}

	/**
	 * sine
	 * @param angle (IN ) �p�x (���W�A��)
	 * @return sine�l
	 */
	public static float fsin(float angle)
	{
		return sinTable[Float.floatToIntBits(angle * TWO_PI_SCALE + FTOI_BIAS_F)
						& (SIN_TABLE_SIZE - 1)];
	}

	/**
	 * cosine
	 * @param angle (IN ) �p�x (���W�A��)
	 * @return cosine�l
	 */
	public static float fcos(float angle)
	{
		return sinTable[(Float.floatToIntBits(angle * TWO_PI_SCALE + FTOI_BIAS_F) + COS_OFFSET)
						& (SIN_TABLE_SIZE - 1)];
	}

	/**
	 * arc cosine
	 * @param x (IN ) cosine�l (-1 <= x <= 1)
	 * @return �p�x (0�`�΃��W�A��)
	 */
	public static float facos(float x)
	{
		return acosTable[Float.floatToIntBits((x + 1.0F) * 0.5F * (ACOS_TABLE_SIZE - 1) + FTOI_BIAS_F)
						 & (ACOS_TABLE_SIZE - 1)];
	}

	/**
	 * float����int�ւ̕ϊ�
	 * @param x (IN ) float�l (-2^22 <= x <= 2^22)
	 * @return int�l
	 */
	public static int floatToInt(float x)
	{
		return (Float.floatToIntBits(x + FTOI_BIAS_F) - FTOI_BIAS_I);
	}

	/**
	 * �ׂ���
	 * @param base     (IN ) ��
	 * @param exponent (IN ) �w��
	 * @return �ׂ���l
	 */
	public static float power(float base, int exponent)
	{
		float pow = 1.0F;
		boolean doInvert = false;

		if (exponent < 0) {
			exponent = -exponent;
			doInvert = true;
		}

		while (exponent > 0) {
			if ((exponent & 1) == 1) {
				pow *= base;
			}
			base *= base;
			exponent >>= 1;
		}

		if (doInvert) {
			pow = 1.0F / pow;
		}

		return pow;
	}

	/**
	 * �������̋t��
	 * @param x (IN ) ���̒l
	 * @return �������̋t��
	 */
	public static float invSqrt(float x)
	{
		float xhalf = 0.5F * x;

		x = Float.intBitsToFloat(0x5f3759df - (Float.floatToIntBits(x) >> 1));

		return x * (1.5F - xhalf * x * x);
	}
}
