import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HTMLTemplate {
	/**
	 * 标签类型
	 */
	private String tag;
	/**
	 * 标签名
	 */
	private String tagName;
	/**
	 * 标签class
	 */
	private List<String> tagClass = new ArrayList<String>();;
	/**
	 * 标签id
	 */
	private String tagId;
	/**
	 * 标签事件
	 */
	private Map<String,String>  tagEvent = new HashMap<String,String>();
	/**
	 * 标签属性
	 */
	private Map<String,String>  tagAttr = new HashMap<String,String>();
	/**
	 * 标签内部标签
	 */
	private List<HTMLTemplate> tagElementList = new ArrayList<HTMLTemplate>();
	/**
	 * 标签内部内容
	 */
	private String tagContext;
	
	private static final String[] EMPTY_TAGS ={"input","img","isindex","area","base",
			"basefont","bgsound","embed","col","frame","keygen","link","meta","nextid","param","plaintext","spacer","wbr"};
	
	
	public HTMLTemplate(){}

	public HTMLTemplate(String tag, String tagName, List<String>tagClass, String tagId, Map<String, String> tagEvent,
			Map<String, String> tagAttr) {
		super();
		this.tag = tag;
		this.tagName = tagName;
		this.tagClass = tagClass;
		this.tagId = tagId;
		this.tagEvent = tagEvent;
		this.tagAttr = tagAttr;
	}
	

	public String getTag() {
		return tag;
	}

	public HTMLTemplate addTag(String tag) {
		this.tag = tag;
		return HTMLTemplate.this; 
	}

	public HTMLTemplate addTagName(String tagName) {
		this.tagName = new String(" name=");
		String tagNameCopy = "'"+tagName+"'";
		this.tagName += tagNameCopy;
		return HTMLTemplate.this; 
	}

	public HTMLTemplate addTagClass(String...tagClass) {
		for(String x:tagClass){
			this.tagClass.add(x);
		}
		return HTMLTemplate.this;
	}


	public HTMLTemplate addTagId(String tagId) {
		this.tagId = new String(" id=");
		String tagIdCopy = "'"+tagId+"'";
		this.tagId += tagIdCopy;
		return HTMLTemplate.this; 
	}

	public String getTagName() {
		return tagName;
	}
	public List<String> getTagClass() {
		return tagClass;
	}
	public String getTagId() {
		return tagId;
	}
	public Map<String, String> getTagEvent() {
		return tagEvent;
	}
	public Map<String, String> getTagAttr() {
		return tagAttr;
	}
	

	public String getTagContext() {
		return tagContext;
	}

	public HTMLTemplate addTagContext(String tagContext) {
		this.tagContext = tagContext+"\n";
		return HTMLTemplate.this;
	}

	public HTMLTemplate addTagEvent(Map<String, String> tagEvent) {
		Set<String> keySet = tagEvent.keySet();
		for(String key:keySet){
			String value = tagEvent.get(key);
			this.tagEvent.put(key, value);
		}
		return HTMLTemplate.this;
	}
	public HTMLTemplate addTagEvent(String key,String value) {
		this.tagEvent.put(key, value);
		return HTMLTemplate.this;
	}

	public HTMLTemplate addTagAttr(Map<String, String> tagAttr) {
		Set<String> keySet = tagAttr.keySet();
		for(String key:keySet){
			String value = tagAttr.get(key);
			this.tagAttr.put(key, value);
		}
		return HTMLTemplate.this;
	}
	public HTMLTemplate addTagAttr(String key,String value) {
		this.tagAttr.put(key, value);
		return HTMLTemplate.this;
	}

	public List<HTMLTemplate> getTagElementList() {
		return tagElementList;
	}

	public HTMLTemplate addTagElementList(List<HTMLTemplate> tagElementList) {
		this.tagElementList.addAll(tagElementList);
		return HTMLTemplate.this;
	}
	
	public HTMLTemplate addTagElementList(HTMLTemplate htmlTemplate) {
		this.tagElementList.add(htmlTemplate);
		return HTMLTemplate.this;
	}
	
	/**
	 * generate HTMLTemplate
	 * @return
	 */
	public String getHTMLTemplate(){
		return getHTMLTemplate(HTMLTemplate.this);
	}
	
	
	/**
	 * generate HTMLTemplate 
	 * @return
	 */
	private String getHTMLTemplate(HTMLTemplate bean){
		//最终生成的字符串
		StringBuffer HTMLTemplate = new StringBuffer();
		//标签内部标签属性
		StringBuffer innerTags = new StringBuffer();
		
		String tag = bean.getTag();
		
		List<HTMLTemplate> tagElementList = bean.getTagElementList();
		Iterator<HTMLTemplate> it = tagElementList.iterator();  
		
		if(it.hasNext()){
			while(it.hasNext()) {  
				HTMLTemplate innerBean = it.next();
				it.remove();
				innerTags.append(getHTMLTemplate(innerBean));
			}
		}
		

		
		//标签前缀如<button>放在后面来执行
		StringBuffer startTag = new StringBuffer();
		//标签后缀如</button> 判断是否为空标签，空标签无需后缀
		StringBuffer endTag = new StringBuffer();
		//获取标签内容 空标签则没有
		String tagContext =bean.getTagContext()!=null?bean.getTagContext():"";
		//获取Id
		String tagId  = bean.getTagId()!=null?bean.getTagId():"";
		//获取name
		String tagName  = bean.getTagName()!=null?bean.getTagName():"";
		
		//获取标签Class 属性
		StringBuffer tagClass = new StringBuffer();
		List<String> tagClassList = bean.getTagClass();
		tagClass.append(tagClassList.size()>0?" class='":"");
		for(String str:tagClassList){
			tagClass.append(" "+str+" ");
		}
		tagClass.append(tagClass.length()>5?"  '":"");
		
		
		Map<String,String>  tagEventMap = bean.getTagEvent();
		//标签事件
		StringBuffer tagEvent = new StringBuffer();
		Set<String> eventKeyset = tagEventMap.keySet();
		for(String key:eventKeyset){
			tagEvent.append(" "+key+"=").append("'"+tagEventMap.get(key)+"' ");
		}
		
		
		Map<String, String> tagAttrMap = bean.getTagAttr();
		// 获取其他标签属性如果有 id 和name ,class看外部是否有属性
		StringBuffer tagAttr = new StringBuffer();
		Set<String> attrKeyset = tagAttrMap.keySet();
		for (String key : attrKeyset) {
			if ("name".equals(key)) {
				tagName = "";
			}
			if ("id".equals(key)) {
				tagId = "";
			}
			if ("class".equals(key)) {
				String tagClassCopy = tagClass.toString();
				String newTagClass = tagClassCopy.substring(0, tagClassCopy.length() - 2);
				newTagClass += " " + tagAttrMap.get(key) + "' ";

				tagClass = new StringBuffer(newTagClass);
			} else {
				tagEvent.append(" " + key + "=").append("'" + tagAttrMap.get(key) + "' ");
			}

		}
		//generate Htmltemplate
		startTag.append("<"+tag).append(tagId)
		.append(tagName).append(tagEvent).append(tagClass).append(tagAttr);
		if(!isEmptyTag(tag)){
			startTag.append(">").append("\n").append(tagContext);
			startTag.append(innerTags);
			endTag.append("</"+tag+">");
			HTMLTemplate.append(startTag).append(endTag).append("\n");
		}else{
			startTag.append("/>");
			HTMLTemplate.append(startTag).append("\n");
		}
		return HTMLTemplate.toString();
	}
	
	/**
	 * Determine the tags are EmptyTag or not
	 * @param tag
	 * @return
	 */
	public  boolean isEmptyTag(String tag){
		synchronized(HTMLTemplate.this){
			for(String str:HTMLTemplate.EMPTY_TAGS){
				if(str.equals(tag)){
					return true;
				}
			}
			return false;
		}
	}
	

}
