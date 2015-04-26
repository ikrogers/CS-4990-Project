package mk.ea.ir.blazercorpscentral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by u786 on 4/26/2015.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = new Intent(getBaseContext(), Splash.class);
        startActivity(intent);
    }
}
