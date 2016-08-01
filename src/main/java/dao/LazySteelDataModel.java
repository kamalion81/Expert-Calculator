package dao;

import profiles.SteelProfile;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author Kamalion
 */
public class LazySteelDataModel extends LazyDataModel<SteelProfile>{
    
    private List<SteelProfile> datasource;
    
    public LazySteelDataModel(List<SteelProfile> datasource) {
        this.datasource = datasource;
    }
    
    @Override
    public List<SteelProfile> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        int dataSize = datasource.size();
        this.setRowCount(dataSize);
        return datasource;
    }
}
