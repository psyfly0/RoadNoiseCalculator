/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.Repositories;

import FileReadingAndDataStore.DataStore.DbfData;
import FileReadingAndDataStore.DbfDataModifyHelper.DbfDataHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
/**
 *
 * @author szaboa
 */
/**
 * Implementation of the {@link DbfRepository} interface that provides methods for reading and accessing data from
 * a dBASE file.
 */
public class DbfRepositoryImpl implements DbfRepository {
    private DbfData dbfData;
    private List<DbfData> dbfDataList = new ArrayList<>();
    private List<String> columns;
    private DbfDataHelper dbfDataHelper;
    
    /**
     * Constructs a new instance of DbfRepositoryImpl.
     */
    public DbfRepositoryImpl() {
        this.dbfDataHelper = new DbfDataHelper(columns);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void readDbfFile(File file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file);
             FileChannel channel = inputStream.getChannel();
             DbaseFileReader dbfReader = new DbaseFileReader(channel, false, 
                     Charset.defaultCharset())) {

            // get column names from header
            DbaseFileHeader header = dbfReader.getHeader();
            List<String> columns = new ArrayList<>();
            for (int i = 0; i < header.getNumFields(); i++) {
                columns.add(header.getFieldName(i));
            }

            List<List<Object>> records = new ArrayList<>(); // create a new list to hold the records
            while (dbfReader.hasNext()) {
                Object[] record = dbfReader.readEntry();
                List<Object> recordList = new ArrayList<>(Arrays.asList(record));
                records.add(recordList); // Add the new ArrayList to the records list
            }

            // create a new DbfData object using the records list
            dbfData = new DbfData(records, columns);
            dbfReader.close();
        } catch (IOException e) {
            throw new IOException("Error reading DBF file: " + file.getName(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DbfData getDbfData() {
        return dbfData;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDbfFile(File file, Map<String, String> selectedColumnMap) {
         DbfData modifiedDbfData = dbfDataHelper.updateDbfFile(dbfData, selectedColumnMap);
         dbfDataList.add(modifiedDbfData);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<DbfData> getFilteredDbfDataList() {
        return dbfDataList;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DbfData getFilteredDbfData(int index) {
        if (index >= 0 && index < dbfDataList.size()) {
            return dbfDataList.get(index);
        } else {
            return null;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDbfData(int index) {
        dbfDataList.remove(index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllDbfData() {
        dbfDataList.clear();
    }
}
