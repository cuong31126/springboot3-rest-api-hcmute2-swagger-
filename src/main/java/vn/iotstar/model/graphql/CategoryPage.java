package vn.iotstar.model.graphql;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iotstar.entity.Category;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPage {
    private List<Category> content;
    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int pageSize;
    private boolean first;
    private boolean last;
}
