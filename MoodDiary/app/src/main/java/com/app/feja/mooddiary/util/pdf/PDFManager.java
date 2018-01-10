package com.app.feja.mooddiary.util.pdf;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.DiaryParser;
import com.app.feja.mooddiary.util.DateTime;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }


    public boolean createPDF(String fileName, List<DiaryEntity> diaryEntities) {
        if (!isSdCardExist()) {
            Toast.makeText(context, "未找到SD卡", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (diaryEntities.size() == 0) {
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
        Document document = new Document(PageSize.A4, 20, 20, 0, 30);
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

        DateTime dateTime = new DateTime(diaryEntity.getCreateTime());
        Paragraph title = new Paragraph(dateTime.toString(DateTime.Format.DATETIME), font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph subtitle = new Paragraph(diaryEntity.getType().getType(), font);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        LineSeparator lineSeparator = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -4);
        subtitle.add(lineSeparator);
        document.add(subtitle);

        DiaryParser.ContentIterator contentIterator = new DiaryParser(diaryEntity).getIterator();
        while (contentIterator.hasNext()) {
            DiaryParser.Element element = contentIterator.next();
            if (element.getClazz().equals(String.class)) {
                String content = (String) element.getObject();
                Chunk chunk = createChunk(content, font);
                document.add(chunk);
            }
            else if (element.getClazz().equals(File.class)) {
                Image image = createImage((File) element.getObject());
                float newWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
                if (image.getWidth() > newWidth) {
                    float newHeight = image.getHeight() * newWidth / image.getWidth();
                    image.scaleAbsolute(newWidth, newHeight);
                }
                document.add(image);
            }
        }
        return document;
    }

    private Chunk createChunk(String content, Font font){
        return new Chunk(content, font);
    }

    private Image createImage(File imagePath) throws IOException, BadElementException {
        return Image.getInstance(imagePath.getPath());
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
        }
        return fontChinese;
    }
}
