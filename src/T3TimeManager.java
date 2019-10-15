/**
 * ���ԊǗ�
 */
public class T3TimeManager
{
	/** �t���[�����[�g(FPS) */
	private float frameRate;
	/** �ő�t���[�����[�g */
	private float maxFrameRate;
	/** �P�ʎ���(��t) */
	private float delta;
	/** ���z�̒P�ʎ��� */
	private float idealDelta;
	/** �P�ʎ��Ԃ̗��z����̌덷 */
	private float errorDelta;
	/** ��������̒P�ʎ��Ԃ̍ő�l */
	private float maxVirtualDelta;
	/** �ŏ��X���[�v���� */
	private long minSleepTime;
	/** �ʎZ�o�ߎ��� */
	private float elapsedTime;
	/** �O��̎��� */
	private long prevTime;

	/**
	 * �R���X�g���N�^
	 * @param maxFrameRate    (IN ) �ő�t���[�����[�g
	 * @param minSleepTime    (IN ) �ŏ��X���[�v����
	 * @param maxVirtualDelta (IN ) ��������̒P�ʎ��Ԃ̍ő�l (�ő�l�����߂Ȃ��Ă悯���0�ȉ��ɂ��Ă���)
	 */
	public T3TimeManager(float maxFrameRate, long minSleepTime, float maxVirtualDelta)
	{
		this.maxFrameRate = maxFrameRate;
		this.minSleepTime = minSleepTime;
		this.maxVirtualDelta = maxVirtualDelta;

		idealDelta = 1000.0F / maxFrameRate;
		delta = idealDelta;
		errorDelta = 0.0F;
		elapsedTime = 0.0F;
		frameRate = this.maxFrameRate;

		prevTime = System.currentTimeMillis();
	}

	/**
	 * ���Ԃ��v�����A�X���[�v����B
	 */
	public void measureAndSleep()
	{
		long currTime = System.currentTimeMillis();
		long workTime = Math.max((currTime - prevTime), 0);
		long sleepTime = Math.max(((long)(idealDelta - errorDelta) - workTime), minSleepTime);

		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}

		long wakedTime = System.currentTimeMillis();
		long actualSleepTime = Math.max((wakedTime - currTime), 0);
		delta = (float)(workTime + actualSleepTime);
		errorDelta += delta - idealDelta;
		if (errorDelta > 10000.0F) {
			errorDelta = 0.0F;				// errorDelta�����܂肷������K���ȂƂ���Ń��Z�b�g
		}
		frameRate = 1000.0F / delta;
		elapsedTime += delta;

		prevTime = wakedTime;

//		System.out.println("W"+Long.toString(workTime)+"S"+Long.toString(sleepTime)+"A"+Long.toString(actualSleepTime)+"E"+Float.toString(errorDelta));
	}

	/**
	 * ��������̒P�ʎ��Ԃ𓾂�B
	 * ���������ׂ���������Ƃ��ɁA�l���傫���Ȃ肷���Ȃ��悤�ɂ��邽�߁A
	 *   ���~�b�g��݂��Ă���B(��������̎��Ԃ̐i�ݕ����x���Ȃ�)
	 * @return ��������̒P�ʎ���
	 */
	public float getDelta()
	{
		float virtualDelta;

		if (maxVirtualDelta > 0.0F) {
			virtualDelta = Math.min(delta, maxVirtualDelta);
		} else {
			virtualDelta = delta;
		}
		return virtualDelta;
	}

	/**
	 * �ʎZ�o�ߎ��Ԃ𓾂�B
	 * ���P�ʎ��Ԃ̕��́A�l���傫���Ȃ肷���Ȃ��悤�ɏ��׍H���Ă��邪�A
	 *   �ʎZ�o�ߎ��Ԃ͖{���̒l���̂܂܂Ȃ̂Œ��ӁB
	 * @return �ʎZ�o�ߎ���
	 */
	public float getElapsedTime()
	{
		return elapsedTime;
	}

	/**
	 * �t���[�����[�g�𓾂�B
	 * @return �t���[�����[�g
	 */
	public float getFrameRate()
	{
		return frameRate;
	}
}
