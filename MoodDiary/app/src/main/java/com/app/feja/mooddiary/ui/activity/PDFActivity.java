package com.app.feja.mooddiary.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.app.feja.mooddiary.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PDFActivity extends BaseActivity {


    @BindView(R.id.id_pdfView)
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String path = bundle.getString(ExportDiaryActivity.BUNDLE_NAME_FILE_PATH);
                if (path != null) {
                    File file = new File(path);
                    pdfView.fromFile(file).load();
                }
            }
        }
    }
}
