package com.project.swadesi.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name="id")
    private Long id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="price")
    private double price;
    
    @Column(name="description")
    private String description;
    
    @Column(name="url")
    private String url;
    
    @Column(name="smallCount")
    private int smallCount;
    
    @Column(name="mediumCount")
    private int mediumCount;
    
    @Column(name="largeCount")
    private int largeCount;
    
    @Column(name="extraLargeCount")
    private int extraLargeCount;
    
    @Column(name="doubleXLCount")
    private int doubleXLCount;

    @Column(name="category")
    private String category;
    
    @Column(name="collection")
    private String collection;
    
    
    
	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSmallCount() {
		return smallCount;
	}

	public void setSmallCount(int smallCount) {
		this.smallCount = smallCount;
	}

	public int getMediumCount() {
		return mediumCount;
	}

	public void setMediumCount(int mediumCount) {
		this.mediumCount = mediumCount;
	}

	public int getLargeCount() {
		return largeCount;
	}

	public void setLargeCount(int largeCount) {
		this.largeCount = largeCount;
	}

	public int getExtraLargeCount() {
		return extraLargeCount;
	}

	public void setExtraLargeCount(int extraLargeCount) {
		this.extraLargeCount = extraLargeCount;
	}

	public int getDoubleXLCount() {
		return doubleXLCount;
	}

	public void setDoubleXLCount(int doubleXLCount) {
		this.doubleXLCount = doubleXLCount;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + ", url="
				+ url + ", smallCount=" + smallCount + ", mediumCount=" + mediumCount + ", largeCount=" + largeCount
				+ ", extraLargeCount=" + extraLargeCount + ", doubleXLCount=" + doubleXLCount + ", category=" + category
				+ ", collection=" + collection + "]";
	}
	
	
}     
