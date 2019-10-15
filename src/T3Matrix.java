/**
 * �s��
 */
public class T3Matrix
{
	/** �v�Z�p�o�b�t�@ */
	private static T3Matrix tempM = new T3Matrix();

	/** �v�f */
	public float[][] elem = new float[4][4];

	/**
	 * �R���X�g���N�^
	 */
	public T3Matrix()
	{
		setIdentity();
	}

	/**
	 * �P�ʍs���ݒ肷��B
	 * @return �ł����P�ʍs��
	 */
	public T3Matrix setIdentity()
	{
		elem[0][0] = 1.0F; elem[0][1] = 0.0F; elem[0][2] = 0.0F; elem[0][3] = 0.0F;
		elem[1][0] = 0.0F; elem[1][1] = 1.0F; elem[1][2] = 0.0F; elem[1][3] = 0.0F;
		elem[2][0] = 0.0F; elem[2][1] = 0.0F; elem[2][2] = 1.0F; elem[2][3] = 0.0F;
		elem[3][0] = 0.0F; elem[3][1] = 0.0F; elem[3][2] = 0.0F; elem[3][3] = 1.0F;

		return this;
	}

	/**
	 * �s��̑S�v�f���R�s�[����B
	 * @param dst (OUT) �R�s�[��
	 * @param src (IN ) �R�s�[��
	 * @return �R�s�[��
	 */
	public static T3Matrix copy(T3Matrix dst, T3Matrix src)
	{
		float[][] a = src.elem;
		float[][] b = dst.elem;
		int i, j;

		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				b[i][j] = a[i][j];
			}
		}

		return dst;
	}

	/**
	 * �]�u����B
	 * @return �]�u����
	 */
	public T3Matrix transpose()
	{
		float[][] a = this.elem;
		int i, j;
		float temp;

		for (i = 1; i < 4; i++) {
			for (j = 0; j < i; j++) {
				temp = a[i][j];
				a[i][j] = a[j][i];
				a[j][i] = temp;
			}
		}

		return this;
	}

	/**
	 * �]�u�s������߂�B
	 * @param transposed (OUT) �]�u����
	 * @param src        (IN ) ���̍s��
	 * @return �]�u����
	 */
	public static T3Matrix makeTransposed(T3Matrix transposed, T3Matrix src)
	{
		float[][] a = src.elem;
		float[][] b = transposed.elem;
		int i, j;

		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				b[j][i] = a[i][j];
			}
		}

		return transposed;
	}

	/**
	 * 2�̍s�����������B
	 * @param a (I/O) 1�ڂ̍s��
	 * @param b (I/O) 2�ڂ̍s��
	 */
	public static void swap(T3Matrix a, T3Matrix b)
	{
		// �t�B�[���h(�����o�ϐ�)�̒��g�����ւ���Ƃ����A���܂��Y��łȂ����@�Ŏ������Ă���B
		// �����t�B�[���h���������ꍇ�́A����������ւ����K�v�B
		float[][] tempElem = a.elem;
		a.elem = b.elem;
		b.elem = tempElem;
	}

	/**
	 * 2�̍s��̐ς����߂�B
	 * @param product      (OUT) ��
	 * @param multiplicand (IN ) �|������s�� (��)
	 * @param multiplier   (IN ) �|����s�� (�E)
	 * @return ��
	 */
	public static T3Matrix multMatrix(T3Matrix product, T3Matrix multiplicand, T3Matrix multiplier)
	{
		float[][] a = multiplicand.elem;
		float[][] b = multiplier.elem;
		float[][] c = product.elem;
		float sum;
		int i, j, k;

		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				sum = 0.0F;
				for (k = 0; k < 4; k++) {
					sum += a[i][k] * b[k][j];
				}
				c[i][j] = sum;
			}
		}

		return product;
	}

	/**
	 * �s��ɍ�����ʂ̍s����|����B
	 * @param other (IN ) ������|����s��
	 * @return �� (�������g)
	 */
	public T3Matrix multFromLeft(T3Matrix other)
	{
		T3Matrix.multMatrix(tempM, other, this);
		T3Matrix.swap(this, tempM);

		return this;
	}

	/**
	 * �s��ɉE����ʂ̍s����|����B
	 * @param other (IN ) �E����|����s��
	 * @return �� (�������g)
	 */
	public T3Matrix multFromRight(T3Matrix other)
	{
		T3Matrix.multMatrix(tempM, this, other);
		T3Matrix.swap(this, tempM);

		return this;
	}

	/**
	 * �s��ƃx�N�g���̐ς����߂�B
	 * @param product      (OUT) �ς̃x�N�g��
	 * @param multiplicand (IN ) �s��
	 * @param multiplier   (IN ) �|����x�N�g��
	 * @return �ς̃x�N�g��
	 */
	public static T3Vector multVector(T3Vector product, T3Matrix multiplicand, T3Vector multiplier)
	{
		float[][] a = multiplicand.elem;
		float[] b = multiplier.elem;
		float[] c = product.elem;
		float sum;
		int i, j;

		for (i = 0; i < 4; i++) {
			sum = 0.0F;
			for (j = 0; j < 4; j++) {
				sum += a[i][j] * b[j];
			}
			c[i] = sum;
		}

		return product;
	}

	/**
	 * ���s�ړ��s��ɂ���B
	 * @param v (IN ) ���s�ړ�������\���x�N�g��
	 * @return ���� (�������g)
	 */
	public T3Matrix setTranslate(T3Vector v)
	{
		setIdentity();

		elem[0][3] = v.x();
		elem[1][3] = v.y();
		elem[2][3] = v.z();

		return this;
	}

	/**
	 * ���s�ړ��s��ɂ���B
	 * @param x (IN ) x����
	 * @param y (IN ) y����
	 * @param z (IN ) z����
	 * @return ���� (�������g)
	 */
	public T3Matrix setTranslate(float x, float y, float z)
	{
		setIdentity();

		elem[0][3] = x;
		elem[1][3] = y;
		elem[2][3] = z;

		return this;
	}

	/**
	 * �X�P�[�����O�s��ɂ���B
	 * @param v (IN ) �X�P�[�����O�{����\���x�N�g��
	 * @return ���� (�������g)
	 */
	public T3Matrix setScale(T3Vector v)
	{
		setIdentity();

		elem[0][0] = v.x();
		elem[1][1] = v.y();
		elem[2][2] = v.z();

		return this;
	}

	/**
	 * �X�P�[�����O�s��ɂ���B
	 * @param scaleX (IN ) x�����̔{��
	 * @param scaleY (IN ) y�����̔{��
	 * @param scaleZ (IN ) z�����̔{��
	 * @return ���� (�������g)
	 */
	public T3Matrix setScale(float scaleX, float scaleY, float scaleZ)
	{
		setIdentity();

		elem[0][0] = scaleX;
		elem[1][1] = scaleY;
		elem[2][2] = scaleZ;

		return this;
	}

	/**
	 * x������̉�]�s��ɂ���B
	 * @param angle (IN ) ��]�p (���W�A��)
	 * @return ���� (�������g)
	 */
	public T3Matrix setRotateX(float angle)
	{
//		float cosine = (float)Math.cos(angle);
//		float sine = (float)Math.sin(angle);
		float cosine = T3Math.fcos(angle);
		float sine = T3Math.fsin(angle);

		setIdentity();

		elem[1][1] = cosine;
		elem[1][2] = -sine;
		elem[2][1] = sine;
		elem[2][2] = cosine;

		return this;
	}

	/**
	 * y������̉�]�s��ɂ���B
	 * @param angle (IN ) ��]�p (���W�A��)
	 * @return ���� (�������g)
	 */
	public T3Matrix setRotateY(float angle)
	{
//		float cosine = (float)Math.cos(angle);
//		float sine = (float)Math.sin(angle);
		float cosine = T3Math.fcos(angle);
		float sine = T3Math.fsin(angle);

		setIdentity();

		elem[2][2] = cosine;
		elem[2][0] = -sine;
		elem[0][2] = sine;
		elem[0][0] = cosine;

		return this;
	}

	/**
	 * z������̉�]�s��ɂ���B
	 * @param angle (IN ) ��]�p (���W�A��)
	 * @return ���� (�������g)
	 */
	public T3Matrix setRotateZ(float angle)
	{
//		float cosine = (float)Math.cos(angle);
//		float sine = (float)Math.sin(angle);
		float cosine = T3Math.fcos(angle);
		float sine = T3Math.fsin(angle);

		setIdentity();

		elem[0][0] = cosine;
		elem[0][1] = -sine;
		elem[1][0] = sine;
		elem[1][1] = cosine;

		return this;
	}

	/**
	 * ��]�s��ɂ���B
	 * ��axis�͐��K������Ă���K�v������B
	 * @param angle (IN ) ��]�p(���W�A��)
	 * @param axis  (IN ) ��]���x�N�g��
	 * @return ���� (�������g)
	 */
	public T3Matrix setRotate(float angle, T3Vector axis)
	{
		// ���N�H�[�^�j�I�������]�s������Ƃ����
		//   �u3D-CG�v���O���}�[�̂��߂̎��H�N�H�[�^�j�I���v���Q�l�ɂ��Ă��܂����A
		//   ���̂܂܂��Ɖ�]�̌������t�ɂȂ����̂ŏC�����Ă��܂��B

//		float cos_half_angle = (float)Math.cos(angle * 0.5F);
//		float sin_half_angle = (float)Math.sin(angle * 0.5F);
		float cos_half_angle = T3Math.fcos(angle * 0.5F);
		float sin_half_angle = T3Math.fsin(angle * 0.5F);

		float s = cos_half_angle;
		float t = axis.x() * sin_half_angle;
		float u = axis.y() * sin_half_angle;
		float v = axis.z() * sin_half_angle;

		float d_t2 = 2.0F * t * t;
		float d_u2 = 2.0F * u * u;
		float d_v2 = 2.0F * v * v;
		float d_tu = 2.0F * t * u;
		float d_vs = 2.0F * v * s;
		float d_vt = 2.0F * v * t;
		float d_su = 2.0F * s * u;
		float d_uv = 2.0F * u * v;
		float d_st = 2.0F * s * t;

		elem[0][0] = 1.0F - d_u2 - d_v2;
		elem[0][1] = d_tu - d_vs;
		elem[0][2] = d_vt + d_su;
		elem[0][3] = 0.0F;
		elem[1][0] = d_tu + d_vs;
		elem[1][1] = 1.0F - d_v2 - d_t2;
		elem[1][2] = d_uv - d_st;
		elem[1][3] = 0.0F;
		elem[2][0] = d_vt - d_su;
		elem[2][1] = d_uv + d_st;
		elem[2][2] = 1.0F - d_t2 - d_u2;
		elem[2][3] = 0.0F;
		elem[3][0] = 0.0F;
		elem[3][1] = 0.0F;
		elem[3][2] = 0.0F;
		elem[3][3] = 1.0F;

		return this;
	}

	/**
	 * x/y/z�������̒P�ʃx�N�g���̕ϊ����^���ĉ�]�s������B
	 * ���^����3�̃x�N�g���͒������A�����K������Ă���K�v������B
	 * @param tx (IN ) x�������̒P�ʃx�N�g��(1, 0, 0)�̕ϊ���
	 * @param ty (IN ) y�������̒P�ʃx�N�g��(0, 1, 0)�̕ϊ���
	 * @param tz (IN ) z�������̒P�ʃx�N�g��(0, 0, 1)�̕ϊ���
	 * @return ���� (�������g)
	 */
	public T3Matrix setRotateWithUnitVector(T3Vector tx, T3Vector ty, T3Vector tz)
	{
		elem[0][0] = tx.x();
		elem[1][0] = tx.y();
		elem[2][0] = tx.z();
		elem[3][0] = 0.0F;
		elem[0][1] = ty.x();
		elem[1][1] = ty.y();
		elem[2][1] = ty.z();
		elem[3][1] = 0.0F;
		elem[0][2] = tz.x();
		elem[1][2] = tz.y();
		elem[2][2] = tz.z();
		elem[3][2] = 0.0F;
		elem[0][3] = 0.0F;
		elem[1][3] = 0.0F;
		elem[2][3] = 0.0F;
		elem[3][3] = 1.0F;

		return this;
	}
}
