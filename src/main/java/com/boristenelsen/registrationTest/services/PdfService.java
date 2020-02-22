package com.boristenelsen.registrationTest.services;

import com.boristenelsen.registrationTest.dao.Angebot;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.google.common.io.ByteStreams;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfService {
    public ByteArrayInputStream generatePdf(ClientBestellung client, Angebot angebot) throws IOException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();


        InputStream input = new ClassPathResource("/static/portly_transparent_300x120.png").getInputStream();
        byte[] bytes = ByteStreams.toByteArray(input);
        Image image = Image.getInstance(bytes);

        image.setAlignment(Element.ALIGN_RIGHT);
        image.setAbsolutePosition(30, 780);
        image.scalePercent(50.0f, 50.0f);
        document.add(image);

        document.add(new Paragraph(" "));
        LineSeparator head = new LineSeparator();
        head.setLineWidth(2.0f);
        document.add(head);


        placeChunck(writer, "Luisental 73", 492, 774);
        placeChunck(writer, "41199 Mönchengladbach", 423, 762);
        placeChunck(writer, "Email: info@portly.de", 444, 750);
        placeChunck(writer, "Internet: www.portly.de", 435, 738);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        Paragraph p = new Paragraph("Bauvorhaben: " + client.getStrasse_hausnummer() + ", " + "Angebot-Nr: " + angebot.getBestellungId(),
                new Font(Font.HELVETICA, 16, Font.BOLD));
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        Paragraph hallo = new Paragraph("Sehr geehrte/r Frau/Herr " + client.getName() + ",",
                new Font(Font.HELVETICA, 12, Font.BOLD));
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(hallo);
        document.add(new Paragraph(" "));


        document.add(new Paragraph("Wir freuen uns Ihnen ein Angebot unterbreiten zu können.",
                new Font(Font.HELVETICA, 12)));
        document.add(new Chunk("Für dieses Bauvorhaben wurde eine Summe von: "));
        document.add(new Chunk(angebot.getGesamtPreis() + " Euro", new Font(Font.HELVETICA, 14, Font.BOLDITALIC, new Color(50, 205, 50))));
        document.add(new Chunk(" als Festpreis für Sie errechnet."));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Hier nochmal eine Übersicht zu ihrem Bauvorhaben: "));

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));


        PdfPTable table = new PdfPTable(2);
        PdfPCell cell = new PdfPCell(new Paragraph("Informationen"));
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell("Gehwegfläche: ");
        table.addCell(client.getGehwegm2() + " m2");
        table.addCell("Hinderniss/e");
        table.addCell(client.getHindernis());
        table.addCell("Anmerkungen");
        table.addCell(client.getAnmerkung());
        table.addCell("Gehwegbreite in cm");
        table.addCell(Double.toString(client.getGehwegbreite()) + " cm");
        table.addCell("Gehweglänge in cm");
        table.addCell("700 cm");
        table.addCell("Plattenlänge in cm");
        table.addCell(Double.toString(client.getPlattenlaenge()) + " cm");
        table.addCell("Plattenbreite in cm");
        table.addCell(Double.toString(client.getPlattenbreite()) + " cm");


        document.add(table);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        LineSeparator ls = new LineSeparator();
        document.add(ls);
        document.add(new Paragraph(" "));
        Paragraph test = new Paragraph("An unser Angebot halten wir uns 3 Monate gebunden. Für Aufmass und Abrechnung gelten Teil B und Teil C der VOB in der neuesten Fassung. Das Zahlungsziel ist sofort nach Rechnungserhalt.");

        document.add(test);
        document.add(new Paragraph("Falls Sie das Angebot bestätigen, werden wir Uns schnellstmöglich um ihr Bauvorhaben kümmern."));


        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void placeChunck(PdfWriter writer, String text, int x, int y) throws IOException {
        PdfContentByte cb = writer.getDirectContent();
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.saveState();
        cb.beginText();
        cb.moveText(x, y);
        cb.setFontAndSize(bf, 12);
        cb.showText(text);
        cb.endText();
        cb.restoreState();
    }
}
