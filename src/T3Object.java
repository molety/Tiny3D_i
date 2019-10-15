/**
 * �I�u�W�F�N�g
 * (���f�����O-�r���[�C���O�ϊ����󂯂���̂̊��)
 */
public class T3Object
{
	/** ���f�����O�ϊ��s�� */
	public T3Matrix transform;

	/**
	 * �R���X�g���N�^
	 */
	public T3Object()
	{
		transform = new T3Matrix();
	}

	/**
	 * ���f�����O�ϊ��s���ݒ肷��B
	 * @param m (IN ) �ϊ��s��
	 */
	public void setTransform(T3Matrix m)
	{
		T3Matrix.copy(transform, m);
	}

	/**
	 * �����̃��f�����O�ϊ��̖����ɕϊ���ǉ�����B
	 * �V�����ϊ��s���������|����B
	 * @param m (IN ) �V�����ϊ��s��
	 */
	public void addTransform(T3Matrix m)
	{
		transform.multFromLeft(m);
	}

	/**
	 * �����̃��f�����O�ϊ��̐擪�ɕϊ���ǉ�����B
	 * �V�����ϊ��s����E����|����B
	 * OpenGL�̍l�����͂�����
	 * @param m (IN ) �V�����ϊ��s��
	 */
	public void addTransformToHead(T3Matrix m)
	{
		transform.multFromRight(m);
	}

	/**
	 * ���[���h���ɔz�u����B
	 * @param world (IN ) ���[���h�I�u�W�F�N�g
	 */
	public void place(T3World world)
	{
		T3Matrix.multMatrix(world.modelView, world.camera.transform, transform);
	}
}
