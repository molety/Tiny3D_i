/**
 * ���[���h
 */
public class T3World
{
	/** render()�����Ŏg�p����e���|���� */
	private static float[] tempColor = new float[3];

	/** ���f�����O-�r���[�C���O�ϊ��s�� */
	public T3Matrix modelView;

	/** ���f�� */
	private T3Model[] modelBuffer;
	/** �o�^����Ă��郂�f���� */
	private int numOfModel;

	/** ���� */
	private T3Light[] lightBuffer;
	/** �o�^����Ă�������� */
	private int numOfLight;

	/** �J����(���_) */
	public T3Camera camera;

	/** ���e�I�u�W�F�N�g */
	public T3Projection projection;

	/** �ʃo�b�t�@ */
	private T3Face[] faceBuffer;
	/** �ʃo�b�t�@(�\�[�g�p�̈�) */
	private T3Face[] faceBufferTemp;
	/** �\�[�g�ςݖʃo�b�t�@ */
	public T3Face[] sortedFace;
	/** �o�b�t�@�ɓ����Ă���ʐ� */
	public int numOfFace;

	/**
	 * �R���X�g���N�^
	 * @param maxNumOfModel (IN ) ���f���̍ő吔
	 * @param maxNumOfLight (IN ) �����̍ő吔
	 * @param maxNumOfFace  (IN ) �ʂ̍ő吔
	 */
	public T3World(int maxNumOfModel, int maxNumOfLight, int maxNumOfFace)
	{
		modelView = new T3Matrix();

		modelBuffer = new T3Model[maxNumOfModel];
		numOfModel = 0;

		lightBuffer = new T3Light[maxNumOfLight];
		numOfLight = 0;

		faceBuffer = new T3Face[maxNumOfFace];
		faceBufferTemp = new T3Face[maxNumOfFace];
		numOfFace = 0;
	}

	/**
	 * �o�^����Ă��郂�f���ƌ������N���A����B
	 */
	public void clearModelAndLight()
	{
		numOfModel = 0;
		numOfLight = 0;
		numOfFace = 0;
	}

	/**
	 * ���f����ǉ�����B
	 * @param model (IN ) ���f��
	 */
	public void addModel(T3Model model)
	{
		modelBuffer[numOfModel++] = model;
	}

	/**
	 * ������ǉ�����B
	 * @param light (IN ) ����
	 */
	public void addLight(T3Light light)
	{
		lightBuffer[numOfLight++] = light;
	}

	/**
	 * �ʂ�ǉ�����B
	 * @param face (IN ) ��
	 */
	public void addFace(T3Face face)
	{
		faceBuffer[numOfFace++] = face;
	}

	/**
	 * �J����(���_)��ݒ肷��B
	 * @param camera (IN ) �J����
	 */
	public void setCamera(T3Camera camera)
	{
		this.camera = camera;
	}

	/**
	 * ���e�I�u�W�F�N�g��ݒ肷��B
	 * @param projection (IN ) ���e�I�u�W�F�N�g
	 */
	public void setProjection(T3Projection projection)
	{
		this.projection = projection;
	}

	/**
	 * �����_�����O����B
	 * @param doCullBack (IN ) �������̖ʂ��J�����O���邩
	 * @param doCullNear (IN ) ���e�ʂ��߂��ɂ���ʂ��J�����O���邩
	 */
	public void render(boolean doCullBack, boolean doCullNear)
	{
		int i, j;

		for (i = 0; i < numOfModel; i++) {
			modelBuffer[i].place(this, doCullBack, doCullNear);
		}

		for (i = 0; i < numOfLight; i++) {
			lightBuffer[i].place(this);
		}

		// Z�\�[�g
		sortedFace = sortFace(faceBuffer, faceBufferTemp, numOfFace);

		// ���C�e�B���O
		for (i = 0; i < numOfFace; i++) {
			tempColor[0] = 0.0F;
			tempColor[1] = 0.0F;
			tempColor[2] = 0.0F;
			for (j = 0; j < numOfLight; j++) {
				lightBuffer[j].calcColor(tempColor, sortedFace[i]);
			}
//			sortedFace[i].renderedColor[0] = Math.min((int)tempColor[0], 255);
//			sortedFace[i].renderedColor[1] = Math.min((int)tempColor[1], 255);
//			sortedFace[i].renderedColor[2] = Math.min((int)tempColor[2], 255);
			sortedFace[i].renderedColor[0] = Math.min(T3Math.floatToInt(tempColor[0]), 255);
			sortedFace[i].renderedColor[1] = Math.min(T3Math.floatToInt(tempColor[1]), 255);
			sortedFace[i].renderedColor[2] = Math.min(T3Math.floatToInt(tempColor[2]), 255);
		}

		// ���e
		projection.project(this);
	}

	/**
	 * �ʂ�Z�\�[�g����B
	 * @param data      (I/O) �\�[�g�Ώۂ̃f�[�^
	 * @param work      (I/O) ���[�N�G���A
	 * @param numOfData (IN ) �f�[�^�̌�
	 * @return �\�[�g�ς݂̃f�[�^ (data/work�̂ǂ��炩���Ԃ�)
	 */
	private T3Face[] sortFace(T3Face[] data, T3Face[] work, int numOfData)
	{
		// �� http://f4.aaa.livedoor.jp/~pointc/203/No.7397.htm �ŋc�_����Ă���
		//   ��ċA�Ń}�[�W�\�[�g�̃A���S���Y�����Q�l�ɂ��܂����B

		T3Face[] src = data;
		T3Face[] dst = work;
		T3Face[] p;
		int pieceHalfSize;
		int pieceTop;
		int pieceBottom;
		int pieceHalf;
		int left;
		int right;
		int dstIndex;
		// ���̃��[�v��O(logN)
		for (pieceHalfSize = 1; pieceHalfSize < numOfData; pieceHalfSize *= 2) {
			dstIndex = 0;
			// O(N^2)�Ɍ����Ă�����O(N)
			for (pieceTop = 0; pieceTop < numOfData; pieceTop += pieceHalfSize * 2) {
				pieceBottom = pieceTop + pieceHalfSize * 2;
				pieceHalf = pieceTop + pieceHalfSize;
				left = pieceTop;
				right = pieceHalf;
				if (pieceHalf >= numOfData) {				// �f�Ђ̉E������������
					while (left < numOfData) {
						dst[dstIndex++] = src[left++];
					}
					break;
				}
				if (pieceBottom > numOfData) {				// �f�Ђ̉E�����̐�����
					pieceBottom = numOfData;
				}
				for (;;) {
					if (src[left].centerZ <= src[right].centerZ) {	// �d�S��z���W�Ń\�[�g
						dst[dstIndex++] = src[left++];
						if (left >= pieceHalf) {			// �f�Ђ̍��������o�s��������
							while (right < pieceBottom) {
								dst[dstIndex++] = src[right++];
							}
							break;
						}
					} else {
						dst[dstIndex++] = src[right++];
						if (right >= pieceBottom) {			// �f�Ђ̉E�������o�s��������
							while (left < pieceHalf) {
								dst[dstIndex++] = src[left++];
							}
							break;
						}
					}
				}
			}

			p = src;
			src = dst;
			dst = p;
		}

		return src;			// data[]��work[]���ǂ��炩���\�[�g�ς݂ɂȂ��ĕԂ�
	}
}
