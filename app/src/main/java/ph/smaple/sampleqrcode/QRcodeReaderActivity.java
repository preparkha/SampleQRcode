package ph.smaple.sampleqrcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;


public class QRcodeReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();

    }

}
