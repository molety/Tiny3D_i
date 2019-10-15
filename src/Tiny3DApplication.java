import com.nttdocomo.ui.*;

/**
 * アプリケーション
 */
public class Tiny3DApplication extends IApplication
{
	/** Canvasオブジェクト */
	private Tiny3DCanvas t3dCanvas;

	/**
	 * アプリケーション開始時に呼ばれる。
	 */
	public void start()
	{
		t3dCanvas = new Tiny3DCanvas();

		Display.setCurrent(t3dCanvas);

		Thread th = new Thread(t3dCanvas);
		th.start();
	}
}
