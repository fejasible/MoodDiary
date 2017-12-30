package com.app.feja.mooddiary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.model.dao.impl.DiaryDaoImpl;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.util.DateTime;
import com.app.feja.mooddiary.util.pdf.PDFManager;
import com.app.feja.mooddiary.widget.setting.ExportTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.FilePicker;

/**
 * created by fejasible@163.com
 */
public class ExportDiaryActivity extends BaseActivity implements ExportTitleBar.OnTitleBarClickListener,
        FilePicker.OnFilePickListener {

    public static final String BUNDLE_NAME_FILE_PATH = "FilePath";

    @BindView(R.id.id_export_title_bar)
    ExportTitleBar exportTitleBar;

    @BindView(R.id.id_text_view_export_start_date)
    TextView startDateTextView;

    @BindView(R.id.id_text_view_export_end_date)
    TextView endDateTextView;

    @BindView(R.id.id_export_execute)
    TextView executeTextView;

    private DatePicker dateStartPicker;
    private DatePicker dateEndPicker;
    private FilePicker filePicker;

    private DiaryDao diaryDao;
    private PDFManager pdfManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_diary);

        ButterKnife.bind(this);

        pdfManager = new PDFManager(this);
        diaryDao = new DiaryDaoImpl(getApplicationContext());
        this.initDatePicker();
        this.initFilePicker();

        startDateTextView.setText(new DateTime().toString(DateTime.Format.DATE));
        endDateTextView.setText(new DateTime().toString(DateTime.Format.DATE));

        exportTitleBar.setOnTitleBarClickListener(this);
        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateStartPicker.show();
            }
        });
        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateEndPicker.show();
            }
        });
        this.refreshTheme();
    }

    private void initFilePicker() {
        filePicker = new FilePicker(this, FilePicker.FILE);
        filePicker.setRootPath(pdfManager.getFilePath().getPath());
        filePicker.setOnFilePickListener(this);
    }

    private void initDatePicker() {
        DateTime dateTime = new DateTime();

        dateStartPicker = new DatePicker(this);
        dateStartPicker.setTitleText(getString(R.string.please_choose_date));
        dateStartPicker.setRangeEnd(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
        dateStartPicker.setSelectedItem(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
        dateStartPicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                DateTime dateTime1 = new DateTime(
                        Integer.valueOf(year),
                        Integer.valueOf(month),
                        Integer.valueOf(day)
                );
                startDateTextView.setText(dateTime1.toString(DateTime.Format.DATE));
            }
        });

        dateEndPicker = new DatePicker(this);
        dateEndPicker.setTitleText(getString(R.string.please_choose_date));
        dateEndPicker.setRangeEnd(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
        dateEndPicker.setSelectedItem(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
        dateEndPicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                DateTime dateTime1 = new DateTime(
                        Integer.valueOf(year),
                        Integer.valueOf(month),
                        Integer.valueOf(day)
                );
                endDateTextView.setText(dateTime1.toString(DateTime.Format.DATE));
            }
        });

    }

    @Override
    public void onBackClick() {
        onBackPressed();
    }

    @Override
    public void onHistoryClick() {
        filePicker.show();
    }

    private List<DiaryEntity> getValidDiaries() {
        List<DiaryEntity> validDiaryEntities = new ArrayList<>();
        DateTime startTime = new DateTime(startDateTextView.getText().toString());
        DateTime endTime = new DateTime(endDateTextView.getText().toString());
        List<DiaryEntity> diaryEntities = diaryDao.getAllDiary();
        for (DiaryEntity diaryEntity : diaryEntities) {
            if (diaryEntity.getCreateTime().after(startTime.addDay(-1).toDate())
                    && diaryEntity.getCreateTime().before(endTime.addDay(1).toDate())) {
                validDiaryEntities.add(diaryEntity);
            }
        }
        return validDiaryEntities;
    }

    private boolean checkDate() {

        DateTime startTime = new DateTime(startDateTextView.getText().toString());
        DateTime endTime = new DateTime(endDateTextView.getText().toString());

        boolean isValid = endTime.addDay(1).after(startTime);

        if (isValid) {
            List<DiaryEntity> diaryEntities = diaryDao.getAllDiary();
            if(diaryEntities.size() == 0){
                return false;
            }
            for (DiaryEntity diaryEntity : diaryEntities) {
                if (!diaryEntity.getCreateTime().after(startTime.addDay(-1).toDate())
                        || !diaryEntity.getCreateTime().before(endTime.addDay(1).toDate())) {
                    isValid = false;
                    break;
                }
            }
        }

        return isValid;
    }

    /**
     * 导入日记操作
     * @param v 被点击的控件
     */
    @OnClick(R.id.id_export_execute)
    public void onClick(View v) {
        boolean isValid = this.checkDate();
        if (!isValid) {
            Toast.makeText(this, getString(R.string.export_invalid), Toast.LENGTH_SHORT).show();
        } else {
            String fileName = startDateTextView.getText().toString() + "_" + endDateTextView.getText().toString() + ".pdf";
            boolean success = pdfManager.createPDF(fileName, getValidDiaries());
            if (!success) {
                Toast.makeText(this, getString(R.string.export_fail), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.export_success), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void refreshTheme() {
        int color = TheApplication.getThemeData().getColor();
        exportTitleBar.setBackgroundColor(color);
        setDatePickerColor(dateStartPicker, color);
        setDatePickerColor(dateEndPicker, color);
        setFilePickerColor(filePicker, color);
    }

    @Override
    public void onFilePicked(String currentPath) {
        if(!currentPath.endsWith(".pdf")){
            Toast.makeText(this, getString(R.string.not_pdf), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, PDFActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_NAME_FILE_PATH, currentPath);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
