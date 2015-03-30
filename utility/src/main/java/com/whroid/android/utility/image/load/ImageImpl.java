package com.whroid.android.utility.image.load;

import java.io.Serializable;

import com.whroid.android.utility.StringUtil;

public  class ImageImpl implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -359881791010095187L;
	public static final int TYPE_IMAGE = 01;//普通图片
	public static final int TYPE_AVATAR = 02;//缩略图

	protected String id;  //文件保存的名称
	protected String url; //网络请求地址,或者本地路径
	protected String tag; //tag

	public ImageImpl()
	{
		this.tag = System.currentTimeMillis() +""+(int)(Math.random()*50);
	}
	public ImageImpl(String id,String url)
	{
		this.id = id;
		this.url = url;
		this.tag = MD5FileNameGenerator.generate(url);
	}
	public ImageImpl(String url)
	{
		this.url = url;
		this.tag = MD5FileNameGenerator.generate(url);
	}
	public int  type;
	public void setType(int type)
	{
		this.type = type;
	}
	public int getType()
	{
		return type;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	/**
	 * 缩略图缓存的key
	 * @return
	 */
	public   String getMemoryCacheKey()
	{
		return url;
	}
	/**
	 * 缓存在文件中的key
	 * @return
	 */
	public  String getFileCacheName()
	{
		if(StringUtil.isEmpty(id))
		{
			return MD5FileNameGenerator.generate(url);
		}
		return id;
	}
	@Override
	public String toString() {
		return "ImageImpl [id=" + id + ", url=" + url + ", tag=" + tag
				+ ", type=" + type + "]";
	}
	
}
