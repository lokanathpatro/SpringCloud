package com.cts.ecom.sales.model;

public class Item {

 	private Long id;
 	private String name;
    private String description;
    private Long price;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Item() {
    }

    public Item(String name, String description, Long price) {
        this.name = name;
        this.description = description; 
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Item{id=%d, name='%s', description=%s , price=%d}", id, name, description, price);
    }
}
