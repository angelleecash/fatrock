package info.chenliang.fatrock.showcase;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.view.Window;
import android.view.WindowManager;

public class FatRockActivity extends Activity {
	FatRockView fatRockView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        fatRockView = new FatRockView(getBaseContext());
        setContentView(fatRockView);
        
        try {
//			Debug.startMethodTracing("fatrock", 32*1024*1024);
//			Debug.enableEmulatorTraceOutput();
//			Debug.startAllocCounting();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		fatRockView.running = false;
		Debug.stopMethodTracing();
		Debug.stopAllocCounting();
	}
}
