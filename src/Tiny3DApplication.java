import com.nttdocomo.ui.*;

/**
 * �A�v���P�[�V����
 */
public class Tiny3DApplication extends IApplication
{
	/** Canvas�I�u�W�F�N�g */
	private Tiny3DCanvas t3dCanvas;

	/**
	 * �A�v���P�[�V�����J�n���ɌĂ΂��B
	 */
	public void start()
	{
		t3dCanvas = new Tiny3DCanvas();

		Display.setCurrent(t3dCanvas);

		Thread th = new Thread(t3dCanvas);
		th.start();
	}
}
