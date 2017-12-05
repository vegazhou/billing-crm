package wang.huaichao;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import wang.huaichao.billgen.PdfBillGenerator;

import java.io.*;
import java.net.URL;

/**
 * Created by Administrator on 9/20/2016.
 */
public class PdfCreator {
    private static final Logger log = LoggerFactory.getLogger(PdfCreator.class);

    private static final String fontName = "simsun.ttc";
    private static final String fontPath = System.getProperty("user.home") +
            File.separator + "bss-rpt" + File.separator + fontName;

    static {

        try {
            _copyFont();
        } catch (IOException e) {
            throw new RuntimeException("failed to copy font to user home directory", e);
        }

    }

    private static void _copyFont() throws IOException {
        File fontFile = new File(fontPath);

        // font already exits
        if (fontFile.exists()) return;

        fontFile.getParentFile().mkdirs();

        InputStream is = PdfBillGenerator.class.getResourceAsStream("/" + fontName);
        FileOutputStream fos = new FileOutputStream(fontPath);
        byte[] buff = new byte[2048];
        int len;
        while ((len = is.read(buff)) != -1) {
            fos.write(buff, 0, len);
        }
        is.close();
        fos.close();
    }

    public static String getFontPath(String clsPth) {
        URL url = wang.huaichao.billgen.PdfRender.class.getResource(clsPth);
        return new File(url.getFile()).getAbsolutePath();
    }

    public static void render(String html, OutputStream output)
            throws DocumentException, IOException {

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);


        ITextFontResolver fontResolver = renderer.getFontResolver();

        fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        renderer.layout();
        renderer.createPDF(output);
    }


}
