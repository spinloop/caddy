package chriseldridge.caddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(this);
    }

    public void QRScanner(View view) {
        setContentView(scannerView);

        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();

        scannerView.stopCamera();
        setContentView(R.layout.activity_main);
    }
    
    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_LONG).show();

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=22d-ZjYn8BE&feature=youtu.be")));
    }
}
