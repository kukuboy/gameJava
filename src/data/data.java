package data;

import java.util.List;

public class data {
    int flag;
    String code;
    String msg;
    List data;
    public void setFlag(int flag){
        this.flag=flag;
    }
    public int getFlag(){
        return flag;
    }
    public void setCode(String code){
        this.code=code;
    }
    public String getCode(){
        return code;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }
    public String getMsg(){
        return msg;
    }
    public void setData(List data){
        this.data=data;
    }
    public List getData(){
        return data;
    }
}
