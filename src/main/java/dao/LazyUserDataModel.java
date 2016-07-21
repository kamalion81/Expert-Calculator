package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author Kamalion
 */
public class LazyUserDataModel extends LazyDataModel<UserProfile>{
    
    private List<UserProfile> datasource;
    
    public LazyUserDataModel(List<UserProfile> datasource) {
        this.datasource = datasource;
    }
    
    @Override
    public List<UserProfile> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        int dataSize = datasource.size();
        this.setRowCount(dataSize);
        return datasource;
    }
}
