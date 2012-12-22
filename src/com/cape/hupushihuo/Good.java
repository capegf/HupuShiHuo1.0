package com.cape.hupushihuo;

import java.io.Serializable;

public class Good implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String price;
	private String title;
	private String url;
	private int brand_id;
	private String img_url;
	private String img_big_url;
	private String category_name;
	private boolean verified;
	private String id;
	private String shopId;
	private String freightPayer;
	private String soldCount;
	private String likeCount;
	private String detailUrl;
	private String brandName;
	private String verifiedText;

	public boolean getVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = "¥" + price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = "http://shihuo.hupu.com" + img_url;
	}

	public String getImg_big_url() {
		return img_big_url;
	}

	public void setImg_big_url(String img_big_url) {
		this.img_big_url = "http://shihuo.hupu.com" + img_big_url;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getFreightPayer() {
		return freightPayer;
	}

	public void setFreightPayer(String freightPayer) {
		this.freightPayer = freightPayer;
	}

	public String getSoldCount() {
		return soldCount;
	}

	public void setSoldCount(String soldCount) {
		this.soldCount = soldCount;
	}

	public String getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(String likeCount) {
		this.likeCount = likeCount;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getVerifiedText() {
		return verifiedText;
	}

	public void setVerifiedText(String verifiedText) {
		this.verifiedText = verifiedText;
	}

}
