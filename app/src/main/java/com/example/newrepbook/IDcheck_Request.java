package com.example.newrepbook;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class IDcheck_Request extends StringRequest
{
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://pjsu2000.dothome.co.kr/ID_check.php";
    private Map<String,String> map;

    public IDcheck_Request(String userID, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return map;
    }
}
