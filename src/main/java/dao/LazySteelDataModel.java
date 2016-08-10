package dao;

import profiles.MaterialProfile;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author Kamalion
 */
public class LazySteelDataModel extends LazyDataModel<MaterialProfile>{
    
    private List<MaterialProfile> datasource;
    
    public LazySteelDataModel(List<MaterialProfile> datasource) {
        this.datasource = datasource;
    }
    
    @Override
    public List<MaterialProfile> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        int dataSize = datasource.size();
        this.setRowCount(dataSize);
        return datasource;
    }
}
