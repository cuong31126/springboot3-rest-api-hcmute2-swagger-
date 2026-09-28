package vn.iotstar.model.graphql;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iotstar.entity.Product;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPage {
    private List<Product> content;
    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int pageSize;
    private boolean first;
    private boolean last;
}
