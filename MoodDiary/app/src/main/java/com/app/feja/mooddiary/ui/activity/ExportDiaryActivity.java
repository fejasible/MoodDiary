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

import java.io.File;
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

    @BindView(R.id.id_text_view_export_path)
    TextView exportPath;

    private DatePicker dateStartPicker;
    private DatePicker dateEndPicker;
    private FilePicker filePicker;
    private FilePicker chooseFilePicker;

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

        exportPath.setText(pdfManager.getFilePath().getPath());
        exportPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFilePicker.show();
            }
        });

        this.refreshTheme();
    }

    /**
     * 初始化文件选择器
     */
    private void initFilePicker() {
        filePicker = new FilePicker(this, FilePicker.FILE);
        filePicker.setRootPath(pdfManager.getFilePath().getPath());
        filePicker.setOnFilePickListener(this);

        chooseFilePicker = new FilePicker(this, FilePicker.DIRECTORY);
        chooseFilePicker.setRootPath(pdfManager.getFilePath().getPath());
        chooseFilePicker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
            /**
             * 选择目录响应
             * @param currentPath 当前选择的目录
             */
            @Override
            public void onFilePicked(String currentPath) {
                exportPath.setText(currentPath);
                pdfManager.setFilePath(new File(currentPath));
            }
        });
    }

    /**
     * 初始化日期选择器
     */
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

    /**
     * 返回按钮响应
     */
    @Override
    public void onBackClick() {
        onBackPressed();
    }

    /**
     * 查看历史记录响应
     */
    @Override
    public void onHistoryClick() {
        filePicker.show();
    }

    /**
     * 获取指定时间范围的有效日记，被删除过的不算在内
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回指定时间方位的有效日记
     */
    private List<DiaryEntity> getValidDiaries(DateTime startTime, DateTime endTime) {
        List<DiaryEntity> validDiaryEntities = new ArrayList<>();
        List<DiaryEntity> diaryEntities = diaryDao.getAllDiary();
        for (DiaryEntity diaryEntity : diaryEntities) {
            if (diaryEntity.getCreateTime().getTime() >= startTime.getTime()
                    && diaryEntity.getCreateTime().getTime() < endTime.addDay(1).getTime()) {
                validDiaryEntities.add(diaryEntity);
            }
        }
        return validDiaryEntities;
    }

    /**
     * 检查所选日期是否有效
     *
     * @return 返回检查结果
     */
    private boolean checkDate() {
        DateTime startTime = new DateTime(startDateTextView.getText().toString()).toZeroTime();
        DateTime endTime = new DateTime(endDateTextView.getText().toString()).toZeroTime();
        return getValidDiaries(startTime, endTime).size() > 0;
    }

    /**
     * 导出日记操作
     *
     * @param v 被点击的控件
     */
    @OnClick(R.id.id_export_execute)
    public void onClick(View v) {
        boolean isValid = this.checkDate();
        if (!isValid) {
            Toast.makeText(this, getString(R.string.export_invalid), Toast.LENGTH_SHORT).show();
        } else {
            String fileName = startDateTextView.getText().toString() + "_" + endDateTextView.getText().toString() + ".pdf";
            List<DiaryEntity> entities = getValidDiaries(
                    new DateTime(startDateTextView.getText().toString()).toZeroTime(),
                    new DateTime(endDateTextView.getText().toString()).toZeroTime()
            );
            boolean success = pdfManager.createPDF(fileName, entities);
            if (!success) {
                Toast.makeText(this, getString(R.string.export_fail), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.export_success), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 更新此activity主题
     */
    private void refreshTheme() {
        int color = TheApplication.getThemeData().getColor();
        exportTitleBar.setBackgroundColor(color);
        setDatePickerColor(dateStartPicker, color);
        setDatePickerColor(dateEndPicker, color);
        setFilePickerColor(filePicker, color);
        setFilePickerColor(chooseFilePicker, color);
    }

    /**
     * 历史导出pdf被选择时的响应
     *
     * @param currentPath 被选择pdf的路径
     */
    @Override
    public void onFilePicked(String currentPath) {
        if (!currentPath.endsWith(".pdf")) {
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
