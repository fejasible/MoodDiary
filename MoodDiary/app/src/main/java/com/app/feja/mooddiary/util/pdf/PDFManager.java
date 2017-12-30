package com.app.feja.mooddiary.util.pdf;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.DiaryParser;
import com.app.feja.mooddiary.util.DateTime;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * created by fejasible@163.com
 */

public class PDFManager {

    private Context context;
    private File filePath;

    public PDFManager(Context context) {
        this.context = context;
        filePath = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name_en));
    }

    public File getFilePath() {
        return filePath;
    }

    public boolean createDefaultFolder() {
        if (!isSdCardExist()) {
            Toast.makeText(context, "未找到SD卡", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return filePath.mkdir();
        }
    }

    public boolean createPDF(String fileName, List<DiaryEntity> diaryEntities) {
        if(diaryEntities.size() == 0){
            return false;
        }
        Font font = getChineseFont();
        if (font == null) {
            Toast.makeText(context, context.getString(R.string.lost_font), Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + File.separator + fileName);
            Document document = createDocument();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, fileOutputStream);
            PdfReportM1HeaderFooter pdfReportM1HeaderFooter =
                    new PdfReportM1HeaderFooter("", 16, document.getPageSize(), font.getBaseFont());
            pdfWriter.setPageEvent(pdfReportM1HeaderFooter);
            document.open();
            for (int i = 0; i < diaryEntities.size(); i++) {
                createPage(document, diaryEntities.get(i), font);
            }
            document.newPage();
            document.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Document createDocument() {
        Document document = new Document(PageSize.A4, 20, 20, 20, 40);
        String name = context.getString(R.string.app_name_ch);
        document.addTitle(name);
        document.addAuthor(name);
        document.addSubject(name);
        document.addKeywords(name);
        document.addCreator(name);
        document.addCreationDate();
        return document;
    }

    private Document createPage(Document document, DiaryEntity diaryEntity, Font font) throws DocumentException, IOException {

        document.newPage();

        LineSeparator lineSeparator = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, 0);
        document.add(lineSeparator);

        DateTime dateTime = new DateTime(diaryEntity.getCreateTime());
        Paragraph title = new Paragraph(dateTime.toString(DateTime.Format.DATETIME), font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph subtitle = new Paragraph(diaryEntity.getType().getType(), font);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        DiaryParser.DiaryIterator diaryIterator = new DiaryParser(diaryEntity).getIterator();
        while (diaryIterator.hasNext()){
            DiaryParser.Element element = diaryIterator.next();
            if(element.getClazz().equals(String.class)){
                Chunk chunk = new Chunk((String)element.getObject(), font);
                document.add(chunk);
            }else if(element.getClazz().equals(File.class)){
                Image image = Image.getInstance(((File)element.getObject()).getPath());
                float newWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
                if(image.getWidth() > newWidth){
                    float newHeight = image.getHeight() * newWidth / image.getWidth();
                    image.scaleAbsolute(newWidth, newHeight);
                }
                document.add(image);
            }
        }
        return document;
    }

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private Font getChineseFont() {

        Font fontChinese = null;
        try {
            @SuppressWarnings("ResourceType") String fontName = context.getResources().getString(R.raw.simsun);
            fontName += ",1";
            BaseFont bf = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            fontChinese = new Font(bf, 24, Font.NORMAL);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            Log.e("PDFManager", e.toString());
        }
        return fontChinese;
    }


}
