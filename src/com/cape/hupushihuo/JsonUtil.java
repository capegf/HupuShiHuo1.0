package com.cape.hupushihuo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonUtil {
	String json;

	public JsonUtil(String json) {
		this.json = json;
	}

	public List<Good> getShoes() {
		try {
			JSONArray jsonArray = new JSONArray(json);
			List<Good> list = new ArrayList<Good>();
			Good g;
			for (int i = 0; i < jsonArray.length(); i++) {
				g = new Good();
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				String shoesName = jsonObject.getString("name");
				String id = jsonObject.getString("id");
				String title = jsonObject.getString("title");
				String price = jsonObject.getString("price");
				String url = jsonObject.getString("url");
				String shopId = jsonObject.getString("shop_id");
				String freightPayer = jsonObject.getString("freight_payer");
				String img_url = jsonObject.getString("img_url");
				boolean isVerified = jsonObject.getBoolean("is_verified");
				String soldCount = jsonObject.getString("sold_count");
				String likeCount = jsonObject.getString("like_count");
				String detailUrl = jsonObject.getString("detail_url");
				String img_big_url = jsonObject.getString("img_big_url");
				String brandName = jsonObject.getString("brand_name");
				int brandId = jsonObject.getInt("brand_id");
				String categoryName = jsonObject.getString("category_name");
				String verifiedText = jsonObject.getString("verified_text");
				g.setId(id);
				g.setName(shoesName);
				g.setTitle(title);
				g.setPrice(price);
				g.setUrl(url);
				g.setShopId(shopId);
				g.setFreightPayer(freightPayer);
				g.setImg_url(img_url);
				g.setVerified(isVerified);
				g.setSoldCount(soldCount);
				g.setLikeCount(likeCount);
				g.setDetailUrl(detailUrl);
				g.setImg_big_url(img_big_url);
				g.setBrandName(brandName);
				g.setBrand_id(brandId);
				g.setCategory_name(categoryName);
				g.setVerifiedText(verifiedText);
				Log.e("info",
						"id ->" + g.getId() + " " + "name-->" + g.getName()
								+ " " + "title-->" + g.getTitle() + " "
								+ "price-->" + g.getPrice() + " " + "url-->"
								+ g.getUrl() + " " + "shopID--->"
								+ g.getShopId() + " " + "freightPayer-->"
								+ g.getFreightPayer() + " " + "img_url-->"
								+ g.getImg_url() + " " + "verified-->"
								+ g.getVerified() + " " + "SoldCount-->"
								+ g.getSoldCount() + " " + "LikeCount-->"
								+ g.getLikeCount() + " " + "DetailUrl-->"
								+ g.getDetailUrl() + " " + "Img_big_url-->"
								+ g.getImg_big_url() + " " + "brandName-->"
								+ g.getBrandName() + " " + "brandId-->"
								+ g.getBrand_id() + " " + "Category_name-->"
								+ g.getCategory_name() + " "
								+ "VerifiedText-->" + g.getVerifiedText());
				list.add(g);
			}
			// Log.e("List_Length", list.size() + "");
			return list;
			// }

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
