package com.verizon.zoetool.service;

import java.util.List;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.orch.account.ProductSearch;
import com.verizon.zoetool.orch.account.GetProductDetailByProductID;
import com.verizon.zoetool.orch.oauth.OAuthUtils;


public class ProductDetailsManager{
	private String sUser;
	public List<String> listofproductsearch;
	public int pagesize, pagenumber;
	
	public ProductDetailsManager(String user)
	{
		sUser = user;	
	}
	

	public String getProductDetails(AppUtils.Env env, String mName)
	{
		//Step 1-2: Get token for OL API calls.
		OAuthUtils oauth = new OAuthUtils(sUser, env);
		String sOAuthToken = oauth.getOAuthToken(env);
		
		//Check Price -Step 3: ProductSearch_MovieName to get a list of product
		ProductSearch movieName = new ProductSearch(sUser, env, sOAuthToken);
		int iPagesize = 20;
		
		List<String> listofproductsearch = movieName.doProductSearch(iPagesize, 1, mName);
			if (listofproductsearch.size() == 0)
			{
				System.out.println("No movie found!");
				return mName + " not found !";	
			}
			else
			{
				//Step 4: loop through the product id list and call getProductDetailsbyProductId
				GetProductDetailByProductID getDetail = new GetProductDetailByProductID(sUser, env, sOAuthToken);
				String sGetDetail = "";
					for (int i =0; i < listofproductsearch.size(); i++)
						{
							String sProductId = listofproductsearch.get(i);
							String response = getDetail.doGetProductDetailByProductID(sProductId); 
							sGetDetail += response;
						}
				return sGetDetail;
			}
		
	}
}