package storage

import grails.transaction.Transactional
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

import static org.apache.poi.ss.usermodel.Cell.*

/**
 * Service to import items from XLS or CSV files
 * Columns should be: externalId, name, quantity
 */
@Transactional
class UploadService {

    static final Integer START_SHEET = 0
    static final Integer START_ROW = 1
    static final Map <Integer, String> header = [0 : "externalId", 1: "name", 2: "quantity"]

    def importItemsFromFile(InputStream is, String filename) throws IllegalArgumentException {
        if (filename.endsWith(".xls")) {
            importItemsExcel(is)
        }
        if (filename.endsWith(".csv")) {
            importItemsCsv(is)
        }
    }

    def importItemsExcel (InputStream is) {
        def workbook = new HSSFWorkbook(is)
        def sheet = workbook.getSheetAt(START_SHEET)
        for (int i = START_ROW; i <= sheet.lastRowNum; i++) {
            def row = sheet.getRow i
            Map<String, Object> values = new HashMap<>()
            for (cell in row.cellIterator()) {
                switch(cell.cellType) {
                    case 1:
                        values["${header[cell.columnIndex]}"] = cell.stringCellValue
                        break
                    case 0:
                        values["${header[cell.columnIndex]}"] = cell.numericCellValue
                        break
                    default:
                        throw new IllegalArgumentException("Illegal cell format")
                }
            }
            def item = Item.findByExternalId(values["externalId"]);
            if (item.name == values["name"]) {
                item.quantity += values["quantity"]
                item.save flush:true
            }
            else {
                throw new IllegalArgumentException("Illegal item name")
            }
        }
    }

    def importItemsCsv(InputStream is) {
        CSVParser parser = CSVParser.parse(is, StandardCharsets.UTF_8, CSVFormat.DEFAULT)
        List<CSVRecord> records = parser.getRecords()
        for (int row = START_ROW; row < records.size(); row++) {
            Map<String, Object> values = new HashMap<>()
            for (int column = 0; column < records.get(row).size(); column++) {
                values[header[column]] = records.get(row).get(column)
            }
            def item = Item.findByExternalId(values["externalId"]);
            if (item.name == values["name"]) {
                new Integer("5")
                item.quantity += new Integer(values["quantity"])
                item.save flush:true
            }
            else {
                throw new IllegalArgumentException("Illegal item name")
            }
        }
    }
}
