package com.verizon.zoetool.service;

import java.util.List;

import com.verizon.zoetool.orch.account.GetProductDetailByProductID;
import com.verizon.zoetool.orch.account.ProductSearch;
import com.verizon.zoetool.orch.oauth.OAuthUtils;
import com.verizon.zoetool.scm.SCMCheckPrice;
import com.verizon.zoetool.utils.AppUtils;

public class SCMCheckPriceManager {
	public String sHtmlSCMCheckPrice, sHtmlProductDetails = "";
	private String sUser;
	
	public SCMCheckPriceManager(String user)
	{
		sUser = user;
	}
	
	public void getProductDetails(AppUtils.Env env, String query)
	{
		// Step 1-2: Get token for OL API calls.
		OAuthUtils oauth = new OAuthUtils(sUser, env);
		String sOAuthToken = oauth.getOAuthToken(env);
		
		// Step 3: ProductSearch_MovieName to get a list of product
		ProductSearch search = new ProductSearch(sUser, env, sOAuthToken);
		int iPagesize = 20;
		
		List<String> listProduct = search.doProductSearch(iPagesize, 1, query);
			
		if (listProduct.size() == 0)
		{
			sHtmlProductDetails = "No product found";
		}
		else 
		{
			// Step 4: loop through the product id list and call getProductDetailsbyProductId
			GetProductDetailByProductID getDetail = new GetProductDetailByProductID(sUser, env, sOAuthToken);
			for (int i = 0; i < listProduct.size(); i++)
			
			{
				String sProductId = listProduct.get(i);
				String response = getDetail.doGetProductDetailByProductID(sProductId); 
				if (response.isEmpty()) // error
				{
					sHtmlProductDetails = "Error occurs";
					break;
				}
				sHtmlProductDetails += response;						
			}
		}
	}
	
	public void checkPrice(AppUtils.Env env, String purchaseoptionid)
	{
		SCMCheckPrice checkprice = new SCMCheckPrice (sUser, env);
		sHtmlSCMCheckPrice = checkprice.getSCMChekPrice(sUser, purchaseoptionid);
	}
}

