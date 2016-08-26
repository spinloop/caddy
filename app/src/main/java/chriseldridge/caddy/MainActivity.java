package chriseldridge.caddy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    public void onStart() {
        super.onStart();
        
        this.checkPermissions();
    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        scannerView.stopCamera();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();

        scannerView.resumeCameraPreview(this);
    }
    
    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_LONG).show();

        String url = "";

        switch(rawResult.getText()) {
            case "SeverneBlade" :
                url = "https://www.youtube.com/watch?v=NhHaTqiquv4";
                break;
            default :
                url = "https://www.youtube.com/watch?v=22d-ZjYn8BE&feature=youtu.be";
        }

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
