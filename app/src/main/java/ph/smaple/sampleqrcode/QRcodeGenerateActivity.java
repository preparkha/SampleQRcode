package ph.smaple.sampleqrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRcodeGenerateActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_QRCODE_GENERATE_ACTIVITY = 2000;

    private EditText editUrl;
    private ImageView ivQRcode;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generate);

        editUrl = (EditText) findViewById(R.id.edit_url);
        ivQRcode = (ImageView) findViewById(R.id.iv_qr_code);

        findViewById(R.id.btn_generate_qr_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String tempText = editUrl.getText().toString();

                if (bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                }

                // 로딩바 구현하기....
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            bitmap = TextToImageEncode(tempText);
                            ivQRcode.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });

    }

    @Override
    protected void onDestroy() {
        bitmap.recycle();
        bitmap = null;
        super.onDestroy();
    }

    public final static int QRcodeWidth = 500;

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(android.R.color.black) : getResources().getColor(android.R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}

