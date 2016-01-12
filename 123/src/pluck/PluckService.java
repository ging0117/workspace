package com.verizon.zoetool.pluck;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.log4j.Logger;
import org.json.JSONException;

import com.pluck.sdk.PluckServer;
import com.pluck.sdk.Interfaces.IResponse;
import com.pluck.sdk.Models.External.ExternalResourceKey;
//import com.pluck.sdk.Models.Reactions.Reviews.Review;
import com.pluck.sdk.Models.System.ResponseStatusCode;
import com.pluck.sdk.Models.System.Sorting.ScoreSort;
import com.pluck.sdk.Models.System.Sorting.ScoreSortColumn;
import com.pluck.sdk.Models.System.Sorting.SortOrder;
import com.pluck.sdk.Requests.Reactions.Reviews.ReviewsPageRequest;
import com.pluck.sdk.Responses.Reactions.Reviews.ReviewsPageResponse;
import com.pluck.sdk.auth.UserAuthenticationToken;
import com.pluck.sdk.batching.RequestBatch;
import com.pluck.sdk.batching.ResponseBatch;
import com.pluck.sdk.batching.ResponseEnvelope;
import com.verizon.zoetool.domain.SystemStatus;
import com.verizon.zoetool.utils.AbstractServiceStatusObject;
import com.verizon.zoetool.utils.AppUtils;

public class PluckService extends AbstractServiceStatusObject{
	private static final Logger logger = Logger.getLogger(PluckService.class);
	private AppUtils.Env eEnv;
	private String sUser;
	
	public enum Steps
    {
		OL_CreateOAuthKey,
		OL_GetOAuthToken,
		OL_ProductSearch,
		RB_GetAccessToken,
		RB_GetCustomerProfile,
		OL_GetProductDetailByProductID,
		Pluck_ReviewsPageRequest;
    	
    	@Override public String toString() 
    	{
    		String s = super.toString().replaceAll("_", "-");
    		return s;
    	}
    }
	private int toInt(Steps step)
	{
		switch(step)
		{
		case OL_CreateOAuthKey:
			return 0;
		case OL_GetOAuthToken:
			return 1;
		case OL_ProductSearch:
			return 2;
		case RB_GetAccessToken:
			return 3;
		case RB_GetCustomerProfile:
			return 4;
		case OL_GetProductDetailByProductID:
			return 5;
		case Pluck_ReviewsPageRequest:
			return 6;
		default:
			return -1;
		}
	}
	
	public PluckService(String user, AppUtils.Env env)
	{
		super();
		eEnv = env;
		sUser = user;
		initStatusList(7);
	}
	public void initStatusList(int size)
	{
		list = new ArrayList<SystemStatus>();
		for(int i = 1; i <= size; i++)
		{
			SystemStatus ss = new SystemStatus();
			ss.setStep(i);
			switch(i)
			{
			case 1:
				ss.setName(Steps.OL_CreateOAuthKey.toString()); 
				break;
			case 2:
				ss.setName(Steps.OL_GetOAuthToken.toString()); 
				break;
			case 3:
				ss.setName(Steps.OL_ProductSearch.toString()); 
				break;
			case 4:
				ss.setName(Steps.RB_GetAccessToken.toString()); 
				break;
			case 5:
				ss.setName(Steps.RB_GetCustomerProfile.toString()); 
				break;
			case 6:
				ss.setName(Steps.OL_GetProductDetailByProductID.toString()); 
				break;
			case 7:
				ss.setName(Steps.Pluck_ReviewsPageRequest.toString()); 
				break;
			}
			list.add(ss);
		}		
	}
	
	public void updateStatusList(Steps step, boolean success, String code, String message)
	{
		int i = toInt(step);
		logger.info("[user=" + sUser + "]\t[api=PluckService]\t[step=" + (i + 1) + "_" + step.toString() + "]\t[success=" + success + "]");
		updateStatusNode(i, success, code, message);
	}	
	
	public boolean checkSystemAvailability(String rbtoken, Map<String, String> mapCustInfo, String altcode)
	{
		return callPluckServer(rbtoken, altcode, mapCustInfo);
	}
	private boolean callPluckServer(String rbtoken, String articleid, Map<String, String> mapCustInfo)
	{
		PluckServer ps = new PluckServer(AppUtils.getPluckUrl(eEnv));
		logger.info("[user=" + sUser + "]\t[api=PluckService]\t[url=" + AppUtils.getPluckUrl(eEnv) + "]");
		
		if (AppUtils.getProxySwitch().equalsIgnoreCase("on"))
			ps.setProxy(AppUtils.getProxyUrl(), AppUtils.getProxyPort(), new UsernamePasswordCredentials("", ""));
		
		ExternalResourceKey articleKey = new ExternalResourceKey();
		articleKey.setKey(articleid);
		
		ReviewsPageRequest request = new ReviewsPageRequest();
		request.setReviewedKey(articleKey);
		request.setOneBasedOnPage(1);
		request.setItemsPerPage(2);
		
		ScoreSort sort = new ScoreSort();
		sort.setSortOrder(SortOrder.Descending);
		sort.setScoreSortColumn(ScoreSortColumn.PositiveScore);
		sort.setScoreId("Review");
		request.setSortType(sort);
		
		RequestBatch requests = new RequestBatch();
		try
		{
			requests.addRequest(request);
		}
		catch(JSONException e)
		{
			logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
		}
		
		ResponseBatch responseBatch = null;
		try
		{
			responseBatch = ps.sendRequest(requests, 
					new UserAuthenticationToken(mapCustInfo.get("CustomerNumber"), 
							mapCustInfo.get("DisplayName"), 
							mapCustInfo.get("EmailAddress"), 
							AppUtils.getPluckSharedSecretKey(eEnv)));
		}
		catch(Exception e)
		{
			logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
			updateStatusList(Steps.Pluck_ReviewsPageRequest, false, "", e.getMessage());
			// invalid server url:
			//Exceeded maximum number of connection attempts while trying to submit JSON request.
		}
		
		if (responseBatch != null)
		{
			return handleResponseBatch(responseBatch);
		}
		
		updateStatusList(Steps.Pluck_ReviewsPageRequest, false, "", "");
		return false;
	}
	private boolean handleResponseBatch(ResponseBatch responseBatch)
	{
		List<ResponseEnvelope> envelopes = responseBatch.getEnvelopes();
		IResponse response = envelopes.get(0).getPayload();
		
		String sCode = response.getResponseStatus().getStatusCode().toString();
		
		if(response.getResponseStatus().getStatusCode().equals(ResponseStatusCode.OK))
		{
			if (response instanceof ReviewsPageResponse)
			{
				ReviewsPageResponse reviewsPageResponse = (ReviewsPageResponse)response;
				logger.info("[user=" + sUser + "]\t[api=PluckService]\t[responsecode=" + sCode + "]\t[numberofreviews=" + reviewsPageResponse.getItems().length + "]");
				updateStatusList(Steps.Pluck_ReviewsPageRequest, true, sCode, "");
				return true;
			}
		}
		
		try {
			String sStatus = response.getResponseStatus().toJson().toString();
			logger.info("[user=" + sUser + "]\t[api=PluckService]\t[responsecode=" + sCode + "]\t[responsestatus=" + sStatus + "]");
			updateStatusList(Steps.Pluck_ReviewsPageRequest, false, sCode, response.toJson().toString());
		} catch (JSONException e) {
			updateStatusList(Steps.Pluck_ReviewsPageRequest, false, sCode, e.getMessage());
		}
		return false;
	}
}
