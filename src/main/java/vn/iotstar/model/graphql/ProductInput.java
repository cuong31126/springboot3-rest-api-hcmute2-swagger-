package vn.iotstar.model.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
    private String productName;
    private int quantity;
    private double unitPrice;
    private String images;
    private String description;
    private Double discount;
    private Integer status;
    private Long categoryId;
}
