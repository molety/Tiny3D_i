/**
 * �x�N�g�� (�ʒu�܂��͕�����\��)
 */
public class T3Vector
{
	/** �v�f */
	public float[] elem = {0.0F, 0.0F, 0.0F, 1.0F};

	/**
	 * �f�t�H���g�R���X�g���N�^
	 */
	public T3Vector()
	{
	}

	/**
	 * �R���X�g���N�^
	 * @param x (IN ) x����
	 * @param y (IN ) y����
	 * @param z (IN ) z����
	 */
	public T3Vector(float x, float y, float z)
	{
		elem[0] = x;
		elem[1] = y;
		elem[2] = z;
	}

	/**
	 * x������Ԃ��B
	 * @return x����
	 */
	public float x()
	{
		return elem[0];
	}

	/**
	 * y������Ԃ��B
	 * @return y����
	 */
	public float y()
	{
		return elem[1];
	}

	/**
	 * z������Ԃ��B
	 * @return z����
	 */
	public float z()
	{
		return elem[2];
	}

	/**
	 * ������ݒ肷��B
	 * @param x (IN ) x����
	 * @param y (IN ) y����
	 * @param z (IN ) z����
	 * @return ���� (�������g)
	 */
	public T3Vector set(float x, float y, float z)
	{
		elem[0] = x;
		elem[1] = y;
		elem[2] = z;

		return this;
	}

	/**
	 * �x�N�g���̃m����(����)�����߂�B
	 * @return �m����
	 */
	public float norm()
	{
		float x = elem[0];
		float y = elem[1];
		float z = elem[2];
		float n2 = x * x + y * y + z * z;

		return (n2 * T3Math.invSqrt(n2));
	}

	/**
	 * �m������2������߂�B
	 * @return �m������2��
	 */
	public float norm2()
	{
		float x = elem[0];
		float y = elem[1];
		float z = elem[2];

		return (x * x + y * y + z * z);
	}

	/**
	 * ���K������B(�m������1�ɂ���)
	 * @return ���� (�������g)
	 */
	public T3Vector normalize()
	{
		float x = elem[0];
		float y = elem[1];
		float z = elem[2];
//		float invNorm = 1.0F / (float)Math.sqrt(x * x + y * y + z * z);
		float invNorm = T3Math.invSqrt(x * x + y * y + z * z);

		elem[0] *= invNorm;
		elem[1] *= invNorm;
		elem[2] *= invNorm;

		return this;
	}

	/**
	 * �t�x�N�g���ɂ���B
	 * @return ���� (�������g)
	 */
	public T3Vector invert()
	{
		elem[0] = -elem[0];
		elem[1] = -elem[1];
		elem[2] = -elem[2];

		return this;
	}

	/**
	 * �t�x�N�g�������B
	 * @param inverse (OUT) �t�x�N�g��
	 * @param src     (IN ) ���̃x�N�g��
	 * @return �t�x�N�g��
	 */
	public static T3Vector makeInverse(T3Vector inverse, T3Vector src)
	{
		float[] a = src.elem;
		float[] b = inverse.elem;

		b[0] = -a[0];
		b[1] = -a[1];
		b[2] = -a[2];

		return inverse;
	}

	/**
	 * �X�J���[�{����B
	 * @param n (IN ) �X�J���[
	 * @return ���� (�������g)
	 */
	public T3Vector multScalar(float n)
	{
		elem[0] *= n;
		elem[1] *= n;
		elem[2] *= n;

		return this;
	}

	/**
	 * �x�N�g�������Z����B
	 * @param addend (IN ) ���Z����x�N�g��
	 * @return ���� (�������g)
	 */
	public T3Vector add(T3Vector addend)
	{
		float[] b = addend.elem;

		elem[0] += b[0];
		elem[1] += b[1];
		elem[2] += b[2];

		return this;
	}

	/**
	 * �x�N�g�������Z����B
	 * @param subtrahend (IN ) ���Z����x�N�g��
	 * @return ���� (�������g)
	 */
	public T3Vector sub(T3Vector subtrahend)
	{
		float[] b = subtrahend.elem;

		elem[0] -= b[0];
		elem[1] -= b[1];
		elem[2] -= b[2];

		return this;
	}

	/**
	 * �a�x�N�g�������߂�B
	 * @param s      (OUT) �a�x�N�g��
	 * @param augend (IN ) �������x�N�g��
	 * @param addend (IN ) �����x�N�g��
	 * @return �a�x�N�g��
	 */
	public static T3Vector sum(T3Vector s, T3Vector augend, T3Vector addend)
	{
		float[] a = augend.elem;
		float[] b = addend.elem;
		float[] c = s.elem;

		c[0] = a[0] + b[0];
		c[1] = a[1] + b[1];
		c[2] = a[2] + b[2];

		return s;
	}

	/**
	 * ���x�N�g�������߂�B
	 * @param d          (OUT) ���x�N�g��
	 * @param minuend    (IN ) �������x�N�g��
	 * @param subtrahend (IN ) �����x�N�g��
	 * @return ���x�N�g��
	 */
	public static T3Vector diff(T3Vector d, T3Vector minuend, T3Vector subtrahend)
	{
		float[] a = minuend.elem;
		float[] b = subtrahend.elem;
		float[] c = d.elem;

		c[0] = a[0] - b[0];
		c[1] = a[1] - b[1];
		c[2] = a[2] - b[2];

		return d;
	}

	/**
	 * ���ς����߂�B
	 * @param multiplicand (IN ) 1�ڂ̃x�N�g��
	 * @param multiplier   (IN ) 2�ڂ̃x�N�g��
	 * @return ����
	 */
	public static float innerProduct(T3Vector multiplicand, T3Vector multiplier)
	{
		float[] a = multiplicand.elem;
		float[] b = multiplier.elem;

		return (a[0] * b[0] + a[1] * b[1] + a[2] * b[2]);
	}

	/**
	 * �O�ς����߂�B
	 * @param product      (OUT) �O��
	 * @param multiplicand (IN ) 1�ڂ̃x�N�g��
	 * @param multiplier   (IN ) 2�ڂ̃x�N�g��
	 * @return �O��
	 */
	public static T3Vector outerProduct(T3Vector product, T3Vector multiplicand, T3Vector multiplier)
	{
		float[] a = multiplicand.elem;
		float[] b = multiplier.elem;
		float[] c = product.elem;

		c[0] = a[1] * b[2] - a[2] * b[1];
		c[1] = a[2] * b[0] - a[0] * b[2];
		c[2] = a[0] * b[1] - a[1] * b[0];

		return product;
	}
}
