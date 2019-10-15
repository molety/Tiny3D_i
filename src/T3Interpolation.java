/**
 * ���
 */
public class T3Interpolation
{
	/** �J�n�p�� */
	private T3Matrix startPosture;
	/** �I���p�� */
	private T3Matrix endPosture;
	/** �J�n�� ���s�ړ����� */
	private T3Matrix startTranslation;
	/** �I���� ���s�ړ����� */
	private T3Matrix endTranslation;
	/** �J�n�� �X�P�[�����O���� */
	private T3Matrix startScaling;
	/** �I���� �X�P�[�����O���� */
	private T3Matrix endScaling;
	/** �J�n�� ��]���� */
	private T3Matrix startRotation;
	/** �I���� ��]���� */
	private T3Matrix endRotation;
	/** �J�n�� ��]�p */
	private float startAngle;
	/** �I���� ��]�p */
	private float endAngle;
	/** �J�n�� ��]�� */
	private T3Vector startAxis;
	/** �I���� ��]�� */
	private T3Vector endAxis;
	/** ��]���� �����p�e���|���� */
	private T3Matrix temp1Rotation;
	/** ��]���� �����p�e���|���� */
	private T3Matrix temp2Rotation;
	/** ��� ���s�ړ����� */
	private T3Matrix interTranslation;
	/** ��� �X�P�[�����O���� */
	private T3Matrix interScaling;
	/** ��� ��]���� */
	private T3Matrix interRotation;
	/** ��� */
//	private T3Matrix interpolate;
	/** �p������ */
	private float duration;
	/** ��ԊJ�n������̌o�ߎ��� */
	private float currTime;

	/**
	 * �R���X�g���N�^
	 */
	public T3Interpolation()
	{
		startTranslation = new T3Matrix();
		endTranslation = new T3Matrix();
		startScaling = new T3Matrix();
		endScaling = new T3Matrix();
		startRotation = new T3Matrix();
		endRotation = new T3Matrix();
		startAxis = new T3Vector();
		endAxis = new T3Vector();
		temp1Rotation = new T3Matrix();
		temp2Rotation = new T3Matrix();
		interTranslation = new T3Matrix();
		interScaling = new T3Matrix();
		interRotation = new T3Matrix();
	}

	/**
	 * �������B
	 * 1�̕�ԃI�u�W�F�N�g���g���񂷂��Ƃ�z�肵�Ă��邽�߁A
	 * �R���X�g���N�^�Ƃ͕ʂɂ��Ă���B
	 * @param startPosture (IN ) �J�n�p��
	 * @param endPosture   (IN ) �I���p��
	 * @param duration     (IN ) �p������ (�P�ʂ͖��Ȃ�)
	 */
	public void init(T3Matrix startPosture, T3Matrix endPosture, float duration)
	{
		this.startPosture = startPosture;
		this.endPosture = endPosture;
		this.duration = duration;
		currTime = 0.0F;

		decompose(startTranslation, startScaling, startRotation, this.startPosture);
		decompose(endTranslation, endScaling, endRotation, this.endPosture);
		startAngle = rotateMatrixToAngleAndAxis(startAxis, startRotation);
		endAngle = rotateMatrixToAngleAndAxis(endAxis, endRotation);
	}

	/**
	 * ��Ԏp���𓾂�B
	 * @param inter (OUT) ��Ԏp��
	 * @param delta (IN ) �O�񂩂�̌o�ߎ���
	 */
	public void interpolate(T3Matrix inter, float delta)
	{
		currTime += delta;
		if (currTime > duration) {
			currTime = duration;
		}

		interpolateTranslation();
		interpolateScaling();
		interpolateRotation();

		T3Matrix.multMatrix(inter, interScaling, interRotation);
		inter.multFromLeft(interTranslation);
	}

	/**
	 * ��ԓ��삪�����������ǂ�����Ԃ��B
	 * @return �������Ă�����^
	 */
	public boolean isEnd()
	{
		return (currTime >= duration);
	}

	/**
	 * ���s�ړ��^�X�P�[�����O�^��]���������ꂽ�ϊ��s��𕪉�����B
	 * M = T�ES�ER�̌`�ɂ�������T / S / R�����߂�B
	 * @param translate (OUT) ���s�ړ�����
	 * @param scale     (OUT) �X�P�[�����O����
	 * @param rotate    (OUT) ��]����
	 * @param m         (IN ) ���̕ϊ��s��
	 */
	private void decompose(T3Matrix translate, T3Matrix scale, T3Matrix rotate, T3Matrix m)
	{
		T3Matrix.copy(rotate, m);
		float[][] e = rotate.elem;

		float translateX = e[0][3];
		float translateY = e[1][3];
		float translateZ = e[2][3];
		translate.setTranslate(translateX, translateY, translateZ);
		e[0][3] = 0.0F;
		e[1][3] = 0.0F;
		e[2][3] = 0.0F;

		float scaleX = (float)Math.sqrt(e[0][0] * e[0][0] + e[0][1] * e[0][1] + e[0][2] * e[0][2]);
		float scaleY = (float)Math.sqrt(e[1][0] * e[1][0] + e[1][1] * e[1][1] + e[1][2] * e[1][2]);
		float scaleZ = (float)Math.sqrt(e[2][0] * e[2][0] + e[2][1] * e[2][1] + e[2][2] * e[2][2]);
		scale.setScale(scaleX, scaleY, scaleZ);
		e[0][0] /= scaleX;
		e[0][1] /= scaleX;
		e[0][2] /= scaleX;
		e[1][0] /= scaleY;
		e[1][1] /= scaleY;
		e[1][2] /= scaleY;
		e[2][0] /= scaleZ;
		e[2][1] /= scaleZ;
		e[2][2] /= scaleZ;

		if ((e[1][0] * e[2][1] - e[2][0] * e[1][1]) * e[0][2] < 0.0F
			|| (e[2][0] * e[0][1] - e[0][0] * e[2][1]) * e[1][2] < 0.0F
			|| (e[0][0] * e[1][1] - e[1][0] * e[0][1]) * e[2][2] < 0.0F) {
			// ����n�ɂȂ��Ă���ꍇ�A�X�P�[�����O�𒲐����ĉE��n�ɒ���
			/* (e[1][0] * e[2][1] - e[2][0] * e[1][1]) == e[0][2] ����
			 * (e[2][0] * e[0][1] - e[0][0] * e[2][1]) == e[1][2] ����
			 * (e[0][0] * e[1][1] - e[1][0] * e[0][1]) == e[2][2] �ł����
			 * �E��n�Ɗm�F�ł���̂����A���Z�덷���l�����ĕ����̃`�F�b�N�ɂƂǂ߂Ă���B
			 */
			scaleZ = -scaleZ;
			e[2][0] = -e[2][0];
			e[2][1] = -e[2][1];
			e[2][2] = -e[2][2];
		}
	}

	/**
	 * ���s�ړ�����`��Ԃ���B
	 */
	private void interpolateTranslation()
	{
		float ratio = currTime / duration;
		float interX = startTranslation.elem[0][3] * (1.0F - ratio)
		  + endTranslation.elem[0][3] * ratio;
		float interY = startTranslation.elem[1][3] * (1.0F - ratio)
		  + endTranslation.elem[1][3] * ratio;
		float interZ = startTranslation.elem[2][3] * (1.0F - ratio)
		  + endTranslation.elem[2][3] * ratio;

		interTranslation.setTranslate(interX, interY, interZ);
	}

	/**
	 * �X�P�[�����O���Ԃ���B
	 */
	private void interpolateScaling()
	{
		float ratio = currTime / duration;
//		float interScaleX = Math.pow(startScaling.elem[0][0], (1.0F - ratio))
//		  * Math.pow(endScaling.elem[0][0], ratio);
//		float interScaleY = Math.pow(startScaling.elem[1][1], (1.0F - ratio))
//		  * Math.pow(endScaling.elem[1][1], ratio);
//		float interScaleZ = Math.pow(startScaling.elem[2][2], (1.0F - ratio))
//		  * Math.pow(endScaling.elem[2][2], ratio);
		// �{���͏㎮�̂悤�ɂȂ邪�A���`��Ԃő�p����B
		float interScaleX = startScaling.elem[0][0] * (1.0F - ratio)
		  + endScaling.elem[0][0] * ratio;
		float interScaleY = startScaling.elem[1][1] * (1.0F - ratio)
		  + endScaling.elem[1][1] * ratio;
		float interScaleZ = startScaling.elem[2][2] * (1.0F - ratio)
		  + endScaling.elem[2][2] * ratio;

		interScaling.setScale(interScaleX, interScaleY, interScaleZ);
	}

	/**
	 * ��]�����ʐ��`��Ԃ���B
	 * slerp (Spherical Linear intERPolation)
	 */
	private void interpolateRotation()
	{
		float ratio = currTime / duration;

		temp1Rotation.setRotate(startAngle * (1.0F - ratio), startAxis);
		temp2Rotation.setRotate(endAngle * ratio, endAxis);
		T3Matrix.multMatrix(interRotation, temp2Rotation, temp1Rotation);
	}

	/**
	 * ��]�s�񂩂��]�p�Ɖ�]�������߂�B
	 * @param axis   (OUT) ��]��
	 * @param rotate (IN ) ��]�s��
	 * @return ��]�p (���W�A��)
	 */
	private float rotateMatrixToAngleAndAxis(T3Vector axis, T3Matrix rotate)
	{
		float[][] e = rotate.elem;
		float angle = 0.0F;
		float s, t, u, v;
		float q_s2 = e[0][0] + e[1][1] + e[2][2] + e[3][3];
		float abs_t = 0.5F * (float)Math.sqrt(Math.max((1.0F + e[0][0] - e[1][1] - e[2][2]), 0.0F));
		float abs_u = 0.5F * (float)Math.sqrt(Math.max((1.0F - e[0][0] + e[1][1] - e[2][2]), 0.0F));
		float abs_v = 0.5F * (float)Math.sqrt(Math.max((1.0F - e[0][0] - e[1][1] + e[2][2]), 0.0F));

		if (q_s2 > 0.0F) {
			// 0 < s=cos(��/2) <= 1�̂Ƃ� (0 <= �� < �΂̂Ƃ�)
			s = Math.min(0.5F * (float)Math.sqrt(q_s2), 1.0F);	// �v�Z�덷���l������1.0�ŗ}���Ă���
			t = (e[2][1] - e[1][2] >= 0.0F) ? abs_t : -abs_t;
			u = (e[0][2] - e[2][0] >= 0.0F) ? abs_u : -abs_u;
			v = (e[1][0] - e[0][1] >= 0.0F) ? abs_v : -abs_v;
//			angle = 2.0F * (float)Math.acos(s);
			angle = 2.0F * T3Math.facos(s);
			if (s < 1.0F) {
				// 0 < s=cos(��/2) < 1�̂Ƃ� (0 < �� < �΂̂Ƃ�)
				float factor = (float)(1.0 / Math.sqrt(1.0 - s * s));
				axis.set(t * factor, u * factor, v * factor);
			} else {
				// s=cos(��/2) = 1�̂Ƃ� (�� = 0�̂Ƃ�)
				// ��]�p=0�Ȃ̂ŁA�K���Ɏ������߂Ă悢
				axis.set(0.0F, 1.0F, 0.0F);
			}
		} else {
			// s=cos(��/2) = 0�̂Ƃ� (�� = �΂̂Ƃ�)
			angle = (float)Math.PI;
			u = abs_u;					// u >= 0�ƌ��߂�
			if (u > 0.0F) {
				t = (e[0][1] >= 0.0F) ? abs_t : -abs_t;
				v = (e[1][2] >= 0.0F) ? abs_v : -abs_v;
			} else {
				t = abs_t;
				v = (e[2][0] >= 0.0F) ? abs_v : -abs_v;
			}
			axis.set(t, u, v);
		}

		return angle;
	}
}
