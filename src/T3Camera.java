/**
 * �J���� (���_)
 */
public class T3Camera extends T3Object
{
	/** x�����̒P�ʃx�N�g���̕ϊ��� */
	private static T3Vector tx = new T3Vector();
	/** y�����̒P�ʃx�N�g���̕ϊ��� */
	private static T3Vector ty = new T3Vector();
	/** z�����̒P�ʃx�N�g���̕ϊ��� */
	private static T3Vector tz = new T3Vector();
	/** �r���[�C���O�ϊ��̉�]���� */
	private static T3Matrix rot = new T3Matrix();
	/** �r���[�C���O�ϊ��̕��s�ړ����� */
	private static T3Matrix trans = new T3Matrix();
	/** �r���[�C���O�ϊ���roll���� */
	private static T3Matrix rollM = new T3Matrix();
	/** �r���[�C���O�ϊ���pitch���� */
	private static T3Matrix pitchM = new T3Matrix();
	/** �r���[�C���O�ϊ���yaw���� */
	private static T3Matrix yawM = new T3Matrix();
	/** �ڕW�ʒu��\���e���|���� */
	private static T3Vector tempTarget = new T3Vector();
	/** �ڕW�ʒu��\���e���|���� */
	private static T3Vector tempTarget2 = new T3Vector();

	/**
	 * ���_��ݒ肷��B
	 * @param eye    (IN ) ���_�ʒu
	 * @param target (IN ) �ڕW�ʒu
	 * @param up     (IN ) �����
	 */
	public void lookAt(T3Vector eye, T3Vector target, T3Vector up)
	{
		// �����̃A���S���Y���� http://3dinc.blog45.fc2.com/blog-entry-392.html ���Q�l�ɂ��܂����B

		// ���_����-z���������āA+y��������Ƃ������̎��_����Ƃ���B
		// ���̊���_����^����ꂽ���_�Ɉړ����邽�߂̕ϊ����l����B
		// ���̕ϊ��̋t�ϊ����A���߂�r���[�C���O�ϊ��ɂȂ�B

		// ��]���������߂�
		T3Vector.diff(tz, eye, target);
		T3Vector.outerProduct(tx, up, tz);
		tz.normalize();
		tx.normalize();
		T3Vector.outerProduct(ty, tz, tx);	// ty�̃m������1�ɂȂ�̂Ő��K���s�v
		rot.setRotateWithUnitVector(tx, ty, tz);

		// ���s�ړ�������eye���̂���

		// ����_����rot������]����eye�������s�ړ�����Η^����ꂽ���_�ɂȂ邩��A
		// ���߂�r���[�C���O�ϊ��́F-eye�������s�ړ���rot�̋t�ɉ�]
		rot.transpose();					// ��]�s��͓]�u����΋t�s��ɂȂ�
		trans.setTranslate(-eye.x(), -eye.y(), -eye.z());
		T3Matrix.multMatrix(transform, rot, trans);
	}

	/**
	 * �p�x(roll-pitch-yaw)�Ǝ��_�ʒu�Ŏ��_��ݒ肷��B
	 * @param roll  (IN ) -z������̉�]�p (���v���ɌX��������)
	 * @param pitch (IN ) -x������̉�]�p (��������������)
	 * @param yaw   (IN ) y������̉�]�p (��������������)
	 * @param eye   (IN ) ���_�ʒu
	 */
	public void lookAtByAngleAndEye(float roll, float pitch, float yaw, T3Vector eye)
	{
		rollM.setRotateZ(-roll);
		pitchM.setRotateX(-pitch);
		yawM.setRotateY(yaw);
		T3Matrix.multMatrix(rot, pitchM, rollM);
		rot.multFromLeft(yawM);
		rot.transpose();

		trans.setTranslate(-eye.x(), -eye.y(), -eye.z());
		T3Matrix.multMatrix(transform, rot, trans);
	}

	/**
	 * �p�x(roll-pitch-yaw)�ƖڕW�ʒu�Ƌ����Ŏ��_��ݒ肷��B
	 * @param roll     (IN ) -z������̉�]�p (���v���ɌX��������)
	 * @param pitch    (IN ) -x������̉�]�p (��������������)
	 * @param yaw      (IN ) y������̉�]�p (��������������)
	 * @param target   (IN ) �ڕW�ʒu
	 * @param distance (IN ) �ڕW�܂ł̋���
	 */
	public void lookAtByAngleAndTarget(float roll, float pitch, float yaw, T3Vector target, float distance)
	{
		rollM.setRotateZ(-roll);
		pitchM.setRotateX(-pitch);
		yawM.setRotateY(yaw);
		T3Matrix.multMatrix(rot, pitchM, rollM);
		rot.multFromLeft(yawM);

		tempTarget.set(0.0F, 0.0F, -distance);
		T3Matrix.multVector(tempTarget2, rot, tempTarget);
		T3Vector.diff(tempTarget, tempTarget2, target);		// ��tempTarget���g���񂵂�
		trans.setTranslate(tempTarget);

		rot.transpose();
		T3Matrix.multMatrix(transform, rot, trans);
	}
}
