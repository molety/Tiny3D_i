import com.nttdocomo.ui.*;

/**
 * ���e
 */
public class T3Projection
{
	/** Graphics�I�u�W�F�N�g */
	private Graphics graphics;

	/** �������e���[�h */
	public static final int PERSPECTIVE = 0;
	/** �����e���[�h */
	public static final int ORTHOGRAPHIC = 1;
	/** ���e���[�h */
	private int mode = PERSPECTIVE;

	/** �E�B���h�E�� */
	private float windowWidth;
	/** �E�B���h�E���� */
	private float windowHeight;
	/** �E�B���h�E�[�� */
	public  float windowDepth;

	/** �r���[�|�[�g���[x���W */
	private float viewportX;
	/** �r���[�|�[�g��[y���W */
	private float viewportY;
	/** �r���[�|�[�g�� */
	private float viewportWidth;
	/** �r���[�|�[�g���� */
	private float viewportHeight;

	/** 1�̖ʂ��\�����钸�_���̍ő�l */
	private static final int MAX_NUM_OF_VERTEX = 128;
	/** �`��ʒux���W */
	private static int[] drawX = new int[MAX_NUM_OF_VERTEX];
	/** �`��ʒuy���W */
	private static int[] drawY = new int[MAX_NUM_OF_VERTEX];

	/**
	 * �R���X�g���N�^
	 * @param g (IN ) Graphics�I�u�W�F�N�g
	 */
	public T3Projection(Graphics g)
	{
		graphics = g;
	}

	/**
	 * �������e���[�h�ɐݒ肷��B
	 * @param width  (IN ) �E�B���h�E��
	 * @param height (IN ) �E�B���h�E����
	 * @param depth  (IN ) �E�B���h�E�[�� (���e�ʂ�z���W�A���̒l)
	 */
	public void setPerspective(float width, float height, float depth)
	{
		mode = PERSPECTIVE;
		windowWidth = width;
		windowHeight = height;
		windowDepth = depth;
	}

	/**
	 * ��p�Əc������w�肵�ē������e���[�h�ɐݒ肷��B
	 * @param fovY   (IN ) ��p(field of view Y) (���W�A���Ŏw��)
	 * @param aspect (IN ) �c���� (�� / �c)
	 * @param depth  (IN ) ���e�ʂ�z���W (< 0)
	 */
	public void setPerspectiveByFovY(float fovY, float aspect, float depth)
	{
		mode = PERSPECTIVE;
		windowHeight = -depth * (float)Math.tan(fovY * 0.5F) * 2.0F;
		windowWidth = windowHeight * aspect;
		windowDepth = depth;
	}

	/**
	 * �����e���[�h�ɐݒ肷��B
	 * @param width  (IN ) �E�B���h�E��
	 * @param height (IN ) �E�B���h�E����
	 * @param depth  (IN ) �E�B���h�E�[�� (���e�ʂ�z���W�A���̒l)
	 */
	public void setOrthographic(float width, float height, float depth)
	{
		mode = ORTHOGRAPHIC;
		windowWidth = width;
		windowHeight = height;
		windowDepth = depth;
	}

	/**
	 * �r���[�|�[�g��ݒ肷��B
	 * @param x      (IN ) ���[��x���W
	 * @param y      (IN ) ��[��y���W
	 * @param width  (IN ) ��
	 * @param height (IN ) ����
	 */
	public void setViewport(int x, int y, int width, int height)
	{
		viewportX = (float)x;
		viewportY = (float)y;
		viewportWidth = (float)(width - 1);
		viewportHeight = (float)(height - 1);

		graphics.setClip(x, y, width, height);
	}

	/**
	 * ���e����B
	 * @param world (IN ) ���[���h�I�u�W�F�N�g
	 */
	public void project(T3World world)
	{
		// ���������e���[�h�̌v�Z����
		//   http://www.manpagez.com/man/3/glFrustum/ ���Q�l�ɂ��܂����B

		float factorX, factorY;
		float offsetX, offsetY;
		int i, j;
		T3Face face;
		T3Vector tempV;
		int[] color;

		switch (mode) {
		  case PERSPECTIVE:
			factorX = viewportWidth / windowWidth * windowDepth;
			factorY = -1.0F * viewportHeight / windowHeight * windowDepth;
			offsetX = viewportX + viewportWidth * 0.5F;
			offsetY = viewportY + viewportHeight * 0.5F;
			for (i = 0; i < world.numOfFace; i++) {
				face = world.sortedFace[i];
				for (j = 0; j < face.numOfVertex; j++) {
					tempV = face.vertex[j];
//					drawX[j] = (int)(factorX * tempV.x() / tempV.z() + offsetX);
//					drawY[j] = (int)(factorY * tempV.y() / tempV.z() + offsetY);
					drawX[j] = T3Math.floatToInt(factorX * tempV.x() / tempV.z() + offsetX);
					drawY[j] = T3Math.floatToInt(factorY * tempV.y() / tempV.z() + offsetY);
				}
				color = face.renderedColor;
				graphics.setColor(graphics.getColorOfRGB(color[0], color[1], color[2]));
				graphics.fillPolygon(drawX, drawY, face.numOfVertex);
			}
			break;
		  case ORTHOGRAPHIC:
			factorX = viewportWidth / windowWidth;
			factorY = -1.0F * viewportHeight / windowHeight;
			offsetX = viewportX + viewportWidth * 0.5F;
			offsetY = viewportY + viewportHeight * 0.5F;
			for (i = 0; i < world.numOfFace; i++) {
				face = world.sortedFace[i];
				for (j = 0; j < face.numOfVertex; j++) {
					tempV = face.vertex[j];
//					drawX[j] = (int)(factorX * tempV.x() + offsetX);
//					drawY[j] = (int)(factorY * tempV.y() + offsetY);
					drawX[j] = T3Math.floatToInt(factorX * tempV.x() + offsetX);
					drawY[j] = T3Math.floatToInt(factorY * tempV.y() + offsetY);
				}
				color = face.renderedColor;
				graphics.setColor(graphics.getColorOfRGB(color[0], color[1], color[2]));
				graphics.fillPolygon(drawX, drawY, face.numOfVertex);
			}
			break;
		}
	}
}
