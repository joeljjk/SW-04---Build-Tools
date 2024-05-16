package sw.build.tools;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class CreatePDF {

    public static void main(String[] args)  {
        // Specify the output file path
        String outputPath =System.getProperty("user.home") + "/Downloads/target.pdf";
        String ttfPath = System.getProperty("user.home") + "/Downloads/helvetica.ttf";
        String imgPath = System.getProperty("user.home") + "/Downloads/img.png";
        
        // Create a new PDF document
        try (PDDocument document = new PDDocument()) {
            // Add a blank page to the document
            PDPage page = new PDPage();
            document.addPage(page);
            
            // Create a content stream to add content to the page
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Set font and add text

                PDFont font = PDType0Font.load(document, new File(ttfPath));
                contentStream.setFont(font, 22);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 750);
                contentStream.showText("Hello, PDFBox!");
                contentStream.newLine();
                contentStream.setFont(font, 16);
                contentStream.newLine();
                contentStream.showText("This is a simple example of creating a PDF.");
                contentStream.endText();

                // Draw a line
                contentStream.setStrokingColor(Color.BLACK);
                contentStream.moveTo(20, 700);
                contentStream.lineTo(220, 700);
                contentStream.stroke();

                // Insert an image with specified width and height
                PDImageXObject pdImage = PDImageXObject.createFromFile(imgPath, document);
                float imageWidth = 480; // specify desired image width
                float imageHeight = 275; // specify desired image height
                contentStream.drawImage(pdImage, 25, 300, imageWidth, imageHeight);

            }
            
            // Save the document to the specified path
            document.save(new File(outputPath));
            System.out.println("PDF created and saved to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
