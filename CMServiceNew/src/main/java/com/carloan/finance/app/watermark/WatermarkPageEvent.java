package com.carloan.finance.app.watermark;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.IOException;
import java.net.MalformedURLException;

public class WatermarkPageEvent extends PdfPageEventHelper {

    private Image backgroundImage;

    public WatermarkPageEvent() {
        try {
        	
        	
        	 backgroundImage = Image.getInstance("https://ibb.co/NnZT4FyZ");
            // Use your image URL or path here
     //   backgroundImage=Image.getInstance("CMServiceNew/src/main/resources/templates/bajaj.png");
         //  backgroundImage = Image.getInstance("https://logowik.com/content/uploads/images/bajaj-finance6835.jpg");
    //    backgroundImage =Image.getInstance("https://sdmntprwestus.oaiusercontent.com/files/00000000-8070-5230-8e5e-c4d251058127/raw?se=2025-04-07T19%3A02%3A35Z&sp=r&sv=2024-08-04&sr=b&scid=ce2889e5-df0c-53d5-8dd5-c7c4fb0e0437&skoid=e825dac8-9fae-4e05-9fdb-3d74e1880d5a&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2025-04-07T10%3A25%3A18Z&ske=2025-04-08T10%3A25%3A18Z&sks=b&skv=2024-08-04&sig=2HJIZi4pE%2BMGcDHk2Uunl7kc6I2bGS%2Bh/FH5Kmd9YYU%3D");
            // Scale image to fit the full page size (for A4 it's approx 595x842 pt)
           backgroundImage.scaleAbsolute(400f, 400f); // Adjust size as needed

            // Set opacity (transparency)
            backgroundImage.setTransparency(new int[] { 0x00, 0x10 });  // 0x00 = fully transparent, 0xFF = opaque

        } catch (BadElementException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContentUnder();

        if (backgroundImage != null) {
            // Center the image on the page
            float x = (document.getPageSize().getWidth() - backgroundImage.getScaledWidth()) /2;
            float y = (document.getPageSize().getHeight() - backgroundImage.getScaledHeight()) / 2;

            backgroundImage.setAbsolutePosition(x, y);
            backgroundImage.setCompressionLevel(2);
            
            try {
                canvas.addImage(backgroundImage);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }
}
