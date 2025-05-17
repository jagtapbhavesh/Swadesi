package com.project.swadesi.dto;

public class CartItemDTO {

	private Long productId;
    private Integer quantity;
    private String size;
    

    // Constructor
    public CartItemDTO(Long productId, Integer quantity, String size) {
        this.productId = productId;
        this.quantity = quantity;
        this.size = size;
    }

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}



	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartItemDTO [productId=" + productId + ", quantity=" + quantity + "]";
	}
    
    
}
