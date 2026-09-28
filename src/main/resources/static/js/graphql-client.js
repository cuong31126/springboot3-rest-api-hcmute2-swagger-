/**
 * graphql-client.js
 * Quản lý các lệnh gọi API GraphQL thông qua fetch/AJAX tới endpoint /graphql
 */

const GRAPHQL_ENDPOINT = '/graphql';

/**
 * Gửi truy vấn hoặc mutation GraphQL lên server
 * @param {string} query - Chuỗi GraphQL query/mutation
 * @param {object} variables - Tham số truyền vào query (nếu có)
 * @param {string} operationName - Tên operation (tùy chọn)
 * @returns {Promise<any>}
 */
async function callGraphQL(query, variables = {}, operationName = null) {
    // Kích hoạt event để panel xem live query cập nhật
    if (window.onGraphQLRequestSent) {
        window.onGraphQLRequestSent(query, variables);
    }

    try {
        const response = await fetch(GRAPHQL_ENDPOINT, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                query: query,
                variables: variables,
                operationName: operationName
            })
        });

        const result = await response.json();

        if (window.onGraphQLResponseReceived) {
            window.onGraphQLResponseReceived(result);
        }

        if (result.errors && result.errors.length > 0) {
            console.error('GraphQL Error:', result.errors);
            const errMsg = result.errors.map(e => e.message).join(' | ');
            throw new Error(errMsg);
        }

        return result.data;
    } catch (err) {
        console.error('Lỗi khi gọi GraphQL:', err);
        throw err;
    }
}

// ==========================================
// Các Query cho Trang HOME
// ==========================================

// 1. Hiển thị tất cả product có price từ thấp đến cao
async function getProductsSortedByPriceAsc() {
    const query = `
        query GetProductsSortedByPriceAsc {
            productsSortedByPriceAsc {
                productId
                productName
                quantity
                unitPrice
                images
                description
                discount
                status
                createDate
                category {
                    categoryId
                    categoryName
                }
            }
        }
    `;
    const data = await callGraphQL(query);
    return data.productsSortedByPriceAsc;
}

// 2. Lấy tất cả product của 01 category
async function getProductsByCategory(categoryId) {
    const query = `
        query GetProductsByCategory($categoryId: ID!) {
            productsByCategory(categoryId: $categoryId) {
                productId
                productName
                quantity
                unitPrice
                images
                description
                discount
                status
                createDate
                category {
                    categoryId
                    categoryName
                }
            }
        }
    `;
    const data = await callGraphQL(query, { categoryId: String(categoryId) });
    return data.productsByCategory;
}

// 3. Lấy tất cả category (để render các tab/nút lọc và dropdown)
async function getAllCategories() {
    const query = `
        query GetAllCategories {
            allCategories {
                categoryId
                categoryName
                icon
            }
        }
    `;
    const data = await callGraphQL(query);
    return data.allCategories;
}

// 4. Lấy chi tiết 1 product theo ID
async function getProductById(id) {
    const query = `
        query GetProductById($id: ID!) {
            productById(id: $id) {
                productId
                productName
                quantity
                unitPrice
                images
                description
                discount
                status
                createDate
                category {
                    categoryId
                    categoryName
                }
            }
        }
    `;
    const data = await callGraphQL(query, { id: String(id) });
    return data.productById;
}

// ==========================================
// Các Query & Mutation cho Quản lý CRUD & Phân trang (ADMIN)
// ==========================================

// 5. Phân trang & Tìm kiếm Product
async function getProductsPaged(page = 0, size = 6, keyword = "", categoryId = null) {
    const query = `
        query GetProductsPaged($page: Int, $size: Int, $keyword: String, $categoryId: ID) {
            productsPaged(page: $page, size: $size, keyword: $keyword, categoryId: $categoryId) {
                content {
                    productId
                    productName
                    quantity
                    unitPrice
                    images
                    description
                    discount
                    status
                    createDate
                    category {
                        categoryId
                        categoryName
                    }
                }
                totalPages
                totalElements
                pageNumber
                pageSize
                first
                last
            }
        }
    `;
    const vars = { page, size, keyword };
    if (categoryId && categoryId > 0) {
        vars.categoryId = String(categoryId);
    }
    const data = await callGraphQL(query, vars);
    return data.productsPaged;
}

// 6. Phân trang & Tìm kiếm Category
async function getCategoriesPaged(page = 0, size = 5, keyword = "") {
    const query = `
        query GetCategoriesPaged($page: Int, $size: Int, $keyword: String) {
            categoriesPaged(page: $page, size: $size, keyword: $keyword) {
                content {
                    categoryId
                    categoryName
                    icon
                }
                totalPages
                totalElements
                pageNumber
                pageSize
                first
                last
            }
        }
    `;
    const data = await callGraphQL(query, { page, size, keyword });
    return data.categoriesPaged;
}

// 7. Lấy chi tiết Category theo ID
async function getCategoryById(id) {
    const query = `
        query GetCategoryById($id: ID!) {
            categoryById(id: $id) {
                categoryId
                categoryName
                icon
            }
        }
    `;
    const data = await callGraphQL(query, { id: String(id) });
    return data.categoryById;
}

// 8. Mutation thêm Category
async function createCategory(categoryName, icon) {
    const query = `
        mutation CreateCategory($input: CategoryInput!) {
            createCategory(input: $input) {
                categoryId
                categoryName
                icon
            }
        }
    `;
    const data = await callGraphQL(query, { input: { categoryName, icon } });
    return data.createCategory;
}

// 9. Mutation cập nhật Category
async function updateCategory(id, categoryName, icon) {
    const query = `
        mutation UpdateCategory($id: ID!, $input: CategoryInput!) {
            updateCategory(id: $id, input: $input) {
                categoryId
                categoryName
                icon
            }
        }
    `;
    const data = await callGraphQL(query, { id: String(id), input: { categoryName, icon } });
    return data.updateCategory;
}

// 10. Mutation xóa Category
async function deleteCategory(id) {
    const query = `
        mutation DeleteCategory($id: ID!) {
            deleteCategory(id: $id)
        }
    `;
    const data = await callGraphQL(query, { id: String(id) });
    return data.deleteCategory;
}

// 11. Mutation thêm Product
async function createProduct(productInput) {
    const query = `
        mutation CreateProduct($input: ProductInput!) {
            createProduct(input: $input) {
                productId
                productName
                quantity
                unitPrice
                images
                description
                discount
                status
                category {
                    categoryId
                    categoryName
                }
            }
        }
    `;
    const data = await callGraphQL(query, { input: productInput });
    return data.createProduct;
}

// 12. Mutation cập nhật Product
async function updateProduct(id, productInput) {
    const query = `
        mutation UpdateProduct($id: ID!, $input: ProductInput!) {
            updateProduct(id: $id, input: $input) {
                productId
                productName
                quantity
                unitPrice
                images
                description
                discount
                status
                category {
                    categoryId
                    categoryName
                }
            }
        }
    `;
    const data = await callGraphQL(query, { id: String(id), input: productInput });
    return data.updateProduct;
}

// 13. Mutation xóa Product
async function deleteProduct(id) {
    const query = `
        mutation DeleteProduct($id: ID!) {
            deleteProduct(id: $id)
        }
    `;
    const data = await callGraphQL(query, { id: String(id) });
    return data.deleteProduct;
}

// Định dạng tiền tệ VNĐ
function formatCurrency(amount) {
    if (amount == null) return '0 ₫';
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
}
